package com.example.onsite_mockups.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    @SerialName("id") val id: String,
    @SerialName("fullName") val fullName: String,
    @SerialName("role") val role: String, // "admin", "foreman", "truck_driver"
    @SerialName("email") val email: String,
    @SerialName("phone") val phone: String?,
    @SerialName("isActive") val isActive: Boolean,
    @SerialName("password") val password: String? = null
)

@Serializable
data class Site(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("address") val address: String,
    @SerialName("isActive") val isActive: Boolean
)

@Serializable
data class SiteUpdate(
    @SerialName("id") val id: String,
    @SerialName("siteId") val siteId: String,
    @SerialName("foremanId") val foremanId: String,
    @SerialName("updateDate") val updateDate: String, // YYYY-MM-DD
    @SerialName("forecastedLabor") val forecastedLabor: Int,
    @SerialName("bricklayers") val bricklayers: Int,
    @SerialName("plasterers") val plasterers: Int,
    @SerialName("pavers") val pavers: Int,
    @SerialName("actualLabor") val actualLabor: Int,
    @SerialName("staffNames") val staffNames: String?,
    @SerialName("powerTools") val powerTools: String?,
    @SerialName("plantMachines") val plantMachines: String?,
    @SerialName("notes") val notes: String?,
    @SerialName("createdAt") val createdAt: String? = null
)

@Serializable
data class AssignmentRequest(
    @SerialName("siteId") val siteId: String,
    @SerialName("foremanId") val foremanId: String
)
