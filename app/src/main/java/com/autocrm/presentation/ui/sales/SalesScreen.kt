package com.autocrm.presentation.ui.sales

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.autocrm.domain.model.CarWithDetails
import com.autocrm.presentation.ui.*
import com.autocrm.presentation.ui.theme.*
import com.autocrm.presentation.viewmodel.CarsViewModel

@Composable
fun SalesScreen(vm: CarsViewModel, navController: NavController) {
    val state by vm.carsUiState.collectAsStateWithLifecycle()
    val sold = state.cars.filter { it.car.status == com.autocrm.domain.model.CarStatus.SOLD }

    val totalProfit  = sold.sumOf { it.netProfit ?: 0.0 }
    val totalRevenue = sold.sumOf { it.car.salePrice ?: 0.0 }
    val avgDays      = if (sold.isNotEmpty()) sold.map { it.daysOnSale }.average() else 0.0
    val avgRoi       = sold.mapNotNull { it.roi }.let { if (it.isNotEmpty()) it.average() else null }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(NeonGreenDim.copy(0.4f), Background)))
                .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Text("История продаж", style = MaterialTheme.typography.headlineMedium)
            Text("${sold.size} завершённых сделок", style = MaterialTheme.typography.bodySmall, color = TextTertiary)

            Spacer(Modifier.height(16.dp))

            // Summary row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MiniStat("Прибыль", "+${totalProfit.toMoneyShort()}", Profit, Modifier.weight(1f))
                MiniStat("Выручка", "${totalRevenue.toMoneyShort()}", NeonBlue, Modifier.weight(1f))
                MiniStat("Ср. дней", "${"%.0f".format(avgDays)}", TextSecondary, Modifier.weight(1f))
                avgRoi?.let {
                    MiniStat("ROI", "${"%.0f".format(it)}%", if (it >= 0) Profit else Loss, Modifier.weight(1f))
                }
            }
        }

        if (sold.isEmpty()) {
            EmptyState(
                icon     = Icons.Default.Sell,
                title    = "Продаж ещё нет",
                subtitle = "Отметьте автомобиль как проданный"
            )
        } else {
            LazyColumn(
                contentPadding      = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sold, key = { it.car.uuid }) { car ->
                    SaleCard(car = car, onClick = { navController.navigate("car_detail/${car.car.uuid}") })
                }
                item { Spacer(Modifier.height(60.dp)) }
            }
        }
    }
}

@Composable
fun MiniStat(label: String, value: String, valueColor: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape    = RoundedCornerShape(12.dp),
        color    = Surface2,
        border   = BorderStroke(0.5.dp, Border)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(label, style = MaterialTheme.typography.labelSmall, color = TextTertiary)
            Text(value, style = MaterialTheme.typography.labelLarge, color = valueColor, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SaleCard(car: CarWithDetails, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape  = RoundedCornerShape(16.dp),
        color  = Surface2,
        border = BorderStroke(0.5.dp, Border)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            // Photo
            Box(
                modifier = Modifier
                    .width(110.dp)
                    .fillMaxHeight()
                    .background(Surface3)
            ) {
                if (car.primaryPhoto != null) {
                    AsyncImage(
                        model              = car.primaryPhoto!!.filePath,
                        contentDescription = null,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier.fillMaxSize()
                    )
                } else {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.DirectionsCar, null, tint = TextTertiary, modifier = Modifier.size(32.dp))
                    }
                }
                // Sold badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(Profit.copy(0.9f))
                        .padding(vertical = 3.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("ПРОДАНА", style = MaterialTheme.typography.labelSmall, color = Color(0xFF001A0F), fontWeight = FontWeight.Bold)
                }
            }

            // Info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(14.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        car.displayName,
                        style    = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(countryFlag(car.car.countryOfOrigin), style = MaterialTheme.typography.bodySmall)
                        Text("${car.daysOnSale} дн. в продаже", style = MaterialTheme.typography.labelSmall, color = TextTertiary)
                    }
                }

                Spacer(Modifier.height(10.dp))

                // Dates
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    DateBadge("Куплена", car.car.purchaseDate.toDateString())
                    DateBadge("Продана", car.car.saleDate?.toDateString() ?: "—")
                }

                Spacer(Modifier.height(10.dp))
                HorizontalDivider(color = Border, thickness = 0.5.dp)
                Spacer(Modifier.height(8.dp))

                // Financials
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Выручка", style = MaterialTheme.typography.labelSmall, color = TextTertiary)
                        Text(
                            car.car.salePrice?.toMoney(car.car.currency) ?: "—",
                            style = MaterialTheme.typography.labelMedium, color = TextPrimary
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Прибыль", style = MaterialTheme.typography.labelSmall, color = TextTertiary)
                        val profit = car.netProfit ?: 0.0
                        Text(
                            profit.toMoney(car.car.currency),
                            style      = MaterialTheme.typography.labelLarge,
                            color      = if (profit >= 0) Profit else Loss,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    car.roi?.let { roi ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = (if (roi >= 0) Profit else Loss).copy(0.12f)
                        ) {
                            Text(
                                "ROI ${"%.0f".format(roi)}%",
                                style    = MaterialTheme.typography.labelSmall,
                                color    = if (roi >= 0) Profit else Loss,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DateBadge(label: String, date: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall, color = TextTertiary)
        Text(date, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
    }
}
