package com.example.data

import com.example.model.HandbookEntry

object HandbookData {
    val all = listOf(
        HandbookEntry("resistor-basics", "Resistor Basics", "Ohm's law, power rating, series and parallel", "Basics", "Use R = V/I and P = VI. In series resistances add; in parallel conductance adds. Always select a resistor with adequate voltage and power margin."),
        HandbookEntry("capacitor-basics", "Capacitor Reference", "Polarity, codes, ESR and practical selection", "Components", "Electrolytic capacitors are polarized. Verify voltage rating, capacitance, ripple-current rating and temperature rating. Ceramic and film capacitors are normally non-polarized."),
        HandbookEntry("diode-testing", "Diode Testing", "Multimeter diode-mode procedure", "Testing", "In diode mode, forward bias should normally show a diode drop while reverse bias should read open on a healthy silicon diode. Exact values vary by device."),
        HandbookEntry("bjt-testing", "BJT Testing", "NPN/PNP junction test workflow", "Testing", "Use diode mode to test base-emitter and base-collector junctions. Check both polarities and verify collector-emitter is not shorted. Pin order varies by package and part."),
        HandbookEntry("mosfet-testing", "MOSFET Testing", "Gate isolation and body-diode checks", "Testing", "Discharge the gate before testing. Check the intrinsic body diode and ensure gate-to-source/drain does not show an unexpected short. Manufacturer pinout must be verified."),
        HandbookEntry("logic-levels", "Logic Level Reference", "Typical digital logic thresholds", "Digital", "Do not assume 5V logic is safe for every 3.3V MCU. Check VIH, VIL, VOH and VOL in the exact device datasheet before connecting signals."),
        HandbookEntry("pullups", "Pull-Up / Pull-Down Guide", "GPIO input stability", "Digital", "Floating inputs can change state unpredictably. Use a suitable pull-up or pull-down resistor and respect the MCU's internal pull resistor limits."),
        HandbookEntry("decoupling", "IC Decoupling Guide", "Bypass capacitor placement", "PCB", "Place a small ceramic bypass capacitor close to each IC supply pin pair. Add bulk capacitance near power entry and load transients as required."),
        HandbookEntry("rectifier", "Bridge Rectifier Reference", "AC to DC conversion", "Power", "A bridge rectifier uses four diodes. Choose reverse-voltage and current ratings with margin, then size the reservoir capacitor for ripple and ripple current."),
        HandbookEntry("ripple", "Power Supply Ripple", "Filter capacitor selection", "Power", "Ripple depends on load current, rectifier topology, frequency and capacitance. Verify capacitor ripple-current rating and regulator dissipation after filtering."),
        HandbookEntry("regulators", "Linear Regulator Guide", "7805, LM317 and thermal limits", "Power", "Linear loss is approximately (Vin - Vout) × Iout. Check dropout voltage, maximum junction temperature and heatsink needs."),
        HandbookEntry("relay", "Relay Driver Guide", "Coil driving and flyback protection", "Switching", "A transistor or MOSFET can drive a relay coil. Add a suitable flyback diode for DC coils and size the driver for the actual coil current."),
        HandbookEntry("opamp", "Op-Amp Practical Guide", "Inverting, non-inverting and comparator basics", "Analog", "Check input common-mode range, output swing, supply voltage, slew rate and stability before using an op-amp in a circuit."),
        HandbookEntry("555", "NE555 Quick Reference", "Astable and monostable timing", "Timing", "The 555 can generate delays and oscillation. Timing depends on resistor/capacitor values and real-device tolerances; verify duty-cycle limits for the chosen configuration."),
        HandbookEntry("awG", "AWG Wire Gauge", "Current and conductor reference", "Wiring", "Wire selection depends on current, length, temperature, insulation and allowable voltage drop. Do not use a single current number for every installation."),
        HandbookEntry("connectors", "Connector Pinout Guide", "Common headers and interfaces", "Interfacing", "Always confirm the exact connector orientation and manufacturer pinout. Similar-looking connectors can have different pin assignments."),
        HandbookEntry("esp32", "ESP32 GPIO Safety", "3.3V MCU practical rules", "Microcontrollers", "ESP32 GPIO is 3.3V logic. Avoid applying voltages above the device limits and verify boot-strapping pins before attaching external circuits."),
        HandbookEntry("arduino", "Arduino Interface Guide", "Uno/Nano I/O basics", "Microcontrollers", "Digital pins can be used for GPIO, PWM or serial interfaces depending on the board. Check current limits and avoid driving loads directly from GPIO."),
        HandbookEntry("i2c", "I2C Wiring Guide", "SDA/SCL pull-ups and addressing", "Communication", "I2C uses open-drain/open-collector signaling with pull-up resistors. Keep wiring short, verify voltage levels and resolve address conflicts."),
        HandbookEntry("spi", "SPI Wiring Guide", "SCK, MOSI, MISO and CS", "Communication", "SPI typically uses a clock, MOSI, MISO and one chip-select per peripheral. Confirm mode, voltage level and CS behavior for the exact devices."),
        HandbookEntry("safety", "Electronics Safety", "Bench and mains safety", "Safety", "Disconnect power before probing resistance or continuity. Discharge capacitors safely. For mains circuits use appropriate isolation, PPE and qualified personnel."),
        HandbookEntry("soldering", "Soldering & Inspection", "Reliable joints and inspection", "Workshop", "Use the correct tip temperature and flux for the joint. Inspect for bridges, cold joints and lifted pads. Clean flux residues when required by the board process.")
    )
}
