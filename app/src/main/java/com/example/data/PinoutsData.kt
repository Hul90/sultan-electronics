package com.example.data

import com.example.model.PinDetail
import com.example.model.PinoutItem

object PinoutsData {
    val allPinouts: List<PinoutItem> = listOf(
        PinoutItem(
            id = "pinout_ne555",
            name = "NE555 Timer IC",
            subtitle = "Precision Timing & Oscillator IC",
            category = "Timer ICs",
            packageType = "DIP-8 / SOP-8",
            pinCount = 8,
            pins = listOf(
                PinDetail(1, "GND", "Ground (0V)", "GND", "Common ground connection"),
                PinDetail(2, "TRIG", "Trigger input", "Control", "Active low trigger (< 1/3 Vcc) starts timing cycle"),
                PinDetail(3, "OUT", "Output", "I/O", "High-current output capable of sourcing/sinking up to 200mA"),
                PinDetail(4, "RESET", "Reset input", "Control", "Active low reset (< 0.7V) interrupts timing and grounds output"),
                PinDetail(5, "CTRL", "Control Voltage", "Analog", "Access to 2/3 Vcc internal voltage divider (0.01µF to GND if unused)"),
                PinDetail(6, "THRESH", "Threshold input", "Control", "Input to upper comparator; ends timing when > 2/3 Vcc"),
                PinDetail(7, "DISCH", "Discharge", "I/O", "Open-collector transistor to discharge timing capacitor"),
                PinDetail(8, "VCC", "Supply Voltage", "Power", "Positive supply (+4.5V to +15V DC)")
            ),
            description = "The industry-standard 555 precision timing circuit. Capable of generating accurate time delays or continuous oscillation in astable, monostable, or bistable modes.",
            keyFeatures = listOf(
                "Supply Voltage: 4.5V to 15V",
                "Max Output Current: 200mA",
                "Timing: Microseconds to Hours",
                "High Temperature Stability: 0.005%/°C"
            ),
            notes = "Always connect a 10nF (0.01µF) decoupling ceramic capacitor from Pin 5 (CTRL) to GND for stable oscillation and noise immunity."
        ),
        PinoutItem(
            id = "pinout_lm358",
            name = "LM358 Dual Op-Amp",
            subtitle = "Low-Power Dual Operational Amplifier",
            category = "Op-Amps",
            packageType = "DIP-8 / SOP-8",
            pinCount = 8,
            pins = listOf(
                PinDetail(1, "OUT1", "Output Amp 1", "I/O", "Output of operational amplifier 1"),
                PinDetail(2, "IN1-", "Inverting Input 1", "Analog", "Negative input of op-amp 1"),
                PinDetail(3, "IN1+", "Non-Inverting Input 1", "Analog", "Positive input of op-amp 1"),
                PinDetail(4, "GND / V-", "Ground / Negative Rail", "GND", "0V for single supply or -Vcc for dual supply"),
                PinDetail(5, "IN2+", "Non-Inverting Input 2", "Analog", "Positive input of op-amp 2"),
                PinDetail(6, "IN2-", "Inverting Input 2", "Analog", "Negative input of op-amp 2"),
                PinDetail(7, "OUT2", "Output Amp 2", "I/O", "Output of operational amplifier 2"),
                PinDetail(8, "VCC / V+", "Positive Supply Rail", "Power", "Positive supply (+3V to +32V single or ±1.5V to ±16V split)")
            ),
            description = "Dual independent high-gain frequency-compensated operational amplifiers designed to operate from a single power supply over a wide range of voltages.",
            keyFeatures = listOf(
                "Single Supply: 3.0V to 32V",
                "Low Supply Current: 500µA",
                "Input Common-Mode Range Includes Ground",
                "Unity Gain Bandwidth: 1.0 MHz"
            )
        ),
        PinoutItem(
            id = "pinout_lm324",
            name = "LM324 Quad Op-Amp",
            subtitle = "Quad Low-Power Operational Amplifier",
            category = "Op-Amps",
            packageType = "DIP-14 / SOIC-14",
            pinCount = 14,
            pins = listOf(
                PinDetail(1, "OUT1", "Output 1", "I/O", "Output of op-amp 1"),
                PinDetail(2, "IN1-", "Inverting Input 1", "Analog", "Negative input of op-amp 1"),
                PinDetail(3, "IN1+", "Non-Inverting Input 1", "Analog", "Positive input of op-amp 1"),
                PinDetail(4, "VCC", "Positive Supply", "Power", "+3V to +32V single supply"),
                PinDetail(5, "IN2+", "Non-Inverting Input 2", "Analog", "Positive input of op-amp 2"),
                PinDetail(6, "IN2-", "Inverting Input 2", "Analog", "Negative input of op-amp 2"),
                PinDetail(7, "OUT2", "Output 2", "I/O", "Output of op-amp 2"),
                PinDetail(8, "OUT3", "Output 3", "I/O", "Output of op-amp 3"),
                PinDetail(9, "IN3-", "Inverting Input 3", "Analog", "Negative input of op-amp 3"),
                PinDetail(10, "IN3+", "Non-Inverting Input 3", "Analog", "Positive input of op-amp 3"),
                PinDetail(11, "GND", "Ground / V-", "GND", "0V or negative supply rail"),
                PinDetail(12, "IN4+", "Non-Inverting Input 4", "Analog", "Positive input of op-amp 4"),
                PinDetail(13, "IN4-", "Inverting Input 4", "Analog", "Negative input of op-amp 4"),
                PinDetail(14, "OUT4", "Output 4", "I/O", "Output of op-amp 4")
            ),
            description = "Four independent high-gain frequency-compensated operational amplifiers on a single chip, ideal for active filters, comparator arrays, and sensor interfaces.",
            keyFeatures = listOf(
                "Quad op-amps in single package",
                "Wide Supply: 3V to 32V",
                "Directly compatible with TTL logic"
            )
        ),
        PinoutItem(
            id = "pinout_lm7805",
            name = "LM7805 Linear Regulator",
            subtitle = "+5V Positive Voltage Regulator (1.5A)",
            category = "Voltage Regulators",
            packageType = "TO-220",
            pinCount = 3,
            pins = listOf(
                PinDetail(1, "INPUT", "Unregulated Input Voltage", "Power", "Input voltage: +7V to +25V DC (min 7V for 5V output)"),
                PinDetail(2, "GROUND", "Ground", "GND", "Common ground (tab is also connected to Ground)"),
                PinDetail(3, "OUTPUT", "Regulated +5.0V Output", "Power", "Clean +5V DC output up to 1.5A max")
            ),
            description = "Three-terminal positive regulator with thermal overload protection and short circuit current limiting. Delivers clean 5V output for microcontrollers and logic circuits.",
            keyFeatures = listOf(
                "Output Voltage: Fixed +5.0V ±4%",
                "Max Output Current: 1.5A (with heatsink)",
                "Min Input Voltage: 7.0V (2V Dropout)",
                "Internal Thermal Overload Protection"
            ),
            notes = "Place a 0.33µF capacitor on the input and a 0.1µF to 10µF capacitor on the output close to the regulator pins to prevent oscillation."
        ),
        PinoutItem(
            id = "pinout_lm7812",
            name = "LM7812 Linear Regulator",
            subtitle = "+12V Positive Voltage Regulator (1.5A)",
            category = "Voltage Regulators",
            packageType = "TO-220",
            pinCount = 3,
            pins = listOf(
                PinDetail(1, "INPUT", "Unregulated Input Voltage", "Power", "+14.5V to +35V DC input"),
                PinDetail(2, "GROUND", "Ground", "GND", "Common ground / heatsink tab"),
                PinDetail(3, "OUTPUT", "Regulated +12.0V Output", "Power", "Clean +12V DC output")
            ),
            description = "Three-terminal 12V positive voltage regulator widely used in audio amplifiers, relay circuits, and 12V power supply stages.",
            keyFeatures = listOf(
                "Output Voltage: +12.0V",
                "Max Current: 1.5A",
                "Dropout Voltage: ~2.0V"
            )
        ),
        PinoutItem(
            id = "pinout_lm317",
            name = "LM317 Adjustable Regulator",
            subtitle = "1.25V to 37V Adjustable Positive Regulator",
            category = "Voltage Regulators",
            packageType = "TO-220",
            pinCount = 3,
            pins = listOf(
                PinDetail(1, "ADJ", "Adjust Pin", "Control", "Connected to resistor divider: Vout = 1.25V * (1 + R2/R1)"),
                PinDetail(2, "VOUT", "Regulated Output", "Power", "Adjustable output voltage (Tab connected to VOUT)"),
                PinDetail(3, "VIN", "Unregulated Input", "Power", "Input voltage (+3V to +40V)")
            ),
            description = "Adjustable 3-terminal positive voltage regulator capable of supplying in excess of 1.5A over an output range of 1.25V to 37V.",
            keyFeatures = listOf(
                "Adjustable Output: 1.25V to 37V",
                "Output Current: 1.5A",
                "Line Regulation: 0.01%/V",
                "Internal Short-Circuit Protection"
            ),
            notes = "Note: Unlike 7805, the TO-220 metal tab is connected to OUTPUT (Pin 2), NOT Ground! Insulate with mica when mounting to shared heatsinks."
        ),
        PinoutItem(
            id = "pinout_2n2222",
            name = "2N2222 / PN2222 NPN Transistor",
            subtitle = "General Purpose NPN Bipolar Junction Transistor",
            category = "Transistors",
            packageType = "TO-92 / TO-18",
            pinCount = 3,
            pins = listOf(
                PinDetail(1, "E (Emitter)", "Emitter Terminal", "I/O", "Connect to ground for low-side switching"),
                PinDetail(2, "B (Base)", "Base Terminal", "Control", "Trigger input (current controlled, Vbe ~ 0.7V)"),
                PinDetail(3, "C (Collector)", "Collector Terminal", "I/O", "Connect to load connected to positive rail")
            ),
            description = "High-speed NPN switching and small-signal amplification transistor. Standard staple for relay drivers, LED driving, and logic interfaces.",
            keyFeatures = listOf(
                "Vceo: 40V",
                "Collector Current Ic: 800mA max",
                "hFE Gain: 100 to 300",
                "Transition Frequency fT: 300 MHz"
            )
        ),
        PinoutItem(
            id = "pinout_bc547",
            name = "BC547 NPN Transistor",
            subtitle = "Audio & General Purpose NPN Transistor",
            category = "Transistors",
            packageType = "TO-92",
            pinCount = 3,
            pins = listOf(
                PinDetail(1, "C (Collector)", "Collector Terminal", "I/O", "Connect to load"),
                PinDetail(2, "B (Base)", "Base Terminal", "Control", "Base control current input"),
                PinDetail(3, "E (Emitter)", "Emitter Terminal", "I/O", "Emitter terminal")
            ),
            description = "Popular low-noise NPN transistor for audio preamplifiers, signal processing, and low-current electronic switching.",
            keyFeatures = listOf(
                "Vceo: 45V",
                "Collector Current Ic: 100mA",
                "hFE Gain: 110 to 800 (A/B/C grades)",
                "Pinout order (flat face front): 1=C, 2=B, 3=E"
            )
        ),
        PinoutItem(
            id = "pinout_irfz44n",
            name = "IRFZ44N Power MOSFET",
            subtitle = "55V 49A N-Channel Power MOSFET (RDSon 17.5mΩ)",
            category = "MOSFETs",
            packageType = "TO-220AB",
            pinCount = 3,
            pins = listOf(
                PinDetail(1, "G (Gate)", "Gate Terminal", "Control", "Voltage-driven control (Vgs threshold 2V to 4V, standard drive 10V)"),
                PinDetail(2, "D (Drain)", "Drain Terminal", "I/O", "Connected to load (Tab is also Drain)"),
                PinDetail(3, "S (Source)", "Source Terminal", "GND", "Connected to system Ground")
            ),
            description = "High-power N-channel HEXFET Power MOSFET with ultra-low on-resistance. Ideal for motor controllers, DC-DC inverters, SMPS, and high-current PWM switching.",
            keyFeatures = listOf(
                "Vds (Drain-Source): 55V",
                "Continuous Drain Current Id: 49A (at 25°C)",
                "RDS(on): 17.5 mΩ max",
                "Ptot: 94W"
            )
        ),
        PinoutItem(
            id = "pinout_uln2003",
            name = "ULN2003A Darlington Array",
            subtitle = "7-Channel High-Voltage High-Current Darlington Array",
            category = "Logic ICs",
            packageType = "DIP-16 / SOIC-16",
            pinCount = 16,
            pins = listOf(
                PinDetail(1, "IN1", "Input Channel 1", "Control", "TTL/CMOS compatible 5V logic input"),
                PinDetail(2, "IN2", "Input Channel 2", "Control", "Channel 2 input"),
                PinDetail(3, "IN3", "Input Channel 3", "Control", "Channel 3 input"),
                PinDetail(4, "IN4", "Input Channel 4", "Control", "Channel 4 input"),
                PinDetail(5, "IN5", "Input Channel 5", "Control", "Channel 5 input"),
                PinDetail(6, "IN6", "Input Channel 6", "Control", "Channel 6 input"),
                PinDetail(7, "IN7", "Input Channel 7", "Control", "Channel 7 input"),
                PinDetail(8, "GND", "Common Ground / Emitter", "GND", "Connect to 0V ground"),
                PinDetail(9, "COM", "Common Flyback Free-wheeling Diode", "Power", "Connect to inductive load supply V+ (for relays/steppers)"),
                PinDetail(10, "OUT7", "Open-Collector Output 7", "I/O", "Sinks up to 500mA"),
                PinDetail(11, "OUT6", "Open-Collector Output 6", "I/O", "Sinks up to 500mA"),
                PinDetail(12, "OUT5", "Open-Collector Output 5", "I/O", "Sinks up to 500mA"),
                PinDetail(13, "OUT4", "Open-Collector Output 4", "I/O", "Sinks up to 500mA"),
                PinDetail(14, "OUT3", "Open-Collector Output 3", "I/O", "Sinks up to 500mA"),
                PinDetail(15, "OUT2", "Open-Collector Output 2", "I/O", "Sinks up to 500mA"),
                PinDetail(16, "OUT1", "Open-Collector Output 1", "I/O", "Sinks up to 500mA")
            ),
            description = "Monolithic high-voltage and high-current Darlington transistor array containing seven open collector Darlington pairs with common emitters and integral clamp diodes for inductive loads.",
            keyFeatures = listOf(
                "500mA rated collector current (single output)",
                "50V output voltage rating",
                "Integrated clamp diodes for inductive transient suppression"
            )
        ),
        PinoutItem(
            id = "pinout_atmega328p",
            name = "ATmega328P Microcontroller",
            subtitle = "8-bit AVR Microcontroller (Arduino Core MCU)",
            category = "Microcontrollers",
            packageType = "DIP-28",
            pinCount = 28,
            pins = listOf(
                PinDetail(1, "PC6 / RESET", "Reset Pin (Active Low)", "Control", "Arduino Reset (PCINT14)"),
                PinDetail(2, "PD0 / RXD", "Digital Pin 0 (RX)", "Comm", "Serial Receive"),
                PinDetail(3, "PD1 / TXD", "Digital Pin 1 (TX)", "Comm", "Serial Transmit"),
                PinDetail(4, "PD2 / INT0", "Digital Pin 2", "I/O", "External Interrupt 0"),
                PinDetail(5, "PD3 / INT1 / OC2B", "Digital Pin 3 (PWM)", "PWM", "PWM / Ext Interrupt 1"),
                PinDetail(6, "PD4 / T0", "Digital Pin 4", "I/O", "Timer0 external clock input"),
                PinDetail(7, "VCC", "Digital Supply Voltage", "Power", "+5V DC supply"),
                PinDetail(8, "GND", "Ground", "GND", "Ground (0V)"),
                PinDetail(9, "PB6 / XTAL1", "Crystal Oscillator 1", "Analog", "16MHz Crystal In"),
                PinDetail(10, "PB7 / XTAL2", "Crystal Oscillator 2", "Analog", "16MHz Crystal Out"),
                PinDetail(11, "PD5 / OC0B", "Digital Pin 5 (PWM)", "PWM", "Timer0 PWM"),
                PinDetail(12, "PD6 / OC0A", "Digital Pin 6 (PWM)", "PWM", "Timer0 PWM"),
                PinDetail(13, "PD7 / AIN0", "Digital Pin 7", "I/O", "Analog Comparator Positive"),
                PinDetail(14, "PB0 / ICP1", "Digital Pin 8", "I/O", "Timer1 Input Capture"),
                PinDetail(15, "PB1 / OC1A", "Digital Pin 9 (PWM)", "PWM", "Timer1 16-bit PWM"),
                PinDetail(16, "PB2 / OC1B / SS", "Digital Pin 10 (PWM / SS)", "PWM", "SPI Slave Select"),
                PinDetail(17, "PB3 / MOSI / OC2A", "Digital Pin 11 (PWM / MOSI)", "PWM", "SPI Master Out"),
                PinDetail(18, "PB4 / MISO", "Digital Pin 12 (MISO)", "Comm", "SPI Master In"),
                PinDetail(19, "PB5 / SCK", "Digital Pin 13 (SCK / LED)", "Comm", "SPI Clock / Built-in LED"),
                PinDetail(20, "AVCC", "Analog Supply Voltage", "Power", "Connect to +5V (with LC filter)"),
                PinDetail(21, "AREF", "Analog Reference Voltage", "Analog", "ADC Reference (0.1µF to GND)"),
                PinDetail(22, "GND", "Analog Ground", "GND", "Ground (0V)"),
                PinDetail(23, "PC0 / ADC0", "Analog Pin A0", "Analog", "10-bit ADC Channel 0"),
                PinDetail(24, "PC1 / ADC1", "Analog Pin A1", "Analog", "10-bit ADC Channel 1"),
                PinDetail(25, "PC2 / ADC2", "Analog Pin A2", "Analog", "10-bit ADC Channel 2"),
                PinDetail(26, "PC3 / ADC3", "Analog Pin A3", "Analog", "10-bit ADC Channel 3"),
                PinDetail(27, "PC4 / ADC4 / SDA", "Analog Pin A4 (I2C SDA)", "Comm", "I2C Data line"),
                PinDetail(28, "PC5 / ADC5 / SCL", "Analog Pin A5 (I2C SCL)", "Comm", "I2C Clock line")
            ),
            description = "High-performance RISC 8-bit AVR microcontroller with 32KB Flash, 2KB SRAM, 1KB EEPROM, 6-channel 10-bit ADC, and standard SPI/I2C/UART hardware interfaces.",
            keyFeatures = listOf(
                "Operating Voltage: 1.8V to 5.5V",
                "Speed: Up to 20MHz (16MHz in Arduino)",
                "32KB Flash Program Memory",
                "6 PWM Channels and 6 Analog Inputs"
            )
        ),
        PinoutItem(
            id = "pinout_esp32",
            name = "ESP-WROOM-32 (ESP32 30-Pin DevKit)",
            subtitle = "Dual-Core 240MHz Wi-Fi & Bluetooth MCU",
            category = "Microcontrollers",
            packageType = "30-Pin NodeMCU DevKit",
            pinCount = 30,
            pins = listOf(
                PinDetail(1, "EN", "Enable / Reset", "Control", "Active High Enable (pull HIGH to run)"),
                PinDetail(2, "VP / GPIO36", "ADC1_CH0 / SENSOR_VP", "Analog", "Input only ADC"),
                PinDetail(3, "VN / GPIO39", "ADC1_CH3 / SENSOR_VN", "Analog", "Input only ADC"),
                PinDetail(4, "GPIO34", "ADC1_CH6", "Analog", "Input only"),
                PinDetail(5, "GPIO35", "ADC1_CH7", "Analog", "Input only"),
                PinDetail(6, "GPIO32", "ADC1_CH4 / Touch9", "Analog", "ADC / Touch / RTC GPIO"),
                PinDetail(7, "GPIO33", "ADC1_CH5 / Touch8", "Analog", "ADC / Touch / RTC GPIO"),
                PinDetail(8, "GPIO25", "DAC1 / ADC2_CH8", "Analog", "8-bit True DAC / GPIO"),
                PinDetail(9, "GPIO26", "DAC2 / ADC2_CH9", "Analog", "8-bit True DAC / GPIO"),
                PinDetail(10, "GPIO27", "ADC2_CH7 / Touch7", "I/O", "Touch / PWM / GPIO"),
                PinDetail(11, "GPIO14", "ADC2_CH6 / Touch6 / HSPI_CLK", "I/O", "SPI / PWM"),
                PinDetail(12, "GPIO12", "ADC2_CH5 / Touch5 / HSPI_MISO", "I/O", "Boot strappin (do not pull HIGH on boot)"),
                PinDetail(13, "GPIO13", "ADC2_CH4 / Touch4 / HSPI_MOSI", "I/O", "SPI / PWM"),
                PinDetail(14, "GND", "Ground", "GND", "0V Ground"),
                PinDetail(15, "VIN / 5V", "Power Input (5V)", "Power", "External 5V DC power in"),
                PinDetail(16, "3V3", "Regulated +3.3V Output", "Power", "+3.3V power output (up to 500mA)"),
                PinDetail(17, "GND", "Ground", "GND", "0V Ground"),
                PinDetail(18, "GPIO15", "ADC2_CH3 / Touch3 / HSPI_CS", "I/O", "PWM / Boot debug output"),
                PinDetail(19, "GPIO2", "ADC2_CH2 / Touch2 / Onboard LED", "I/O", "Connected to blue onboard LED"),
                PinDetail(20, "GPIO4", "ADC2_CH0 / Touch0", "I/O", "Touch / PWM / GPIO"),
                PinDetail(21, "GPIO16 / RX2", "UART2 RX", "Comm", "Hardware Serial 2 RX"),
                PinDetail(22, "GPIO17 / TX2", "UART2 TX", "Comm", "Hardware Serial 2 TX"),
                PinDetail(23, "GPIO5", "VSPI_CS", "I/O", "SPI Chip Select"),
                PinDetail(24, "GPIO18", "VSPI_SCK", "Comm", "SPI Clock"),
                PinDetail(25, "GPIO19", "VSPI_MISO", "Comm", "SPI Master In"),
                PinDetail(26, "GPIO21", "I2C SDA", "Comm", "Hardware I2C Data"),
                PinDetail(27, "GPIO3 / RX0", "UART0 RX", "Comm", "USB Serial RX"),
                PinDetail(28, "GPIO1 / TX0", "UART0 TX", "Comm", "USB Serial TX"),
                PinDetail(29, "GPIO22", "I2C SCL", "Comm", "Hardware I2C Clock"),
                PinDetail(30, "GPIO23", "VSPI_MOSI", "Comm", "SPI Master Out")
            ),
            description = "Xtensa 32-bit Dual-Core microcontroller with integrated Wi-Fi 802.11 b/g/n, Bluetooth v4.2 BR/EDR and BLE, ultra-low power co-processor, capacitive touch sensors, Hall sensor, and dual DACs.",
            keyFeatures = listOf(
                "3.3V Logic Level (NOT 5V tolerant)",
                "240MHz Dual Core CPU",
                "520KB SRAM, 4MB Flash",
                "12-bit ADC & 2x 8-bit DAC channels"
            ),
            notes = "GPIOs 34, 35, 36, 39 are input only and have no internal pull-up/pull-down resistors."
        ),
        PinoutItem(
            id = "pinout_lcd1602",
            name = "16x2 Character LCD (HD44780)",
            subtitle = "Standard 16 Pin Alphanumeric LCD Display",
            category = "Displays",
            packageType = "16-Pin Header",
            pinCount = 16,
            pins = listOf(
                PinDetail(1, "VSS", "Ground", "GND", "0V Ground reference"),
                PinDetail(2, "VDD", "Power Supply (+5V)", "Power", "+5V DC Logic supply"),
                PinDetail(3, "V0", "Contrast Adjustment", "Analog", "Connect to 10k potentiometer wiper (0V to 5V)"),
                PinDetail(4, "RS", "Register Select", "Control", "LOW = Command register, HIGH = Data register"),
                PinDetail(5, "R/W", "Read / Write Select", "Control", "LOW = Write to LCD (ground in 99% of projects), HIGH = Read"),
                PinDetail(6, "E", "Enable Strobe", "Control", "High-to-Low pulse latches data"),
                PinDetail(7, "D0", "Data Bit 0", "I/O", "8-bit mode data line 0"),
                PinDetail(8, "D1", "Data Bit 1", "I/O", "8-bit mode data line 1"),
                PinDetail(9, "D2", "Data Bit 2", "I/O", "8-bit mode data line 2"),
                PinDetail(10, "D3", "Data Bit 3", "I/O", "8-bit mode data line 3"),
                PinDetail(11, "D4", "Data Bit 4", "I/O", "4-bit mode data line 4"),
                PinDetail(12, "D5", "Data Bit 5", "I/O", "4-bit mode data line 5"),
                PinDetail(13, "D6", "Data Bit 6", "I/O", "4-bit mode data line 6"),
                PinDetail(14, "D7", "Data Bit 7", "I/O", "4-bit mode data line 7 (Busy flag)"),
                PinDetail(15, "A / LED+", "Backlight Anode (+5V)", "Power", "Connect to +5V through 220Ω resistor"),
                PinDetail(16, "K / LED-", "Backlight Cathode (GND)", "GND", "Connect to Ground (0V)")
            ),
            description = "Standard 16 character x 2 line alphanumeric LCD module powered by the Hitachi HD44780 or equivalent controller. Supports 4-bit and 8-bit parallel interfaces.",
            keyFeatures = listOf(
                "5x8 dot matrix with cursor",
                "Built-in ASCII character generator ROM",
                "I2C Backpack adapter available (PCFx8574 with 4 pins: GND, VCC, SDA, SCL)"
            )
        ),
        PinoutItem(
            id = "pinout_db25",
            name = "DB25 Parallel Port (IEEE 1284)",
            subtitle = "Legacy PC 25-Pin Parallel Printer Port",
            category = "Ports & Connectors",
            packageType = "DB-25 Female / Male",
            pinCount = 25,
            pins = listOf(
                PinDetail(1, "STROBE", "Data Strobe (Active Low)", "Control", "Output: Latches byte into printer"),
                PinDetail(2, "DATA 0", "Data Bit 0 (LSB)", "I/O", "Output / Bidirectional Data 0"),
                PinDetail(3, "DATA 1", "Data Bit 1", "I/O", "Data 1"),
                PinDetail(4, "DATA 2", "Data Bit 2", "I/O", "Data 2"),
                PinDetail(5, "DATA 3", "Data Bit 3", "I/O", "Data 3"),
                PinDetail(6, "DATA 4", "Data Bit 4", "I/O", "Data 4"),
                PinDetail(7, "DATA 5", "Data Bit 5", "I/O", "Data 5"),
                PinDetail(8, "DATA 6", "Data Bit 6", "I/O", "Data 6"),
                PinDetail(9, "DATA 7", "Data Bit 7 (MSB)", "I/O", "Data 7"),
                PinDetail(10, "ACK", "Acknowledge (Active Low)", "Control", "Input: Peripheral ready for next byte"),
                PinDetail(11, "BUSY", "Busy", "Control", "Input: Peripheral busy, do not send"),
                PinDetail(12, "PAPER OUT", "Paper End / Empty", "Control", "Input: Out of paper error"),
                PinDetail(13, "SELECT", "Select In", "Control", "Input: Device is online"),
                PinDetail(14, "AUTO FEED", "Auto Line Feed", "Control", "Output: Automatic line feed on CR"),
                PinDetail(15, "ERROR", "Fault / Error", "Control", "Input: Peripheral error"),
                PinDetail(16, "INIT", "Initialize Printer", "Control", "Output: Reset pulse to printer"),
                PinDetail(17, "SELECT IN", "Select Printer", "Control", "Output: Select device"),
                PinDetail(18, "GND", "Signal Ground 18", "GND", "Ground return"),
                PinDetail(19, "GND", "Signal Ground 19", "GND", "Ground return"),
                PinDetail(20, "GND", "Signal Ground 20", "GND", "Ground return"),
                PinDetail(21, "GND", "Signal Ground 21", "GND", "Ground return"),
                PinDetail(22, "GND", "Signal Ground 22", "GND", "Ground return"),
                PinDetail(23, "GND", "Signal Ground 23", "GND", "Ground return"),
                PinDetail(24, "GND", "Signal Ground 24", "GND", "Ground return"),
                PinDetail(25, "GND", "Signal Ground 25", "GND", "Ground return")
            ),
            description = "Standard 25-pin D-sub connector used historically on personal computers for Centronics printer interface, CNC controllers, FPGA programmers, and custom hardware instrumentation.",
            keyFeatures = listOf(
                "8 Data Lines (Pins 2-9)",
                "4 Control Lines (Pins 1, 14, 16, 17)",
                "5 Status Lines (Pins 10, 11, 12, 13, 15)",
                "8 Dedicated Ground Pins (18-25)"
            )
        ),
        PinoutItem(
            id = "pinout_dht22",
            name = "DHT22 / AM2302 Sensor",
            subtitle = "High Accuracy Digital Temperature & Humidity Sensor",
            category = "Sensors",
            packageType = "4-Pin Grid Module",
            pinCount = 4,
            pins = listOf(
                PinDetail(1, "VDD", "Power Supply (+3.3V to +5.5V)", "Power", "DC Power In"),
                PinDetail(2, "DATA", "Single-Bus Digital Data", "I/O", "Serial communication line (requires 4.7k-10k pullup to VDD)"),
                PinDetail(3, "NC", "Null Pin", "Control", "No connection (leave open)"),
                PinDetail(4, "GND", "Ground", "GND", "0V Ground")
            ),
            description = "Capacitive humidity sensor and thermistor with custom single-wire digital protocol delivering calibrated digital signals for ambient humidity and temperature.",
            keyFeatures = listOf(
                "Humidity Range: 0 to 100% RH (±2% accuracy)",
                "Temperature: -40°C to +80°C (±0.5°C)",
                "Sampling Rate: 0.5 Hz (every 2 seconds)"
            )
        ),
        PinoutItem(
            id = "pinout_hcsr04",
            name = "HC-SR04 Ultrasonic Distance Sensor",
            subtitle = "Ultrasonic Distance Ranging Module (2cm - 400cm)",
            category = "Sensors",
            packageType = "4-Pin Header",
            pinCount = 4,
            pins = listOf(
                PinDetail(1, "VCC", "+5V Power Supply", "Power", "+5V DC regulated supply"),
                PinDetail(2, "TRIG", "Trigger Pulse Input", "Control", "Send 10µs HIGH TTL pulse to start ultrasonic ping"),
                PinDetail(3, "ECHO", "Echo Pulse Output", "I/O", "Output pulse width proportional to distance: Distance = (Time * 0.0343) / 2 cm"),
                PinDetail(4, "GND", "Ground", "GND", "0V Ground")
            ),
            description = "Provides 2cm to 400cm non-contact distance measurement with accuracy down to 3mm. Module includes ultrasonic transmitter, receiver, and control circuit.",
            keyFeatures = listOf(
                "Working Frequency: 40 kHz",
                "Measuring Angle: 15 degrees",
                "Trigger Input: 10µs TTL pulse"
            )
        )
    )
}
