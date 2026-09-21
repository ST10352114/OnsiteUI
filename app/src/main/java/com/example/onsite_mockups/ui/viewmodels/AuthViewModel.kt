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