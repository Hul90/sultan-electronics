package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.AppRepository
import com.example.data.ComponentsData
import com.example.data.FormulasData
import com.example.data.HandbookData
import com.example.data.PinoutsData
import com.example.data.ResourcesData
import com.example.data.SultanAdvancedData
import com.example.model.CalculationHistoryEntity
import com.example.model.FavoriteEntity
import com.example.model.ItemType
import com.example.model.PcbProjectEntity
import com.example.model.StockItemEntity
import com.example.model.WorkbenchProjectEntity
import com.example.model.SearchResult
import com.example.utils.CalculatorEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SultanViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository

    init {
        val db = AppDatabase.getInstance(application)
        repository = AppRepository(db)
    }

    // Room DB State Flows
    val historyList: StateFlow<List<CalculationHistoryEntity>> = repository.allHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val recentHistoryList: StateFlow<List<CalculationHistoryEntity>> = repository.recentHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoritesList: StateFlow<List<FavoriteEntity>> = repository.allFavorites
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pcbProjects: StateFlow<List<PcbProjectEntity>> = repository.allProjects
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val stockItems: StateFlow<List<StockItemEntity>> = repository.allStock
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val workbenchProjects: StateFlow<List<WorkbenchProjectEntity>> = repository.allWorkbenchProjects
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Settings
    private val _themeMode = MutableStateFlow("SYSTEM") // SYSTEM, DARK, LIGHT
    val themeMode: StateFlow<String> = _themeMode.asStateFlow()

    private val _decimalPrecision = MutableStateFlow(3)
    val decimalPrecision: StateFlow<Int> = _decimalPrecision.asStateFlow()

    private val _autoSaveHistory = MutableStateFlow(true)
    val autoSaveHistory: StateFlow<Boolean> = _autoSaveHistory.asStateFlow()

    // Global Search State
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedSearchCategory = MutableStateFlow("ALL")
    val selectedSearchCategory: StateFlow<String> = _selectedSearchCategory.asStateFlow()

    // Combined Universal Search Results
    val searchResults: StateFlow<List<SearchResult>> = combine(
        _searchQuery,
        _selectedSearchCategory
    ) { query, cat ->
        if (query.isBlank()) {
            emptyList()
        } else {
            val q = query.trim().lowercase()
            val allItems = mutableListOf<SearchResult>()

            // 1. Calculators
            val calculators = listOf(
                SearchResult("calc_resistor", "Resistor Color Code Calculator", "4-Band, 5-Band, 6-Band & Reverse Code", "Calculators", ItemType.CALCULATOR, "calc_resistor"),
                SearchResult("calc_smd", "SMD Resistor Decoder", "3-Digit, 4-Digit, R-Notation, EIA-96", "Calculators", ItemType.CALCULATOR, "calc_smd"),
                SearchResult("calc_ohms_law", "Ohm's Law & Power Calculator", "V, I, R, P, Energy", "Calculators", ItemType.CALCULATOR, "calc_ohms_law"),
                SearchResult("calc_capacitor", "Capacitor Code & Reactance", "EIA Codes, Series/Parallel, RC, Energy", "Calculators", ItemType.CALCULATOR, "calc_capacitor"),
                SearchResult("calc_inductor", "Inductor Color & Reactance", "Series/Parallel, RL, Stored Energy", "Calculators", ItemType.CALCULATOR, "calc_inductor"),
                SearchResult("calc_ac_power", "Power & RMS Calculator", "DC, AC Single-Phase, 3-Phase Watts, VA, VAR", "Calculators", ItemType.CALCULATOR, "calc_ac_power"),
                SearchResult("calc_led", "LED Current Limiting Resistor", "Series/Parallel, E24 Resistor Selection", "Calculators", ItemType.CALCULATOR, "calc_led"),
                SearchResult("calc_zener", "Zener Voltage Regulator", "Series Resistor, Wattage, Power", "Calculators", ItemType.CALCULATOR, "calc_zener"),
                SearchResult("calc_555", "NE555 Timer Calculator", "Astable & Monostable Multivibrator", "Calculators", ItemType.CALCULATOR, "calc_555"),
                SearchResult("calc_voltage_divider", "Voltage Divider Calculator", "Loaded & Unloaded Resistor Divider", "Calculators", ItemType.CALCULATOR, "calc_voltage_divider"),
                SearchResult("calc_battery", "Battery Pack Runtime & Energy", "Li-ion, LiPo, LiFePO4, SLA, Wh/Ah", "Calculators", ItemType.CALCULATOR, "calc_battery"),
                SearchResult("calc_wire", "Wire & Cable Voltage Drop", "AWG, mm², Copper/Aluminum Resistance", "Calculators", ItemType.CALCULATOR, "calc_wire"),
                SearchResult("calc_rf", "RF Suite & Antenna Calculator", "Dipole, Monopole, Wavelength, SWR", "Calculators", ItemType.CALCULATOR, "calc_rf"),
                SearchResult("calc_unit_conv", "Universal Electronics Unit Converter", "V, A, Ω, F, H, Hz, W, dBm", "Calculators", ItemType.CALCULATOR, "calc_unit_conv")
            )
            allItems.addAll(calculators)

            // 2. Pro Tools
            val proTools = listOf(
                SearchResult("tool_oscilloscope", "Sultan Oscilloscope (PRO)", "Dual-Channel Virtual Signal Analyzer", "Pro Tools", ItemType.TOOL, "tool_oscilloscope"),
                SearchResult("tool_spectrum", "Sultan Spectrum Analyzer (PRO)", "FFT Frequency Spectrum & Harmonics", "Pro Tools", ItemType.TOOL, "tool_spectrum"),
                SearchResult("tool_pcb", "Sultan PCB Designer (PRO)", "2D Schematic & Board Layout Workspace", "Pro Tools", ItemType.TOOL, "tool_pcb")
            )
            allItems.addAll(proTools)

            // 3. Components
            ComponentsData.allComponents.forEach { comp ->
                allItems.add(
                    SearchResult(
                        id = comp.id,
                        title = "${comp.partNumber} - ${comp.name}",
                        subtitle = "${comp.category} | ${comp.packageType} | ${comp.description.take(60)}...",
                        category = "Components",
                        type = ItemType.COMPONENT,
                        targetRoute = "component_detail/${comp.id}"
                    )
                )
            }

            // 4. Pinouts
            PinoutsData.allPinouts.forEach { pin ->
                allItems.add(
                    SearchResult(
                        id = pin.id,
                        title = pin.name,
                        subtitle = "${pin.category} | ${pin.packageType} (${pin.pinCount} Pins)",
                        category = "Pinouts",
                        type = ItemType.PINOUT,
                        targetRoute = "pinout_detail/${pin.id}"
                    )
                )
            }

            // 5. Formulas
            FormulasData.allFormulas.forEach { f ->
                allItems.add(
                    SearchResult(
                        id = f.id,
                        title = f.title,
                        subtitle = "${f.category} | ${f.equation}",
                        category = "Formulas",
                        type = ItemType.FORMULA,
                        targetRoute = "formula_detail/${f.id}"
                    )
                )
            }

            // 6. Resources
            ResourcesData.allResources.forEach { r ->
                allItems.add(
                    SearchResult(
                        id = r.id,
                        title = r.title,
                        subtitle = r.description,
                        category = "Resources",
                        type = ItemType.RESOURCE,
                        targetRoute = "resource_detail/${r.id}"
                    )
                )
            }

            // 7. Sultan Advanced Components & 3D Models
            SultanAdvancedData.allComponents.forEach { saComp ->
                allItems.add(
                    SearchResult(
                        id = saComp.id,
                        title = "${saComp.partNumber} - ${saComp.name} (Sultan Advanced)",
                        subtitle = "${saComp.packageType} | ${saComp.description.take(70)}...",
                        category = "Components",
                        type = ItemType.COMPONENT,
                        targetRoute = "sa_detail/${saComp.id}"
                    )
                )
            }

            // 8. Sultan Advanced Fan & Motor Wiring
            SultanAdvancedData.fanMotorGuides.forEach { fan ->
                allItems.add(
                    SearchResult(
                        id = fan.id,
                        title = "${fan.title} (${fan.category})",
                        subtitle = "Cap: ${fan.capacitorRating} | ${fan.subtitle}",
                        category = "Resources",
                        type = ItemType.RESOURCE,
                        targetRoute = "sa_fan_motors"
                    )
                )
            }

            // 9. Sultan Advanced Practical Guides
            SultanAdvancedData.practicalGuides.forEach { pg ->
                allItems.add(
                    SearchResult(
                        id = pg.id,
                        title = pg.title,
                        subtitle = "${pg.category} | ${pg.subtitle}",
                        category = "Resources",
                        type = ItemType.RESOURCE,
                        targetRoute = "sa_practical_guides"
                    )
                )
            }

            // 10. Workshop utilities
            allItems.add(SearchResult("stock_manager", "SULTAN Component Stock Manager", "Inventory, quantity, location and low-stock alerts", "Workshop", ItemType.TOOL, "sa_stock"))
            allItems.add(SearchResult("workbench", "SULTAN Workbench Mode", "Repair projects, notes, measurements and parts", "Workshop", ItemType.TOOL, "sa_workbench"))
            HandbookData.all.forEach { h ->
                allItems.add(SearchResult(h.id, h.title, h.summary, "Handbook", ItemType.RESOURCE, "sa_handbook/${h.id}"))
            }

            // Filter by search query and category
            allItems.filter { item ->
                val matchesQuery = item.title.lowercase().contains(q) ||
                        item.subtitle.lowercase().contains(q) ||
                        item.category.lowercase().contains(q) ||
                        (q == "555" && item.id.contains("555")) ||
                        (q == "7805" && item.id.contains("7805")) ||
                        (q == "ohm" && item.title.lowercase().contains("ohm")) ||
                        (q == "smd" && item.title.lowercase().contains("smd"))

                val matchesCat = if (cat == "ALL") true else item.category.equals(cat, ignoreCase = true)
                matchesQuery && matchesCat
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSearchCategory(cat: String) {
        _selectedSearchCategory.value = cat
    }

    fun toggleFavorite(
        id: String,
        itemType: String,
        title: String,
        subtitle: String,
        routeOrKey: String,
        iconName: String = ""
    ) {
        viewModelScope.launch {
            repository.toggleFavorite(id, itemType, title, subtitle, routeOrKey, iconName)
        }
    }

    fun isFavorite(id: String): Boolean {
        return favoritesList.value.any { it.id == id }
    }

    fun saveCalculation(
        toolId: String,
        toolName: String,
        category: String,
        inputSummary: String,
        resultSummary: String,
        stepDetails: String = ""
    ) {
        if (!_autoSaveHistory.value) return
        viewModelScope.launch {
            repository.saveHistory(toolId, toolName, category, inputSummary, resultSummary, stepDetails)
        }
    }

    fun deleteHistory(id: Long) {
        viewModelScope.launch {
            repository.deleteHistory(id)
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }

    fun clearAllFavorites() {
        viewModelScope.launch {
            repository.clearFavorites()
        }
    }

    fun setThemeMode(mode: String) {
        _themeMode.value = mode
        viewModelScope.launch {
            repository.setSetting("theme_mode", mode)
        }
    }

    fun setDecimalPrecision(precision: Int) {
        _decimalPrecision.value = precision
        viewModelScope.launch {
            repository.setSetting("decimal_precision", precision.toString())
        }
    }

    fun setAutoSaveHistory(enabled: Boolean) {
        _autoSaveHistory.value = enabled
        viewModelScope.launch {
            repository.setSetting("auto_save_history", enabled.toString())
        }
    }

    fun saveStockItem(item: StockItemEntity) {
        viewModelScope.launch { repository.saveStockItem(item) }
    }

    fun deleteStockItem(id: Long) {
        viewModelScope.launch { repository.deleteStockItem(id) }
    }

    fun adjustStock(id: Long, delta: Int) {
        viewModelScope.launch { repository.adjustStock(id, delta) }
    }

    fun saveWorkbenchProject(project: WorkbenchProjectEntity) {
        viewModelScope.launch { repository.saveWorkbenchProject(project) }
    }

    fun deleteWorkbenchProject(id: Long) {
        viewModelScope.launch { repository.deleteWorkbenchProject(id) }
    }

    // PCB Designer actions
    fun savePcbProject(name: String, description: String, compJson: String, wiresJson: String, id: Long = 0) {
        viewModelScope.launch {
            repository.savePcbProject(name, description, compJson, wiresJson, id)
        }
    }

    fun deletePcbProject(id: Long) {
        viewModelScope.launch {
            repository.deleteProject(id)
        }
    }
}
