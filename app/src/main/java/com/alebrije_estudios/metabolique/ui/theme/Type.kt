package com.alebrije_estudios.metabolique.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = Lexend,
        fontWeight = FontWeight.Normal,
        //color = TextColor,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Lexend,
        fontWeight = FontWeight.Normal,
       //color = PrimaryColor,
        fontSize = 15.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Lexend,
        fontWeight = FontWeight.Normal,
        //color = DefaultColor,
        fontSize = 15.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Lexend,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        //color = TextColor,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Lexend,
        fontWeight = FontWeight.Normal,
       // color = LabelColor,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Lexend,
        fontWeight = FontWeight.Normal,
        //color = TextColor,
        fontSize = 15.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp
    )
)