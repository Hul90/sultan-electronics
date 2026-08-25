package com.example.ui.screens.calculators

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.ui.theme.TechAmber
import com.example.ui.viewmodel.SultanViewModel
import com.example.utils.CalculatorEngine

@Composable
fun OhmsLawCalculatorScreen(
    viewModel: SultanViewModel,
    onBack: () -> Unit
) {
    var vInput by remember { mutableStateOf("12") }
    var vUnit by remember { mutableStateOf("V") }

    var iInput by remember { mutableStateOf("") }
    var iUnit by remember { mutableStateOf("mA") }

    var rInput by remember { mutableStateOf("1000") }
    var rUnit by remember { mutableStateOf("Ω") }

    var timeInput by remember { mutableStateOf("3600") }
    var timeUnit by remember { mutableStateOf("s") }

    val isFav = viewModel.isFavorite("calc_ohms_law")

    fun parseValue(raw: String, unit: String): Double? {
        val num = raw.toDoubleOrNull() ?: return null
        return when (unit) {
            "mV", "mA", "mΩ" -> num * 1e-3
            "µV", "µA", "µΩ" -> num * 1e-6
            "kV", "kA", "kΩ" -> num * 1e3
            "MV", "MA", "MΩ" -> num * 1e6
            "h (hours)" -> num * 3600.0
            "min (minutes)" -> num * 60.0
            else -> num
        }
    }

    val v = parseValue(vInput, vUnit)
    val i = parseValue(iInput, iUnit)
    val r = parseValue(rInput, rUnit)
    val t = parseValue(timeInput, timeUnit)

    val calculation = CalculatorEngine.calculateOhmsLaw(v, i, r, t)

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Ohm's Law & Power",
                subtitle = "Enter any 2 parameters (V, I, R)",
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite("calc_ohms_law", "CALCULATOR", "Ohm's Law", "V, I, R, P, Energy", "calc_ohms_law")
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
                            text = "Ohm's Law & Joule's Heating Equations",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TechAmber
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "V = I × R   |   P = V × I = I² × R = V² / R   |   Energy = P × t",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }

            // Input Fields
            item {
                TechnicalInputField(
                    label = "Voltage (V)",
                    value = vInput,
                    onValueChange = { vInput = it },
                    unitOptions = listOf("V", "mV", "kV"),
                    selectedUnit = vUnit,
                    onUnitSelected = { vUnit = it },
                    placeholder = "e.g. 12"
                )
            }

            item {
                TechnicalInputField(
                    label = "Current (I)",
                    value = iInput,
                    onValueChange = { iInput = it },
                    unitOptions = listOf("A", "mA", "µA"),
                    selectedUnit = iUnit,
                    onUnitSelected = { iUnit = it },
                    placeholder = "e.g. 20"
                )
            }

            item {
                TechnicalInputField(
                    label = "Resistance (R)",
                    value = rInput,
                    onValueChange = { rInput = it },
                    unitOptions = listOf("Ω", "kΩ", "MΩ"),
                    selectedUnit = rUnit,
                    onUnitSelected = { rUnit = it },
                    placeholder = "e.g. 1000"
                )
            }

            item {
                TechnicalInputField(
                    label = "Operating Time (Optional for Energy)",
                    value = timeInput,
                    onValueChange = { timeInput = it },
                    unitOptions = listOf("s", "min (minutes)", "h (hours)"),
                    selectedUnit = timeUnit,
                    onUnitSelected = { timeUnit = it },
                    placeholder = "e.g. 3600"
                )
            }

            // Result Panel
            item {
                ResultPanel(
                    title = "Ohm's Law Calculation Result",
                    resultText = calculation.formattedResult,
                    stepsText = calculation.calculationSteps,
                    toolName = "Ohm's Law",
                    onReset = {
                        vInput = ""
                        iInput = ""
                        rInput = ""
                        timeInput = ""
                    }
                )
            }

            item {
                ActionButtonsRow(
                    onCalculate = {
                        viewModel.saveCalculation(
                            "calc_ohms_law",
                            "Ohm's Law",
                            "Calculators",
                            "Inputs: V=$vInput$vUnit, I=$iInput$iUnit, R=$rInput$rUnit",
                            calculation.formattedResult,
                            calculation.calculationSteps
                        )
                    },
                    onReset = {
                        vInput = "12"
                        vUnit = "V"
                        iInput = ""
                        iUnit = "mA"
                        rInput = "1000"
                        rUnit = "Ω"
                        timeInput = "3600"
                    },
                    calculateLabel = "Save to History"
                )
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}
