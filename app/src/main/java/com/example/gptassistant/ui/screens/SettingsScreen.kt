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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(0.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text("Настройки", style = MaterialTheme.typography.headlineSmall) },
            actions = {
                IconButton(onClick = onClose) {
                    Icon(Icons.Outlined.Close, contentDescription = "Закрыть")
                }
            }
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Тема", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                ThemeMode.entries.forEachIndexed { idx, mode ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onThemeChange(mode) }
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = currentTheme == mode,
                            onClick = { onThemeChange(mode) }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(mode.displayName)
                    }
                    if (idx < ThemeMode.entries.size - 1) {
                        Divider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 0.5.dp)
                    }
                }
            }
        }
        Divider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 0.5.dp)
        Card(
            modifier = Modifier.fillMaxWidth().clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Language_assistant1_bot"))
                context.startActivity(intent)
            },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
                Icon(Icons.Outlined.Info, contentDescription = "Telegram", modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Мы в Telegram", style = MaterialTheme.typography.titleMedium)
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth().clickable { onAboutClick() },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(Modifier.padding(16.dp)) {
                Text("О разработчиках", style = MaterialTheme.typography.titleMedium)
            }
        }
        Divider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 0.5.dp)
        Card(
            modifier = Modifier.fillMaxWidth().clickable { onClearChat() },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
                Icon(Icons.Outlined.Delete, contentDescription = "Очистить чат", modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Очистить чат", style = MaterialTheme.typography.titleMedium)
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth().clickable { onResetRole() },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
                Icon(Icons.Outlined.Refresh, contentDescription = "Сбросить роль", modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Сбросить роль", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

enum class ThemeMode(val displayName: String) {
    LIGHT("Светлая"),
    DARK("Тёмная"),
    SYSTEM("Системная")
} 