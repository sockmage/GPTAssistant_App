package com.example.lingro.ui.screens.role
import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeOut
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable

@Composable
fun RoleScreen(
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + scaleIn(initialScale = 0.92f),
        exit = fadeOut() + scaleOut(targetScale = 0.92f)
    ) {
        // ... существующий UI ...
    }
} 