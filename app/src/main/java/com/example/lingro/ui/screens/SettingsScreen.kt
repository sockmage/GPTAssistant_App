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
import androidx.compose.foundation.shape.RoundedCornerShape

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
                    ThemeMode.entries.forEachIndexed { idx, mode ->
                        val shape = when (idx) {
                            0 -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            ThemeMode.entries.lastIndex -> RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                            else -> RoundedCornerShape(0.dp)
                        }
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
                                .clip(shape)
                                .clickable { onThemeChange(mode) }
                                .padding(vertical = 2.dp)
                        )
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
                    listOf(
                        Pair("Очистить чат", Icons.Outlined.Delete to { showClearDialog = true }),
                        Pair("Сбросить роль", Icons.Outlined.Refresh to { onResetRole() })
                    ).forEachIndexed { idx, (text, pair) ->
                        val (icon, onClick) = pair
                        val shape = when (idx) {
                            0 -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            1 -> RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                            else -> RoundedCornerShape(0.dp)
                        }
                        ListItem(
                            headlineContent = { Text(text, style = MaterialTheme.typography.bodyLarge) },
                            leadingContent = {
                                Icon(icon, contentDescription = text, modifier = Modifier.size(24.dp))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape)
                                .clickable { onClick() }
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
                    listOf(
                        Triple("Мы в Telegram", Icons.Outlined.Send) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Language_assistant1_bot"))
                            context.startActivity(intent)
                        },
                        Triple("О разработчиках", Icons.Outlined.Info) { onAboutClick() }
                    ).forEachIndexed { idx, (text, icon, onClick) ->
                        val shape = when (idx) {
                            0 -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            1 -> RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                            else -> RoundedCornerShape(0.dp)
                        }
                        ListItem(
                            headlineContent = { Text(text, style = MaterialTheme.typography.bodyLarge) },
                            leadingContent = {
                                Icon(icon, contentDescription = text, modifier = Modifier.size(24.dp))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape)
                                .clickable { onClick() }
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