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

/**
 * ViewModel for Administrator functions.
 * Manages the global state of sites, foremen, and assignments.
 */
class AdminViewModel : ViewModel() {

    private val _sites =
        MutableStateFlow<List<Site>>(emptyList())

    /**
     * List of all construction sites.
     */
    val sites: StateFlow<List<Site>> =
        _sites.asStateFlow()

    private val _updates =
        MutableStateFlow<List<SiteUpdate>>(emptyList())

    /**
     * List of all submitted site updates.
     */
    val updates: StateFlow<List<SiteUpdate>> =
        _updates.asStateFlow()

    private val _foremen =
        MutableStateFlow<List<Profile>>(emptyList())

    /**
     * List of all user profiles with the 'foreman' role.
     */
    val foremen: StateFlow<List<Profile>> =
        _foremen.asStateFlow()

    private val _assignments =
        MutableStateFlow<List<SiteForemanAssignment>>(
            emptyList()
        )

    /**
     * List of all foreman-to-site assignments.
     */
    val assignments:
            StateFlow<List<SiteForemanAssignment>> =
        _assignments.asStateFlow()

    private val _selectedUpdate =
        MutableStateFlow<SiteUpdate?>(null)

    /**
     * Currently selected update for detailed viewing.
     */
    val selectedUpdate:
            StateFlow<SiteUpdate?> =
        _selectedUpdate.asStateFlow()

    private val _isSaving =
        MutableStateFlow(false)

    /**
     * True if a write operation (add/update) is in progress.
     */
    val isSaving: StateFlow<Boolean> =
        _isSaving.asStateFlow()

    private val _isLoading =
        MutableStateFlow(false)

    /**
     * True if data is being fetched from the server.
     */
    val isLoading: StateFlow<Boolean> =
        _isLoading.asStateFlow()

    private val _errorMessage =
        MutableStateFlow<String?>(null)

    /**
     * Contains the last error message, or null if none.
     */
    val errorMessage:
            StateFlow<String?> =
        _errorMessage.asStateFlow()

    private val _successMessage =
        MutableStateFlow<String?>(null)

    /**
     * Contains a success message after an action, or null if none.
     */
    val successMessage:
            StateFlow<String?> =
        _successMessage.asStateFlow()

    /**
     * Finds the profile of the foreman who submitted the given update.
     */
    fun getForemanForUpdate(
        update: SiteUpdate?
    ): Profile? {
        return _foremen.value.find {
            it.id == update?.foremanId
        }
    }

    /**
     * Filters assignments for a specific site.
     */
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

    /**
     * Checks if a specific foreman is already assigned to a site.
     */
    fun isForemanAssignedToSite(
        siteId: String,
        foremanId: String
    ): Boolean {
        return _assignments.value.any {
            it.siteId == siteId &&
                    it.foremanId == foremanId
        }
    }

    /**
     * Loads all administrator data (sites, foremen, assignments, updates) in parallel.
     */
    fun loadAdminData() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            var firstError: String? = null

            try {
                _sites.value =
                    OnSiteRepository.getSites()
            } catch (e: Exception) {
                firstError =
                    e.message
                        ?: "Failed to load sites."
            }

            try {
                _foremen.value =
                    OnSiteRepository
                        .getProfiles("foreman")
            } catch (e: Exception) {
                if (firstError == null) {
                    firstError =
                        e.message
                            ?: "Failed to load foremen."
                }
            }

            try {
                _assignments.value =
                    OnSiteRepository
                        .getAssignments()
            } catch (e: Exception) {
                if (firstError == null) {
                    firstError =
                        e.message
                            ?: "Failed to load assignments."
                }
            }

            try {
                _updates.value =
                    OnSiteRepository
                        .getSiteUpdates()
            } catch (e: Exception) {
                if (firstError == null) {
                    firstError =
                        e.message
                            ?: "Failed to load site updates."
                }
            }

            _errorMessage.value = firstError
            _isLoading.value = false
        }
    }

    /**
     * Refreshes sites, foremen, and assignments.
     */
    fun refreshAssignmentData() {
        viewModelScope.launch {
            _errorMessage.value = null

            try {
                _sites.value =
                    OnSiteRepository.getSites()

                _foremen.value =
                    OnSiteRepository
                        .getProfiles("foreman")

                _assignments.value =
                    OnSiteRepository
                        .getAssignments()
            } catch (e: Exception) {
                _errorMessage.value =
                    e.message
                        ?: "Failed to refresh sites and foremen."
            }
        }
    }

    /**
     * Sets the currently selected update by ID.
     */
    fun selectUpdate(
        updateId: String
    ) {
        _selectedUpdate.value =
            _updates.value.find {
                it.id == updateId ||
                        it.siteId == updateId
            }
    }

    /**
     * Creates a new construction site.
     */
    fun addSite(
        name: String,
        address: String
    ) {
        if (
            name.isBlank() ||
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

    /**
     * Updates an existing site's basic info and status.
     */
    fun updateSite(
        id: String?,
        name: String,
        address: String,
        isActive: Boolean
    ) {
        if (id.isNullOrBlank()) {
            _errorMessage.value =
                "The selected site has no ID."
            return
        }

        if (
            name.isBlank() ||
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
                OnSiteRepository.updateSite(
                    id = id,
                    name = name.trim(),
                    address = address.trim(),
                    isActive = isActive
                )

                _sites.value =
                    OnSiteRepository.getSites()

                _successMessage.value =
                    "Site updated successfully."
            } catch (e: Exception) {
                _errorMessage.value =
                    e.message
                        ?: "Failed to update the site."
            } finally {
                _isSaving.value = false
            }
        }
    }

    /**
     * Creates a new user profile with the foreman role.
     */
    fun addForeman(
        fullName: String,
        email: String
    ) {
        if (
            fullName.isBlank() ||
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
                val response =
                    OnSiteRepository.addProfile(
                        fullName = fullName.trim(),
                        role = "foreman",
                        email = email.trim()
                    )

                _foremen.value =
                    OnSiteRepository
                        .getProfiles("foreman")

                _successMessage.value =
                    "Foreman added successfully.\n\n" +
                            "Name: ${response.profile.fullName}\n" +
                            "Email: ${response.profile.email}\n\n" +
                            "Temporary password: " +
                            "${response.temporaryPassword}\n\n" +
                            "Give this password to the foreman. " +
                            "They can use it to log in."
            } catch (e: Exception) {
                _errorMessage.value =
                    e.message
                        ?: "Failed to add the foreman."
            } finally {
                _isSaving.value = false
            }
        }
    }

    /**
     * Links a foreman to a site.
     */
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

        if (
            isForemanAssignedToSite(
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
                    OnSiteRepository
                        .getAssignments()

                val assignedForeman =
                    _foremen.value.find {
                        it.id == foremanId
                    }

                val assignedSite =
                    _sites.value.find {
                        it.id == siteId
                    }

                _successMessage.value =
                    "Foreman ${assignedForeman?.fullName ?: ""} " +
                            "assigned to " +
                            "${assignedSite?.name ?: "site"}."
            } catch (e: Exception) {
                _errorMessage.value =
                    e.message
                        ?: "Failed to assign foreman to site."
            } finally {
                _isSaving.value = false
            }
        }
    }

    /**
     * Unlinks a foreman from a site.
     */
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
                    OnSiteRepository
                        .getAssignments()

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

    /**
     * Clears any active error or success notifications.
     */
    fun clearMessages() {
        _errorMessage.value = null
        _successMessage.value = null
    }
}
