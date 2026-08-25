package com.example.utils

import java.util.Locale
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

object CalculatorEngine {

    // Format double helper with scientific notation handling and configurable precision
    fun formatNumber(value: Double, precision: Int = 3): String {
        if (value.isNaN()) return "NaN"
        if (value.isInfinite()) return if (value > 0) "∞" else "-∞"
        val absVal = abs(value)
        return if (absVal == 0.0) {
            "0"
        } else if (absVal < 0.0001 || absVal >= 1_000_000.0) {
            String.format(Locale.US, "%.${precision}e", value)
        } else {
            String.format(Locale.US, "%.${precision}f", value).trimEnd('0').trimEnd('.')
        }
    }

    fun formatEngineering(value: Double, unit: String): String {
        if (value.isNaN() || value.isInfinite() || value == 0.0) return "0 $unit"
        val absVal = abs(value)
        return when {
            absVal >= 1e12 -> "${formatNumber(value / 1e12)} T$unit"
            absVal >= 1e9 -> "${formatNumber(value / 1e9)} G$unit"
            absVal >= 1e6 -> "${formatNumber(value / 1e6)} M$unit"
            absVal >= 1e3 -> "${formatNumber(value / 1e3)} k$unit"
            absVal >= 1.0 -> "${formatNumber(value)} $unit"
            absVal >= 1e-3 -> "${formatNumber(value * 1e3)} m$unit"
            absVal >= 1e-6 -> "${formatNumber(value * 1e6)} µ$unit"
            absVal >= 1e-9 -> "${formatNumber(value * 1e9)} n$unit"
            absVal >= 1e-12 -> "${formatNumber(value * 1e12)} p$unit"
            else -> "${formatNumber(value)} $unit"
        }
    }

    // --- 1. RESISTOR COLOR CODE ---
    data class ResistorBandColor(
        val name: String,
        val hexColor: Long,
        val digit: Int?,
        val multiplier: Double,
        val tolerancePercent: Double?,
        val tempcoPpm: Int? = null
    )

    val resistorColors = listOf(
        ResistorBandColor("Black", 0xFF212121, 0, 1.0, null, 250),
        ResistorBandColor("Brown", 0xFF6D4C41, 1, 10.0, 1.0, 100),
        ResistorBandColor("Red", 0xFFE53935, 2, 100.0, 2.0, 50),
        ResistorBandColor("Orange", 0xFFFF9800, 3, 1_000.0, 0.05, 15),
        ResistorBandColor("Yellow", 0xFFFFEB3B, 4, 10_000.0, 0.02, 25),
        ResistorBandColor("Green", 0xFF4CAF50, 5, 100_000.0, 0.5, 20),
        ResistorBandColor("Blue", 0xFF1E88E5, 6, 1_000_000.0, 0.25, 10),
        ResistorBandColor("Violet", 0xFF8E24AA, 7, 10_000_000.0, 0.1, 5),
        ResistorBandColor("Gray", 0xFF757575, 8, 100_000_000.0, 0.01, 1),
        ResistorBandColor("White", 0xFFECEFF1, 9, 1_000_000_000.0, null, null),
        ResistorBandColor("Gold", 0xFFFFD700, null, 0.1, 5.0, null),
        ResistorBandColor("Silver", 0xFFB0BEC5, null, 0.01, 10.0, null)
    )

    fun calculateResistor4Band(b1: Int, b2: Int, multIdx: Int, tolIdx: Int): CalculationResult {
        val d1 = resistorColors[b1].digit ?: 0
        val d2 = resistorColors[b2].digit ?: 0
        val mult = resistorColors[multIdx].multiplier
        val tol = resistorColors[tolIdx].tolerancePercent ?: 5.0

        val baseVal = (d1 * 10 + d2) * mult
        val minVal = baseVal * (1.0 - tol / 100.0)
        val maxVal = baseVal * (1.0 + tol / 100.0)

        val formatted = "${formatEngineering(baseVal, "Ω")} ±${formatNumber(tol)}%"
        val steps = "Significant digits: ($d1$d2) = ${d1 * 10 + d2}\n" +
                "Multiplier: ×${formatEngineering(mult, "Ω")}\n" +
                "Resistance = ${d1 * 10 + d2} × $mult = ${formatEngineering(baseVal, "Ω")}\n" +
                "Tolerance range: ${formatEngineering(minVal, "Ω")} — ${formatEngineering(maxVal, "Ω")}"

        return CalculationResult(formatted, steps, baseVal)
    }

    fun calculateResistor5Band(b1: Int, b2: Int, b3: Int, multIdx: Int, tolIdx: Int): CalculationResult {
        val d1 = resistorColors[b1].digit ?: 0
        val d2 = resistorColors[b2].digit ?: 0
        val d3 = resistorColors[b3].digit ?: 0
        val mult = resistorColors[multIdx].multiplier
        val tol = resistorColors[tolIdx].tolerancePercent ?: 1.0

        val baseVal = (d1 * 100 + d2 * 10 + d3) * mult
        val minVal = baseVal * (1.0 - tol / 100.0)
        val maxVal = baseVal * (1.0 + tol / 100.0)

        val formatted = "${formatEngineering(baseVal, "Ω")} ±${formatNumber(tol)}%"
        val steps = "Significant digits: ($d1$d2$d3) = ${d1 * 100 + d2 * 10 + d3}\n" +
                "Multiplier: ×${formatEngineering(mult, "Ω")}\n" +
                "Resistance = ${d1 * 100 + d2 * 10 + d3} × $mult = ${formatEngineering(baseVal, "Ω")}\n" +
                "Tolerance range: ${formatEngineering(minVal, "Ω")} — ${formatEngineering(maxVal, "Ω")}"

        return CalculationResult(formatted, steps, baseVal)
    }

    fun reverseResistorColor(resistanceOhms: Double, is5Band: Boolean = false): List<Int> {
        if (resistanceOhms <= 0.0) return if (is5Band) listOf(1, 0, 0, 0, 1) else listOf(1, 0, 0, 10)
        var temp = resistanceOhms
        var exponent = 0
        while (temp >= (if (is5Band) 1000.0 else 100.0)) {
            temp /= 10.0
            exponent++
        }
        while (temp < (if (is5Band) 100.0 else 10.0) && exponent > -2) {
            temp *= 10.0
            exponent--
        }
        val intDigits = temp.roundToInt().coerceIn(1, 999)
        if (is5Band) {
            val d1 = (intDigits / 100).coerceIn(0, 9)
            val d2 = ((intDigits / 10) % 10).coerceIn(0, 9)
            val d3 = (intDigits % 10).coerceIn(0, 9)
            val multIdx = when (exponent) {
                -2 -> 11
                -1 -> 10
                in 0..9 -> exponent
                else -> 0
            }
            return listOf(d1, d2, d3, multIdx, 1) // Brown 1%
        } else {
            val d1 = (intDigits / 10).coerceIn(0, 9)
            val d2 = (intDigits % 10).coerceIn(0, 9)
            val multIdx = when (exponent) {
                -2 -> 11
                -1 -> 10
                in 0..9 -> exponent
                else -> 0
            }
            return listOf(d1, d2, multIdx, 10) // Gold 5%
        }
    }

    // --- 2. SMD RESISTOR DECODER ---
    private val eia96Codes = mapOf(
        "01" to 100, "02" to 102, "03" to 105, "04" to 107, "05" to 110, "06" to 113, "07" to 115, "08" to 118,
        "09" to 121, "10" to 124, "11" to 127, "12" to 130, "13" to 133, "14" to 137, "15" to 140, "16" to 143,
        "17" to 147, "18" to 150, "19" to 154, "20" to 158, "21" to 162, "22" to 165, "23" to 169, "24" to 174,
        "25" to 178, "26" to 182, "27" to 187, "28" to 191, "29" to 196, "30" to 200, "31" to 205, "32" to 210,
        "33" to 215, "34" to 221, "35" to 226, "36" to 232, "37" to 237, "38" to 243, "39" to 249, "40" to 255,
        "41" to 261, "42" to 267, "43" to 274, "44" to 280, "45" to 287, "46" to 294, "47" to 301, "48" to 309,
        "49" to 316, "50" to 324, "51" to 332, "52" to 340, "53" to 348, "54" to 357, "55" to 365, "56" to 374,
        "57" to 383, "58" to 392, "59" to 402, "60" to 412, "61" to 422, "62" to 432, "63" to 442, "64" to 453,
        "65" to 464, "66" to 475, "67" to 487, "68" to 499, "69" to 511, "70" to 523, "71" to 536, "72" to 549,
        "73" to 562, "74" to 576, "75" to 590, "76" to 604, "77" to 619, "78" to 634, "79" to 649, "80" to 665,
        "81" to 681, "82" to 698, "83" to 715, "84" to 732, "85" to 750, "86" to 768, "87" to 787, "88" to 806,
        "89" to 825, "90" to 845, "91" to 866, "92" to 887, "93" to 909, "94" to 931, "95" to 953, "96" to 976
    )

    private val eia96Multipliers = mapOf(
        'Z' to 0.001, 'Y' to 0.01, 'X' to 0.1, 'A' to 1.0, 'B' to 10.0,
        'C' to 100.0, 'D' to 1000.0, 'E' to 10000.0, 'F' to 100000.0,
        's' to 0.001, 'R' to 0.01, 'H' to 10.0
    )

    fun decodeSmdResistor(rawCode: String): CalculationResult {
        val code = rawCode.trim().uppercase()
        if (code.isEmpty()) return CalculationResult("0 Ω", "Please enter an SMD code (e.g. 103, 4R7, 01C).", 0.0)

        // Special 0Ω jumper
        if (code == "0" || code == "00" || code == "000" || code == "0000") {
            return CalculationResult("0 Ω (Zero-Ohm Jumper)", "Zero-ohm link jumper used to bridge PCB tracks.", 0.0)
        }

        // R notation (e.g. 4R7, R05, 0R22, 2R20)
        if (code.contains('R')) {
            val parts = code.split('R')
            val whole = if (parts[0].isEmpty()) "0" else parts[0]
            val decimal = if (parts.size > 1 && parts[1].isNotEmpty()) parts[1] else "0"
            val numStr = "$whole.$decimal"
            val value = numStr.toDoubleOrNull() ?: return CalculationResult("Invalid", "Cannot parse '$code' as R-notation.", 0.0)
            return CalculationResult(
                formatEngineering(value, "Ω"),
                "R notation: 'R' represents the decimal point.\n$code = $numStr Ω",
                value
            )
        }

        // EIA-96 1% 3-character code (2 digits + 1 letter, e.g. 01C, 47A)
        if (code.length == 3 && code[0].isDigit() && code[1].isDigit() && code[2].isLetter()) {
            val codeDigits = code.substring(0, 2)
            val multChar = code[2]
            val base = eia96Codes[codeDigits]
            val mult = eia96Multipliers[multChar]
            if (base != null && mult != null) {
                val value = base * mult
                val steps = "EIA-96 Precision SMD Standard (±1%):\n" +
                        "Code '$codeDigits' corresponds to base value: $base\n" +
                        "Multiplier '$multChar' = ×$mult\n" +
                        "Resistance = $base × $mult = ${formatEngineering(value, "Ω")} ±1%"
                return CalculationResult("${formatEngineering(value, "Ω")} ±1%", steps, value)
            }
        }

        // 3-digit standard SMD (e.g. 103 -> 10 x 10^3 = 10k)
        if (code.length == 3 && code.all { it.isDigit() }) {
            val d1 = code[0].digitToInt()
            val d2 = code[1].digitToInt()
            val multExp = code[2].digitToInt()
            val base = d1 * 10 + d2
            val value = base * 10.0.pow(multExp)
            val steps = "3-Digit SMD Resistor (±5% standard):\n" +
                    "Significant digits: $d1$d2 = $base\n" +
                    "Multiplier: 10^$multExp = ${10.0.pow(multExp).toLong()}\n" +
                    "Resistance = $base × 10^$multExp = ${formatEngineering(value, "Ω")} ±5%"
            return CalculationResult("${formatEngineering(value, "Ω")} ±5%", steps, value)
        }

        // 4-digit precision SMD (e.g. 1002 -> 100 x 10^2 = 10k)
        if (code.length == 4 && code.all { it.isDigit() }) {
            val d1 = code[0].digitToInt()
            val d2 = code[1].digitToInt()
            val d3 = code[2].digitToInt()
            val multExp = code[3].digitToInt()
            val base = d1 * 100 + d2 * 10 + d3
            val value = base * 10.0.pow(multExp)
            val steps = "4-Digit Precision SMD Resistor (±1% standard):\n" +
                    "Significant digits: $d1$d2$d3 = $base\n" +
                    "Multiplier: 10^$multExp = ${10.0.pow(multExp).toLong()}\n" +
                    "Resistance = $base × 10^$multExp = ${formatEngineering(value, "Ω")} ±1%"
            return CalculationResult("${formatEngineering(value, "Ω")} ±1%", steps, value)
        }

        return CalculationResult("Invalid Code", "Unrecognized SMD format. Try 103, 4702, 4R7, or EIA-96 like 01C.", 0.0)
    }

    // --- 3. OHM'S LAW ---
    fun calculateOhmsLaw(
        vIn: Double?,
        iIn: Double?,
        rIn: Double?,
        timeSec: Double? = null
    ): CalculationResult {
        var v = vIn
        var i = iIn
        var r = rIn

        val count = listOfNotNull(v, i, r).size
        if (count < 2) {
            return CalculationResult("Insufficient Inputs", "Please enter any 2 parameters: Voltage, Current, or Resistance.", 0.0)
        }

        val stepList = mutableListOf<String>()

        if (v != null && i != null) {
            if (i == 0.0) return CalculationResult("Division by Zero", "Current cannot be zero.", 0.0)
            r = v / i
            stepList.add("Resistance calculated: R = V / I = $v V / $i A = ${formatEngineering(r, "Ω")}")
        } else if (v != null && r != null) {
            if (r == 0.0) return CalculationResult("Division by Zero", "Resistance cannot be zero.", 0.0)
            i = v / r
            stepList.add("Current calculated: I = V / R = $v V / $r Ω = ${formatEngineering(i, "A")}")
        } else if (i != null && r != null) {
            v = i * r
            stepList.add("Voltage calculated: V = I × R = $i A × $r Ω = ${formatEngineering(v, "V")}")
        }

        val finalV = v ?: 0.0
        val finalI = i ?: 0.0
        val finalR = r ?: 0.0
        val power = finalV * finalI

        stepList.add("Electric Power: P = V × I = $finalV × $finalI = ${formatEngineering(power, "W")}")

        var energyStr = ""
        if (timeSec != null && timeSec > 0) {
            val energyJoules = power * timeSec
            val energyWh = energyJoules / 3600.0
            stepList.add("Energy for $timeSec s: E = P × t = ${formatEngineering(energyJoules, "J")} (${formatNumber(energyWh)} Wh)")
            energyStr = " | Energy: ${formatEngineering(energyJoules, "J")}"
        }

        val summary = "V = ${formatEngineering(finalV, "V")} | I = ${formatEngineering(finalI, "A")} | R = ${formatEngineering(finalR, "Ω")} | P = ${formatEngineering(power, "W")}$energyStr"
        return CalculationResult(summary, stepList.joinToString("\n"), power)
    }

    // --- 4. CAPACITOR CALCULATIONS ---
    fun decodeCapacitorCode(code: String): CalculationResult {
        val trimmed = code.trim().uppercase()
        if (trimmed.isEmpty()) return CalculationResult("0 pF", "Enter 3-digit code (e.g. 104, 222).", 0.0)
        if (trimmed.length >= 3 && trimmed.take(3).all { it.isDigit() }) {
            val d1 = trimmed[0].digitToInt()
            val d2 = trimmed[1].digitToInt()
            val multExp = trimmed[2].digitToInt()
            val pF = (d1 * 10 + d2) * 10.0.pow(multExp)
            val nF = pF / 1000.0
            val uF = nF / 1000.0

            val tolChar = if (trimmed.length > 3) trimmed[3] else null
            val tolText = when (tolChar) {
                'J' -> "±5%"
                'K' -> "±10%"
                'M' -> "±20%"
                'Z' -> "+80%/-20%"
                else -> ""
            }

            val summary = "${formatNumber(uF, 4)} µF  (${formatNumber(nF, 2)} nF / ${formatNumber(pF, 0)} pF) $tolText".trim()
            val steps = "Capacitor 3-Digit EIA Marking:\n" +
                    "Base value: ($d1$d2) = ${d1 * 10 + d2}\n" +
                    "Multiplier: 10^$multExp = ${10.0.pow(multExp).toLong()} pF\n" +
                    "Capacitance = $pF pF\n" +
                    "= $nF nF\n" +
                    "= $uF µF" + if (tolText.isNotEmpty()) "\nTolerance letter '$tolChar' = $tolText" else ""
            return CalculationResult(summary, steps, uF)
        }
        return CalculationResult("Invalid Code", "Please enter a valid 3-digit capacitor code like 104 or 473K.", 0.0)
    }

    fun calculateCapacitiveReactance(freqHz: Double, capFarads: Double): CalculationResult {
        if (freqHz <= 0 || capFarads <= 0) return CalculationResult("Invalid Input", "Frequency and Capacitance must be > 0.", 0.0)
        val xc = 1.0 / (2.0 * PI * freqHz * capFarads)
        val summary = "Xc = ${formatEngineering(xc, "Ω")}"
        val steps = "Formula: Xc = 1 / (2 × π × f × C)\n" +
                "Xc = 1 / (2 × 3.14159 × $freqHz Hz × $capFarads F)\n" +
                "= ${formatEngineering(xc, "Ω")}"
        return CalculationResult(summary, steps, xc)
    }

    fun calculateRcTimeConstant(resistanceOhms: Double, capFarads: Double, supplyVolts: Double? = null): CalculationResult {
        if (resistanceOhms <= 0 || capFarads <= 0) return CalculationResult("Invalid Input", "R and C must be > 0.", 0.0)
        val tau = resistanceOhms * capFarads
        val fc = 1.0 / (2.0 * PI * resistanceOhms * capFarads)
        val t5tau = 5.0 * tau

        var extra = ""
        if (supplyVolts != null && supplyVolts > 0) {
            val v1tau = supplyVolts * 0.632
            extra = "\nVoltage after 1τ (63.2%): ${formatNumber(v1tau)} V\nVoltage after 5τ (99.3% full charge): ${formatNumber(supplyVolts * 0.993)} V"
        }

        val summary = "τ = ${formatEngineering(tau, "s")} | Cutoff fc = ${formatEngineering(fc, "Hz")}"
        val steps = "Time Constant: τ = R × C = $resistanceOhms Ω × $capFarads F = ${formatEngineering(tau, "s")}\n" +
                "Full charge/discharge time (5τ): ${formatEngineering(t5tau, "s")}\n" +
                "Low-Pass / High-Pass -3dB Cutoff Frequency: fc = 1 / (2 × π × R × C) = ${formatEngineering(fc, "Hz")}$extra"
        return CalculationResult(summary, steps, tau)
    }

    fun calculateCapacitorStoredEnergy(capFarads: Double, volts: Double): CalculationResult {
        if (capFarads <= 0 || volts < 0) return CalculationResult("Invalid Input", "C and V must be positive.", 0.0)
        val energyJoules = 0.5 * capFarads * volts.pow(2)
        val chargeCoulombs = capFarads * volts
        val summary = "E = ${formatEngineering(energyJoules, "J")} | Q = ${formatEngineering(chargeCoulombs, "C")}"
        val steps = "Formula: E = ½ × C × V²\n" +
                "E = 0.5 × $capFarads F × ($volts V)² = ${formatEngineering(energyJoules, "J")}\n" +
                "Stored electric charge: Q = C × V = ${formatEngineering(chargeCoulombs, "C")}"
        return CalculationResult(summary, steps, energyJoules)
    }

    // --- 5. INDUCTOR CALCULATIONS ---
    fun calculateInductiveReactance(freqHz: Double, indHenries: Double): CalculationResult {
        if (freqHz <= 0 || indHenries <= 0) return CalculationResult("Invalid Input", "Frequency and Inductance must be > 0.", 0.0)
        val xl = 2.0 * PI * freqHz * indHenries
        val summary = "Xl = ${formatEngineering(xl, "Ω")}"
        val steps = "Formula: Xl = 2 × π × f × L\n" +
                "Xl = 2 × 3.14159 × $freqHz Hz × $indHenries H\n" +
                "= ${formatEngineering(xl, "Ω")}"
        return CalculationResult(summary, steps, xl)
    }

    fun calculateRlTimeConstant(resistanceOhms: Double, indHenries: Double): CalculationResult {
        if (resistanceOhms <= 0 || indHenries <= 0) return CalculationResult("Invalid Input", "R and L must be > 0.", 0.0)
        val tau = indHenries / resistanceOhms
        val summary = "τ = ${formatEngineering(tau, "s")}"
        val steps = "Formula: τ = L / R\n" +
                "τ = $indHenries H / $resistanceOhms Ω = ${formatEngineering(tau, "s")}\n" +
                "Settling time (5τ): ${formatEngineering(5.0 * tau, "s")}"
        return CalculationResult(summary, steps, tau)
    }

    // --- 6. LED CURRENT LIMITING RESISTOR ---
    private val standardE24Resistors = doubleArrayOf(
        1.0, 1.1, 1.2, 1.3, 1.5, 1.6, 1.8, 2.0, 2.2, 2.4, 2.7, 3.0,
        3.3, 3.6, 3.9, 4.3, 4.7, 5.1, 5.6, 6.2, 6.8, 7.5, 8.2, 9.1
    )

    fun findNearestStandardResistor(targetOhms: Double): Double {
        if (targetOhms <= 0) return 1.0
        var decade = 1.0
        var testVal = targetOhms
        while (testVal >= 10.0) {
            testVal /= 10.0
            decade *= 10.0
        }
        while (testVal < 1.0) {
            testVal *= 10.0
            decade /= 10.0
        }
        var closest = standardE24Resistors[0] * decade
        var minDiff = Double.MAX_VALUE
        for (std in standardE24Resistors) {
            val candidate = std * decade
            val diff = abs(candidate - targetOhms)
            if (diff < minDiff) {
                minDiff = diff
                closest = candidate
            }
        }
        // If standard is slightly lower and would overcurrent, step up one
        return closest
    }

    fun calculateLedResistor(
        supplyVolts: Double,
        ledVf: Double,
        ledCurrentAmps: Double,
        ledCount: Int = 1,
        isSeries: Boolean = true
    ): CalculationResult {
        if (supplyVolts <= 0 || ledVf <= 0 || ledCurrentAmps <= 0 || ledCount <= 0) {
            return CalculationResult("Invalid Input", "All values must be greater than zero.", 0.0)
        }

        val totalVf = if (isSeries) ledVf * ledCount else ledVf
        val totalCurrent = if (isSeries) ledCurrentAmps else ledCurrentAmps * ledCount

        if (supplyVolts <= totalVf) {
            return CalculationResult(
                "Supply Voltage Too Low",
                "Supply voltage ($supplyVolts V) must be greater than total LED forward voltage ($totalVf V).",
                0.0
            )
        }

        val exactR = (supplyVolts - totalVf) / totalCurrent
        val stdR = findNearestStandardResistor(exactR)
        val actualCurrent = (supplyVolts - totalVf) / stdR
        val resistorPower = (supplyVolts - totalVf) * actualCurrent
        val recWattage = when {
            resistorPower <= 0.125 -> "0.25 W (1/4 Watt)"
            resistorPower <= 0.25 -> "0.5 W (1/2 Watt)"
            resistorPower <= 0.5 -> "1.0 W (1 Watt)"
            resistorPower <= 1.0 -> "2.0 W (2 Watt)"
            else -> "${formatNumber(resistorPower * 2.0, 1)} W Power Resistor"
        }

        val summary = "R = ${formatEngineering(stdR, "Ω")} (Exact: ${formatEngineering(exactR, "Ω")}) | Power: ${formatEngineering(resistorPower, "W")}"
        val steps = "Configuration: $ledCount LED(s) in " + (if (isSeries) "SERIES" else "PARALLEL") + "\n" +
                "Total LED forward drop: $totalVf V\n" +
                "Total design current: ${formatEngineering(totalCurrent, "A")}\n" +
                "Exact resistance: R = (Vs - Vf) / If = ($supplyVolts - $totalVf) / $totalCurrent = ${formatEngineering(exactR, "Ω")}\n" +
                "Recommended standard E24 resistor: ${formatEngineering(stdR, "Ω")}\n" +
                "Actual LED current with standard resistor: ${formatEngineering(actualCurrent, "A")}\n" +
                "Resistor power dissipation: P = ${formatEngineering(resistorPower, "W")}\n" +
                "Minimum recommended resistor power rating: $recWattage"

        return CalculationResult(summary, steps, stdR)
    }

    // --- 7. ZENER DIODE CALCULATOR ---
    fun calculateZenerRegulator(
        vInMin: Double,
        vInMax: Double,
        zenerV: Double,
        iLoadAmps: Double,
        iZenerMinAmps: Double = 0.005
    ): CalculationResult {
        if (vInMin <= zenerV) {
            return CalculationResult("Supply Too Low", "Minimum input voltage ($vInMin V) must exceed Zener voltage ($zenerV V).", 0.0)
        }
        val rs = (vInMin - zenerV) / (iLoadAmps + iZenerMinAmps)
        val rsStd = findNearestStandardResistor(rs)

        val maxCurrentThroughRs = (vInMax - zenerV) / rsStd
        val pRsMax = (vInMax - zenerV) * maxCurrentThroughRs
        val maxZenerCurrentNoLoad = maxCurrentThroughRs
        val pZenerMax = zenerV * maxZenerCurrentNoLoad

        val summary = "Rs = ${formatEngineering(rsStd, "Ω")} | P_Rs = ${formatEngineering(pRsMax, "W")} | P_Zener = ${formatEngineering(pZenerMax, "W")}"
        val steps = "Series resistor calculation:\n" +
                "Rs = (Vin_min - Vz) / (I_load + Iz_min) = ($vInMin - $zenerV) / ($iLoadAmps + $iZenerMinAmps) = ${formatEngineering(rs, "Ω")}\n" +
                "Selected standard resistor: ${formatEngineering(rsStd, "Ω")}\n" +
                "Max resistor power dissipation @ Vin_max ($vInMax V): ${formatEngineering(pRsMax, "W")}\n" +
                "Max Zener power dissipation (at zero load): ${formatEngineering(pZenerMax, "W")}\n" +
                "Recommended Zener diode rating: > ${formatEngineering(pZenerMax * 1.5, "W")}"

        return CalculationResult(summary, steps, rsStd)
    }

    // --- 8. 555 TIMER CALCULATOR ---
    fun calculate555Astable(r1Ohms: Double, r2Ohms: Double, cFarads: Double): CalculationResult {
        if (r1Ohms <= 0 || r2Ohms <= 0 || cFarads <= 0) {
            return CalculationResult("Invalid Inputs", "R1, R2, and C must be positive non-zero numbers.", 0.0)
        }
        val tHigh = 0.693 * (r1Ohms + r2Ohms) * cFarads
        val tLow = 0.693 * r2Ohms * cFarads
        val period = tHigh + tLow
        val freq = 1.44 / ((r1Ohms + 2.0 * r2Ohms) * cFarads)
        val dutyCycle = (tHigh / period) * 100.0

        val summary = "f = ${formatEngineering(freq, "Hz")} | Duty = ${formatNumber(dutyCycle, 1)}% | T = ${formatEngineering(period, "s")}"
        val steps = "NE555 Astable Mode:\n" +
                "High Time (Thigh) = 0.693 × (R1 + R2) × C = ${formatEngineering(tHigh, "s")}\n" +
                "Low Time (Tlow) = 0.693 × R2 × C = ${formatEngineering(tLow, "s")}\n" +
                "Total Period (T) = Thigh + Tlow = ${formatEngineering(period, "s")}\n" +
                "Oscillation Frequency (f) = 1.44 / ((R1 + 2×R2) × C) = ${formatEngineering(freq, "Hz")}\n" +
                "Duty Cycle = Thigh / T = ${formatNumber(dutyCycle, 2)}%"

        return CalculationResult(summary, steps, freq)
    }

    fun calculate555Monostable(rOhms: Double, cFarads: Double): CalculationResult {
        if (rOhms <= 0 || cFarads <= 0) {
            return CalculationResult("Invalid Inputs", "R and C must be positive.", 0.0)
        }
        val pulseWidth = 1.1 * rOhms * cFarads
        val summary = "Pulse Width T = ${formatEngineering(pulseWidth, "s")}"
        val steps = "NE555 Monostable Mode (One-Shot Pulse):\n" +
                "Formula: T = 1.1 × R × C\n" +
                "T = 1.1 × $rOhms Ω × $cFarads F = ${formatEngineering(pulseWidth, "s")}"
        return CalculationResult(summary, steps, pulseWidth)
    }

    // --- 9. VOLTAGE DIVIDER ---
    fun calculateVoltageDivider(vin: Double, r1: Double, r2: Double, rLoad: Double? = null): CalculationResult {
        if (r1 < 0 || r2 <= 0) return CalculationResult("Invalid Resistance", "Resistors must be positive.", 0.0)
        val vout: Double
        val totalCurrent: Double
        val steps: String

        if (rLoad != null && rLoad > 0) {
            val r2Equiv = (r2 * rLoad) / (r2 + rLoad)
            vout = vin * (r2Equiv / (r1 + r2Equiv))
            totalCurrent = vin / (r1 + r2Equiv)
            val pR1 = (vin - vout).pow(2) / r1
            val pR2 = vout.pow(2) / r2
            steps = "Loaded Voltage Divider:\n" +
                    "R2 in parallel with R_load: (${formatEngineering(r2, "Ω")} || ${formatEngineering(rLoad, "Ω")}) = ${formatEngineering(r2Equiv, "Ω")}\n" +
                    "Vout = Vin × [R2_equiv / (R1 + R2_equiv)] = ${formatEngineering(vout, "V")}\n" +
                    "Divider current: ${formatEngineering(totalCurrent, "A")}\n" +
                    "R1 Power: ${formatEngineering(pR1, "W")} | R2 Power: ${formatEngineering(pR2, "W")}"
        } else {
            vout = vin * (r2 / (r1 + r2))
            totalCurrent = vin / (r1 + r2)
            val pR1 = (vin - vout).pow(2) / r1
            val pR2 = vout.pow(2) / r2
            steps = "Unloaded Voltage Divider:\n" +
                    "Formula: Vout = Vin × [R2 / (R1 + R2)]\n" +
                    "Vout = $vin × [$r2 / ($r1 + $r2)] = ${formatEngineering(vout, "V")}\n" +
                    "Divider branch current: ${formatEngineering(totalCurrent, "A")}\n" +
                    "R1 Power Dissipation: ${formatEngineering(pR1, "W")}\n" +
                    "R2 Power Dissipation: ${formatEngineering(pR2, "W")}"
        }

        val summary = "Vout = ${formatEngineering(vout, "V")} | I = ${formatEngineering(totalCurrent, "A")}"
        return CalculationResult(summary, steps, vout)
    }

    // --- 10. BATTERY CALCULATOR ---
    data class BatteryChemistry(val name: String, val nominalV: Double, val fullV: Double, val cutoffV: Double)
    val batteryChemistries = listOf(
        BatteryChemistry("Lithium-Ion (18650/21700)", 3.7, 4.2, 3.0),
        BatteryChemistry("Lithium Polymer (LiPo)", 3.7, 4.2, 3.2),
        BatteryChemistry("LiFePO4 (LFP)", 3.2, 3.65, 2.5),
        BatteryChemistry("NiMH / NiCd", 1.2, 1.45, 1.0),
        BatteryChemistry("Lead-Acid (12V SLA)", 12.0, 14.4, 10.5)
    )

    fun calculateBatteryPack(
        chemistryIdx: Int,
        seriesCount: Int,
        parallelCount: Int,
        cellCapAh: Double,
        loadWatts: Double? = null,
        loadAmps: Double? = null
    ): CalculationResult {
        val chem = batteryChemistries.getOrElse(chemistryIdx) { batteryChemistries[0] }
        val s = seriesCount.coerceAtLeast(1)
        val p = parallelCount.coerceAtLeast(1)

        val totalNominalV = s * chem.nominalV
        val totalFullV = s * chem.fullV
        val totalCutoffV = s * chem.cutoffV
        val totalCapAh = p * cellCapAh
        val totalEnergyWh = totalNominalV * totalCapAh

        var runtimeText = ""
        val currentAmps = when {
            loadAmps != null && loadAmps > 0 -> loadAmps
            loadWatts != null && loadWatts > 0 -> loadWatts / totalNominalV
            else -> null
        }

        if (currentAmps != null && currentAmps > 0) {
            val hours = (totalCapAh / currentAmps) * 0.85 // 85% typical usable capacity factor
            val minutes = hours * 60.0
            runtimeText = "\nEstimated runtime @ ${formatEngineering(currentAmps, "A")} load (~85% efficiency): " +
                    if (hours >= 1.0) "${formatNumber(hours, 1)} hours (${formatNumber(minutes, 0)} min)"
                    else "${formatNumber(minutes, 1)} minutes"
        }

        val summary = "${s}S${p}P | ${formatNumber(totalNominalV, 1)}V | ${formatNumber(totalCapAh, 2)} Ah (${formatNumber(totalEnergyWh, 1)} Wh)"
        val steps = "Battery Configuration (${s}S ${p}P):\n" +
                "Chemistry: ${chem.name}\n" +
                "Nominal Voltage: $s × ${chem.nominalV}V = ${formatNumber(totalNominalV, 1)} V\n" +
                "Fully Charged Voltage: $s × ${chem.fullV}V = ${formatNumber(totalFullV, 1)} V\n" +
                "Discharge Cutoff Voltage: $s × ${chem.cutoffV}V = ${formatNumber(totalCutoffV, 1)} V\n" +
                "Total Capacity: $p × $cellCapAh Ah = ${formatNumber(totalCapAh, 2)} Ah\n" +
                "Total Stored Energy: ${formatNumber(totalNominalV, 1)}V × ${formatNumber(totalCapAh, 2)}Ah = ${formatNumber(totalEnergyWh, 1)} Wh$runtimeText"

        return CalculationResult(summary, steps, totalEnergyWh)
    }

    // --- 11. WIRE / CABLE CALCULATOR ---
    private val awgAreas = mapOf(
        10 to 5.26, 12 to 3.31, 14 to 2.08, 16 to 1.31, 18 to 0.823,
        20 to 0.518, 22 to 0.326, 24 to 0.205, 26 to 0.129, 28 to 0.081, 30 to 0.051
    )

    fun calculateWireDrop(
        isAwg: Boolean,
        awgSize: Int,
        mm2Size: Double,
        isCopper: Boolean,
        lengthMeters: Double,
        currentAmps: Double,
        systemVolts: Double
    ): CalculationResult {
        val areaMm2 = if (isAwg) (awgAreas[awgSize] ?: 1.0) else mm2Size.coerceAtLeast(0.01)
        val resistivity = if (isCopper) 1.72e-8 else 2.82e-8 // ohm-meter
        val areaM2 = areaMm2 * 1e-6

        // 2-wire round trip
        val totalLength = lengthMeters * 2.0
        val resistance = resistivity * (totalLength / areaM2)
        val vDrop = currentAmps * resistance
        val dropPercent = (vDrop / systemVolts.coerceAtLeast(0.1)) * 100.0
        val powerLossWatts = currentAmps.pow(2) * resistance

        val material = if (isCopper) "Copper" else "Aluminum"
        val summary = "Drop: ${formatNumber(vDrop, 2)} V (${formatNumber(dropPercent, 2)}%) | R = ${formatNumber(resistance, 3)} Ω | Loss: ${formatNumber(powerLossWatts, 2)} W"
        val steps = "Conductor: $material | Area: $areaMm2 mm²\n" +
                "One-way length: $lengthMeters m (Total loop: $totalLength m)\n" +
                "Loop Wire Resistance: R = ρ × L / A = ${formatNumber(resistance, 4)} Ω\n" +
                "Voltage Drop: V_drop = I × R = $currentAmps A × ${formatNumber(resistance, 4)} Ω = ${formatNumber(vDrop, 3)} V\n" +
                "Percentage Voltage Drop: ${formatNumber(dropPercent, 2)}% of $systemVolts V\n" +
                "Power Loss in Cable: P = I² × R = ${formatNumber(powerLossWatts, 2)} Watts"

        return CalculationResult(summary, steps, vDrop)
    }

    // --- 12. RF SUITE CALCULATORS ---
    fun calculateRfAntenna(freqMhz: Double, velocityFactor: Double = 0.95): CalculationResult {
        if (freqMhz <= 0) return CalculationResult("Invalid Frequency", "Frequency must be > 0.", 0.0)
        val c = 299.792458 // Speed of light in Mm/s (or millions of m/s)
        val wavelengthMeters = (c / freqMhz)
        val dipoleHalfWave = (142.5 / freqMhz) * (velocityFactor / 0.95)
        val monopoleQuarterWave = dipoleHalfWave / 2.0

        val summary = "Dipole λ/2 = ${formatNumber(dipoleHalfWave, 3)} m | λ/4 = ${formatNumber(monopoleQuarterWave, 3)} m | λ = ${formatNumber(wavelengthMeters, 3)} m"
        val steps = "RF Antenna Calculations @ $freqMhz MHz (VF = $velocityFactor):\n" +
                "Free Space Wavelength (λ): c / f = ${formatNumber(wavelengthMeters, 4)} meters\n" +
                "Half-Wave Dipole (Total End-to-End): ${formatNumber(dipoleHalfWave, 4)} meters (${formatNumber(dipoleHalfWave * 100.0, 1)} cm)\n" +
                "Quarter-Wave Monopole (Each Arm): ${formatNumber(monopoleQuarterWave, 4)} meters (${formatNumber(monopoleQuarterWave * 100.0, 1)} cm)\n" +
                "Velocity Factor adjustment: $velocityFactor"

        return CalculationResult(summary, steps, dipoleHalfWave)
    }

    fun calculateSwr(forwardPowerW: Double, reflectedPowerW: Double): CalculationResult {
        if (forwardPowerW <= 0 || reflectedPowerW < 0 || reflectedPowerW > forwardPowerW) {
            return CalculationResult("Invalid Power", "Forward power must be > 0 and Reflected <= Forward.", 0.0)
        }
        val gamma = sqrt(reflectedPowerW / forwardPowerW)
        val swr = if (gamma >= 1.0) 99.9 else (1.0 + gamma) / (1.0 - gamma)
        val returnLossDb = if (reflectedPowerW == 0.0) 99.0 else -10.0 * log10(reflectedPowerW / forwardPowerW)
        val efficiency = (1.0 - (reflectedPowerW / forwardPowerW)) * 100.0

        val summary = "SWR = ${formatNumber(swr, 2)} : 1 | Return Loss: ${formatNumber(returnLossDb, 1)} dB | Power Transmitted: ${formatNumber(efficiency, 1)}%"
        val steps = "RF Voltage Standing Wave Ratio (VSWR):\n" +
                "Reflection Coefficient (Γ) = √(Pr / Pf) = √( $reflectedPowerW / $forwardPowerW ) = ${formatNumber(gamma, 4)}\n" +
                "SWR = (1 + Γ) / (1 - Γ) = ${formatNumber(swr, 2)} : 1\n" +
                "Return Loss = 10 × log10(Pf / Pr) = ${formatNumber(returnLossDb, 2)} dB\n" +
                "Transmitted Power to Antenna: ${formatNumber(efficiency, 2)}%"

        return CalculationResult(summary, steps, swr)
    }

    // --- 13. INDUCTOR CALCULATIONS ---
    fun calculateInductorColorCode(b1: Int, b2: Int, multIdx: Int, tolIdx: Int): CalculationResult {
        val d1 = resistorColors.getOrNull(b1)?.digit ?: 0
        val d2 = resistorColors.getOrNull(b2)?.digit ?: 0
        val mult = resistorColors.getOrNull(multIdx)?.multiplier ?: 1.0
        val tol = when (tolIdx) {
            0 -> 20.0 // Black
            1 -> 1.0  // Brown
            2 -> 2.0  // Red
            10 -> 5.0 // Gold
            11 -> 10.0 // Silver
            else -> 10.0
        }
        val microHenries = (d1 * 10 + d2) * mult
        val henries = microHenries * 1e-6
        val summary = "${formatEngineering(henries, "H")} (${formatNumber(microHenries, 2)} µH) ±${formatNumber(tol)}%"
        val steps = "4-Band Inductor Color Code:\n" +
                "Digits: ($d1$d2) = ${d1 * 10 + d2}\n" +
                "Multiplier: ×$mult µH\n" +
                "Inductance = ${formatNumber(microHenries, 3)} µH = ${formatEngineering(henries, "H")}\n" +
                "Tolerance: ±${formatNumber(tol)}%"
        return CalculationResult(summary, steps, microHenries)
    }

    fun calculateSeriesParallelInductors(l1Henries: Double, l2Henries: Double, isSeries: Boolean): CalculationResult {
        if (l1Henries <= 0 || l2Henries <= 0) return CalculationResult("Invalid Input", "Inductance values must be > 0.", 0.0)
        val lTotal = if (isSeries) {
            l1Henries + l2Henries
        } else {
            (l1Henries * l2Henries) / (l1Henries + l2Henries)
        }
        val modeStr = if (isSeries) "Series: L_total = L1 + L2" else "Parallel: L_total = (L1 × L2) / (L1 + L2)"
        val summary = "L_total = ${formatEngineering(lTotal, "H")}"
        val steps = "Inductors Combination ($modeStr):\n" +
                "L1 = ${formatEngineering(l1Henries, "H")}\n" +
                "L2 = ${formatEngineering(l2Henries, "H")}\n" +
                "Total Equivalent Inductance = ${formatEngineering(lTotal, "H")}"
        return CalculationResult(summary, steps, lTotal)
    }

    // --- 14. AC POWER (SINGLE & THREE-PHASE) ---
    fun calculateAcPower(
        voltageV: Double,
        currentA: Double,
        powerFactor: Double,
        isThreePhase: Boolean
    ): CalculationResult {
        if (voltageV <= 0 || currentA <= 0 || powerFactor !in 0.0..1.0) {
            return CalculationResult("Invalid Input", "Voltage, current must be > 0 and Power Factor between 0.0 and 1.0.", 0.0)
        }
        val multiplier = if (isThreePhase) sqrt(3.0) else 1.0
        val sVa = multiplier * voltageV * currentA
        val pWatts = sVa * powerFactor
        val qVar = sqrt((sVa * sVa - pWatts * pWatts).coerceAtLeast(0.0))
        val phaseDeg = Math.toDegrees(kotlin.math.acos(powerFactor.coerceIn(0.0, 1.0)))

        val phaseType = if (isThreePhase) "3-Phase Balanced (Line-to-Line)" else "Single Phase"
        val summary = "P = ${formatEngineering(pWatts, "W")} | S = ${formatEngineering(sVa, "VA")} | Q = ${formatEngineering(qVar, "VAR")} | PF = ${formatNumber(powerFactor, 3)}"
        val steps = "AC Power Analysis ($phaseType):\n" +
                "Real Power (P) = ${if (isThreePhase) "√3 × " else ""}V × I × PF = ${formatEngineering(pWatts, "W")}\n" +
                "Apparent Power (S) = ${if (isThreePhase) "√3 × " else ""}V × I = ${formatEngineering(sVa, "VA")}\n" +
                "Reactive Power (Q) = √(S² - P²) = ${formatEngineering(qVar, "VAR")}\n" +
                "Phase Angle (θ) = arccos(PF) = ${formatNumber(phaseDeg, 1)}°\n" +
                "Power Factor (PF): ${formatNumber(powerFactor * 100.0, 1)}% (${if (powerFactor >= 0.95) "Excellent" else if (powerFactor >= 0.85) "Acceptable" else "Low / Needs Correction"})"

        return CalculationResult(summary, steps, pWatts)
    }

    // --- 15. UNIT CONVERTER ---
    fun convertUnit(
        value: Double,
        category: String,
        fromIdx: Int,
        toIdx: Int
    ): CalculationResult {
        if (value.isNaN() || value.isInfinite()) return CalculationResult("Invalid", "Please enter a valid number.", 0.0)
        return when (category) {
            "Voltage" -> {
                val factors = listOf(1e-12, 1e-9, 1e-6, 1e-3, 1.0, 1e3, 1e6)
                val units = listOf("pV", "nV", "µV", "mV", "V", "kV", "MV")
                val base = value * factors.getOrElse(fromIdx) { 1.0 }
                val targetFactor = factors.getOrElse(toIdx) { 1.0 }
                val result = base / targetFactor
                val summary = "${formatNumber(result, 6)} ${units[toIdx]}"
                val steps = "Conversion: $value ${units[fromIdx]} = $base V\n= ${formatNumber(result, 6)} ${units[toIdx]}"
                CalculationResult(summary, steps, result)
            }
            "Current" -> {
                val factors = listOf(1e-12, 1e-9, 1e-6, 1e-3, 1.0, 1e3)
                val units = listOf("pA", "nA", "µA", "mA", "A", "kA")
                val base = value * factors.getOrElse(fromIdx) { 1.0 }
                val targetFactor = factors.getOrElse(toIdx) { 1.0 }
                val result = base / targetFactor
                val summary = "${formatNumber(result, 6)} ${units[toIdx]}"
                val steps = "Conversion: $value ${units[fromIdx]} = $base A\n= ${formatNumber(result, 6)} ${units[toIdx]}"
                CalculationResult(summary, steps, result)
            }
            "Resistance" -> {
                val factors = listOf(1e-3, 1.0, 1e3, 1e6, 1e9)
                val units = listOf("mΩ", "Ω", "kΩ", "MΩ", "GΩ")
                val base = value * factors.getOrElse(fromIdx) { 1.0 }
                val targetFactor = factors.getOrElse(toIdx) { 1.0 }
                val result = base / targetFactor
                val summary = "${formatNumber(result, 6)} ${units[toIdx]}"
                val steps = "Conversion: $value ${units[fromIdx]} = $base Ω\n= ${formatNumber(result, 6)} ${units[toIdx]}"
                CalculationResult(summary, steps, result)
            }
            "Capacitance" -> {
                val factors = listOf(1e-12, 1e-9, 1e-6, 1e-3, 1.0)
                val units = listOf("pF", "nF", "µF", "mF", "F")
                val base = value * factors.getOrElse(fromIdx) { 1.0 }
                val targetFactor = factors.getOrElse(toIdx) { 1.0 }
                val result = base / targetFactor
                val summary = "${formatNumber(result, 6)} ${units[toIdx]}"
                val steps = "Conversion: $value ${units[fromIdx]} = $base F\n= ${formatNumber(result, 6)} ${units[toIdx]}"
                CalculationResult(summary, steps, result)
            }
            "Frequency" -> {
                val factors = listOf(1.0, 1e3, 1e6, 1e9, 1e12)
                val units = listOf("Hz", "kHz", "MHz", "GHz", "THz")
                val base = value * factors.getOrElse(fromIdx) { 1.0 }
                val targetFactor = factors.getOrElse(toIdx) { 1.0 }
                val result = base / targetFactor
                val summary = "${formatNumber(result, 6)} ${units[toIdx]}"
                val steps = "Conversion: $value ${units[fromIdx]} = $base Hz\n= ${formatNumber(result, 6)} ${units[toIdx]}"
                CalculationResult(summary, steps, result)
            }
            "Power & dBm" -> {
                // Units: µW (0), mW (1), W (2), kW (3), dBm (4)
                val mwValue = when (fromIdx) {
                    0 -> value * 1e-3 // µW -> mW
                    1 -> value // mW
                    2 -> value * 1e3 // W -> mW
                    3 -> value * 1e6 // kW -> mW
                    4 -> 10.0.pow(value / 10.0) // dBm -> mW
                    else -> value
                }
                val result = when (toIdx) {
                    0 -> mwValue * 1e3 // mW -> µW
                    1 -> mwValue // mW
                    2 -> mwValue * 1e-3 // mW -> W
                    3 -> mwValue * 1e-6 // mW -> kW
                    4 -> if (mwValue <= 0) -999.0 else 10.0 * log10(mwValue)
                    else -> mwValue
                }
                val units = listOf("µW", "mW", "W", "kW", "dBm")
                val summary = "${formatNumber(result, 4)} ${units[toIdx]}"
                val steps = "Power Conversion: $value ${units[fromIdx]} = ${formatNumber(mwValue, 4)} mW\n= ${formatNumber(result, 4)} ${units[toIdx]}"
                CalculationResult(summary, steps, result)
            }
            else -> CalculationResult("${formatNumber(value)}", "Direct value", value)
        }
    }
}

data class CalculationResult(
    val formattedResult: String,
    val calculationSteps: String,
    val primaryNumericValue: Double
)
