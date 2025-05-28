package com.example.lingro.data.model

data class Message(
    val content: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val attachmentUrl: String? = null,
    val attachmentType: String? = null
) 