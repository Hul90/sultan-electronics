package com.example.ui.screens.calculators

import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ActionButtonsRow
import com.example.ui.components.ResultPanel
import com.example.ui.components.SultanTopAppBar
import com.example.ui.components.TechnicalInputField
import com.example.ui.theme.ElectricGreen
import com.example.ui.theme.PhosphorCyan
import com.example.ui.theme.TechPurple
import com.example.ui.viewmodel.SultanViewModel
import com.example.utils.CalculatorEngine

@Composable
fun Timer555CalculatorScreen(
    viewModel: SultanViewModel,
    onBack: () -> Unit
) {
    var isAstable by remember { mutableStateOf(true) }

    // Astable values
    var r1Input by remember { mutableStateOf("1") }
    var r1Unit by remember { mutableStateOf("kΩ") }
    var r2Input by remember { mutableStateOf("10") }
    var r2Unit by remember { mutableStateOf("kΩ") }
    var cInput by remember { mutableStateOf("10") }
    var cUnit by remember { mutableStateOf("µF") }

    val isFav = viewModel.isFavorite("calc_555")

    fun parseR(rStr: String, unit: String): Double {
        val r = rStr.toDoubleOrNull() ?: 1.0
        return when (unit) {
            "kΩ" -> r * 1000.0
            "MΩ" -> r * 1_000_000.0
            else -> r
        }
    }

    fun parseC(cStr: String, unit: String): Double {
        val c = cStr.toDoubleOrNull() ?: 1.0
        return when (unit) {
            "µF" -> c * 1e-6
            "nF" -> c * 1e-9
            "pF" -> c * 1e-12
            else -> c
        }
    }

    val r1 = parseR(r1Input, r1Unit)
    val r2 = parseR(r2Input, r2Unit)
    val cap = parseC(cInput, cUnit)

    val calculation = if (isAstable) {
        CalculatorEngine.calculate555Astable(r1, r2, cap)
    } else {
        CalculatorEngine.calculate555Monostable(r1, cap)
    }

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "NE555 Timer Calculator",
                subtitle = if (isAstable) "Astable Multivibrator (Oscillator)" else "Monostable (One-Shot Pulse)",
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite("calc_555", "CALCULATOR", "555 Timer", "Astable & Monostable Modes", "calc_555")
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
                TabRow(
                    selectedTabIndex = if (isAstable) 0 else 1,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = TechPurple
                ) {
                    Tab(
                        selected = isAstable,
                        onClick = { isAstable = true },
                        text = { Text("Astable (Oscillator)", fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = !isAstable,
                        onClick = { isAstable = false },
                        text = { Text("Monostable (One-Shot)", fontWeight = FontWeight.Bold) }
                    )
                }
            }

            // Live Square Waveform Preview Canvas
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height
                        val highY = h * 0.25f
                        val lowY = h * 0.75f

                        // Grid lines
                        for (i in 1..4) {
                            drawLine(
                                color = Color.DarkGray.copy(alpha = 0.3f),
                                start = Offset(0f, h * i / 5),
                                end = Offset(w, h * i / 5),
                                strokeWidth = 1f
                            )
                        }

                        // Square wave path
                        val path = Path()
                        val periodPx = w / 4f
                        val dutyRatio = if (isAstable) ((r1 + r2) / (r1 + 2.0 * r2)).coerceIn(0.1, 0.9).toFloat() else 0.5f

                        var currentX = 0f
                        path.moveTo(0f, lowY)

                        while (currentX < w) {
                            path.lineTo(currentX, highY)
                            currentX += periodPx * dutyRatio
                            path.lineTo(currentX, highY)
                            path.lineTo(currentX, lowY)
                            currentX += periodPx * (1f - dutyRatio)
                            path.lineTo(currentX, lowY)
                        }

                        drawPath(
                            path = path,
                            color = PhosphorCyan,
                            style = Stroke(width = 4f)
                        )
                    }
                }
            }

            // Inputs
            item {
                TechnicalInputField(
                    label = if (isAstable) "R1 (Vcc to Pin 7)" else "Timing Resistor R",
                    value = r1Input,
                    onValueChange = { r1Input = it },
                    unitOptions = listOf("Ω", "kΩ", "MΩ"),
                    selectedUnit = r1Unit,
                    onUnitSelected = { r1Unit = it },
                    placeholder = "e.g. 1"
                )
            }

            if (isAstable) {
                item {
                    TechnicalInputField(
                        label = "R2 (Pin 7 to Pin 6/2)",
                        value = r2Input,
                        onValueChange = { r2Input = it },
                        unitOptions = listOf("Ω", "kΩ", "MΩ"),
                        selectedUnit = r2Unit,
                        onUnitSelected = { r2Unit = it },
                        placeholder = "e.g. 10"
                    )
                }
            }

            item {
                TechnicalInputField(
                    label = "Timing Capacitor C",
                    value = cInput,
                    onValueChange = { cInput = it },
                    unitOptions = listOf("pF", "nF", "µF"),
                    selectedUnit = cUnit,
                    onUnitSelected = { cUnit = it },
                    placeholder = "e.g. 10"
                )
            }

            // Result
            item {
                ResultPanel(
                    title = "Timing Analysis Output",
                    resultText = calculation.formattedResult,
                    stepsText = calculation.calculationSteps,
                    toolName = "NE555 Timer",
                    onReset = {
                        r1Input = "1"
                        r1Unit = "kΩ"
                        r2Input = "10"
                        r2Unit = "kΩ"
                        cInput = "10"
                        cUnit = "µF"
                    }
                )
            }

            item {
                ActionButtonsRow(
                    onCalculate = {
                        viewModel.saveCalculation(
                            "calc_555",
                            "NE555 Timer",
                            "Calculators",
                            if (isAstable) "Astable: R1=$r1Input$r1Unit, R2=$r2Input$r2Unit, C=$cInput$cUnit" else "Monostable: R=$r1Input$r1Unit, C=$cInput$cUnit",
                            calculation.formattedResult,
                            calculation.calculationSteps
                        )
                    },
                    onReset = {
                        r1Input = "1"; r2Input = "10"; cInput = "10"
                    },
                    calculateLabel = "Save to History"
                )
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}
