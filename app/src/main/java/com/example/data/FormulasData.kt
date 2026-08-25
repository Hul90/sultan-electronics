package com.example.data

import com.example.model.FormulaItem
import com.example.model.FormulaVariable

object FormulasData {
    val allFormulas: List<FormulaItem> = listOf(
        FormulaItem(
            id = "formula_ohms_law",
            title = "Ohm's Law",
            category = "Basic Electrical",
            equation = "V = I × R   |   I = V / R   |   R = V / I",
            explanation = "Defines the fundamental linear relationship between voltage (potential difference), current (flow of electric charge), and resistance in an electrical circuit.",
            variables = listOf(
                FormulaVariable("V", "Voltage", "Volts (V)"),
                FormulaVariable("I", "Current", "Amperes (A)"),
                FormulaVariable("R", "Resistance", "Ohms (Ω)")
            ),
            workedExample = "For a 12V supply connected across a 1000Ω resistor:\nI = 12 / 1000 = 0.012 A = 12 mA\nPower dissipated: P = 12V × 0.012A = 0.144 W = 144 mW",
            calculatorRoute = "calc_ohms_law"
        ),
        FormulaItem(
            id = "formula_power",
            title = "Electrical Power (Joule's Law)",
            category = "Basic Electrical",
            equation = "P = V × I = I² × R = V² / R",
            explanation = "Calculates the rate at which electrical energy is converted into heat, light, or mechanical work in an electrical component.",
            variables = listOf(
                FormulaVariable("P", "Electric Power", "Watts (W)"),
                FormulaVariable("V", "Voltage Drop", "Volts (V)"),
                FormulaVariable("I", "Current", "Amperes (A)"),
                FormulaVariable("R", "Resistance", "Ohms (Ω)")
            ),
            workedExample = "A heating element draws 5A from a 230V mains line:\nP = 230V × 5A = 1150 Watts (1.15 kW)\nElement resistance: R = 230 / 5 = 46 Ω",
            calculatorRoute = "calc_power"
        ),
        FormulaItem(
            id = "formula_resistors",
            title = "Resistors in Series and Parallel",
            category = "Circuit Analysis",
            equation = "Series: R_total = R1 + R2 + ... + Rn\nParallel: 1 / R_total = 1/R1 + 1/R2 + ... (For 2: R1×R2 / (R1+R2))",
            explanation = "In series, resistances simply add together. In parallel, the equivalent resistance is always smaller than the smallest individual resistor.",
            variables = listOf(
                FormulaVariable("R_total", "Equivalent Resistance", "Ohms (Ω)"),
                FormulaVariable("R1, R2", "Individual Branch Resistors", "Ohms (Ω)")
            ),
            workedExample = "Two 10kΩ resistors in parallel:\nR_total = (10k × 10k) / (10k + 10k) = 100M / 20k = 5 kΩ",
            calculatorRoute = "calc_resistor"
        ),
        FormulaItem(
            id = "formula_capacitors",
            title = "Capacitors in Series and Parallel",
            category = "Circuit Analysis",
            equation = "Parallel: C_total = C1 + C2 + ... + Cn\nSeries: 1 / C_total = 1/C1 + 1/C2 + ... (For 2: C1×C2 / (C1+C2))",
            explanation = "Opposite of resistors: capacitors in parallel add their plate areas together, increasing total capacitance. Series capacitors divide capacitance.",
            variables = listOf(
                FormulaVariable("C_total", "Equivalent Capacitance", "Farads (F, µF, nF, pF)"),
                FormulaVariable("C1, C2", "Branch Capacitors", "Farads (F)")
            ),
            workedExample = "Two 100µF capacitors in series:\nC_total = (100 × 100) / (100 + 100) = 50 µF (with double voltage rating)",
            calculatorRoute = "calc_capacitor"
        ),
        FormulaItem(
            id = "formula_rc_filter",
            title = "RC Time Constant & Cutoff Frequency",
            category = "Filters & AC",
            equation = "Time Constant: τ = R × C\nCutoff Frequency: fc = 1 / (2 × π × R × C)",
            explanation = "The time constant τ is the time required for a capacitor to charge to ~63.2% of supply voltage. The cutoff frequency fc is where signal power drops by 3dB (-3dB point).",
            variables = listOf(
                FormulaVariable("τ", "Time Constant", "Seconds (s)"),
                FormulaVariable("fc", "-3dB Cutoff Frequency", "Hertz (Hz)"),
                FormulaVariable("R", "Resistance", "Ohms (Ω)"),
                FormulaVariable("C", "Capacitance", "Farads (F)")
            ),
            workedExample = "With R = 10kΩ (10,000Ω) and C = 100nF (100×10⁻⁹ F):\nτ = 10,000 × 100×10⁻⁹ = 0.001 s = 1.0 ms\nfc = 1 / (2 × π × 10,000 × 100×10⁻⁹) = 159.15 Hz",
            calculatorRoute = "calc_capacitor"
        ),
        FormulaItem(
            id = "formula_reactance",
            title = "Capacitive and Inductive Reactance",
            category = "Filters & AC",
            equation = "Capacitive Reactance: Xc = 1 / (2 × π × f × C)\nInductive Reactance: Xl = 2 × π × f × L",
            explanation = "Reactance is the frequency-dependent opposition of capacitors and inductors to alternating current (AC). Xc decreases with higher frequency; Xl increases with frequency.",
            variables = listOf(
                FormulaVariable("Xc", "Capacitive Reactance", "Ohms (Ω)"),
                FormulaVariable("Xl", "Inductive Reactance", "Ohms (Ω)"),
                FormulaVariable("f", "AC Frequency", "Hertz (Hz)"),
                FormulaVariable("C", "Capacitance", "Farads (F)"),
                FormulaVariable("L", "Inductance", "Henries (H)")
            ),
            workedExample = "For C = 10µF at f = 50 Hz:\nXc = 1 / (2 × 3.14159 × 50 × 10×10⁻⁶) = 318.3 Ω",
            calculatorRoute = "calc_capacitor"
        ),
        FormulaItem(
            id = "formula_resonance",
            title = "LC Resonance Frequency",
            category = "Filters & AC",
            equation = "fr = 1 / (2 × π × √(L × C))",
            explanation = "At resonance, capacitive reactance equals inductive reactance (Xc = Xl), minimizing impedance in series LC circuits and maximizing impedance in parallel LC circuits.",
            variables = listOf(
                FormulaVariable("fr", "Resonant Frequency", "Hertz (Hz)"),
                FormulaVariable("L", "Inductance", "Henries (H)"),
                FormulaVariable("C", "Capacitance", "Farads (F)")
            ),
            workedExample = "For L = 100 µH (100×10⁻⁶ H) and C = 100 pF (100×10⁻¹² F):\nfr = 1 / (2 × π × √(100×10⁻⁶ × 100×10⁻¹²)) = 1.5915 MHz (AM Band)",
            calculatorRoute = "calc_rf"
        ),
        FormulaItem(
            id = "formula_led",
            title = "LED Current Limiting Resistor",
            category = "Semiconductors",
            equation = "R = (Vs - Vf) / If   |   P_resistor = (Vs - Vf) × If",
            explanation = "Calculates the exact series resistance required to protect an LED from burning out by dropping excess supply voltage at the rated forward LED current.",
            variables = listOf(
                FormulaVariable("Vs", "Supply Voltage", "Volts (V)"),
                FormulaVariable("Vf", "LED Forward Voltage (Red: ~1.8V-2.0V, Blue/White: ~3.0V-3.3V)", "Volts (V)"),
                FormulaVariable("If", "Desired LED Forward Current (typically 10-20mA)", "Amperes (A)"),
                FormulaVariable("R", "Required Resistor Value", "Ohms (Ω)")
            ),
            workedExample = "Vs = 12V, White LED Vf = 3.2V, If = 20mA (0.02A):\nR = (12 - 3.2) / 0.02 = 8.8 / 0.02 = 440 Ω (Choose standard 470Ω)\nP_resistor = 8.8V × 0.02A = 0.176W (Use 1/4W 250mW resistor)",
            calculatorRoute = "calc_led"
        ),
        FormulaItem(
            id = "formula_zener",
            title = "Zener Shunt Voltage Regulator",
            category = "Semiconductors",
            equation = "Rs = (Vs_min - Vz) / (IL_max + Iz_min)",
            explanation = "Calculates the maximum series resistor Rs required to ensure the Zener diode remains in breakdown conduction and delivers a regulated output voltage under full load.",
            variables = listOf(
                FormulaVariable("Vs_min", "Minimum Input Supply Voltage", "Volts (V)"),
                FormulaVariable("Vz", "Zener Breakdown Voltage", "Volts (V)"),
                FormulaVariable("IL_max", "Maximum Load Current", "Amperes (A)"),
                FormulaVariable("Iz_min", "Minimum Zener Bias Current (typically 5mA)", "Amperes (A)")
            ),
            workedExample = "Vs = 12V, Vz = 5.1V, IL_max = 20mA, Iz_min = 5mA:\nRs = (12 - 5.1) / (0.020 + 0.005) = 6.9 / 0.025 = 276 Ω (Use standard 270Ω)\nP_zener_no_load = 5.1V × (6.9V / 270Ω) = 0.130 Watts",
            calculatorRoute = "calc_zener"
        ),
        FormulaItem(
            id = "formula_555_astable",
            title = "NE555 Astable Multivibrator Frequency",
            category = "Oscillators",
            equation = "Frequency: f = 1.44 / ((R1 + 2×R2) × C)\nHigh Time: T_high = 0.693 × (R1 + R2) × C\nLow Time: T_low = 0.693 × R2 × C\nDuty Cycle: D = (R1 + R2) / (R1 + 2×R2) × 100%",
            explanation = "Configures the 555 timer in free-running oscillator mode to output continuous square waves.",
            variables = listOf(
                FormulaVariable("f", "Oscillation Frequency", "Hertz (Hz)"),
                FormulaVariable("R1", "Upper Resistor (Vcc to Pin 7)", "Ohms (Ω)"),
                FormulaVariable("R2", "Lower Resistor (Pin 7 to Pin 6/2)", "Ohms (Ω)"),
                FormulaVariable("C", "Timing Capacitor (Pin 6/2 to GND)", "Farads (F)")
            ),
            workedExample = "R1 = 1kΩ, R2 = 10kΩ, C = 10µF:\nf = 1.44 / ((1000 + 20000) × 10×10⁻⁶) = 1.44 / (21000 × 10⁻⁵) = 6.86 Hz\nDuty cycle = (1k + 10k) / (1k + 20k) = 11/21 = 52.4%",
            calculatorRoute = "calc_555"
        ),
        FormulaItem(
            id = "formula_voltage_divider",
            title = "Unloaded Voltage Divider",
            category = "Circuit Analysis",
            equation = "Vout = Vin × (R2 / (R1 + R2))",
            explanation = "Calculates the output voltage tapped between two series resistors connected across an input voltage source.",
            variables = listOf(
                FormulaVariable("Vin", "Input Voltage", "Volts (V)"),
                FormulaVariable("Vout", "Divided Output Voltage", "Volts (V)"),
                FormulaVariable("R1", "Top Resistor (Vin to Vout)", "Ohms (Ω)"),
                FormulaVariable("R2", "Bottom Resistor (Vout to GND)", "Ohms (Ω)")
            ),
            workedExample = "Vin = 12V, R1 = 10kΩ, R2 = 5kΩ:\nVout = 12 × (5k / (10k + 5k)) = 12 × (5/15) = 4.0 Volts",
            calculatorRoute = "calc_voltage_divider"
        ),
        FormulaItem(
            id = "formula_rf_antenna",
            title = "Half-Wave Dipole Antenna Length",
            category = "RF & Wireless",
            equation = "Length (meters) = 142.5 / f(MHz)\nEach Element (λ/4) = 71.25 / f(MHz)",
            explanation = "Calculates the end-to-end physical length of a resonant half-wave dipole wire antenna in free space incorporating typical 0.95 velocity factor wire end-effects.",
            variables = listOf(
                FormulaVariable("Length", "Total Dipole Length (λ/2)", "Meters (m)"),
                FormulaVariable("f", "Operating Frequency", "Megahertz (MHz)")
            ),
            workedExample = "For FM Broadcast Band f = 100 MHz:\nTotal Length = 142.5 / 100 = 1.425 meters\nEach arm = 71.25 / 100 = 0.7125 meters (71.25 cm)",
            calculatorRoute = "calc_rf"
        )
    )
}
