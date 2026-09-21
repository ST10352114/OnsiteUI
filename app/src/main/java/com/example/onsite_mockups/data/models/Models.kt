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
