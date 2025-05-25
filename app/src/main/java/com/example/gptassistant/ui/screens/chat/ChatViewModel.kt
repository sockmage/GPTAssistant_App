package com.example.gptassistant.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gptassistant.data.model.Message
import com.example.gptassistant.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping.asStateFlow()

    fun sendMessage(content: String) {
        viewModelScope.launch {
            // Add user message
            val userMessage = Message(content = content, isUser = true)
            _messages.value = _messages.value + userMessage

            // Show typing indicator
            _isTyping.value = true

            try {
                // Get AI response
                val response = chatRepository.getResponse(content)
                val aiMessage = Message(content = response, isUser = false)
                _messages.value = _messages.value + aiMessage
            } catch (e: Exception) {
                // Handle error
                val errorMessage = Message(
                    content = "Извините, произошла ошибка: ${e.message}",
                    isUser = false
                )
                _messages.value = _messages.value + errorMessage
            } finally {
                _isTyping.value = false
            }
        }
    }

    fun resetConversation() {
        _messages.value = emptyList()
    }
} 