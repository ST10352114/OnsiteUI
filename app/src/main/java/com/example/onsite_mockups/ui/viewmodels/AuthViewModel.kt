//Reference list
// Android Developers, 2019. Save data in a local database using room  |  android developers. [online] Android Developers. Available at: <https://developer.android.com/training/data-storage/room> [Accessed 17 August 2026].
// Android Developers, n.d. App architecture: Data layer - persistent work with WorkManager - android developers | background work. [online] Android Developers. Available at: <https://developer.android.com/develop/background-work/background-tasks/persistent> [Accessed 17 August 2026].
// Android Developers, n.d. BiometricPrompt. [online] Android Developers. Available at: <https://developer.android.com/reference/android/hardware/biometrics/BiometricPrompt> [Accessed 17 August 2026].
// Android Developers, n.d. Material design 3 in compose | jetpack compose. [online] Android Developers. Available at: <https://developer.android.com/develop/ui/compose/designsystems/material3> [Accessed 17 August 2026].
// Authgear, 2025. Login & signup UX: The 2025 guide to best practices (examples & tips). [online] Authgear. Available at: <https://www.authgear.com/post/login-signup-ux-guide/> [Accessed 23 August 2026].
// Bennett, T., 2024. Direct database access vs. REST APIs: Compare application activity. [online] blog.dreamfactory.com. Available at: <https://blog.dreamfactory.com/direct-database-access-vs-rest-apis-pros-and-cons-for-application-connectivity> [Accessed 17 August 2026].
// Cloudflare, 2024. What is rate limiting? | Rate limiting and bots. [online] Cloudflare.com. Available at: <https://www.cloudflare.com/learning/bots/what-is-rate-limiting/> [Accessed 23 August 2026].
// Firebase, 2026. Get started with firebase cloud messaging in android apps. [online] Firebase. Available at: <https://firebase.google.com/docs/cloud-messaging/android/get-started> [Accessed 17 August 2026].
// InEight, 2023. 8 Must-haves for a construction management platform. [online] InEight. Available at: <https://ineight.com/blog/8-must-haves-for-a-construction-management-platform/> [Accessed 17 August 2026].
// Kitch, B., 2024. How to create an agile project plan for software development. [online] Mural.co. Available at: <https://www.mural.co/blog/how-to-create-an-agile-project-plan> [Accessed 17 August 2026].
// Kohler, T., 2022. Autonomy, relatedness, and competence in UX design. [online] Nielsen Norman Group. Available at: <https://www.nngroup.com/articles/autonomy-relatedness-competence/> [Accessed 17 August 2026].
// PostgREST, 2017. Pagination and count. [online] PostgREST 16. Available at: <https://docs.postgrest.org/en/stable/references/api/pagination_count.html> [Accessed 17 August 2026].
// QuickBooks, 2026. What is data export? Meaning & process in 2025 | QuickBooks. [online] Intuit.com. Available at: <https://quickbooks.intuit.com/r/bookkeeping/data-export/> [Accessed 23 August 2026].
// Render, n.d. Cloud application hosting for developers | render. [online] Cloud Application Hosting for Developers | Render. Available at: <https://render.com/> [Accessed 17 August 2026].
// Softbiz, 2026. Why business logic belongs on the server, not the frontend. [online] Softbiz. Available at: <https://www.softbiz.com/technology/backend-and-api-development/why-business-logic-belongs-on-the-server-not-the-frontend> [Accessed 17 August 2026].
// Supabase, 2023. Auth | supabase docs. [online] supabase.com. Available at: <https://supabase.com/docs/guides/auth> [Accessed 17 August 2026].
// Supabase, 2024. Row level security | supabase docs. [online] Supabase. Available at: <https://supabase.com/docs/guides/database/postgres/row-level-security> [Accessed 17 August 2026].
// Supabase, 2026. Environment variables | supabase Docs. [online] supabase. Available at: <https://supabase.com/docs/guides/functions/secrets> [Accessed 17 August 2026].W3C, 2024. Web content accessibility guidelines (WCAG) 2.2. [online] www.w3.org. W3C. Available at: <https://www.w3.org/TR/WCAG22/> [Accessed 17 August 2026].


package com.example.onsite_mockups.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.network.GoogleRegistrationRequest
import com.example.onsite_mockups.data.network.RetrofitClient
import com.example.onsite_mockups.data.network.SupabaseClient
import com.example.onsite_mockups.data.repository.OnSiteRepository
import com.google.firebase.messaging.FirebaseMessaging
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * ViewModel responsible for user authentication and session management.
 * Interfaces with Supabase Auth for login/logout and manages the current user's profile.
 */
class AuthViewModel : ViewModel() {

    companion object {
        private const val TAG =
            "AuthViewModel"

        // URL handled by the app for Google OAuth callbacks
        const val GOOGLE_REDIRECT_URL =
            "onsite://login-callback"
    }

    private val _currentProfile =
        MutableStateFlow<Profile?>(null)

    /**
     * The profile of the currently authenticated user.
     */
    val currentProfile:
            StateFlow<Profile?> =
        _currentProfile.asStateFlow()

    private val _loginState =
        MutableStateFlow<LoginState>(
            LoginState.Idle
        )

    /**
     * Observable flow of the current login process state.
     */
    val loginState:
            StateFlow<LoginState> =
        _loginState.asStateFlow()

    private val auth =
        SupabaseClient.client.auth

    /**
     * Authenticates a user using email and password.
     */
    fun login(
        email: String,
        password: String,
        onSuccess: (Profile) -> Unit
    ) {
        if (
            email.isBlank() ||
            password.isBlank()
        ) {
            _loginState.value =
                LoginState.Error(
                    "Email and password cannot be empty."
                )

            return
        }

        _loginState.value =
            LoginState.Loading

        viewModelScope.launch {
            try {
                // Sign in with Supabase
                auth.signInWith(
                    Email
                ) {
                    this.email =
                        email.trim()

                    this.password =
                        password
                }

                // Load OnSite profile after successful Supabase auth
                completeAuthenticatedLogin(
                    onSuccess = onSuccess
                )

            } catch (e: Exception) {
                Log.e(
                    TAG,
                    "Email login failed.",
                    e
                )

                _loginState.value =
                    LoginState.Error(
                        e.message
                            ?: "Login failed."
                    )
            }
        }
    }

    /**
     * Starts the Google OAuth sign-in flow.
     */
    fun loginWithGoogle() {
        _loginState.value =
            LoginState.Loading

        viewModelScope.launch {
            try {
                auth.signInWith(
                    Google,
                    redirectUrl =
                        GOOGLE_REDIRECT_URL
                )

                _loginState.value =
                    LoginState.Idle

            } catch (e: Exception) {
                Log.e(
                    TAG,
                    "Google OAuth failed to start.",
                    e
                )

                _loginState.value =
                    LoginState.Error(
                        e.message
                            ?: "Could not start Google sign-in."
                    )
            }
        }
    }

    /**
     * Attempts to log in using an existing Supabase session and biometric verification.
     */
    fun loginWithBiometrics(
        onSuccess: (Profile) -> Unit
    ) {
        _loginState.value =
            LoginState.Loading

        viewModelScope.launch {
            try {
                val user =
                    auth.currentUserOrNull()

                if (user == null) {
                    _loginState.value =
                        LoginState.Error(
                            "Your login session has expired."
                        )

                    return@launch
                }

                val session =
                    auth.currentSessionOrNull()

                if (session == null) {
                    _loginState.value =
                        LoginState.Error(
                            "Your login session has expired."
                        )

                    return@launch
                }

                // Configure Retrofit with the existing access token
                RetrofitClient.setToken(
                    session.accessToken
                )

                val profile =
                    loadProfileForCurrentUser()

                if (profile == null) {
                    _loginState.value =
                        LoginState.Error(
                            "Your OnSite profile could not be found."
                        )

                    return@launch
                }

                finishLogin(
                    profile,
                    onSuccess
                )

            } catch (e: Exception) {

                _loginState.value =
                    LoginState.Error(
                        e.message
                            ?: "Biometric login failed."
                    )
            }
        }
    }

    /**
     * Completes the login process after a successful Google OAuth redirection.
     */
    fun completeGoogleLogin(
        onSuccess: (Profile) -> Unit
    ) {
        _loginState.value =
            LoginState.Loading

        viewModelScope.launch {
            try {
                val user =
                    auth.currentUserOrNull()

                if (user == null) {
                    _loginState.value =
                        LoginState.Error(
                            "Google sign-in did not return an authenticated user."
                        )

                    return@launch
                }

                val session =
                    auth.currentSessionOrNull()

                if (session == null) {
                    _loginState.value =
                        LoginState.Error(
                            "Google authentication session is unavailable."
                        )

                    return@launch
                }

                RetrofitClient.setToken(
                    session.accessToken
                )

                // Try to find an existing OnSite profile for this Google user
                val existingProfile =
                    try {
                        SupabaseClient
                            .client
                            .from("profiles")
                            .select {
                                filter {
                                    eq(
                                        "id",
                                        user.id
                                    )
                                }
                            }
                            .decodeSingle<Profile>()

                    } catch (_: Exception) {
                        null
                    }

                // Use existing profile or create a new one via the API
                val profile =
                    if (existingProfile != null && !existingProfile.fullName.isNullOrBlank()) {
                        existingProfile
                    } else {
                        val fullName =
                            user.email
                                ?.substringBefore("@")
                                ?.replaceFirstChar {
                                    it.uppercase()
                                }
                                ?: "OnSite User"

                        RetrofitClient
                            .apiService
                            .registerGoogleUser(
                                GoogleRegistrationRequest(
                                    fullName =
                                        fullName
                                )
                            )
                    }

                finishLogin(
                    profile =
                        profile,
                    onSuccess =
                        onSuccess
                )

            } catch (e: Exception) {
                Log.e(
                    TAG,
                    "Google login completion failed.",
                    e
                )

                _loginState.value =
                    LoginState.Error(
                        e.message
                            ?: "Google sign-in failed."
                    )
            }
        }
    }

    /**
     * Finishes the authentication process after Supabase sign-in by loading the user profile.
     */
    private fun completeAuthenticatedLogin(
        onSuccess: (Profile) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val profile =
                    loadProfileForCurrentUser()

                if (profile == null) {
                    _loginState.value =
                        LoginState.Error(
                            "Your OnSite profile could not be found. Contact an administrator."
                        )

                    return@launch
                }

                finishLogin(
                    profile =
                        profile,
                    onSuccess =
                        onSuccess
                )

            } catch (e: Exception) {
                Log.e(
                    TAG,
                    "Authenticated login completion failed.",
                    e
                )

                _loginState.value =
                    LoginState.Error(
                        e.message
                            ?: "Login failed."
                    )
            }
        }
    }

    /**
     * Fetches the OnSite profile for the currently authenticated Supabase user.
     */
    private suspend fun loadProfileForCurrentUser(): Profile? {

        val user =
            auth.currentUserOrNull()
                ?: return null

        val session =
            auth.currentSessionOrNull()

        if (session == null) {
            Log.e(
                TAG,
                "No Supabase session available."
            )

            return null
        }

        Log.d(
            TAG,
            "Loading profile for Supabase user: ${user.id}"
        )

        return try {

            // Query the 'profiles' table directly via Postgrest
            val profiles =
                SupabaseClient
                    .client
                    .from("profiles")
                    .select {
                        filter {
                            eq(
                                "id",
                                user.id
                            )
                        }
                    }
                    .decodeList<Profile>()

            Log.d(
                TAG,
                "Profile query returned ${profiles.size} row(s)."
            )

            if (profiles.isEmpty()) {

                Log.e(
                    TAG,
                    "No profile visible to the Supabase client for user ${user.id}. This is likely an RLS policy issue."
                )

                null

            } else {

                profiles.first()
            }

        } catch (e: Exception) {

            Log.e(
                TAG,
                "Could not load profile.",
                e
            )

            null
        }
    }

    /**
     * Finalizes the login process: checks if user is active, sets repository data, and starts FCM registration.
     */
    private fun finishLogin(
        profile: Profile,
        onSuccess: (Profile) -> Unit
    ) {
        if (!profile.isActive) {
            _loginState.value =
                LoginState.Error(
                    "Your OnSite account is inactive."
                )

            return
        }

        val session =
            auth.currentSessionOrNull()

        if (session == null) {
            _loginState.value =
                LoginState.Error(
                    "Authentication session is unavailable."
                )

            return
        }

        // Set the token for subsequent API calls
        RetrofitClient.setToken(
            session.accessToken
        )

        _currentProfile.value =
            profile

        OnSiteRepository
            .setCurrentProfile(
                profile
            )

        // Asynchronously register for push notifications
        registerFcmToken()

        _loginState.value =
            LoginState.Success(
                profile
            )

        onSuccess(profile)
    }

    /**
     * Fetches the FCM token and registers it with the backend.
     */
    private fun registerFcmToken() {
        viewModelScope.launch {
            try {
                val token =
                    FirebaseMessaging
                        .getInstance()
                        .token
                        .await()

                if (token.isBlank()) {
                    return@launch
                }

                OnSiteRepository
                    .registerDeviceToken(
                        token
                    )

                Log.d(
                    TAG,
                    "FCM token registered."
                )

            } catch (e: Exception) {
                Log.e(
                    TAG,
                    "Failed to register FCM token.",
                    e
                )
            }
        }
    }

    /**
     * Logs the user out from Supabase and the local repository.
     */
    fun logout(
        onLoggedOut: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Try to deactivate the device token before signing out
                val token =
                    try {
                        FirebaseMessaging
                            .getInstance()
                            .token
                            .await()
                    } catch (_: Exception) {
                        null
                    }

                if (!token.isNullOrBlank()) {
                    OnSiteRepository
                        .deactivateDeviceToken(
                            token
                        )
                }

                auth.signOut()

            } catch (_: Exception) {
            }

            OnSiteRepository.logout()

            RetrofitClient
                .setToken(null)

            _currentProfile.value =
                null

            _loginState.value =
                LoginState.Idle

            onLoggedOut()
        }
    }
}

sealed interface LoginState {

    object Idle :
        LoginState

    object Loading :
        LoginState

    data class Success(
        val profile: Profile
    ) : LoginState

    data class Error(
        val message: String
    ) : LoginState
}