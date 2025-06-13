# 🚀 Space X

A modern Android application built with **Kotlin**,
following **Clean Architecture** and **MVI** pattern for scalable, maintainable, and testable code.
---

## Project Structure

├── app/ # Application module: navigation , entry point
├── core/ # Common utilities and shared code
├── data/ # Repositories, DTOs, data sources (network/local)
├── domain/ # Use-cases, business models, interfaces
├── presentation/ # UI logic: ViewModels, state handling, UI events

---

## Tech Stack

- Clean Architecture
- Multi-Modules
- MVI (Model–View–Intent)
- Kotlin
- Jetpack Compose

## Dependencies

- Hilt
- Retrofit
- Kotlin Coroutines
- Glide
- OkHttp

## Testing
- Unit Testing:
    - JUnit 5
    - MockK
    - Turbine
- UI Testing:
    - Jetpack Compose UI Test
