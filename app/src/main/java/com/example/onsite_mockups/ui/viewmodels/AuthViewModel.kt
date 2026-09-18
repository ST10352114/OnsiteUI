package com.example.onsite_mockups.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.repository.OnSiteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _currentProfile = MutableStateFlow<Profile?>(null)
    val currentProfile: StateFlow<Profile?> = _currentProfile.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(username: String, onSuccess: (Profile) -> Unit) {
        if (username.isBlank()) {
            _loginState.value = LoginState.Error("Username cannot be empty")
            return
        }
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            val profile = OnSiteRepository.login(username)
            if (profile != null) {
                _currentProfile.value = profile
                _loginState.value = LoginState.Success(profile)
                onSuccess(profile)
            } else {
                _loginState.value = LoginState.Error("Profile not found")
            }
        }
    }

    fun logout(onLoggedOut: () -> Unit) {
        OnSiteRepository.logout()
        _currentProfile.value = null
        _loginState.value = LoginState.Idle
        onLoggedOut()
    }
}

sealed interface LoginState {
    object Idle : LoginState
    object Loading : LoginState
    data class Success(val profile: Profile) : LoginState
    data class Error(val message: String) : LoginState
}
