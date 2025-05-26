package com.example.gptassistant.ui.screens.role

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SupervisorAccount
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.draw.scale
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.Crossfade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleSelectionScreen(
    onRoleSelected: (String) -> Unit
) {
    var selectedRole by remember { mutableStateOf<String?>(null) }
    var showRoleInfo by remember { mutableStateOf(false) }

    val studentElevation = 1.dp
    val teacherElevation = 1.dp
    val studentContainerColor by animateColorAsState(
        targetValue = if (selectedRole == "student") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    )
    val teacherContainerColor by animateColorAsState(
        targetValue = if (selectedRole == "teacher") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    )

    Crossfade(targetState = showRoleInfo && selectedRole != null, label = "roleCrossfade") { showInfo ->
        if (showInfo && selectedRole != null) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInHorizontally(initialOffsetX = { it }),
                exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it }),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = if (selectedRole == "student") "Роль 'Ученик':\nВы можете задавать вопросы, получать объяснения и учиться новым темам. Всё, что вы напишете, будет восприниматься как запрос от ученика." else "Роль 'Учитель':\nВы можете объяснять, помогать, отвечать на вопросы и вести диалог как преподаватель. Всё, что вы напишете, будет восприниматься как ответ учителя.",
                        style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val returnScale by animateFloatAsState(targetValue = 1f, label = "returnScale")
                        val nextScale by animateFloatAsState(targetValue = 1f, label = "nextScale")
                        FilledTonalButton(
                            onClick = { showRoleInfo = false },
                            modifier = Modifier.weight(1f).scale(returnScale)
                        ) {
                            Text("Вернуться", style = MaterialTheme.typography.titleMedium)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = { onRoleSelected(selectedRole!!) },
                            modifier = Modifier.weight(1f).scale(nextScale)
                        ) {
                            Text("Далее", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .widthIn(max = 600.dp)
                        .padding(horizontal = 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Выберите роль",
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Кто вы сегодня? Роль влияет только на стиль общения в чате.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    val studentScale by animateFloatAsState(targetValue = if (selectedRole == "student") 1.04f else 1f, label = "studentScale")
                    val teacherScale by animateFloatAsState(targetValue = if (selectedRole == "teacher") 1.04f else 1f, label = "teacherScale")
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .scale(studentScale)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true),
                                onClick = { selectedRole = "student"; showRoleInfo = true }
                            ),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = studentElevation),
                        colors = CardDefaults.elevatedCardColors(containerColor = studentContainerColor)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "Ученик",
                                tint = if (selectedRole == "student") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Ученик",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = if (selectedRole == "student") MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Учусь, задаю вопросы",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                                    modifier = Modifier.padding(top = 8.dp),
                                    color = if (selectedRole == "student") MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .scale(teacherScale)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true),
                                onClick = { selectedRole = "teacher"; showRoleInfo = true }
                            ),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = teacherElevation),
                        colors = CardDefaults.elevatedCardColors(containerColor = teacherContainerColor)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.SupervisorAccount,
                                contentDescription = "Учитель",
                                tint = if (selectedRole == "teacher") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Учитель",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = if (selectedRole == "teacher") MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Объясняю, помогаю",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                                    modifier = Modifier.padding(top = 8.dp),
                                    color = if (selectedRole == "teacher") MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    AnimatedVisibility(
                        visible = selectedRole != null,
                        enter = fadeIn() + slideInHorizontally(initialOffsetX = { -it }),
                        exit = fadeOut() + slideOutHorizontally(targetOffsetX = { -it }),
                    ) {
                        val continueScale by animateFloatAsState(targetValue = 1f, label = "continueScale")
                        FilledTonalButton(
                            onClick = { selectedRole?.let { onRoleSelected(it) } },
                            enabled = selectedRole != null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .scale(continueScale)
                        ) {
                            Text("Продолжить", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
        }
    }
} 