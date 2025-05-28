package com.example.gptassistant.data.repository

import com.example.gptassistant.data.api.OpenAIApi
import com.example.gptassistant.data.model.ChatRequest
import com.example.gptassistant.data.model.ChatResponse
import com.example.gptassistant.data.model.MessageRequest
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

@ViewModelScoped
class ChatRepository @Inject constructor(
    private val api: OpenAIApi
) {
    suspend fun getResponse(message: String): String {
        if (message.contains("photo", ignoreCase = true) || message.contains("найди фото", ignoreCase = true)) {
            // Реальный поиск фото через LAIH_Proxy
            val client = OkHttpClient()
            val url = "https://laihproxy-production.up.railway.app/image/search?q=" + java.net.URLEncoder.encode(message, "UTF-8")
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val body = response.body?.string()
                val imageUrl = JSONObject(body).optString("image")
                if (imageUrl.isNotBlank()) {
                    return "[image]$imageUrl[/image]"
                }
            }
            return "Извините, не удалось найти фото по вашему запросу."
        }
        val request = ChatRequest(
            messages = listOf(MessageRequest("user", message))
        )
        val response = api.chat(authorization = "", request = request)
        return response.choices.firstOrNull()?.message?.content ?: "Извините, не удалось получить ответ."
    }
} 