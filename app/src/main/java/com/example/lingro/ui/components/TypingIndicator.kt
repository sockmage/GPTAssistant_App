package com.example.lingro.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun TypingIndicatorAnimated() {
    val dotCount = 3
    val delayBetween = 120

    val infiniteTransition = rememberInfiniteTransition()
    val scales = List(dotCount) { index ->
        infiniteTransition.animateFloat(
            initialValue = 0.7f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = dotCount * delayBetween
                    1.2f at delayBetween * index
                    0.7f at delayBetween * (index + 1)
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Row {
        for (i in 0 until dotCount) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .graphicsLayer {
                        scaleY = scales[i].value
                        scaleX = scales[i].value
                    }
                    .padding(horizontal = 3.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        shape = CircleShape
                    )
            )
        }
    }
} 