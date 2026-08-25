package com.example.ui.screens.calculators

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ActionButtonsRow
import com.example.ui.components.ResultPanel
import com.example.ui.components.SultanTopAppBar
import com.example.ui.components.TechnicalInputField
import com.example.ui.theme.ElectricGreen
import com.example.ui.theme.NeonRed
import com.example.ui.theme.PhosphorCyan
import com.example.ui.theme.TechAmber
import com.example.ui.viewmodel.SultanViewModel
import com.example.utils.CalculatorEngine

data class LedPreset(val name: String, val typicalVf: Double, val color: Color)

@Composable
fun LedCalculatorScreen(
    viewModel: SultanViewModel,
    onBack: () -> Unit
) {
    var supplyVoltageInput by remember { mutableStateOf("12") }
    var forwardVoltageInput by remember { mutableStateOf("2.0") }
    var ledCurrentInput by remember { mutableStateOf("20") }
    var currentUnit by remember { mutableStateOf("mA") }
    var ledCountInput by remember { mutableStateOf("1") }
    var isSeries by remember { mutableStateOf(true) }

    val ledPresets = listOf(
        LedPreset("Red", 1.9, NeonRed),
        LedPreset("Amber/Orange", 2.0, TechAmber),
        LedPreset("Yellow", 2.1, Color(0xFFFFEB3B)),
        LedPreset("Green", 2.2, ElectricGreen),
        LedPreset("Blue", 3.2, PhosphorCyan),
        LedPreset("White", 3.2, Color.White),
        LedPreset("UV", 3.5, Color(0xFF9C27B0))
    )

    val vs = supplyVoltageInput.toDoubleOrNull() ?: 12.0
    val vf = forwardVoltageInput.toDoubleOrNull() ?: 2.0
    val iAmps = (ledCurrentInput.toDoubleOrNull() ?: 20.0) * (if (currentUnit == "mA") 1e-3 else 1.0)
    val count = ledCountInput.toIntOrNull() ?: 1

    val calculation = CalculatorEngine.calculateLedResistor(vs, vf, iAmps, count, isSeries)
    val isFav = viewModel.isFavorite("calc_led")

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "LED Current Limiter",
                subtitle = "Series Resistor & Power Dissipation",
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite("calc_led", "CALCULATOR", "LED Resistor", "LED Current Limiting Resistor", "calc_led")
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Configuration mode: Series vs Parallel
            item {
                TabRow(
                    selectedTabIndex = if (isSeries) 0 else 1,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = ElectricGreen
                ) {
                    Tab(
                        selected = isSeries,
                        onClick = { isSeries = true },
                        text = { Text("Series Array", fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = !isSeries,
                        onClick = { isSeries = false },
                        text = { Text("Parallel Array", fontWeight = FontWeight.Bold) }
                    )
                }
            }

            // Quick Color Presets
            item {
                Column {
                    Text(
                        text = "LED Color Presets (Typical Vf):",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(ledPresets) { preset ->
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(
                                        1.dp,
                                        if (forwardVoltageInput == preset.typicalVf.toString()) ElectricGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { forwardVoltageInput = preset.typicalVf.toString() }
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(12.dp)
                                            .clip(CircleShape)
                                            .background(preset.color)
                                            .border(0.5.dp, Color.Gray, CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "${preset.name} (${preset.typicalVf}V)",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Input Fields
            item {
                TechnicalInputField(
                    label = "Supply Voltage (Vs)",
                    value = supplyVoltageInput,
                    onValueChange = { supplyVoltageInput = it },
                    unit = "V",
                    placeholder = "e.g. 12"
                )
            }

            item {
                TechnicalInputField(
                    label = "LED Forward Voltage Drop (Vf)",
                    value = forwardVoltageInput,
                    onValueChange = { forwardVoltageInput = it },
                    unit = "V",
                    placeholder = "e.g. 2.0"
                )
            }

            item {
                TechnicalInputField(
                    label = "Desired LED Current (If)",
                    value = ledCurrentInput,
                    onValueChange = { ledCurrentInput = it },
                    unitOptions = listOf("mA", "A"),
                    selectedUnit = currentUnit,
                    onUnitSelected = { currentUnit = it },
                    placeholder = "e.g. 20"
                )
            }

            item {
                TechnicalInputField(
                    label = "Number of LEDs in Array",
                    value = ledCountInput,
                    onValueChange = { ledCountInput = it },
                    placeholder = "e.g. 1"
                )
            }

            // Result
            item {
                ResultPanel(
                    title = "Recommended Resistor",
                    resultText = calculation.formattedResult,
                    stepsText = calculation.calculationSteps,
                    toolName = "LED Resistor",
                    onReset = {
                        supplyVoltageInput = "12"
                        forwardVoltageInput = "2.0"
                        ledCurrentInput = "20"
                        ledCountInput = "1"
                    }
                )
            }

            item {
                ActionButtonsRow(
                    onCalculate = {
                        viewModel.saveCalculation(
                            "calc_led",
                            "LED Resistor",
                            "Calculators",
                            "Vs=$supplyVoltageInput V, Vf=$forwardVoltageInput V, If=$ledCurrentInput $currentUnit, Count=$ledCountInput",
                            calculation.formattedResult,
                            calculation.calculationSteps
                        )
                    },
                    onReset = {
                        supplyVoltageInput = "12"
                        forwardVoltageInput = "2.0"
                        ledCurrentInput = "20"
                        ledCountInput = "1"
                    },
                    calculateLabel = "Save to History"
                )
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}
