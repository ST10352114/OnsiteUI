# OnSite - Construction Management System

OnSite is a modern Android application designed for construction site management, crew assignment, and daily progress reporting. It features a robust architecture leveraging Jetpack Compose, Supabase, and MVVM principles.

## 🚀 Features

- **Authentication**: Secure login via Supabase Auth (Email & Google OAuth).
- **Biometric Security**: Quick unlock using fingerprint or face recognition.
- **Role-Based Access**:
    - **Admin Dashboard**: Oversee all sites, manage crew, and review daily updates.
    - **Foreman View**: Manage assigned sites and submit daily progress reports.
- **Daily Updates**: Capture site progress with notes and photos.
- **Offline Support**: Cache updates when connectivity is low (ready for sync).
- **Notifications**: Real-time alerts via Firebase Cloud Messaging (FCM).
- **Achievements**: Gamified progress tracking for foremen.

## 🛠 Tech Stack

- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Backend**: [Supabase](https://supabase.com/) (Auth, Postgrest)
- **Networking**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/)
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/) (if applicable)
- **Serialization**: [Kotlinx Serialization](https://kotlinlang.org/docs/serialization.html)
- **Navigation**: [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- **Architecture**: MVVM (Model-View-ViewModel) with Coroutines and StateFlow.
- **CI/CD**: GitHub Actions for automated builds and testing.

## 📂 Project Structure

- `app/src/main/java/com/example/onsite_mockups/`
    - `data/`: Models, Repositories, and Network clients.
    - `security/`: Biometric management.
    - `ui/`: Compose screens, themes, and ViewModels.
        - `screens/admin/`: Administrator-specific UI.
        - `screens/foreman/`: Foreman-specific UI.
        - `screens/shared/`: Login, Splash, and Notifications.
        - `viewmodels/`: Business logic and state management.

## 🛠 Setup & Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/OnSite-Mockups.git
   ```
2. **Open in Android Studio**:
   Use Android Studio Ladybug (or newer) for the best experience.
3. **Configure Supabase**:
   Update `SupabaseClient.kt` with your Project URL and Anon Key.
4. **Firebase Configuration**:
   Add your `google-services.json` to the `app/` directory for push notifications.
5. **Build & Run**:
   Sync Gradle and run the `:app` module on an emulator or physical device.

## 👷 CI/CD

The project includes a GitHub Actions workflow located at `.github/workflows/android.yml`. It automatically:
- Builds the debug APK.
- Runs unit tests.
- Uploads the build artifact on every push or pull request to `main`/`master`.

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
