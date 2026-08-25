package com.example.model

import androidx.compose.ui.graphics.Color

enum class Component3DType {
    TO92_TRANSISTOR,
    TO220_PACKAGE,
    DIP8_IC,
    DIP14_IC,
    DIP16_IC,
    DIP28_IC,
    SOIC8_SMD,
    ELECTROLYTIC_CAP,
    CERAMIC_CAP,
    MOTOR_RUN_CAP,
    RESISTOR_AXIAL,
    DIODE_DO41,
    LED_5MM,
    RELAY_SPDT,
    ESP32_BOARD,
    ARDUINO_UNO,
    ARDUINO_NANO,
    CEILING_FAN_MOTOR,
    TABLE_FAN_MOTOR
}

data class AdvancedCategoryItem(
    val id: String,
    val name: String,
    val subtitle: String,
    val count: Int,
    val iconType: Component3DType,
    val accentColor: Long = 0xFF00E676
)

data class AdvancedPinoutEntry(
    val pinNumber: Int,
    val pinName: String,
    val function: String,
    val type: String, // Power, GND, Input, Output, Analog, PWM, Comm, Control
    val description: String = ""
)

data class ElectricalRating(
    val parameter: String,
    val symbol: String,
    val testCondition: String,
    val min: String = "—",
    val typ: String = "—",
    val max: String = "—",
    val unit: String
)

data class TypicalCircuit(
    val title: String,
    val description: String,
    val componentsNeeded: List<String>,
    val schematicDiagramType: String,
    val formulas: List<String> = emptyList(),
    val practicalNotes: String = ""
)

data class AdvancedComponent(
    val id: String,
    val partNumber: String,
    val name: String,
    val category: String,
    val family: String,
    val packageType: String,
    val pinCount: Int,
    val pins: List<AdvancedPinoutEntry>,
    val threeDType: Component3DType,
    val absoluteMaxRatings: List<Pair<String, String>>,
    val electricalRatings: List<ElectricalRating>,
    val symbolType: String,
    val pinoutWarning: String? = null,
    val description: String,
    val typicalCircuits: List<TypicalCircuit>,
    val commonApplications: List<String>,
    val equivalents: List<String>,
    val datasheetNotes: String
)

data class FanMotorWire(
    val colorName: String,
    val hexColor: Long,
    val terminalName: String,
    val function: String,
    val connectionTarget: String
)

data class FanMotorGuide(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String, // Ceiling Fan, Table Fan, Single-Phase Motor, Capacitor Sizing, Speed Control
    val motorType: String,
    val capacitorRating: String,
    val wires: List<FanMotorWire>,
    val workingPrinciple: String,
    val stepByStepWiring: List<String>,
    val safetyWarning: String,
    val troubleshooting: List<Pair<String, String>>
)

data class PracticalCircuitGuide(
    val id: String,
    val title: String,
    val category: String, // Switching, Power Supply, Amplifiers, Timing, Logic, Protection
    val subtitle: String,
    val componentsUsed: List<String>,
    val schematicSymbol: String,
    val workingPrinciple: String,
    val stepByStepConnections: List<String>,
    val keyFormulas: List<Pair<String, String>>,
    val typicalValues: List<Pair<String, String>>,
    val safetyNotes: String
)

data class SchematicSymbol(
    val id: String,
    val name: String,
    val standard: String, // IEEE / ANSI / IEC
    val category: String,
    val description: String,
    val terminals: List<String>,
    val typicalApplications: String
)
