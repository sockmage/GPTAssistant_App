package com.example.gptassistant.ui.screens.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gptassistant.ui.components.ChatMessage
import com.example.gptassistant.ui.components.TypingIndicator
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.animation.AnimatedVisibility
import kotlinx.coroutines.launch
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.History
import androidx.compose.animation.slideInVertically
import androidx.activity.compose.BackHandler
import android.net.Uri
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.rememberCoroutineScope

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChatScreen(
    onBackPressed: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    var messageText by remember { mutableStateOf("") }
    val messages by viewModel.messages.collectAsState()
    val isTyping by viewModel.isTyping.collectAsState()
    val listState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }
    var showHistoryDialog by remember { mutableStateOf(false) }
    var showHelpDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.sendAttachment(it.toString(), "image")
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    BackHandler(onBack = onBackPressed)

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -40 }),
                exit = fadeOut()
            ) {
                TopAppBar(
                    title = { Text("Чат", style = MaterialTheme.typography.headlineMedium) },
                    navigationIcon = {
                        IconButton(onClick = onBackPressed) {
                            Icon(Icons.Outlined.ArrowBack, contentDescription = "Назад")
                        }
                    },
                    actions = {
                        IconButton(onClick = { showHelpDialog = true }) {
                            Icon(Icons.Outlined.HelpOutline, contentDescription = "Помощь")
                        }
                    }
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (messages.isEmpty() && messageText.isBlank()) {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ChatBubbleOutline,
                                contentDescription = "Нет сообщений",
                                modifier = Modifier.size(96.dp),
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                            )
                        }
                    }
                } else {
                items(messages) { message ->
                    ChatMessage(message = message)
                    }
                }
                if (isTyping) {
                    item {
                        TypingIndicator()
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
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
                                        viewModel.sendMessage(messageText)
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
                                                viewModel.sendMessage(messageText)
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
                                            Icon(Icons.Outlined.Send, contentDescription = "Отправить")
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
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
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