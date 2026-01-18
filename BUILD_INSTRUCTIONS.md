# How to Build the Android App

## Option 1: Build from Android Studio (Recommended)

1. **Open the project:**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to and select the `android-app` folder
   - Wait for Gradle sync to complete

2. **Build the app:**
   - **Debug build**: Click the green "Run" button (▶️) or press `Shift + F10`
   - **Release build**: Build → Generate Signed Bundle / APK

3. **Build without running:**
   - Build → Make Project (or `Ctrl + F9`)
   - Build → Clean Project (to clean)
   - Build → Rebuild Project (to clean and rebuild)

## Option 2: Build from Command Line

### Setup Gradle Wrapper (if not present)

First, you need to generate the Gradle wrapper scripts. You can do this by:

1. **If you have Gradle installed globally:**
   ```bash
   cd android-app
   gradle wrapper
   ```

2. **Or use Android Studio:**
   - Open the project in Android Studio
   - The wrapper scripts will be generated automatically during sync

### Build Commands

Once the wrapper is set up, navigate to the `android-app` directory and use:

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK (requires signing configuration)
./gradlew assembleRelease

# Clean build
./gradlew clean build

# Install on connected device
./gradlew installDebug

# Run tests
./gradlew test
```

The built APK files will be located at:
- Debug: `android-app/app/build/outputs/apk/debug/app-debug.apk`
- Release: `android-app/app/build/outputs/apk/release/app-release.apk`

## Prerequisites

- **Android Studio** Hedgehog (2023.1.1) or later
- **JDK 17** or later
- **Android SDK** with API level 24+ (Android 7.0)
- **Target SDK**: API level 34 (Android 14)

## Troubleshooting

### "Gradle sync failed"
- File → Invalidate Caches / Restart in Android Studio
- Delete `.gradle` folder in `android-app` directory
- Run `./gradlew clean` (if wrapper exists)

### "SDK location not found"
- Configure Android SDK location: File → Project Structure → SDK Location
- Or set `ANDROID_HOME` environment variable

### Build takes too long
- First build downloads dependencies (can take 5-10 minutes)
- Subsequent builds are much faster
- Ensure stable internet connection
