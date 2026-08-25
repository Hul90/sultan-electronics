package com.example.ui.screens.tools

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SultanTopAppBar
import com.example.ui.components.TechnicalInputField
import com.example.ui.components.shareText
import com.example.ui.theme.ElectricGreen
import com.example.ui.theme.ElectricGreenDark
import com.example.ui.theme.NeonRed
import com.example.ui.theme.PhosphorCyan
import com.example.ui.theme.TechAmber
import com.example.ui.viewmodel.SultanViewModel

data class PcbComponent(
    val id: String,
    val type: String, // Resistor, Capacitor, LED, NE555, VCC, GND
    val label: String,
    var x: Float,
    var y: Float,
    val color: Color
)

data class PcbWire(
    val id: String,
    val fromId: String,
    val toId: String,
    val color: Color
)

@Composable
fun PcbDesignerScreen(
    viewModel: SultanViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var projectName by remember { mutableStateOf("LED Flasher Circuit") }
    var selectedTool by remember { mutableStateOf("SELECT") } // SELECT, WIRE, DELETE

    // Canvas Placed Components List
    val components = remember {
        mutableStateListOf(
            PcbComponent("c1", "VCC", "5V Power", 80f, 60f, NeonRed),
            PcbComponent("c2", "Resistor", "R1 1kΩ", 200f, 60f, TechAmber),
            PcbComponent("c3", "LED", "LED1 Red", 320f, 120f, ElectricGreen),
            PcbComponent("c4", "GND", "Ground", 320f, 220f, PhosphorCyan)
        )
    }

    val wires = remember {
        mutableStateListOf(
            PcbWire("w1", "c1", "c2", NeonRed),
            PcbWire("w2", "c2", "c3", TechAmber),
            PcbWire("w3", "c3", "c4", Color.Gray)
        )
    }

    var selectedCompId by remember { mutableStateOf<String?>(null) }
    var wireStartCompId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Sultan PCB & Schematic",
                subtitle = "Interactive Circuit Layout & BOM Generator",
                onBackClick = onBack,
                isFavorite = viewModel.isFavorite("tool_pcb"),
                onFavoriteToggle = {
                    viewModel.toggleFavorite("tool_pcb", "TOOL", "PCB Designer", "2D Schematic & Layout", "tool_pcb")
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
            // Project Name & Action Bar
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = projectName,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = ElectricGreen)
                        )
                        Text(
                            text = "${components.size} Components | ${wires.size} Wires (Grid Snapped)",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row {
                        IconButton(
                            onClick = {
                                val bomSummary = buildString {
                                    appendLine("=== $projectName — Bill of Materials & Netlist ===")
                                    appendLine("Components (${components.size}):")
                                    components.forEachIndexed { i, c ->
                                        appendLine("${i + 1}. [${c.type}] ${c.label} (ID: ${c.id})")
                                    }
                                    appendLine("\nNetlist Connections (${wires.size}):")
                                    wires.forEach { w ->
                                        val from = components.find { it.id == w.fromId }?.label ?: w.fromId
                                        val to = components.find { it.id == w.toId }?.label ?: w.toId
                                        appendLine("• $from  <———>  $to")
                                    }
                                    appendLine("\n— Exported with SULTAN ELECTRONICS")
                                }
                                shareText(context, "$projectName BOM & Netlist", bomSummary)
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "Export BOM", tint = PhosphorCyan)
                        }

                        IconButton(
                            onClick = {
                                viewModel.savePcbProject(
                                    projectName,
                                    "${components.size} Components, ${wires.size} Wires",
                                    components.joinToString(";") { "${it.id}:${it.type}:${it.label}:${it.x}:${it.y}" },
                                    wires.joinToString(";") { "${it.id}:${it.fromId}:${it.toId}" }
                                )
                                Toast.makeText(context, "Circuit project saved to local storage!", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Save, contentDescription = "Save Project", tint = ElectricGreen)
                        }
                    }
                }
            }

            // Interactive Schematic Canvas
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .border(2.dp, Color(0xFF00382B), RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF02140E))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(selectedTool) {
                                    detectTapGestures { tapOffset ->
                                        val tapped = components.find { comp ->
                                            val dist = (Offset(comp.x, comp.y) - tapOffset).getDistance()
                                            dist < 36f
                                        }
                                        if (selectedTool == "SELECT") {
                                            selectedCompId = tapped?.id
                                        } else if (selectedTool == "WIRE") {
                                            if (tapped != null) {
                                                if (wireStartCompId == null) {
                                                    wireStartCompId = tapped.id
                                                    Toast.makeText(context, "Connected from ${tapped.label}. Tap 2nd component.", Toast.LENGTH_SHORT).show()
                                                } else if (wireStartCompId != tapped.id) {
                                                    wires.add(PcbWire("w_${System.currentTimeMillis()}", wireStartCompId!!, tapped.id, ElectricGreen))
                                                    Toast.makeText(context, "Connected to ${tapped.label}!", Toast.LENGTH_SHORT).show()
                                                    wireStartCompId = null
                                                }
                                            }
                                        } else if (selectedTool == "DELETE") {
                                            if (tapped != null) {
                                                wires.removeAll { it.fromId == tapped.id || it.toId == tapped.id }
                                                components.remove(tapped)
                                                selectedCompId = null
                                            }
                                        }
                                    }
                                }
                                .pointerInput(selectedTool) {
                                    detectDragGestures { change, dragAmount ->
                                        change.consume()
                                        val comp = components.find { it.id == selectedCompId }
                                        if (comp != null) {
                                            comp.x = (comp.x + dragAmount.x).coerceIn(40f, 900f)
                                            comp.y = (comp.y + dragAmount.y).coerceIn(40f, 700f)
                                        }
                                    }
                                }
                        ) {
                            val w = size.width
                            val h = size.height

                            // Draw PCB Dot Grid
                            val gridSpacing = 20f
                            var gx = 10f
                            while (gx < w) {
                                var gy = 10f
                                while (gy < h) {
                                    drawCircle(
                                        color = Color(0xFF0B3A28),
                                        radius = 1.5f,
                                        center = Offset(gx, gy)
                                    )
                                    gy += gridSpacing
                                }
                                gx += gridSpacing
                            }

                            // Draw Wires
                            wires.forEach { wire ->
                                val c1 = components.find { it.id == wire.fromId }
                                val c2 = components.find { it.id == wire.toId }
                                if (c1 != null && c2 != null) {
                                    // Manhattan right-angle wire routing
                                    val midX = (c1.x + c2.x) / 2f
                                    drawLine(
                                        color = wire.color,
                                        start = Offset(c1.x, c1.y),
                                        end = Offset(midX, c1.y),
                                        strokeWidth = 3f
                                    )
                                    drawLine(
                                        color = wire.color,
                                        start = Offset(midX, c1.y),
                                        end = Offset(midX, c2.y),
                                        strokeWidth = 3f
                                    )
                                    drawLine(
                                        color = wire.color,
                                        start = Offset(midX, c2.y),
                                        end = Offset(c2.x, c2.y),
                                        strokeWidth = 3f
                                    )
                                    // Junction dots
                                    drawCircle(color = wire.color, radius = 4f, center = Offset(c1.x, c1.y))
                                    drawCircle(color = wire.color, radius = 4f, center = Offset(c2.x, c2.y))
                                }
                            }

                            // Draw Components
                            components.forEach { comp ->
                                val isSelected = comp.id == selectedCompId
                                val isWireSource = comp.id == wireStartCompId

                                val compW = 60f
                                val compH = 30f
                                val topLeft = Offset(comp.x - compW / 2f, comp.y - compH / 2f)

                                drawRoundRect(
                                    color = if (isSelected) ElectricGreen.copy(alpha = 0.35f) else Color(0xFF0F261D),
                                    topLeft = topLeft,
                                    size = Size(compW, compH),
                                    cornerRadius = CornerRadius(6f, 6f)
                                )

                                drawRoundRect(
                                    color = if (isWireSource) PhosphorCyan else if (isSelected) ElectricGreen else comp.color,
                                    topLeft = topLeft,
                                    size = Size(compW, compH),
                                    cornerRadius = CornerRadius(6f, 6f),
                                    style = Stroke(width = if (isSelected || isWireSource) 3f else 1.5f)
                                )

                                // Connection pin nodes
                                drawCircle(color = Color.White, radius = 3f, center = Offset(comp.x - compW / 2f, comp.y))
                                drawCircle(color = Color.White, radius = 3f, center = Offset(comp.x + compW / 2f, comp.y))
                            }
                        }

                        // Overlay Active HUD
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color.Black.copy(alpha = 0.6f)
                            ) {
                                Text(
                                    text = "TOOL: $selectedTool " + if (wireStartCompId != null) "(Connecting...)" else "",
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = if (selectedTool == "WIRE") PhosphorCyan else ElectricGreen,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Toolbar: SELECT, WIRE, DELETE
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ToolButton(label = "Select & Move", icon = Icons.Default.TouchApp, isSelected = selectedTool == "SELECT", modifier = Modifier.weight(1f)) {
                        selectedTool = "SELECT"
                        wireStartCompId = null
                    }
                    ToolButton(label = "Connect Wire", icon = Icons.Default.Cable, isSelected = selectedTool == "WIRE", modifier = Modifier.weight(1f)) {
                        selectedTool = "WIRE"
                    }
                    ToolButton(label = "Delete Part", icon = Icons.Default.Delete, isSelected = selectedTool == "DELETE", modifier = Modifier.weight(1f)) {
                        selectedTool = "DELETE"
                        wireStartCompId = null
                    }
                }
            }

            // Add Components Palette
            item {
                Column {
                    Text(
                        text = "Add Component to Canvas:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        val palette = listOf(
                            Triple("Resistor", "R 10k", TechAmber),
                            Triple("Capacitor", "C 100nF", PhosphorCyan),
                            Triple("LED", "LED Red", ElectricGreen),
                            Triple("NE555", "555 Timer", Color(0xFFAB47BC)),
                            Triple("Transistor", "2N2222", Color(0xFF42A5F5)),
                            Triple("VCC", "3.3V / 5V", NeonRed),
                            Triple("GND", "Ground", Color.LightGray)
                        )
                        items(palette) { (type, defaultLabel, color) ->
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                                    .clickable {
                                        val newId = "c_${System.currentTimeMillis() % 10000}"
                                        val newComp = PcbComponent(
                                            id = newId,
                                            type = type,
                                            label = "$type ($newId)",
                                            x = 100f + (components.size * 30f) % 250f,
                                            y = 100f + (components.size * 25f) % 150f,
                                            color = color
                                        )
                                        components.add(newComp)
                                        selectedCompId = newId
                                        Toast.makeText(context, "Added $type to schematic!", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(type, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
                                }
                            }
                        }
                    }
                }
            }

            // BOM / Placed Parts List
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Bill of Materials (BOM)",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = PhosphorCyan)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        components.forEach { comp ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(comp.color))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(comp.label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                }
                                Text(
                                    text = "(${comp.x.toInt()}, ${comp.y.toInt()})",
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

@Composable
fun ToolButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(
                1.dp,
                if (isSelected) ElectricGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.25f),
                RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        color = if (isSelected) ElectricGreen.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) ElectricGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) ElectricGreen else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
