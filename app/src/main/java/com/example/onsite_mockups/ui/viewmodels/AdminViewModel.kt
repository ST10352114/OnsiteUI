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

    fun loadAdminData() {
        viewModelScope.launch {
            _sites.value = OnSiteRepository.getSites()
            _updates.value = OnSiteRepository.getSiteUpdates()
            _foremen.value = OnSiteRepository.getProfiles("foreman")
        }
    }

    fun selectUpdate(updateId: String) {
        _selectedUpdate.value = _updates.value.find { it.id == updateId || it.siteId == updateId }
    }

    fun addSite(name: String, address: String) {
        viewModelScope.launch {
            OnSiteRepository.addSite(name, address)
            loadAdminData()
        }
    }

    fun addForeman(fullName: String, email: String) {
        viewModelScope.launch {
            OnSiteRepository.addProfile(fullName, "foreman", email)
            loadAdminData()
        }
    }
}
