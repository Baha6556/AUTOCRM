package com.autocrm.presentation.ui.cars

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.autocrm.domain.model.CarStatus
import com.autocrm.presentation.Screen
import com.autocrm.presentation.ui.*
import com.autocrm.presentation.ui.theme.*
import com.autocrm.presentation.viewmodel.CarsViewModel
import com.autocrm.presentation.viewmodel.SortBy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarsScreen(vm: CarsViewModel, navController: NavController) {
    val state by vm.carsUiState.collectAsStateWithLifecycle()
    var showSortSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Background)) {
        Column {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Мои машины", style = MaterialTheme.typography.headlineMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = { showSortSheet = true }) {
                        Icon(Icons.Default.Sort, null, tint = TextSecondary)
                    }
                }
            }

            // Search bar
            OutlinedTextField(
                value         = state.searchQuery,
                onValueChange = vm::updateSearchQuery,
                placeholder   = { Text("Марка, модель, VIN…", color = TextTertiary) },
                leadingIcon   = { Icon(Icons.Default.Search, null, tint = TextTertiary) },
                trailingIcon  = if (state.searchQuery.isNotEmpty()) {{
                    IconButton(onClick = { vm.updateSearchQuery("") }) {
                        Icon(Icons.Default.Clear, null, tint = TextTertiary)
                    }
                }} else null,
                modifier      = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors        = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = NeonBlue,
                    unfocusedBorderColor = Border,
                    focusedContainerColor   = Surface2,
                    unfocusedContainerColor = Surface2,
                    cursorColor          = NeonBlue,
                    focusedTextColor     = TextPrimary,
                    unfocusedTextColor   = TextPrimary
                ),
                shape         = RoundedCornerShape(14.dp),
                singleLine    = true
            )

            Spacer(Modifier.height(12.dp))

            // Status filter chips
            val statuses = listOf(null) + CarStatus.values().toList()
            LazyRow(
                contentPadding        = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(statuses) { status ->
                    val selected = state.selectedStatus == status
                    FilterChip(
                        selected = selected,
                        onClick  = { vm.updateFilter(status) },
                        label    = { Text(status?.displayName ?: "Все") },
                        colors   = FilterChipDefaults.filterChipColors(
                            selectedContainerColor      = NeonBlueDim,
                            selectedLabelColor          = NeonBlue,
                            containerColor              = Surface2,
                            labelColor                  = TextSecondary
                        ),
                        border   = FilterChipDefaults.filterChipBorder(
                            enabled             = true,
                            selected            = selected,
                            selectedBorderColor = NeonBlue,
                            borderColor         = Border,
                            selectedBorderWidth = 1.dp
                        )
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Count
            Text(
                "${state.cars.size} автомобилей",
                style    = MaterialTheme.typography.bodySmall,
                color    = TextTertiary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(Modifier.height(8.dp))

            // List
            if (state.cars.isEmpty()) {
                EmptyState(
                    icon     = Icons.Default.DirectionsCar,
                    title    = "Нет автомобилей",
                    subtitle = "Добавьте первый автомобиль"
                )
            } else {
                LazyColumn(
                    contentPadding        = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                    verticalArrangement   = Arrangement.spacedBy(14.dp)
                ) {
                    items(state.cars, key = { it.car.uuid }) { car ->
                        CarCard(
                            car     = car,
                            onClick = { navController.navigate("car_detail/${car.car.uuid}") }
                        )
                    }
                    item { Spacer(Modifier.height(80.dp)) }
                }
            }
        }

        // FAB
        FloatingActionButton(
            onClick          = { navController.navigate(Screen.AddCar.route) },
            modifier         = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 24.dp),
            containerColor   = NeonBlue,
            contentColor     = Background
        ) {
            Icon(Icons.Default.Add, "Добавить машину")
        }

        // Sort sheet
        if (showSortSheet) {
            ModalBottomSheet(
                onDismissRequest    = { showSortSheet = false },
                containerColor      = Surface1,
                scrimColor          = Background.copy(0.7f),
                dragHandle          = {
                    Box(
                        Modifier
                            .width(40.dp).height(4.dp)
                            .clip(CircleShape)
                            .background(Border)
                    )
                }
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Сортировка", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(16.dp))
                    SortBy.values().forEach { sort ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    vm.setSortBy(sort)
                                    showSortSheet = false
                                }
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(sort.label, color = if (state.sortBy == sort) NeonBlue else TextPrimary)
                            if (state.sortBy == sort) {
                                Icon(Icons.Default.Check, null, tint = NeonBlue, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}
