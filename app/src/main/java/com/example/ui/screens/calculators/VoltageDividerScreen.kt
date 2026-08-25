package com.example.ui.screens.calculators

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ActionButtonsRow
import com.example.ui.components.ResultPanel
import com.example.ui.components.SultanTopAppBar
import com.example.ui.components.TechnicalInputField
import com.example.ui.theme.ElectricGreen
import com.example.ui.theme.PhosphorCyan
import com.example.ui.viewmodel.SultanViewModel
import com.example.utils.CalculatorEngine

@Composable
fun VoltageDividerScreen(
    viewModel: SultanViewModel,
    onBack: () -> Unit
) {
    var vinInput by remember { mutableStateOf("12") }
    var r1Input by remember { mutableStateOf("10") }
    var r1Unit by remember { mutableStateOf("kΩ") }
    var r2Input by remember { mutableStateOf("10") }
    var r2Unit by remember { mutableStateOf("kΩ") }
    var rLoadInput by remember { mutableStateOf("") }
    var rLoadUnit by remember { mutableStateOf("kΩ") }

    val isFav = viewModel.isFavorite("calc_voltage_divider")

    fun parseR(valStr: String, unit: String): Double? {
        val num = valStr.toDoubleOrNull() ?: return null
        return when (unit) {
            "kΩ" -> num * 1000.0
            "MΩ" -> num * 1_000_000.0
            else -> num
        }
    }

    val vin = vinInput.toDoubleOrNull() ?: 12.0
    val r1 = parseR(r1Input, r1Unit) ?: 10000.0
    val r2 = parseR(r2Input, r2Unit) ?: 10000.0
    val rLoad = parseR(rLoadInput, rLoadUnit)

    val calculation = CalculatorEngine.calculateVoltageDivider(vin, r1, r2, rLoad)

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Voltage Divider",
                subtitle = "Loaded & Unloaded Resistor Divider",
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite("calc_voltage_divider", "CALCULATOR", "Voltage Divider", "Loaded/Unloaded Divider", "calc_voltage_divider")
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
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Divider Formula",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = PhosphorCyan
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Vout = Vin × [R2 / (R1 + R2)]\nWith Load: Vout = Vin × [(R2 || RL) / (R1 + (R2 || RL))]",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }

            item {
                TechnicalInputField(
                    label = "Input Voltage (Vin)",
                    value = vinInput,
                    onValueChange = { vinInput = it },
                    unit = "V",
                    placeholder = "e.g. 12"
                )
            }

            item {
                TechnicalInputField(
                    label = "Top Resistor (R1)",
                    value = r1Input,
                    onValueChange = { r1Input = it },
                    unitOptions = listOf("Ω", "kΩ", "MΩ"),
                    selectedUnit = r1Unit,
                    onUnitSelected = { r1Unit = it },
                    placeholder = "e.g. 10"
                )
            }

            item {
                TechnicalInputField(
                    label = "Bottom Resistor (R2)",
                    value = r2Input,
                    onValueChange = { r2Input = it },
                    unitOptions = listOf("Ω", "kΩ", "MΩ"),
                    selectedUnit = r2Unit,
                    onUnitSelected = { r2Unit = it },
                    placeholder = "e.g. 10"
                )
            }

            item {
                TechnicalInputField(
                    label = "Load Resistance (RL - Optional)",
                    value = rLoadInput,
                    onValueChange = { rLoadInput = it },
                    unitOptions = listOf("Ω", "kΩ", "MΩ"),
                    selectedUnit = rLoadUnit,
                    onUnitSelected = { rLoadUnit = it },
                    placeholder = "Leave empty for unloaded"
                )
            }

            item {
                ResultPanel(
                    title = "Divided Output Voltage",
                    resultText = calculation.formattedResult,
                    stepsText = calculation.calculationSteps,
                    toolName = "Voltage Divider",
                    onReset = {
                        vinInput = "12"
                        r1Input = "10"
                        r2Input = "10"
                        rLoadInput = ""
                    }
                )
            }

            item {
                ActionButtonsRow(
                    onCalculate = {
                        viewModel.saveCalculation(
                            "calc_voltage_divider",
                            "Voltage Divider",
                            "Calculators",
                            "Vin=$vinInput V, R1=$r1Input$r1Unit, R2=$r2Input$r2Unit" + if (rLoadInput.isNotBlank()) ", RL=$rLoadInput$rLoadUnit" else "",
                            calculation.formattedResult,
                            calculation.calculationSteps
                        )
                    },
                    onReset = {
                        vinInput = "12"; r1Input = "10"; r2Input = "10"; rLoadInput = ""
                    },
                    calculateLabel = "Save to History"
                )
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}
