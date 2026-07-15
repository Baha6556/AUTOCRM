package com.autocrm.presentation.ui.dashboard

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController
import com.autocrm.presentation.Screen
import com.autocrm.presentation.ui.*
import com.autocrm.presentation.ui.theme.*
import com.autocrm.presentation.viewmodel.DashboardViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DashboardScreen(vm: DashboardViewModel, navController: NavController) {
    val state by vm.dashboardState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(NeonBlueDim.copy(0.4f), Background)
                    )
                )
                .padding(top = 56.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)
        ) {
            Column {
                Text(
                    "AutoCRM",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                val fmt = SimpleDateFormat("EEEE, d MMMM", Locale("ru"))
                Text(
                    fmt.format(Date()),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextTertiary
                )
            }
        }

        if (state.isLoading) {
            Box(Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = NeonBlue)
            }
        } else {
            val s = state.stats

            // Main profit card
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(listOf(NeonBlueDim, NeonGreenDim))
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Text("Общая прибыль", style = MaterialTheme.typography.bodySmall, color = NeonBlueLight)
                        Text(
                            "${s.totalProfit.toMoneyShort()}",
                            style = MaterialTheme.typography.displayMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                            Column {
                                Text("За месяц", style = MaterialTheme.typography.labelSmall, color = TextTertiary)
                                Text(
                                    "+${s.monthProfit.toMoneyShort()}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Profit,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Column {
                                Text("Ср. прибыль/авто", style = MaterialTheme.typography.labelSmall, color = TextTertiary)
                                Text(
                                    "${s.avgProfitPerCar.toMoneyShort()}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = NeonBlue,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Stats grid
            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title     = "В наличии",
                    value     = "${s.activeCount}",
                    subtitle  = "автомобилей",
                    icon      = Icons.Default.DirectionsCar,
                    modifier  = Modifier.weight(1f)
                )
                StatCard(
                    title     = "Продано",
                    value     = "${s.soldCount}",
                    subtitle  = "сделок",
                    icon      = Icons.Default.Sell,
                    valueColor = Profit,
                    modifier  = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title    = "Расходы всего",
                    value    = "${s.totalExpenses.toMoneyShort()}",
                    icon     = Icons.Default.Receipt,
                    valueColor = Loss,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title    = "Всего сделок",
                    value    = "${s.soldCount + s.activeCount}",
                    subtitle = "автомобилей",
                    icon     = Icons.Default.Assessment,
                    modifier = Modifier.weight(1f)
                )
            }

            // Monthly chart
            if (state.monthlyProfits.isNotEmpty()) {
                Spacer(Modifier.height(24.dp))
                SectionHeader("Прибыль по месяцам")
                MonthlyProfitChart(
                    months   = state.monthlyProfits,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(horizontal = 20.dp)
                )
            }

            // Top cars
            if (s.topProfitableCars.isNotEmpty()) {
                Spacer(Modifier.height(24.dp))
                SectionHeader("Топ по прибыли")
                LazyRow(
                    contentPadding       = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(s.topProfitableCars) { car ->
                        TopCarChip(car) {
                            navController.navigate("car_detail/${car.car.uuid}")
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun MonthlyProfitChart(
    months: List<com.autocrm.domain.model.MonthlyProfit>,
    modifier: Modifier = Modifier
) {
    val maxVal = months.maxOfOrNull { maxOf(it.profit, it.expenses) }?.takeIf { it > 0 } ?: 1.0
    Surface(
        modifier = modifier,
        shape    = RoundedCornerShape(16.dp),
        color    = Surface2,
        border   = BorderStroke(0.5.dp, Border)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            months.takeLast(7).forEach { m ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                ) {
                    val profitRatio = (m.profit / maxVal).toFloat().coerceIn(0f, 1f)
                    val expRatio    = (m.expenses / maxVal).toFloat().coerceIn(0f, 1f)

                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomCenter) {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            // Profit bar
                            Box(
                                modifier = Modifier
                                    .width(8.dp)
                                    .fillMaxHeight(profitRatio)
                                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                    .background(
                                        if (m.profit >= 0) Profit else Loss
                                    )
                            )
                            // Expense bar
                            Box(
                                modifier = Modifier
                                    .width(8.dp)
                                    .fillMaxHeight(expRatio)
                                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                    .background(NeonBlue.copy(0.5f))
                            )
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        m.month.take(3),
                        style = MaterialTheme.typography.labelSmall,
                        color = TextTertiary
                    )
                }
            }
        }
    }
}

@Composable
fun TopCarChip(car: com.autocrm.domain.model.CarWithDetails, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .width(200.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        shape  = RoundedCornerShape(14.dp),
        color  = Surface2,
        border = BorderStroke(0.5.dp, Border)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(NeonBlueDim),
                contentAlignment = Alignment.Center
            ) {
                Text(countryFlag(car.car.countryOfOrigin), style = MaterialTheme.typography.titleLarge)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "${car.car.make} ${car.car.model}",
                    style    = MaterialTheme.typography.labelMedium,
                    maxLines = 1
                )
                Text(
                    "+${car.netProfit?.toMoneyShort() ?: "–"}",
                    style      = MaterialTheme.typography.labelLarge,
                    color      = Profit,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
