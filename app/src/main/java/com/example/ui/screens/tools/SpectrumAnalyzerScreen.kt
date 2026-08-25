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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SultanTopAppBar
import com.example.ui.theme.ElectricGreen
import com.example.ui.theme.ElectricGreenDark
import com.example.ui.theme.PhosphorCyan
import com.example.ui.theme.TechAmber
import com.example.ui.viewmodel.SultanViewModel
import kotlinx.coroutines.delay
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun SpectrumAnalyzerScreen(
    viewModel: SultanViewModel,
    onBack: () -> Unit
) {
    var isRunning by remember { mutableStateOf(true) }
    var signalType by remember { mutableStateOf("Square (Harmonics)") } // Pure Sine, Square (Harmonics), FM Modulated, Two-Tone Intermod
    var fundamentalFreqKhz by remember { mutableFloatStateOf(10f) }
    var noiseFloorDb by remember { mutableFloatStateOf(-75f) }

    var tick by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(50)
            tick += 0.2f
        }
    }

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Spectrum Analyzer",
                subtitle = "FFT Frequency Domain & Harmonics (PRO SIMULATION)",
                onBackClick = onBack,
                isFavorite = viewModel.isFavorite("tool_spectrum"),
                onFavoriteToggle = {
                    viewModel.toggleFavorite("tool_spectrum", "TOOL", "Spectrum Analyzer", "FFT Harmonics Analyzer", "tool_spectrum")
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
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    color = Color.Black.copy(alpha = 0.4f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PhosphorCyan.copy(alpha = 0.5f))
                ) {
                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(PhosphorCyan))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "FREQUENCY DOMAIN FFT HARMONICS ANALYZER (SIMULATION)",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = PhosphorCyan
                        )
                    }
                }
            }

            // Spectrum FFT Screen
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .border(2.dp, Color(0xFF0F3038), RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF020E12))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height

                            // Draw Spectrum dB Grid Lines (0 dBm to -90 dBm)
                            for (db in 0..9) {
                                val y = (db / 9f) * h
                                drawLine(
                                    color = Color(0xFF0B2E38),
                                    start = Offset(0f, y),
                                    end = Offset(w, y),
                                    strokeWidth = 1f
                                )
                            }
                            for (f in 0..10) {
                                val x = (f / 10f) * w
                                drawLine(
                                    color = Color(0xFF0B2E38),
                                    start = Offset(x, 0f),
                                    end = Offset(x, h),
                                    strokeWidth = 1f
                                )
                            }

                            // Generate and draw FFT frequency bins (64 bins)
                            val numBins = 64
                            val binW = w / numBins

                            for (b in 0 until numBins) {
                                val binFreqKhz = (b / numBins.toFloat()) * 100f // 0 - 100 kHz span
                                var amplitudeDb = noiseFloorDb + Random.nextFloat() * 6f

                                when (signalType) {
                                    "Pure Sine" -> {
                                        val dist = kotlin.math.abs(binFreqKhz - fundamentalFreqKhz)
                                        if (dist < 3f) {
                                            amplitudeDb = -10f - (dist * 12f)
                                        }
                                    }
                                    "Square (Harmonics)" -> {
                                        // Fundamental
                                        val distF1 = kotlin.math.abs(binFreqKhz - fundamentalFreqKhz)
                                        if (distF1 < 2.5f) amplitudeDb = -10f - (distF1 * 10f)

                                        // 3rd Harmonic (1/3 = -9.5 dB)
                                        val distF3 = kotlin.math.abs(binFreqKhz - fundamentalFreqKhz * 3f)
                                        if (distF3 < 2.5f) amplitudeDb = -19.5f - (distF3 * 10f)

                                        // 5th Harmonic (1/5 = -14 dB)
                                        val distF5 = kotlin.math.abs(binFreqKhz - fundamentalFreqKhz * 5f)
                                        if (distF5 < 2.5f) amplitudeDb = -24.0f - (distF5 * 10f)

                                        // 7th Harmonic
                                        val distF7 = kotlin.math.abs(binFreqKhz - fundamentalFreqKhz * 7f)
                                        if (distF7 < 2.5f) amplitudeDb = -28.0f - (distF7 * 10f)
                                    }
                                    "FM Modulated" -> {
                                        val center = fundamentalFreqKhz
                                        val deviation = 10f
                                        val currentInstantFreq = center + deviation * sin(tick)
                                        val dist = kotlin.math.abs(binFreqKhz - currentInstantFreq)
                                        if (dist < 4f) amplitudeDb = -12f - (dist * 8f)
                                    }
                                }

                                val normalizedHeight = ((amplitudeDb + 90f) / 90f).coerceIn(0.05f, 0.95f)
                                val barH = normalizedHeight * h
                                val barY = h - barH

                                drawRect(
                                    brush = Brush.verticalGradient(
                                        listOf(PhosphorCyan, Color(0xFF00695C))
                                    ),
                                    topLeft = Offset(b * binW + 1f, barY),
                                    size = Size(binW - 2f, barH)
                                )
                            }
                        }

                        // HUD Marker Overlay
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
                                    text = "MARKER 1: ${fundamentalFreqKhz.toInt()} kHz | -10.0 dBm",
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = PhosphorCyan
                                )
                                Text(
                                    text = "SPAN: 100 kHz | RBW: 1 kHz",
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "NOISE FLOOR: ${noiseFloorDb.toInt()} dBm",
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.Gray
                                )
                                Text(
                                    text = if (isRunning) "● SWEEP ACTIVE" else "❚❚ HOLD",
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isRunning) PhosphorCyan else Color.Red
                                )
                            }
                        }
                    }
                }
            }

            // Signal Selection
            item {
                Column {
                    Text("Input Test Signal:", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(listOf("Square (Harmonics)", "Pure Sine", "FM Modulated")) { sType ->
                            val isSel = signalType == sType
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, if (isSel) PhosphorCyan else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                    .clickable { signalType = sType }
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(sType, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal, color = if (isSel) PhosphorCyan else MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }

            item {
                Column {
                    Text("Fundamental Center Frequency: ${fundamentalFreqKhz.toInt()} kHz", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                    Slider(
                        value = fundamentalFreqKhz,
                        onValueChange = { fundamentalFreqKhz = it },
                        valueRange = 5f..40f,
                        colors = SliderDefaults.colors(thumbColor = PhosphorCyan, activeTrackColor = PhosphorCyan)
                    )
                }
            }

            item {
                Button(
                    onClick = { isRunning = !isRunning },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isRunning) Color(0xFFB71C1C) else PhosphorCyan),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isRunning) "Pause Sweep" else "Resume Sweep", color = if (isRunning) Color.White else Color.Black, fontWeight = FontWeight.Bold)
                }
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}
