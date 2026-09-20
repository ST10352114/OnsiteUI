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

class ForemanViewModel : ViewModel() {

    private val _foremanName =
        MutableStateFlow("Foreman")

    val foremanName: StateFlow<String> =
        _foremanName.asStateFlow()

    private val _sites =
        MutableStateFlow<List<Site>>(emptyList())

    val sites: StateFlow<List<Site>> =
        _sites.asStateFlow()

    private val _selectedSite =
        MutableStateFlow<Site?>(null)

    val selectedSite: StateFlow<Site?> =
        _selectedSite.asStateFlow()

    private val _updates =
        MutableStateFlow<List<SiteUpdate>>(emptyList())

    val updates: StateFlow<List<SiteUpdate>> =
        _updates.asStateFlow()

    fun setForemanProfile(profile: Profile?) {
        _foremanName.value =
            profile?.fullName?.trim()
                ?.takeIf { it.isNotBlank() }
                ?: "Foreman"
    }

    fun loadForemanData() {
        viewModelScope.launch {
            _sites.value =
                OnSiteRepository.getSites()

            _updates.value =
                OnSiteRepository.getSiteUpdates()
        }
    }

    fun selectSite(siteId: String) {
        _selectedSite.value =
            _sites.value.find {
                it.id == siteId
            }
    }

    fun submitDailyUpdate(
        headcount: Int,
        staffNames: String,
        powerTools: String,
        plantMachines: String,
        onSubmitDone: () -> Unit
    ) {
        val currentSiteId =
            _selectedSite.value?.id
                ?: "ridgeview"

        viewModelScope.launch {
            OnSiteRepository.addSiteUpdate(
                siteId = currentSiteId,
                headcount = headcount,
                staffNames = staffNames,
                powerTools = powerTools,
                plantMachines = plantMachines
            )

            loadForemanData()

            onSubmitDone()
        }
    }
}