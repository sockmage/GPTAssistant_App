package com.example.gptassistant.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Refresh
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentTheme: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    onAboutClick: () -> Unit,
    onClose: () -> Unit,
    onClearChat: () -> Unit,
    onResetRole: () -> Unit
) {
    BackHandler(onBack = onClose)
    val context = LocalContext.current
    var showClearDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Настройки", style = MaterialTheme.typography.headlineMedium) },
                actions = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Outlined.Close, contentDescription = "Закрыть")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            item {
                Text("Тема", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 8.dp, top = 8.dp))
            }
            items(ThemeMode.entries.size) { idx ->
                val mode = ThemeMode.entries[idx]
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onThemeChange(mode) }
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                ) {
                    RadioButton(
                        selected = currentTheme == mode,
                        onClick = { onThemeChange(mode) },
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(mode.displayName, style = MaterialTheme.typography.bodyLarge)
                }
            }
            item {
                Text("Действия", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 4.dp, top = 8.dp))
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ListItem(
                        headlineContent = { Text("Очистить чат", style = MaterialTheme.typography.bodyLarge) },
                        leadingContent = {
                            Icon(Icons.Outlined.Delete, contentDescription = "Очистить чат", modifier = Modifier.size(24.dp))
                        },
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true),
                                onClick = { showClearDialog = true }
                            )
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                    )
                    ListItem(
                        headlineContent = { Text("Сбросить роль", style = MaterialTheme.typography.bodyLarge) },
                        leadingContent = {
                            Icon(Icons.Outlined.Refresh, contentDescription = "Сбросить роль", modifier = Modifier.size(24.dp))
                        },
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true),
                                onClick = { onResetRole() }
                            )
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                    )
                }
            }
            item {
                Text("Информация", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 4.dp, top = 8.dp))
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ListItem(
                        headlineContent = { Text("Мы в Telegram", style = MaterialTheme.typography.bodyLarge) },
                        leadingContent = {
                            Icon(Icons.Outlined.Info, contentDescription = "Telegram", modifier = Modifier.size(24.dp))
                        },
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true),
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Language_assistant1_bot"))
                                    context.startActivity(intent)
                                }
                            )
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                    )
                    ListItem(
                        headlineContent = { Text("О разработчиках", style = MaterialTheme.typography.bodyLarge) },
                        leadingContent = {
                            Icon(Icons.Outlined.Info, contentDescription = "О разработчиках", modifier = Modifier.size(24.dp))
                        },
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true),
                                onClick = { onAboutClick() }
                            )
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                    )
                }
            }
        }
        if (showClearDialog) {
            AlertDialog(
                onDismissRequest = { showClearDialog = false },
                title = { Text("Вы уверены?") },
                text = { Text("Вы потеряете всю историю чата") },
                confirmButton = {
                    Button(
                        onClick = {
                            showClearDialog = false
                            onClearChat()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Да", color = MaterialTheme.colorScheme.onError)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showClearDialog = false }) {
                        Text("Нет")
                    }
                }
            )
        }
    }
}

enum class ThemeMode(val displayName: String) {
    LIGHT("Светлая"),
    DARK("Тёмная"),
    SYSTEM("Системная")
} 