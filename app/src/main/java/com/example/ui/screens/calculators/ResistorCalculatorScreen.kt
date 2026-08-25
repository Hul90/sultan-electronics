package com.example.ui.screens.calculators

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ActionButtonsRow
import com.example.ui.components.ResultPanel
import com.example.ui.components.SultanTopAppBar
import com.example.ui.components.TechnicalInputField
import com.example.ui.theme.ElectricGreen
import com.example.ui.theme.ElectricGreenDark
import com.example.ui.theme.PhosphorCyan
import com.example.ui.viewmodel.SultanViewModel
import com.example.utils.CalculatorEngine

@Composable
fun ResistorCalculatorScreen(
    viewModel: SultanViewModel,
    onBack: () -> Unit
) {
    var bandMode by remember { mutableIntStateOf(4) } // 4, 5, or reverse (0)
    var isReverseMode by remember { mutableStateOf(false) }

    // 4-Band State (Defaults: Brown 1, Black 0, Red x100, Gold 5% -> 1.0 kΩ ±5%)
    var b1 by remember { mutableIntStateOf(1) } // Brown
    var b2 by remember { mutableIntStateOf(0) } // Black
    var b3 by remember { mutableIntStateOf(0) } // Black (for 5-band)
    var multIdx by remember { mutableIntStateOf(2) } // Red
    var tolIdx by remember { mutableIntStateOf(10) } // Gold

    // Reverse State
    var targetResistanceInput by remember { mutableStateOf("1000") }
    var targetUnit by remember { mutableStateOf("Ω") }

    val isFav = viewModel.isFavorite("calc_resistor")

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Resistor Color Code",
                subtitle = if (isReverseMode) "Resistance to Color Bands" else "$bandMode-Band Color to Resistance",
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite(
                        "calc_resistor",
                        "CALCULATOR",
                        "Resistor Color Code",
                        "4/5-Band & Reverse Code",
                        "calc_resistor"
                    )
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
            // Mode Selector Tabs
            item {
                TabRow(
                    selectedTabIndex = if (isReverseMode) 2 else if (bandMode == 4) 0 else 1,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = ElectricGreen
                ) {
                    Tab(
                        selected = !isReverseMode && bandMode == 4,
                        onClick = { isReverseMode = false; bandMode = 4 },
                        text = { Text("4-Band", fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = !isReverseMode && bandMode == 5,
                        onClick = { isReverseMode = false; bandMode = 5 },
                        text = { Text("5-Band", fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = isReverseMode,
                        onClick = { isReverseMode = true },
                        text = { Text("Reverse (Ω→Bands)", fontWeight = FontWeight.Bold) }
                    )
                }
            }

            if (!isReverseMode) {
                // Forward Mode: Color Bands Selector
                val calculation = if (bandMode == 4) {
                    CalculatorEngine.calculateResistor4Band(b1, b2, multIdx, tolIdx)
                } else {
                    CalculatorEngine.calculateResistor5Band(b1, b2, b3, multIdx, tolIdx)
                }

                // Interactive Visual Resistor Graphic
                item {
                    ResistorVisualGraphic(
                        bandColors = if (bandMode == 4) {
                            listOf(
                                CalculatorEngine.resistorColors[b1].hexColor,
                                CalculatorEngine.resistorColors[b2].hexColor,
                                CalculatorEngine.resistorColors[multIdx].hexColor,
                                CalculatorEngine.resistorColors[tolIdx].hexColor
                            )
                        } else {
                            listOf(
                                CalculatorEngine.resistorColors[b1].hexColor,
                                CalculatorEngine.resistorColors[b2].hexColor,
                                CalculatorEngine.resistorColors[b3].hexColor,
                                CalculatorEngine.resistorColors[multIdx].hexColor,
                                CalculatorEngine.resistorColors[tolIdx].hexColor
                            )
                        }
                    )
                }

                // Result Box
                item {
                    ResultPanel(
                        title = "Calculated Resistance",
                        resultText = calculation.formattedResult,
                        stepsText = calculation.calculationSteps,
                        toolName = "Resistor $bandMode-Band",
                        onReset = {
                            b1 = 1
                            b2 = 0
                            b3 = 0
                            multIdx = 2
                            tolIdx = 10
                        }
                    )
                }

                // Color Band Pickers
                item {
                    ColorBandSelector(
                        title = "1st Digit (Band 1)",
                        selectedIndex = b1,
                        options = CalculatorEngine.resistorColors.take(10),
                        onSelect = { b1 = it }
                    )
                }

                item {
                    ColorBandSelector(
                        title = "2nd Digit (Band 2)",
                        selectedIndex = b2,
                        options = CalculatorEngine.resistorColors.take(10),
                        onSelect = { b2 = it }
                    )
                }

                if (bandMode == 5) {
                    item {
                        ColorBandSelector(
                            title = "3rd Digit (Band 3)",
                            selectedIndex = b3,
                            options = CalculatorEngine.resistorColors.take(10),
                            onSelect = { b3 = it }
                        )
                    }
                }

                item {
                    ColorBandSelector(
                        title = "Multiplier (Band ${if (bandMode == 4) 3 else 4})",
                        selectedIndex = multIdx,
                        options = CalculatorEngine.resistorColors,
                        onSelect = { multIdx = it }
                    )
                }

                item {
                    ColorBandSelector(
                        title = "Tolerance (Band ${if (bandMode == 4) 4 else 5})",
                        selectedIndex = tolIdx,
                        options = CalculatorEngine.resistorColors.filter { it.tolerancePercent != null },
                        onSelect = { tolIdx = it }
                    )
                }

                item {
                    ActionButtonsRow(
                        onCalculate = {
                            viewModel.saveCalculation(
                                "calc_resistor",
                                "Resistor $bandMode-Band",
                                "Calculators",
                                "Bands: [${CalculatorEngine.resistorColors[b1].name}, ${CalculatorEngine.resistorColors[b2].name}, ...]",
                                calculation.formattedResult,
                                calculation.calculationSteps
                            )
                        },
                        onReset = {
                            b1 = 1; b2 = 0; b3 = 0; multIdx = 2; tolIdx = 10
                        },
                        calculateLabel = "Save to History"
                    )
                }
            } else {
                // Reverse Mode: Ohms to Color Bands
                val multiplier = when (targetUnit) {
                    "kΩ" -> 1_000.0
                    "MΩ" -> 1_000_000.0
                    else -> 1.0
                }
                val enteredOhms = (targetResistanceInput.toDoubleOrNull() ?: 0.0) * multiplier
                val bands4 = CalculatorEngine.reverseResistorColor(enteredOhms, is5Band = false)
                val bands5 = CalculatorEngine.reverseResistorColor(enteredOhms, is5Band = true)

                item {
                    TechnicalInputField(
                        label = "Target Resistance",
                        value = targetResistanceInput,
                        onValueChange = { targetResistanceInput = it },
                        unitOptions = listOf("Ω", "kΩ", "MΩ"),
                        selectedUnit = targetUnit,
                        onUnitSelected = { targetUnit = it },
                        placeholder = "e.g. 4700"
                    )
                }

                item {
                    Text(
                        text = "4-Band Representation (5% Standard)",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = ElectricGreen
                        )
                    )
                }

                item {
                    ResistorVisualGraphic(
                        bandColors = listOf(
                            CalculatorEngine.resistorColors[bands4[0]].hexColor,
                            CalculatorEngine.resistorColors[bands4[1]].hexColor,
                            CalculatorEngine.resistorColors[bands4[2]].hexColor,
                            CalculatorEngine.resistorColors[bands4[3]].hexColor
                        )
                    )
                }

                item {
                    BandNamesRow(
                        bands = listOf(
                            CalculatorEngine.resistorColors[bands4[0]],
                            CalculatorEngine.resistorColors[bands4[1]],
                            CalculatorEngine.resistorColors[bands4[2]],
                            CalculatorEngine.resistorColors[bands4[3]]
                        )
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "5-Band Precision Representation (1%)",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = PhosphorCyan
                        )
                    )
                }

                item {
                    ResistorVisualGraphic(
                        bandColors = listOf(
                            CalculatorEngine.resistorColors[bands5[0]].hexColor,
                            CalculatorEngine.resistorColors[bands5[1]].hexColor,
                            CalculatorEngine.resistorColors[bands5[2]].hexColor,
                            CalculatorEngine.resistorColors[bands5[3]].hexColor,
                            CalculatorEngine.resistorColors[bands5[4]].hexColor
                        )
                    )
                }

                item {
                    BandNamesRow(
                        bands = listOf(
                            CalculatorEngine.resistorColors[bands5[0]],
                            CalculatorEngine.resistorColors[bands5[1]],
                            CalculatorEngine.resistorColors[bands5[2]],
                            CalculatorEngine.resistorColors[bands5[3]],
                            CalculatorEngine.resistorColors[bands5[4]]
                        )
                    )
                }

                item {
                    ActionButtonsRow(
                        onCalculate = {
                            viewModel.saveCalculation(
                                "calc_resistor",
                                "Reverse Resistor",
                                "Calculators",
                                "Input: $targetResistanceInput $targetUnit",
                                "4-Band: ${bands4.map { CalculatorEngine.resistorColors[it].name }.joinToString(" - ")}"
                            )
                        },
                        onReset = { targetResistanceInput = "1000"; targetUnit = "Ω" },
                        calculateLabel = "Save to History"
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

@Composable
fun ResistorVisualGraphic(bandColors: List<Long>) {
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

            // Leads
            val leadY = h / 2
            drawLine(
                color = Color(0xFF9E9E9E),
                start = Offset(20f, leadY),
                end = Offset(w - 20f, leadY),
                strokeWidth = 10f
            )

            // Resistor Body
            val bodyLeft = w * 0.2f
            val bodyRight = w * 0.8f
            val bodyTop = h * 0.22f
            val bodyHeight = h * 0.56f
            val bodyWidth = bodyRight - bodyLeft

            drawRoundRect(
                color = Color(0xFFD7CCC8), // Ceramic beige body
                topLeft = Offset(bodyLeft, bodyTop),
                size = Size(bodyWidth, bodyHeight),
                cornerRadius = CornerRadius(20f, 20f)
            )

            // Color stripes
            val bandCount = bandColors.size
            val spacing = bodyWidth / (bandCount + 1)
            bandColors.forEachIndexed { i, colorHex ->
                val bandX = bodyLeft + spacing * (i + 1) - 10f
                drawRect(
                    color = Color(colorHex),
                    topLeft = Offset(bandX, bodyTop),
                    size = Size(18f, bodyHeight)
                )
            }
        }
    }
}

@Composable
fun ColorBandSelector(
    title: String,
    selectedIndex: Int,
    options: List<CalculatorEngine.ResistorBandColor>,
    onSelect: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(options) { index, color ->
                val actualIndex = CalculatorEngine.resistorColors.indexOf(color)
                val isSelected = actualIndex == selectedIndex

                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) ElectricGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { onSelect(actualIndex) }
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(Color(color.hexColor))
                                .border(0.5.dp, Color.Gray, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = color.name,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) ElectricGreen else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BandNamesRow(bands: List<CalculatorEngine.ResistorBandColor>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        bands.forEach { b ->
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp)),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Row(
                    modifier = Modifier.padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color(b.hexColor))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = b.name,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
