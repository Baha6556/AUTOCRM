package com.autocrm.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ── Premium Dark Palette ─────────────────────────────────────────
val Background      = Color(0xFF0A0A0F)
val Surface1        = Color(0xFF111118)
val Surface2        = Color(0xFF1A1A24)
val Surface3        = Color(0xFF22222F)
val SurfaceGlass    = Color(0xFF1E1E2A)

val NeonBlue        = Color(0xFF4A9EFF)
val NeonBlueLight   = Color(0xFF82BEFF)
val NeonBlueDim     = Color(0xFF1A3A66)
val NeonGreen       = Color(0xFF00E5A0)
val NeonGreenLight  = Color(0xFF66F0C0)
val NeonGreenDim    = Color(0xFF003D2B)

val Profit          = Color(0xFF00E5A0)
val Loss            = Color(0xFFFF4D6A)
val Warning         = Color(0xFFFFB347)

val TextPrimary     = Color(0xFFF0F0F8)
val TextSecondary   = Color(0xFF9090A8)
val TextTertiary    = Color(0xFF606078)

val Border          = Color(0xFF2A2A3A)
val BorderHighlight = Color(0xFF3A3A55)

// Status colors
val StatusInTransit  = Color(0xFF4A9EFF)
val StatusForSale    = Color(0xFF00E5A0)
val StatusSold       = Color(0xFF9090A8)
val StatusRepair     = Color(0xFFFFB347)
val StatusReserved   = Color(0xFFAA80FF)

// ── Color Scheme ──────────────────────────────────────────────────
private val DarkColorScheme = darkColorScheme(
    primary           = NeonBlue,
    onPrimary         = Color(0xFF0A0A1A),
    primaryContainer  = NeonBlueDim,
    onPrimaryContainer = NeonBlueLight,
    secondary         = NeonGreen,
    onSecondary       = Color(0xFF001A0F),
    secondaryContainer = NeonGreenDim,
    onSecondaryContainer = NeonGreenLight,
    tertiary          = Color(0xFFAA80FF),
    background        = Background,
    onBackground      = TextPrimary,
    surface           = Surface1,
    onSurface         = TextPrimary,
    surfaceVariant    = Surface2,
    onSurfaceVariant  = TextSecondary,
    outline           = Border,
    outlineVariant    = BorderHighlight,
    error             = Loss,
    onError           = Color.White,
)

// ── Typography ────────────────────────────────────────────────────
val AutoCrmTypography = Typography(
    displayLarge  = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold,   color = TextPrimary,   letterSpacing = (-0.5).sp),
    displayMedium = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold,   color = TextPrimary),
    displaySmall  = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary),
    headlineLarge = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary),
    headlineMedium= TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary),
    headlineSmall = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium,  color = TextPrimary),
    titleLarge    = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary),
    titleMedium   = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium,  color = TextPrimary),
    titleSmall    = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium,  color = TextSecondary),
    bodyLarge     = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal,  color = TextPrimary),
    bodyMedium    = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal,  color = TextSecondary),
    bodySmall     = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal,  color = TextTertiary),
    labelLarge    = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium,  color = TextPrimary),
    labelMedium   = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium,  color = TextSecondary),
    labelSmall    = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Medium,  color = TextTertiary, letterSpacing = 0.5.sp),
)

@Composable
fun AutoCrmTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography  = AutoCrmTypography,
        content     = content
    )
}
