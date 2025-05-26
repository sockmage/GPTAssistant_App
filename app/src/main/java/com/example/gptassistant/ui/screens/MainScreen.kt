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
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import com.example.gptassistant.ui.screens.SettingsScreen
import com.example.gptassistant.ui.screens.ThemeMode
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.togetherWith
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gptassistant.ui.screens.chat.ChatViewModel
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit
) {
    var selectedRole by remember { mutableStateOf<String?>(null) }
    var showSettings by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) }

    val chatViewModel: ChatViewModel = hiltViewModel()

    // AlertDialog теперь вне AnimatedContent
    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            confirmButton = {
                TextButton(onClick = { showAboutDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text("О разработчиках") },
            text = { Text("От @cockmage и @nssklns") }
        )
    }

    AnimatedContent(
        targetState = Triple(selectedRole, showSettings, showAboutDialog),
        transitionSpec = {
            if (targetState.first == null && initialState.first != null) {
                // Возврат на начальный экран — сдвиг вправо
                slideInHorizontally(initialOffsetX = { -it }) + fadeIn() togetherWith slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
            } else if (targetState.first != null && initialState.first == null) {
                // Переход к чату — сдвиг влево
                slideInHorizontally(initialOffsetX = { it }) + fadeIn() togetherWith slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
            } else {
                fadeIn() + scaleIn(initialScale = 0.96f) togetherWith fadeOut() + scaleOut(targetScale = 0.96f)
            }
        }
    ) { (role, settings, _) ->
        when {
            settings -> {
                SettingsScreen(
                    currentTheme = themeMode,
                    onThemeChange = onThemeModeChange,
                    onAboutClick = { showAboutDialog = true },
                    onClose = { showSettings = false },
                    onClearChat = { chatViewModel.resetConversation() },
                    onResetRole = { selectedRole = null; showSettings = false }
                )
            }
            role == null -> {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                            title = { Text("Language AI Helper", style = MaterialTheme.typography.headlineSmall) },
                            actions = {
                                IconButton(onClick = { showSettings = true }) {
                                    Icon(Icons.Outlined.Settings, contentDescription = "Настройки")
                                }
                            }
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                            .widthIn(max = 600.dp)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                        AnimatedVisibility(visible = true, enter = fadeIn()) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(32.dp))
                RoleSelectionScreen(onRoleSelected = { selectedRole = it })
                            }
                        }
            }
            if (showInfoDialog) {
                        AnimatedVisibility(
                            visible = showInfoDialog,
                            enter = fadeIn() + scaleIn(initialScale = 0.92f),
                            exit = fadeOut() + scaleOut(targetScale = 0.92f)
                        ) {
                AlertDialog(
                    onDismissRequest = { showInfoDialog = false },
                    confirmButton = {
                        TextButton(onClick = { showInfoDialog = false }) {
                            Text("OK")
                        }
                    },
                                title = { Text("О приложении") },
                                text = { Text("Language AI Helper помогает преподавателям совершенствовать навыки преподавания, а ученикам — учиться эффективнее. Используйте чат для объяснений, вопросов и совместного обучения!") }
                )
            }
        }
                }
            }
            else -> {
        ChatScreen(
            onBackPressed = { selectedRole = null }
        )
            }
        }
    }
} 