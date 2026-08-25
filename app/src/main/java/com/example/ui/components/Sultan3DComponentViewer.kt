package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CropRotate
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AdvancedComponent
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.ElectricGreen
import com.example.ui.theme.PhosphorCyan
import com.example.ui.theme.TechAmber

enum class ViewAngle {
    FRONT,
    TOP,
    SIDE,
    PERSPECTIVE_3D
}

@Composable
fun Sultan3DComponentViewer(
    component: AdvancedComponent,
    modifier: Modifier = Modifier
) {
    var selectedAngle by remember { mutableStateOf(ViewAngle.FRONT) }
    var zoomScale by remember { mutableFloatStateOf(1.0f) }
    var rotationAngle by remember { mutableFloatStateOf(0f) }
    var selectedPin by remember { mutableStateOf<Int?>(null) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
        border = androidx.compose.foundation.BorderStroke(1.dp, ElectricGreen.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with 3D Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CropRotate,
                        contentDescription = "3D View",
                        tint = PhosphorCyan,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "3D COMPONENT WORKSTATION",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = PhosphorCyan,
                            letterSpacing = 1.sp
                        )
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Black.copy(alpha = 0.6f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ElectricGreen.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = component.packageType,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = ElectricGreen,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main Interactive Viewport
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF040A08))
                    .border(1.dp, PhosphorCyan.copy(alpha = 0.2f), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Background Engineering Grid
                Box(
                    modifier = Modifier
                        .scale(zoomScale)
                        .rotate(rotationAngle)
                ) {
                    RealisticComponentRenderer(
                        type = component.threeDType,
                        label = component.partNumber,
                        modifier = Modifier.size(190.dp)
                    )
                }

                // Top View Angle Overlay Badges
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    ViewAngle.values().forEach { angle ->
                        val isSel = angle == selectedAngle
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isSel) ElectricGreen.copy(alpha = 0.2f) else Color.Black.copy(alpha = 0.5f),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSel) ElectricGreen else Color.White.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.clickable {
                                selectedAngle = angle
                                when (angle) {
                                    ViewAngle.FRONT -> { rotationAngle = 0f; zoomScale = 1.0f }
                                    ViewAngle.TOP -> { rotationAngle = 90f; zoomScale = 1.15f }
                                    ViewAngle.SIDE -> { rotationAngle = 180f; zoomScale = 0.95f }
                                    ViewAngle.PERSPECTIVE_3D -> { rotationAngle = 25f; zoomScale = 1.1f }
                                }
                            }
                        ) {
                            Text(
                                text = angle.name.replace("_", " "),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSel) ElectricGreen else Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                // Interactive Controls (Rotate / Zoom) on Bottom Right
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.Black.copy(alpha = 0.7f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PhosphorCyan),
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                rotationAngle = (rotationAngle + 45f) % 360f
                            }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.CropRotate,
                                contentDescription = "Rotate 45°",
                                tint = PhosphorCyan,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Surface(
                        shape = CircleShape,
                        color = Color.Black.copy(alpha = 0.7f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PhosphorCyan),
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                zoomScale = if (zoomScale >= 1.3f) 1.0f else zoomScale + 0.15f
                            }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.ZoomIn,
                                contentDescription = "Zoom",
                                tint = PhosphorCyan,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Quick Pinout Bar with Interactive Highlighting
            Text(
                text = "INTERACTIVE PIN CONFIGURATION (${component.pinCount} PINS):",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.8f)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                component.pins.forEach { pin ->
                    val isSel = selectedPin == pin.pinNumber
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { selectedPin = if (isSel) null else pin.pinNumber }
                            .border(
                                1.dp,
                                if (isSel) TechAmber else Color.White.copy(alpha = 0.1f),
                                RoundedCornerShape(8.dp)
                            ),
                        color = if (isSel) TechAmber.copy(alpha = 0.15f) else Color.Black.copy(alpha = 0.3f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .clip(CircleShape)
                                        .background(if (isSel) TechAmber else ElectricGreen.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${pin.pinNumber}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSel) Color.Black else ElectricGreen
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = pin.pinName,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    if (pin.description.isNotBlank()) {
                                        Text(
                                            text = pin.description,
                                            fontSize = 10.sp,
                                            color = Color.White.copy(alpha = 0.6f)
                                        )
                                    }
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = when (pin.type) {
                                    "Power" -> Color(0xFFFF1744).copy(alpha = 0.2f)
                                    "GND" -> Color(0xFF616161).copy(alpha = 0.3f)
                                    "Analog" -> PhosphorCyan.copy(alpha = 0.2f)
                                    "PWM" -> TechAmber.copy(alpha = 0.2f)
                                    "Comm" -> Color(0xFF7C4DFF).copy(alpha = 0.2f)
                                    else -> ElectricGreen.copy(alpha = 0.2f)
                                }
                            ) {
                                Text(
                                    text = pin.type,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = when (pin.type) {
                                        "Power" -> Color(0xFFFF5252)
                                        "GND" -> Color(0xFFB0BEC5)
                                        "Analog" -> PhosphorCyan
                                        "PWM" -> TechAmber
                                        "Comm" -> Color(0xFFB388FF)
                                        else -> ElectricGreen
                                    },
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
