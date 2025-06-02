package com.example.lingro.ui.screens

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
import androidx.compose.material.icons.outlined.Send
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.selection.selectable
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentTheme: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    onAboutClick: () -> Unit,
    onClose: () -> Unit,
    onClearChat: () -> Unit,
    onResetRole: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    BackHandler(onBack = onClose)
    val context = LocalContext.current
    var showClearDialog by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item {
            Text("Тема", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 6.dp, top = 8.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 2.dp,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Column(Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
                    ThemeMode.entries.forEach { mode ->
                        Surface(
                            shape = MaterialTheme.shapes.medium,
                            color = Color.Transparent
                        ) {
                            ListItem(
                                headlineContent = { Text(mode.displayName, style = MaterialTheme.typography.bodyLarge) },
                                leadingContent = {
                                    RadioButton(
                                        selected = currentTheme == mode,
                                        onClick = { onThemeChange(mode) },
                                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onThemeChange(mode) }
                                    .padding(vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
        item {
            Text("Действия", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 6.dp, top = 8.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 2.dp,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Column(Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = Color.Transparent
                    ) {
                        ListItem(
                            headlineContent = { Text("Очистить чат", style = MaterialTheme.typography.bodyLarge) },
                            leadingContent = {
                                Icon(Icons.Outlined.Delete, contentDescription = "Очистить чат", modifier = Modifier.size(24.dp))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showClearDialog = true }
                                .padding(vertical = 2.dp)
                        )
                    }
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = Color.Transparent
                    ) {
                        ListItem(
                            headlineContent = { Text("Сбросить роль", style = MaterialTheme.typography.bodyLarge) },
                            leadingContent = {
                                Icon(Icons.Outlined.Refresh, contentDescription = "Сбросить роль", modifier = Modifier.size(24.dp))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onResetRole() }
                                .padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
        item {
            Text("Информация", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 6.dp, top = 8.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 2.dp,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Column(Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = Color.Transparent
                    ) {
                        ListItem(
                            headlineContent = { Text("Мы в Telegram", style = MaterialTheme.typography.bodyLarge) },
                            leadingContent = {
                                Icon(Icons.Outlined.Send, contentDescription = "Telegram", modifier = Modifier.size(24.dp))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Language_assistant1_bot"))
                                    context.startActivity(intent)
                                }
                                .padding(vertical = 2.dp)
                        )
                    }
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = Color.Transparent
                    ) {
                        ListItem(
                            headlineContent = { Text("О разработчиках", style = MaterialTheme.typography.bodyLarge) },
                            leadingContent = {
                                Icon(Icons.Outlined.Info, contentDescription = "О разработчиках", modifier = Modifier.size(24.dp))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onAboutClick() }
                                .padding(vertical = 2.dp)
                        )
                    }
                }
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

enum class ThemeMode(val displayName: String) {
    LIGHT("Светлая"),
    DARK("Тёмная"),
    SYSTEM("Системная")
} 