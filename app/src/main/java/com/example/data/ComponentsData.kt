package com.example.data

import com.example.model.ComponentItem
import com.example.model.ComponentSpec

object ComponentsData {
    val allComponents: List<ComponentItem> = listOf(
        // Transistors & MOSFETs
        ComponentItem(
            id = "comp_2n2222",
            partNumber = "2N2222A",
            name = "NPN General Purpose Transistor",
            category = "Transistors",
            type = "BJT NPN",
            packageType = "TO-92 / TO-18 / SOT-23",
            description = "High-speed NPN transistor capable of switching currents up to 800mA and frequencies up to 300 MHz.",
            specs = listOf(
                ComponentSpec("Max Collector Voltage (Vceo)", "40V"),
                ComponentSpec("Continuous Collector Current (Ic)", "800mA"),
                ComponentSpec("DC Current Gain (hFE)", "100 - 300"),
                ComponentSpec("Transition Frequency (fT)", "300 MHz"),
                ComponentSpec("Power Dissipation (Pd)", "625 mW")
            ),
            typicalApplication = "Low-side relay driver, LED switching, small signal preamplifier, Arduino digital I/O buffer.",
            pinoutRefId = "pinout_2n2222",
            relatedParts = listOf("2N3904", "BC547", "PN2222", "2N2907 (PNP complement)")
        ),
        ComponentItem(
            id = "comp_bc547",
            partNumber = "BC547",
            name = "Audio & Signal NPN Transistor",
            category = "Transistors",
            type = "BJT NPN Low Noise",
            packageType = "TO-92",
            description = "Ubiquitous general purpose audio pre-amplifier and switching transistor.",
            specs = listOf(
                ComponentSpec("Max Vceo", "45V"),
                ComponentSpec("Collector Current (Ic)", "100mA"),
                ComponentSpec("Current Gain (hFE)", "110 - 800"),
                ComponentSpec("Noise Figure (NF)", "2 dB typ"),
                ComponentSpec("Power Dissipation", "500 mW")
            ),
            typicalApplication = "Microphone amplifiers, audio mixer input stages, touch sensor circuits.",
            pinoutRefId = "pinout_bc547",
            relatedParts = listOf("BC548", "BC549 (Ultra low noise)", "BC557 (PNP complement)")
        ),
        ComponentItem(
            id = "comp_bc557",
            partNumber = "BC557",
            name = "Audio & Signal PNP Transistor",
            category = "Transistors",
            type = "BJT PNP",
            packageType = "TO-92",
            description = "Standard PNP complementary transistor to BC547, widely used in push-pull audio amplifiers.",
            specs = listOf(
                ComponentSpec("Max Vceo", "-45V"),
                ComponentSpec("Collector Current (Ic)", "-100mA"),
                ComponentSpec("Current Gain (hFE)", "125 - 800"),
                ComponentSpec("Power Dissipation", "500 mW")
            ),
            typicalApplication = "High-side switching, push-pull output stages, inverted logic buffering.",
            relatedParts = listOf("BC558", "BC547 (NPN complement)")
        ),
        ComponentItem(
            id = "comp_tip31c",
            partNumber = "TIP31C",
            name = "Medium Power NPN Transistor",
            category = "Transistors",
            type = "BJT NPN Power",
            packageType = "TO-220",
            description = "Medium power bipolar junction transistor suitable for power amplification and linear switching up to 3A.",
            specs = listOf(
                ComponentSpec("Max Vceo", "100V"),
                ComponentSpec("Continuous Ic", "3A (Peak 5A)"),
                ComponentSpec("Base Current Ib", "1A"),
                ComponentSpec("Power Dissipation", "40W (with heatsink)")
            ),
            typicalApplication = "Audio power amplifiers, solenoid/motor drivers, series-pass voltage regulator pass elements.",
            relatedParts = listOf("TIP32C (PNP complement)", "TIP41C", "2N3055")
        ),
        ComponentItem(
            id = "comp_2n3055",
            partNumber = "2N3055",
            name = "Classic 15A Power NPN Transistor",
            category = "Transistors",
            type = "BJT NPN High Power",
            packageType = "TO-3 Metal Can",
            description = "The classic legendary silicon NPN power transistor created by RCA. Revered for high power linear DC power supplies and rugged audio amplifiers.",
            specs = listOf(
                ComponentSpec("Collector-Emitter Voltage", "60V (100V for 2N3055H)"),
                ComponentSpec("Collector Current (Ic)", "15A continuous"),
                ComponentSpec("Total Power Dissipation", "115W"),
                ComponentSpec("hFE @ 4A", "20 - 70")
            ),
            typicalApplication = "Linear lab bench power supplies, high-power audio amplifiers, power inverters.",
            relatedParts = listOf("MJ2955 (PNP complement)", "TIP3055 (TO-247 equivalent)")
        ),
        ComponentItem(
            id = "comp_irfz44n",
            partNumber = "IRFZ44N",
            name = "55V 49A N-Channel Power MOSFET",
            category = "MOSFETs",
            type = "N-Channel HEXFET",
            packageType = "TO-220AB",
            description = "Standard power MOSFET featuring extremely low RDS(on) and fast switching performance.",
            specs = listOf(
                ComponentSpec("Drain-Source Voltage (Vds)", "55V"),
                ComponentSpec("Continuous Drain Current (Id)", "49A @ 25°C"),
                ComponentSpec("On-Resistance RDS(on)", "17.5 mΩ max"),
                ComponentSpec("Gate-Source Threshold (Vgs(th))", "2.0V - 4.0V"),
                ComponentSpec("Gate Drive Voltage", "10V recommended")
            ),
            typicalApplication = "H-Bridge DC motor speed controllers, 12V-to-220V power inverters, high-power LED strips PWM.",
            pinoutRefId = "pinout_irfz44n",
            relatedParts = listOf("IRLZ44N (Logic Level 5V)", "IRF3205 (110A)", "IRF540N (100V)")
        ),
        ComponentItem(
            id = "comp_irlz44n",
            partNumber = "IRLZ44N",
            name = "Logic-Level 55V 47A N-Channel MOSFET",
            category = "MOSFETs",
            type = "N-Channel Logic Level",
            packageType = "TO-220AB",
            description = "Logic-level gate drive N-channel MOSFET designed to fully saturate directly from 3.3V / 5V microcontroller pins without an external gate driver IC.",
            specs = listOf(
                ComponentSpec("Drain-Source Voltage", "55V"),
                ComponentSpec("Drain Current", "47A"),
                ComponentSpec("RDS(on) @ 5V Vgs", "22 mΩ"),
                ComponentSpec("RDS(on) @ 4V Vgs", "25 mΩ"),
                ComponentSpec("Gate Threshold Vgs(th)", "1.0V - 2.0V")
            ),
            typicalApplication = "Direct 3.3V/5V microcontroller power switching (ESP32, STM32, Arduino).",
            relatedParts = listOf("IRFZ44N", "FQP30N06L")
        ),
        // Diodes & Rectifiers
        ComponentItem(
            id = "comp_1n4007",
            partNumber = "1N4007",
            name = "1000V 1A Silicon Rectifier Diode",
            category = "Diodes",
            type = "Standard Silicon PN Junction",
            packageType = "DO-41 Axial",
            description = "The standard 1A mains frequency rectifier diode with 1000V peak reverse voltage rating.",
            specs = listOf(
                ComponentSpec("Peak Repetitive Reverse Voltage (Vrrm)", "1000V"),
                ComponentSpec("Average Forward Current (Io)", "1.0A"),
                ComponentSpec("Forward Voltage Drop (Vf)", "1.1V @ 1A"),
                ComponentSpec("Peak Surge Current (Ifsm)", "30A (8.3ms half sine)"),
                ComponentSpec("Reverse Leakage", "5 µA")
            ),
            typicalApplication = "Mains AC-DC bridge rectifiers, reverse battery polarity protection, relay coil flyback suppression.",
            relatedParts = listOf("1N4001 (50V)", "1N4004 (400V)", "1N5408 (3A 1000V)")
        ),
        ComponentItem(
            id = "comp_1n5819",
            partNumber = "1N5819",
            name = "40V 1A Schottky Barrier Diode",
            category = "Diodes",
            type = "Schottky Barrier Diode",
            packageType = "DO-41 / SOD-123",
            description = "Ultra-fast low forward-drop Schottky diode designed for high efficiency switching power supplies and high-frequency freewheeling.",
            specs = listOf(
                ComponentSpec("Max Reverse Voltage (Vr)", "40V"),
                ComponentSpec("Average Forward Current", "1.0A"),
                ComponentSpec("Forward Voltage Drop (Vf)", "0.35V @ 0.1A / 0.60V @ 1A"),
                ComponentSpec("Reverse Recovery Time", "< 10 ns")
            ),
            typicalApplication = "Buck/Boost converters, solar panel bypass, low-voltage reverse polarity protection.",
            relatedParts = listOf("1N5822 (3A 40V)", "SS14 (SMD)", "BAT43")
        ),
        ComponentItem(
            id = "comp_1n4148",
            partNumber = "1N4148",
            name = "High-Speed Small Signal Diode",
            category = "Diodes",
            type = "High-Speed Switching",
            packageType = "DO-35 Glass Axial / LL-34",
            description = "Extremely fast switching diode with 4ns reverse recovery time, ideal for logic gating and wave-shaping.",
            specs = listOf(
                ComponentSpec("Peak Reverse Voltage", "100V"),
                ComponentSpec("Continuous Forward Current", "200mA (Peak 450mA)"),
                ComponentSpec("Reverse Recovery Time (trr)", "4 ns"),
                ComponentSpec("Junction Capacitance", "4 pF")
            ),
            typicalApplication = "High-speed digital logic, diode clipping/clamping, frequency mixers, signal detection.",
            relatedParts = listOf("1N914", "BAV99 (Dual SMD)")
        ),
        ComponentItem(
            id = "comp_1n4733a",
            partNumber = "1N4733A",
            name = "5.1V 1W Zener Diode",
            category = "Zener Diodes",
            type = "Zener Voltage Reference",
            packageType = "DO-41 Glass",
            description = "1 Watt 5.1V Zener diode used for voltage regulation, precision clamping, and analog reference generation.",
            specs = listOf(
                ComponentSpec("Nominal Zener Voltage (Vz)", "5.1V ±5%"),
                ComponentSpec("Power Dissipation (Pd)", "1.0 Watt"),
                ComponentSpec("Zener Test Current (Izt)", "49 mA"),
                ComponentSpec("Dynamic Impedance (Zzt)", "7 Ω")
            ),
            typicalApplication = "5V voltage clamps, ADC overvoltage protection, simple shunt voltage regulators.",
            relatedParts = listOf("1N4728A (3.3V)", "1N4735A (6.2V)", "1N4742A (12V)")
        ),
        // Voltage Regulators
        ComponentItem(
            id = "comp_lm7805",
            partNumber = "LM7805",
            name = "+5V 1.5A Linear Voltage Regulator",
            category = "Voltage Regulators",
            type = "Fixed Positive Linear Regulator",
            packageType = "TO-220 / DPAK",
            description = "Industry benchmark 3-terminal +5V voltage regulator with internal current-limit and thermal-shutdown.",
            specs = listOf(
                ComponentSpec("Output Voltage", "+5.0V ±4%"),
                ComponentSpec("Max Output Current", "1.5A (with heatsink)"),
                ComponentSpec("Input Voltage Range", "7.0V - 25V (Max 35V)"),
                ComponentSpec("Dropout Voltage", "2.0V typ"),
                ComponentSpec("Quiescent Current", "5 mA")
            ),
            typicalApplication = "Microcontroller power supply (Arduino, PIC), 5V digital logic power rail.",
            pinoutRefId = "pinout_lm7805",
            relatedParts = listOf("LM7812 (+12V)", "LM7809 (+9V)", "LM7905 (-5V negative regulator)")
        ),
        ComponentItem(
            id = "comp_lm317",
            partNumber = "LM317T",
            name = "1.25V - 37V 1.5A Adjustable Regulator",
            category = "Voltage Regulators",
            type = "Adjustable Linear Regulator",
            packageType = "TO-220",
            description = "Exceptional adjustable voltage regulator capable of providing 1.25V to 37V with over 1.5A current.",
            specs = listOf(
                ComponentSpec("Output Voltage Range", "1.25V to 37V"),
                ComponentSpec("Output Current", "1.5A"),
                ComponentSpec("Reference Voltage (Vref)", "1.25V ±2%"),
                ComponentSpec("Line Regulation", "0.01%/V typ"),
                ComponentSpec("Ripple Rejection", "80 dB")
            ),
            typicalApplication = "Variable lab power supplies, constant current battery chargers, audio preamp regulators.",
            pinoutRefId = "pinout_lm317",
            relatedParts = listOf("LM337 (Negative adjustable regulator)", "LM350 (3A)", "LM338 (5A)")
        ),
        ComponentItem(
            id = "comp_ams1117",
            partNumber = "AMS1117-3.3",
            name = "3.3V 800mA Low Dropout (LDO) Regulator",
            category = "Voltage Regulators",
            type = "Low Dropout Linear Regulator",
            packageType = "SOT-223",
            description = "Popular low-dropout regulator found on ESP8266, ESP32, and Arduino boards to step 5V down to 3.3V.",
            specs = listOf(
                ComponentSpec("Output Voltage", "3.3V ±1.5%"),
                ComponentSpec("Output Current", "800mA to 1000mA"),
                ComponentSpec("Dropout Voltage", "1.1V @ 800mA"),
                ComponentSpec("Max Input Voltage", "15V")
            ),
            typicalApplication = "Powering 3.3V sensors, microcontrollers, and wireless modules from 5V USB.",
            relatedParts = listOf("AMS1117-5.0", "AMS1117-ADJ", "AP2112K (Ultra low dropout)")
        ),
        // Op-Amps & Timers
        ComponentItem(
            id = "comp_ne555",
            partNumber = "NE555P",
            name = "Precision Timing IC",
            category = "Timer ICs",
            type = "Timer / Oscillator",
            packageType = "DIP-8 / SOIC-8",
            description = "The most widely manufactured integrated circuit in human history. Operates in Astable, Monostable, or Bistable modes.",
            specs = listOf(
                ComponentSpec("Supply Voltage (Vcc)", "4.5V to 16V (18V max)"),
                ComponentSpec("Max Output Current", "200mA (source & sink)"),
                ComponentSpec("Max Operating Frequency", "500 kHz (Bipolar) / 2MHz (CMOS LMC555)"),
                ComponentSpec("Timing Interval", "Microseconds to Hours")
            ),
            typicalApplication = "Pulse-width modulation (PWM), tone generators, LED flashers, delay timers, debouncing circuits.",
            pinoutRefId = "pinout_ne555",
            relatedParts = listOf("NE556 (Dual 555 in DIP-14)", "LMC555 (Low power CMOS 1.5V)", "NE558 (Quad 555)")
        ),
        ComponentItem(
            id = "comp_lm358",
            partNumber = "LM358N",
            name = "Dual Low Power Operational Amplifier",
            category = "Op-Amps",
            type = "Dual Op-Amp",
            packageType = "DIP-8 / SOP-8",
            description = "Single-supply dual op-amp capable of sensing voltages down to ground rail.",
            specs = listOf(
                ComponentSpec("Supply Voltage", "3V to 32V (Single) / ±1.5V to ±16V (Dual)"),
                ComponentSpec("Supply Current", "0.7 mA typ"),
                ComponentSpec("Gain Bandwidth Product", "1.0 MHz"),
                ComponentSpec("Input Offset Voltage", "2 mV typ"),
                ComponentSpec("Slew Rate", "0.5 V/µs")
            ),
            typicalApplication = "Active filters, sensor signal conditioners, voltage followers, summing amplifiers.",
            pinoutRefId = "pinout_lm358",
            relatedParts = listOf("LM324 (Quad version)", "TL072 (Low noise JFET)", "NE5532 (Audiophile op-amp)")
        ),
        ComponentItem(
            id = "comp_tl072",
            partNumber = "TL072CP",
            name = "Dual Low-Noise JFET Input Op-Amp",
            category = "Op-Amps",
            type = "Dual JFET Op-Amp",
            packageType = "DIP-8",
            description = "High input impedance JFET-input operational amplifier known for high slew rate and low harmonic distortion in audio circuits.",
            specs = listOf(
                ComponentSpec("Input Impedance", "10^12 Ω (1 TΩ)"),
                ComponentSpec("Slew Rate", "13 V/µs"),
                ComponentSpec("Gain Bandwidth", "3 MHz"),
                ComponentSpec("Total Harmonic Distortion (THD)", "0.003%")
            ),
            typicalApplication = "Guitar effect pedals, audio preamplifiers, high-impedance buffer amplifiers.",
            relatedParts = listOf("TL074 (Quad)", "TL071 (Single)", "NE5532", "OPA2134")
        ),
        // Logic ICs
        ComponentItem(
            id = "comp_74hc595",
            partNumber = "74HC595",
            name = "8-Bit Serial-In Parallel-Out Shift Register",
            category = "Logic ICs",
            type = "CMOS Shift Register with Latch",
            packageType = "DIP-16 / SOIC-16",
            description = "8-bit shift register with storage register and 3-state outputs. Turns 3 microcontroller pins into 8, 16, 24+ digital outputs by daisy-chaining.",
            specs = listOf(
                ComponentSpec("Operating Voltage", "2.0V to 6.0V"),
                ComponentSpec("Clock Frequency", "100 MHz @ 5V"),
                ComponentSpec("Output Drive Current", "35 mA max per pin / 70 mA Vcc"),
                ComponentSpec("Inputs", "Data (DS), Clock (SHCP), Latch (STCP)")
            ),
            typicalApplication = "LED matrix drivers, 7-segment display arrays, relay board expansion for Arduino/ESP32.",
            relatedParts = listOf("74HC165 (Parallel-In Serial-Out)", "CD4094", "MAX7219")
        ),
        ComponentItem(
            id = "comp_cd4017",
            partNumber = "CD4017BE",
            name = "Decade Counter with 10 Decoded Outputs",
            category = "Logic ICs",
            type = "CMOS Decade Counter",
            packageType = "DIP-16",
            description = "Johnson decade counter with 10 decoded active-high outputs that sequence consecutively on each clock pulse.",
            specs = listOf(
                ComponentSpec("Supply Voltage Range", "3V to 18V"),
                ComponentSpec("Clock Speed", "Up to 5.5 MHz @ 10V"),
                ComponentSpec("Outputs", "10 Decoded Outputs (Q0 - Q9) + Carry Out")
            ),
            typicalApplication = "Knight Rider running LED chasers, electronic dice, frequency division by 10, sequential timers.",
            relatedParts = listOf("CD4026 (Counter with 7-segment output)", "74HC4017")
        )
    )
}
