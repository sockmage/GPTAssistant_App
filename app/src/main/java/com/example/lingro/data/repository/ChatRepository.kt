package com.example.lingro.data.repository

import com.example.lingro.data.api.OpenAIApi
import com.example.lingro.data.model.ChatRequest
import com.example.lingro.data.model.ChatResponse
import com.example.lingro.data.model.MessageRequest
import com.example.lingro.data.model.ImageGenerationRequest
import com.example.lingro.data.model.ImageGenerationResponse
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@ViewModelScoped
class ChatRepository @Inject constructor(
    private val api: OpenAIApi
) {

    // Обычный текстовый чат
    suspend fun getChatResponse(message: String): String {
        val request = ChatRequest(
            messages = listOf(MessageRequest("user", message)),
            model = "gpt-4o"
        )
        val response = api.chat(authorization = "", request = request)
        return response.choices.firstOrNull()?.message?.content ?: "Извините, не удалось получить ответ."
    }

    // Анализ изображения с текстом (Vision)
    suspend fun getVisionResponse(file: File, prompt: String, model: String = "gpt-4o"): String {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val promptPart = prompt.toRequestBody("text/plain".toMediaTypeOrNull())

        val response = api.chatVision(file = filePart, prompt = promptPart)
        return response.choices.firstOrNull()?.message?.content ?: "Извините, не удалось проанализировать изображение."
    }

    // Генерация изображения (DALL·E 3)
    suspend fun generateImage(prompt: String, size: String = "1024x1024"): String {
        val request = ImageGenerationRequest(
            prompt = prompt,
            n = 1,
            size = size
        )
        val response = api.generateImage(request = request)
        // DALL·E 3 возвращает список объектов data, каждый содержит url
        return response.data.firstOrNull()?.url ?: "Извините, не удалось сгенерировать изображение."
    }
} 