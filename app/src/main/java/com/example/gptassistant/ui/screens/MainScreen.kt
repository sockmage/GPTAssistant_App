package com.example.gptassistant.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gptassistant.ui.screens.role.RoleSelectionScreen
import com.example.gptassistant.ui.screens.chat.ChatScreen

@Composable
fun MainScreen() {
    var selectedRole by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (selectedRole == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Добро пожаловать в GPT Assistant!",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                RoleSelectionScreen(onRoleSelected = { role ->
                    selectedRole = role
                })
            }
        } else {
            ChatScreen(
                role = selectedRole!!,
                onBackPressed = { selectedRole = null }
            )
        }
    }
} 