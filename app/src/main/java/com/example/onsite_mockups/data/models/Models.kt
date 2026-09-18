package com.example.onsite_mockups.data.models

import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("id") val id: String,
    @SerializedName("fullName") val fullName: String,
    @SerializedName("role") val role: String, // "admin", "foreman", "truck_driver"
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String?,
    @SerializedName("isActive") val isActive: Boolean,
    @SerializedName("password") val password: String? = null
)

data class Site(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("isActive") val isActive: Boolean
)

data class SiteUpdate(
    @SerializedName("id") val id: String,
    @SerializedName("siteId") val siteId: String,
    @SerializedName("foremanId") val foremanId: String,
    @SerializedName("updateDate") val updateDate: String, // YYYY-MM-DD
    @SerializedName("forecastedLabor") val forecastedLabor: Int,
    @SerializedName("bricklayers") val bricklayers: Int,
    @SerializedName("plasterers") val plasterers: Int,
    @SerializedName("pavers") val pavers: Int,
    @SerializedName("actualLabor") val actualLabor: Int,
    @SerializedName("staffNames") val staffNames: String?,
    @SerializedName("powerTools") val powerTools: String?,
    @SerializedName("plantMachines") val plantMachines: String?,
    @SerializedName("notes") val notes: String?,
    @SerializedName("createdAt") val createdAt: String? = null
)

data class AssignmentRequest(
    @SerializedName("siteId") val siteId: String,
    @SerializedName("foremanId") val foremanId: String
)
