package com.example.lingro.data.api

import com.example.gptassistant.data.model.ChatRequest
import com.example.gptassistant.data.model.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAIApi {
    @POST("chat")
    suspend fun chat(
        @Header("Authorization") authorization: String,
        @Body request: ChatRequest
    ): ChatResponse
} 