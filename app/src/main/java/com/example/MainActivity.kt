package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.CalculatorsListScreen
import com.example.ui.screens.ComponentDetailScreen
import com.example.ui.screens.ComponentsListScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.FormulaDetailScreen
import com.example.ui.screens.FormulasListScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PinoutDetailScreen
import com.example.ui.screens.PinoutsListScreen
import com.example.ui.screens.ResourceDetailScreen
import com.example.ui.screens.ResourcesListScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.ToolsListScreen
import com.example.ui.screens.advanced.FanMotorConnectionsScreen
import com.example.ui.screens.advanced.PracticalConnectionsScreen
import com.example.ui.screens.advanced.Sultan3DShowcaseScreen
import com.example.ui.screens.advanced.SultanAdvancedDetailScreen
import com.example.ui.screens.advanced.SultanAdvancedMainScreen
import com.example.ui.screens.advanced.SultanPinoutCompareScreen
import com.example.ui.screens.advanced.SultanStockManagerScreen
import com.example.ui.screens.advanced.SultanWorkbenchScreen
import com.example.ui.screens.advanced.SultanHandbookScreen
import com.example.ui.screens.calculators.AcPowerCalculatorScreen
import com.example.ui.screens.calculators.BatteryCalculatorScreen
import com.example.ui.screens.calculators.CapacitorCalculatorScreen
import com.example.ui.screens.calculators.InductorCalculatorScreen
import com.example.ui.screens.calculators.LedCalculatorScreen
import com.example.ui.screens.calculators.OhmsLawCalculatorScreen
import com.example.ui.screens.calculators.ResistorCalculatorScreen
import com.example.ui.screens.calculators.RfSuiteScreen
import com.example.ui.screens.calculators.SmdCalculatorScreen
import com.example.ui.screens.calculators.Timer555CalculatorScreen
import com.example.ui.screens.calculators.UnitConverterScreen
import com.example.ui.screens.calculators.VoltageDividerScreen
import com.example.ui.screens.calculators.WireCalculatorScreen
import com.example.ui.screens.calculators.ZenerCalculatorScreen
import com.example.ui.screens.tools.OscilloscopeScreen
import com.example.ui.screens.tools.PcbDesignerScreen
import com.example.ui.screens.tools.SpectrumAnalyzerScreen
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ElectricGreen
import com.example.ui.theme.PhosphorCyan
import com.example.ui.theme.SultanElectronicsTheme
import com.example.ui.viewmodel.SultanViewModel

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

class MainActivity : ComponentActivity() {
    private val viewModel: SultanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SultanElectronicsTheme {
                val navController = rememberNavController()
                MainAppScaffold(navController = navController, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppScaffold(
    navController: NavHostController,
    viewModel: SultanViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem("home", "Home", Icons.Default.Home),
        BottomNavItem("calculators", "Calculators", Icons.Default.Calculate),
        BottomNavItem("components", "Parts & ICs", Icons.Default.Memory),
        BottomNavItem("tools", "Instruments", Icons.Default.Construction),
        BottomNavItem("resources", "Reference", Icons.Default.MenuBook)
    )

    val showBottomBar = currentRoute in listOf("home", "calculators", "components", "tools", "resources")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = DarkSurface,
                    contentColor = ElectricGreen
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo("home") { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    tint = if (selected) ElectricGreen else PhosphorCyan.copy(alpha = 0.5f)
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    fontSize = 10.sp,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selected) ElectricGreen else PhosphorCyan.copy(alpha = 0.7f)
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = ElectricGreen.copy(alpha = 0.15f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Main Bottom Tab Destinations
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
            composable("calculators") {
                CalculatorsListScreen(
                    viewModel = viewModel,
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
            composable("components") {
                ComponentsListScreen(
                    viewModel = viewModel,
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
            composable("tools") {
                ToolsListScreen(
                    viewModel = viewModel,
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
            composable("resources") {
                ResourcesListScreen(
                    viewModel = viewModel,
                    onNavigate = { route -> navController.navigate(route) }
                )
            }

            // Calculators
            composable("calc_resistor") {
                ResistorCalculatorScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("calc_smd") {
                SmdCalculatorScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("calc_ohms_law") {
                OhmsLawCalculatorScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("calc_led") {
                LedCalculatorScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("calc_555") {
                Timer555CalculatorScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("calc_voltage_divider") {
                VoltageDividerScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("calc_battery") {
                BatteryCalculatorScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("calc_wire") {
                WireCalculatorScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("calc_rf") {
                RfSuiteScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("calc_capacitor") {
                CapacitorCalculatorScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("calc_zener") {
                ZenerCalculatorScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("calc_inductor") {
                InductorCalculatorScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("calc_ac_power") {
                AcPowerCalculatorScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("calc_unit_conv") {
                UnitConverterScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }

            // Pro Simulation Instruments
            composable("tool_oscilloscope") {
                OscilloscopeScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("tool_spectrum") {
                SpectrumAnalyzerScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("tool_pcb") {
                PcbDesignerScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }

            // Pinouts & Details
            composable("pinouts") {
                PinoutsListScreen(viewModel = viewModel, onNavigate = { route -> navController.navigate(route) })
            }
            composable(
                route = "pinout_detail/{pinoutId}",
                arguments = listOf(navArgument("pinoutId") { type = NavType.StringType })
            ) { backStackEntry ->
                val pinoutId = backStackEntry.arguments?.getString("pinoutId") ?: ""
                PinoutDetailScreen(pinoutId = pinoutId, viewModel = viewModel, onBack = { navController.popBackStack() })
            }

            // Component Detail
            composable(
                route = "component_detail/{compId}",
                arguments = listOf(navArgument("compId") { type = NavType.StringType })
            ) { backStackEntry ->
                val compId = backStackEntry.arguments?.getString("compId") ?: ""
                ComponentDetailScreen(
                    compId = compId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onNavigate = { route -> navController.navigate(route) }
                )
            }

            // Formulas & Details
            composable("formulas") {
                FormulasListScreen(viewModel = viewModel, onNavigate = { route -> navController.navigate(route) })
            }
            composable(
                route = "formula_detail/{formulaId}",
                arguments = listOf(navArgument("formulaId") { type = NavType.StringType })
            ) { backStackEntry ->
                val formulaId = backStackEntry.arguments?.getString("formulaId") ?: ""
                FormulaDetailScreen(
                    formulaId = formulaId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onNavigate = { route -> navController.navigate(route) }
                )
            }

            // Resource Detail
            composable(
                route = "resource_detail/{resourceId}",
                arguments = listOf(navArgument("resourceId") { type = NavType.StringType })
            ) { backStackEntry ->
                val resourceId = backStackEntry.arguments?.getString("resourceId") ?: ""
                ResourceDetailScreen(resourceId = resourceId, viewModel = viewModel, onBack = { navController.popBackStack() })
            }

            // SULTAN ADVANCED Workstation Routes
            composable("sa_main") {
                SultanAdvancedMainScreen(viewModel = viewModel, onNavigate = { route -> navController.navigate(route) })
            }
            composable(
                route = "sa_detail/{compId}",
                arguments = listOf(navArgument("compId") { type = NavType.StringType })
            ) { backStackEntry ->
                val compId = backStackEntry.arguments?.getString("compId") ?: ""
                SultanAdvancedDetailScreen(
                    compId = compId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
            composable(
                route = "sa_category/{catId}",
                arguments = listOf(navArgument("catId") { type = NavType.StringType })
            ) { backStackEntry ->
                val catId = backStackEntry.arguments?.getString("catId") ?: "ALL"
                SultanAdvancedMainScreen(
                    viewModel = viewModel,
                    onNavigate = { route -> navController.navigate(route) },
                    initialCategoryFilter = catId
                )
            }
            composable("sa_fan_motors") {
                FanMotorConnectionsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("sa_practical_guides") {
                PracticalConnectionsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("sa_compare") {
                SultanPinoutCompareScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("sa_3d_showcase") {
                Sultan3DShowcaseScreen(viewModel = viewModel, onBack = { navController.popBackStack() }, onNavigate = { route -> navController.navigate(route) })
            }
            composable("sa_stock") {
                SultanStockManagerScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("sa_workbench") {
                SultanWorkbenchScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("sa_handbook") {
                SultanHandbookScreen(onBack = { navController.popBackStack() })
            }
            composable("sa_handbook/{entryId}", arguments = listOf(navArgument("entryId") { type = NavType.StringType })) { backStackEntry ->
                SultanHandbookScreen(onBack = { navController.popBackStack() }, entryId = backStackEntry.arguments?.getString("entryId"))
            }

            // Favorites, History, Search, Settings, About
            composable("favorites") {
                FavoritesScreen(viewModel = viewModel, onNavigate = { route -> navController.navigate(route) }, onBack = { navController.popBackStack() })
            }
            composable("history") {
                HistoryScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable("search") {
                SearchScreen(viewModel = viewModel, onNavigate = { route -> navController.navigate(route) }, onBack = { navController.popBackStack() })
            }
            composable("settings") {
                SettingsScreen(viewModel = viewModel, onNavigate = { route -> navController.navigate(route) }, onBack = { navController.popBackStack() })
            }
            composable("about") {
                AboutScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
