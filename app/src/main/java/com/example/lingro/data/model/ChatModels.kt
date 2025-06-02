package com.example.lingro.data.model

data class ChatRequest(
    val model: String = "gpt-4o",
    val messages: List<MessageRequest>,
    val temperature: Double = 0.7,
    val max_tokens: Int = 1000
)

data class MessageRequest(
    val role: String,
    val content: String
)

data class ChatResponse(
    val id: String,
    val object_: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage
)

data class Choice(
    val index: Int,
    val message: Message,
    val finish_reason: String
)

data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)

data class ImageGenerationRequest(
    val prompt: String,
    val n: Int = 1,
    val size: String = "1024x1024"
)

data class ImageGenerationResponse(
    val created: Long,
    val data: List<ImageData>
)

data class ImageData(
    val url: String? = null,
    val b64_json: String? = null // Опционально, если используем b64
) 