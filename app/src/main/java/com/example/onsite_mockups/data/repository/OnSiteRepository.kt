package com.example.onsite_mockups.data.repository
import com.example.onsite_mockups.data.models.DeviceTokenRequest
import com.example.onsite_mockups.data.models.NotificationModel
import com.example.onsite_mockups.data.models.NotificationPreferenceRequest
import com.example.onsite_mockups.data.models.NotificationPreferences
import android.util.Log
import com.example.onsite_mockups.data.models.AssignmentRequest
import com.example.onsite_mockups.data.models.CreateProfileResponse
import com.example.onsite_mockups.data.models.PhotoInput
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.models.Site
import com.example.onsite_mockups.data.models.SiteEditRequest
import com.example.onsite_mockups.data.models.SiteForemanAssignment
import com.example.onsite_mockups.data.models.SiteUpdate
import com.example.onsite_mockups.data.network.OnSiteApiService
import com.example.onsite_mockups.data.network.RetrofitClient
import com.example.onsite_mockups.data.network.SiteUpdateRequest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Repository class that acts as the single source of truth for all data in the application.
 * It manages communication between the UI (ViewModels) and the network services (Retrofit and Supabase).
 */
object OnSiteRepository {

    private val api =
        RetrofitClient.apiService

    /**
     * The profile of the currently logged-in user.
     */
    var currentProfile: Profile? = null
        private set

    // In-memory caches for data
    private val profilesList =
        mutableListOf<Profile>()

    private val sitesList =
        mutableListOf<Site>()

    private val assignmentsList =
        mutableListOf<SiteForemanAssignment>()

    private val siteUpdatesList =
        mutableListOf<SiteUpdate>()

    /**
     * Registers the current device's FCM token with the backend.
     */
    suspend fun registerDeviceToken(
        token: String
    ) {
        if (token.isBlank()) {
            return
        }

        try {
            api.registerDeviceToken(
                DeviceTokenRequest(
                    token = token,
                    platform = "android"
                )
            )

            Log.d(
                "OnSiteRepository",
                "FCM device token registered."
            )

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Failed to register FCM device token.",
                e
            )

            throw e
        }
    }

    /**
     * Deactivates the given FCM token in the backend.
     */
    suspend fun deactivateDeviceToken(
        token: String
    ) {
        if (token.isBlank()) {
            return
        }

        try {
            api.deactivateDeviceToken(
                token
            )

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Failed to deactivate FCM token.",
                e
            )
        }
    }

    /**
     * Fetches all notifications for the current user.
     */
    suspend fun getNotifications():
            List<NotificationModel> {

        return try {

            api.getNotifications()

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Failed to load notifications.",
                e
            )

            throw e
        }
    }

    /**
     * Gets the current unread notification count.
     */
    suspend fun getUnreadNotificationCount():
            Int {

        return try {

            api.getUnreadNotificationCount()
                .count

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Failed to load unread notification count.",
                e
            )

            throw e
        }
    }

    /**
     * Marks a specific notification as read.
     */
    suspend fun markNotificationRead(
        notificationId: String
    ) {

        try {

            api.markNotificationRead(
                notificationId
            )

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Failed to mark notification as read.",
                e
            )

            throw e
        }
    }

    /**
     * Marks all notifications as read for the user.
     */
    suspend fun markAllNotificationsRead() {

        try {

            api.markAllNotificationsRead()

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Failed to mark all notifications as read.",
                e
            )

            throw e
        }
    }

    /**
     * Fetches the user's notification preferences.
     */
    suspend fun getNotificationPreferences():
            NotificationPreferences {

        return try {

            api.getNotificationPreferences()

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Failed to load notification preferences.",
                e
            )

            throw e
        }
    }

    /**
     * Updates whether push notifications are enabled for the user.
     */
    suspend fun updateNotificationPreferences(
        enabled: Boolean
    ) {

        try {

            api.updateNotificationPreferences(
                NotificationPreferenceRequest(
                    pushEnabled = enabled
                )
            )

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Failed to update notification preference.",
                e
            )

            throw e
        }
    }

    /**
     * Updates the local cache of the current user's profile.
     */
    fun setCurrentProfile(
        profile: Profile?
    ) {
        currentProfile = profile
    }

    /**
     * Clears local user data and tokens upon logout.
     */
    fun logout() {
        currentProfile = null
        RetrofitClient.setToken(null)
    }

    /**
     * Fetches all user profiles, optionally filtered by role.
     */
    suspend fun getProfiles(
        role: String? = null
    ): List<Profile> {

        return try {

            val response =
                api.getProfiles(role)

            profilesList.clear()
            profilesList.addAll(response)

            if (role != null) {

                profilesList.filter {
                    it.role.equals(
                        role,
                        ignoreCase = true
                    )
                }

            } else {

                profilesList.toList()
            }

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Error fetching profiles",
                e
            )

            throw e
        }
    }

    /**
     * Adds a new user profile (Administrator only).
     */
    suspend fun addProfile(
        fullName: String,
        role: String,
        email: String
    ): CreateProfileResponse {

        val newProfile =
            Profile(
                fullName =
                    fullName,
                role =
                    role,
                email =
                    email,
                phone =
                    null,
                isActive =
                    true
            )

        return try {

            val response =
                api.createProfile(
                    newProfile
                )

            profilesList.removeAll {
                it.id ==
                        response.profile.id
            }

            profilesList.add(
                response.profile
            )

            response

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Error adding profile",
                e
            )

            throw e
        }
    }

    /**
     * Fetches all construction sites.
     */
    suspend fun getSites():
            List<Site> {

        return try {

            val response =
                api.getSites()

            sitesList.clear()
            sitesList.addAll(response)

            sitesList.toList()

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Error fetching sites",
                e
            )

            throw e
        }
    }

    /**
     * Creates a new construction site.
     */
    suspend fun addSite(
        name: String,
        address: String
    ): Site {

        val newSite =
            Site(
                name =
                    name,
                address =
                    address,
                isActive =
                    true
            )

        return try {

            val response =
                api.createSite(
                    newSite
                )

            sitesList.removeAll {
                it.id ==
                        response.id
            }

            sitesList.add(
                response
            )

            response

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Error adding site",
                e
            )

            throw e
        }
    }

    /**
     * Updates an existing site's details.
     */
    suspend fun updateSite(
        id: String,
        name: String,
        address: String,
        isActive: Boolean
    ): Site {

        val request =
            SiteEditRequest(
                name =
                    name.trim(),
                address =
                    address.trim(),
                isActive =
                    isActive
            )

        return try {

            val response =
                api.updateSite(
                    id =
                        id,
                    siteUpdate =
                        request
                )

            sitesList.removeAll {
                it.id ==
                        response.id
            }

            sitesList.add(
                response
            )

            response

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Error updating site",
                e
            )

            throw e
        }
    }

    /**
     * Fetches all site-foreman assignments.
     */
    suspend fun getAssignments():
            List<SiteForemanAssignment> {

        return try {

            val response =
                api.getAssignments()

            assignmentsList.clear()
            assignmentsList.addAll(response)

            assignmentsList.toList()

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Error fetching assignments",
                e
            )

            throw e
        }
    }

    /**
     * Assigns a foreman to a site.
     */
    suspend fun assignForemanToSite(
        siteId: String,
        foremanId: String
    ): SiteForemanAssignment? {

        return try {

            val request =
                AssignmentRequest(
                    siteId =
                        siteId,
                    foremanId =
                        foremanId
                )

            api.createAssignment(
                request
            )

            val refreshed =
                getAssignments()

            refreshed.firstOrNull {
                it.siteId ==
                        siteId &&
                        it.foremanId ==
                        foremanId
            }

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Error assigning foreman",
                e
            )

            throw e
        }
    }

    /**
     * Removes an assignment for a foreman from a site.
     */
    suspend fun removeForemanFromSite(
        siteId: String,
        foremanId: String
    ) {

        api.deleteAssignment(
            siteId =
                siteId,
            foremanId =
                foremanId
        )

        assignmentsList.removeAll {
            it.siteId ==
                    siteId &&
                    it.foremanId ==
                    foremanId
        }
    }

    /**
     * Fetches all site progress reports.
     */
    suspend fun getSiteUpdates():
            List<SiteUpdate> {

        return try {

            val response =
                api.getSiteUpdates()

            siteUpdatesList.clear()
            siteUpdatesList.addAll(response)

            siteUpdatesList.toList()

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Error fetching site updates",
                e
            )

            throw e
        }
    }

    /**
     * Checks if a progress report has already been submitted for a site today.
     */
    suspend fun getTodaySiteUpdate(
        siteId: String
    ): SiteUpdate? {

        val today =
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.US
            ).format(
                Date()
            )

        return try {

            val response =
                api.getSiteUpdates(
                    siteId =
                        siteId,
                    startDate =
                        today,
                    endDate =
                        today
                )

            val todayUpdate =
                response.firstOrNull {
                    it.siteId ==
                            siteId &&
                            it.updateDate
                                .startsWith(
                                    today
                                )
                }

            if (todayUpdate != null) {

                siteUpdatesList.removeAll {
                    it.id ==
                            todayUpdate.id
                }

                siteUpdatesList.add(
                    todayUpdate
                )
            }

            todayUpdate

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Error fetching today's site update",
                e
            )

            throw e
        }
    }

    /**
     * Submits a new daily site progress report.
     */
    suspend fun addSiteUpdate(
        siteId: String,
        staffNames: String,
        powerTools: String,
        plantMachines: String,
        photos: List<PhotoInput>,
        notes: String?
    ): SiteUpdate {

        val request =
            SiteUpdateRequest(
                siteId =
                    siteId,
                staffNames =
                    staffNames,
                powerTools =
                    powerTools,
                plantMachines =
                    plantMachines,
                notes =
                    notes,
                photos =
                    photos
            )

        return try {

            val response =
                api.submitUpdate(
                    request
                )

            siteUpdatesList.removeAll {
                it.id ==
                        response.id
            }

            siteUpdatesList.add(
                response
            )

            Log.d(
                "OnSiteRepository",
                "Daily update submitted successfully"
            )

            response

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Error submitting daily update",
                e
            )

            throw e
        }
    }
}
