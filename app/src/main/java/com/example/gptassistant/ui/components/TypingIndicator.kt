package com.example.gptassistant.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TypingIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "typing")
    val alpha1 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha1"
    )
    val alpha2 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, delayMillis = 200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha2"
    )
    val alpha3 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, delayMillis = 400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha3"
    )

    Card(
        modifier = Modifier
            .widthIn(max = 100.dp)
            .padding(horizontal = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(8.dp),
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = alpha1)
            ) {}
            Surface(
                modifier = Modifier.size(8.dp),
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = alpha2)
            ) {}
            Surface(
                modifier = Modifier.size(8.dp),
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = alpha3)
            ) {}
        }
    }
} 