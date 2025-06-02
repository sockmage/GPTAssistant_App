package com.example.lingro.data.api

import com.example.lingro.data.model.ChatRequest
import com.example.lingro.data.model.ChatResponse
import com.example.lingro.data.model.ImageGenerationRequest
import com.example.lingro.data.model.ImageGenerationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface OpenAIApi {
    @POST("chat")
    suspend fun chat(
        @Header("Authorization") authorization: String,
        @Body request: ChatRequest
    ): ChatResponse

    @Multipart
    @POST("chat/vision")
    suspend fun chatVision(
        @Part file: MultipartBody.Part,
        @Part("prompt") prompt: RequestBody
    ): ChatResponse // Vision API возвращает тот же формат ответа, что и обычный чат

    @POST("image/generate")
    suspend fun generateImage(
        @Body request: ImageGenerationRequest
    ): ImageGenerationResponse
} 