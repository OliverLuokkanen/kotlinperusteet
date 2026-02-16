package com.example.kotlinperusteet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kotlinperusteet.navigation.ROUTE_CALENDAR
import com.example.kotlinperusteet.navigation.ROUTE_HOME
import com.example.kotlinperusteet.navigation.ROUTE_SETTINGS
import com.example.kotlinperusteet.navigation.ROUTE_WEATHER
import com.example.kotlinperusteet.ui.theme.KotlinPerusteetTheme
import com.example.kotlinperusteet.view.CalendarScreen
import com.example.kotlinperusteet.view.HomeScreen
import com.example.kotlinperusteet.view.SettingsScreen
import com.example.kotlinperusteet.view.WeatherScreen
import com.example.kotlinperusteet.viewmodel.TaskViewModel
import com.example.kotlinperusteet.viewmodel.WeatherViewModel
import androidx.compose.material.icons.automirrored.filled.List

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinPerusteetTheme {
                Surface {
                    val navController = rememberNavController()
                    val vm: TaskViewModel = viewModel()
                    val weatherVm: WeatherViewModel = viewModel()

                    val items = listOf(
                        BottomNavItem("Tehtävät", ROUTE_HOME, Icons.AutoMirrored.Filled.List),
                        BottomNavItem("Kalenteri", ROUTE_CALENDAR, Icons.Default.DateRange),
                        BottomNavItem("Sää", ROUTE_WEATHER, Icons.Default.Cloud),
                        BottomNavItem("Asetukset", ROUTE_SETTINGS, Icons.Default.Settings)
                    )

                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                items.forEach { item ->
                                    NavigationBarItem(
                                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                                        onClick = {
                                            navController.navigate(item.route) {
                                                popUpTo(ROUTE_HOME) { inclusive = false }
                                                launchSingleTop = true
                                            }
                                        },
                                        icon = { androidx.compose.material3.Icon(item.icon, contentDescription = item.label) },
                                        label = { Text(item.label) }
                                    )
                                }
                            }
                        }
                    ) { padding ->
                        NavHost(
                            navController = navController,
                            startDestination = ROUTE_HOME
                        ) {
                            composable(ROUTE_HOME) {
                                HomeScreen(vm = vm, paddingValues = padding)
                            }
                            composable(ROUTE_CALENDAR) {
                                CalendarScreen(vm = vm, paddingValues = padding)
                            }
                            composable(ROUTE_WEATHER) {
                                WeatherScreen(vm = weatherVm, paddingValues = padding)
                            }
                            composable(ROUTE_SETTINGS) {
                                SettingsScreen(paddingValues = padding)
                            }
                        }
                    }
                }
            }
        }
    }
}

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)