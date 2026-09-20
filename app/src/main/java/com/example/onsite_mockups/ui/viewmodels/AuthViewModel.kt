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

class AuthViewModel : ViewModel() {

    companion object {
        private const val TAG =
            "AuthViewModel"

        const val GOOGLE_REDIRECT_URL =
            "onsite://login-callback"
    }

    private val _currentProfile =
        MutableStateFlow<Profile?>(null)

    val currentProfile:
            StateFlow<Profile?> =
        _currentProfile.asStateFlow()

    private val _loginState =
        MutableStateFlow<LoginState>(
            LoginState.Idle
        )

    val loginState:
            StateFlow<LoginState> =
        _loginState.asStateFlow()

    private val auth =
        SupabaseClient.client.auth

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
                auth.signInWith(
                    Email
                ) {
                    this.email =
                        email.trim()

                    this.password =
                        password
                }

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

                val profile =
                    if (existingProfile != null) {
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

    private suspend fun loadProfileForCurrentUser():
            Profile? {

        val user =
            auth.currentUserOrNull()
                ?: return null

        val session =
            auth.currentSessionOrNull()

        if (session == null) {
            return null
        }

        RetrofitClient.setToken(
            session.accessToken
        )

        return try {
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

        } catch (e: Exception) {
            Log.e(
                TAG,
                "Could not load profile.",
                e
            )

            null
        }
    }

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

        RetrofitClient.setToken(
            session.accessToken
        )

        _currentProfile.value =
            profile

        OnSiteRepository
            .setCurrentProfile(
                profile
            )

        registerFcmToken()

        _loginState.value =
            LoginState.Success(
                profile
            )

        onSuccess(profile)
    }

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

    fun logout(
        onLoggedOut: () -> Unit
    ) {
        viewModelScope.launch {
            try {
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