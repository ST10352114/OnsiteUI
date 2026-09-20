package com.example.onsite_mockups.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.models.Site
import com.example.onsite_mockups.data.models.SiteForemanAssignment
import com.example.onsite_mockups.data.models.SiteUpdate
import com.example.onsite_mockups.data.repository.OnSiteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {

    private val _sites =
        MutableStateFlow<List<Site>>(emptyList())

    val sites: StateFlow<List<Site>> =
        _sites.asStateFlow()

    private val _updates =
        MutableStateFlow<List<SiteUpdate>>(emptyList())

    val updates: StateFlow<List<SiteUpdate>> =
        _updates.asStateFlow()

    private val _foremen =
        MutableStateFlow<List<Profile>>(emptyList())

    val foremen: StateFlow<List<Profile>> =
        _foremen.asStateFlow()

    private val _assignments =
        MutableStateFlow<List<SiteForemanAssignment>>(emptyList())

    val assignments: StateFlow<List<SiteForemanAssignment>> =
        _assignments.asStateFlow()

    private val _selectedUpdate =
        MutableStateFlow<SiteUpdate?>(null)

    val selectedUpdate: StateFlow<SiteUpdate?> =
        _selectedUpdate.asStateFlow()

    private val _isSaving =
        MutableStateFlow(false)

    val isSaving: StateFlow<Boolean> =
        _isSaving.asStateFlow()

    private val _errorMessage =
        MutableStateFlow<String?>(null)

    val errorMessage: StateFlow<String?> =
        _errorMessage.asStateFlow()

    private val _successMessage =
        MutableStateFlow<String?>(null)

    val successMessage: StateFlow<String?> =
        _successMessage.asStateFlow()

    fun getForemanForUpdate(
        update: SiteUpdate?
    ): Profile? {
        return _foremen.value.find {
            it.id == update?.foremanId
        }
    }

    fun getAssignmentsForSite(
        siteId: String?
    ): List<SiteForemanAssignment> {
        if (siteId == null) {
            return emptyList()
        }

        return _assignments.value.filter {
            it.siteId == siteId
        }
    }

    fun isForemanAssignedToSite(
        siteId: String,
        foremanId: String
    ): Boolean {
        return _assignments.value.any {
            it.siteId == siteId &&
                    it.foremanId == foremanId
        }
    }

    fun loadAdminData() {
        viewModelScope.launch {

            _errorMessage.value = null

            try {
                _sites.value =
                    OnSiteRepository.getSites()
            } catch (e: Exception) {
                _errorMessage.value =
                    e.message ?: "Failed to load sites."
            }

            try {
                _updates.value =
                    OnSiteRepository.getSiteUpdates()
            } catch (e: Exception) {
                if (_errorMessage.value == null) {
                    _errorMessage.value =
                        e.message
                            ?: "Failed to load site updates."
                }
            }

            try {
                _foremen.value =
                    OnSiteRepository.getProfiles("foreman")
            } catch (e: Exception) {
                if (_errorMessage.value == null) {
                    _errorMessage.value =
                        e.message
                            ?: "Failed to load foremen."
                }
            }

            try {
                _assignments.value =
                    OnSiteRepository.getAssignments()
            } catch (e: Exception) {
                if (_errorMessage.value == null) {
                    _errorMessage.value =
                        e.message
                            ?: "Failed to load site assignments."
                }
            }
        }
    }

    fun selectUpdate(
        updateId: String
    ) {
        _selectedUpdate.value =
            _updates.value.find {
                it.id == updateId ||
                        it.siteId == updateId
            }
    }

    fun addSite(
        name: String,
        address: String
    ) {
        if (name.isBlank() ||
            address.isBlank()
        ) {
            _errorMessage.value =
                "Site name and address are required."
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

                _sites.value =
                    OnSiteRepository.getSites()

                _successMessage.value =
                    "Site added successfully."
            } catch (e: Exception) {
                _errorMessage.value =
                    e.message
                        ?: "Failed to add the site."
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun addForeman(
        fullName: String,
        email: String
    ) {
        if (fullName.isBlank() ||
            email.isBlank()
        ) {
            _errorMessage.value =
                "Foreman name and email are required."
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

                _foremen.value =
                    OnSiteRepository.getProfiles("foreman")

                _successMessage.value =
                    "Foreman added successfully."
            } catch (e: Exception) {
                _errorMessage.value =
                    e.message
                        ?: "Failed to add the foreman."
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun assignForeman(
        siteId: String?,
        foremanId: String?
    ) {
        if (siteId.isNullOrBlank()) {
            _errorMessage.value =
                "Please select a site."
            return
        }

        if (foremanId.isNullOrBlank()) {
            _errorMessage.value =
                "Please select a foreman."
            return
        }

        if (isForemanAssignedToSite(
                siteId,
                foremanId
            )
        ) {
            _errorMessage.value =
                "This foreman is already assigned to this site."
            return
        }

        viewModelScope.launch {
            _isSaving.value = true
            _errorMessage.value = null
            _successMessage.value = null

            try {
                OnSiteRepository.assignForemanToSite(
                    siteId = siteId,
                    foremanId = foremanId
                )

                _assignments.value =
                    OnSiteRepository.getAssignments()

                val assignedForeman =
                    _foremen.value.find {
                        it.id == foremanId
                    }

                val assignedSite =
                    _sites.value.find {
                        it.id == siteId
                    }

                _successMessage.value =
                    "Foreman ${assignedForeman?.fullName ?: ""} assigned to ${assignedSite?.name ?: "site"}."
            } catch (e: Exception) {
                _errorMessage.value =
                    e.message
                        ?: "Failed to assign foreman to site."
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun removeAssignment(
        siteId: String,
        foremanId: String
    ) {
        viewModelScope.launch {
            _isSaving.value = true
            _errorMessage.value = null
            _successMessage.value = null

            try {
                OnSiteRepository.removeForemanFromSite(
                    siteId = siteId,
                    foremanId = foremanId
                )

                _assignments.value =
                    OnSiteRepository.getAssignments()

                _successMessage.value =
                    "Foreman assignment removed."
            } catch (e: Exception) {
                _errorMessage.value =
                    e.message
                        ?: "Failed to remove assignment."
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