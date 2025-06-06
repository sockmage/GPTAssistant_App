package com.example.lingro.ui.screens.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lingro.ui.components.ChatMessage
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.KeyEventType
import kotlinx.coroutines.launch
import androidx.activity.compose.BackHandler
import android.net.Uri
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import com.example.lingro.ui.components.TypingIndicatorAnimated
import com.example.lingro.ui.components.AnimatedChatMessage
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import com.example.lingro.data.model.Message
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import com.example.lingro.ui.components.TTSManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    onBackPressed: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel(),
    selectedVoice: String
) {
    var messageText by remember { mutableStateOf("") }
    val messages by viewModel.messages.collectAsState()
    val isTyping by viewModel.isTyping.collectAsState()
    val listState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }
    var showHistoryDialog by remember { mutableStateOf(false) }
    var showHelpDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.sendMessage(context, messageText, it)
            messageText = ""
        }
    }
    val ttsManager = remember { TTSManager(context) }
    var speakingMessageId by remember { mutableStateOf<Long?>(null) }
    var ttsLoadingMessageId by remember { mutableStateOf<Long?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    BackHandler(onBack = onBackPressed)

    fun handleSpeak(message: Message) {
        speakingMessageId = message.timestamp
        coroutineScope.launch {
            ttsManager.speak(
                message.content,
                selectedVoice,
                onDone = { speakingMessageId = null },
                onLoadingStart = { ttsLoadingMessageId = message.timestamp },
                onLoadingEnd = { ttsLoadingMessageId = null }
            )
        }
    }

    fun handleStopSpeak() {
        ttsManager.stop()
        speakingMessageId = null
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0)
    ) { innerScaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
                .padding(innerScaffoldPadding)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                if (messages.isEmpty() && messageText.isBlank()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ChatBubbleOutline,
                            contentDescription = "Нет сообщений",
                            modifier = Modifier.size(96.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                        )
                    }
                } else {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(messages, key = { it.timestamp }) { message ->
                            AnimatedChatMessage(message = message) { msg ->
                                ChatMessage(
                                    message = msg,
                                    onSpeak = { handleSpeak(it) },
                                    isSpeaking = speakingMessageId == msg.timestamp,
                                    isTtsLoading = ttsLoadingMessageId == msg.timestamp,
                                    onStopSpeak = { handleStopSpeak() }
                                )
                            }
                        }
                    }
                }
            }

            if (isTyping) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    TypingIndicatorAnimated()
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(focusRequester)
                            .onKeyEvent { event ->
                                if (event.type == KeyEventType.KeyUp && event.key == Key.Enter && !event.isShiftPressed) {
                                    if (messageText.isNotBlank() && !isTyping) {
                                        viewModel.sendMessage(context, messageText)
                                        messageText = ""
                                    }
                                    true
                                } else {
                                    false
                                }
                            },
                        placeholder = { Text("Введите сообщение...", style = MaterialTheme.typography.bodyMedium) },
                        singleLine = true,
                        shape = MaterialTheme.shapes.extraLarge,
                        trailingIcon = {
                            Row {
                                AnimatedVisibility(
                                    visible = messageText.isNotBlank(),
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    IconButton(onClick = { messageText = "" }) {
                                        Icon(Icons.Outlined.Clear, contentDescription = "Очистить")
                                    }
                                }
                                AnimatedVisibility(
                                    visible = messageText.isNotBlank(),
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    IconButton(
                                        onClick = {
                                            if (messageText.isNotBlank() && !isTyping) {
                                                viewModel.sendMessage(context, messageText)
                                                messageText = ""
                                            }
                                        },
                                        enabled = !isTyping
                                    ) {
                                        if (isTyping) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(24.dp),
                                                strokeWidth = 2.dp
                                            )
                                        } else {
                                            Icon(Icons.AutoMirrored.Outlined.Send, contentDescription = "Отправить")
                                        }
                                    }
                                }
                                IconButton(
                                    onClick = {
                                        imagePickerLauncher.launch("image/*")
                                    },
                                    enabled = !isTyping
                                ) {
                                    Icon(Icons.Outlined.AttachFile, contentDescription = "Прикрепить файл")
                                }
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                            disabledIndicatorColor = MaterialTheme.colorScheme.outlineVariant
                        )
                    )
                }
            }

            AnimatedVisibility(visible = showHistoryDialog) {
                AlertDialog(
                    onDismissRequest = { showHistoryDialog = false },
                    confirmButton = {
                        TextButton(onClick = { showHistoryDialog = false }) {
                            Text("OK")
                        }
                    },
                    icon = { Icon(Icons.Outlined.History, contentDescription = null) },
                    title = { Text("История чатов") },
                    text = {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                if (viewModel.chatHistory.isEmpty()) {
                                    Text("История пуста", color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(vertical = 16.dp))
                                } else {
                                    viewModel.chatHistory.forEachIndexed { idx, chat ->
                                        ListItem(
                                            headlineContent = { Text("Чат #${idx + 1}") },
                                            supportingContent = { Text("${chat.size} сообщений") },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable(enabled = false) {}, // В будущем: восстановление чата
                                            leadingContent = {
                                                Icon(Icons.Outlined.History, contentDescription = null)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }

            if (showHelpDialog) {
                AlertDialog(
                    onDismissRequest = { showHelpDialog = false },
                    confirmButton = {
                        TextButton(onClick = { showHelpDialog = false }) {
                            Text("OK")
                        }
                    },
                    title = { Text("Как пользоваться чатом?") },
                    text = { Text("В этом окне вы можете вести диалог с ИИ-помощником. Задавайте вопросы, получайте объяснения, копируйте ответы. Используйте стрелку назад для возврата к выбору роли.") }
                )
            }
        }
    }
} 