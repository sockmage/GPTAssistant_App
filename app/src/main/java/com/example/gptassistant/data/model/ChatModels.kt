package com.example.gptassistant.data.model

data class ChatRequest(
    val model: String = "gpt-4",
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