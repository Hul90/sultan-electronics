package com.example.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SultanTopAppBar
import com.example.ui.theme.ElectricGreen
import com.example.ui.theme.PhosphorCyan
import com.example.ui.theme.TechAmber
import com.example.ui.viewmodel.SultanViewModel

data class ProToolItem(
    val id: String,
    val title: String,
    val description: String,
    val badge: String,
    val icon: ImageVector,
    val color: Color,
    val route: String
)

@Composable
fun ToolsListScreen(
    viewModel: SultanViewModel,
    onNavigate: (String) -> Unit
) {
    val proTools = listOf(
        ProToolItem(
            id = "tool_oscilloscope",
            title = "Virtual Dual-Channel Oscilloscope",
            description = "Interactive signal waveforms (Sine, Square, Triangle), timebase, volts/div, and XY Lissajous analyzer.",
            badge = "PRO SIMULATION",
            icon = Icons.Default.ShowChart,
            color = ElectricGreen,
            route = "tool_oscilloscope"
        ),
        ProToolItem(
            id = "tool_spectrum",
            title = "FFT Spectrum Analyzer",
            description = "Frequency domain harmonics visualizer, dBm power levels, marker detection, and noise floor analyzer.",
            badge = "PRO SIMULATION",
            icon = Icons.Default.GraphicEq,
            color = PhosphorCyan,
            route = "tool_spectrum"
        ),
        ProToolItem(
            id = "tool_pcb",
            title = "Sultan PCB & Schematic Workspace",
            description = "2D breadboard and schematic layout builder with draggable components, wire routing, and BOM export.",
            badge = "CAD DESIGNER",
            icon = Icons.Default.Architecture,
            color = TechAmber,
            route = "tool_pcb"
        )
    )

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Pro Engineering Tools",
                subtitle = "Laboratory Instruments & CAD Workspace"
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
            items(proTools) { tool ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(tool.route) }
                        .border(1.dp, tool.color.copy(alpha = 0.35f), RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(1.dp, tool.color.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = tool.icon, contentDescription = tool.title, tint = tool.color, modifier = Modifier.size(24.dp))
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = tool.color.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = tool.badge,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = tool.color
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = tool.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = tool.description,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 18.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Launch Instrument", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = tool.color)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = tool.color, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(60.dp)) }
        }
    }
}
