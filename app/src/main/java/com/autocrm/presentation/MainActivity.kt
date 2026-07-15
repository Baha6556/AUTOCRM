package com.autocrm.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.autocrm.presentation.ui.addcar.AddCarScreen
import com.autocrm.presentation.ui.analytics.AnalyticsScreen
import com.autocrm.presentation.ui.cardetail.CarDetailScreen
import com.autocrm.presentation.ui.cars.CarsScreen
import com.autocrm.presentation.ui.dashboard.DashboardScreen
import com.autocrm.presentation.ui.sales.SalesScreen
import com.autocrm.presentation.ui.theme.*
import com.autocrm.presentation.viewmodel.CarsViewModel
import dagger.hilt.android.AndroidEntryPoint

sealed class Screen(val route: String) {
    object Dashboard  : Screen("dashboard")
    object Cars       : Screen("cars")
    object AddCar     : Screen("add_car")
    object EditCar    : Screen("edit_car/{carUuid}")
    object CarDetail  : Screen("car_detail/{carUuid}")
    object Sales      : Screen("sales")
    object Analytics  : Screen("analytics")
}

data class BottomNavItem(val screen: Screen, val icon: ImageVector, val label: String)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AutoCrmTheme {
                AutoCrmApp()
            }
        }
    }
}

@Composable
fun AutoCrmApp() {
    val navController  = rememberNavController()
    val vm: CarsViewModel = hiltViewModel()

    val bottomItems = listOf(
        BottomNavItem(Screen.Dashboard, Icons.Default.Dashboard,   "Главная"),
        BottomNavItem(Screen.Cars,      Icons.Default.DirectionsCar,"Машины"),
        BottomNavItem(Screen.Sales,     Icons.Default.Sell,         "Продажи"),
        BottomNavItem(Screen.Analytics, Icons.Default.BarChart,     "Аналитика")
    )

    val currentBack by navController.currentBackStackEntryAsState()
    val currentDest  = currentBack?.destination
    val topRoutes    = listOf(Screen.Dashboard.route, Screen.Cars.route, Screen.Sales.route, Screen.Analytics.route)
    val showBottom   = topRoutes.any { currentDest?.route == it }

    Scaffold(
        containerColor = Background,
        bottomBar = {
            if (showBottom) {
                NavigationBar(
                    containerColor   = Surface1,
                    contentColor     = TextSecondary,
                    tonalElevation   = androidx.compose.ui.unit.Dp(0f)
                ) {
                    bottomItems.forEach { item ->
                        val selected = currentDest?.hierarchy?.any { it.route == item.screen.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick  = {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState    = true
                                }
                            },
                            icon  = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label, style = MaterialTheme.typography.labelSmall) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor   = NeonBlue,
                                selectedTextColor   = NeonBlue,
                                unselectedIconColor = TextTertiary,
                                unselectedTextColor = TextTertiary,
                                indicatorColor      = NeonBlueDim
                            )
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController  = navController,
            startDestination = Screen.Dashboard.route,
            modifier       = Modifier.padding(padding),
            enterTransition  = { slideInHorizontally(tween(300)) { it / 4 } + fadeIn(tween(300)) },
            exitTransition   = { slideOutHorizontally(tween(300)) { -it / 4 } + fadeOut(tween(300)) },
            popEnterTransition  = { slideInHorizontally(tween(300)) { -it / 4 } + fadeIn(tween(300)) },
            popExitTransition   = { slideOutHorizontally(tween(300)) { it / 4 } + fadeOut(tween(300)) }
        ) {
            composable(Screen.Dashboard.route)  { 
                val dashVm: com.autocrm.presentation.viewmodel.DashboardViewModel = hiltViewModel()
                DashboardScreen(dashVm, navController) 
            }
            composable(Screen.Cars.route)        { CarsScreen(vm, navController) }
            composable(Screen.Sales.route)       { SalesScreen(vm, navController) }
            composable(Screen.Analytics.route)   { 
                val dashVm: com.autocrm.presentation.viewmodel.DashboardViewModel = hiltViewModel()
                AnalyticsScreen(carsVm = vm, dashboardVm = dashVm) 
            }
            composable(Screen.AddCar.route)      { AddCarScreen(editCarUuid = null, vm = vm, navController = navController) }
            composable(Screen.CarDetail.route)   { back ->
                val uuid = back.arguments?.getString("carUuid") ?: return@composable
                CarDetailScreen(uuid, vm, navController)
            }
            composable(Screen.EditCar.route)     { back ->
                val uuid = back.arguments?.getString("carUuid") ?: return@composable
                AddCarScreen(editCarUuid = uuid, vm = vm, navController = navController)
            }
        }
    }
}
