# Pharmacy Management System - Android App

A modern Android application built with Jetpack Compose for managing pharmacy operations, including drugs, stock, and orders.

## Features

- **Authentication**: Secure login with token-based authentication
- **Drug Management**: Browse and search drugs
- **Stock Management**: View stock levels, available stock, and expired items
- **Order Management**: View and manage orders
- **User Profile**: View profile and logout

## Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- JDK 17 or later
- Android SDK with API level 24 (minimum) and 34 (target)
- Gradle 8.2 or later

## Setup Instructions

### 1. Configure API Base URL

Before running the app, you need to configure the API base URL in `RetrofitClient.kt`:

```kotlin
// For Android Emulator
private const val BASE_URL = "http://10.0.2.2:8000/api/v1/"

// For Physical Device (replace with your server IP)
private const val BASE_URL = "http://YOUR_SERVER_IP:8000/api/v1/"
```

**Note:**
- `10.0.2.2` is the special IP address that points to `localhost` on your development machine when using the Android emulator
- For physical devices, use your computer's local IP address (e.g., `192.168.1.100`)
- Make sure your Laravel backend is running and accessible

### 2. Open Project in Android Studio

1. Open Android Studio
2. Select "Open an Existing Project"
3. Navigate to the `android-app` directory
4. Wait for Gradle sync to complete

### 3. Build and Run

1. Connect an Android device or start an emulator
2. Click "Run" or press `Shift + F10`
3. The app will build and install on your device/emulator

## Project Structure

```
android-app/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/pharmacy/pms/
│   │       │   ├── data/
│   │       │   │   ├── api/          # API service interfaces
│   │       │   │   ├── local/        # Local storage (token management)
│   │       │   │   ├── model/        # Data models
│   │       │   │   └── repository/   # Repository pattern implementation
│   │       │   ├── ui/
│   │       │   │   ├── navigation/   # Navigation setup
│   │       │   │   ├── screen/       # UI screens
│   │       │   │   ├── theme/        # App theme and colors
│   │       │   │   └── viewmodel/    # ViewModels
│   │       │   └── MainActivity.kt
│   │       └── res/                  # Resources (strings, themes, etc.)
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Architecture

The app follows **MVVM (Model-View-ViewModel)** architecture:

- **Model**: Data models and API interfaces
- **View**: Jetpack Compose UI screens
- **ViewModel**: Business logic and state management
- **Repository**: Data layer abstraction

## Key Technologies

- **Jetpack Compose**: Modern declarative UI framework
- **Retrofit**: Type-safe HTTP client for API calls
- **Gson**: JSON serialization/deserialization
- **DataStore**: Secure token storage
- **Navigation Compose**: Navigation between screens
- **Material Design 3**: Modern Material Design components
- **Coroutines**: Asynchronous programming
- **Flow**: Reactive streams for state management

## API Integration

The app integrates with the Laravel backend API. Make sure:

1. The Laravel backend is running
2. CORS is properly configured for mobile app requests
3. The API base URL is correctly set in `RetrofitClient.kt`
4. You have valid user credentials for testing

## Testing

### Testing on Emulator

1. Start the Android emulator
2. Ensure the Laravel backend is running on your host machine
3. Use `http://10.0.2.2:8000/api/v1/` as the base URL

### Testing on Physical Device

1. Connect your Android device via USB
2. Enable USB debugging
3. Find your computer's IP address:
   - Windows: `ipconfig`
   - Linux/Mac: `ifconfig` or `ip addr`
4. Update the base URL in `RetrofitClient.kt` to use your IP
5. Ensure your device and computer are on the same network
6. Make sure the Laravel backend allows connections from your network

## Troubleshooting

### Connection Issues

- **Cannot connect to API**: 
  - Verify the backend is running
  - Check the base URL is correct
  - For physical devices, ensure firewall allows connections
  - Check network connectivity

### Build Errors

- **Gradle sync failed**: 
  - Invalidate caches: File → Invalidate Caches / Restart
  - Clean project: Build → Clean Project
  - Rebuild: Build → Rebuild Project

- **Dependency issues**:
  - Update Android Studio to the latest version
  - Update Gradle wrapper: `./gradlew wrapper --gradle-version=8.2`

### Runtime Errors

- **Token not found**: 
  - Logout and login again
  - Clear app data: Settings → Apps → Pharmacy Management → Clear Data

## Development

### Adding New Features

1. Create data models in `data/model/`
2. Add API endpoints in `data/api/ApiService.kt`
3. Create repository in `data/repository/`
4. Create ViewModel in `ui/viewmodel/`
5. Create UI screen in `ui/screen/`
6. Add navigation route in `ui/navigation/NavGraph.kt`

### Code Style

- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex logic
- Keep functions focused and small

## License

This project is part of the Pharmacy Management System.

## Support

For issues or questions, please refer to the main project documentation or create an issue in the repository.
# android-pms
