package com.example.ui.screens.tools

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SultanTopAppBar
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.ElectricGreen
import com.example.ui.theme.ElectricGreenDark
import com.example.ui.theme.NeonRed
import com.example.ui.theme.PhosphorCyan
import com.example.ui.theme.TechAmber
import com.example.ui.viewmodel.SultanViewModel
import com.example.utils.CalculatorEngine
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun OscilloscopeScreen(
    viewModel: SultanViewModel,
    onBack: () -> Unit
) {
    var isRunning by remember { mutableStateOf(true) }
    var selectedChannel by remember { mutableIntStateOf(0) } // 0: CH1, 1: CH2, 2: MATH/XY

    // Channel 1 Settings
    var ch1Waveform by remember { mutableStateOf("Sine") } // Sine, Square, Triangle, Saw
    var ch1FreqHz by remember { mutableFloatStateOf(1000f) }
    var ch1AmpVolts by remember { mutableFloatStateOf(2.5f) }
    var ch1OffsetVolts by remember { mutableFloatStateOf(0f) }

    // Channel 2 Settings
    var ch2Enabled by remember { mutableStateOf(true) }
    var ch2Waveform by remember { mutableStateOf("Sine") }
    var ch2FreqHz by remember { mutableFloatStateOf(1000f) }
    var ch2AmpVolts by remember { mutableFloatStateOf(2.0f) }
    var ch2PhaseDeg by remember { mutableFloatStateOf(90f) } // 90° for Lissajous circle

    // Oscilloscope Timebase & Volts/Div
    var timeDivIdx by remember { mutableIntStateOf(2) } // e.g. 1ms/div
    val timeDivLabels = listOf("100µs/div", "500µs/div", "1ms/div", "5ms/div", "10ms/div")
    val timeDivSeconds = listOf(0.0001, 0.0005, 0.001, 0.005, 0.01)

    var voltsDivIdx by remember { mutableIntStateOf(1) } // 1V/div
    val voltsDivLabels = listOf("0.5V/div", "1.0V/div", "2.0V/div", "5.0V/div")
    val voltsDivValues = listOf(0.5f, 1.0f, 2.0f, 5.0f)

    var isLissajousXy by remember { mutableStateOf(false) }

    // Dynamic animation ticker
    var phaseTick by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(33) // ~30 FPS animation
            phaseTick += 0.15f
        }
    }

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Virtual Oscilloscope",
                subtitle = "Dual-Channel Signal Analyzer (PRO SIMULATION)",
                onBackClick = onBack,
                isFavorite = viewModel.isFavorite("tool_oscilloscope"),
                onFavoriteToggle = {
                    viewModel.toggleFavorite("tool_oscilloscope", "TOOL", "Oscilloscope", "Dual-Channel Virtual Scope", "tool_oscilloscope")
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
            // PRO Simulation Warning Badge
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    color = Color.Black.copy(alpha = 0.4f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ElectricGreen.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(ElectricGreen)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "HIGH-PRECISION VIRTUAL SIGNAL & DSP LAB (SIMULATION)",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = ElectricGreen,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            // High-Tech CRT Phosphor Oscilloscope Screen
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .border(2.dp, Color(0xFF1B382B), RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF04100C))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height
                            val midX = w / 2f
                            val midY = h / 2f

                            // Draw Scope Reticle Grid (10 divisions horizontally, 8 vertically)
                            val numHDivs = 10
                            val numVDivs = 8
                            val divW = w / numHDivs
                            val divH = h / numVDivs

                            for (i in 0..numHDivs) {
                                val x = i * divW
                                drawLine(
                                    color = if (i == 5) Color(0xFF0D4734) else Color(0xFF07241A),
                                    start = Offset(x, 0f),
                                    end = Offset(x, h),
                                    strokeWidth = if (i == 5) 2f else 1f
                                )
                            }

                            for (j in 0..numVDivs) {
                                val y = j * divH
                                drawLine(
                                    color = if (j == 4) Color(0xFF0D4734) else Color(0xFF07241A),
                                    start = Offset(0f, y),
                                    end = Offset(w, y),
                                    strokeWidth = if (j == 4) 2f else 1f
                                )
                            }

                            val curTimeDiv = timeDivSeconds[timeDivIdx].toFloat()
                            val curVoltsDiv = voltsDivValues[voltsDivIdx]
                            val pixelsPerVolt = (divH) / curVoltsDiv

                            if (isLissajousXy && ch2Enabled) {
                                // XY Lissajous Mode
                                val xyPath = Path()
                                val numPoints = 300
                                val phaseRad = (ch2PhaseDeg * PI / 180.0).toFloat()

                                for (k in 0..numPoints) {
                                    val t = (k.toFloat() / numPoints) * 2f * PI.toFloat()
                                    val xVal = ch1AmpVolts * sin(t + phaseTick)
                                    val yVal = ch2AmpVolts * sin((ch2FreqHz / ch1FreqHz) * t + phaseRad + phaseTick)

                                    val screenX = midX + xVal * pixelsPerVolt
                                    val screenY = midY - yVal * pixelsPerVolt

                                    if (k == 0) xyPath.moveTo(screenX, screenY)
                                    else xyPath.lineTo(screenX, screenY)
                                }

                                drawPath(
                                    path = xyPath,
                                    color = PhosphorCyan,
                                    style = Stroke(width = 3.5f)
                                )
                            } else {
                                // Timebase Waveform Rendering
                                // Channel 1 (Green)
                                val ch1Path = Path()
                                val totalTime = curTimeDiv * numHDivs

                                for (px in 0..w.toInt() step 2) {
                                    val t = (px / w) * totalTime
                                    val arg = 2f * PI.toFloat() * ch1FreqHz * t + phaseTick
                                    val v = when (ch1Waveform) {
                                        "Square" -> if (sin(arg) >= 0) ch1AmpVolts else -ch1AmpVolts
                                        "Triangle" -> (2f * ch1AmpVolts / PI.toFloat()) * kotlin.math.asin(sin(arg))
                                        "Saw" -> ch1AmpVolts * (2f * ((ch1FreqHz * t + phaseTick / (2f * PI.toFloat())) % 1f) - 1f)
                                        else -> ch1AmpVolts * sin(arg)
                                    } + ch1OffsetVolts

                                    val screenY = midY - (v * pixelsPerVolt)
                                    if (px == 0) ch1Path.moveTo(0f, screenY)
                                    else ch1Path.lineTo(px.toFloat(), screenY)
                                }

                                drawPath(
                                    path = ch1Path,
                                    color = ElectricGreen,
                                    style = Stroke(width = 3.5f)
                                )

                                // Channel 2 (Cyan)
                                if (ch2Enabled) {
                                    val ch2Path = Path()
                                    val phaseRad = (ch2PhaseDeg * PI / 180.0).toFloat()

                                    for (px in 0..w.toInt() step 2) {
                                        val t = (px / w) * totalTime
                                        val arg = 2f * PI.toFloat() * ch2FreqHz * t + phaseRad + phaseTick
                                        val v = when (ch2Waveform) {
                                            "Square" -> if (sin(arg) >= 0) ch2AmpVolts else -ch2AmpVolts
                                            "Triangle" -> (2f * ch2AmpVolts / PI.toFloat()) * kotlin.math.asin(sin(arg))
                                            "Saw" -> ch2AmpVolts * (2f * ((ch2FreqHz * t + (phaseRad + phaseTick) / (2f * PI.toFloat())) % 1f) - 1f)
                                            else -> ch2AmpVolts * sin(arg)
                                        }

                                        val screenY = midY - (v * pixelsPerVolt)
                                        if (px == 0) ch2Path.moveTo(0f, screenY)
                                        else ch2Path.lineTo(px.toFloat(), screenY)
                                    }

                                    drawPath(
                                        path = ch2Path,
                                        color = PhosphorCyan,
                                        style = Stroke(width = 3f)
                                    )
                                }
                            }
                        }

                        // HUD Overlay on Screen (Vpp, Freq, Status)
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "CH1: $ch1Waveform ${ch1FreqHz.toInt()}Hz ${ch1AmpVolts * 2}Vpp",
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = ElectricGreen
                                )
                                if (ch2Enabled) {
                                    Text(
                                        text = "CH2: $ch2Waveform ${ch2FreqHz.toInt()}Hz ${ch2AmpVolts * 2}Vpp",
                                        fontSize = 11.sp,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        color = PhosphorCyan
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "TIME: ${timeDivLabels[timeDivIdx]} | SCALE: ${voltsDivLabels[voltsDivIdx]}",
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = if (isRunning) "● RUNNING" else "❚❚ FROZEN",
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isRunning) ElectricGreen else NeonRed
                                )
                            }
                        }
                    }
                }
            }

            // Quick Run / Pause / Mode Controls Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { isRunning = !isRunning },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isRunning) Color(0xFFB71C1C) else ElectricGreenDark
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isRunning) "Freeze" else "Run")
                    }

                    OutlinedButton(
                        onClick = { isLissajousXy = !isLissajousXy },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (isLissajousXy) "Timebase Mode" else "XY Lissajous")
                    }
                }
            }

            // Channel Tabs
            item {
                TabRow(
                    selectedTabIndex = selectedChannel,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = ElectricGreen
                ) {
                    Tab(selected = selectedChannel == 0, onClick = { selectedChannel = 0 }, text = { Text("CH1 (Green)", fontWeight = FontWeight.Bold) })
                    Tab(selected = selectedChannel == 1, onClick = { selectedChannel = 1 }, text = { Text("CH2 (Cyan)", fontWeight = FontWeight.Bold) })
                    Tab(selected = selectedChannel == 2, onClick = { selectedChannel = 2 }, text = { Text("Scale & Timebase", fontWeight = FontWeight.Bold) })
                }
            }

            // Channel Specific Controls
            if (selectedChannel == 0) {
                // CH1 Controls
                item {
                    Column {
                        Text("CH1 Waveform Type:", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(6.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(listOf("Sine", "Square", "Triangle", "Saw")) { wForm ->
                                val isSel = ch1Waveform == wForm
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, if (isSel) ElectricGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                        .clickable { ch1Waveform = wForm }
                                        .padding(horizontal = 12.dp, vertical = 6.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = wForm,
                                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSel) ElectricGreen else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Frequency: ${ch1FreqHz.toInt()} Hz", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            Text("100 Hz — 10 kHz", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Slider(
                            value = ch1FreqHz,
                            onValueChange = { ch1FreqHz = it },
                            valueRange = 100f..10000f,
                            colors = SliderDefaults.colors(thumbColor = ElectricGreen, activeTrackColor = ElectricGreen)
                        )
                    }
                }

                item {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Peak Amplitude: ${CalculatorEngine.formatNumber(ch1AmpVolts.toDouble(), 1)} V (${CalculatorEngine.formatNumber(ch1AmpVolts.toDouble() * 2, 1)} Vpp)", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                        }
                        Slider(
                            value = ch1AmpVolts,
                            onValueChange = { ch1AmpVolts = it },
                            valueRange = 0.5f..5.0f,
                            colors = SliderDefaults.colors(thumbColor = ElectricGreen, activeTrackColor = ElectricGreen)
                        )
                    }
                }
            } else if (selectedChannel == 1) {
                // CH2 Controls
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Channel 2 Output", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                        Button(
                            onClick = { ch2Enabled = !ch2Enabled },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (ch2Enabled) PhosphorCyan else MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = if (ch2Enabled) Color.Black else MaterialTheme.colorScheme.onSurface
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(if (ch2Enabled) "CH2 ACTIVE" else "CH2 OFF", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                item {
                    Column {
                        Text("CH2 Waveform Type:", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(6.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(listOf("Sine", "Square", "Triangle", "Saw")) { wForm ->
                                val isSel = ch2Waveform == wForm
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, if (isSel) PhosphorCyan else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                        .clickable { ch2Waveform = wForm }
                                        .padding(horizontal = 12.dp, vertical = 6.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = wForm,
                                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSel) PhosphorCyan else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Column {
                        Text("CH2 Phase Offset: ${ch2PhaseDeg.toInt()}°", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                        Slider(
                            value = ch2PhaseDeg,
                            onValueChange = { ch2PhaseDeg = it },
                            valueRange = 0f..360f,
                            colors = SliderDefaults.colors(thumbColor = PhosphorCyan, activeTrackColor = PhosphorCyan)
                        )
                    }
                }
            } else {
                // Scale & Timebase
                item {
                    Column {
                        Text("Horizontal Time/Division:", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(6.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            itemsIndexed(timeDivLabels) { idx, label ->
                                val isSel = idx == timeDivIdx
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, if (isSel) ElectricGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                        .clickable { timeDivIdx = idx }
                                        .padding(horizontal = 10.dp, vertical = 6.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(label, fontSize = 11.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal, color = if (isSel) ElectricGreen else MaterialTheme.colorScheme.onSurface)
                                }
                            }
                        }
                    }
                }

                item {
                    Column {
                        Text("Vertical Volts/Division:", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(6.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            itemsIndexed(voltsDivLabels) { idx, label ->
                                val isSel = idx == voltsDivIdx
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, if (isSel) ElectricGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                        .clickable { voltsDivIdx = idx }
                                        .padding(horizontal = 10.dp, vertical = 6.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(label, fontSize = 11.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal, color = if (isSel) ElectricGreen else MaterialTheme.colorScheme.onSurface)
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}
