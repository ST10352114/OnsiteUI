package com.example.onsite_mockups.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onsite_mockups.data.models.PhotoInput
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.models.Site
import com.example.onsite_mockups.data.models.SiteUpdate
import com.example.onsite_mockups.data.repository.OnSiteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Foreman-specific functions.
 * Manages assigned sites and the submission of daily progress reports.
 */
class ForemanViewModel : ViewModel() {

    private val _foremanName =
        MutableStateFlow("Foreman")

    /**
     * Display name of the current foreman.
     */
    val foremanName:
            StateFlow<String> =
        _foremanName.asStateFlow()

    private val _sites =
        MutableStateFlow<List<Site>>(
            emptyList()
        )

    /**
     * List of sites assigned to this foreman.
     */
    val sites:
            StateFlow<List<Site>> =
        _sites.asStateFlow()

    private val _selectedSite =
        MutableStateFlow<Site?>(null)

    /**
     * Currently selected site for report submission.
     */
    val selectedSite:
            StateFlow<Site?> =
        _selectedSite.asStateFlow()

    private val _updates =
        MutableStateFlow<List<SiteUpdate>>(
            emptyList()
        )

    /**
     * History of updates submitted by this foreman.
     */
    val updates:
            StateFlow<List<SiteUpdate>> =
        _updates.asStateFlow()

    private val _todayUpdate =
        MutableStateFlow<SiteUpdate?>(null)

    /**
     * Today's progress report for the selected site, if already submitted.
     */
    val todayUpdate:
            StateFlow<SiteUpdate?> =
        _todayUpdate.asStateFlow()

    private val _isLoadingTodayUpdate =
        MutableStateFlow(false)

    /**
     * True if checking for today's existing update.
     */
    val isLoadingTodayUpdate:
            StateFlow<Boolean> =
        _isLoadingTodayUpdate.asStateFlow()

    private val _isSubmitting =
        MutableStateFlow(false)

    /**
     * True if a report submission is in progress.
     */
    val isSubmitting:
            StateFlow<Boolean> =
        _isSubmitting.asStateFlow()

    private val _errorMessage =
        MutableStateFlow<String?>(null)

    /**
     * Error message for UI display.
     */
    val errorMessage:
            StateFlow<String?> =
        _errorMessage.asStateFlow()

    /**
     * Initializes the foreman's profile data in the ViewModel and Repository.
     */
    fun setForemanProfile(
        profile: Profile?
    ) {

        _foremanName.value =
            profile
                ?.fullName
                ?.trim()
                ?.takeIf {
                    it.isNotBlank()
                }
                ?: "Foreman"

        OnSiteRepository.setCurrentProfile(
            profile
        )
    }

    /**
     * Loads assigned sites and update history for the foreman.
     */
    fun loadForemanData() {

        viewModelScope.launch {

            try {

                _sites.value =
                    OnSiteRepository.getSites()

                _updates.value =
                    OnSiteRepository.getSiteUpdates()

            } catch (e: Exception) {

                _errorMessage.value =
                    e.message
                        ?: "Failed to load foreman data."
            }
        }
    }

    /**
     * Selects a site to view details or submit an update.
     */
    fun selectSite(
        siteId: String
    ) {

        val site =
            _sites.value.find {
                it.id == siteId
            }

        _selectedSite.value =
            site

        _todayUpdate.value =
            null

        _errorMessage.value =
            null
    }

    /**
     * Checks if a report has already been submitted for the given site today.
     * Called synchronously from a LaunchedEffect in the UI.
     */
    suspend fun loadTodayUpdate(
        siteId: String
    ): SiteUpdate? {

        if (siteId.isBlank()) {

            _todayUpdate.value =
                null

            return null
        }

        _isLoadingTodayUpdate.value =
            true

        _errorMessage.value =
            null

        _todayUpdate.value =
            null

        return try {

            val existingUpdate =
                OnSiteRepository
                    .getTodaySiteUpdate(
                        siteId
                    )

            _todayUpdate.value =
                existingUpdate

            existingUpdate

        } catch (e: Exception) {

            _todayUpdate.value =
                null

            _errorMessage.value =
                e.message
                    ?: "Failed to load today's update."

            null

        } finally {

            _isLoadingTodayUpdate.value =
                false
        }
    }

    /**
     * Submits a new daily progress report for the selected site.
     */
    fun submitDailyUpdate(
        staffNames: String,
        powerTools: String,
        plantMachines: String,
        photos: List<PhotoInput>,
        notes: String?,
        onSubmitDone: () -> Unit
    ) {

        val currentSiteId =
            _selectedSite.value?.id

        if (currentSiteId.isNullOrBlank()) {

            _errorMessage.value =
                "No site has been selected."

            return
        }

        viewModelScope.launch {

            _isSubmitting.value =
                true

            _errorMessage.value =
                null

            try {

                // Submit the update to the repository/API
                val response =
                    OnSiteRepository
                        .addSiteUpdate(
                            siteId =
                                currentSiteId,
                            staffNames =
                                staffNames,
                            powerTools =
                                powerTools,
                            plantMachines =
                                plantMachines,
                            photos =
                                photos,
                            notes =
                                notes
                        )

                _todayUpdate.value =
                    response

                // Refresh the list of all updates
                _updates.value =
                    OnSiteRepository
                        .getSiteUpdates()

                onSubmitDone()

            } catch (e: Exception) {

                _errorMessage.value =
                    e.message
                        ?: "Failed to submit daily update."

            } finally {

                _isSubmitting.value =
                    false
            }
        }
    }

    /**
     * Clears the current error message.
     */
    fun clearError() {
        _errorMessage.value = null
    }
}
