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

    // AnimatedContent должен быть внутри MaterialTheme, а не снаружи!
    // Поэтому MainScreen должен вызываться уже внутри Theme, а не наоборот.
    // Если MaterialTheme вызывается выше, просто убедись, что AnimatedContent не оборачивает Theme.
    // Если Theme вызывается внутри MainScreen, оберни всё в Theme и только потом AnimatedContent.
    // ... existing code ...

    AnimatedContent(
        targetState = Triple(selectedRole, showSettings, showAboutDialog),
        transitionSpec = {
            fadeIn() + scaleIn(initialScale = 0.96f) togetherWith fadeOut() + scaleOut(targetScale = 0.96f)
        }
    ) { (role, settings, _) ->
        when {
            settings -> {
                SettingsScreen(
                    currentTheme = themeMode,
                    onThemeChange = onThemeModeChange,
                    onAboutClick = { showAboutDialog = true },
                    onClose = { showSettings = false }
                )
                if (showAboutDialog) {
                    AnimatedVisibility(
                        visible = showAboutDialog,
                        enter = fadeIn() + scaleIn(initialScale = 0.92f),
                        exit = fadeOut() + scaleOut(targetScale = 0.92f)
                    ) {
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
                }
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
            floatingActionButton = {
                        if (!showSettings) {
                FloatingActionButton(
                    onClick = { showInfoDialog = true },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                ) {
                                Icon(Icons.Outlined.Info, contentDescription = "О приложении")
                            }
                }
            },
                    floatingActionButtonPosition = FabPosition.End,
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
                Text(
                                    text = "Добро пожаловать в Language AI Helper!",
                                    style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Language AI Helper помогает преподавателям совершенствовать навыки преподавания, а ученикам — учиться эффективнее. Используйте чат для объяснений, вопросов и совместного обучения!",
                                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(bottom = 16.dp)
                )
                                Spacer(modifier = Modifier.height(16.dp))
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
                    role = role,
            onBackPressed = { selectedRole = null }
        )
            }
        }
    }
} 