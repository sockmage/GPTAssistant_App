package com.example.gptassistant.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle as ComposeFontStyle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.ui.graphics.Color
import com.example.gptassistant.data.model.Message
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.ui.text.style.TextOverflow
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.clickable
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatMessage(message: Message) {
    val clipboardManager = LocalClipboardManager.current
    var showCopied by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val parsed = parseMarkdown(message.content)
    val time = remember(message) {
        val date = Date(message.timestamp)
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
    }
    val configuration = LocalConfiguration.current
    val maxBubbleWidth = (configuration.screenWidthDp * 0.85f).dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 2.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!message.isUser) {
            // Кнопка копирования для ассистента
            IconButton(
                onClick = {
                    clipboardManager.setText(AnnotatedString(message.content))
                    showCopied = true
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Скопировано!")
                    }
                },
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ContentCopy,
                    contentDescription = "Копировать",
                    tint = MaterialTheme.colorScheme.outline.copy(alpha = if (showCopied) 1f else 0.6f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Card(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(horizontal = 2.dp)
                .widthIn(max = maxBubbleWidth)
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                    onClick = {
                        clipboardManager.setText(AnnotatedString(message.content))
                        showCopied = true
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Скопировано!")
                        }
                    },
                    onLongClick = {
                        clipboardManager.setText(AnnotatedString(message.content))
                        showCopied = true
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Скопировано!")
                        }
                    }
                ),
            colors = CardDefaults.cardColors(
                containerColor = if (message.isUser)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .wrapContentWidth()
            ) {
                parsed.forEach { part ->
                    when (part) {
                        is MarkdownPart.Code -> Text(
                            text = part.text,
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            modifier = Modifier
                                .padding(vertical = 2.dp, horizontal = 2.dp)
                                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
                                .padding(4.dp)
                        )
                        is MarkdownPart.Bold -> Text(
                            text = part.text,
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            color = if (message.isUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.wrapContentWidth()
                        )
                        is MarkdownPart.Italic -> Text(
                            text = part.text,
                            fontStyle = ComposeFontStyle.Italic,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            color = if (message.isUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.wrapContentWidth()
                        )
                        is MarkdownPart.Text -> Text(
                            text = part.text,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            maxLines = 20,
                            overflow = TextOverflow.Ellipsis,
                            color = if (message.isUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.wrapContentWidth()
                        )
                    }
                }
                // Отображение вложения, если оно есть
                if (message.attachmentUrl != null && message.attachmentType != null) {
                    Spacer(Modifier.height(8.dp))
                    when (message.attachmentType) {
                        "image" -> {
                            AsyncImage(
                                model = message.attachmentUrl,
                                contentDescription = "Вложенное изображение",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 240.dp)
                                    .clip(MaterialTheme.shapes.extraLarge)
                            )
                        }
                        "file" -> {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.ContentCopy, // Можно заменить на иконку файла
                                    contentDescription = "Вложение файл",
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = message.attachmentUrl.substringAfterLast('/'),
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.clickable {
                                        // TODO: Реализовать открытие/скачивание файла
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(2.dp))
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = time,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.7f),
                        modifier = Modifier.padding(end = 2.dp)
                    )
                }
            }
        }
        if (message.isUser) {
            // Кнопка копирования для пользователя
            IconButton(
                onClick = {
                    clipboardManager.setText(AnnotatedString(message.content))
                    showCopied = true
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Скопировано!")
                    }
                },
                modifier = Modifier
                    .padding(start = 4.dp)
                    .size(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ContentCopy,
                    contentDescription = "Копировать",
                    tint = MaterialTheme.colorScheme.outline.copy(alpha = if (showCopied) 1f else 0.6f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

sealed class MarkdownPart {
    data class Text(val text: String) : MarkdownPart()
    data class Bold(val text: String) : MarkdownPart()
    data class Italic(val text: String) : MarkdownPart()
    data class Code(val text: String) : MarkdownPart()
}

fun parseMarkdown(text: String): List<MarkdownPart> {
    val result = mutableListOf<MarkdownPart>()
    var i = 0
    while (i < text.length) {
        when {
            text.startsWith("```", i) -> {
                val end = text.indexOf("```", i + 3)
                if (end != -1) {
                    result.add(MarkdownPart.Code(text.substring(i + 3, end)))
                    i = end + 3
                } else {
                    result.add(MarkdownPart.Text(text.substring(i)))
                    break
                }
            }
            text.startsWith("**", i) -> {
                val end = text.indexOf("**", i + 2)
                if (end != -1) {
                    result.add(MarkdownPart.Bold(text.substring(i + 2, end)))
                    i = end + 2
                } else {
                    result.add(MarkdownPart.Text(text.substring(i)))
                    break
                }
            }
            text.startsWith("*", i) -> {
                val end = text.indexOf("*", i + 1)
                if (end != -1) {
                    result.add(MarkdownPart.Italic(text.substring(i + 1, end)))
                    i = end + 1
                } else {
                    result.add(MarkdownPart.Text(text.substring(i)))
                    break
                }
            }
            else -> {
                val next = listOf(
                    text.indexOf("```", i).takeIf { it != -1 },
                    text.indexOf("**", i).takeIf { it != -1 },
                    text.indexOf("*", i).takeIf { it != -1 }
                ).filterNotNull().minOrNull() ?: text.length
                result.add(MarkdownPart.Text(text.substring(i, next)))
                i = next
            }
        }
    }
    return result
} 