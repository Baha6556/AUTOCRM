package com.autocrm.presentation.ui.addcar

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.autocrm.domain.model.*
import com.autocrm.presentation.ui.StatusChip
import com.autocrm.presentation.ui.theme.*
import com.autocrm.presentation.viewmodel.CarsViewModel
import com.autocrm.presentation.ui.cardetail.AddExpenseDialog
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCarScreen(
    vm: CarsViewModel,
    navController: NavController,
    editCarUuid: String? = null
) {
    val isEdit = editCarUuid != null

    // Load existing car if editing
    if (isEdit) {
        LaunchedEffect(editCarUuid) { vm.loadCar(editCarUuid!!) }
    }
    val existingCar by vm.selectedCar.collectAsStateWithLifecycle()

    val newCarUuid = remember { UUID.randomUUID().toString() }
    val finalCarUuid = if (isEdit) existingCar?.car?.uuid ?: newCarUuid else newCarUuid

    // Form state
    var make           by remember { mutableStateOf("") }
    var model          by remember { mutableStateOf("") }
    var year           by remember { mutableStateOf("") }
    var vin            by remember { mutableStateOf("") }
    var mileage        by remember { mutableStateOf("") }
    var country        by remember { mutableStateOf("CN") }
    var color          by remember { mutableStateOf("") }
    var engine         by remember { mutableStateOf("") }
    var transmission   by remember { mutableStateOf("") }
    var purchasePrice  by remember { mutableStateOf("") }
    var estimatedPrice by remember { mutableStateOf("") }
    var notes          by remember { mutableStateOf("") }
    var status         by remember { mutableStateOf(CarStatus.IN_TRANSIT) }

    var statusExpanded  by remember { mutableStateOf(false) }
    var countryExpanded by remember { mutableStateOf(false) }

    // Photos state
    var localPhotos by remember { mutableStateOf(listOf<String>()) }
    val photoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        localPhotos = localPhotos + uris.map { it.toString() }
    }

    // Expenses state
    var localExpenses by remember { mutableStateOf(listOf<Expense>()) }
    var showAddExpense by remember { mutableStateOf(false) }

    // Prefill if editing
    LaunchedEffect(existingCar) {
        if (isEdit && existingCar != null) {
            val c = existingCar!!.car
            make           = c.make
            model          = c.model
            year           = c.year.toString()
            vin            = c.vin ?: ""
            mileage        = c.mileage?.toString() ?: ""
            country        = c.countryOfOrigin
            color          = c.color ?: ""
            engine         = c.engineVolume?.toString() ?: ""
            transmission   = c.transmission ?: ""
            purchasePrice  = c.purchasePrice.toString()
            estimatedPrice = c.estimatedSalePrice?.toString() ?: ""
            notes          = c.notes ?: ""
            status         = c.status
        }
    }

    val isValid = make.isNotBlank() && model.isNotBlank() &&
            year.toIntOrNull() != null && purchasePrice.toDoubleOrNull() != null

    val countries = listOf("CN" to "🇨🇳 Китай", "KR" to "🇰🇷 Корея", "JP" to "🇯🇵 Япония",
        "DE" to "🇩🇪 Германия", "US" to "🇺🇸 США", "AE" to "🇦🇪 ОАЭ", "OTHER" to "🌍 Другая")

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title         = { Text(if (isEdit) "Редактировать" else "Новый автомобиль") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = Background,
                    titleContentColor = TextPrimary
                )
            )
        },
        bottomBar = {
            Surface(color = Surface1, border = BorderStroke(0.5.dp, Border)) {
                Button(
                    onClick  = {
                        val car = Car(
                            uuid             = finalCarUuid,
                            serverId         = if (isEdit) existingCar!!.car.serverId else null,
                            make             = make.trim(),
                            model            = model.trim(),
                            year             = year.toIntOrNull() ?: 2024,
                            vin              = vin.ifBlank { null },
                            mileage          = mileage.toIntOrNull(),
                            countryOfOrigin  = country,
                            color            = color.ifBlank { null },
                            engineVolume     = engine.toDoubleOrNull(),
                            transmission     = transmission.ifBlank { null },
                            purchasePrice    = purchasePrice.toDoubleOrNull() ?: 0.0,
                            estimatedSalePrice = estimatedPrice.toDoubleOrNull(),
                            status           = status,
                            purchaseDate     = if (isEdit) existingCar!!.car.purchaseDate else System.currentTimeMillis(),
                            notes            = notes.ifBlank { null },
                            updatedAt        = System.currentTimeMillis(),
                            syncStatus       = EntitySyncStatus.PENDING
                        )
                        vm.saveCar(car)
                        
                        // Save local photos
                        localPhotos.forEach { uri ->
                            vm.addPhoto(finalCarUuid, uri, isPrimary = false)
                        }
                        
                        // Save local expenses
                        localExpenses.forEach { exp ->
                            vm.addExpense(exp.copy(carUuid = finalCarUuid))
                        }
                        
                        navController.popBackStack()
                    },
                    enabled  = isValid,
                    modifier = Modifier.fillMaxWidth().padding(16.dp).height(52.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor = NeonBlue,
                        contentColor   = Background,
                        disabledContainerColor = NeonBlueDim
                    ),
                    shape    = RoundedCornerShape(14.dp)
                ) {
                    Icon(if (isEdit) Icons.Default.Save else Icons.Default.Add, null)
                    Spacer(Modifier.width(8.dp))
                    Text(if (isEdit) "Сохранить" else "Добавить", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            // Section: Основное
            FormSection("Основная информация") {
                FormField("Марка *", make, { make = it }, "Toyota, BYD, Hyundai…")
                FormField("Модель *", model, { model = it }, "Camry, Han, Tucson…")
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    FormField("Год *", year, { year = it }, "2023", Modifier.weight(1f))
                    FormField("Пробег (км)", mileage, { mileage = it }, "50000", Modifier.weight(1f))
                }
                FormField("VIN", vin, { vin = it }, "WBADT43452G021")
                FormField("Цвет", color, { color = it }, "Белый перламутр")

                // Country selector
                ExposedDropdownMenuBox(expanded = countryExpanded, onExpandedChange = { countryExpanded = it }) {
                    OutlinedTextField(
                        value         = countries.find { it.first == country }?.second ?: country,
                        onValueChange = {},
                        readOnly      = true,
                        label         = { Text("Страна импорта") },
                        trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(countryExpanded) },
                        modifier      = Modifier.menuAnchor().fillMaxWidth(),
                        colors        = crmFieldColors()
                    )
                    ExposedDropdownMenu(expanded = countryExpanded, onDismissRequest = { countryExpanded = false }, containerColor = Surface3) {
                        countries.forEach { (code, label) ->
                            DropdownMenuItem(
                                text    = { Text(label, color = TextPrimary) },
                                onClick = { country = code; countryExpanded = false }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Section: Фотографии
            FormSection("Фотографии") {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Surface(
                            modifier = Modifier
                                .size(80.dp)
                                .clickable { photoLauncher.launch("image/*") },
                            shape = RoundedCornerShape(12.dp),
                            color = Surface3,
                            border = BorderStroke(1.dp, Border)
                        ) {
                            Icon(Icons.Default.AddPhotoAlternate, null, modifier = Modifier.padding(24.dp), tint = TextTertiary)
                        }
                    }
                    items(localPhotos) { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Section: Техника
            FormSection("Технические данные") {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    FormField("Объём (л)", engine, { engine = it }, "2.0", Modifier.weight(1f))
                    FormField("КПП", transmission, { transmission = it }, "Автомат", Modifier.weight(1f))
                }
            }

            Spacer(Modifier.height(16.dp))

            // Section: Финансы
            FormSection("Финансы") {
                FormField("Цена покупки (TJS) *", purchasePrice, { purchasePrice = it }, "250000")
                FormField("Планируемая цена продажи (TJS)", estimatedPrice, { estimatedPrice = it }, "320000")

                val purchase  = purchasePrice.toDoubleOrNull() ?: 0.0
                val estimated = estimatedPrice.toDoubleOrNull() ?: 0.0
                val totalExp  = localExpenses.sumOf { it.amount }
                if (purchase > 0 && estimated > 0) {
                    val profit = estimated - purchase - totalExp
                    val invested = purchase + totalExp
                    val roi    = if (invested > 0) (profit / invested * 100) else 0.0
                    Surface(
                        modifier  = Modifier.fillMaxWidth(),
                        shape     = RoundedCornerShape(12.dp),
                        color     = if (profit >= 0) Profit.copy(0.08f) else Loss.copy(0.08f),
                        border    = BorderStroke(0.5.dp, if (profit >= 0) Profit.copy(0.3f) else Loss.copy(0.3f))
                    ) {
                        Row(
                            Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Прогноз прибыли", style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                            Text(
                                "${profit.toLong()} TJS (ROI ${"%.0f".format(roi)}%)",
                                style      = MaterialTheme.typography.labelMedium,
                                color      = if (profit >= 0) Profit else Loss,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Section: Доп. Расходы
            FormSection("Дополнительные расходы") {
                localExpenses.forEach { exp ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("${exp.category.icon} ${exp.category.displayName}", style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                        Text("${exp.amount.toLong()} TJS", style = MaterialTheme.typography.bodyMedium, color = Loss)
                    }
                }
                TextButton(onClick = { showAddExpense = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp), tint = NeonBlue)
                    Text(" Добавить расход", color = NeonBlue)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Section: Статус
            FormSection("Статус") {
                ExposedDropdownMenuBox(expanded = statusExpanded, onExpandedChange = { statusExpanded = it }) {
                    OutlinedTextField(
                        value         = status.displayName,
                        onValueChange = {},
                        readOnly      = true,
                        label         = { Text("Текущий статус") },
                        trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(statusExpanded) },
                        modifier      = Modifier.menuAnchor().fillMaxWidth(),
                        colors        = crmFieldColors()
                    )
                    ExposedDropdownMenu(expanded = statusExpanded, onDismissRequest = { statusExpanded = false }, containerColor = Surface3) {
                        CarStatus.values().forEach { s ->
                            DropdownMenuItem(
                                text    = {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                        StatusChip(s)
                                    }
                                },
                                onClick = { status = s; statusExpanded = false }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Notes
            FormSection("Заметки") {
                OutlinedTextField(
                    value         = notes,
                    onValueChange = { notes = it },
                    placeholder   = { Text("Дополнительная информация…", color = TextTertiary) },
                    modifier      = Modifier.fillMaxWidth().height(100.dp),
                    colors        = crmFieldColors(),
                    shape         = RoundedCornerShape(12.dp)
                )
            }

            Spacer(Modifier.height(80.dp))
        }

        // Add expense dialog
        if (showAddExpense) {
            AddExpenseDialog(
                carUuid = finalCarUuid,
                currency = "TJS",
                onDismiss = { showAddExpense = false },
                onAdd = { exp ->
                    localExpenses = localExpenses + exp
                    showAddExpense = false
                }
            )
        }
    }
}

@Composable
fun FormSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(title, style = MaterialTheme.typography.titleSmall, color = TextTertiary)
        Spacer(Modifier.height(10.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape    = RoundedCornerShape(16.dp),
            color    = Surface2,
            border   = BorderStroke(0.5.dp, Border)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                content  = content
            )
        }
    }
}

@Composable
fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    OutlinedTextField(
        value         = value,
        onValueChange = onValueChange,
        label         = { Text(label, style = MaterialTheme.typography.labelMedium) },
        placeholder   = { Text(placeholder, color = TextTertiary) },
        modifier      = modifier,
        colors        = crmFieldColors(),
        shape         = RoundedCornerShape(12.dp),
        singleLine    = true
    )
}

@Composable
fun crmFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor      = NeonBlue,
    unfocusedBorderColor    = Border,
    focusedTextColor        = TextPrimary,
    unfocusedTextColor      = TextPrimary,
    focusedContainerColor   = Surface3,
    unfocusedContainerColor = Surface3,
    focusedLabelColor       = NeonBlue,
    unfocusedLabelColor     = TextTertiary,
    cursorColor             = NeonBlue
)
