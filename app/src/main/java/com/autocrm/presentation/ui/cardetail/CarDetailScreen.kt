package com.autocrm.presentation.ui.cardetail

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.autocrm.domain.model.*
import com.autocrm.presentation.ui.*
import com.autocrm.presentation.ui.theme.*
import com.autocrm.presentation.viewmodel.CarsViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetailScreen(carUuid: String, vm: CarsViewModel, navController: NavController) {
    LaunchedEffect(carUuid) { vm.loadCar(carUuid) }
    val car by vm.selectedCar.collectAsStateWithLifecycle()

    var showSellDialog by remember { mutableStateOf(false) }
    var showAddExpense  by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (car == null) {
        Box(Modifier.fillMaxSize().background(Background), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = NeonBlue)
        }
        return
    }

    val c = car!!

    Box(modifier = Modifier.fillMaxSize().background(Background)) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {

            // Photo gallery
            Box(modifier = Modifier.fillMaxWidth().height(260.dp)) {
                if (c.photos.isNotEmpty()) {
                    AsyncImage(
                        model              = c.primaryPhoto?.filePath,
                        contentDescription = c.displayName,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        Modifier.fillMaxSize().background(Surface3),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.DirectionsCar, null, tint = TextTertiary, modifier = Modifier.size(72.dp))
                    }
                }
                // Gradient
                Box(
                    Modifier.fillMaxSize().background(
                        Brush.verticalGradient(listOf(Color.Transparent, Background), startY = 120f)
                    )
                )
                // Back button
                IconButton(
                    onClick  = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.TopStart).padding(8.dp)
                ) {
                    Surface(shape = RoundedCornerShape(12.dp), color = Surface1.copy(0.85f)) {
                        Icon(Icons.Default.ArrowBack, null, tint = TextPrimary, modifier = Modifier.padding(8.dp))
                    }
                }
                // Edit / Delete
                Row(modifier = Modifier.align(Alignment.TopEnd).padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(shape = RoundedCornerShape(12.dp), color = Surface1.copy(0.85f)) {
                        IconButton(onClick = { navController.navigate("edit_car/${c.car.uuid}") }) {
                            Icon(Icons.Default.Edit, null, tint = TextPrimary)
                        }
                    }
                    Surface(shape = RoundedCornerShape(12.dp), color = Surface1.copy(0.85f)) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Default.Delete, null, tint = Loss)
                        }
                    }
                }
                // Status chip bottom
                StatusChip(c.car.status, modifier = Modifier.align(Alignment.BottomStart).padding(16.dp))
            }

            // Photo strip
            if (c.photos.size > 1) {
                LazyRow(
                    contentPadding        = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(c.photos) { photo ->
                        AsyncImage(
                            model              = photo.filePath,
                            contentDescription = null,
                            contentScale       = ContentScale.Crop,
                            modifier           = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    if (photo.isPrimary) BorderStroke(2.dp, NeonBlue) else BorderStroke(0.5.dp, Border),
                                    RoundedCornerShape(10.dp)
                                )
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(20.dp)) {
                // Title
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.Top
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(c.displayName, style = MaterialTheme.typography.headlineLarge)
                        Text(
                            countryFlag(c.car.countryOfOrigin) + " " + countryName(c.car.countryOfOrigin),
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                // Financials summary
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Финансы", style = MaterialTheme.typography.titleMedium, color = TextSecondary)
                        Spacer(Modifier.height(14.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            FinanceBlock("Покупка", c.car.purchasePrice.toMoney(c.car.currency), TextPrimary)
                            FinanceBlock("Расходы", c.totalExpenses.toMoney(c.car.currency), Loss)
                            val profit = c.netProfit ?: c.estimatedProfit
                            FinanceBlock(
                                if (c.car.status == CarStatus.SOLD) "Прибыль" else "Прогноз",
                                profit?.toMoney(c.car.currency) ?: "—",
                                if ((profit ?: 0.0) >= 0) Profit else Loss
                            )
                        }
                        c.roi?.let {
                            Spacer(Modifier.height(12.dp))
                            HorizontalDivider(color = Border, thickness = 0.5.dp)
                            Spacer(Modifier.height(12.dp))
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("ROI", style = MaterialTheme.typography.labelMedium, color = TextTertiary)
                                Text(
                                    "${"%.1f".format(it)}%",
                                    style      = MaterialTheme.typography.titleMedium,
                                    color      = if (it >= 0) Profit else Loss,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.weight(1f))
                                Text("${c.daysOnSale} дн.", style = MaterialTheme.typography.labelMedium, color = TextTertiary)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Specs
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Характеристики", style = MaterialTheme.typography.titleMedium, color = TextSecondary)
                        Spacer(Modifier.height(12.dp))
                        val specs = buildList {
                            c.car.vin?.let        { add("VIN" to it) }
                            c.car.mileage?.let    { add("Пробег" to "${it} км") }
                            c.car.color?.let      { add("Цвет" to it) }
                            c.car.engineVolume?.let { add("Объём" to "${it} л") }
                            c.car.transmission?.let { add("КПП" to it) }
                            add("Дата покупки" to c.car.purchaseDate.toDateString())
                            c.car.saleDate?.let   { add("Дата продажи" to it.toDateString()) }
                        }
                        specs.forEachIndexed { i, (k, v) ->
                            Row(
                                Modifier.fillMaxWidth().padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(k, style = MaterialTheme.typography.bodySmall, color = TextTertiary)
                                Text(v, style = MaterialTheme.typography.bodySmall, color = TextPrimary)
                            }
                            if (i < specs.size - 1) HorizontalDivider(color = Border.copy(0.4f), thickness = 0.5.dp)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Expenses
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text("Расходы", style = MaterialTheme.typography.titleMedium)
                    TextButton(onClick = { showAddExpense = true }) {
                        Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp), tint = NeonBlue)
                        Text(" Добавить", color = NeonBlue)
                    }
                }
                Spacer(Modifier.height(8.dp))
                if (c.expenses.isEmpty()) {
                    Text("Расходов нет", style = MaterialTheme.typography.bodySmall, color = TextTertiary)
                } else {
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp)) {
                            c.expenses.forEachIndexed { i, exp ->
                                Row(
                                    Modifier.fillMaxWidth().padding(vertical = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Text(exp.category.icon, fontSize = 16.sp)
                                        Column {
                                            Text(exp.category.displayName, style = MaterialTheme.typography.labelMedium)
                                            exp.description?.let { Text(it, style = MaterialTheme.typography.labelSmall, color = TextTertiary) }
                                        }
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(exp.amount.toMoney(exp.currency), style = MaterialTheme.typography.labelMedium, color = Loss)
                                        IconButton(onClick = { vm.deleteExpense(exp.uuid) }, modifier = Modifier.size(32.dp)) {
                                            Icon(Icons.Default.Close, null, tint = TextTertiary, modifier = Modifier.size(14.dp))
                                        }
                                    }
                                }
                                if (i < c.expenses.size - 1) HorizontalDivider(color = Border.copy(0.4f), thickness = 0.5.dp)
                            }
                        }
                    }
                }

                // Notes
                c.car.notes?.let { notes ->
                    Spacer(Modifier.height(16.dp))
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Заметки", style = MaterialTheme.typography.titleSmall, color = TextTertiary)
                            Spacer(Modifier.height(6.dp))
                            Text(notes, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Sell button
                if (c.car.status != CarStatus.SOLD) {
                    Button(
                        onClick  = { showSellDialog = true },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        colors   = ButtonDefaults.buttonColors(
                            containerColor = Profit,
                            contentColor   = Color(0xFF001A0F)
                        ),
                        shape    = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.Sell, null)
                        Spacer(Modifier.width(8.dp))
                        Text("Отметить как проданную", fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(Modifier.height(32.dp))
            }
        }

        // Sell dialog
        if (showSellDialog) {
            SellDialog(
                car          = c,
                onDismiss    = { showSellDialog = false },
                onConfirm    = { price ->
                    vm.markAsSold(c.car.uuid, price)
                    showSellDialog = false
                }
            )
        }

        // Add expense dialog
        if (showAddExpense) {
            AddExpenseDialog(
                carUuid   = c.car.uuid,
                currency  = c.car.currency,
                onDismiss = { showAddExpense = false },
                onAdd     = { expense ->
                    vm.addExpense(expense)
                    showAddExpense = false
                }
            )
        }

        // Delete confirm
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest  = { showDeleteDialog = false },
                containerColor    = Surface2,
                title             = { Text("Удалить автомобиль?") },
                text              = { Text("${c.displayName} будет удалён", color = TextSecondary) },
                confirmButton     = {
                    TextButton(onClick = {
                        vm.deleteCar(c.car.uuid)
                        navController.popBackStack()
                    }) { Text("Удалить", color = Loss) }
                },
                dismissButton     = {
                    TextButton(onClick = { showDeleteDialog = false }) { Text("Отмена", color = TextSecondary) }
                }
            )
        }
    }
}

@Composable
fun FinanceBlock(label: String, value: String, valueColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = TextTertiary)
        Spacer(Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.titleSmall, color = valueColor, fontWeight = FontWeight.Bold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellDialog(car: CarWithDetails, onDismiss: () -> Unit, onConfirm: (Double) -> Unit) {
    var priceText by remember { mutableStateOf(car.car.estimatedSalePrice?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor   = Surface2,
        title            = { Text("Продажа автомобиля") },
        text             = {
            Column {
                Text("${car.displayName}", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value         = priceText,
                    onValueChange = { priceText = it },
                    label         = { Text("Цена продажи") },
                    prefix        = { Text("TJS ") },
                    singleLine    = true,
                    colors        = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = NeonBlue,
                        unfocusedBorderColor = Border,
                        focusedTextColor     = TextPrimary,
                        unfocusedTextColor   = TextPrimary,
                        focusedContainerColor   = Surface3,
                        unfocusedContainerColor = Surface3
                    )
                )
                val price = priceText.toDoubleOrNull() ?: 0.0
                val profit = price - car.car.purchasePrice - car.totalExpenses
                Spacer(Modifier.height(12.dp))
                Text(
                    "Прибыль: ${profit.toMoney(car.car.currency)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (profit >= 0) Profit else Loss
                )
            }
        },
        confirmButton = {
            Button(
                onClick  = { priceText.toDoubleOrNull()?.let { onConfirm(it) } },
                enabled  = priceText.toDoubleOrNull() != null,
                colors   = ButtonDefaults.buttonColors(containerColor = Profit, contentColor = Color(0xFF001A0F))
            ) { Text("Продать") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Отмена", color = TextSecondary) }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseDialog(carUuid: String, currency: String, onDismiss: () -> Unit, onAdd: (Expense) -> Unit) {
    var category    by remember { mutableStateOf(ExpenseCategory.CUSTOMS) }
    var amount      by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var expanded    by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor   = Surface2,
        title            = { Text("Добавить расход") },
        text             = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                    OutlinedTextField(
                        value         = "${category.icon} ${category.displayName}",
                        onValueChange = {},
                        readOnly      = true,
                        label         = { Text("Категория") },
                        trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier      = Modifier.menuAnchor().fillMaxWidth(),
                        colors        = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonBlue, unfocusedBorderColor = Border,
                            focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                            focusedContainerColor = Surface3, unfocusedContainerColor = Surface3
                        )
                    )
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, containerColor = Surface3) {
                        ExpenseCategory.values().forEach { cat ->
                            DropdownMenuItem(
                                text    = { Text("${cat.icon} ${cat.displayName}", color = TextPrimary) },
                                onClick = { category = cat; expanded = false }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value         = amount,
                    onValueChange = { amount = it },
                    label         = { Text("Сумма") },
                    prefix        = { Text("TJS ") },
                    singleLine    = true,
                    modifier      = Modifier.fillMaxWidth(),
                    colors        = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonBlue, unfocusedBorderColor = Border,
                        focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                        focusedContainerColor = Surface3, unfocusedContainerColor = Surface3
                    )
                )
                OutlinedTextField(
                    value         = description,
                    onValueChange = { description = it },
                    label         = { Text("Описание (необязательно)") },
                    singleLine    = true,
                    modifier      = Modifier.fillMaxWidth(),
                    colors        = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonBlue, unfocusedBorderColor = Border,
                        focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                        focusedContainerColor = Surface3, unfocusedContainerColor = Surface3
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick  = {
                    amount.toDoubleOrNull()?.let { amt ->
                        onAdd(Expense(
                            uuid        = UUID.randomUUID().toString(),
                            carUuid     = carUuid,
                            category    = category,
                            amount      = amt,
                            currency    = currency,
                            description = description.ifBlank { null }
                        ))
                    }
                },
                enabled  = amount.toDoubleOrNull() != null,
                colors   = ButtonDefaults.buttonColors(containerColor = NeonBlue, contentColor = Background)
            ) { Text("Добавить") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Отмена", color = TextSecondary) }
        }
    )
}

fun countryName(code: String) = when(code.uppercase()) {
    "CN" -> "Китай"
    "KR" -> "Корея"
    "JP" -> "Япония"
    "DE" -> "Германия"
    "US" -> "США"
    "AE" -> "ОАЭ"
    else -> code
}
