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


## 📜 Reference List


- Android Developers, 2019. Save data in a local database using room  |  android developers. [online] Android Developers. Available at: <https://developer.android.com/training/data-storage/room> [Accessed 17 August 2026].
- Android Developers, n.d. App architecture: Data layer - persistent work with WorkManager - android developers | background work. [online] Android Developers. Available at: <https://developer.android.com/develop/background-work/background-tasks/persistent> [Accessed 17 August 2026].
- Android Developers, n.d. BiometricPrompt. [online] Android Developers. Available at: <https://developer.android.com/reference/android/hardware/biometrics/BiometricPrompt> [Accessed 17 August 2026].
- Android Developers, n.d. Material design 3 in compose | jetpack compose. [online] Android Developers. Available at: <https://developer.android.com/develop/ui/compose/designsystems/material3> [Accessed 17 August 2026].
- Authgear, 2025. Login & signup UX: The 2025 guide to best practices (examples & tips). [online] Authgear. Available at: <https://www.authgear.com/post/login-signup-ux-guide/> [Accessed 23 August 2026].
- Bennett, T., 2024. Direct database access vs. REST APIs: Compare application activity. [online] blog.dreamfactory.com. Available at: <https://blog.dreamfactory.com/direct-database-access-vs-rest-apis-pros-and-cons-for-application-connectivity> [Accessed 17 August 2026].
- Cloudflare, 2024. What is rate limiting? | Rate limiting and bots. [online] Cloudflare.com. Available at: <https://www.cloudflare.com/learning/bots/what-is-rate-limiting/> [Accessed 23 August 2026].
- Firebase, 2026. Get started with firebase cloud messaging in android apps. [online] Firebase. Available at: <https://firebase.google.com/docs/cloud-messaging/android/get-started> [Accessed 17 August 2026].
- InEight, 2023. 8 Must-haves for a construction management platform. [online] InEight. Available at: <https://ineight.com/blog/8-must-haves-for-a-construction-management-platform/> [Accessed 17 August 2026].
- Kitch, B., 2024. How to create an agile project plan for software development. [online] Mural.co. Available at: <https://www.mural.co/blog/how-to-create-an-agile-project-plan> [Accessed 17 August 2026].
- Kohler, T., 2022. Autonomy, relatedness, and competence in UX design. [online] Nielsen Norman Group. Available at: <https://www.nngroup.com/articles/autonomy-relatedness-competence/> [Accessed 17 August 2026].
- PostgREST, 2017. Pagination and count. [online] PostgREST 16. Available at: <https://docs.postgrest.org/en/stable/references/api/pagination_count.html> [Accessed 17 August 2026].
- QuickBooks, 2026. What is data export? Meaning & process in 2025 | QuickBooks. [online] Intuit.com. Available at: <https://quickbooks.intuit.com/r/bookkeeping/data-export/> [Accessed 23 August 2026].
- Render, n.d. Cloud application hosting for developers | render. [online] Cloud Application Hosting for Developers | Render. Available at: <https://render.com/> [Accessed 17 August 2026].
- Softbiz, 2026. Why business logic belongs on the server, not the frontend. [online] Softbiz. Available at: <https://www.softbiz.com/technology/backend-and-api-development/why-business-logic-belongs-on-the-server-not-the-frontend> [Accessed 17 August 2026].
- Supabase, 2023. Auth | supabase docs. [online] supabase.com. Available at: <https://supabase.com/docs/guides/auth> [Accessed 17 August 2026].
- Supabase, 2024. Row level security | supabase docs. [online] Supabase. Available at: <https://supabase.com/docs/guides/database/postgres/row-level-security> [Accessed 17 August 2026].
- Supabase, 2026. Environment variables | supabase Docs. [online] supabase. Available at: <https://supabase.com/docs/guides/functions/secrets> [Accessed 17 August 2026].W3C, 2024. Web content accessibility guidelines (WCAG) 2.2. [online] www.w3.org. W3C. Available at: <https://www.w3.org/TR/WCAG22/> [Accessed 17 August 2026].
