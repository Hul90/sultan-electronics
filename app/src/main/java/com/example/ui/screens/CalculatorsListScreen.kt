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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.ElectricMeter
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.ui.theme.NeonRed
import com.example.ui.theme.PhosphorCyan
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechPurple
import com.example.ui.viewmodel.SultanViewModel

data class CalculatorEntry(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val icon: ImageVector,
    val color: Color,
    val route: String
)

@Composable
fun CalculatorsListScreen(
    viewModel: SultanViewModel,
    onNavigate: (String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("ALL") }
    val favorites by viewModel.favoritesList.collectAsState()

    val categories = listOf("ALL", "RESISTIVE", "PASSIVE", "POWER", "SEMICONDUCTOR", "TIMING & RF")

    val allCalculators = listOf(
        CalculatorEntry("calc_resistor", "Resistor Color Code", "4-Band, 5-Band, 6-Band & Reverse Code", "RESISTIVE", Icons.Default.Palette, ElectricGreen, "calc_resistor"),
        CalculatorEntry("calc_smd", "SMD Resistor Decoder", "3-Digit, 4-Digit, R-Notation, EIA-96", "RESISTIVE", Icons.Default.Memory, PhosphorCyan, "calc_smd"),
        CalculatorEntry("calc_ohms_law", "Ohm's Law & Power", "V, I, R, P, Energy Calculations", "POWER", Icons.Default.Bolt, TechAmber, "calc_ohms_law"),
        CalculatorEntry("calc_led", "LED Current Limiting Resistor", "Series/Parallel Arrays & E24 Resistor", "SEMICONDUCTOR", Icons.Default.Lightbulb, NeonRed, "calc_led"),
        CalculatorEntry("calc_555", "NE555 Timer Calculator", "Astable & Monostable Multivibrators", "TIMING & RF", Icons.Default.Timer, TechPurple, "calc_555"),
        CalculatorEntry("calc_voltage_divider", "Voltage Divider", "Loaded & Unloaded Resistor Dividers", "RESISTIVE", Icons.Default.ElectricMeter, ElectricGreen, "calc_voltage_divider"),
        CalculatorEntry("calc_battery", "Battery Pack Designer", "S/P Cell Config, Wh Energy & Runtime", "POWER", Icons.Default.BatteryChargingFull, PhosphorCyan, "calc_battery"),
        CalculatorEntry("calc_wire", "Wire & Cable Voltage Drop", "AWG, mm², Copper/Alum Loop Loss", "POWER", Icons.Default.Cable, TechAmber, "calc_wire"),
        CalculatorEntry("calc_capacitor", "Capacitor Codes & RC Filters", "3-Digit EIA, RC Time Constant, Xc", "PASSIVE", Icons.Default.FlashOn, ElectricGreen, "calc_capacitor"),
        CalculatorEntry("calc_inductor", "Inductor Color Code & Reactance", "4-Band Inductor, XL Reactance & Combos", "PASSIVE", Icons.Default.ChangeCircle, TechAmber, "calc_inductor"),
        CalculatorEntry("calc_ac_power", "AC Power Analysis", "Single & 3-Phase Watts, VA, VAR & PF", "POWER", Icons.Default.ElectricMeter, PhosphorCyan, "calc_ac_power"),
        CalculatorEntry("calc_zener", "Zener Shunt Regulator", "Series Resistor & Power Dissipation", "SEMICONDUCTOR", Icons.Default.Shield, NeonRed, "calc_zener"),
        CalculatorEntry("calc_rf", "RF Suite & Antenna Length", "Dipole, Quarter-Wave & VSWR", "TIMING & RF", Icons.Default.Radio, PhosphorCyan, "calc_rf"),
        CalculatorEntry("calc_unit_conv", "Unit & Prefix Converter", "Voltage, Current, R, C, Freq, dBm", "RESISTIVE", Icons.Default.ChangeCircle, ElectricGreen, "calc_unit_conv")
    )

    val filtered = allCalculators.filter {
        if (selectedCategory == "ALL") true else it.category == selectedCategory
    }

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Engineering Calculators",
                subtitle = "${allCalculators.size} Specialized Precision Tools",
                onSearchClick = { onNavigate("search") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Category Filter Chips
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categories) { cat ->
                        val isSel = cat == selectedCategory
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .border(1.dp, if (isSel) ElectricGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                                .clickable { selectedCategory = cat }
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

            // Calculators List
            items(filtered) { calc ->
                val isFav = favorites.any { it.id == calc.id }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(calc.route) }
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f), RoundedCornerShape(14.dp)),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(1.dp, calc.color.copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = calc.icon,
                                    contentDescription = calc.title,
                                    tint = calc.color,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = calc.title,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = calc.description,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 11.sp
                                    ),
                                    maxLines = 1
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = {
                                    viewModel.toggleFavorite(calc.id, "CALCULATOR", calc.title, calc.description, calc.route)
                                }
                            ) {
                                Icon(
                                    imageVector = if (isFav) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Favorite",
                                    tint = if (isFav) TechAmber else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Open",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(60.dp)) }
        }
    }
}
