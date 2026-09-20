package com.example.onsite_mockups.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.models.Site
import com.example.onsite_mockups.data.models.SiteUpdate
import com.example.onsite_mockups.data.repository.OnSiteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {

    private val _sites = MutableStateFlow<List<Site>>(emptyList())
    val sites: StateFlow<List<Site>> = _sites.asStateFlow()

    private val _updates = MutableStateFlow<List<SiteUpdate>>(emptyList())
    val updates: StateFlow<List<SiteUpdate>> = _updates.asStateFlow()

    private val _foremen = MutableStateFlow<List<Profile>>(emptyList())
    val foremen: StateFlow<List<Profile>> = _foremen.asStateFlow()

    private val _selectedUpdate = MutableStateFlow<SiteUpdate?>(null)
    val selectedUpdate: StateFlow<SiteUpdate?> = _selectedUpdate.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    fun getForemanForUpdate(update: SiteUpdate?): Profile? {
        return _foremen.value.find { it.id == update?.foremanId }
    }

    fun loadAdminData() {
        viewModelScope.launch {
            try {
                _sites.value = OnSiteRepository.getSites()
                _updates.value = OnSiteRepository.getSiteUpdates()
                _foremen.value = OnSiteRepository.getProfiles("foreman")
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to load admin data."
            }
        }
    }

    fun selectUpdate(updateId: String) {
        _selectedUpdate.value = _updates.value.find {
            it.id == updateId || it.siteId == updateId
        }
    }

    fun addSite(name: String, address: String) {
        if (name.isBlank() || address.isBlank()) {
            _errorMessage.value = "Site name and address are required."
            return
        }

        viewModelScope.launch {
            _isSaving.value = true
            _errorMessage.value = null
            _successMessage.value = null

            try {
                OnSiteRepository.addSite(
                    name = name.trim(),
                    address = address.trim()
                )

                _sites.value = OnSiteRepository.getSites()
                _successMessage.value = "Site added successfully."
            } catch (e: Exception) {
                _errorMessage.value =
                    e.message ?: "Failed to add the site."
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun addForeman(fullName: String, email: String) {
        if (fullName.isBlank() || email.isBlank()) {
            _errorMessage.value = "Foreman name and email are required."
            return
        }

        viewModelScope.launch {
            _isSaving.value = true
            _errorMessage.value = null
            _successMessage.value = null

            try {
                OnSiteRepository.addProfile(
                    fullName = fullName.trim(),
                    role = "foreman",
                    email = email.trim()
                )

                _foremen.value = OnSiteRepository.getProfiles("foreman")
                _successMessage.value = "Foreman added successfully."
            } catch (e: Exception) {
                _errorMessage.value =
                    e.message ?: "Failed to add the foreman."
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun clearMessages() {
        _errorMessage.value = null
        _successMessage.value = null
    }
}