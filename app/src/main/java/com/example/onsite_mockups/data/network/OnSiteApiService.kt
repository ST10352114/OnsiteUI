package com.example.onsite_mockups.data.network

import com.example.onsite_mockups.data.models.AssignmentRequest
import com.example.onsite_mockups.data.models.CreateProfileResponse
import com.example.onsite_mockups.data.models.DeviceTokenRequest
import com.example.onsite_mockups.data.models.NotificationModel
import com.example.onsite_mockups.data.models.NotificationPreferenceRequest
import com.example.onsite_mockups.data.models.NotificationPreferences
import com.example.onsite_mockups.data.models.PhotoInput
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.models.Site
import com.example.onsite_mockups.data.models.SiteEditRequest
import com.example.onsite_mockups.data.models.SiteForemanAssignment
import com.example.onsite_mockups.data.models.SiteUpdate
import com.example.onsite_mockups.data.models.UnreadNotificationCount
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API interface for OnSite backend services.
 * Handles profiles, sites, assignments, daily updates, and notifications.
 */
interface OnSiteApiService {

    /**
     * Completes registration for a user who signed in via Google OAuth.
     */
    @POST("auth/google-register")
    suspend fun registerGoogleUser(
        @Body request: GoogleRegistrationRequest
    ): Profile

    /**
     * Fetches a list of profiles, optionally filtered by role.
     */
    @GET("profiles")
    suspend fun getProfiles(
        @Query("role")
        role: String? = null
    ): List<Profile>

    /**
     * Creates a new user profile (Administrator only).
     */
    @POST("profiles")
    suspend fun createProfile(
        @Body profile: Profile
    ): CreateProfileResponse

    /**
     * Updates an existing profile's status (e.g., active/inactive).
     */
    @PATCH("profiles/{id}")
    suspend fun updateProfile(
        @Path("id")
        id: String,

        @Body
        profileUpdate: Map<String, Boolean>
    ): Profile

    /**
     * Fetches all construction sites.
     */
    @GET("sites")
    suspend fun getSites(): List<Site>

    /**
     * Creates a new construction site.
     */
    @POST("sites")
    suspend fun createSite(
        @Body site: Site
    ): Site

    /**
     * Updates site details (name, address, status).
     */
    @PATCH("sites/{id}")
    suspend fun updateSite(
        @Path("id")
        id: String,

        @Body
        siteUpdate: SiteEditRequest
    ): Site

    /**
     * Fetches foreman-to-site assignments.
     */
    @GET("assignments")
    suspend fun getAssignments():
            List<SiteForemanAssignment>

    /**
     * Assigns a foreman to a specific construction site.
     */
    @POST("assignments")
    suspend fun createAssignment(
        @Body assignment: AssignmentRequest
    ): SiteForemanAssignment

    /**
     * Removes an assignment for a foreman.
     */
    @DELETE("assignments")
    suspend fun deleteAssignment(
        @Query("site_id")
        siteId: String,

        @Query("foreman_id")
        foremanId: String
    )

    /**
     * Fetches daily updates, optionally filtered by site and date range.
     */
    @GET("site-updates")
    suspend fun getSiteUpdates(
        @Query("site_id")
        siteId: String? = null,

        @Query("start_date")
        startDate: String? = null,

        @Query("end_date")
        endDate: String? = null
    ): List<SiteUpdate>

    /**
     * Submits a new daily progress report for a site.
     */
    @POST("site-updates")
    suspend fun submitUpdate(
        @Body update: SiteUpdateRequest
    ): SiteUpdate

    // =====================================================
    // NOTIFICATIONS
    // =====================================================

    /**
     * Registers a device's FCM token for push notifications.
     */
    @POST("notifications/device-token")
    suspend fun registerDeviceToken(
        @Body request: DeviceTokenRequest
    )

    /**
     * Deactivates an FCM token (usually on logout).
     */
    @DELETE("notifications/device-token")
    suspend fun deactivateDeviceToken(
        @Query("token")
        token: String
    )

    /**
     * Fetches a list of in-app notifications.
     */
    @GET("notifications")
    suspend fun getNotifications(
        @Query("limit")
        limit: Int = 50
    ): List<NotificationModel>

    /**
     * Gets the count of unread notifications for the current user.
     */
    @GET("notifications/unread-count")
    suspend fun getUnreadNotificationCount():
            UnreadNotificationCount

    /**
     * Marks a specific notification as read.
     */
    @POST("notifications/{id}/read")
    suspend fun markNotificationRead(
        @Path("id")
        id: String
    )

    /**
     * Marks all notifications as read.
     */
    @POST("notifications/read-all")
    suspend fun markAllNotificationsRead()

    /**
     * Fetches notification preferences (e.g., push enabled).
     */
    @GET("notifications/preferences")
    suspend fun getNotificationPreferences():
            NotificationPreferences

    /**
     * Updates notification preferences.
     */
    @PUT("notifications/preferences")
    suspend fun updateNotificationPreferences(
        @Body request: NotificationPreferenceRequest
    )
}

/**
 * Request model for submitting a site update.
 */
@kotlinx.serialization.Serializable
data class SiteUpdateRequest(
    @kotlinx.serialization.SerialName("siteId")
    val siteId: String,

    @kotlinx.serialization.SerialName("staffNames")
    val staffNames: String?, // JSON string

    @SerialName("powerTools")
    val powerTools: String?, // JSON string

    @SerialName("plantMachines")
    val plantMachines: String?, // JSON string

    @kotlinx.serialization.SerialName("notes")
    val notes: String?,

    @kotlinx.serialization.SerialName("photos")
    val photos: List<PhotoInput>
)

@kotlinx.serialization.Serializable
data class GoogleRegistrationRequest(
    @kotlinx.serialization.SerialName("fullName")
    val fullName: String
)
