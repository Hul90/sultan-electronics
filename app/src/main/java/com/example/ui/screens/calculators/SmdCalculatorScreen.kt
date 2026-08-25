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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
fun SmdCalculatorScreen(
    viewModel: SultanViewModel,
    onBack: () -> Unit
) {
    var smdCode by remember { mutableStateOf("103") }
    val result = CalculatorEngine.decodeSmdResistor(smdCode)
    val isFav = viewModel.isFavorite("calc_smd")

    val quickExamples = listOf("103", "472", "1001", "4702", "4R7", "R05", "01C", "68A", "0")

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "SMD Resistor Decoder",
                subtitle = "3-Digit, 4-Digit, R-Notation, EIA-96",
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite("calc_smd", "CALCULATOR", "SMD Resistor", "SMD Code Decoder", "calc_smd")
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
            // Visual SMD Chip Component
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        // SMT 1206 Chip Body
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .width(220.dp)
                                .height(70.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                        ) {
                            // Left silver termination
                            Box(
                                modifier = Modifier
                                    .width(28.dp)
                                    .height(70.dp)
                                    .background(Color(0xFFB0BEC5))
                            )
                            // Black ceramic center
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(70.dp)
                                    .background(Color(0xFF1E1E1E)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (smdCode.isBlank()) "---" else smdCode.uppercase(),
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    letterSpacing = 2.sp
                                )
                            }
                            // Right silver termination
                            Box(
                                modifier = Modifier
                                    .width(28.dp)
                                    .height(70.dp)
                                    .background(Color(0xFFB0BEC5))
                            )
                        }
                    }
                }
            }

            // Input Field
            item {
                TechnicalInputField(
                    label = "SMD Marking Code",
                    value = smdCode,
                    onValueChange = { smdCode = it.trim().uppercase() },
                    placeholder = "e.g. 103, 4702, 4R7, 01C",
                    keyboardType = KeyboardType.Text
                )
            }

            // Quick Example Chips
            item {
                Column {
                    Text(
                        text = "Quick Examples:",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(quickExamples) { example ->
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { smdCode = example }
                                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                color = if (smdCode == example) ElectricGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = example,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = if (smdCode == example) ElectricGreen else MaterialTheme.colorScheme.onSurface,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            // Result Panel
            item {
                ResultPanel(
                    title = "Decoded Resistance",
                    resultText = result.formattedResult,
                    stepsText = result.calculationSteps,
                    toolName = "SMD Resistor Decoder",
                    onReset = { smdCode = "" }
                )
            }

            // Save Action
            item {
                ActionButtonsRow(
                    onCalculate = {
                        viewModel.saveCalculation(
                            "calc_smd",
                            "SMD Resistor Decoder",
                            "Calculators",
                            "Code: $smdCode",
                            result.formattedResult,
                            result.calculationSteps
                        )
                    },
                    onReset = { smdCode = "103" },
                    calculateLabel = "Save to History"
                )
            }

            // Reference Info Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "SMD Marking Standards Guide",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = PhosphorCyan
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "• 3-Digit (±5%): First 2 digits are value, 3rd is power of 10. (103 = 10kΩ)\n" +
                                    "• 4-Digit (±1%): First 3 digits are value, 4th is power of 10. (4702 = 47kΩ)\n" +
                                    "• R-Notation: 'R' acts as decimal point for <100Ω. (4R7 = 4.7Ω, 0R22 = 0.22Ω)\n" +
                                    "• EIA-96 (±1%): 2-digit lookup code (01-96) followed by multiplier letter (A-F, X, Y, Z).",
                            style = MaterialTheme.typography.bodySmall.copy(
                                lineHeight = 18.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}
