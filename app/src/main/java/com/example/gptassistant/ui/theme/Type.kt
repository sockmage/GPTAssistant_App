package com.example.lingro.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.example.gptassistant.R

val Rubik = FontFamily(
    Font(resId = R.font.rubik_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    Font(resId = R.font.rubik_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(resId = R.font.rubik_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(resId = R.font.rubik_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(resId = R.font.rubik_semibold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
    Font(resId = R.font.rubik_semibolditalic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
    Font(resId = R.font.rubik_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
    Font(resId = R.font.rubik_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(resId = R.font.rubik_extrabold, weight = FontWeight.ExtraBold, style = FontStyle.Normal),
    Font(resId = R.font.rubik_extrabolditalic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
    Font(resId = R.font.rubik_black, weight = FontWeight.Black, style = FontStyle.Normal),
    Font(resId = R.font.rubik_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(resId = R.font.rubik_light, weight = FontWeight.Light, style = FontStyle.Normal),
    Font(resId = R.font.rubik_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = -0.25.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Light,
        fontSize = 11.sp,
        lineHeight = 16.sp
    )
) 