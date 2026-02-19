package com.publicservices.pakistan.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.publicservices.pakistan.R

// Define Jameel Noori Nastaleeq Font Family
val JameelNooriNastaleeq = FontFamily(
    Font(R.font.jameel_noori_nastaleeq)
)

// Onest font for English UI. Using system SansSerif; to use actual Onest add
// Onest-Regular.ttf (and weights) to res/font and set OnestFontFamily = FontFamily(Font(R.font.onest_regular, FontWeight.Normal), ...)
private val OnestFontFamily = FontFamily.SansSerif

// English Typography using Onest-style family
val EnglishTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    displayMedium = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    displaySmall = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    titleSmall = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp
    )
)

// Urdu Typography (Nastaleeq) - Adjusted line heights for complex script
val UrduTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = JameelNooriNastaleeq,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 48.sp // Increased line height
    ),
    displayMedium = TextStyle(
        fontFamily = JameelNooriNastaleeq,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 44.sp
    ),
    displaySmall = TextStyle(
        fontFamily = JameelNooriNastaleeq,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 40.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = JameelNooriNastaleeq,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 36.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = JameelNooriNastaleeq,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 34.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = JameelNooriNastaleeq,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = JameelNooriNastaleeq,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp, // Slightly larger for readability
        lineHeight = 34.sp
    ),
    titleMedium = TextStyle(
        fontFamily = JameelNooriNastaleeq,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 32.sp
    ),
    titleSmall = TextStyle(
        fontFamily = JameelNooriNastaleeq,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 28.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = JameelNooriNastaleeq,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp, // Larger body for accessibility
        lineHeight = 32.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = JameelNooriNastaleeq,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 30.sp
    ),
    bodySmall = TextStyle(
        fontFamily = JameelNooriNastaleeq,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 26.sp
    ),
    labelLarge = TextStyle(
        fontFamily = JameelNooriNastaleeq,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 28.sp
    ),
    labelMedium = TextStyle(
        fontFamily = JameelNooriNastaleeq,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 26.sp
    ),
    labelSmall = TextStyle(
        fontFamily = JameelNooriNastaleeq,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 22.sp
    )
)

fun getTypography(language: String): Typography {
    return if (language == "ur") UrduTypography else EnglishTypography
}

// Default export for backward compatibility
val Typography = EnglishTypography
