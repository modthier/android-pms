# Quick Start Guide

## Prerequisites Check

- [ ] Android Studio installed (Hedgehog or later)
- [ ] JDK 17 or later installed
- [ ] Laravel backend running and accessible
- [ ] Android device or emulator ready

## Step-by-Step Setup

### 1. Configure API URL (IMPORTANT!)

Open `app/src/main/java/com/pharmacy/pms/data/api/RetrofitClient.kt` and update the `BASE_URL`:

**For Android Emulator:**
```kotlin
private const val BASE_URL = "http://10.0.2.2:8000/api/v1/"
```

**For Physical Device:**
1. Find your computer's IP address:
   - Windows: Open CMD and run `ipconfig`
   - Linux/Mac: Open terminal and run `ifconfig` or `ip addr`
2. Update the URL:
```kotlin
private const val BASE_URL = "http://192.168.1.XXX:8000/api/v1/" // Replace XXX with your IP
```

### 2. Open Project

1. Launch Android Studio
2. File → Open
3. Select the `android-app` folder
4. Wait for Gradle sync (may take a few minutes on first open)

### 3. Run the App

1. Connect device or start emulator
2. Click the green "Run" button (or press Shift+F10)
3. Wait for build to complete
4. App will launch automatically

### 4. Test Login

Use credentials from your Laravel backend to test the login functionality.

## Common Issues

### "Cannot connect to API"
- Verify backend is running: `php artisan serve`
- Check BASE_URL is correct
- For physical device: Ensure same WiFi network
- Check firewall settings

### "Gradle sync failed"
- File → Invalidate Caches / Restart
- Build → Clean Project
- Build → Rebuild Project

### "Build failed"
- Update Android Studio
- Check internet connection (for downloading dependencies)
- Verify JDK version: File → Project Structure → SDK Location

## Next Steps

- Review the main [README.md](README.md) for detailed documentation
- Explore the code structure
- Customize the UI to match your needs
- Add additional features as required
