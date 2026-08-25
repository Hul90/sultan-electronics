package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.ElectricMeter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ComponentsData
import com.example.data.PinoutsData
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.ElectricGreen
import com.example.ui.theme.ElectricGreenDark
import com.example.ui.theme.NeonRed
import com.example.ui.theme.PhosphorCyan
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechPurple
import com.example.ui.viewmodel.SultanViewModel

data class QuickTool(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val color: Color,
    val route: String
)

@Composable
fun HomeScreen(
    viewModel: SultanViewModel,
    onNavigate: (String) -> Unit
) {
    val favorites by viewModel.favoritesList.collectAsState()
    val recentHistory by viewModel.recentHistoryList.collectAsState()

    val quickTools = listOf(
        QuickTool("calc_resistor", "Resistor Bands", "4/5/6 Band & Reverse", Icons.Default.Palette, ElectricGreen, "calc_resistor"),
        QuickTool("calc_smd", "SMD Decoder", "3/4 Digit & EIA-96", Icons.Default.Memory, PhosphorCyan, "calc_smd"),
        QuickTool("calc_ohms_law", "Ohm's Law", "V, I, R, P Calculator", Icons.Default.Bolt, TechAmber, "calc_ohms_law"),
        QuickTool("calc_led", "LED Resistor", "Current Limiter & E24", Icons.Default.Lightbulb, NeonRed, "calc_led"),
        QuickTool("calc_555", "555 Timer", "Astable & Monostable", Icons.Default.Timer, TechPurple, "calc_555"),
        QuickTool("calc_voltage_divider", "Voltage Divider", "Loaded & Unloaded", Icons.Default.ElectricMeter, ElectricGreen, "calc_voltage_divider"),
        QuickTool("calc_battery", "Battery Pack", "Runtime & Wh Calculator", Icons.Default.BatteryChargingFull, PhosphorCyan, "calc_battery"),
        QuickTool("calc_wire", "Wire Drop", "AWG & Voltage Loss", Icons.Default.Cable, TechAmber, "calc_wire")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // 1. Engineering Brand Header Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, ElectricGreen.copy(alpha = 0.4f), RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF00382B),
                                    Color(0xFF00221A),
                                    Color(0xFF001510)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(ElectricGreenDark)
                                        .border(1.5.dp, ElectricGreen, RoundedCornerShape(10.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "SE",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "SULTAN ELECTRONICS",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Black,
                                            letterSpacing = 1.sp,
                                            color = Color.White
                                        )
                                    )
                                    Text(
                                        text = "Engineering Toolkit by MD SULTAN MAHAMUD",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = PhosphorCyan,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }

                            // Offline Active Badge
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.Black.copy(alpha = 0.4f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, ElectricGreen.copy(alpha = 0.5f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(ElectricGreen)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "OFFLINE OK",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ElectricGreen
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Quick Search Bar Trigger
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onNavigate("search") }
                                .background(Color.Black.copy(alpha = 0.35f))
                                .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            color = Color.Transparent
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search",
                                        tint = ElectricGreen,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "Search calculators, pinouts, ICs...",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = Color.White.copy(alpha = 0.7f)
                                        )
                                    )
                                }
                                Text(
                                    text = "Ctrl+K",
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.White.copy(alpha = 0.5f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Quick Stats Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            QuickStatItem("Calculators", "14+", Icons.Default.Bolt)
                            QuickStatItem("Pinouts", "${PinoutsData.allPinouts.size}", Icons.Default.Memory)
                            QuickStatItem("Components", "${ComponentsData.allComponents.size}", Icons.Default.Science)
                            QuickStatItem("Favorites", "${favorites.size}", Icons.Default.Bookmarks)
                        }
                    }
                }
            }
        }

        // 1.5. SULTAN ADVANCED Major Workstation Entry Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate("sa_main") }
                    .border(1.5.dp, Color(0xFFFFD700).copy(alpha = 0.7f), RoundedCornerShape(18.dp)),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF061A14))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF003023),
                                    Color(0xFF002219),
                                    Color(0xFF00150F)
                                )
                            )
                        )
                        .padding(16.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFF003D2E))
                                        .border(1.5.dp, Color(0xFFFFD700), RoundedCornerShape(10.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "SA",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 18.sp,
                                        color = Color(0xFFFFD700),
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "SULTAN ADVANCED",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Black,
                                                color = Color(0xFFFFD700),
                                                letterSpacing = 0.5.sp
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = Color(0xFFFFD700).copy(alpha = 0.2f),
                                            border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFFFD700))
                                        ) {
                                            Text(
                                                text = "3D & REPAIR",
                                                fontSize = 8.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFFFFD700),
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = "25 Categories • 3D Component Models • Fan Wiring",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = PhosphorCyan,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Open Sultan Advanced",
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Quick Chips Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color.Black.copy(alpha = 0.4f),
                                border = androidx.compose.foundation.BorderStroke(0.8.dp, ElectricGreen),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onNavigate("sa_3d_showcase") }
                            ) {
                                Text(
                                    text = "3D VIEW",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ElectricGreen,
                                    fontFamily = FontFamily.Monospace,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp)
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color.Black.copy(alpha = 0.4f),
                                border = androidx.compose.foundation.BorderStroke(0.8.dp, NeonRed),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onNavigate("sa_fan_motors") }
                            ) {
                                Text(
                                    text = "FAN/MOTOR",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NeonRed,
                                    fontFamily = FontFamily.Monospace,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp)
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color.Black.copy(alpha = 0.4f),
                                border = androidx.compose.foundation.BorderStroke(0.8.dp, PhosphorCyan),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onNavigate("sa_compare") }
                            ) {
                                Text(
                                    text = "COMPARE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PhosphorCyan,
                                    fontFamily = FontFamily.Monospace,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp)
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color.Black.copy(alpha = 0.4f),
                                border = androidx.compose.foundation.BorderStroke(0.8.dp, Color(0xFFFFD700)),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onNavigate("sa_main") }
                            ) {
                                Text(
                                    text = "ALL 25 CAT",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFD700),
                                    fontFamily = FontFamily.Monospace,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. Pro Engineering Tools (Oscilloscope, Spectrum, PCB Designer)
        item {
            SectionHeader(
                title = "Pro Simulation Tools",
                badge = "PRO",
                onSeeAll = { onNavigate("tools") }
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ProToolCard(
                    title = "Oscilloscope",
                    subtitle = "Dual-Ch Signal Analyzer",
                    icon = Icons.Default.ShowChart,
                    color = ElectricGreen,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate("tool_oscilloscope") }
                )
                ProToolCard(
                    title = "Spectrum",
                    subtitle = "FFT Harmonics Analyzer",
                    icon = Icons.Default.Speed,
                    color = PhosphorCyan,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate("tool_spectrum") }
                )
                ProToolCard(
                    title = "PCB Designer",
                    subtitle = "2D Schematic & Board",
                    icon = Icons.Default.Memory,
                    color = TechAmber,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate("tool_pcb") }
                )
            }
        }

        // 3. Quick Popular Calculators Grid
        item {
            SectionHeader(
                title = "Quick Calculators",
                onSeeAll = { onNavigate("calculators") }
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                for (i in quickTools.indices step 2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        QuickToolItem(
                            tool = quickTools[i],
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigate(quickTools[i].route) }
                        )
                        if (i + 1 < quickTools.size) {
                            QuickToolItem(
                                tool = quickTools[i + 1],
                                modifier = Modifier.weight(1f),
                                onClick = { onNavigate(quickTools[i + 1].route) }
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        // 4. Featured Pinouts Quick Scroll
        item {
            SectionHeader(
                title = "Popular IC & Board Pinouts",
                onSeeAll = { onNavigate("pinouts") }
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(PinoutsData.allPinouts.take(6)) { pin ->
                    Card(
                        modifier = Modifier
                            .width(180.dp)
                            .clickable { onNavigate("pinout_detail/${pin.id}") }
                            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(14.dp)),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${pin.pinCount} PINS",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PhosphorCyan
                                )
                                Text(
                                    text = pin.packageType.split(" ").firstOrNull() ?: "",
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = pin.name,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                maxLines = 1
                            )
                            Text(
                                text = pin.category,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                ),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // 5. Recent History
        if (recentHistory.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "Recent Calculations",
                    onSeeAll = { onNavigate("history") }
                )
            }

            items(recentHistory.take(4)) { item ->
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(item.toolId) },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.toolName,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ElectricGreen
                                )
                            )
                            Text(
                                text = item.resultSummary,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontFamily = FontFamily.Monospace
                                ),
                                maxLines = 1
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Open",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuickStatItem(label: String, value: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = PhosphorCyan, modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = value,
                fontWeight = FontWeight.Black,
                fontSize = 13.sp,
                color = Color.White
            )
        }
        Text(
            text = label,
            fontSize = 10.sp,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun SectionHeader(title: String, badge: String? = null, onSeeAll: (() -> Unit)? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            )
            if (badge != null) {
                Spacer(modifier = Modifier.width(6.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = ElectricGreenDark
                ) {
                    Text(
                        text = badge,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
            }
        }
        if (onSeeAll != null) {
            Text(
                text = "View All",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = ElectricGreen,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { onSeeAll() }
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun ProToolCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(110.dp)
            .clickable { onClick() }
            .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(18.dp)
                )
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    ),
                    maxLines = 1
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 9.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun QuickToolItem(
    tool: QuickTool,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(72.dp)
            .clickable { onClick() }
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f), RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(tool.color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = tool.icon,
                    contentDescription = tool.title,
                    tint = tool.color,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tool.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    ),
                    maxLines = 1
                )
                Text(
                    text = tool.subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    maxLines = 1
                )
            }
        }
    }
}
