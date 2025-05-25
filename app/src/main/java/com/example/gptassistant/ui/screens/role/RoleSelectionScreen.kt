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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleSelectionScreen(
    onRoleSelected: (String) -> Unit
) {
    var selectedRole by remember { mutableStateOf<String?>(null) }

    val studentElevation = 1.dp
    val teacherElevation = 1.dp
    val studentBorderColor by animateColorAsState(
        targetValue = if (selectedRole == "student") MaterialTheme.colorScheme.primary else Color.Transparent
    )
    val teacherBorderColor by animateColorAsState(
        targetValue = if (selectedRole == "teacher") MaterialTheme.colorScheme.primary else Color.Transparent
    )
    val studentContainerColor by animateColorAsState(
        targetValue = if (selectedRole == "student") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    )
    val teacherContainerColor by animateColorAsState(
        targetValue = if (selectedRole == "teacher") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Выберите роль",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { selectedRole = "student" },
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
                    imageVector = Icons.Filled.Person,
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
                        text = "Я хочу учиться",
                        style = MaterialTheme.typography.bodyMedium,
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
                .clickable { selectedRole = "teacher" },
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
                    imageVector = Icons.Filled.SupervisorAccount,
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
                        text = "Я хочу преподавать",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp),
                        color = if (selectedRole == "teacher") MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        AnimatedVisibility(
            visible = selectedRole != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
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
    }
} 