package com.example.gptassistant.data.api

import com.example.gptassistant.data.model.ChatRequest
import com.example.gptassistant.data.model.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAIApi {
    @POST("v1/chat/completions")
    suspend fun chat(
        @Header("Authorization") authorization: String = "Bearer ${BuildConfig.OPENAI_API_KEY}",
        @Body request: ChatRequest
    ): ChatResponse
} 