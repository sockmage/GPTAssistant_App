package com.example.lingro.data.repository

import com.example.lingro.data.api.OpenAIApi
import com.example.lingro.data.model.ChatRequest
import com.example.lingro.data.model.ChatResponse
import com.example.lingro.data.model.MessageRequest
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@ViewModelScoped
class ChatRepository @Inject constructor(
    private val api: OpenAIApi
) {
    suspend fun getResponse(message: String, model: String = "gpt-4o"): String {
        android.util.Log.d("HTTP", "getResponse вызван с message: $message")
        if (message.contains("photo", ignoreCase = true) || message.contains("найди фото", ignoreCase = true)) {
            android.util.Log.d("HTTP", "Условие поиска фото сработало для: $message")
            return try {
                withContext(Dispatchers.IO) {
                    val client = OkHttpClient()
                    val url = "https://lingroddgimageproxy-production.up.railway.app/image/search?q=" + java.net.URLEncoder.encode(message, "UTF-8")
                    val request = Request.Builder()
                        .url(url)
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                        .build()
                    val response = client.newCall(request).execute()
                    val code = response.code
                    val messageResp = response.message
                    val headers = response.headers
                    val body = response.body?.string()
                    android.util.Log.d("HTTP", "URL: $url")
                    android.util.Log.d("HTTP", "Code: $code")
                    android.util.Log.d("HTTP", "Message: $messageResp")
                    android.util.Log.d("HTTP", "Headers: $headers")
                    android.util.Log.d("HTTP", "Body: ${body ?: "null"}")
                    if (body == null) {
                        return@withContext "Ошибка поиска изображения: code=$code, message=$messageResp, body is null"
                    }
                    try {
                        val imageUrl = org.json.JSONObject(body).optString("image")
                        if (imageUrl.isNotBlank()) {
                            val safeImageUrl = if (imageUrl.startsWith("http://")) imageUrl.replaceFirst("http://", "https://") else imageUrl
                            return@withContext "[image]$safeImageUrl[/image]"
                        } else {
                            return@withContext "Ошибка поиска изображения: code=$code, message=$messageResp, body=$body"
                        }
                    } catch (e: Exception) {
                        return@withContext "Ошибка парсинга JSON: ${e.message}, code=$code, message=$messageResp, body=$body"
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("HTTP", "Ошибка при поиске фото: ${e.message}", e)
                "Ошибка при поиске фото: ${e.message}"
            }
        }
        val request = ChatRequest(
            messages = listOf(MessageRequest("user", message)),
            model = model
        )
        val response = api.chat(authorization = "", request = request)
        return response.choices.firstOrNull()?.message?.content ?: "Извините, не удалось получить ответ."
    }
} 