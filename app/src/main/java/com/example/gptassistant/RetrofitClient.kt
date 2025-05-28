package com.example.lingro
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.gptassistant.data.api.OpenAIApi

object RetrofitClient {
    // Базовый URL API OpenAI
    private const val BASE_URL = "https://lingroproxy-production.up.railway.app/"

    // Создаем объект API один раз и переиспользуем
    val api: OpenAIApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Указываем базовый URL
            .addConverterFactory(GsonConverterFactory.create()) // Добавляем конвертер JSON
            .build() // Собираем Retrofit-клиент
            .create(OpenAIApi::class.java) // Создаем реализацию нашего интерфейса
    }
}