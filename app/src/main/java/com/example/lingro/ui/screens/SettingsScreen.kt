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
import androidx.compose.material.icons.automirrored.outlined.VolumeUp
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
import android.speech.tts.Voice
import com.example.lingro.ui.components.TTSManager
import java.util.Locale
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.outlined.Language

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentTheme: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    onAboutClick: () -> Unit,
    onClose: () -> Unit,
    onClearChat: () -> Unit,
    onVoiceSelected: (Voice) -> Unit = {},
    selectedVoice: Voice? = null,
    selectedLanguage: String? = null,
    onLanguageSelected: (String) -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    BackHandler(onBack = onClose)
    val context = LocalContext.current
    var showClearDialog by remember { mutableStateOf(false) }
    var showVoiceDialog by remember { mutableStateOf(false) }
    var showNoVoicesDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showTTSHelpDialog by remember { mutableStateOf(false) }
    val voices = remember { mutableStateListOf<Voice>() }
    val languageList = listOf(
        "ru" to "Русский",
        "en" to "English",
        "de" to "Deutsch",
        "fr" to "Français",
        "es" to "Español",
        "it" to "Italiano",
        "zh" to "中文",
        "ja" to "日本語",
        "ko" to "한국어"
    )
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
            Text("Озвучивание", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 6.dp, top = 8.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 2.dp,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Column(Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
                    ListItem(
                        headlineContent = { Text("Язык озвучивания" + if (selectedLanguage != null) ": ${languageList.find { it.first == selectedLanguage }?.second ?: selectedLanguage}" else "", style = MaterialTheme.typography.bodyLarge) },
                        leadingContent = {
                            Icon(Icons.Outlined.Language, contentDescription = "Язык озвучивания", modifier = Modifier.size(24.dp))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .clickable { showLanguageDialog = true }
                            .padding(vertical = 2.dp)
                    )
                    ListItem(
                        headlineContent = { Text("Выбрать голос", style = MaterialTheme.typography.bodyLarge) },
                        leadingContent = {
                            Icon(Icons.AutoMirrored.Outlined.VolumeUp, contentDescription = "Выбрать голос", modifier = Modifier.size(24.dp))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(0.dp))
                            .clickable {
                                val availableVoices = TTSManager.getVoices().filter { it.locale.language == (selectedLanguage ?: Locale.getDefault().language) }
                                if (availableVoices.isEmpty()) {
                                    showNoVoicesDialog = true
                                } else {
                                    showVoiceDialog = true
                                }
                            }
                            .padding(vertical = 2.dp)
                    )
                    ListItem(
                        headlineContent = { Text("Установить новые голоса", style = MaterialTheme.typography.bodyLarge) },
                        leadingContent = {
                            Icon(Icons.Outlined.Info, contentDescription = "Установить новые голоса", modifier = Modifier.size(24.dp))
                        },
                        trailingContent = {
                            IconButton(onClick = { showTTSHelpDialog = true }) {
                                Icon(Icons.AutoMirrored.Outlined.HelpOutline, contentDescription = "Инструкция по голосам")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                            .clickable {
                                val intent = android.content.Intent(android.provider.Settings.ACTION_VOICE_INPUT_SETTINGS)
                                context.startActivity(intent)
                            }
                            .padding(vertical = 2.dp)
                    )
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
                        Triple("Мы в Telegram", Icons.AutoMirrored.Outlined.Send) {
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
    if (showNoVoicesDialog) {
        AlertDialog(
            onDismissRequest = { showNoVoicesDialog = false },
            title = { Text("Нет голосов") },
            text = { Text("На устройстве не найдено голосов для озвучивания. Проверьте настройки TTS или установите голосовые движки.") },
            confirmButton = {
                TextButton(onClick = { showNoVoicesDialog = false }) {
                    Text("ОК")
                }
            }
        )
    }
    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text("Выберите язык озвучивания") },
            text = {
                Column {
                    languageList.forEach { (code, name) ->
                        ListItem(
                            headlineContent = { Text(name) },
                            leadingContent = {
                                RadioButton(
                                    selected = selectedLanguage == code,
                                    onClick = { onLanguageSelected(code); showLanguageDialog = false }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onLanguageSelected(code); showLanguageDialog = false }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLanguageDialog = false }) {
                    Text("Закрыть")
                }
            }
        )
    }
    if (showVoiceDialog) {
        LaunchedEffect(selectedLanguage) {
            voices.clear()
            voices.addAll(TTSManager.getVoices().filter { it.locale.language == (selectedLanguage ?: Locale.getDefault().language) })
        }
        AlertDialog(
            onDismissRequest = { showVoiceDialog = false },
            title = { Text("Выберите голос") },
            text = {
                Column {
                    if (voices.isEmpty()) {
                        Text("Нет доступных голосов", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    } else {
                        voices.forEach { voice ->
                            ListItem(
                                headlineContent = { Text(voice.name) },
                                supportingContent = { Text(voice.locale.displayName) },
                                leadingContent = {
                                    RadioButton(
                                        selected = selectedVoice?.name == voice.name,
                                        onClick = { onVoiceSelected(voice); showVoiceDialog = false }
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onVoiceSelected(voice); showVoiceDialog = false }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showVoiceDialog = false }) {
                    Text("Закрыть")
                }
            }
        )
    }
    if (showTTSHelpDialog) {
        AlertDialog(
            onDismissRequest = { showTTSHelpDialog = false },
            title = { Text("Как сменить или установить голос TTS?") },
            text = {
                Text("1. Нажмите 'Установить новые голоса'. Откроются системные настройки TTS.\n\n2. Выберите движок (например, Google TTS) и перейдите в его настройки.\n\n3. В настройках выберите язык и скачайте нужные голоса.\n\n4. После установки вернитесь в приложение и выберите голос в разделе 'Выбрать голос'.\n\n⚠️ Некоторые голоса требуют интернет или загрузки через Play Market.")
            },
            confirmButton = {
                TextButton(onClick = { showTTSHelpDialog = false }) {
                    Text("Понятно")
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