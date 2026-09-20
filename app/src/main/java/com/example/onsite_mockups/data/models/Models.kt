package com.example.onsite_mockups.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    @SerialName("id")
    val id: String? = null,

    @SerialName("fullName")
    val fullName: String,

    @SerialName("role")
    val role: String,

    @SerialName("email")
    val email: String,

    @SerialName("phone")
    val phone: String? = null,

    @SerialName("isActive")
    val isActive: Boolean = true,

    @SerialName("password")
    val password: String? = null,

    @SerialName("createdAt")
    val createdAt: String? = null
)

@Serializable
data class CreateProfileResponse(
    @SerialName("profile")
    val profile: Profile,

    @SerialName("temporaryPassword")
    val temporaryPassword: String
)

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

@Serializable
data class SiteEditRequest(
    @SerialName("name")
    val name: String? = null,

    @SerialName("address")
    val address: String? = null,

    @SerialName("isActive")
    val isActive: Boolean? = null
)

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
    val staffNames: String? = null,

    @SerialName("powerTools")
    val powerTools: String? = null,

    @SerialName("plantMachines")
    val plantMachines: String? = null,

    @SerialName("notes")
    val notes: String? = null,

    @SerialName("createdAt")
    val createdAt: String? = null,

    @SerialName("updatePhotos")
    val updatePhotos: List<UpdatePhoto> = emptyList(),

    @SerialName("variance")
    val variance: Int? = null
)

@Serializable
data class UpdatePhoto(
    @SerialName("id")
    val id: String? = null,

    @SerialName("updateId")
    val updateId: String? = null,

    @SerialName("storagePath")
    val storagePath: String,

    @SerialName("caption")
    val caption: String? = null,

    @SerialName("createdAt")
    val createdAt: String? = null
)

@Serializable
data class AssignmentRequest(
    @SerialName("siteId")
    val siteId: String,

    @SerialName("foremanId")
    val foremanId: String
)

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