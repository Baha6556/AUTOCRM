package com.autocrm.presentation.ui.analytics

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.autocrm.domain.model.*
import com.autocrm.presentation.ui.*
import com.autocrm.presentation.ui.theme.*
import com.autocrm.presentation.viewmodel.CarsViewModel
import com.autocrm.presentation.viewmodel.DashboardViewModel

@Composable
fun AnalyticsScreen(carsVm: CarsViewModel, dashboardVm: DashboardViewModel) {
    val dashboard by dashboardVm.dashboardState.collectAsStateWithLifecycle()
    val carsUi      by carsVm.carsUiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(Color(0xFF1A0A33).copy(0.6f), Background)))
                .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Text("Аналитика", style = MaterialTheme.typography.headlineMedium)
            Text("Детальная статистика бизнеса", style = MaterialTheme.typography.bodySmall, color = TextTertiary)
        }

        val stats = dashboard.stats
        val allCars = carsUi.cars

        if (dashboard.isLoading) {
            Box(Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = NeonBlue)
            }
            return@Column
        }

        // KPI cards
        Spacer(Modifier.height(4.dp))
        SectionHeader("Ключевые показатели")
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                KpiCard("Общая прибыль", "${stats.totalProfit.toMoneyShort()}", Profit,
                    "За всё время", Icons.Default.TrendingUp, Modifier.weight(1f))
                KpiCard("За месяц", "${stats.monthProfit.toMoneyShort()}", NeonBlue,
                    "Текущий месяц", Icons.Default.CalendarMonth, Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                KpiCard("Продано", "${stats.soldCount}", TextPrimary,
                    "автомобилей", Icons.Default.Sell, Modifier.weight(1f))
                KpiCard("В наличии", "${stats.activeCount}", Warning,
                    "сейчас", Icons.Default.DirectionsCar, Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                KpiCard("Ср. прибыль", "${stats.avgProfitPerCar.toMoneyShort()}", NeonGreen,
                    "на автомобиль", Icons.Default.Analytics, Modifier.weight(1f))
                KpiCard("Все расходы", "${stats.totalExpenses.toMoneyShort()}", Loss,
                    "суммарно", Icons.Default.Receipt, Modifier.weight(1f))
            }
        }

        // Monthly profit chart
        if (dashboard.monthlyProfits.isNotEmpty()) {
            Spacer(Modifier.height(24.dp))
            SectionHeader("Прибыль по месяцам")
            MonthlyTable(dashboard.monthlyProfits, modifier = Modifier.padding(horizontal = 20.dp))
        }

        // Country breakdown
        Spacer(Modifier.height(24.dp))
        SectionHeader("По странам импорта")
        CountryBreakdown(allCars, modifier = Modifier.padding(horizontal = 20.dp))

        // Status breakdown
        Spacer(Modifier.height(24.dp))
        SectionHeader("По статусам")
        StatusBreakdown(allCars, modifier = Modifier.padding(horizontal = 20.dp))

        // Expense categories
        val allExpenses = allCars.flatMap { it.expenses }
        if (allExpenses.isNotEmpty()) {
            Spacer(Modifier.height(24.dp))
            SectionHeader("Расходы по категориям")
            ExpenseCategories(allExpenses, modifier = Modifier.padding(horizontal = 20.dp))
        }

        Spacer(Modifier.height(40.dp))
    }
}

@Composable
fun KpiCard(
    title: String,
    value: String,
    valueColor: Color,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape    = RoundedCornerShape(16.dp),
        color    = Surface2,
        border   = BorderStroke(0.5.dp, Border)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, null, tint = valueColor.copy(0.6f), modifier = Modifier.size(18.dp))
                Text(title, style = MaterialTheme.typography.labelSmall, color = TextTertiary)
            }
            Spacer(Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.headlineSmall, color = valueColor, fontWeight = FontWeight.Bold)
            Text(subtitle, style = MaterialTheme.typography.labelSmall, color = TextTertiary)
        }
    }
}

@Composable
fun MonthlyTable(months: List<MonthlyProfit>, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(16.dp),
        color    = Surface2,
        border   = BorderStroke(0.5.dp, Border)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("Месяц", style = MaterialTheme.typography.labelSmall, color = TextTertiary, modifier = Modifier.weight(1.5f))
                Text("Прибыль", style = MaterialTheme.typography.labelSmall, color = TextTertiary, modifier = Modifier.weight(1f))
                Text("Расходы", style = MaterialTheme.typography.labelSmall, color = TextTertiary, modifier = Modifier.weight(1f))
                Text("Авто", style = MaterialTheme.typography.labelSmall, color = TextTertiary, modifier = Modifier.weight(0.5f))
            }
            HorizontalDivider(Modifier.padding(vertical = 8.dp), color = Border, thickness = 0.5.dp)
            months.reversed().forEach { m ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(m.month, style = MaterialTheme.typography.labelMedium, modifier = Modifier.weight(1.5f))
                    Text(
                        "+${m.profit.toMoneyShort()}",
                        style      = MaterialTheme.typography.labelMedium,
                        color      = if (m.profit >= 0) Profit else Loss,
                        fontWeight = FontWeight.SemiBold,
                        modifier   = Modifier.weight(1f)
                    )
                    Text(
                        "-${m.expenses.toMoneyShort()}",
                        style    = MaterialTheme.typography.labelMedium,
                        color    = Loss,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "${m.soldCount}",
                        style    = MaterialTheme.typography.labelMedium,
                        color    = NeonBlue,
                        modifier = Modifier.weight(0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun CountryBreakdown(cars: List<CarWithDetails>, modifier: Modifier = Modifier) {
    val byCountry = cars.groupBy { it.car.countryOfOrigin }
        .mapValues { (_, v) -> v.size }
        .entries.sortedByDescending { it.value }
    val total = cars.size.toFloat().takeIf { it > 0 } ?: 1f

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(16.dp),
        color    = Surface2,
        border   = BorderStroke(0.5.dp, Border)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            byCountry.forEach { (code, count) ->
                val pct = count / total
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(countryFlag(code), style = MaterialTheme.typography.titleMedium)
                    Column(modifier = Modifier.weight(1f)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(countryNameFull(code), style = MaterialTheme.typography.labelMedium)
                            Text("$count авто (${"%.0f".format(pct * 100)}%)", style = MaterialTheme.typography.labelSmall, color = TextTertiary)
                        }
                        Spacer(Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Border)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(pct)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Brush.horizontalGradient(listOf(NeonBlue, NeonGreen)))
                            )
                        }
                    }
                }
            }
            if (byCountry.isEmpty()) {
                Text("Нет данных", style = MaterialTheme.typography.bodySmall, color = TextTertiary)
            }
        }
    }
}

@Composable
fun StatusBreakdown(cars: List<CarWithDetails>, modifier: Modifier = Modifier) {
    val byStatus = CarStatus.values().map { status ->
        status to cars.count { it.car.status == status }
    }.filter { it.second > 0 }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(16.dp),
        color    = Surface2,
        border   = BorderStroke(0.5.dp, Border)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            byStatus.forEach { (status, count) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusChip(status)
                    Text(
                        "$count авто",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextSecondary
                    )
                }
            }
            if (byStatus.isEmpty()) {
                Text("Нет данных", style = MaterialTheme.typography.bodySmall, color = TextTertiary)
            }
        }
    }
}

@Composable
fun ExpenseCategories(expenses: List<Expense>, modifier: Modifier = Modifier) {
    val byCategory = expenses
        .groupBy { it.category }
        .mapValues { (_, v) -> v.sumOf { it.amount } }
        .entries.sortedByDescending { it.value }
    val total = byCategory.sumOf { it.value }.takeIf { it > 0 } ?: 1.0

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(16.dp),
        color    = Surface2,
        border   = BorderStroke(0.5.dp, Border)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            byCategory.forEach { (cat, amount) ->
                val pct = (amount / total).toFloat()
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(cat.icon, style = MaterialTheme.typography.titleMedium)
                    Column(modifier = Modifier.weight(1f)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(cat.displayName, style = MaterialTheme.typography.labelMedium)
                            Text("${amount.toMoneyShort()} (${"%.0f".format(pct * 100)}%)",
                                style = MaterialTheme.typography.labelSmall, color = Loss)
                        }
                        Spacer(Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Border)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(pct)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Loss.copy(0.7f))
                            )
                        }
                    }
                }
            }
        }
    }
}

fun countryNameFull(code: String) = when(code.uppercase()) {
    "CN" -> "Китай"
    "KR" -> "Корея"
    "JP" -> "Япония"
    "DE" -> "Германия"
    "US" -> "США"
    "AE" -> "ОАЭ"
    else -> code
}
