package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ItemType {
    CALCULATOR,
    COMPONENT,
    PINOUT,
    FORMULA,
    RESOURCE,
    TOOL
}

@Entity(tableName = "calculation_history")
data class CalculationHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val toolId: String,
    val toolName: String,
    val category: String,
    val inputSummary: String,
    val resultSummary: String,
    val stepDetails: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: String, // e.g. "calc_resistor" or "pinout_555"
    val itemType: String,
    val title: String,
    val subtitle: String,
    val iconName: String = "",
    val routeOrKey: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "pcb_projects")
data class PcbProjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String = "",
    val componentsJson: String = "[]",
    val wiresJson: String = "[]",
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "app_settings")
data class AppSettingEntity(
    @PrimaryKey val key: String,
    val value: String
)

// In-memory / Preloaded Data Classes

data class PinDetail(
    val pinNumber: Int,
    val pinName: String,
    val pinFunction: String,
    val pinType: String = "I/O", // Power, GND, I/O, Analog, PWM, Comm, Control
    val description: String = ""
)

data class PinoutItem(
    val id: String,
    val name: String,
    val subtitle: String,
    val category: String, // ICs, Transistors, Regulators, Microcontrollers, Displays, Ports, Sensors
    val packageType: String, // DIP-8, TO-220, DIP-14, Module, etc.
    val pinCount: Int,
    val pins: List<PinDetail>,
    val description: String,
    val keyFeatures: List<String> = emptyList(),
    val notes: String = ""
)

data class ComponentSpec(
    val label: String,
    val value: String
)

data class ComponentItem(
    val id: String,
    val partNumber: String,
    val name: String,
    val category: String, // Resistors, Capacitors, Diodes, Zener, Transistors, MOSFETs, IGBTs, Op-Amps, Voltage Regulators, Logic ICs, Timer ICs, Microcontrollers, Sensors
    val type: String,
    val packageType: String,
    val description: String,
    val specs: List<ComponentSpec>,
    val typicalApplication: String,
    val pinoutRefId: String? = null,
    val relatedParts: List<String> = emptyList()
)

data class FormulaVariable(
    val symbol: String,
    val name: String,
    val unit: String
)

data class FormulaItem(
    val id: String,
    val title: String,
    val category: String,
    val equation: String,
    val explanation: String,
    val variables: List<FormulaVariable>,
    val workedExample: String,
    val calculatorRoute: String? = null
)

data class ResourceSection(
    val id: String,
    val title: String,
    val description: String,
    val contentMarkdown: String = "",
    val tableHeaders: List<String> = emptyList(),
    val tableRows: List<List<String>> = emptyList()
)

// Universal Global Search Item
data class SearchResult(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val type: ItemType,
    val targetRoute: String
)

@Entity(tableName = "stock_items")
data class StockItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val partNumber: String,
    val name: String,
    val category: String,
    val quantity: Int = 0,
    val minimumQuantity: Int = 0,
    val location: String = "",
    val unitCost: Double = 0.0,
    val notes: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "workbench_projects")
data class WorkbenchProjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val deviceModel: String = "",
    val faultDescription: String = "",
    val notes: String = "",
    val measurementsJson: String = "[]",
    val partsJson: String = "[]",
    val updatedAt: Long = System.currentTimeMillis()
)


data class HandbookEntry(
    val id: String,
    val title: String,
    val summary: String,
    val category: String,
    val content: String
)
