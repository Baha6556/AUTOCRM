package com.autocrm.presentation.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.autocrm.domain.model.*
import com.autocrm.presentation.ui.theme.*
import java.text.NumberFormat
import java.util.*

// ── Formatters ────────────────────────────────────────────────────
fun Double.toMoney(currency: String = "TJS"): String {
    val fmt = NumberFormat.getNumberInstance(Locale("ru"))
    fmt.minimumFractionDigits = 0
    fmt.maximumFractionDigits = 2
    return "${fmt.format(this)} TJS"
}

fun Double.toMoneyShort(): String = when {
    this >= 1_000_000 -> "${"%.1f".format(this / 1_000_000)}M TJS"
    this >= 1_000     -> "${"%.1f".format(this / 1_000)}K TJS"
    else              -> "${"%.0f".format(this)} TJS"
}

fun Long.toDateString(): String {
    val fmt = java.text.SimpleDateFormat("dd MMM yyyy", Locale("ru"))
    return fmt.format(Date(this))
}

// ── Status chip ───────────────────────────────────────────────────
@Composable
fun StatusChip(status: CarStatus, modifier: Modifier = Modifier) {
    val color = Color(status.colorHex)
    Surface(
        modifier  = modifier,
        shape     = RoundedCornerShape(20.dp),
        color     = color.copy(alpha = 0.15f),
        border    = BorderStroke(0.5.dp, color.copy(alpha = 0.4f))
    ) {
        Text(
            text  = status.displayName,
            color = color,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

// ── Stat card ─────────────────────────────────────────────────────
@Composable
fun StatCard(
    title: String,
    value: String,
    subtitle: String?    = null,
    icon: ImageVector    = Icons.Default.TrendingUp,
    valueColor: Color    = TextPrimary,
    modifier: Modifier   = Modifier
) {
    Surface(
        modifier      = modifier,
        shape         = RoundedCornerShape(16.dp),
        color         = Surface2,
        border        = BorderStroke(0.5.dp, Border)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(title, style = MaterialTheme.typography.bodySmall, color = TextTertiary)
                Icon(icon, contentDescription = null, tint = NeonBlue.copy(0.6f), modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.headlineMedium, color = valueColor, fontWeight = FontWeight.Bold)
            if (subtitle != null) {
                Text(subtitle, style = MaterialTheme.typography.labelSmall, color = TextTertiary)
            }
        }
    }
}

// ── Car card ─────────────────────────────────────────────────────
@Composable
fun CarCard(
    car: CarWithDetails,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier  = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape     = RoundedCornerShape(16.dp),
        color     = Surface2,
        border    = BorderStroke(0.5.dp, Border)
    ) {
        Column {
            // Photo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Surface3)
            ) {
                if (car.primaryPhoto != null) {
                    AsyncImage(
                        model             = car.primaryPhoto!!.filePath,
                        contentDescription = car.displayName,
                        contentScale      = ContentScale.Crop,
                        modifier          = Modifier.fillMaxSize()
                    )
                } else {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.DirectionsCar,
                            contentDescription = null,
                            tint   = TextTertiary,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Background.copy(0.7f)),
                                startY = 80f
                            )
                        )
                )
                // Status
                StatusChip(car.car.status, modifier = Modifier.align(Alignment.TopEnd).padding(10.dp))
                // Country flag
                Box(modifier = Modifier.align(Alignment.TopStart).padding(10.dp)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Surface1.copy(0.9f)
                    ) {
                        Text(
                            text = countryFlag(car.car.countryOfOrigin),
                            fontSize = 18.sp,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                }
            }

            // Info
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text     = car.displayName,
                    style    = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    car.car.mileage?.let {
                        InfoChip(Icons.Default.Speed, "${NumberFormat.getNumberInstance(Locale("ru")).format(it)} км")
                    }
                    car.car.vin?.let {
                        InfoChip(Icons.Default.Tag, it.takeLast(6))
                    }
                }
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(color = Border, thickness = 0.5.dp)
                Spacer(Modifier.height(10.dp))

                // Financials
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FinanceItem("Покупка", car.car.purchasePrice.toMoney(car.car.currency))
                    FinanceItem("Расходы", car.totalExpenses.toMoney(car.car.currency))
                    val profit = car.netProfit ?: car.estimatedProfit
                    if (profit != null) {
                        FinanceItem(
                            if (car.car.status == CarStatus.SOLD) "Прибыль" else "Прогноз",
                            profit.toMoney(car.car.currency),
                            if (profit >= 0) Profit else Loss
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoChip(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Icon(icon, contentDescription = null, tint = TextTertiary, modifier = Modifier.size(12.dp))
        Text(text, style = MaterialTheme.typography.labelSmall, color = TextTertiary)
    }
}

@Composable
fun FinanceItem(label: String, value: String, valueColor: Color = TextPrimary) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall, color = TextTertiary)
        Text(value, style = MaterialTheme.typography.labelMedium, color = valueColor, fontWeight = FontWeight.SemiBold)
    }
}

fun countryFlag(code: String): String = when(code.uppercase()) {
    "CN" -> "🇨🇳"
    "KR" -> "🇰🇷"
    "JP" -> "🇯🇵"
    "DE" -> "🇩🇪"
    "US" -> "🇺🇸"
    "AE" -> "🇦🇪"
    else -> "🌍"
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape    = RoundedCornerShape(20.dp),
        color    = SurfaceGlass,
        border   = BorderStroke(0.5.dp, BorderHighlight)
    ) {
        Column(content = content)
    }
}

@Composable
fun SectionHeader(title: String, action: (@Composable () -> Unit)? = null) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge)
        action?.invoke()
    }
}

@Composable
fun EmptyState(icon: ImageVector, title: String, subtitle: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = null, tint = TextTertiary, modifier = Modifier.size(64.dp))
        Spacer(Modifier.height(16.dp))
        Text(title, style = MaterialTheme.typography.titleMedium, color = TextSecondary)
        Spacer(Modifier.height(4.dp))
        Text(subtitle, style = MaterialTheme.typography.bodySmall, color = TextTertiary)
    }
}
