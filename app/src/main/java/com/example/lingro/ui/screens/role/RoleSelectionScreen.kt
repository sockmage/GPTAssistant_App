package com.example.lingro.ui.screens.role

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SupervisorAccount
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.animateColorAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleSelectionScreen(
    onRoleSelected: (String) -> Unit,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    var selectedRole by remember { mutableStateOf<String?>(null) }
    var showInfoDialog by remember { mutableStateOf(false) }

    val studentElevation = 1.dp
    val teacherElevation = 1.dp
    val studentContainerColor by animateColorAsState(
        targetValue = if (selectedRole == "student") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    )
    val teacherContainerColor by animateColorAsState(
        targetValue = if (selectedRole == "teacher") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .widthIn(max = 600.dp)
                .padding(horizontal = 24.dp)
                .padding(paddingValues)
                .padding(top = 64.dp),
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
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true),
                        onClick = { selectedRole = "student" }
                    ),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = studentElevation),
                colors = CardDefaults.elevatedCardColors(containerColor = studentContainerColor),
                shape = RoundedCornerShape(16.dp)
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
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true),
                        onClick = { selectedRole = "teacher" }
                    ),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = teacherElevation),
                colors = CardDefaults.elevatedCardColors(containerColor = teacherContainerColor),
                shape = RoundedCornerShape(16.dp)
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
            FilledTonalButton(
                onClick = { selectedRole?.let { onRoleSelected(it) } },
                enabled = selectedRole != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Продолжить", style = MaterialTheme.typography.titleMedium)
            }
        }
        if (showInfoDialog) {
            AlertDialog(
                onDismissRequest = { showInfoDialog = false },
                confirmButton = {
                    TextButton(onClick = { showInfoDialog = false }) {
                        Text("OK")
                    }
                },
                title = { Text("Описание ролей") },
                text = {
                    Column {
                        Text("\uD83D\uDC68\u200D\uD83C\uDF93 Ученик: Вы можете задавать вопросы, получать объяснения и учиться новым темам. Всё, что вы напишете, будет восприниматься как запрос от ученика.", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(bottom = 12.dp))
                        Text("\uD83D\uDC68\u200D\uD83C\uDFEB Учитель: Вы можете объяснять, помогать, отвечать на вопросы и вести диалог как преподаватель. Всё, что вы напишете, будет восприниматься как ответ учителя.", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            )
        }
        FloatingActionButton(
            onClick = { showInfoDialog = true },
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 48.dp)
        ) {
            Icon(Icons.Outlined.Info, contentDescription = "О ролях")
        }
    }
} 