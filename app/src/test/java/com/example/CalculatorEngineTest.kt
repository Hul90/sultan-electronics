package com.example

import com.example.utils.CalculatorEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.abs

class CalculatorEngineTest {

    @Test
    fun testResistor4Band() {
        // Brown (1), Black (0), Red (*100), Gold (5%) -> 1000 Ω = 1 kΩ
        val res = CalculatorEngine.calculateResistor4Band(1, 0, 2, 10)
        assertEquals(1000.0, res.primaryNumericValue, 0.001)
        assertTrue(res.formattedResult.contains("1 kΩ") || res.formattedResult.contains("1.000 kΩ") || res.formattedResult.contains("1.0 kΩ") || res.formattedResult.contains("1000"))
    }

    @Test
    fun testResistor5Band() {
        // Yellow (4), Violet (7), Black (0), Orange (*1000), Brown (1%) -> 470 kΩ
        val res = CalculatorEngine.calculateResistor5Band(4, 7, 0, 3, 1)
        assertEquals(470000.0, res.primaryNumericValue, 0.001)
        assertTrue(res.formattedResult.contains("470 kΩ") || res.formattedResult.contains("470.000 kΩ") || res.formattedResult.contains("470.0 kΩ"))
    }

    @Test
    fun testReverseResistorCode() {
        val bands = CalculatorEngine.reverseResistorColor(4700.0, is5Band = false)
        // 4700 -> 4, 7, x100 (Red = 2), Gold (10)
        assertEquals(4, bands[0])
        assertEquals(7, bands[1])
        assertEquals(2, bands[2])
    }

    @Test
    fun testSmdResistor() {
        // 3-digit: 103 -> 10 kΩ
        val res3 = CalculatorEngine.decodeSmdResistor("103")
        assertEquals(10000.0, res3.primaryNumericValue, 0.001)

        // 4-digit: 4702 -> 47 kΩ
        val res4 = CalculatorEngine.decodeSmdResistor("4702")
        assertEquals(47000.0, res4.primaryNumericValue, 0.001)

        // R notation: 4R7 -> 4.7 Ω
        val resR = CalculatorEngine.decodeSmdResistor("4R7")
        assertEquals(4.7, resR.primaryNumericValue, 0.001)

        // EIA-96: 01A -> 100 Ω
        val resEia = CalculatorEngine.decodeSmdResistor("01A")
        assertEquals(100.0, resEia.primaryNumericValue, 0.001)
    }

    @Test
    fun testOhmsLaw() {
        // V = 12V, R = 4Ω -> I = 3A, P = 36W
        val res = CalculatorEngine.calculateOhmsLaw(12.0, null, 4.0, null)
        assertTrue(res.formattedResult.contains("3 A") || res.formattedResult.contains("3.0 A") || res.formattedResult.contains("36 W") || res.formattedResult.contains("36.0 W"))
    }

    @Test
    fun testLedResistor() {
        // Vin = 12V, Vf = 2V, If = 20mA (0.02A) -> R = (12-2)/0.02 = 500Ω
        val res = CalculatorEngine.calculateLedResistor(12.0, 2.0, 0.02, 1, true)
        assertTrue(res.primaryNumericValue > 0.0)
    }

    @Test
    fun testTimer555Astable() {
        // R1 = 10k, R2 = 10k, C = 10uF
        // f = 1.44 / ((10k + 20k) * 10uF) = 1.44 / (30000 * 0.00001) = 1.44 / 0.3 = 4.8 Hz
        val res = CalculatorEngine.calculate555Astable(10000.0, 10000.0, 0.00001)
        assertEquals(4.8, res.primaryNumericValue, 0.1)
    }

    @Test
    fun testVoltageDivider() {
        // Vin = 12V, R1 = 10k, R2 = 10k -> Vout = 6V
        val res = CalculatorEngine.calculateVoltageDivider(12.0, 10000.0, 10000.0, null)
        assertEquals(6.0, res.primaryNumericValue, 0.001)
    }

    @Test
    fun testBatteryPack() {
        // 3S2P, cell = 3.7V, 2.5Ah -> 11.1V, 5.0Ah, 55.5Wh, at 50W -> ~1.11h
        val res = CalculatorEngine.calculateBatteryPack(0, 3, 2, 2.5, 50.0, null)
        assertEquals(55.5, res.primaryNumericValue, 0.5)
        assertTrue(res.formattedResult.contains("55.5 Wh") || res.formattedResult.contains("55.50 Wh"))
    }

    @Test
    fun testWireVoltageDrop() {
        // AWG 14 (8.286 mΩ/m), 10m one-way (20m loop), 10A at 12V
        val res = CalculatorEngine.calculateWireDrop(
            isAwg = true,
            awgSize = 14,
            mm2Size = 2.08,
            isCopper = true,
            lengthMeters = 10.0,
            currentAmps = 10.0,
            systemVolts = 12.0
        )
        assertTrue(res.primaryNumericValue in 0.2..3.0)
    }

    @Test
    fun testRfDipole() {
        // f = 145 MHz -> Dipole half-wave length = 142.5 / 145 = ~0.982m
        val res = CalculatorEngine.calculateRfAntenna(145.0, 0.95)
        assertTrue(abs(res.primaryNumericValue - 0.982) < 0.05)
    }

    @Test
    fun testCapacitorEiaCode() {
        // 104 -> 10 * 10^4 pF = 100,000 pF = 100 nF = 0.1 µF
        val res = CalculatorEngine.decodeCapacitorCode("104")
        assertEquals(0.1, res.primaryNumericValue, 0.001)
    }

    @Test
    fun testZenerRegulator() {
        // VinMin = 12V, VinMax = 15V, Vz = 5.1V, IL = 20mA (0.02A), IzMin = 5mA (0.005A)
        val res = CalculatorEngine.calculateZenerRegulator(12.0, 15.0, 5.1, 0.02, 0.005)
        assertTrue(res.primaryNumericValue in 200.0..350.0)
    }

    @Test
    fun testInductorCalculations() {
        // Brown (1), Black (0), Red (*100) -> 1000 µH = 1 mH
        val colorRes = CalculatorEngine.calculateInductorColorCode(1, 0, 2, 10)
        assertEquals(1000.0, colorRes.primaryNumericValue, 0.001)

        // Series: 10mH + 20mH = 30mH
        val seriesRes = CalculatorEngine.calculateSeriesParallelInductors(0.010, 0.020, true)
        assertEquals(0.030, seriesRes.primaryNumericValue, 0.0001)

        // Reactance XL: f = 1000 Hz, L = 10mH (0.01H) -> XL = 2 * PI * 1000 * 0.01 = 62.83 Ω
        val xlRes = CalculatorEngine.calculateInductiveReactance(1000.0, 0.01)
        assertEquals(62.83, xlRes.primaryNumericValue, 0.1)
    }

    @Test
    fun testAcPower() {
        // 230V, 10A, PF = 0.85 -> P = 230 * 10 * 0.85 = 1955 W, S = 2300 VA
        val singleRes = CalculatorEngine.calculateAcPower(230.0, 10.0, 0.85, false)
        assertEquals(1955.0, singleRes.primaryNumericValue, 0.1)

        // 3-Phase: 400V, 10A, PF = 0.85 -> P = sqrt(3) * 400 * 10 * 0.85 = 1.73205 * 3400 = 5888.97 W
        val threeRes = CalculatorEngine.calculateAcPower(400.0, 10.0, 0.85, true)
        assertEquals(5888.97, threeRes.primaryNumericValue, 1.0)
    }

    @Test
    fun testUnitConverter() {
        // 1000 mV to V -> 1.0 V
        val vRes = CalculatorEngine.convertUnit(1000.0, "Voltage", 3, 4)
        assertEquals(1.0, vRes.primaryNumericValue, 0.001)

        // Power & dBm: 1000 mW to dBm -> 30 dBm
        val dbmRes = CalculatorEngine.convertUnit(1000.0, "Power & dBm", 1, 4)
        assertEquals(30.0, dbmRes.primaryNumericValue, 0.01)
    }
}
