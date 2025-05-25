package com.example.gptassistant.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class AskRequest(val message: String)
data class AskResponse(val response: String)

interface ApiService {
    @POST("ask")
    suspend fun ask(@Body request: AskRequest): Response<AskResponse>
}
