package com.example.ui.screens.calculators

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
import androidx.compose.ui.text.font.FontFamily
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
import com.example.ui.theme.TechPurple
import com.example.ui.viewmodel.SultanViewModel
import com.example.utils.CalculatorEngine

// --- 1. BATTERY CALCULATOR SCREEN ---
@Composable
fun BatteryCalculatorScreen(viewModel: SultanViewModel, onBack: () -> Unit) {
    var chemIdx by remember { mutableIntStateOf(0) }
    var seriesCount by remember { mutableStateOf("3") }
    var parallelCount by remember { mutableStateOf("2") }
    var cellCapAh by remember { mutableStateOf("2.5") }
    var loadWatts by remember { mutableStateOf("50") }

    val s = seriesCount.toIntOrNull() ?: 3
    val p = parallelCount.toIntOrNull() ?: 2
    val cap = cellCapAh.toDoubleOrNull() ?: 2.5
    val loadW = loadWatts.toDoubleOrNull()

    val calculation = CalculatorEngine.calculateBatteryPack(chemIdx, s, p, cap, loadWatts = loadW)
    val isFav = viewModel.isFavorite("calc_battery")

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Battery Pack Designer",
                subtitle = "S/P Config, Wh Energy & Runtime",
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite("calc_battery", "CALCULATOR", "Battery Pack", "Battery Pack Runtime & Energy", "calc_battery")
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
                Column {
                    Text("Cell Chemistry:", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        itemsIndexed(CalculatorEngine.batteryChemistries) { idx, chem ->
                            val isSel = idx == chemIdx
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(1.dp, if (isSel) ElectricGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                                    .clickable { chemIdx = idx }
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = "${chem.name} (${chem.nominalV}V)",
                                    fontSize = 11.sp,
                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSel) ElectricGreen else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            item { TechnicalInputField(label = "Series Cells (S)", value = seriesCount, onValueChange = { seriesCount = it }, placeholder = "e.g. 3") }
            item { TechnicalInputField(label = "Parallel Cells (P)", value = parallelCount, onValueChange = { parallelCount = it }, placeholder = "e.g. 2") }
            item { TechnicalInputField(label = "Single Cell Capacity (Ah)", value = cellCapAh, onValueChange = { cellCapAh = it }, unit = "Ah", placeholder = "e.g. 2.5") }
            item { TechnicalInputField(label = "Connected Load (Watts)", value = loadWatts, onValueChange = { loadWatts = it }, unit = "W", placeholder = "e.g. 50") }

            item {
                ResultPanel(
                    title = "Battery Pack Specs",
                    resultText = calculation.formattedResult,
                    stepsText = calculation.calculationSteps,
                    toolName = "Battery Pack",
                    onReset = { seriesCount = "3"; parallelCount = "2"; cellCapAh = "2.5"; loadWatts = "50" }
                )
            }

            item {
                ActionButtonsRow(
                    onCalculate = {
                        viewModel.saveCalculation("calc_battery", "Battery Pack", "Calculators", "${s}S${p}P @ $cap Ah, Load: $loadWatts W", calculation.formattedResult, calculation.calculationSteps)
                    },
                    onReset = { seriesCount = "3"; parallelCount = "2"; cellCapAh = "2.5"; loadWatts = "50" },
                    calculateLabel = "Save to History"
                )
            }
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

// --- 2. WIRE & CABLE SCREEN ---
@Composable
fun WireCalculatorScreen(viewModel: SultanViewModel, onBack: () -> Unit) {
    var isAwg by remember { mutableStateOf(true) }
    var selectedAwg by remember { mutableIntStateOf(18) }
    var mm2Input by remember { mutableStateOf("1.5") }
    var isCopper by remember { mutableStateOf(true) }
    var lengthInput by remember { mutableStateOf("10") }
    var currentInput by remember { mutableStateOf("5") }
    var systemVoltsInput by remember { mutableStateOf("12") }

    val awgList = listOf(10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30)
    val mm2 = mm2Input.toDoubleOrNull() ?: 1.5
    val length = lengthInput.toDoubleOrNull() ?: 10.0
    val current = currentInput.toDoubleOrNull() ?: 5.0
    val sysV = systemVoltsInput.toDoubleOrNull() ?: 12.0

    val calculation = CalculatorEngine.calculateWireDrop(isAwg, selectedAwg, mm2, isCopper, length, current, sysV)
    val isFav = viewModel.isFavorite("calc_wire")

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Wire & Cable Voltage Drop",
                subtitle = "AWG / mm² Resistance & Loss",
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite("calc_wire", "CALCULATOR", "Wire Drop", "Wire & Cable Voltage Drop", "calc_wire")
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
                    selectedTabIndex = if (isAwg) 0 else 1,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = TechAmber
                ) {
                    Tab(selected = isAwg, onClick = { isAwg = true }, text = { Text("AWG Gauge", fontWeight = FontWeight.Bold) })
                    Tab(selected = !isAwg, onClick = { isAwg = false }, text = { Text("Metric (mm²)", fontWeight = FontWeight.Bold) })
                }
            }

            if (isAwg) {
                item {
                    Column {
                        Text("Select AWG Size:", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(6.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(awgList.size) { i ->
                                val awg = awgList[i]
                                val isSel = awg == selectedAwg
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, if (isSel) ElectricGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                    .clickable { selectedAwg = awg }
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = "$awg AWG",
                                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSel) ElectricGreen else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                item { TechnicalInputField(label = "Cross Section (mm²)", value = mm2Input, onValueChange = { mm2Input = it }, unit = "mm²", placeholder = "e.g. 1.5") }
            }

            item { TechnicalInputField(label = "One-Way Cable Length", value = lengthInput, onValueChange = { lengthInput = it }, unit = "meters", placeholder = "e.g. 10") }
            item { TechnicalInputField(label = "Current Draw", value = currentInput, onValueChange = { currentInput = it }, unit = "A", placeholder = "e.g. 5") }
            item { TechnicalInputField(label = "System Voltage", value = systemVoltsInput, onValueChange = { systemVoltsInput = it }, unit = "V", placeholder = "e.g. 12") }

            item {
                ResultPanel(
                    title = "Voltage Drop & Losses",
                    resultText = calculation.formattedResult,
                    stepsText = calculation.calculationSteps,
                    toolName = "Wire Drop",
                    onReset = { lengthInput = "10"; currentInput = "5"; systemVoltsInput = "12" }
                )
            }

            item {
                ActionButtonsRow(
                    onCalculate = {
                        viewModel.saveCalculation("calc_wire", "Wire Drop", "Calculators", "${if (isAwg) "$selectedAwg AWG" else "$mm2 mm²"}, $length m @ $current A", calculation.formattedResult, calculation.calculationSteps)
                    },
                    onReset = { lengthInput = "10"; currentInput = "5"; systemVoltsInput = "12" },
                    calculateLabel = "Save to History"
                )
            }
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

// --- 3. RF SUITE SCREEN ---
@Composable
fun RfSuiteScreen(viewModel: SultanViewModel, onBack: () -> Unit) {
    var modeIdx by remember { mutableIntStateOf(0) } // 0 = Antenna, 1 = SWR, 2 = Resonance
    var freqMhzInput by remember { mutableStateOf("433.92") }
    var forwardPowerInput by remember { mutableStateOf("10") }
    var reflectedPowerInput by remember { mutableStateOf("0.5") }

    val freq = freqMhzInput.toDoubleOrNull() ?: 433.92
    val fwd = forwardPowerInput.toDoubleOrNull() ?: 10.0
    val refl = reflectedPowerInput.toDoubleOrNull() ?: 0.5

    val calculation = when (modeIdx) {
        0 -> CalculatorEngine.calculateRfAntenna(freq)
        1 -> CalculatorEngine.calculateSwr(fwd, refl)
        else -> CalculatorEngine.calculateRfAntenna(freq)
    }

    val isFav = viewModel.isFavorite("calc_rf")

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "RF Engineering Suite",
                subtitle = "Antenna Length, Wavelength & VSWR",
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite("calc_rf", "CALCULATOR", "RF Suite", "Antenna & SWR Calculations", "calc_rf")
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
                    selectedTabIndex = modeIdx,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = PhosphorCyan
                ) {
                    Tab(selected = modeIdx == 0, onClick = { modeIdx = 0 }, text = { Text("Antenna Length", fontWeight = FontWeight.Bold) })
                    Tab(selected = modeIdx == 1, onClick = { modeIdx = 1 }, text = { Text("SWR & Loss", fontWeight = FontWeight.Bold) })
                }
            }

            if (modeIdx == 0) {
                item {
                    TechnicalInputField(
                        label = "Transmitter Frequency (MHz)",
                        value = freqMhzInput,
                        onValueChange = { freqMhzInput = it },
                        unit = "MHz",
                        placeholder = "e.g. 433.92"
                    )
                }
            } else {
                item { TechnicalInputField(label = "Forward Power (Pf)", value = forwardPowerInput, onValueChange = { forwardPowerInput = it }, unit = "Watts", placeholder = "e.g. 10") }
                item { TechnicalInputField(label = "Reflected Power (Pr)", value = reflectedPowerInput, onValueChange = { reflectedPowerInput = it }, unit = "Watts", placeholder = "e.g. 0.5") }
            }

            item {
                ResultPanel(
                    title = if (modeIdx == 0) "Antenna Dimensions" else "VSWR & Power Matching",
                    resultText = calculation.formattedResult,
                    stepsText = calculation.calculationSteps,
                    toolName = "RF Suite",
                    onReset = { freqMhzInput = "433.92"; forwardPowerInput = "10"; reflectedPowerInput = "0.5" }
                )
            }

            item {
                ActionButtonsRow(
                    onCalculate = {
                        viewModel.saveCalculation("calc_rf", "RF Suite", "Calculators", if (modeIdx == 0) "Freq: $freqMhzInput MHz" else "Pf=$forwardPowerInput W, Pr=$reflectedPowerInput W", calculation.formattedResult, calculation.calculationSteps)
                    },
                    onReset = { freqMhzInput = "433.92"; forwardPowerInput = "10"; reflectedPowerInput = "0.5" },
                    calculateLabel = "Save to History"
                )
            }
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

// --- 4. CAPACITOR CALCULATOR SCREEN ---
@Composable
fun CapacitorCalculatorScreen(viewModel: SultanViewModel, onBack: () -> Unit) {
    var modeIdx by remember { mutableIntStateOf(0) } // 0 = 3-digit EIA Code, 1 = RC Filter, 2 = Reactance Xc
    var capCodeInput by remember { mutableStateOf("104") }
    var rInput by remember { mutableStateOf("10") }
    var cInput by remember { mutableStateOf("100") }
    var freqInput by remember { mutableStateOf("1000") }

    val isFav = viewModel.isFavorite("calc_capacitor")

    val calculation = when (modeIdx) {
        0 -> CalculatorEngine.decodeCapacitorCode(capCodeInput)
        1 -> {
            val r = (rInput.toDoubleOrNull() ?: 10.0) * 1000.0
            val c = (cInput.toDoubleOrNull() ?: 100.0) * 1e-9
            CalculatorEngine.calculateRcTimeConstant(r, c)
        }
        else -> {
            val f = freqInput.toDoubleOrNull() ?: 1000.0
            val c = (cInput.toDoubleOrNull() ?: 100.0) * 1e-9
            CalculatorEngine.calculateCapacitiveReactance(f, c)
        }
    }

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Capacitor Engineering Tool",
                subtitle = "EIA Codes, RC Filters & Reactance",
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite("calc_capacitor", "CALCULATOR", "Capacitor Tool", "Capacitor Codes, RC, Reactance", "calc_capacitor")
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
                    selectedTabIndex = modeIdx,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = ElectricGreen
                ) {
                    Tab(selected = modeIdx == 0, onClick = { modeIdx = 0 }, text = { Text("EIA Code", fontWeight = FontWeight.Bold) })
                    Tab(selected = modeIdx == 1, onClick = { modeIdx = 1 }, text = { Text("RC Filter", fontWeight = FontWeight.Bold) })
                    Tab(selected = modeIdx == 2, onClick = { modeIdx = 2 }, text = { Text("Reactance (Xc)", fontWeight = FontWeight.Bold) })
                }
            }

            if (modeIdx == 0) {
                item { TechnicalInputField(label = "3-Digit Capacitor Code", value = capCodeInput, onValueChange = { capCodeInput = it }, placeholder = "e.g. 104, 473, 222") }
            } else if (modeIdx == 1) {
                item { TechnicalInputField(label = "Resistance (R)", value = rInput, onValueChange = { rInput = it }, unit = "kΩ", placeholder = "e.g. 10") }
                item { TechnicalInputField(label = "Capacitance (C)", value = cInput, onValueChange = { cInput = it }, unit = "nF", placeholder = "e.g. 100") }
            } else {
                item { TechnicalInputField(label = "AC Frequency (f)", value = freqInput, onValueChange = { freqInput = it }, unit = "Hz", placeholder = "e.g. 1000") }
                item { TechnicalInputField(label = "Capacitance (C)", value = cInput, onValueChange = { cInput = it }, unit = "nF", placeholder = "e.g. 100") }
            }

            item {
                ResultPanel(
                    title = "Capacitor Analysis",
                    resultText = calculation.formattedResult,
                    stepsText = calculation.calculationSteps,
                    toolName = "Capacitor Tool",
                    onReset = { capCodeInput = "104"; rInput = "10"; cInput = "100"; freqInput = "1000" }
                )
            }

            item {
                ActionButtonsRow(
                    onCalculate = {
                        viewModel.saveCalculation("calc_capacitor", "Capacitor Tool", "Calculators", "Mode: $modeIdx", calculation.formattedResult, calculation.calculationSteps)
                    },
                    onReset = { capCodeInput = "104"; rInput = "10"; cInput = "100"; freqInput = "1000" },
                    calculateLabel = "Save to History"
                )
            }
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

// --- 5. ZENER DIODE SCREEN ---
@Composable
fun ZenerCalculatorScreen(viewModel: SultanViewModel, onBack: () -> Unit) {
    var vinMinInput by remember { mutableStateOf("12") }
    var vinMaxInput by remember { mutableStateOf("15") }
    var zenerVInput by remember { mutableStateOf("5.1") }
    var loadCurrentInput by remember { mutableStateOf("20") }

    val vinMin = vinMinInput.toDoubleOrNull() ?: 12.0
    val vinMax = vinMaxInput.toDoubleOrNull() ?: 15.0
    val vz = zenerVInput.toDoubleOrNull() ?: 5.1
    val iLoad = (loadCurrentInput.toDoubleOrNull() ?: 20.0) * 1e-3

    val calculation = CalculatorEngine.calculateZenerRegulator(vinMin, vinMax, vz, iLoad)
    val isFav = viewModel.isFavorite("calc_zener")

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Zener Shunt Regulator",
                subtitle = "Series Resistor & Power Dissipation",
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite("calc_zener", "CALCULATOR", "Zener Regulator", "Zener Shunt Voltage Regulator", "calc_zener")
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
            item { TechnicalInputField(label = "Minimum Supply (Vin Min)", value = vinMinInput, onValueChange = { vinMinInput = it }, unit = "V", placeholder = "e.g. 12") }
            item { TechnicalInputField(label = "Maximum Supply (Vin Max)", value = vinMaxInput, onValueChange = { vinMaxInput = it }, unit = "V", placeholder = "e.g. 15") }
            item { TechnicalInputField(label = "Zener Voltage (Vz)", value = zenerVInput, onValueChange = { zenerVInput = it }, unit = "V", placeholder = "e.g. 5.1") }
            item { TechnicalInputField(label = "Max Load Current (IL)", value = loadCurrentInput, onValueChange = { loadCurrentInput = it }, unit = "mA", placeholder = "e.g. 20") }

            item {
                ResultPanel(
                    title = "Regulator Design Values",
                    resultText = calculation.formattedResult,
                    stepsText = calculation.calculationSteps,
                    toolName = "Zener Regulator",
                    onReset = { vinMinInput = "12"; vinMaxInput = "15"; zenerVInput = "5.1"; loadCurrentInput = "20" }
                )
            }

            item {
                ActionButtonsRow(
                    onCalculate = {
                        viewModel.saveCalculation("calc_zener", "Zener Regulator", "Calculators", "Vin: $vinMinInput-$vinMaxInput V, Vz: $zenerVInput V, IL: $loadCurrentInput mA", calculation.formattedResult, calculation.calculationSteps)
                    },
                    onReset = { vinMinInput = "12"; vinMaxInput = "15"; zenerVInput = "5.1"; loadCurrentInput = "20" },
                    calculateLabel = "Save to History"
                )
            }
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

// --- 6. INDUCTOR CALCULATOR SCREEN ---
@Composable
fun InductorCalculatorScreen(viewModel: SultanViewModel, onBack: () -> Unit) {
    var modeIdx by remember { mutableIntStateOf(0) } // 0 = Color Code, 1 = Series/Parallel, 2 = Reactance XL
    var b1 by remember { mutableIntStateOf(1) } // Brown
    var b2 by remember { mutableIntStateOf(0) } // Black
    var multIdx by remember { mutableIntStateOf(2) } // Red (*100) -> 1000 uH = 1 mH
    var tolIdx by remember { mutableIntStateOf(10) } // Gold 5%

    var l1Input by remember { mutableStateOf("10") }
    var l2Input by remember { mutableStateOf("22") }
    var isSeries by remember { mutableStateOf(true) }

    var freqInput by remember { mutableStateOf("1000") }
    var indMhInput by remember { mutableStateOf("10") }

    val isFav = viewModel.isFavorite("calc_inductor")

    val calculation = when (modeIdx) {
        0 -> CalculatorEngine.calculateInductorColorCode(b1, b2, multIdx, tolIdx)
        1 -> {
            val l1 = (l1Input.toDoubleOrNull() ?: 10.0) * 1e-3
            val l2 = (l2Input.toDoubleOrNull() ?: 22.0) * 1e-3
            CalculatorEngine.calculateSeriesParallelInductors(l1, l2, isSeries)
        }
        else -> {
            val f = freqInput.toDoubleOrNull() ?: 1000.0
            val l = (indMhInput.toDoubleOrNull() ?: 10.0) * 1e-3
            CalculatorEngine.calculateInductiveReactance(f, l)
        }
    }

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Inductor Engineering Tool",
                subtitle = "Color Code, Series/Parallel & Reactance",
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite("calc_inductor", "CALCULATOR", "Inductor Tool", "Color Code, Reactance & Combinations", "calc_inductor")
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
                    selectedTabIndex = modeIdx,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = TechAmber
                ) {
                    Tab(selected = modeIdx == 0, onClick = { modeIdx = 0 }, text = { Text("Color Bands", fontWeight = FontWeight.Bold) })
                    Tab(selected = modeIdx == 1, onClick = { modeIdx = 1 }, text = { Text("Combinations", fontWeight = FontWeight.Bold) })
                    Tab(selected = modeIdx == 2, onClick = { modeIdx = 2 }, text = { Text("Reactance (XL)", fontWeight = FontWeight.Bold) })
                }
            }

            when (modeIdx) {
                0 -> {
                    item {
                        Column {
                            Text("Band 1 (1st Digit): ${CalculatorEngine.resistorColors[b1].name}", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                            Spacer(modifier = Modifier.height(4.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                itemsIndexed(CalculatorEngine.resistorColors.take(10)) { idx, col ->
                                    Surface(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .border(1.dp, if (idx == b1) ElectricGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                            .clickable { b1 = idx }
                                            .padding(horizontal = 8.dp, vertical = 6.dp),
                                        color = if (idx == b1) ElectricGreen.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                                    ) {
                                        Text("${col.digit}: ${col.name}", fontSize = 11.sp, fontWeight = if (idx == b1) FontWeight.Bold else FontWeight.Normal)
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Column {
                            Text("Band 2 (2nd Digit): ${CalculatorEngine.resistorColors[b2].name}", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                            Spacer(modifier = Modifier.height(4.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                itemsIndexed(CalculatorEngine.resistorColors.take(10)) { idx, col ->
                                    Surface(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .border(1.dp, if (idx == b2) ElectricGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                            .clickable { b2 = idx }
                                            .padding(horizontal = 8.dp, vertical = 6.dp),
                                        color = if (idx == b2) ElectricGreen.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                                    ) {
                                        Text("${col.digit}: ${col.name}", fontSize = 11.sp, fontWeight = if (idx == b2) FontWeight.Bold else FontWeight.Normal)
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Column {
                            Text("Band 3 (Multiplier): ${CalculatorEngine.resistorColors[multIdx].name} (×${CalculatorEngine.resistorColors[multIdx].multiplier} µH)", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                            Spacer(modifier = Modifier.height(4.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                itemsIndexed(CalculatorEngine.resistorColors) { idx, col ->
                                    Surface(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .border(1.dp, if (idx == multIdx) TechAmber else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                            .clickable { multIdx = idx }
                                            .padding(horizontal = 8.dp, vertical = 6.dp),
                                        color = if (idx == multIdx) TechAmber.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                                    ) {
                                        Text(col.name, fontSize = 11.sp, fontWeight = if (idx == multIdx) FontWeight.Bold else FontWeight.Normal)
                                    }
                                }
                            }
                        }
                    }
                }
                1 -> {
                    item {
                        TabRow(
                            selectedTabIndex = if (isSeries) 0 else 1,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = PhosphorCyan
                        ) {
                            Tab(selected = isSeries, onClick = { isSeries = true }, text = { Text("Series (L1 + L2)") })
                            Tab(selected = !isSeries, onClick = { isSeries = false }, text = { Text("Parallel (L1 || L2)") })
                        }
                    }
                    item { TechnicalInputField(label = "Inductor L1 (mH)", value = l1Input, onValueChange = { l1Input = it }, unit = "mH", placeholder = "e.g. 10") }
                    item { TechnicalInputField(label = "Inductor L2 (mH)", value = l2Input, onValueChange = { l2Input = it }, unit = "mH", placeholder = "e.g. 22") }
                }
                else -> {
                    item { TechnicalInputField(label = "AC Frequency (f)", value = freqInput, onValueChange = { freqInput = it }, unit = "Hz", placeholder = "e.g. 1000") }
                    item { TechnicalInputField(label = "Inductance (L)", value = indMhInput, onValueChange = { indMhInput = it }, unit = "mH", placeholder = "e.g. 10") }
                }
            }

            item {
                ResultPanel(
                    title = "Inductance Analysis",
                    resultText = calculation.formattedResult,
                    stepsText = calculation.calculationSteps,
                    toolName = "Inductor Tool",
                    onReset = { l1Input = "10"; l2Input = "22"; freqInput = "1000"; indMhInput = "10" }
                )
            }

            item {
                ActionButtonsRow(
                    onCalculate = {
                        viewModel.saveCalculation("calc_inductor", "Inductor Tool", "Calculators", "Mode: $modeIdx", calculation.formattedResult, calculation.calculationSteps)
                    },
                    onReset = { l1Input = "10"; l2Input = "22"; freqInput = "1000"; indMhInput = "10" },
                    calculateLabel = "Save to History"
                )
            }
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

// --- 7. AC POWER (SINGLE & 3-PHASE) SCREEN ---
@Composable
fun AcPowerCalculatorScreen(viewModel: SultanViewModel, onBack: () -> Unit) {
    var isThreePhase by remember { mutableStateOf(false) }
    var voltsInput by remember { mutableStateOf("230") }
    var currentInput by remember { mutableStateOf("10") }
    var pfInput by remember { mutableStateOf("0.85") }

    val v = voltsInput.toDoubleOrNull() ?: 230.0
    val i = currentInput.toDoubleOrNull() ?: 10.0
    val pf = pfInput.toDoubleOrNull() ?: 0.85

    val calculation = CalculatorEngine.calculateAcPower(v, i, pf, isThreePhase)
    val isFav = viewModel.isFavorite("calc_ac_power")

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "AC Power Analysis",
                subtitle = "Watts, VA, VAR & Power Factor",
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite("calc_ac_power", "CALCULATOR", "AC Power", "Single & 3-Phase AC Power Analyzer", "calc_ac_power")
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
                    selectedTabIndex = if (isThreePhase) 1 else 0,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = TechAmber
                ) {
                    Tab(selected = !isThreePhase, onClick = { isThreePhase = false; if (voltsInput == "400") voltsInput = "230" }, text = { Text("Single Phase", fontWeight = FontWeight.Bold) })
                    Tab(selected = isThreePhase, onClick = { isThreePhase = true; if (voltsInput == "230") voltsInput = "400" }, text = { Text("3-Phase Balanced", fontWeight = FontWeight.Bold) })
                }
            }

            item { TechnicalInputField(label = if (isThreePhase) "Line-to-Line Voltage (VL-L)" else "AC Voltage (RMS)", value = voltsInput, onValueChange = { voltsInput = it }, unit = "V", placeholder = "e.g. 230") }
            item { TechnicalInputField(label = "Load Current (I_RMS)", value = currentInput, onValueChange = { currentInput = it }, unit = "A", placeholder = "e.g. 10") }
            item { TechnicalInputField(label = "Power Factor (cos φ)", value = pfInput, onValueChange = { pfInput = it }, unit = "0.0 - 1.0", placeholder = "e.g. 0.85") }

            item {
                ResultPanel(
                    title = "AC Power Breakdown",
                    resultText = calculation.formattedResult,
                    stepsText = calculation.calculationSteps,
                    toolName = "AC Power",
                    onReset = { voltsInput = if (isThreePhase) "400" else "230"; currentInput = "10"; pfInput = "0.85" }
                )
            }

            item {
                ActionButtonsRow(
                    onCalculate = {
                        viewModel.saveCalculation("calc_ac_power", "AC Power", "Calculators", "${if (isThreePhase) "3-Phase" else "1-Phase"} $voltsInput V, $currentInput A @ PF $pfInput", calculation.formattedResult, calculation.calculationSteps)
                    },
                    onReset = { voltsInput = if (isThreePhase) "400" else "230"; currentInput = "10"; pfInput = "0.85" },
                    calculateLabel = "Save to History"
                )
            }
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

// --- 8. UNIT CONVERTER SCREEN ---
@Composable
fun UnitConverterScreen(viewModel: SultanViewModel, onBack: () -> Unit) {
    val categories = listOf("Voltage", "Current", "Resistance", "Capacitance", "Frequency", "Power & dBm")
    var catIdx by remember { mutableIntStateOf(0) }
    var valueInput by remember { mutableStateOf("100") }
    var fromIdx by remember { mutableIntStateOf(3) } // e.g. mV
    var toIdx by remember { mutableIntStateOf(4) }   // e.g. V

    val currentCategory = categories[catIdx]
    val unitsList = when (currentCategory) {
        "Voltage" -> listOf("pV", "nV", "µV", "mV", "V", "kV", "MV")
        "Current" -> listOf("pA", "nA", "µA", "mA", "A", "kA")
        "Resistance" -> listOf("mΩ", "Ω", "kΩ", "MΩ", "GΩ")
        "Capacitance" -> listOf("pF", "nF", "µF", "mF", "F")
        "Frequency" -> listOf("Hz", "kHz", "MHz", "GHz", "THz")
        "Power & dBm" -> listOf("µW", "mW", "W", "kW", "dBm")
        else -> listOf("Base")
    }

    val valueNum = valueInput.toDoubleOrNull() ?: 100.0
    val safeFrom = fromIdx.coerceIn(0, unitsList.size - 1)
    val safeTo = toIdx.coerceIn(0, unitsList.size - 1)
    val calculation = CalculatorEngine.convertUnit(valueNum, currentCategory, safeFrom, safeTo)

    val isFav = viewModel.isFavorite("calc_unit_conv")

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Engineering Unit Converter",
                subtitle = "Metric Prefixes & Electronic Quantities",
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite("calc_unit_conv", "CALCULATOR", "Unit Converter", "Electronics Units & dBm Converter", "calc_unit_conv")
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
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    itemsIndexed(categories) { idx, cat ->
                        val isSel = idx == catIdx
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .border(1.dp, if (isSel) ElectricGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                                .clickable {
                                    catIdx = idx
                                    fromIdx = 0
                                    toIdx = 1
                                }
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            color = if (isSel) ElectricGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = cat,
                                fontSize = 11.sp,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSel) ElectricGreen else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            item { TechnicalInputField(label = "Input Quantity", value = valueInput, onValueChange = { valueInput = it }, placeholder = "e.g. 100") }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("From Unit:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            itemsIndexed(unitsList) { idx, u ->
                                val isSel = idx == safeFrom
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .border(1.dp, if (isSel) ElectricGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                        .clickable { fromIdx = idx }
                                        .padding(horizontal = 8.dp, vertical = 6.dp),
                                    color = if (isSel) ElectricGreen.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(u, fontSize = 11.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal)
                                }
                            }
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text("To Unit:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            itemsIndexed(unitsList) { idx, u ->
                                val isSel = idx == safeTo
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .border(1.dp, if (isSel) PhosphorCyan else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                        .clickable { toIdx = idx }
                                        .padding(horizontal = 8.dp, vertical = 6.dp),
                                    color = if (isSel) PhosphorCyan.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(u, fontSize = 11.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal)
                                }
                            }
                        }
                    }
                }
            }

            item {
                ResultPanel(
                    title = "Conversion Result",
                    resultText = calculation.formattedResult,
                    stepsText = calculation.calculationSteps,
                    toolName = "Unit Converter",
                    onReset = { valueInput = "100" }
                )
            }

            item {
                ActionButtonsRow(
                    onCalculate = {
                        viewModel.saveCalculation("calc_unit_conv", "Unit Converter", "Calculators", "$valueInput ${unitsList[safeFrom]} to ${unitsList[safeTo]}", calculation.formattedResult, calculation.calculationSteps)
                    },
                    onReset = { valueInput = "100" },
                    calculateLabel = "Save to History"
                )
            }
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}
