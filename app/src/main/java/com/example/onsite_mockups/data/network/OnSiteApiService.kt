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
