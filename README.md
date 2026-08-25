# SULTAN ELECTRONICS

SULTAN ELECTRONICS is an offline-first professional electronics calculator, reference, pinout and virtual laboratory application.

## SULTAN ADVANCED
Includes realistic component references, pinouts, 3D-style component views, fan/motor wiring guides, practical circuit references and comparison tools.

## New Workshop Features (v1.1)
- **SULTAN Component Stock Manager** — local inventory with part number, category, quantity, minimum-stock threshold, location, unit cost, notes, search, +/- quantity controls and low-stock alerts.
- **SULTAN Workbench Mode** — persistent repair/workshop projects with device model, fault/complaint, measurements, parts used/replaced and diagnosis notes.
- **SULTAN Electronics Handbook** — offline searchable practical reference guides covering component testing, power supplies, regulators, relay drivers, MCU safety, I2C/SPI, soldering and electronics safety.

All three features use the existing Room database and work without internet access.

## Build
GitHub Actions workflow: `.github/workflows/android-build.yml`

The workflow installs Gradle 9.3.1, uses JDK 17, runs the debug build, runs unit tests, and uploads the generated debug APK as an artifact.

## Launcher Icon
The launcher artwork is sourced from the developer-supplied `sultan_advanced_icon.png` and copied into density-specific launcher resources without redesigning the artwork.

## Important
The source package has been statically checked in this environment. A full Android Gradle build requires the Android/Gradle dependency environment available in GitHub Actions or Android Studio.
