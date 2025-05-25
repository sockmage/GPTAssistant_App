package com.example.gptassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gptassistant.ui.theme.GPTAssistantTheme
import com.example.gptassistant.ui.screens.chat.ChatScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GPTAssistantTheme {
                ChatScreen(
                    role = "student",
                    onBackPressed = {}
                )
            }
        }
    }
} 