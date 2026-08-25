package com.example.data

import com.example.model.ResourceSection

object ResourcesData {
    val allResources: List<ResourceSection> = listOf(
        ResourceSection(
            id = "res_resistor_colors",
            title = "Resistor Color Code Reference Chart",
            description = "Standard 4-Band, 5-Band, and 6-Band EIA color coding system for leaded through-hole resistors.",
            tableHeaders = listOf("Color", "Significant Digit", "Multiplier", "Tolerance (±%)", "Tempco (ppm/K)"),
            tableRows = listOf(
                listOf("Black", "0", "10⁰ (1 Ω)", "—", "250"),
                listOf("Brown", "1", "10¹ (10 Ω)", "±1% (F)", "100"),
                listOf("Red", "2", "10² (100 Ω)", "±2% (G)", "50"),
                listOf("Orange", "3", "10³ (1 kΩ)", "—", "15"),
                listOf("Yellow", "4", "10⁴ (10 kΩ)", "—", "25"),
                listOf("Green", "5", "10⁵ (100 kΩ)", "±0.5% (D)", "20"),
                listOf("Blue", "6", "10⁶ (1 MΩ)", "±0.25% (C)", "10"),
                listOf("Violet", "7", "10⁷ (10 MΩ)", "±0.1% (B)", "5"),
                listOf("Gray", "8", "10⁸ (100 MΩ)", "±0.05%", "1"),
                listOf("White", "9", "10⁹ (1 GΩ)", "—", "—"),
                listOf("Gold", "—", "10⁻¹ (0.1 Ω)", "±5% (J)", "—"),
                listOf("Silver", "—", "10⁻² (0.01 Ω)", "±10% (K)", "—"),
                listOf("None", "—", "—", "±20% (M)", "—")
            ),
            contentMarkdown = "**4-Band Resistors:** Band 1 = 1st digit, Band 2 = 2nd digit, Band 3 = Multiplier, Band 4 = Tolerance.\n**5-Band Resistors (Precision):** Band 1 = 1st digit, Band 2 = 2nd digit, Band 3 = 3rd digit, Band 4 = Multiplier, Band 5 = Tolerance.\n**6-Band Resistors:** 5 bands as above + Band 6 = Temperature coefficient in ppm/°C."
        ),
        ResourceSection(
            id = "res_capacitor_codes",
            title = "Capacitor 3-Digit EIA Code Table",
            description = "Quick lookup for 3-digit ceramic and film capacitor markings (expressed in picofarads pF).",
            tableHeaders = listOf("Code", "Value (pF)", "Value (nF)", "Value (µF)", "Typical Marking"),
            tableRows = listOf(
                listOf("101", "100 pF", "0.1 nF", "0.0001 µF", "101"),
                listOf("102", "1,000 pF", "1.0 nF", "0.001 µF", "102 / 1n0"),
                listOf("222", "2,200 pF", "2.2 nF", "0.0022 µF", "222 / 2n2"),
                listOf("472", "4,700 pF", "4.7 nF", "0.0047 µF", "472 / 4n7"),
                listOf("103", "10,000 pF", "10 nF", "0.01 µF", "103 / 10n"),
                listOf("223", "22,000 pF", "22 nF", "0.022 µF", "223 / 22n"),
                listOf("473", "47,000 pF", "47 nF", "0.047 µF", "473 / 47n"),
                listOf("104", "100,000 pF", "100 nF", "0.1 µF", "104 / 100n / 0.1"),
                listOf("224", "220,000 pF", "220 nF", "0.22 µF", "224 / 220n"),
                listOf("474", "470,000 pF", "470 nF", "0.47 µF", "474 / 470n"),
                listOf("105", "1,000,000 pF", "1,000 nF", "1.0 µF", "105 / 1u0"),
                listOf("106", "10,000,000 pF", "10,000 nF", "10.0 µF", "106 / 10u")
            ),
            contentMarkdown = "**Tolerance Letters:**\n- **J** = ±5%\n- **K** = ±10%\n- **M** = ±20%\n- **Z** = +80% / -20%\n*Example:* `104K` = 100nF (0.1µF) with ±10% tolerance."
        ),
        ResourceSection(
            id = "res_si_prefixes",
            title = "SI Metric Unit Multipliers & Engineering Notation",
            description = "Standard SI unit prefixes utilized across electronics engineering calculations.",
            tableHeaders = listOf("Prefix", "Symbol", "Scientific", "Multiplier Name", "Common Electronics Context"),
            tableRows = listOf(
                listOf("Tera", "T", "10¹²", "1,000,000,000,000", "Data Storage, Terahertz"),
                listOf("Giga", "G", "10⁹", "1,000,000,000", "GHz Frequency (Wi-Fi 2.4/5GHz, CPU)"),
                listOf("Mega", "M", "10⁶", "1,000,000", "MHz Frequency, MΩ High Resistance"),
                listOf("Kilo", "k", "10³", "1,000", "kΩ Resistors, kHz Audio, kW Power"),
                listOf("Base", "—", "10⁰", "1", "Volts (V), Amperes (A), Ohms (Ω), Watts (W)"),
                listOf("Milli", "m", "10⁻³", "0.001", "mA Current, mH Inductors, mV Signals"),
                listOf("Micro", "µ / u", "10⁻⁶", "0.000001", "µF Capacitors, µA Sleep Current, µH Coils"),
                listOf("Nano", "n", "10⁻⁹", "0.000000001", "nF Capacitors, ns Switching Times"),
                listOf("Pico", "p", "10⁻¹²", "0.000000000001", "pF Crystal Osc Tuning, Stray Capacitance"),
                listOf("Femto", "f", "10⁻¹⁵", "0.000000000000001", "Silicon gate capacitance")
            )
        ),
        ResourceSection(
            id = "res_awg_table",
            title = "AWG (American Wire Gauge) Copper Wire Chart",
            description = "Standard wire gauge specifications for solid copper wire at 20°C.",
            tableHeaders = listOf("AWG", "Diameter (mm)", "Area (mm²)", "Resistance (Ω/km)", "Max Current Chassis (A)"),
            tableRows = listOf(
                listOf("10", "2.588 mm", "5.26 mm²", "3.27 Ω/km", "30 A"),
                listOf("12", "2.053 mm", "3.31 mm²", "5.21 Ω/km", "20 A"),
                listOf("14", "1.628 mm", "2.08 mm²", "8.28 Ω/km", "15 A"),
                listOf("16", "1.291 mm", "1.31 mm²", "13.17 Ω/km", "10 A"),
                listOf("18", "1.024 mm", "0.823 mm²", "20.95 Ω/km", "7 A"),
                listOf("20", "0.812 mm", "0.518 mm²", "33.31 Ω/km", "5 A"),
                listOf("22", "0.644 mm", "0.326 mm²", "52.96 Ω/km", "3 A"),
                listOf("24", "0.511 mm", "0.205 mm²", "84.22 Ω/km", "2 A (Ethernet / Breadboard)"),
                listOf("26", "0.405 mm", "0.129 mm²", "133.9 Ω/km", "1.3 A"),
                listOf("28", "0.321 mm", "0.081 mm²", "212.9 Ω/km", "0.8 A (Ribbon Cable)"),
                listOf("30", "0.255 mm", "0.051 mm²", "338.6 Ω/km", "0.5 A (Wire Wrap)")
            ),
            contentMarkdown = "**Rule of Thumb:** Every decrease of 3 AWG sizes approximately doubles the cross-sectional area and halves the resistance per unit length."
        ),
        ResourceSection(
            id = "res_logic_gates",
            title = "Digital Logic Gates Truth Tables",
            description = "Complete truth tables, Boolean algebra expressions, and standard logic functions for fundamental digital gates.",
            tableHeaders = listOf("Gate", "Symbol Equation", "A=0, B=0", "A=0, B=1", "A=1, B=0", "A=1, B=1"),
            tableRows = listOf(
                listOf("AND", "Y = A · B", "0", "0", "0", "1"),
                listOf("OR", "Y = A + B", "0", "1", "1", "1"),
                listOf("NOT", "Y = Ā (Single Input)", "1 (for A=0)", "0 (for A=1)", "—", "—"),
                listOf("NAND", "Y = (A · B)' (Universal Gate)", "1", "1", "1", "0"),
                listOf("NOR", "Y = (A + B)' (Universal Gate)", "1", "0", "0", "0"),
                listOf("XOR", "Y = A ⊕ B", "0", "1", "1", "0"),
                listOf("XNOR", "Y = (A ⊕ B)'", "1", "0", "0", "1")
            ),
            contentMarkdown = "**Universal Gates:** Any digital logic circuit (from flip-flops to full 64-bit CPUs) can be constructed solely using NAND gates or solely using NOR gates."
        ),
        ResourceSection(
            id = "res_e_series",
            title = "Standard E-Series Preferred Resistor Values",
            description = "International standard preferred numbers (IEC 60063) for 5% (E24) and 10% (E12) tolerance resistors.",
            tableHeaders = listOf("Series", "Tolerance", "Values in each Decade (×10^n)"),
            tableRows = listOf(
                listOf("E6", "±20%", "1.0, 1.5, 2.2, 3.3, 4.7, 6.8"),
                listOf("E12", "±10%", "1.0, 1.2, 1.5, 1.8, 2.2, 2.7, 3.3, 3.9, 4.7, 5.6, 6.8, 8.2"),
                listOf("E24", "±5%", "1.0, 1.1, 1.2, 1.3, 1.5, 1.6, 1.8, 2.0, 2.2, 2.4, 2.7, 3.0, 3.3, 3.6, 3.9, 4.3, 4.7, 5.1, 5.6, 6.2, 6.8, 7.5, 8.2, 9.1")
            ),
            contentMarkdown = "Multipliers apply to each decade: e.g. for E12, available values include 12Ω, 120Ω, 1.2kΩ, 12kΩ, 120kΩ, 1.2MΩ."
        ),
        ResourceSection(
            id = "res_safety_guide",
            title = "Electronics Laboratory & Workshop Safety Principles",
            description = "Essential safety protocols for electrical testing, high voltage handling, soldering, and battery safety.",
            contentMarkdown = """
### 1. Mains Electricity Safety (110V / 230V AC)
- Always disconnect AC power and verify with a reliable multimeter or non-contact voltage tester before touching internal wiring.
- Keep one hand in your pocket when probing live high-voltage chassis to prevent a current path across your chest/heart.
- Always use an **Isolation Transformer** when probing mains-powered equipment with an oscilloscope to prevent ground loop explosions.

### 2. High-Voltage Capacitors
- High-voltage electrolytic filter capacitors in power supplies and CRT equipment can hold lethal charges for days after being unplugged.
- Always safely discharge high-voltage capacitors using a 1kΩ to 10kΩ 10W power resistor with insulated clip leads—never short with a screwdriver.

### 3. Lithium-Ion & LiPo Battery Safety
- Never short-circuit, puncture, crush, or heat lithium cells above 60°C.
- Always charge lithium battery packs inside a fire-retardant LiPo bag with a dedicated balance charger.
- Never discharge Li-ion cells below 2.8V or charge above 4.20V per cell without a functioning BMS (Battery Management System).

### 4. Soldering & Chemical Safety
- Always work in a well-ventilated area with a fume extractor to avoid inhaling rosin and flux vapor.
- Always wear safety goggles when soldering, desoldering (solder splatter), or clipping component lead wires.
- Wash hands thoroughly with soap after handling leaded solder (Sn60/Pb40).
            """.trimIndent()
        )
    )
}
