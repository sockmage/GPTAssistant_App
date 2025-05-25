package com.example.gptassistant.data.repository

import com.example.gptassistant.data.api.OpenAIApi
import com.example.gptassistant.data.model.ChatRequest
import com.example.gptassistant.data.model.ChatResponse
import com.example.gptassistant.data.model.MessageRequest
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ChatRepository @Inject constructor(
    private val api: OpenAIApi
) {
    suspend fun getResponse(message: String): String {
        val request = ChatRequest(
            messages = listOf(MessageRequest("user", message))
        )
        val response = api.chat(authorization = "", request = request)
        return response.choices.firstOrNull()?.message?.content ?: "Извините, не удалось получить ответ."
    }
} 