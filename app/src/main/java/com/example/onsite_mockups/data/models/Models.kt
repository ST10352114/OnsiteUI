package com.example.onsite_mockups.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    @SerialName("id") val id: String? = null,
    @SerialName("full_name") val fullName: String,
    @SerialName("role") val role: String, // "admin", "foreman", "truck_driver"
    @SerialName("email") val email: String,
    @SerialName("phone") val phone: String?,
    @SerialName("is_active") val isActive: Boolean,
    @SerialName("password") val password: String? = null
)

@Serializable
data class Site(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String,
    @SerialName("address") val address: String,
    @SerialName("is_active") val isActive: Boolean
)

@Serializable
data class SiteUpdate(
    @SerialName("id") val id: String? = null,
    @SerialName("site_id") val siteId: String,
    @SerialName("foreman_id") val foremanId: String,
    @SerialName("update_date") val updateDate: String, // YYYY-MM-DD
    @SerialName("forecasted_labor") val forecastedLabor: Int,
    @SerialName("bricklayers") val bricklayers: Int,
    @SerialName("plasterers") val plasterers: Int,
    @SerialName("pavers") val pavers: Int,
    @SerialName("actual_labor") val actualLabor: Int,
    @SerialName("staff_names") val staffNames: String?,
    @SerialName("power_tools") val powerTools: String?,
    @SerialName("plant_machines") val plantMachines: String?,
    @SerialName("notes") val notes: String?,
    @SerialName("created_at") val createdAt: String? = null
)

@Serializable
data class AssignmentRequest(
    @SerialName("site_id") val siteId: String,
    @SerialName("foreman_id") val foremanId: String
)
