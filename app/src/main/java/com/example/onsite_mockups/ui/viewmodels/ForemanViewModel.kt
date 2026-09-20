package com.example.onsite_mockups.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.models.Site
import com.example.onsite_mockups.data.models.SiteUpdate
import com.example.onsite_mockups.data.network.PhotoInput
import com.example.onsite_mockups.data.repository.OnSiteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForemanViewModel : ViewModel() {

    private val _foremanName =
        MutableStateFlow("Foreman")

    val foremanName:
            StateFlow<String> =
        _foremanName.asStateFlow()

    private val _sites =
        MutableStateFlow<List<Site>>(
            emptyList()
        )

    val sites:
            StateFlow<List<Site>> =
        _sites.asStateFlow()

    private val _selectedSite =
        MutableStateFlow<Site?>(null)

    val selectedSite:
            StateFlow<Site?> =
        _selectedSite.asStateFlow()

    private val _updates =
        MutableStateFlow<List<SiteUpdate>>(
            emptyList()
        )

    val updates:
            StateFlow<List<SiteUpdate>> =
        _updates.asStateFlow()

    private val _todayUpdate =
        MutableStateFlow<SiteUpdate?>(null)

    val todayUpdate:
            StateFlow<SiteUpdate?> =
        _todayUpdate.asStateFlow()

    private val _isLoadingTodayUpdate =
        MutableStateFlow(false)

    val isLoadingTodayUpdate:
            StateFlow<Boolean> =
        _isLoadingTodayUpdate.asStateFlow()

    private val _isSubmitting =
        MutableStateFlow(false)

    val isSubmitting:
            StateFlow<Boolean> =
        _isSubmitting.asStateFlow()

    private val _errorMessage =
        MutableStateFlow<String?>(null)

    val errorMessage:
            StateFlow<String?> =
        _errorMessage.asStateFlow()

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

    /*
     * Loads today's update synchronously from the
     * calling coroutine.
     *
     * DailyUpdateFormScreen calls this from LaunchedEffect,
     * so the screen waits until the API request has actually
     * completed before populating the form.
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

    fun clearError() {
        _errorMessage.value = null
    }
}