package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.model.Component3DType

@Composable
fun RealisticComponentRenderer(
    type: Component3DType,
    label: String = "",
    modifier: Modifier = Modifier.size(160.dp),
    accentColor: Color = Color(0xFF00E676)
) {
    Box(
        modifier = modifier
            .background(Color(0xFF081210), RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFF00E676).copy(alpha = 0.3f), RoundedCornerShape(12.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            when (type) {
                Component3DType.TO92_TRANSISTOR -> drawTO92(label)
                Component3DType.TO220_PACKAGE -> drawTO220(label)
                Component3DType.DIP8_IC -> drawDIP(8, label)
                Component3DType.DIP14_IC -> drawDIP(14, label)
                Component3DType.DIP16_IC -> drawDIP(16, label)
                Component3DType.DIP28_IC -> drawDIP(28, label)
                Component3DType.SOIC8_SMD -> drawSOIC8(label)
                Component3DType.ELECTROLYTIC_CAP -> drawElectrolyticCap(label)
                Component3DType.CERAMIC_CAP -> drawCeramicCap(label)
                Component3DType.MOTOR_RUN_CAP -> drawMotorRunCap(label)
                Component3DType.RESISTOR_AXIAL -> drawResistor(label)
                Component3DType.DIODE_DO41 -> drawDiode(label)
                Component3DType.LED_5MM -> drawLED(label)
                Component3DType.RELAY_SPDT -> drawRelay(label)
                Component3DType.ESP32_BOARD -> drawESP32(label)
                Component3DType.ARDUINO_UNO -> drawArduino(label)
                Component3DType.ARDUINO_NANO -> drawArduinoNano(label)
                Component3DType.CEILING_FAN_MOTOR -> drawCeilingFanDiagram()
                Component3DType.TABLE_FAN_MOTOR -> drawTableFanDiagram()
            }
        }
    }
}

// 1. Realistic TO-92 Package
private fun DrawScope.drawTO92(partName: String) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h * 0.45f

    // 3 Metal Leads
    val leadColor = Color(0xFFC0C0C0)
    val leadShadow = Color(0xFF707070)
    val leadWidth = 4.dp.toPx()
    val leadSpacing = 16.dp.toPx()

    for (i in -1..1) {
        val lx = cx + i * leadSpacing
        // Lead shadow
        drawLine(leadShadow, Offset(lx + 1, cy + 20), Offset(lx + 1, h - 16), strokeWidth = leadWidth)
        // Lead shiny body
        drawLine(leadColor, Offset(lx, cy + 20), Offset(lx, h - 18), strokeWidth = leadWidth - 1, cap = StrokeCap.Round)
    }

    // Black Epoxy Body (TO-92 D-shape)
    val bodyRadius = 38.dp.toPx()
    val bodyPath = Path().apply {
        arcTo(
            rect = androidx.compose.ui.geometry.Rect(
                cx - bodyRadius,
                cy - bodyRadius,
                cx + bodyRadius,
                cy + bodyRadius
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 180f,
            forceMoveTo = true
        )
        lineTo(cx + bodyRadius * 0.9f, cy + 18)
        lineTo(cx - bodyRadius * 0.9f, cy + 18)
        close()
    }

    // Gradient Epoxy Fill
    drawPath(
        path = bodyPath,
        brush = Brush.verticalGradient(
            colors = listOf(Color(0xFF333333), Color(0xFF1E1E1E), Color(0xFF111111)),
            startY = cy - bodyRadius,
            endY = cy + 20
        )
    )
    drawPath(
        path = bodyPath,
        color = Color(0xFF4FC3F7).copy(alpha = 0.4f),
        style = Stroke(width = 1.5.dp.toPx())
    )

    // Flat front face highlight
    drawRoundRect(
        color = Color(0xFF222222),
        topLeft = Offset(cx - bodyRadius * 0.75f, cy - 8),
        size = Size(bodyRadius * 1.5f, 22.dp.toPx()),
        cornerRadius = CornerRadius(4.dp.toPx())
    )

    // Laser etched text simulation
    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.argb(220, 200, 220, 210)
        textSize = 22f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText(if (partName.isNotBlank()) partName else "BC547", cx, cy + 8, textPaint)
}

// 2. Realistic TO-220 Package (Transistor / MOSFET / Voltage Regulator)
private fun DrawScope.drawTO220(partName: String) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h * 0.48f

    // 3 Pins at bottom
    val leadColor = Color(0xFFD6D6D6)
    val leadWidth = 5.dp.toPx()
    val leadSpacing = 20.dp.toPx()
    for (i in -1..1) {
        val lx = cx + i * leadSpacing
        drawLine(Color(0xFF555555), Offset(lx + 1, cy + 24), Offset(lx + 1, h - 12), strokeWidth = leadWidth)
        drawLine(leadColor, Offset(lx, cy + 24), Offset(lx, h - 14), strokeWidth = leadWidth - 1, cap = StrokeCap.Square)
    }

    // Metal Mounting Tab (Al/Copper)
    val tabWidth = 84.dp.toPx()
    val tabHeight = 36.dp.toPx()
    val tabTop = cy - 54.dp.toPx()

    drawRoundRect(
        brush = Brush.horizontalGradient(
            listOf(Color(0xFF8E8E8E), Color(0xFFDCDCDC), Color(0xFFA6A6A6), Color(0xFF757575))
        ),
        topLeft = Offset(cx - tabWidth / 2, tabTop),
        size = Size(tabWidth, tabHeight),
        cornerRadius = CornerRadius(4.dp.toPx())
    )

    // Tab Mounting Hole
    drawCircle(
        color = Color(0xFF1A1A1A),
        radius = 8.dp.toPx(),
        center = Offset(cx, tabTop + tabHeight * 0.45f)
    )
    drawCircle(
        color = Color(0xFFB0B0B0),
        radius = 8.dp.toPx(),
        center = Offset(cx, tabTop + tabHeight * 0.45f),
        style = Stroke(width = 1.dp.toPx())
    )

    // Dark Molded Plastic Body
    val bodyWidth = 84.dp.toPx()
    val bodyHeight = 52.dp.toPx()
    val bodyTop = tabTop + tabHeight - 6.dp.toPx()

    drawRoundRect(
        brush = Brush.verticalGradient(
            listOf(Color(0xFF2E2E2E), Color(0xFF1C1C1C), Color(0xFF141414))
        ),
        topLeft = Offset(cx - bodyWidth / 2, bodyTop),
        size = Size(bodyWidth, bodyHeight),
        cornerRadius = CornerRadius(3.dp.toPx())
    )

    drawRoundRect(
        color = Color(0xFF00E676).copy(alpha = 0.5f),
        topLeft = Offset(cx - bodyWidth / 2, bodyTop),
        size = Size(bodyWidth, bodyHeight),
        cornerRadius = CornerRadius(3.dp.toPx()),
        style = Stroke(width = 1.dp.toPx())
    )

    // Laser Marked Text
    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.argb(230, 230, 240, 235)
        textSize = 24f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText(if (partName.isNotBlank()) partName else "IRFZ44N", cx, bodyTop + bodyHeight * 0.55f, textPaint)
}

// 3. Realistic Dual In-Line Package (DIP-8, DIP-14, DIP-16, DIP-28)
private fun DrawScope.drawDIP(pins: Int, partName: String) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f

    val bodyWidth = 60.dp.toPx()
    val bodyHeight = when (pins) {
        8 -> 70.dp.toPx()
        14 -> 95.dp.toPx()
        16 -> 105.dp.toPx()
        else -> 125.dp.toPx()
    }
    val bodyTop = cy - bodyHeight / 2f

    val pinsPerSide = pins / 2
    val pinSpacing = bodyHeight / (pinsPerSide + 0.5f)

    // Draw Shiny Tin Pins
    val pinLength = 16.dp.toPx()
    val pinThickness = 4.dp.toPx()
    val pinColor = Color(0xFFCCCCCC)

    for (i in 0 until pinsPerSide) {
        val py = bodyTop + (i + 0.75f) * pinSpacing

        // Left Pins (1 to N/2)
        drawLine(pinColor, Offset(cx - bodyWidth / 2 - pinLength, py), Offset(cx - bodyWidth / 2, py), strokeWidth = pinThickness, cap = StrokeCap.Square)

        // Right Pins (N down to N/2 + 1)
        drawLine(pinColor, Offset(cx + bodyWidth / 2, py), Offset(cx + bodyWidth / 2 + pinLength, py), strokeWidth = pinThickness, cap = StrokeCap.Square)
    }

    // Black Ceramic/Plastic Body
    drawRoundRect(
        brush = Brush.horizontalGradient(
            listOf(Color(0xFF222222), Color(0xFF141414), Color(0xFF252525))
        ),
        topLeft = Offset(cx - bodyWidth / 2, bodyTop),
        size = Size(bodyWidth, bodyHeight),
        cornerRadius = CornerRadius(4.dp.toPx())
    )

    // Green/Cyan Outline
    drawRoundRect(
        color = Color(0xFF00E5FF).copy(alpha = 0.5f),
        topLeft = Offset(cx - bodyWidth / 2, bodyTop),
        size = Size(bodyWidth, bodyHeight),
        cornerRadius = CornerRadius(4.dp.toPx()),
        style = Stroke(width = 1.dp.toPx())
    )

    // Orientation Notch (Half-circle at top)
    val notchRadius = 7.dp.toPx()
    drawCircle(
        color = Color(0xFF0D0D0D),
        radius = notchRadius,
        center = Offset(cx, bodyTop)
    )

    // Pin 1 Dot Marker
    drawCircle(
        color = Color(0xFF444444),
        radius = 3.dp.toPx(),
        center = Offset(cx - bodyWidth / 2 + 8.dp.toPx(), bodyTop + 10.dp.toPx())
    )

    // Laser Marking Text
    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.argb(220, 200, 240, 255)
        textSize = if (pins >= 28) 18f else 22f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText(
        if (partName.isNotBlank()) partName else "DIP-$pins",
        cx,
        cy + 4.dp.toPx(),
        textPaint
    )
}

// 4. SOIC-8 SMD
private fun DrawScope.drawSOIC8(partName: String) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f
    val bodyWidth = 44.dp.toPx()
    val bodyHeight = 54.dp.toPx()
    val bodyTop = cy - bodyHeight / 2f

    // Gull-wing pins
    val pinColor = Color(0xFFDDDDDD)
    val pinSpacing = bodyHeight / 4.5f
    for (i in 0..3) {
        val py = bodyTop + (i + 0.75f) * pinSpacing
        // Left
        drawLine(pinColor, Offset(cx - bodyWidth / 2 - 8.dp.toPx(), py), Offset(cx - bodyWidth / 2, py), strokeWidth = 3.dp.toPx())
        // Right
        drawLine(pinColor, Offset(cx + bodyWidth / 2, py), Offset(cx + bodyWidth / 2 + 8.dp.toPx(), py), strokeWidth = 3.dp.toPx())
    }

    drawRoundRect(
        brush = Brush.verticalGradient(listOf(Color(0xFF262626), Color(0xFF141414))),
        topLeft = Offset(cx - bodyWidth / 2, bodyTop),
        size = Size(bodyWidth, bodyHeight),
        cornerRadius = CornerRadius(2.dp.toPx())
    )

    // Pin 1 dot
    drawCircle(Color(0xFF00E5FF), 2.5.dp.toPx(), Offset(cx - bodyWidth / 2 + 5.dp.toPx(), bodyTop + 6.dp.toPx()))

    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 16f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText(if (partName.isNotBlank()) partName else "SOIC-8", cx, cy + 4, textPaint)
}

// 5. Realistic Electrolytic Capacitor (Aluminum Can with Negative Stripe)
private fun DrawScope.drawElectrolyticCap(label: String) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h * 0.45f

    // Radial leads at bottom (Longer = Positive, Shorter = Negative)
    val leadColor = Color(0xFFC0C0C0)
    // Left lead (Positive Anode - longer)
    drawLine(leadColor, Offset(cx - 12.dp.toPx(), cy + 30.dp.toPx()), Offset(cx - 12.dp.toPx(), h - 10.dp.toPx()), strokeWidth = 3.dp.toPx(), cap = StrokeCap.Round)
    // Right lead (Negative Cathode - shorter)
    drawLine(leadColor, Offset(cx + 12.dp.toPx(), cy + 30.dp.toPx()), Offset(cx + 12.dp.toPx(), h - 22.dp.toPx()), strokeWidth = 3.dp.toPx(), cap = StrokeCap.Round)

    // Can body
    val canWidth = 56.dp.toPx()
    val canHeight = 76.dp.toPx()
    val canTop = cy - canHeight / 2f

    // Dark Blue / Black PVC sleeve
    drawRoundRect(
        brush = Brush.horizontalGradient(
            listOf(Color(0xFF0D253A), Color(0xFF1B4965), Color(0xFF0D253A))
        ),
        topLeft = Offset(cx - canWidth / 2, canTop),
        size = Size(canWidth, canHeight),
        cornerRadius = CornerRadius(6.dp.toPx())
    )

    // Negative Stripe on right side (Gold or Light Gray with '-' signs)
    val stripeWidth = 14.dp.toPx()
    drawRoundRect(
        color = Color(0xFFE0E0E0),
        topLeft = Offset(cx + canWidth / 2 - stripeWidth, canTop),
        size = Size(stripeWidth, canHeight),
        cornerRadius = CornerRadius(2.dp.toPx())
    )

    // Negative minus markings
    val minusPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.BLACK
        textSize = 20f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
    }
    drawContext.canvas.nativeCanvas.drawText("-", cx + canWidth / 2 - stripeWidth / 2, canTop + 24.dp.toPx(), minusPaint)
    drawContext.canvas.nativeCanvas.drawText("-", cx + canWidth / 2 - stripeWidth / 2, canTop + 48.dp.toPx(), minusPaint)

    // Capacitor value rating
    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 18f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText(if (label.isNotBlank()) label else "1000µF", cx - 6.dp.toPx(), cy + 4.dp.toPx(), textPaint)
    val voltPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.argb(200, 0, 229, 255)
        textSize = 14f
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText("25V", cx - 6.dp.toPx(), cy + 20.dp.toPx(), voltPaint)
}

// 6. Realistic Ceramic Capacitor (Orange / Tan Disc with 3-digit marking)
private fun DrawScope.drawCeramicCap(label: String) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h * 0.42f

    // Two wire leads
    val leadColor = Color(0xFFC0C0C0)
    drawLine(leadColor, Offset(cx - 10.dp.toPx(), cy + 18.dp.toPx()), Offset(cx - 10.dp.toPx(), h - 16.dp.toPx()), strokeWidth = 3.dp.toPx(), cap = StrokeCap.Round)
    drawLine(leadColor, Offset(cx + 10.dp.toPx(), cy + 18.dp.toPx()), Offset(cx + 10.dp.toPx(), h - 16.dp.toPx()), strokeWidth = 3.dp.toPx(), cap = StrokeCap.Round)

    // Disc body (Warm orange/yellow ceramic glaze)
    val radius = 32.dp.toPx()
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0xFFFFB74D), Color(0xFFE65100), Color(0xFFBF360C)),
            center = Offset(cx - 8.dp.toPx(), cy - 8.dp.toPx()),
            radius = radius
        ),
        radius = radius,
        center = Offset(cx, cy)
    )

    // Ceramic Code Label (e.g. 104)
    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.BLACK
        textSize = 24f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText(if (label.isNotBlank()) label else "104", cx, cy + 8.dp.toPx(), textPaint)
}

// 7. Motor Run / Start Capacitor (CBB61 Box / Can with Spade Terminals)
private fun DrawScope.drawMotorRunCap(label: String) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f

    // Black plastic box (CBB61)
    val boxWidth = 90.dp.toPx()
    val boxHeight = 65.dp.toPx()
    val boxTop = cy - boxHeight / 2f

    // Faston Spade Terminals / Wires on Top
    val termColor = Color(0xFFD6D6D6)
    drawRoundRect(termColor, Offset(cx - 24.dp.toPx(), boxTop - 16.dp.toPx()), Size(10.dp.toPx(), 18.dp.toPx()), CornerRadius(2.dp.toPx()))
    drawRoundRect(termColor, Offset(cx + 14.dp.toPx(), boxTop - 16.dp.toPx()), Size(10.dp.toPx(), 18.dp.toPx()), CornerRadius(2.dp.toPx()))

    // Main Box Body
    drawRoundRect(
        brush = Brush.verticalGradient(listOf(Color(0xFF2C2C2C), Color(0xFF141414))),
        topLeft = Offset(cx - boxWidth / 2, boxTop),
        size = Size(boxWidth, boxHeight),
        cornerRadius = CornerRadius(6.dp.toPx())
    )
    drawRoundRect(
        color = Color(0xFFFFD600).copy(alpha = 0.6f),
        topLeft = Offset(cx - boxWidth / 2, boxTop),
        size = Size(boxWidth, boxHeight),
        cornerRadius = CornerRadius(6.dp.toPx()),
        style = Stroke(width = 1.5.dp.toPx())
    )

    // Label
    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 18f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText("CBB61", cx, cy - 8.dp.toPx(), textPaint)

    val capPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.YELLOW
        textSize = 20f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText(if (label.isNotBlank()) label else "2.5µF 450VAC", cx, cy + 14.dp.toPx(), capPaint)
}

// 8. Resistor Leaded
private fun DrawScope.drawResistor(label: String) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f

    // Axial leads
    val leadColor = Color(0xFFCCCCCC)
    drawLine(leadColor, Offset(16.dp.toPx(), cy), Offset(w - 16.dp.toPx(), cy), strokeWidth = 3.dp.toPx())

    // Ceramic body
    val bodyWidth = 80.dp.toPx()
    val bodyHeight = 28.dp.toPx()
    drawRoundRect(
        color = Color(0xFFD2B48C), // Tan body
        topLeft = Offset(cx - bodyWidth / 2, cy - bodyHeight / 2),
        size = Size(bodyWidth, bodyHeight),
        cornerRadius = CornerRadius(14.dp.toPx())
    )

    // Color Bands (e.g. Brown, Black, Red, Gold = 1kΩ 5%)
    val bandWidth = 8.dp.toPx()
    val bandHeight = bodyHeight
    val colors = listOf(Color(0xFF8B4513), Color(0xFF000000), Color(0xFFFF0000), Color(0xFFFFD700))
    val offsets = listOf(-26.dp.toPx(), -10.dp.toPx(), 6.dp.toPx(), 22.dp.toPx())

    colors.forEachIndexed { i, c ->
        drawRect(c, Offset(cx + offsets[i], cy - bandHeight / 2), Size(bandWidth, bandHeight))
    }
}

// 9. Diode DO-41
private fun DrawScope.drawDiode(label: String) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f

    // Axial leads
    drawLine(Color(0xFFCCCCCC), Offset(16.dp.toPx(), cy), Offset(w - 16.dp.toPx(), cy), strokeWidth = 3.dp.toPx())

    // Black cylindrical body
    val bodyWidth = 70.dp.toPx()
    val bodyHeight = 26.dp.toPx()
    drawRoundRect(
        brush = Brush.verticalGradient(listOf(Color(0xFF333333), Color(0xFF141414))),
        topLeft = Offset(cx - bodyWidth / 2, cy - bodyHeight / 2),
        size = Size(bodyWidth, bodyHeight),
        cornerRadius = CornerRadius(4.dp.toPx())
    )

    // Silver Cathode Ring Band
    val bandWidth = 10.dp.toPx()
    drawRect(
        color = Color(0xFFE0E0E0),
        topLeft = Offset(cx + bodyWidth / 2 - bandWidth - 6.dp.toPx(), cy - bodyHeight / 2),
        size = Size(bandWidth, bodyHeight)
    )

    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 16f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText(if (label.isNotBlank()) label else "1N4007", cx - 8.dp.toPx(), cy + 6.dp.toPx(), textPaint)
}

// 10. LED 5mm
private fun DrawScope.drawLED(label: String) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h * 0.4f

    // Leads (Anode longer, Cathode shorter with flat rim)
    drawLine(Color(0xFFCCCCCC), Offset(cx - 8.dp.toPx(), cy + 24.dp.toPx()), Offset(cx - 8.dp.toPx(), h - 14.dp.toPx()), strokeWidth = 3.dp.toPx())
    drawLine(Color(0xFFCCCCCC), Offset(cx + 8.dp.toPx(), cy + 24.dp.toPx()), Offset(cx + 8.dp.toPx(), h - 26.dp.toPx()), strokeWidth = 3.dp.toPx())

    // Epoxy Dome (Translucent Glowing Red/Green)
    val domeRadius = 26.dp.toPx()
    drawCircle(
        brush = Brush.radialGradient(
            listOf(Color(0xFF00FF88), Color(0xFF00C853), Color(0xFF00796B)),
            center = Offset(cx - 6.dp.toPx(), cy - 6.dp.toPx()),
            radius = domeRadius
        ),
        radius = domeRadius,
        center = Offset(cx, cy)
    )
}

// 11. Relay SPDT
private fun DrawScope.drawRelay(label: String) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f

    val rw = 80.dp.toPx()
    val rh = 70.dp.toPx()

    // Blue Sugar Cube Relay (Songle Style)
    drawRoundRect(
        brush = Brush.verticalGradient(listOf(Color(0xFF1E88E5), Color(0xFF0D47A1))),
        topLeft = Offset(cx - rw / 2, cy - rh / 2),
        size = Size(rw, rh),
        cornerRadius = CornerRadius(6.dp.toPx())
    )

    drawRoundRect(
        color = Color(0xFF64B5F6),
        topLeft = Offset(cx - rw / 2, cy - rh / 2),
        size = Size(rw, rh),
        cornerRadius = CornerRadius(6.dp.toPx()),
        style = Stroke(width = 1.dp.toPx())
    )

    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 18f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText("RELAY", cx, cy - 6.dp.toPx(), textPaint)

    val subPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.YELLOW
        textSize = 14f
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText("5V / 10A 250V", cx, cy + 14.dp.toPx(), subPaint)
}

// 12. ESP32 Board
private fun DrawScope.drawESP32(label: String) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f

    // Matte Black PCB
    val bw = 70.dp.toPx()
    val bh = 110.dp.toPx()
    drawRoundRect(
        color = Color(0xFF1B1B1B),
        topLeft = Offset(cx - bw / 2, cy - bh / 2),
        size = Size(bw, bh),
        cornerRadius = CornerRadius(4.dp.toPx())
    )

    // Metal RF Shielding Can at top
    drawRoundRect(
        color = Color(0xFFBDBDBD),
        topLeft = Offset(cx - bw / 2 + 8.dp.toPx(), cy - bh / 2 + 8.dp.toPx()),
        size = Size(bw - 16.dp.toPx(), 45.dp.toPx()),
        cornerRadius = CornerRadius(2.dp.toPx())
    )

    // Golden PCB Wi-Fi Antenna Trace at Top Edge
    drawRect(
        color = Color(0xFFFFD700),
        topLeft = Offset(cx - 20.dp.toPx(), cy - bh / 2 + 2.dp.toPx()),
        size = Size(40.dp.toPx(), 4.dp.toPx())
    )

    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.BLACK
        textSize = 16f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText("ESP-WROOM-32", cx, cy - bh / 2 + 32.dp.toPx(), textPaint)

    // Micro-USB Port at bottom
    drawRoundRect(
        color = Color(0xFFE0E0E0),
        topLeft = Offset(cx - 12.dp.toPx(), cy + bh / 2 - 12.dp.toPx()),
        size = Size(24.dp.toPx(), 12.dp.toPx()),
        cornerRadius = CornerRadius(2.dp.toPx())
    )
}

// 13. Arduino Uno
private fun DrawScope.drawArduino(label: String) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f

    // Classic Arduino Blue PCB
    val bw = 90.dp.toPx()
    val bh = 70.dp.toPx()
    drawRoundRect(
        color = Color(0xFF00878F), // Teal / Arduino Blue
        topLeft = Offset(cx - bw / 2, cy - bh / 2),
        size = Size(bw, bh),
        cornerRadius = CornerRadius(4.dp.toPx())
    )

    // USB Type-B Port (Metal box on left top)
    drawRoundRect(
        color = Color(0xFFE0E0E0),
        topLeft = Offset(cx - bw / 2 - 4.dp.toPx(), cy - bh / 2 + 8.dp.toPx()),
        size = Size(18.dp.toPx(), 20.dp.toPx()),
        cornerRadius = CornerRadius(2.dp.toPx())
    )

    // DC Barrel Jack (Black box on left bottom)
    drawRoundRect(
        color = Color(0xFF1E1E1E),
        topLeft = Offset(cx - bw / 2 - 4.dp.toPx(), cy + bh / 2 - 26.dp.toPx()),
        size = Size(22.dp.toPx(), 18.dp.toPx()),
        cornerRadius = CornerRadius(2.dp.toPx())
    )

    // ATmega328P DIP Socket in center right
    drawRoundRect(
        color = Color(0xFF121212),
        topLeft = Offset(cx + 4.dp.toPx(), cy - 10.dp.toPx()),
        size = Size(36.dp.toPx(), 16.dp.toPx()),
        cornerRadius = CornerRadius(2.dp.toPx())
    )

    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 16f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.SANS_SERIF
    }
    drawContext.canvas.nativeCanvas.drawText("ARDUINO UNO", cx + 8.dp.toPx(), cy + 24.dp.toPx(), textPaint)
}

// 14. Arduino Nano
private fun DrawScope.drawArduinoNano(label: String) {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f

    val bw = 48.dp.toPx()
    val bh = 95.dp.toPx()
    drawRoundRect(
        color = Color(0xFF007A87),
        topLeft = Offset(cx - bw / 2, cy - bh / 2),
        size = Size(bw, bh),
        cornerRadius = CornerRadius(3.dp.toPx())
    )

    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 15f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText("NANO V3", cx, cy + 4, textPaint)
}

// 15. Ceiling Fan Stator / Motor Connection Diagram
private fun DrawScope.drawCeilingFanDiagram() {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f

    // Motor Outer Rotor Ring
    drawCircle(
        color = Color(0xFF37474F),
        radius = 42.dp.toPx(),
        center = Offset(cx, cy - 8.dp.toPx())
    )
    drawCircle(
        color = Color(0xFF00E676),
        radius = 42.dp.toPx(),
        center = Offset(cx, cy - 8.dp.toPx()),
        style = Stroke(width = 2.dp.toPx())
    )

    // Stator Core (Inner Circle with Copper Windings)
    drawCircle(
        color = Color(0xFFBF360C), // Copper winding
        radius = 24.dp.toPx(),
        center = Offset(cx, cy - 8.dp.toPx())
    )

    // 3 Terminal Wires coming down
    val redWire = Color(0xFFFF1744)
    val blackWire = Color(0xFF212121)
    val yellowWire = Color(0xFFFFD600)

    // Red (Live / Phase)
    drawLine(redWire, Offset(cx - 24.dp.toPx(), cy + 24.dp.toPx()), Offset(cx - 24.dp.toPx(), h - 10.dp.toPx()), strokeWidth = 4.dp.toPx())
    // Black (Running Winding / Neutral)
    drawLine(blackWire, Offset(cx, cy + 24.dp.toPx()), Offset(cx, h - 10.dp.toPx()), strokeWidth = 4.dp.toPx())
    // Yellow (Starting Winding / Capacitor)
    drawLine(yellowWire, Offset(cx + 24.dp.toPx(), cy + 24.dp.toPx()), Offset(cx + 24.dp.toPx(), h - 10.dp.toPx()), strokeWidth = 4.dp.toPx())

    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 14f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText("CEILING FAN", cx, cy - 8.dp.toPx(), textPaint)
}

// 16. Table Fan Motor Diagram
private fun DrawScope.drawTableFanDiagram() {
    val w = size.width
    val h = size.height
    val cx = w / 2f
    val cy = h / 2f

    // Cage / Motor Hub
    drawCircle(Color(0xFF263238), 38.dp.toPx(), Offset(cx, cy - 10.dp.toPx()))
    drawCircle(Color(0xFF00E5FF), 38.dp.toPx(), Offset(cx, cy - 10.dp.toPx()), style = Stroke(width = 2.dp.toPx()))

    // Multi-speed harness wires (5 wires)
    val colors = listOf(Color(0xFFFF1744), Color(0xFFFFFFFF), Color(0xFF2979FF), Color(0xFF212121), Color(0xFFFFD600))
    val spacing = 12.dp.toPx()
    colors.forEachIndexed { i, c ->
        val x = cx - 24.dp.toPx() + i * spacing
        drawLine(c, Offset(x, cy + 20.dp.toPx()), Offset(x, h - 8.dp.toPx()), strokeWidth = 3.dp.toPx())
    }

    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 14f
        isFakeBoldText = true
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText("TABLE FAN", cx, cy - 10.dp.toPx(), textPaint)
}
