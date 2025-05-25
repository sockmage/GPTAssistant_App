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
import androidx.compose.material.icons.Icons
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.filled.Info


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selectedRole by remember { mutableStateOf<String?>(null) }
    var showInfoDialog by remember { mutableStateOf(false) }

    if (selectedRole == null) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Language AI Helper", style = MaterialTheme.typography.headlineSmall) },
                    actions = {}
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showInfoDialog = true },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                ) {
                    Icon(Icons.Filled.Info, contentDescription = "Информация")
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Добро пожаловать в Language AI Helper!",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                RoleSelectionScreen(onRoleSelected = { role ->
                    selectedRole = role
                })
            }
            if (showInfoDialog) {
                AlertDialog(
                    onDismissRequest = { showInfoDialog = false },
                    confirmButton = {
                        TextButton(onClick = { showInfoDialog = false }) {
                            Text("OK")
                        }
                    },
                    title = { Text("О разработчиках") },
                    text = { Text("От @cockmage и @nssklns") }
                )
            }
        }
    } else {
        ChatScreen(
            role = selectedRole!!,
            onBackPressed = { selectedRole = null }
        )
    }
} 