package com.example.data

import com.example.model.AdvancedCategoryItem
import com.example.model.ComponentItem
import com.example.model.PinoutItem
import com.example.model.AdvancedComponent
import com.example.model.AdvancedPinoutEntry
import com.example.model.Component3DType
import com.example.model.ElectricalRating
import com.example.model.FanMotorGuide
import com.example.model.FanMotorWire
import com.example.model.PracticalCircuitGuide
import com.example.model.SchematicSymbol
import com.example.model.TypicalCircuit

object SultanAdvancedData {

    // 25 Specific Categories Required by Specification
    val categories: List<AdvancedCategoryItem> = listOf(
        AdvancedCategoryItem("cat_transistors", "Transistors", "BJT NPN / PNP (TO-92, TO-220, SOT-23)", 16, Component3DType.TO92_TRANSISTOR, 0xFF00E676),
        AdvancedCategoryItem("cat_mosfets", "MOSFETs", "Power & Logic-Level MOSFETs (N/P-Ch)", 14, Component3DType.TO220_PACKAGE, 0xFF00E5FF),
        AdvancedCategoryItem("cat_diodes", "Diodes", "Rectifiers, Schottky, Zener & Bridge", 12, Component3DType.DIODE_DO41, 0xFFFFD600),
        AdvancedCategoryItem("cat_regulators", "Voltage Regulators", "Linear 78xx/79xx, LM317, AMS1117, Buck", 10, Component3DType.TO220_PACKAGE, 0xFFFF3D00),
        AdvancedCategoryItem("cat_opamps", "Operational Amplifiers", "LM358, LM324, LM741, TL072, NE5532", 8, Component3DType.DIP8_IC, 0xFF7C4DFF),
        AdvancedCategoryItem("cat_timers", "Timer ICs", "NE555, NE556, CMOS 7555, CD4541", 6, Component3DType.DIP8_IC, 0xFF00E676),
        AdvancedCategoryItem("cat_logic", "Logic ICs", "74HCxx & CD4000 Gates, Shift Registers", 12, Component3DType.DIP14_IC, 0xFF00E5FF),
        AdvancedCategoryItem("cat_mcu", "Microcontrollers", "ATmega328P, ATmega2560, STM32, RP2040", 8, Component3DType.DIP28_IC, 0xFFFFAB00),
        AdvancedCategoryItem("cat_esp", "ESP Modules", "ESP32-WROOM, ESP32-S3, NodeMCU, D1 Mini", 6, Component3DType.ESP32_BOARD, 0xFF00E5FF),
        AdvancedCategoryItem("cat_arduino", "Arduino Boards", "Uno R3, Nano V3, Mega 2560, Pro Mini", 6, Component3DType.ARDUINO_UNO, 0xFF00E676),
        AdvancedCategoryItem("cat_rpi", "Raspberry Pi", "Pi 4/5 40-Pin GPIO Header, Pi Pico", 4, Component3DType.DIP28_IC, 0xFFFF1744),
        AdvancedCategoryItem("cat_motor_drivers", "Motor Driver ICs", "L293D, L298N, ULN2003, A4988 Stepper", 8, Component3DType.DIP16_IC, 0xFFFF9100),
        AdvancedCategoryItem("cat_smps", "Power Supply ICs", "VIPer22A, UC3842, TOP223, MC34063", 6, Component3DType.DIP8_IC, 0xFFFF3D00),
        AdvancedCategoryItem("cat_optocouplers", "Optocouplers", "PC817, 4N35, MOC3021 Triac, 6N137", 6, Component3DType.DIP8_IC, 0xFFE040FB),
        AdvancedCategoryItem("cat_sensors", "Sensors", "DHT11/22, HC-SR04, BMP280, MPU6050, PIR", 10, Component3DType.DIP8_IC, 0xFF00E676),
        AdvancedCategoryItem("cat_connectors", "Connectors", "USB-C, USB-A, Micro-USB, DC Jack, RJ45", 8, Component3DType.SOIC8_SMD, 0xFF00E5FF),
        AdvancedCategoryItem("cat_displays", "Displays", "1602 LCD, 0.96 OLED I2C, 7-Segment, Matrix", 6, Component3DType.DIP16_IC, 0xFF7C4DFF),
        AdvancedCategoryItem("cat_relays", "Relays", "5V/12V SPDT Relay, SSR Solid State, Modules", 6, Component3DType.RELAY_SPDT, 0xFFFFAB00),
        AdvancedCategoryItem("cat_capacitors", "Capacitors", "Ceramic, Electrolytic, Film, Motor Run", 10, Component3DType.ELECTROLYTIC_CAP, 0xFF00E5FF),
        AdvancedCategoryItem("cat_inductors", "Inductors & Chokes", "Toroid, Drum Core, SMD Power, Common Mode", 6, Component3DType.RESISTOR_AXIAL, 0xFFFFD600),
        AdvancedCategoryItem("cat_transformers", "Transformers", "Center-Tap, SMPS Flyback, Current CT", 6, Component3DType.RELAY_SPDT, 0xFFFF3D00),
        AdvancedCategoryItem("cat_crystals", "Crystals & Oscillators", "16MHz HC-49S, 32.768kHz RTC, Active Osc", 4, Component3DType.DIP8_IC, 0xFF00E676),
        AdvancedCategoryItem("cat_switches", "Electronic Switches", "Toggle, Slide, Pushbutton, Rotary Encoder", 6, Component3DType.RELAY_SPDT, 0xFFFF9100),
        AdvancedCategoryItem("cat_fan_motors", "Fan / Motor Connections", "Ceiling Fan, Table Fan, 2/3/4/5-Wire, Caps", 12, Component3DType.CEILING_FAN_MOTOR, 0xFFFF1744),
        AdvancedCategoryItem("cat_wiring_guides", "Practical Wiring Guides", "22+ Bench Wiring Diagrams & Schematics", 22, Component3DType.ARDUINO_UNO, 0xFF00E676)
    )

    // Comprehensive Database of Components with Exact Pinouts, Ratings, and Warnings
    val allComponents: List<AdvancedComponent> = buildList {
        addAll(listOf(
        // 1. Transistors
        AdvancedComponent(
            id = "sa_bc547",
            partNumber = "BC547",
            name = "NPN General Purpose Bipolar Transistor",
            category = "Transistors",
            family = "BJT NPN",
            packageType = "TO-92",
            pinCount = 3,
            pins = listOf(
                AdvancedPinoutEntry(1, "COLLECTOR (C)", "Collector terminal", "Output", "Current sink connection"),
                AdvancedPinoutEntry(2, "BASE (B)", "Base terminal", "Input", "Controls conduction (Vbe ~ 0.7V)"),
                AdvancedPinoutEntry(3, "EMITTER (E)", "Emitter terminal", "GND", "Connects to Ground in standard NPN switch")
            ),
            threeDType = Component3DType.TO92_TRANSISTOR,
            absoluteMaxRatings = listOf(
                "Collector-Emitter Voltage (Vceo)" to "45 V",
                "Collector-Base Voltage (Vcbo)" to "50 V",
                "Emitter-Base Voltage (Vebo)" to "6.0 V",
                "Continuous Collector Current (Ic)" to "100 mA (200mA peak)",
                "Total Power Dissipation (Ptot)" to "500 mW",
                "Operating Temperature (Tj)" to "-65°C to +150°C"
            ),
            electricalRatings = listOf(
                ElectricalRating("DC Current Gain", "hFE", "Vce = 5V, Ic = 2mA", "110", "200-450", "800", "—"),
                ElectricalRating("Collector Saturation Voltage", "Vce(sat)", "Ic = 10mA, Ib = 0.5mA", "—", "90", "250", "mV"),
                ElectricalRating("Base Saturation Voltage", "Vbe(sat)", "Ic = 10mA, Ib = 0.5mA", "—", "700", "900", "mV"),
                ElectricalRating("Transition Frequency", "fT", "Vce = 5V, Ic = 10mA, f=100MHz", "100", "300", "—", "MHz")
            ),
            symbolType = "BJT_NPN",
            pinoutWarning = "PINOUT WARNING: In TO-92 package with flat face pointing towards you and leads pointing down: Pin 1 (Left) is COLLECTOR, Pin 2 (Middle) is BASE, Pin 3 (Right) is EMITTER (C-B-E). Note: American transistors like 2N3904 use E-B-C order!",
            description = "The BC547 is an industry standard European NPN epitaxial silicon transistor designed for low-noise audio amplification, small signal switching, and sensor interfacing.",
            typicalCircuits = listOf(
                TypicalCircuit(
                    title = "NPN Low-Side Transistor Switch (Relay / LED / Buzzer)",
                    description = "Drives high-current loads from a 3.3V or 5V microcontroller pin using BC547 as a saturated switch.",
                    componentsNeeded = listOf("BC547 NPN", "1kΩ - 4.7kΩ Base Resistor", "1N4007 Flyback Diode (for inductive loads)", "5V/12V Relay or LED"),
                    schematicDiagramType = "NPN_SWITCH",
                    formulas = listOf(
                        "Base Current: Ib = (V_control - V_be) / R_base",
                        "Required Base Current for Saturation: Ib >= Ic / (hFE_min / 10)"
                    ),
                    practicalNotes = "Always add a 1N4007 flyback diode in reverse across relay coils or DC motors to suppress high voltage back-EMF spikes that destroy the transistor."
                ),
                TypicalCircuit(
                    title = "Common-Emitter Small Signal Audio Pre-Amplifier",
                    description = "Single-supply class-A audio voltage amplifier with voltage divider biasing.",
                    componentsNeeded = listOf("BC547", "R1: 47kΩ", "R2: 10kΩ", "Rc: 4.7kΩ", "Re: 1kΩ", "Cin: 10µF", "Cout: 10µF", "Ce: 100µF"),
                    schematicDiagramType = "CE_AMPLIFIER",
                    formulas = listOf("Voltage Gain: Av ≈ - Rc / Re (without bypass) or - Rc / r'e (with bypass)"),
                    practicalNotes = "Ce bypasses the emitter resistor Re to provide high AC voltage gain while preserving DC thermal bias stability."
                )
            ),
            commonApplications = listOf(
                "Arduino & Raspberry Pi digital output buffer",
                "Relay, buzzer, and LED driver",
                "Small signal audio preamplifier stage",
                "Optical and sensor trigger interface"
            ),
            equivalents = listOf("BC548", "BC549 (Low Noise)", "2N3904 (Note pinout E-B-C)", "2N2222 (Higher current)", "S8050"),
            datasheetNotes = "Classified by gain suffix: BC547A (hFE 110-220), BC547B (hFE 200-450), BC547C (hFE 420-800)."
        ),

        AdvancedComponent(
            id = "sa_2n2222",
            partNumber = "2N2222 / PN2222",
            name = "High-Current NPN Fast Switching Transistor",
            category = "Transistors",
            family = "BJT NPN",
            packageType = "TO-92 / TO-18 Metal Can",
            pinCount = 3,
            pins = listOf(
                AdvancedPinoutEntry(1, "EMITTER (E)", "Emitter terminal", "GND", "Connects to Ground (Pin 1 on TO-92 flat face)"),
                AdvancedPinoutEntry(2, "BASE (B)", "Base terminal", "Input", "Saturation control terminal"),
                AdvancedPinoutEntry(3, "COLLECTOR (C)", "Collector terminal", "Output", "Sinks load current up to 800mA")
            ),
            threeDType = Component3DType.TO92_TRANSISTOR,
            absoluteMaxRatings = listOf(
                "Collector-Emitter Voltage (Vceo)" to "40 V",
                "Collector-Base Voltage (Vcbo)" to "75 V",
                "Continuous Collector Current (Ic)" to "800 mA",
                "Total Power Dissipation (Ptot)" to "625 mW",
                "Transition Frequency (fT)" to "300 MHz"
            ),
            electricalRatings = listOf(
                ElectricalRating("DC Current Gain", "hFE", "Vce = 10V, Ic = 150mA", "100", "—", "300", "—"),
                ElectricalRating("Collector-Emitter Saturation", "Vce(sat)", "Ic = 500mA, Ib = 50mA", "—", "—", "1.0", "V")
            ),
            symbolType = "BJT_NPN",
            pinoutWarning = "CRITICAL PINOUT DIFFERENCE: Looking at TO-92 flat face: Pin 1 is EMITTER, Pin 2 is BASE, Pin 3 is COLLECTOR (E-B-C). Do not confuse with European BC547 which is C-B-E!",
            description = "High-speed, medium-power NPN switching transistor capable of driving loads up to 800mA, including high-power relays, small DC motors, and high-brightness LED clusters.",
            typicalCircuits = listOf(
                TypicalCircuit(
                    title = "DC Motor & Solenoid High-Speed Switch",
                    description = "Switching 12V DC loads with microcontroller logic.",
                    componentsNeeded = listOf("2N2222", "1kΩ Base Resistor", "1N4007 Diode", "12V Solenoid / Motor"),
                    schematicDiagramType = "NPN_SWITCH",
                    formulas = listOf("P_transistor = V_ce(sat) * I_load + V_be * I_base"),
                    practicalNotes = "For currents > 500mA, ensure adequate airflow as the TO-92 package will warm up."
                )
            ),
            commonApplications = listOf("High current relay driver", "DC motor on/off controller", "Fast pulse generator", "RF transmitter oscillator"),
            equivalents = listOf("PN2222A", "2N3904 (Lower current)", "BC337", "S8050"),
            datasheetNotes = "The original 2N2222 came in TO-18 metal can. The plastic TO-92 package is technically PN2222 or 2N2222A."
        ),

        AdvancedComponent(
            id = "sa_bc557",
            partNumber = "BC557",
            name = "PNP General Purpose Bipolar Transistor",
            category = "Transistors",
            family = "BJT PNP",
            packageType = "TO-92",
            pinCount = 3,
            pins = listOf(
                AdvancedPinoutEntry(1, "COLLECTOR (C)", "Collector terminal", "Output", "Sinks current to lower rail or ground"),
                AdvancedPinoutEntry(2, "BASE (B)", "Base terminal", "Input", "Conduction occurs when Vb < Ve - 0.7V"),
                AdvancedPinoutEntry(3, "EMITTER (E)", "Emitter terminal", "Power", "Connects to positive supply Vcc")
            ),
            threeDType = Component3DType.TO92_TRANSISTOR,
            absoluteMaxRatings = listOf(
                "Collector-Emitter Voltage (Vceo)" to "-45 V",
                "Collector-Base Voltage (Vcbo)" to "-50 V",
                "Continuous Collector Current (Ic)" to "-100 mA",
                "Power Dissipation" to "500 mW"
            ),
            electricalRatings = listOf(
                ElectricalRating("DC Current Gain", "hFE", "Vce = -5V, Ic = -2mA", "125", "220-475", "800", "—"),
                ElectricalRating("Collector Saturation", "Vce(sat)", "Ic = -10mA, Ib = -0.5mA", "—", "-90", "-300", "mV")
            ),
            symbolType = "BJT_PNP",
            pinoutWarning = "Looking at flat face: Pin 1 is COLLECTOR, Pin 2 is BASE, Pin 3 is EMITTER (C-B-E). Complementary pair to BC547.",
            description = "The BC557 is a classic PNP complementary partner to BC547, widely used for high-side switching, push-pull complementary amplifier output stages, and current mirrors.",
            typicalCircuits = listOf(
                TypicalCircuit(
                    title = "PNP High-Side Load Switch",
                    description = "Switches Vcc power rail to a load with active-low logic signal.",
                    componentsNeeded = listOf("BC557 PNP", "10kΩ Pull-up Resistor to Vcc", "1kΩ Base Resistor", "NPN Pre-driver (if Vcc > V_logic)"),
                    schematicDiagramType = "PNP_HIGH_SIDE",
                    formulas = listOf("V_be = V_base - V_emitter ≈ -0.7V for ON"),
                    practicalNotes = "If controlling a 12V rail with 5V microcontroller, use an NPN transistor to pull the PNP base to ground safely."
                )
            ),
            commonApplications = listOf("High-side power switching", "Audio Push-Pull Class AB stage", "Current mirror circuit"),
            equivalents = listOf("BC558", "BC559", "2N3906 (E-B-C pinout)", "BC327"),
            datasheetNotes = "Matches BC547 specifications in reverse polarity."
        ),

        // 2. MOSFETs
        AdvancedComponent(
            id = "sa_irfz44n",
            partNumber = "IRFZ44N",
            name = "55V 49A N-Channel Power MOSFET",
            category = "MOSFETs",
            family = "N-Channel Power HEXFET",
            packageType = "TO-220AB",
            pinCount = 3,
            pins = listOf(
                AdvancedPinoutEntry(1, "GATE (G)", "Gate control terminal", "Input", "Voltage driven; requires 10V for full RDS(on)"),
                AdvancedPinoutEntry(2, "DRAIN (D)", "Drain power terminal", "Output", "Internally tied to metal mounting tab"),
                AdvancedPinoutEntry(3, "SOURCE (S)", "Source ground terminal", "GND", "Connects to power ground return")
            ),
            threeDType = Component3DType.TO220_PACKAGE,
            absoluteMaxRatings = listOf(
                "Drain-to-Source Voltage (Vds)" to "55 V",
                "Continuous Drain Current (Id @ 25°C)" to "49 A",
                "Continuous Drain Current (Id @ 100°C)" to "35 A",
                "Pulsed Drain Current (Idm)" to "160 A",
                "Maximum Power Dissipation (Pd)" to "94 W",
                "Gate-to-Source Voltage (Vgs)" to "±20 V",
                "Operating Junction Temp (Tj)" to "-55°C to +175°C"
            ),
            electricalRatings = listOf(
                ElectricalRating("Static Drain-Source On-Resistance", "RDS(on)", "Vgs = 10V, Id = 25A", "—", "17.5", "22", "mΩ"),
                ElectricalRating("Gate Threshold Voltage", "Vgs(th)", "Vds = Vgs, Id = 250µA", "2.0", "—", "4.0", "V"),
                ElectricalRating("Forward Transconductance", "gfs", "Vds = 25V, Id = 25A", "19", "—", "—", "S"),
                ElectricalRating("Total Gate Charge", "Qg", "Id = 25A, Vds = 44V, Vgs = 10V", "—", "63", "95", "nC")
            ),
            symbolType = "MOSFET_N_CH",
            pinoutWarning = "STANDARD TO-220 PINOUT: Facing the front label with pins downward and metal tab at top: Pin 1 (Left) = GATE, Pin 2 (Middle) = DRAIN, Pin 3 (Right) = SOURCE (G-D-S). The metal tab is connected to Pin 2 (DRAIN).",
            description = "The IRFZ44N utilizes advanced HEXFET technology to achieve extremely low on-resistance per silicon area. It is one of the most widely used power MOSFETs in DC motor speed controllers, high-current inverters, automotive switching, and SMPS power stages.",
            typicalCircuits = listOf(
                TypicalCircuit(
                    title = "High-Current DC Motor / Heater PWM Controller",
                    description = "Drives heavy 12V/24V loads up to 30A with PWM speed control.",
                    componentsNeeded = listOf("IRFZ44N MOSFET", "TC4420 or TC4427 Gate Driver / NPN buffer", "100Ω Gate Resistor", "10kΩ Gate-Source Bleeder Resistor", "MBR20100 Schottky Flyback Diode"),
                    schematicDiagramType = "MOSFET_LOW_SIDE",
                    formulas = listOf(
                        "Conduction Loss: P_cond = Id² * RDS(on)",
                        "Example at 10A: P_cond = 10² * 0.0175Ω = 1.75 Watts"
                    ),
                    practicalNotes = "IMPORTANT: Standard IRFZ44N needs 10V on the gate to fully open. If driving directly from a 3.3V or 5V microcontroller, use the logic-level IRLZ44N instead, otherwise the MOSFET will operate in linear mode and overheat rapidly!"
                )
            ),
            commonApplications = listOf("H-Bridge motor drivers", "Inverter 12V to 220V power stages", "High-power LED strip dimmers", "Electronic DC load circuits"),
            equivalents = listOf("IRLZ44N (Logic Level 5V Gate)", "IRF3205 (55V 110A)", "IRF540N (100V 33A)", "STP55NF06"),
            datasheetNotes = "Mount on an aluminum heatsink with thermal paste and mica insulator if the tab is connected to a shared grounded heatsink."
        ),

        AdvancedComponent(
            id = "sa_irlz44n",
            partNumber = "IRLZ44N",
            name = "Logic-Level 55V 47A N-Channel Power MOSFET",
            category = "MOSFETs",
            family = "Logic-Level N-Channel",
            packageType = "TO-220AB",
            pinCount = 3,
            pins = listOf(
                AdvancedPinoutEntry(1, "GATE (G)", "Logic Level Gate", "Input", "Fully saturates at 4.5V logic"),
                AdvancedPinoutEntry(2, "DRAIN (D)", "Drain terminal", "Output", "Connected to metal tab"),
                AdvancedPinoutEntry(3, "SOURCE (S)", "Source terminal", "GND", "Connected to ground")
            ),
            threeDType = Component3DType.TO220_PACKAGE,
            absoluteMaxRatings = listOf(
                "Drain-Source Voltage (Vds)" to "55 V",
                "Continuous Drain Current (Id)" to "47 A",
                "Gate-Source Voltage (Vgs)" to "±16 V",
                "Power Dissipation" to "110 W"
            ),
            electricalRatings = listOf(
                ElectricalRating("On-Resistance @ 5V Gate", "RDS(on)", "Vgs = 5.0V, Id = 25A", "—", "22", "25", "mΩ"),
                ElectricalRating("On-Resistance @ 4V Gate", "RDS(on)", "Vgs = 4.0V, Id = 21A", "—", "25", "35", "mΩ"),
                ElectricalRating("Gate Threshold", "Vgs(th)", "Vds = Vgs, Id = 250µA", "1.0", "—", "2.0", "V")
            ),
            symbolType = "MOSFET_N_CH",
            pinoutWarning = "PINOUT: Pin 1 = GATE, Pin 2 = DRAIN, Pin 3 = SOURCE (G-D-S). Perfect drop-in logic-level replacement for IRFZ44N.",
            description = "Logic-Level N-Channel MOSFET specifically engineered for direct driving from 5V Arduino, ESP32 (with 3.3V-5V shifter), and TTL/CMOS microcontrollers without external gate drivers.",
            typicalCircuits = listOf(
                TypicalCircuit(
                    title = "Direct Arduino PWM 12V LED / Motor Driver",
                    description = "Direct connection to MCU GPIO with no auxiliary gate driver required.",
                    componentsNeeded = listOf("IRLZ44N", "220Ω Gate Series Resistor", "10kΩ Gate Pulldown Resistor", "Load"),
                    schematicDiagramType = "MOSFET_LOW_SIDE",
                    formulas = listOf("Direct Gate Drive: Vgs = 5.0V gives RDS(on) <= 25mΩ"),
                    practicalNotes = "A 10kΩ pull-down resistor from Gate to Source ensures the MOSFET remains completely OFF during microcontroller boot and reset."
                )
            ),
            commonApplications = listOf("Arduino direct-drive projects", "3D printer heated bed controller", "Robotics motor control"),
            equivalents = listOf("IRFZ44N (Requires 10V gate)", "STP55NF06L", "FQP30N06L"),
            datasheetNotes = "Ideal for battery-operated and microcontroller-driven systems."
        ),

        // 3. Diodes
        AdvancedComponent(
            id = "sa_1n4007",
            partNumber = "1N4007",
            name = "1000V 1A Silicon Standard Recovery Rectifier",
            category = "Diodes",
            family = "Silicon Rectifier",
            packageType = "DO-41 Leaded Axial",
            pinCount = 2,
            pins = listOf(
                AdvancedPinoutEntry(1, "ANODE (A)", "Positive current entry", "Input", "No band side (longer lead)"),
                AdvancedPinoutEntry(2, "CATHODE (K)", "Negative output / Banded side", "Output", "Marked with silver/white painted band")
            ),
            threeDType = Component3DType.DIODE_DO41,
            absoluteMaxRatings = listOf(
                "Peak Repetitive Reverse Voltage (Vrrm)" to "1000 V",
                "Average Rectified Forward Current (Io)" to "1.0 A",
                "Non-Repetitive Peak Surge Current (Ifsm)" to "30 A (8.3ms half sine)",
                "Operating Temperature" to "-65°C to +175°C"
            ),
            electricalRatings = listOf(
                ElectricalRating("Forward Voltage Drop", "VF", "If = 1.0A, Tj = 25°C", "—", "0.7", "1.1", "V"),
                ElectricalRating("Reverse Leakage Current", "IR", "Vr = 1000V, Tj = 25°C", "—", "0.05", "5.0", "µA")
            ),
            symbolType = "DIODE_RECTIFIER",
            pinoutWarning = "The silver or white printed ring indicates the CATHODE terminal. Current flows only from Anode to Cathode (in the direction of the arrow).",
            description = "The 1N4007 is the world's most ubiquitous general-purpose mains rectifier diode. Capable of blocking up to 1000V reverse voltage, it is universally found in AC-to-DC power supplies, flyback protection, and reverse-polarity protection circuits.",
            typicalCircuits = listOf(
                TypicalCircuit(
                    title = "Full-Wave Bridge Rectifier (4x 1N4007)",
                    description = "Converts secondary AC voltage from a step-down transformer to pulsating DC.",
                    componentsNeeded = listOf("4x 1N4007 Diodes", "Transformer (e.g. 230V to 12VAC)", "1000µF 25V Electrolytic Capacitor"),
                    schematicDiagramType = "BRIDGE_RECTIFIER",
                    formulas = listOf("V_peak_dc = (V_rms_ac * √2) - 2 * V_f ≈ (12 * 1.414) - 1.4V ≈ 15.57V DC"),
                    practicalNotes = "Add a 100nF ceramic cap in parallel with the electrolytic smoothing capacitor to suppress high-frequency line noise."
                ),
                TypicalCircuit(
                    title = "Relay Coil Flyback (Snubber) Diode",
                    description = "Absorbs inductive kickback spike generated when a relay de-energizes.",
                    componentsNeeded = listOf("1N4007", "5V/12V Relay"),
                    schematicDiagramType = "FLYBACK_SNUBBER",
                    formulas = listOf("Back EMF = - L * (di / dt) can reach 200V+ without diode"),
                    practicalNotes = "Connect Cathode (band) to the positive relay supply (+V) and Anode to the transistor switching collector."
                )
            ),
            commonApplications = listOf("AC mains linear power supplies", "Reverse polarity protection on battery inputs", "Inductive flyback suppressor"),
            equivalents = listOf("1N4001 (50V)", "1N4004 (400V)", "1N5408 (3A 1000V)", "UF4007 (Ultra-fast version)"),
            datasheetNotes = "For high-frequency SMPS (above 1 kHz), use ultra-fast diodes like UF4007 or Schottky diodes instead."
        ),

        // 4. Voltage Regulators
        AdvancedComponent(
            id = "sa_lm7805",
            partNumber = "LM7805 / L7805CV",
            name = "+5V 1.5A Positive Linear Voltage Regulator",
            category = "Voltage Regulators",
            family = "Linear 3-Terminal",
            packageType = "TO-220",
            pinCount = 3,
            pins = listOf(
                AdvancedPinoutEntry(1, "INPUT (Vin)", "Unregulated DC Input", "Power", "Input voltage: +7V to +25V DC (min 7V for dropout)"),
                AdvancedPinoutEntry(2, "GROUND (GND)", "Ground reference", "GND", "Common ground (internally tied to mounting tab)"),
                AdvancedPinoutEntry(3, "OUTPUT (Vout)", "Regulated +5.0V DC", "Power", "Provides stable +5V output up to 1.5A")
            ),
            threeDType = Component3DType.TO220_PACKAGE,
            absoluteMaxRatings = listOf(
                "Input Voltage (Vin max)" to "35 V DC",
                "Output Current (Io max with heatsink)" to "1.5 A",
                "Internal Thermal Shutdown" to "150°C",
                "Dropout Voltage" to "2.0 V (Vin must be >= 7.0V)"
            ),
            electricalRatings = listOf(
                ElectricalRating("Output Voltage", "Vo", "Vin = 10V, Io = 500mA", "4.8", "5.0", "5.2", "V"),
                ElectricalRating("Line Regulation", "Reg_line", "Vin = 7V to 25V", "—", "3.0", "50", "mV"),
                ElectricalRating("Load Regulation", "Reg_load", "Io = 5mA to 1.5A", "—", "15", "50", "mV"),
                ElectricalRating("Quiescent Current", "Iq", "Vin = 10V, Io = 0", "—", "5.0", "8.0", "mA")
            ),
            symbolType = "VOLTAGE_REGULATOR_3PIN",
            pinoutWarning = "PINOUT (TO-220 Facing Front): Pin 1 (Left) = INPUT, Pin 2 (Center) = GROUND, Pin 3 (Right) = OUTPUT (I-G-O). Tab is GROUND.",
            description = "The classic LM7805 delivers a rock-solid +5V DC output with internal current limiting, thermal shutdown, and safe-area compensation.",
            typicalCircuits = listOf(
                TypicalCircuit(
                    title = "Standard 5V Power Supply with Noise Decoupling",
                    description = "Clean +5V logic power supply from a 9V/12V DC input.",
                    componentsNeeded = listOf("LM7805", "Cin: 0.33µF Ceramic / 100µF Electrolytic", "Cout: 0.1µF Ceramic / 10µF Electrolytic", "1N4007 Reverse Protection Diode"),
                    schematicDiagramType = "REGULATOR_7805",
                    formulas = listOf("Power Dissipation: P_d = (Vin - Vout) * I_load", "Example at 12V in, 500mA out: P_d = (12 - 5) * 0.5 = 3.5 Watts (Heatsink Required)"),
                    practicalNotes = "Cin (0.33µF) prevents high frequency parasitic oscillations. Place capacitors as close to regulator pins as physically possible."
                )
            ),
            commonApplications = listOf("5V Microcontroller and TTL supply", "Sensor bus power", "Bench laboratory power supply"),
            equivalents = listOf("L7805CV", "LM340-5.0", "AMS1117-5.0 (Low dropout SMD)", "LM2596 (Buck switching alternative)"),
            datasheetNotes = "For high current or large input-to-output voltage differences, use a switching buck converter to prevent severe thermal waste."
        ),

        // 5. Op-Amps
        AdvancedComponent(
            id = "sa_lm358",
            partNumber = "LM358",
            name = "Dual Low-Power Single-Supply Operational Amplifier",
            category = "Operational Amplifiers",
            family = "Dual Op-Amp",
            packageType = "DIP-8 / SOIC-8",
            pinCount = 8,
            pins = listOf(
                AdvancedPinoutEntry(1, "OUT1", "Output Amp 1", "Output", "Output swing: 0V to (Vcc - 1.5V)"),
                AdvancedPinoutEntry(2, "IN1-", "Inverting Input 1", "Analog", "Negative input of op-amp 1"),
                AdvancedPinoutEntry(3, "IN1+", "Non-Inverting Input 1", "Analog", "Positive input of op-amp 1"),
                AdvancedPinoutEntry(4, "GND / V-", "Ground / Negative supply rail", "GND", "0V for single supply or -Vcc for split supply"),
                AdvancedPinoutEntry(5, "IN2+", "Non-Inverting Input 2", "Analog", "Positive input of op-amp 2"),
                AdvancedPinoutEntry(6, "IN2-", "Inverting Input 2", "Analog", "Negative input of op-amp 2"),
                AdvancedPinoutEntry(7, "OUT2", "Output Amp 2", "Output", "Output of op-amp 2"),
                AdvancedPinoutEntry(8, "VCC / V+", "Positive Supply Rail", "Power", "+3V to +32V single or ±1.5V to ±16V split")
            ),
            threeDType = Component3DType.DIP8_IC,
            absoluteMaxRatings = listOf(
                "Supply Voltage (Vcc)" to "32 V (or ±16V)",
                "Differential Input Voltage" to "32 V",
                "Input Voltage Range" to "-0.3V to 32V (includes ground)",
                "Output Short-Circuit to GND" to "Continuous"
            ),
            electricalRatings = listOf(
                ElectricalRating("Input Offset Voltage", "Vio", "Vcc = 5V, Vo = 1.4V", "—", "2.0", "7.0", "mV"),
                ElectricalRating("Input Bias Current", "Ib", "Tj = 25°C", "—", "45", "250", "nA"),
                ElectricalRating("Large Signal Voltage Gain", "Avd", "Vcc = 15V, Rl = 2kΩ", "25", "100", "—", "V/mV"),
                ElectricalRating("Unity Gain Bandwidth", "GBW", "f = 100 kHz", "0.7", "1.0", "—", "MHz")
            ),
            symbolType = "DUAL_OPAMP",
            pinoutWarning = "STANDARD DIP-8 NOTCH ORIENTATION: With top notch pointing UP, Pin 1 is top-left, counting counter-clockwise: 1 to 4 on left (top to bottom), 5 to 8 on right (bottom to top).",
            description = "The LM358 contains two independent, high-gain, internally frequency-compensated op-amps specifically designed to operate from a single power supply over a wide voltage range.",
            typicalCircuits = listOf(
                TypicalCircuit(
                    title = "Non-Inverting AC/DC Amplifier",
                    description = "Amplifies weak analog sensor signals without inverting polarity.",
                    componentsNeeded = listOf("LM358", "R_feedback: 100kΩ", "R_ground: 10kΩ", "Decoupling cap 100nF"),
                    schematicDiagramType = "OPAMP_NON_INVERTING",
                    formulas = listOf("Gain: Av = 1 + (Rf / Rg) = 1 + (100k / 10k) = 11x (+20.8 dB)"),
                    practicalNotes = "Because the inputs include ground rail, LM358 can sense 0V ground-referenced signals directly on single 5V supply."
                ),
                TypicalCircuit(
                    title = "Precision Voltage Comparator with Hysteresis (Schmitt Trigger)",
                    description = "Compares sensor voltage against threshold with chatter-free transition.",
                    componentsNeeded = listOf("LM358", "10kΩ Potentiometer (Threshold)", "100kΩ Positive Feedback Resistor"),
                    schematicDiagramType = "OPAMP_COMPARATOR",
                    formulas = listOf("Hysteresis Width: ΔVh = Vcc * (Rin / (Rin + Rf))"),
                    practicalNotes = "Positive feedback creates clean high/low switching ideal for LDR light switches and temperature triggers."
                )
            ),
            commonApplications = listOf("Sensor signal conditioning (LDR, Thermistor, Current shunt)", "Active audio low-pass filter", "Precision comparator with hysteresis"),
            equivalents = listOf("LM324 (Quad version in DIP-14)", "TL072 (JFET High Impedance)", "NE5532 (Low Noise Audio)", "LM393 (Dedicated open-collector comparator)"),
            datasheetNotes = "For audio preamps, TL072 or NE5532 offer lower noise and higher slew rates than LM358."
        ),

        // 6. Microcontrollers
        AdvancedComponent(
            id = "sa_atmega328p",
            partNumber = "ATmega328P",
            name = "8-bit AVR Microcontroller (Arduino Uno Core)",
            category = "Microcontrollers",
            family = "AVR RISC",
            packageType = "DIP-28 / TQFP-32",
            pinCount = 28,
            pins = listOf(
                AdvancedPinoutEntry(1, "PC6 / RESET", "Active-Low Reset Pin", "Control", "Arduino Reset (Pull high to Vcc with 10kΩ)"),
                AdvancedPinoutEntry(2, "PD0 / RXD", "Digital Pin 0 (USART RX)", "Comm", "Serial Receive data"),
                AdvancedPinoutEntry(3, "PD1 / TXD", "Digital Pin 1 (USART TX)", "Comm", "Serial Transmit data"),
                AdvancedPinoutEntry(4, "PD2 / INT0", "Digital Pin 2 (External Interrupt 0)", "I/O", "Hardware interrupt INT0"),
                AdvancedPinoutEntry(5, "PD3 / INT1 / PWM", "Digital Pin 3 (PWM ~)", "PWM", "Timer2 OC2B PWM output"),
                AdvancedPinoutEntry(6, "PD4 / T0", "Digital Pin 4", "I/O", "Timer0 external clock input"),
                AdvancedPinoutEntry(7, "VCC", "Digital Supply Voltage", "Power", "+5.0V (+1.8V to 5.5V)"),
                AdvancedPinoutEntry(8, "GND", "Digital Ground", "GND", "0V Ground reference"),
                AdvancedPinoutEntry(9, "PB6 / XTAL1", "Crystal Oscillator In", "Analog", "Connects to 16MHz Crystal + 22pF cap"),
                AdvancedPinoutEntry(10, "PB7 / XTAL2", "Crystal Oscillator Out", "Analog", "Connects to 16MHz Crystal + 22pF cap"),
                AdvancedPinoutEntry(11, "PD5 / T1 / PWM", "Digital Pin 5 (PWM ~)", "PWM", "Timer0 OC0B PWM"),
                AdvancedPinoutEntry(12, "PD6 / AIN0 / PWM", "Digital Pin 6 (PWM ~)", "PWM", "Timer0 OC0A PWM"),
                AdvancedPinoutEntry(13, "PD7 / AIN1", "Digital Pin 7", "I/O", "Analog comparator negative input"),
                AdvancedPinoutEntry(14, "PB0 / ICP1", "Digital Pin 8", "I/O", "Timer1 input capture"),
                AdvancedPinoutEntry(15, "PB1 / OC1A / PWM", "Digital Pin 9 (PWM ~)", "PWM", "Timer1 16-bit PWM A"),
                AdvancedPinoutEntry(16, "PB2 / SS / PWM", "Digital Pin 10 (SPI SS / PWM ~)", "PWM", "SPI Slave Select"),
                AdvancedPinoutEntry(17, "PB3 / MOSI / PWM", "Digital Pin 11 (SPI MOSI / PWM ~)", "Comm", "SPI Master Out Slave In"),
                AdvancedPinoutEntry(18, "PB4 / MISO", "Digital Pin 12 (SPI MISO)", "Comm", "SPI Master In Slave Out"),
                AdvancedPinoutEntry(19, "PB5 / SCK", "Digital Pin 13 (SPI SCK / Builtin LED)", "Comm", "SPI Serial Clock & Built-in LED"),
                AdvancedPinoutEntry(20, "AVCC", "Analog Supply Rail", "Power", "Connect to +5V through 10µH inductor or ferrite"),
                AdvancedPinoutEntry(21, "AREF", "Analog Reference Voltage", "Analog", "External ADC reference (0.1µF to GND if unused)"),
                AdvancedPinoutEntry(22, "GND", "Analog Ground", "GND", "0V Ground reference"),
                AdvancedPinoutEntry(23, "PC0 / ADC0", "Analog Pin A0", "Analog", "10-bit ADC Channel 0"),
                AdvancedPinoutEntry(24, "PC1 / ADC1", "Analog Pin A1", "Analog", "10-bit ADC Channel 1"),
                AdvancedPinoutEntry(25, "PC2 / ADC2", "Analog Pin A2", "Analog", "10-bit ADC Channel 2"),
                AdvancedPinoutEntry(26, "PC3 / ADC3", "Analog Pin A3", "Analog", "10-bit ADC Channel 3"),
                AdvancedPinoutEntry(27, "PC4 / ADC4 / SDA", "Analog Pin A4 (I2C SDA)", "Comm", "I2C Two-Wire Serial Data"),
                AdvancedPinoutEntry(28, "PC5 / ADC5 / SCL", "Analog Pin A5 (I2C SCL)", "Comm", "I2C Two-Wire Serial Clock")
            ),
            threeDType = Component3DType.DIP28_IC,
            absoluteMaxRatings = listOf(
                "Operating Voltage (Vcc)" to "1.8V to 5.5V",
                "DC Current per I/O Pin" to "40.0 mA (20mA recommended)",
                "Total DC Current from Vcc/GND" to "200.0 mA",
                "Maximum Clock Frequency" to "20 MHz (16 MHz on Arduino)"
            ),
            electricalRatings = listOf(
                ElectricalRating("Flash Memory", "Flash", "Bytes", "—", "32768 (32 KB)", "—", "Bytes"),
                ElectricalRating("SRAM Data Memory", "SRAM", "Bytes", "—", "2048 (2 KB)", "—", "Bytes"),
                ElectricalRating("EEPROM Memory", "EEPROM", "Bytes", "—", "1024 (1 KB)", "—", "Bytes"),
                ElectricalRating("ADC Resolution", "ADC", "Bits", "—", "10", "—", "Bits")
            ),
            symbolType = "MCU_DIP28",
            pinoutWarning = "DIP-28 PIN LAYOUT: Pins 1 to 14 on left (top to bottom), Pins 15 to 28 on right (bottom to top). Remember to connect BOTH Pin 7 (VCC) and Pin 20 (AVCC) to 5V, and BOTH Pin 8 and Pin 22 to GND!",
            description = "High-performance Microchip (Atmel) 8-bit AVR RISC microcontroller. It powers the Arduino Uno, Arduino Nano, and countless embedded industrial and hobbyist projects.",
            typicalCircuits = listOf(
                TypicalCircuit(
                    title = "Minimal Standalone Breadboard Arduino (ATmega328P-PU)",
                    description = "Runs Arduino sketches without an official Arduino board.",
                    componentsNeeded = listOf("ATmega328P-PU", "16 MHz HC-49S Crystal", "2x 22pF Ceramic Capacitors", "10kΩ Reset Pull-up Resistor", "100nF Ceramic Decoupling Cap", "CP2102 / FT232RL USB-UART Module"),
                    schematicDiagramType = "STANDALONE_ATMEGA",
                    formulas = listOf("Oscillator Capacitors: C1 = C2 = 2 * C_load - C_stray ≈ 2 * 15pF - 8pF = 22pF"),
                    practicalNotes = "Connect a 100nF capacitor between the DTR pin of the USB-to-Serial converter and Pin 1 (RESET) of the ATmega328P to enable auto-reset during sketch upload!"
                )
            ),
            commonApplications = listOf("Arduino Uno R3 and Nano V3 development boards", "Custom PCB embedded controllers", "Robotics sensors and actuator drivers"),
            equivalents = listOf("ATmega328PB (Enhanced dual USART/I2C)", "ATmega168P (16KB Flash)", "ATmega88P (8KB Flash)", "STM32F103 (32-bit ARM upgrade)"),
            datasheetNotes = "Comes pre-flashed with the Optiboot bootloader on official Arduino kits for seamless USB programming via UART."
        ),

        // 7. ESP Modules
        AdvancedComponent(
            id = "sa_esp32_wroom",
            partNumber = "ESP32-WROOM-32",
            name = "Dual-Core 240MHz Wi-Fi & Bluetooth BLE SoC Module",
            category = "ESP Modules",
            family = "Espressif Xtensa 32-bit",
            packageType = "38-Pin DevKit Board / SMD Module",
            pinCount = 38,
            pins = listOf(
                AdvancedPinoutEntry(1, "3V3", "3.3V Power Input / Output", "Power", "Main regulated 3.3V power rail"),
                AdvancedPinoutEntry(2, "EN", "Chip Enable / Reset", "Control", "Active High (Pull to 3.3V with 10kΩ; button to GND resets)"),
                AdvancedPinoutEntry(3, "VP (GPIO36)", "Sensor VP / ADC1_CH0", "Analog", "Input-Only ADC pin (No internal pull-up/down)"),
                AdvancedPinoutEntry(4, "VN (GPIO39)", "Sensor VN / ADC1_CH3", "Analog", "Input-Only ADC pin"),
                AdvancedPinoutEntry(5, "GPIO34", "ADC1_CH6", "Analog", "Input-Only pin"),
                AdvancedPinoutEntry(6, "GPIO35", "ADC1_CH7", "Analog", "Input-Only pin"),
                AdvancedPinoutEntry(7, "GPIO32", "ADC1_CH4 / Touch 9", "I/O", "RTC GPIO & Capacitive Touch"),
                AdvancedPinoutEntry(8, "GPIO33", "ADC1_CH5 / Touch 8", "I/O", "RTC GPIO & Capacitive Touch"),
                AdvancedPinoutEntry(9, "GPIO25", "DAC1 / ADC2_CH8", "Analog", "8-bit True DAC Analog Output 1"),
                AdvancedPinoutEntry(10, "GPIO26", "DAC2 / ADC2_CH9", "Analog", "8-bit True DAC Analog Output 2"),
                AdvancedPinoutEntry(11, "GPIO27", "Touch 7 / ADC2_CH7", "I/O", "General Purpose I/O"),
                AdvancedPinoutEntry(12, "GPIO14", "HSPI_CLK / Touch 6", "Comm", "SPI Clock"),
                AdvancedPinoutEntry(13, "GPIO12", "HSPI_MISO / Touch 5", "Comm", "Boot strapping pin (Must be LOW at boot)"),
                AdvancedPinoutEntry(14, "GND", "Ground", "GND", "Power Ground"),
                AdvancedPinoutEntry(15, "GPIO13", "HSPI_MOSI / Touch 4", "Comm", "SPI Data Out"),
                AdvancedPinoutEntry(16, "GPIO9 / SD2", "Flash Memory SD2", "Control", "RESERVED - Connected to SPI Flash, do not use!"),
                AdvancedPinoutEntry(17, "GPIO10 / SD3", "Flash Memory SD3", "Control", "RESERVED - Connected to SPI Flash, do not use!"),
                AdvancedPinoutEntry(18, "GPIO11 / CMD", "Flash Memory CMD", "Control", "RESERVED - Connected to SPI Flash, do not use!"),
                AdvancedPinoutEntry(19, "VIN / 5V", "5V Power Input (VBUS)", "Power", "External 5V supply connected to onboard AMS1117-3.3V"),
                AdvancedPinoutEntry(20, "GPIO6 / CLK", "Flash Memory CLK", "Control", "RESERVED - Connected to SPI Flash!"),
                AdvancedPinoutEntry(21, "GPIO7 / SD0", "Flash Memory SD0", "Control", "RESERVED - Connected to SPI Flash!"),
                AdvancedPinoutEntry(22, "GPIO8 / SD1", "Flash Memory SD1", "Control", "RESERVED - Connected to SPI Flash!"),
                AdvancedPinoutEntry(23, "GPIO15", "HSPI_SS / Touch 3", "Comm", "Boot strapping pin (Must be HIGH for flashing output)"),
                AdvancedPinoutEntry(24, "GPIO2", "Built-in LED / Touch 2", "I/O", "Boot strapping pin (Must be floating or LOW to boot)"),
                AdvancedPinoutEntry(25, "GPIO0", "Boot Mode Select / Touch 1", "Control", "Pushed to GND to enter UART flashing mode"),
                AdvancedPinoutEntry(26, "GPIO4", "Touch 0 / ADC2_CH0", "I/O", "General Purpose I/O"),
                AdvancedPinoutEntry(27, "GPIO16", "UART2 RX", "Comm", "Hardware Serial 2 RX"),
                AdvancedPinoutEntry(28, "GPIO17", "UART2 TX", "Comm", "Hardware Serial 2 TX"),
                AdvancedPinoutEntry(29, "GPIO5", "VSPI_SS", "Comm", "SPI Chip Select"),
                AdvancedPinoutEntry(30, "GPIO18", "VSPI_SCK", "Comm", "SPI Serial Clock"),
                AdvancedPinoutEntry(31, "GPIO19", "VSPI_MISO", "Comm", "SPI Data In"),
                AdvancedPinoutEntry(32, "GND", "Ground", "GND", "Power Ground"),
                AdvancedPinoutEntry(33, "GPIO21", "I2C SDA", "Comm", "I2C Default Serial Data"),
                AdvancedPinoutEntry(34, "GPIO3", "UART0 RX (U0RXD)", "Comm", "USB Serial Programming RX"),
                AdvancedPinoutEntry(35, "GPIO1", "UART0 TX (U0TXD)", "Comm", "USB Serial Programming TX"),
                AdvancedPinoutEntry(36, "GPIO22", "I2C SCL", "Comm", "I2C Default Serial Clock"),
                AdvancedPinoutEntry(37, "GPIO23", "VSPI_MOSI", "Comm", "SPI Master Out Data"),
                AdvancedPinoutEntry(38, "GND", "Ground", "GND", "Power Ground")
            ),
            threeDType = Component3DType.ESP32_BOARD,
            absoluteMaxRatings = listOf(
                "Operating Voltage (3.3V Rail)" to "3.0V to 3.6V (NEVER 5V!)",
                "Max Current per GPIO Pin" to "12.0 mA",
                "Total Current drawn by Wi-Fi TX" to "260 mA peak",
                "Operating Temperature" to "-40°C to +85°C"
            ),
            electricalRatings = listOf(
                ElectricalRating("CPU Clock Speed", "f_cpu", "Dual Core Xtensa LX6", "—", "240", "—", "MHz"),
                ElectricalRating("SRAM Memory", "SRAM", "Internal", "—", "520", "—", "KB"),
                ElectricalRating("SPI Flash Memory", "Flash", "Standard WROOM", "—", "4", "16", "MB"),
                ElectricalRating("Wi-Fi Standard", "Wi-Fi", "802.11 b/g/n (2.4 GHz)", "—", "150", "—", "Mbps"),
                ElectricalRating("Bluetooth", "BLE", "v4.2 BR/EDR & BLE", "—", "—", "—", "—")
            ),
            symbolType = "ESP32_38PIN",
            pinoutWarning = "WARNING: GPIOs 6, 7, 8, 9, 10, 11 are connected directly to the internal SPI Flash memory and CANNOT be used for external circuits. GPIOs 34, 35, 36, 39 are INPUT-ONLY (no internal pullups or output driver). All GPIOs are 3.3V logic ONLY — do not apply 5V directly to pins!",
            description = "The ESP32-WROOM-32 is a powerhouse dual-core IoT system-on-chip featuring integrated 2.4GHz Wi-Fi, Bluetooth BLE 4.2, capacitive touch inputs, hardware cryptographic accelerators, and dual 8-bit DACs.",
            typicalCircuits = listOf(
                TypicalCircuit(
                    title = "ESP32 Safe Power Supply & I2C OLED / Sensor Bus",
                    description = "Clean 3.3V supply and connections for 0.96 OLED and BME280 sensor.",
                    componentsNeeded = listOf("ESP32 DevKit", "AMS1117-3.3V Regulator", "100µF & 10µF Electrolytic Caps", "4.7kΩ Pull-up resistors for I2C (GPIO21 & GPIO22)"),
                    schematicDiagramType = "ESP32_CIRCUIT",
                    formulas = listOf("Peak Wi-Fi current requires minimum 500mA @ 3.3V supply capability."),
                    practicalNotes = "If ESP32 crashes or resets when Wi-Fi connects (Brownout detector trigger), add a 470µF low-ESR electrolytic capacitor across 3.3V and GND."
                )
            ),
            commonApplications = listOf("IoT smart home automation and sensors", "Wi-Fi Web Server & MQTT client", "Bluetooth BLE beacon & audio bridge", "Robotics telemetry"),
            equivalents = listOf("ESP32-S3 (Dual Core with USB OTG & Vector instructions)", "ESP32-C3 (Single Core RISC-V)", "ESP8266 NodeMCU (Older single-core alternative)"),
            datasheetNotes = "Can be programmed in Arduino C++, MicroPython, or Espressif ESP-IDF framework."
        )
    
        ))

        // Integrate the project's existing component reference database into SULTAN ADVANCED.
        // These records keep their existing technical specifications and link to exact pinouts when available.
        ComponentsData.allComponents.forEach { c ->
            val p = c.pinoutRefId?.let { ref -> PinoutsData.allPinouts.find { it.id == ref } }
            val inferredPins = p?.pins?.map { pin ->
                AdvancedPinoutEntry(pin.pinNumber, pin.pinName, pin.pinFunction, pin.pinType, pin.description)
            } ?: emptyList()
            val packagePins = when {
                p != null -> p.pinCount
                c.packageType.contains("TO-220", ignoreCase = true) -> 3
                c.packageType.contains("TO-92", ignoreCase = true) -> 3
                c.packageType.contains("DIP-8", ignoreCase = true) -> 8
                c.packageType.contains("DIP-14", ignoreCase = true) -> 14
                c.packageType.contains("DIP-16", ignoreCase = true) -> 16
                c.packageType.contains("DIP-28", ignoreCase = true) -> 28
                else -> 0
            }
            val model = when {
                c.packageType.contains("TO-92", ignoreCase = true) -> Component3DType.TO92_TRANSISTOR
                c.packageType.contains("TO-220", ignoreCase = true) -> Component3DType.TO220_PACKAGE
                c.packageType.contains("DIP-14", ignoreCase = true) -> Component3DType.DIP14_IC
                c.packageType.contains("DIP-16", ignoreCase = true) -> Component3DType.DIP16_IC
                c.packageType.contains("DIP-28", ignoreCase = true) -> Component3DType.DIP28_IC
                c.packageType.contains("DIP-8", ignoreCase = true) -> Component3DType.DIP8_IC
                c.category.contains("Capacitor", ignoreCase = true) -> Component3DType.ELECTROLYTIC_CAP
                c.category.contains("Diode", ignoreCase = true) -> Component3DType.DIODE_DO41
                c.category.contains("MOSFET", ignoreCase = true) -> Component3DType.TO220_PACKAGE
                else -> Component3DType.DIP8_IC
            }
            add(
                AdvancedComponent(
                    id = "sa_ref_${c.id}",
                    partNumber = c.partNumber,
                    name = c.name,
                    category = c.category,
                    family = c.type,
                    packageType = c.packageType,
                    pinCount = packagePins,
                    pins = inferredPins,
                    threeDType = model,
                    absoluteMaxRatings = emptyList(),
                    electricalRatings = c.specs.map { spec ->
                        ElectricalRating(spec.label, "", "Reference", typ = spec.value, unit = "")
                    },
                    symbolType = c.type,
                    pinoutWarning = if (p == null) "This reference record does not contain a dedicated bundled pinout. Verify the exact manufacturer/package datasheet before wiring." else p.notes.ifBlank { null },
                    description = c.description,
                    typicalCircuits = listOf(
                        TypicalCircuit(
                            title = "Typical Application",
                            description = c.typicalApplication,
                            componentsNeeded = listOf(c.partNumber),
                            schematicDiagramType = "REFERENCE_APPLICATION"
                        )
                    ),
                    commonApplications = listOf(c.typicalApplication),
                    equivalents = c.relatedParts,
                    datasheetNotes = "Reference data integrated from the existing SULTAN ELECTRONICS component database. Always verify the exact manufacturer datasheet for production use."
                )
            )
        }

        // Also expose the existing dedicated pinout library inside SULTAN ADVANCED.
        PinoutsData.allPinouts.forEach { p ->
            val model = when {
                p.category.contains("Transistor", true) -> Component3DType.TO92_TRANSISTOR
                p.packageType.contains("TO-220", true) -> Component3DType.TO220_PACKAGE
                p.packageType.contains("DIP-14", true) -> Component3DType.DIP14_IC
                p.packageType.contains("DIP-16", true) -> Component3DType.DIP16_IC
                p.packageType.contains("DIP-28", true) -> Component3DType.DIP28_IC
                p.packageType.contains("DIP-8", true) -> Component3DType.DIP8_IC
                p.category.contains("Microcontroller", true) -> Component3DType.DIP28_IC
                p.category.contains("Displays", true) -> Component3DType.DIP16_IC
                else -> Component3DType.DIP8_IC
            }
            add(
                AdvancedComponent(
                    id = "sa_pin_${p.id}",
                    partNumber = p.name,
                    name = p.subtitle,
                    category = p.category,
                    family = "Pinout Reference",
                    packageType = p.packageType,
                    pinCount = p.pinCount,
                    pins = p.pins.map { pin ->
                        AdvancedPinoutEntry(pin.pinNumber, pin.pinName, pin.pinFunction, pin.pinType, pin.description)
                    },
                    threeDType = model,
                    absoluteMaxRatings = emptyList(),
                    electricalRatings = p.keyFeatures.map { feature ->
                        ElectricalRating("Key Feature", "", "Reference", typ = feature, unit = "")
                    },
                    symbolType = "PINOUT_REFERENCE",
                    pinoutWarning = p.notes.ifBlank { "Verify the exact package and manufacturer datasheet before connecting hardware." },
                    description = p.description,
                    typicalCircuits = emptyList(),
                    commonApplications = p.keyFeatures,
                    equivalents = emptyList(),
                    datasheetNotes = "Bundled pinout reference. Exact package/manufacturer variants may differ."
                )
            )
        }
    }


    // Fan & Motor Connection Guides
    val fanMotorGuides: List<FanMotorGuide> = listOf(
        FanMotorGuide(
            id = "guide_ceiling_fan",
            title = "Ceiling Fan Capacitor Connection Guide",
            subtitle = "Single-Phase Induction Motor (Permanent Split Capacitor PSC)",
            category = "Ceiling Fan",
            motorType = "Single-Phase Permanent Split Capacitor (PSC) Stator with Running & Starting Windings",
            capacitorRating = "2.25 µF to 3.5 µF / 400V - 450V AC (Non-Polarized Motor Run Metallized Film)",
            wires = listOf(
                FanMotorWire("Example color", 0xFFFF1744, "Common / Winding Junction", "Example visual only; actual wire color varies by manufacturer", "Identify by continuity/resistance and the manufacturer diagram"),
                FanMotorWire("Example color", 0xFF212121, "Running Winding", "Example visual only; actual wire color varies by manufacturer", "Identify the running winding from the motor diagram/measurements"),
                FanMotorWire("Example color", 0xFFFFD600, "Auxiliary / Starting Winding", "Example visual only; actual wire color varies by manufacturer", "Identify the auxiliary winding from the motor diagram/measurements")
            ),
            workingPrinciple = "Single-phase AC induction motors cannot self-start because a single pulsating magnetic field produces zero starting torque. A permanent split capacitor (2.5µF - 3.5µF 450VAC) is placed in series with the high-resistance Auxiliary (Starting) winding to shift its phase by approximately 90 electrical degrees. This creates a synthetic rotating magnetic field that drags the aluminum rotor into continuous rotation.",
            stepByStepWiring = listOf(
                "Step 1: Disconnect main AC power supply breaker before opening the canopy.",
                "Step 2: Do NOT identify the wires by color alone. Use the manufacturer wiring diagram and a de-energized resistance/continuity test to identify Common, Running, and Auxiliary/Starting windings.",
                "Step 3: Measure winding resistances with a digital multimeter in Ω mode: Running winding will have lower resistance (e.g. 180 Ω), Starting winding will have higher resistance (e.g. 240 Ω), and between Yellow & Black will equal the sum (420 Ω).",
                "Step 4: Connect the motor-run capacitor only according to the verified manufacturer winding diagram; do not assume a particular wire color or terminal.",
                "Step 5: Complete the capacitor/winding connections exactly as specified by the verified motor diagram; do not substitute mains Neutral for an unidentified winding terminal.",
                "Step 6: Only after the winding terminals are positively identified, connect the supply according to the manufacturer diagram and local electrical code.",
                "Step 7: Do not swap mains conductors as a generic reverse-rotation method. If reversal is supported, follow the manufacturer-approved winding reversal procedure."
            ),
            safetyWarning = "DANGER — MAINS VOLTAGE (220V–240V AC): Isolate and lock out the breaker before opening the motor/fan. Wire colors are NOT universal. Motor capacitors can retain a dangerous charge; use an appropriate rated discharge method and verify zero voltage before handling. Follow the manufacturer wiring diagram and local electrical code.",
            troubleshooting = listOf(
                "Fan runs very slowly or requires manual push to start" to "Weak or open-circuit motor run capacitor. Replace with brand new 2.5µF or 3.15µF 450VAC capacitor.",
                "Fan spins in reverse direction (anti-clockwise)" to "Neutral supply is connected to Starting winding instead of Running winding. Swap the capacitor connection lead.",
                "Fan hums loudly but motor shaft does not rotate" to "Open circuit in starting winding, seized ball bearings (6201/6202), or shorted capacitor.",
                "Motor overheats after 15 minutes of running" to "Installed oversized capacitor (e.g. 4µF instead of 2.5µF) which drives excessive current through auxiliary winding, or dry seized bearings."
            )
        ),

        FanMotorGuide(
            id = "guide_table_fan",
            title = "Table / Stand Fan Multi-Speed Connection Guide",
            subtitle = "3-Speed Tapped Stator Induction Motor Wiring",
            category = "Table Fan",
            motorType = "Single-Phase 4-Pole Shaded-Pole or Tapped Winding Motor",
            capacitorRating = "1.5 µF to 2.5 µF / 450V AC",
            wires = listOf(
                FanMotorWire("Example color", 0xFFFF1744, "High-Speed Tap", "Example visual only; actual wire color varies", "Identify from the manufacturer diagram"),
                FanMotorWire("Example color", 0xFFFFFFFF, "Medium-Speed Tap", "Example visual only; actual wire color varies", "Identify from the manufacturer diagram"),
                FanMotorWire("Example color", 0xFF2979FF, "Low-Speed Tap", "Example visual only; actual wire color varies", "Identify from the manufacturer diagram"),
                FanMotorWire("Example color", 0xFF212121, "Common / Neutral-side terminal", "Example visual only; actual wire color varies", "Identify from the manufacturer diagram"),
                FanMotorWire("Example color", 0xFFFFD600, "Auxiliary / Capacitor lead", "Example visual only; actual wire color varies", "Identify from the manufacturer diagram")
            ),
            workingPrinciple = "Table and pedestal fans vary speed by changing the effective number of stator winding turns in series with the rotor. When Low Speed is selected, extra inductive winding resistance drops the voltage across the main stator poles, increasing motor slip and lowering RPM.",
            stepByStepWiring = listOf(
                "Step 1: Check the 5-wire harness exiting the motor casing.",
                "Step 2: Identify Common, High, Medium, Low, and Auxiliary/Capacitor terminals using the manufacturer diagram; do not rely on wire color.",
                "Step 3: Connect the motor-run capacitor only to the verified capacitor/auxiliary terminals shown on the motor diagram.",
                "Step 4: Connect the supply and speed selector exactly according to the verified motor/switch wiring diagram.",
                "Step 5: Verify all connections with power isolated before energizing; wire colors and terminal order vary by manufacturer."
            ),
            safetyWarning = "DANGER — MAINS VOLTAGE: Disconnect and isolate the supply before opening or rewiring the fan. Wire colors and speed-tap order vary by manufacturer. Use the actual motor wiring diagram and verify connections before energizing.",
            troubleshooting = listOf(
                "Runs on High but not Low or Medium" to "Open internal speed tap winding or oxidized push-button switch contact.",
                "Oscillation mechanism jerks or stops" to "Worn plastic oscillating gearbox gears or loose crank screw.",
                "Shaft stiff to rotate by hand" to "Dry bronze sleeve bearings. Lubricate with ISO VG 68 or synthetic non-detergent motor oil (do not use WD-40!)."
            )
        ),

        FanMotorGuide(
            id = "guide_single_phase_motor",
            title = "Single-Phase Induction Motor (Run vs Start Capacitor)",
            subtitle = "Capacitor-Start / Capacitor-Run Industrial Motors (1/2 HP - 3 HP)",
            category = "Single-Phase Motor",
            motorType = "Capacitor-Start Capacitor-Run (CSCR) Motor with Centrifugal Switch",
            capacitorRating = "Start Cap: 150 - 250 µF (250VAC Electrolytic) | Run Cap: 20 - 40 µF (450VAC Film)",
            wires = listOf(
                FanMotorWire("U1 (example)", 0xFFFF1744, "Main Winding Terminal", "IEC-style terminal label example; verify the actual motor plate", "Follow the manufacturer diagram"),
                FanMotorWire("U2 (example)", 0xFF212121, "Main Winding Terminal", "IEC-style terminal label example; verify the actual motor plate", "Follow the manufacturer diagram"),
                FanMotorWire("Z1 (example)", 0xFFFFD600, "Auxiliary Winding Terminal", "Example label only; verify the actual motor terminal box", "Follow the manufacturer diagram"),
                FanMotorWire("Z2 (example)", 0xFF00E676, "Auxiliary Winding Terminal", "Example label only; verify the actual motor terminal box", "Follow the manufacturer diagram")
            ),
            workingPrinciple = "High-inertia loads (air compressors, water pumps, table saws) require high starting torque. A large value non-polarized electrolytic Start Capacitor (100-300µF) provides 300% starting torque. Once the motor reaches 75% rated RPM, an internal mechanical Centrifugal Switch clicks open to disconnect the Start Capacitor, leaving the heavy-duty oil-filled Run Capacitor (20-40µF) in circuit for high operating efficiency and power factor correction.",
            stepByStepWiring = listOf(
                "Step 1: Inspect the terminal box connections (terminals marked U1, U2, Z1, Z2, V1, V2).",
                "Step 2: Connect Main Winding (U1-U2) across 220V AC Live and Neutral.",
                "Step 3: Connect the heavy Start Capacitor in series with the Centrifugal Switch contacts across U1 and Z1.",
                "Step 4: Connect the permanent Run Capacitor in parallel with the Start Switch circuit across U1 and Z1.",
                "Step 5: Connect Z2 to U2 (Neutral).",
                "Step 6: To reverse rotation direction of the motor shaft, swap the two auxiliary winding connections (swap Z1 and Z2) while leaving U1 and U2 unchanged."
            ),
            safetyWarning = "DANGER — MAINS VOLTAGE: Start/run capacitor values, terminal labels, and reversal methods are motor-specific. Start capacitors can fail violently if left energized. Isolate power, verify the actual motor diagram/nameplate, and use correctly rated capacitors and protection.",
            troubleshooting = listOf(
                "Motor hums and trips circuit breaker on startup" to "Stuck centrifugal switch, defective start capacitor, or locked pump impeller.",
                "Start capacitor bulging or venting electrolyte" to "Centrifugal switch did not disengage after startup, subjecting capacitor to continuous AC voltage.",
                "Motor runs but lacks full power under load" to "Defective Run Capacitor (open circuit)."
            )
        )
    )

    // Practical Circuit Guides
    val practicalGuides: List<PracticalCircuitGuide> = listOf(
        PracticalCircuitGuide(
            id = "prac_led_resistor",
            title = "LED Current Limiting Resistor Wiring",
            category = "Switching",
            subtitle = "Single & Series LED driver circuits for 5V / 12V DC supplies",
            componentsUsed = listOf("5mm LED (Red/Green/Blue)", "1/4W Metal Film Resistor", "DC Power Supply (5V / 12V)"),
            schematicSymbol = "LED_SCHEMATIC",
            workingPrinciple = "Light Emitting Diodes are current-controlled semiconductor devices with a fixed forward voltage drop (Vf). Without a series current-limiting resistor, the diode will draw infinite current until thermal destruction.",
            stepByStepConnections = listOf(
                "1. Identify LED polarity: Anode (+) has longer lead and rounded plastic base; Cathode (-) has shorter lead and flat notch on the epoxy rim.",
                "2. Connect the positive supply (+Vcc) to one terminal of the current limiting resistor R.",
                "3. Connect the second terminal of the resistor to the LED Anode (+).",
                "4. Connect the LED Cathode (-) directly to Power Ground (0V / GND)."
            ),
            keyFormulas = listOf(
                "Resistor Value (Ω)" to "R = (V_supply - V_f) / I_led",
                "Resistor Wattage (W)" to "P = I_led² * R = (V_supply - V_f) * I_led"
            ),
            typicalValues = listOf(
                "Red LED (Vf=2.0V, 20mA) on 5V supply" to "R = (5 - 2.0) / 0.02 = 150 Ω (Use standard 150Ω or 220Ω)",
                "Blue/White LED (Vf=3.2V, 20mA) on 5V" to "R = (5 - 3.2) / 0.02 = 90 Ω (Use standard 100Ω)",
                "Red LED on 12V supply" to "R = (12 - 2.0) / 0.02 = 500 Ω (Use standard 510Ω or 560Ω 1/2W)"
            ),
            safetyNotes = "Never connect an LED directly across a battery or power supply without a resistor or constant-current driver."
        ),

        PracticalCircuitGuide(
            id = "prac_transistor_switch",
            title = "NPN Transistor Switch (Relay / Motor / Solenoid)",
            category = "Switching",
            subtitle = "Controlling 12V loads with 3.3V / 5V microcontroller GPIOs",
            componentsUsed = listOf("BC547 / 2N2222 NPN Transistor", "1kΩ - 4.7kΩ Base Resistor", "1N4007 Flyback Diode", "12V Relay or DC Motor"),
            schematicSymbol = "TRANSISTOR_SWITCH_SCHEMATIC",
            workingPrinciple = "Microcontrollers can only source 10-20mA at 3.3V/5V. An NPN transistor operates in full saturation mode as an electronic switch to control heavy currents from higher voltage rails.",
            stepByStepConnections = listOf(
                "1. Connect Microcontroller GPIO pin to one side of the 1kΩ base resistor.",
                "2. Connect other side of the base resistor to the Transistor BASE pin (Pin 2 on BC547).",
                "3. Connect the Transistor EMITTER pin directly to Common Ground (GND).",
                "4. Connect one terminal of the Load (Relay coil or Motor) to the +12V DC power rail.",
                "5. Connect the other terminal of the Load to the Transistor COLLECTOR pin.",
                "6. CRITICAL: Connect 1N4007 diode directly across the load terminals with Cathode (band) to +12V and Anode to Collector."
            ),
            keyFormulas = listOf(
                "Base Current" to "I_b = (V_gpio - 0.7V) / R_base",
                "Forced Saturation Condition" to "I_b >= I_load / 10"
            ),
            typicalValues = listOf(
                "5V Arduino driving 12V 80mA Relay" to "R_base = (5 - 0.7) / (0.08 / 10) = 4.3V / 0.008A = 537 Ω (Use 470Ω or 1kΩ)"
            ),
            safetyNotes = "Inductive loads create massive voltage spikes when switched off. Omitting the 1N4007 diode will destroy the transistor on the first cycle."
        ),

        PracticalCircuitGuide(
            id = "prac_mosfet_switch",
            title = "N-Channel MOSFET High-Power Switch",
            category = "Switching",
            subtitle = "Switching 10A - 30A DC loads (Heater, High-Power LED, Brushless Motors)",
            componentsUsed = listOf("IRLZ44N / IRFZ44N N-Ch MOSFET", "100Ω Gate Series Resistor", "10kΩ Gate-to-Source Pull-Down", "Load"),
            schematicSymbol = "MOSFET_SWITCH_SCHEMATIC",
            workingPrinciple = "MOSFETs are voltage-controlled devices with near-zero static gate current and minimal on-state resistance (RDS(on) < 20mΩ), producing far less heat than bipolar transistors.",
            stepByStepConnections = listOf(
                "1. Connect MCU GPIO pin through a 100Ω resistor to the MOSFET GATE pin.",
                "2. Connect a 10kΩ resistor directly between the GATE and SOURCE pins (pull-down).",
                "3. Connect the SOURCE pin to the negative power ground (GND).",
                "4. Connect the negative lead of the high-power load to the DRAIN pin (middle pin / tab).",
                "5. Connect the positive lead of the load to +12V / +24V supply."
            ),
            keyFormulas = listOf(
                "Conduction Heat Loss" to "P_loss = I_load² * RDS(on)",
                "Example 15A load on 18mΩ MOSFET" to "P = 15² * 0.018 = 4.05 Watts (Mount on small heatsink)"
            ),
            typicalValues = listOf(
                "Gate Series Resistor" to "47 Ω - 220 Ω (Damps LC gate ringing)",
                "Gate Pull-down Resistor" to "10 kΩ (Prevents floating gate turning ON during boot)"
            ),
            safetyNotes = "Never leave the Gate pin floating. A floating gate will accumulate static charge and cause partial conduction, burning out the MOSFET."
        ),

        PracticalCircuitGuide(
            id = "prac_bridge_rectifier",
            title = "Mains AC Step-Down & Filtered DC Power Supply",
            category = "Power Supply",
            subtitle = "230V AC to Regulated +5V/+12V DC Bench Supply Wiring",
            componentsUsed = listOf("Step-down Transformer (230V to 12V AC)", "4x 1N4007 / KBPC Bridge Rectifier", "1000µF 25V Electrolytic Cap", "100nF Ceramic Cap", "LM7805 / LM7812 Regulator"),
            schematicSymbol = "POWER_SUPPLY_SCHEMATIC",
            workingPrinciple = "Transformer steps down AC voltage, bridge rectifier converts bidirectional AC into pulsating DC, reservoir capacitor smooths voltage ripple, and 3-terminal regulator maintains precise output voltage under fluctuating loads.",
            stepByStepConnections = listOf(
                "1. Connect transformer primary leads to 230V AC mains through a 0.5A fuse and DPST power switch.",
                "2. Connect transformer secondary 12VAC leads to the two AC input terminals (~) of the bridge rectifier.",
                "3. Connect bridge rectifier positive (+) terminal to the positive (+) lead of 1000µF capacitor and Pin 1 (IN) of LM7805.",
                "4. Connect bridge rectifier negative (-) terminal to capacitor negative (-) lead, regulator Pin 2 (GND), and circuit ground.",
                "5. Connect regulator Pin 3 (OUT) to a 10µF electrolytic capacitor and 100nF ceramic capacitor for clean 5.0V output."
            ),
            keyFormulas = listOf(
                "DC Peak Voltage" to "V_dc_peak = (V_ac_rms * 1.414) - 1.4V",
                "Ripple Voltage" to "V_ripple = I_load / (2 * f_line * C_filter)"
            ),
            typicalValues = listOf(
                "12V AC secondary" to "V_peak = 12 * 1.414 - 1.4 = 15.57V DC before regulator",
                "Filter Capacitor Rule of Thumb" to "1000 µF to 2200 µF per 1 Ampere of load current"
            ),
            safetyNotes = "All primary 230V AC connections must be double-insulated with heat shrink tubing and enclosed in a grounded metal or fire-retardant plastic chassis."
        )
    )

    // Schematic Engineering Symbols Library
    val schematicSymbols: List<SchematicSymbol> = listOf(
        SchematicSymbol("sym_resistor_ansi", "Resistor (Fixed)", "ANSI / IEEE (Zig-zag) & IEC (Rectangle)", "Passives", "Impedes electric current flow and drops voltage according to Ohm's Law (V = I * R).", listOf("Terminal 1", "Terminal 2"), "Current limiting, voltage division, pull-up/pull-down, biasing"),
        SchematicSymbol("sym_potentiometer", "Potentiometer (Variable Resistor)", "ANSI / IEC", "Passives", "3-terminal variable resistor with adjustable wiper voltage ratio.", listOf("Terminal 1", "Wiper (Adjustable)", "Terminal 2"), "Volume controls, voltage calibration, dimmer controls"),
        SchematicSymbol("sym_capacitor_nonpolar", "Capacitor (Non-Polarized Ceramic/Film)", "ANSI / IEC (Dual parallel plates)", "Passives", "Stores electric energy in an electrostatic field. Blocks DC while passing AC signals.", listOf("Plate 1", "Plate 2"), "AC coupling, DC blocking, RF tuning, noise filtering"),
        SchematicSymbol("sym_capacitor_polar", "Electrolytic Capacitor (Polarized)", "ANSI (Curved plate = Negative) / IEC", "Passives", "High capacitance polarized electrolytic capacitor. Must be connected with correct polarity (+ to positive, - to ground).", listOf("Anode (+)", "Cathode (-)"), "Power supply smoothing, audio coupling, energy storage"),
        SchematicSymbol("sym_inductor", "Inductor / Choke Coil", "ANSI / IEC (Multi-loop coil)", "Passives", "Stores energy in a magnetic field. Resists changes in electric current (V = L * di/dt).", listOf("Lead 1", "Lead 2"), "Switching regulators, LC filters, EMI suppression, transformers"),
        SchematicSymbol("sym_diode_rectifier", "Rectifier Diode", "ANSI / IEC (Triangle pointing to bar)", "Semiconductors", "Allows current flow in only one direction (Anode to Cathode) with ~0.7V forward drop.", listOf("Anode (A)", "Cathode (K)"), "AC rectification, reverse voltage protection, flyback suppression"),
        SchematicSymbol("sym_zener", "Zener Diode", "ANSI / IEC (Bar with bent tabs)", "Semiconductors", "Designed to conduct in reverse breakdown mode at a precise avalanche voltage (Vz).", listOf("Anode (A)", "Cathode (K)"), "Voltage reference, voltage clamp, overvoltage protection"),
        SchematicSymbol("sym_led", "Light Emitting Diode (LED)", "ANSI / IEC (Diode with two outward arrows)", "Optoelectronics", "Emits light when forward-biased via electroluminescence.", listOf("Anode (+)", "Cathode (-)"), "Status indicators, illumination, optical displays"),
        SchematicSymbol("sym_photodiode", "Photodiode", "ANSI / IEC (Diode with two inward arrows)", "Optoelectronics", "Generates reverse photocurrent proportional to incident light intensity.", listOf("Anode", "Cathode"), "Light sensors, optical fiber receivers, infrared remote detectors"),
        SchematicSymbol("sym_bjt_npn", "NPN Bipolar Junction Transistor (BJT)", "ANSI / IEC (Emitter arrow pointing outward)", "Semiconductors", "Current-controlled amplifier / switch. Base current controls collector current.", listOf("Base (B)", "Collector (C)", "Emitter (E)"), "Low-side switching, audio preamplifiers, logic gates"),
        SchematicSymbol("sym_bjt_pnp", "PNP Bipolar Junction Transistor (BJT)", "ANSI / IEC (Emitter arrow pointing inward)", "Semiconductors", "Current-controlled amplifier / switch with inverted polarities.", listOf("Base (B)", "Collector (C)", "Emitter (E)"), "High-side switching, push-pull complementary amplifier"),
        SchematicSymbol("sym_mosfet_nch", "N-Channel Enhancement MOSFET", "ANSI / IEC (Substrate arrow pointing inward)", "Semiconductors", "Voltage-controlled power switch with ultra-low on-resistance.", listOf("Gate (G)", "Drain (D)", "Source (S)", "Body Diode"), "Motor speed control, DC-DC buck converters, inverter bridges"),
        SchematicSymbol("sym_mosfet_pch", "P-Channel Enhancement MOSFET", "ANSI / IEC (Substrate arrow pointing outward)", "Semiconductors", "High-side voltage-controlled power switch.", listOf("Gate (G)", "Drain (D)", "Source (S)", "Body Diode"), "Reverse polarity protection, high-side power distribution"),
        SchematicSymbol("sym_opamp", "Operational Amplifier (Op-Amp)", "ANSI / IEC (Triangle with +/- inputs and single output)", "Analog ICs", "High-gain differential amplifier with infinite input impedance and zero output impedance.", listOf("Non-inverting (+)", "Inverting (-)", "Output", "V+ Supply", "V- Supply"), "Signal amplification, active filters, comparators, buffers"),
        SchematicSymbol("sym_optocoupler", "Optocoupler / Photocoupler", "ANSI / IEC (LED facing Phototransistor)", "Optoelectronics", "Transfers electrical signals using light waves to provide complete galvanic isolation.", listOf("Anode (1)", "Cathode (2)", "Emitter (3)", "Collector (4)"), "Mains isolation, SMPS feedback, ground-loop elimination"),
        SchematicSymbol("sym_relay_spdt", "Electromechanical Relay (SPDT)", "ANSI / IEC (Coil next to switch contacts)", "Electromechanical", "Electromagnetically actuated mechanical switch.", listOf("Coil 1", "Coil 2", "Common (COM)", "Normally Open (NO)", "Normally Closed (NC)"), "Mains AC switching, automotive wiring, isolation"),
        SchematicSymbol("sym_transformer", "Transformer (Iron Core)", "ANSI / IEC (Dual coils separated by parallel bars)", "Magnetic", "Steps AC voltage up or down via mutual electromagnetic induction.", listOf("Primary 1", "Primary 2", "Secondary 1", "Secondary 2", "Center Tap"), "Power supply step down, audio impedance matching, isolation"),
        SchematicSymbol("sym_crystal", "Quartz Crystal Resonator", "ANSI / IEC (Quartz rectangle between electrode plates)", "Timing", "Piezoelectric crystal providing precise resonant frequency clock reference.", listOf("Terminal 1", "Terminal 2"), "Microcontroller clock, RF synthesizer, RTC real time clock"),
        SchematicSymbol("sym_fuse", "Fuse", "ANSI / IEC (Rectangle with through line or S-curve)", "Protection", "Overcurrent protective device that melts when current exceeds rating.", listOf("Terminal 1", "Terminal 2"), "Short-circuit protection, fire prevention")
    )
}
