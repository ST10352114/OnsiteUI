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


package com.example.onsite_mockups.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data model representing a user profile in the OnSite system.
 */
@Serializable
data class Profile(
    @SerialName("id")
    val id: String? = null, // Supabase user ID

    @SerialName("fullName")
    val fullName: String? = null,

    @SerialName("role")
    val role: String? = null, // "admin" or "foreman"

    @SerialName("email")
    val email: String? = null,

    @SerialName("phone")
    val phone: String? = null,

    @SerialName("isActive")
    val isActive: Boolean = true,

    @SerialName("password")
    val password: String? = null, // Used during creation/login

    @SerialName("createdAt")
    val createdAt: String? = null
)

/**
 * Response received when a new profile is successfully created.
 */
@Serializable
data class CreateProfileResponse(
    @SerialName("profile")
    val profile: Profile,

    @SerialName("temporaryPassword")
    val temporaryPassword: String // Generated password for the new user
)

/**
 * Data model for a construction site.
 */
@Serializable
data class Site(
    @SerialName("id")
    val id: String? = null,

    @SerialName("name")
    val name: String,

    @SerialName("address")
    val address: String,

    @SerialName("isActive")
    val isActive: Boolean = true,

    @SerialName("createdAt")
    val createdAt: String? = null,

    @SerialName("status")
    val status: String? = null
)

/**
 * Request object for editing an existing site.
 */
@Serializable
data class SiteEditRequest(
    @SerialName("name")
    val name: String? = null,

    @SerialName("address")
    val address: String? = null,

    @SerialName("isActive")
    val isActive: Boolean? = null
)

/**
 * Data model for a daily site update submitted by a foreman.
 */
@Serializable
data class SiteUpdate(
    @SerialName("id")
    val id: String? = null,

    @SerialName("siteId")
    val siteId: String,

    @SerialName("foremanId")
    val foremanId: String,

    @SerialName("updateDate")
    val updateDate: String,

    @SerialName("forecastedLabor")
    val forecastedLabor: Int,

    @SerialName("bricklayers")
    val bricklayers: Int,

    @SerialName("plasterers")
    val plasterers: Int,

    @SerialName("pavers")
    val pavers: Int,

    @SerialName("actualLabor")
    val actualLabor: Int,

    @SerialName("staffNames")
    val staffNames: String? = null, // JSON string of StaffMember list

    @SerialName("powerTools")
    val powerTools: String? = null, // JSON string of tool names

    @SerialName("plantMachines")
    val plantMachines: String? = null, // JSON string of machine names

    @SerialName("notes")
    val notes: String? = null,

    @SerialName("createdAt")
    val createdAt: String? = null,

    @SerialName("updatePhotos")
    val updatePhotos: List<UpdatePhoto> = emptyList(),

    @SerialName("variance")
    val variance: Int? = null // Difference between forecasted and actual labor
)

/**
 * Data model for a photo attached to a site update.
 */
@Serializable
data class UpdatePhoto(
    @SerialName("id")
    val id: String? = null,

    @SerialName("updateId")
    val updateId: String? = null,

    @SerialName("photoData")
    val photoData: String, // Base64 encoded image data

    @SerialName("caption")
    val caption: String? = null,

    @SerialName("createdAt")
    val createdAt: String? = null
)

/**
 * Request to assign a foreman to a site.
 */
@Serializable
data class AssignmentRequest(
    @SerialName("siteId")
    val siteId: String,

    @SerialName("foremanId")
    val foremanId: String
)

/**
 * Represents a foreman assigned to a specific site.
 */
@Serializable
data class SiteForemanAssignment(
    @SerialName("siteId")
    val siteId: String,

    @SerialName("siteName")
    val siteName: String,

    @SerialName("foremanId")
    val foremanId: String,

    @SerialName("foremanName")
    val foremanName: String
)

/**
 * Represents a staff member on site.
 */
@Serializable
data class StaffMember(
    @SerialName("name")
    val name: String,

    @SerialName("job")
    val job: String
)

/**
 * Data for a single photo in an update submission.
 */
@Serializable
data class PhotoInput(
    val fileName: String,
    val base64Data: String
)

/**
 * Data model for an in-app notification.
 */
@Serializable
data class NotificationModel(
    @SerialName("id")
    val id: String,

    @SerialName("type")
    val type: String,

    @SerialName("title")
    val title: String,

    @SerialName("message")
    val message: String,

    @SerialName("data")
    val data: String = "{}", // Extra data for navigation/actions

    @SerialName("isRead")
    val isRead: Boolean = false,

    @SerialName("createdAt")
    val createdAt: String
)

/**
 * Response for unread notification count.
 */
@Serializable
data class UnreadNotificationCount(
    @SerialName("count")
    val count: Int
)

/**
 * User preferences for notifications.
 */
@Serializable
data class NotificationPreferences(
    @SerialName("pushEnabled")
    val pushEnabled: Boolean
)

/**
 * Request to update notification preferences.
 */
@Serializable
data class NotificationPreferenceRequest(
    @SerialName("pushEnabled")
    val pushEnabled: Boolean
)

/**
 * Request to register or deactivate a device FCM token.
 */
@Serializable
data class DeviceTokenRequest(
    @SerialName("token")
    val token: String,

    @SerialName("platform")
    val platform: String = "android"
)
