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
