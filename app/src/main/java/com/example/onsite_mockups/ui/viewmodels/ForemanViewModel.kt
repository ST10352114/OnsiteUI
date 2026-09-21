//Reference list
// Android Developers, 2019. Save data in a local database using room  |  android developers. [online] Android Developers. Available at: <https://developer.android.com/training/data-storage/room> [Accessed 17 August 2026].
// Android Developers, n.d. App architecture: Data layer - persistent work with WorkManager - android developers | background work. [online] Android Developers. Available at: <https://developer.android.com/develop/background-work/background-tasks/persistent> [Accessed 17 August 2026].
// Android Developers, n.d. BiometricPrompt. [online] Android Developers. Available at: <https://developer.android.com/reference/android/hardware/biometrics/BiometricPrompt> [Accessed 17 August 2026].
// Android Developers, n.d. Material design 3 in compose | jetpack compose. [online] Android Developers. Available at: <https://developer.android.com/develop/ui/compose/designsystems/material3> [Accessed 17 August 2026].
// Authgear, 2025. Login & signup UX: The 2025 guide to best practices (examples & tips). [online] Authgear. Available at: <https://www.authgear.com/post/login-signup-ux-guide/> [Accessed 23 August 2026].
// Bennett, T., 2024. Direct database access vs. REST APIs: Compare application activity. [online] blog.dreamfactory.com. Available at: <https://blog.dreamfactory.com/direct-database-access-vs-rest-apis-pros-and-cons-for-application-connectivity> [Accessed 17 August 2026].
// Cloudflare, 2024. What is rate limiting? | Rate limiting and bots. [online] Cloudflare.com. Available at: <https://www.cloudflare.com/learning/bots/what-is-rate-limiting/> [Accessed 23 August 2026].
// Firebase, 2026. Get started with firebase cloud messaging in android apps. [online] Firebase. Available at: <https://firebase.google.com/docs/cloud-messaging/android/get-started> [Accessed 17 August 2026].
// InEight, 2023. 8 Must-haves for a construction management platform. [online] InEight. Available at: <https://ineight.com/blog/8-must-haves-for-a-construction-management-platform/> [Accessed 17 August 2026].
// Kitch, B., 2024. How to create an agile project plan for software development. [online] Mural.co. Available at: <https://www.mural.co/blog/how-to-create-an-agile-project-plan> [Accessed 17 August 2026].
// Kohler, T., 2022. Autonomy, relatedness, and competence in UX design. [online] Nielsen Norman Group. Available at: <https://www.nngroup.com/articles/autonomy-relatedness-competence/> [Accessed 17 August 2026].
// PostgREST, 2017. Pagination and count. [online] PostgREST 16. Available at: <https://docs.postgrest.org/en/stable/references/api/pagination_count.html> [Accessed 17 August 2026].
// QuickBooks, 2026. What is data export? Meaning & process in 2025 | QuickBooks. [online] Intuit.com. Available at: <https://quickbooks.intuit.com/r/bookkeeping/data-export/> [Accessed 23 August 2026].
// Render, n.d. Cloud application hosting for developers | render. [online] Cloud Application Hosting for Developers | Render. Available at: <https://render.com/> [Accessed 17 August 2026].
// Softbiz, 2026. Why business logic belongs on the server, not the frontend. [online] Softbiz. Available at: <https://www.softbiz.com/technology/backend-and-api-development/why-business-logic-belongs-on-the-server-not-the-frontend> [Accessed 17 August 2026].
// Supabase, 2023. Auth | supabase docs. [online] supabase.com. Available at: <https://supabase.com/docs/guides/auth> [Accessed 17 August 2026].
// Supabase, 2024. Row level security | supabase docs. [online] Supabase. Available at: <https://supabase.com/docs/guides/database/postgres/row-level-security> [Accessed 17 August 2026].
// Supabase, 2026. Environment variables | supabase Docs. [online] supabase. Available at: <https://supabase.com/docs/guides/functions/secrets> [Accessed 17 August 2026].W3C, 2024. Web content accessibility guidelines (WCAG) 2.2. [online] www.w3.org. W3C. Available at: <https://www.w3.org/TR/WCAG22/> [Accessed 17 August 2026].


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
