package com.example.ui.screens

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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Memory
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PinoutsData
import com.example.model.PinDetail
import com.example.ui.components.SultanTopAppBar
import com.example.ui.theme.ElectricGreen
import com.example.ui.theme.NeonRed
import com.example.ui.theme.PhosphorCyan
import com.example.ui.theme.TechAmber
import com.example.ui.theme.TechPurple
import com.example.ui.viewmodel.SultanViewModel

@Composable
fun PinoutsListScreen(
    viewModel: SultanViewModel,
    onNavigate: (String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("ALL") }
    val favorites by viewModel.favoritesList.collectAsState()

    val categories = listOf("ALL", "Microcontrollers", "Development Boards", "Timer ICs", "Operational Amplifiers", "Voltage Regulators", "Transistors", "Power MOSFETs", "Connectors")

    val filtered = PinoutsData.allPinouts.filter {
        if (selectedCategory == "ALL") true else it.category.equals(selectedCategory, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = "Pinouts & IC Database",
                subtitle = "${PinoutsData.allPinouts.size} Standard Pinout References",
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

            items(filtered) { pinout ->
                val isFav = favorites.any { it.id == pinout.id }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate("pinout_detail/${pinout.id}") }
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
                                    .border(1.dp, ElectricGreen.copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Memory,
                                    contentDescription = pinout.name,
                                    tint = ElectricGreen,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = pinout.name,
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "(${pinout.pinCount} Pins)",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PhosphorCyan
                                    )
                                }
                                Text(
                                    text = "${pinout.category} • ${pinout.packageType}",
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
                                    viewModel.toggleFavorite(pinout.id, "PINOUT", pinout.name, "${pinout.category} (${pinout.pinCount} Pins)", "pinout_detail/${pinout.id}")
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

@Composable
fun PinoutDetailScreen(
    pinoutId: String,
    viewModel: SultanViewModel,
    onBack: () -> Unit
) {
    val pinout = PinoutsData.allPinouts.find { it.id == pinoutId } ?: PinoutsData.allPinouts[0]
    val isFav = viewModel.isFavorite(pinout.id)
    var selectedPin by remember { mutableStateOf<PinDetail?>(null) }

    Scaffold(
        topBar = {
            SultanTopAppBar(
                title = pinout.name,
                subtitle = "${pinout.packageType} (${pinout.pinCount} Pins)",
                onBackClick = onBack,
                isFavorite = isFav,
                onFavoriteToggle = {
                    viewModel.toggleFavorite(pinout.id, "PINOUT", pinout.name, "${pinout.category} (${pinout.pinCount} Pins)", "pinout_detail/${pinout.id}")
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
            // Visual Chip Diagram Header
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .border(1.dp, Color(0xFF00382B), RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF02140E))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height
                            val midX = w / 2f
                            val midY = h / 2f

                            // IC Body
                            val icWidth = 140f
                            val icHeight = 130f
                            drawRoundRect(
                                color = Color(0xFF1E2623),
                                topLeft = Offset(midX - icWidth / 2f, midY - icHeight / 2f),
                                size = Size(icWidth, icHeight),
                                cornerRadius = CornerRadius(10f, 10f)
                            )
                            // IC Notch
                            drawCircle(
                                color = Color(0xFF02140E),
                                radius = 10f,
                                center = Offset(midX, midY - icHeight / 2f)
                            )

                            // Draw Dual-In-Line (DIP) or multi pins on left & right
                            val halfCount = (pinout.pinCount / 2).coerceAtLeast(1)
                            val pinSpacing = (icHeight - 20f) / halfCount

                            for (i in 0 until halfCount) {
                                val py = (midY - icHeight / 2f + 10f) + i * pinSpacing + (pinSpacing / 2f)
                                // Left Pin
                                drawRect(
                                    color = Color(0xFFB0BEC5),
                                    topLeft = Offset(midX - icWidth / 2f - 24f, py - 4f),
                                    size = Size(24f, 8f)
                                )
                                // Right Pin
                                drawRect(
                                    color = Color(0xFFB0BEC5),
                                    topLeft = Offset(midX + icWidth / 2f, py - 4f),
                                    size = Size(24f, 8f)
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = pinout.name,
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Monospace,
                                color = ElectricGreen
                            )
                            Text(
                                text = pinout.packageType,
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }

            // Description
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Pinout Overview",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = PhosphorCyan)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = pinout.description,
                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp)
                        )
                    }
                }
            }

            // Interactive Pin Table
            item {
                Text(
                    text = "Pin Mapping & Functions Table:",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ElectricGreen)
                )
            }

            items(pinout.pins) { pin ->
                val isSelected = selectedPin?.pinNumber == pin.pinNumber
                val pinBadgeColor = when (pin.pinType) {
                    "Power" -> NeonRed
                    "Ground" -> Color.Gray
                    "Input", "Analog" -> TechAmber
                    "Output" -> ElectricGreen
                    "IO", "Digital" -> PhosphorCyan
                    else -> TechPurple
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedPin = if (isSelected) null else pin }
                        .border(
                            1.dp,
                            if (isSelected) ElectricGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.25f),
                            RoundedCornerShape(12.dp)
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) ElectricGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            // Pin Number Badge
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${pin.pinNumber}",
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 13.sp,
                                        color = ElectricGreen
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = pin.pinName,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = pin.description.ifEmpty { pin.pinFunction },
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = pinBadgeColor.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = pin.pinType,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = pinBadgeColor
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}
