package com.example.onsite_mockups.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.network.RetrofitClient
import com.example.onsite_mockups.data.network.SupabaseClient
import com.example.onsite_mockups.data.repository.OnSiteRepository
import com.google.firebase.messaging.FirebaseMessaging
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

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
                    "Email and password cannot be empty"
                )

            return
        }

        _loginState.value =
            LoginState.Loading

        Log.d(
            "AuthViewModel",
            "Attempting login."
        )

        viewModelScope.launch {

            try {

                auth.signInWith(
                    Email
                ) {
                    this.email =
                        email

                    this.password =
                        password
                }

                val session =
                    auth.currentSessionOrNull()

                session?.let { s ->

                    Log.d(
                        "AuthViewModel",
                        "Login successful."
                    )

                    RetrofitClient
                        .setToken(
                            s.accessToken
                        )
                }

                val user =
                    auth.currentUserOrNull()

                Log.d(
                    "AuthViewModel",
                    "Supabase user authenticated."
                )

                val profile =
                    if (user != null) {

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

                        } catch (e: Exception) {

                            Log.e(
                                "AuthViewModel",
                                "Error fetching profile.",
                                e
                            )

                            Profile(
                                id =
                                    user.id,

                                fullName =
                                    email
                                        .substringBefore(
                                            "@"
                                        ),

                                role =
                                    if (
                                        email.contains(
                                            "admin"
                                        )
                                    ) {
                                        "admin"
                                    } else {
                                        "foreman"
                                    },

                                email =
                                    email,

                                phone =
                                    null,

                                isActive =
                                    true
                            )
                        }

                    } else {
                        null
                    }

                if (profile != null) {

                    Log.d(
                        "AuthViewModel",
                        "Profile loaded: ${profile.fullName} (${profile.role})"
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

                    onSuccess(
                        profile
                    )

                } else {

                    Log.w(
                        "AuthViewModel",
                        "Profile not found."
                    )

                    _loginState.value =
                        LoginState.Error(
                            "User not found"
                        )
                }

            } catch (e: Exception) {

                Log.e(
                    "AuthViewModel",
                    "Login error.",
                    e
                )

                _loginState.value =
                    LoginState.Error(
                        e.message
                            ?: "Login failed"
                    )
            }
        }
    }

    private fun registerFcmToken() {

        viewModelScope.launch {

            try {

                val token =
                    FirebaseMessaging
                        .getInstance()
                        .token
                        .await()

                if (
                    token.isBlank()
                ) {
                    return@launch
                }

                OnSiteRepository
                    .registerDeviceToken(
                        token
                    )

                Log.d(
                    "AuthViewModel",
                    "FCM token registered with API."
                )

            } catch (e: Exception) {

                Log.e(
                    "AuthViewModel",
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

                if (
                    !token.isNullOrBlank()
                ) {

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
                .setToken(
                    null
                )

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