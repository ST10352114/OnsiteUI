package com.example.onsite_mockups.data.network

import com.example.onsite_mockups.data.models.AssignmentRequest
import com.example.onsite_mockups.data.models.CreateProfileResponse
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.models.Site
import com.example.onsite_mockups.data.models.SiteEditRequest
import com.example.onsite_mockups.data.models.SiteForemanAssignment
import com.example.onsite_mockups.data.models.SiteUpdate
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OnSiteApiService {

    @GET("profiles")
    suspend fun getProfiles(
        @Query("role") role: String? = null
    ): List<Profile>

    @POST("profiles")
    suspend fun createProfile(
        @Body profile: Profile
    ): CreateProfileResponse

    @PATCH("profiles/{id}")
    suspend fun updateProfile(
        @Path("id") id: String,
        @Body profileUpdate: Map<String, Boolean>
    ): Profile

    @GET("sites")
    suspend fun getSites(): List<Site>

    @POST("sites")
    suspend fun createSite(
        @Body site: Site
    ): Site

    @PATCH("sites/{id}")
    suspend fun updateSite(
        @Path("id") id: String,
        @Body siteUpdate: SiteEditRequest
    ): Site

    @GET("assignments")
    suspend fun getAssignments(): List<SiteForemanAssignment>

    @POST("assignments")
    suspend fun createAssignment(
        @Body assignment: AssignmentRequest
    ): SiteForemanAssignment

    @DELETE("assignments")
    suspend fun deleteAssignment(
        @Query("site_id") siteId: String,
        @Query("foreman_id") foremanId: String
    )

    @GET("site-updates")
    suspend fun getSiteUpdates(
        @Query("site_id") siteId: String? = null,
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null
    ): List<SiteUpdate>

    @POST("site-updates")
    suspend fun submitUpdate(
        @Body update: SiteUpdateRequest
    ): SiteUpdate
}

@kotlinx.serialization.Serializable
data class SiteUpdateRequest(
    @kotlinx.serialization.SerialName("siteId")
    val siteId: String,

    @kotlinx.serialization.SerialName("staffNames")
    val staffNames: String?,

    @kotlinx.serialization.SerialName("powerTools")
    val powerTools: String?,

    @kotlinx.serialization.SerialName("plantMachines")
    val plantMachines: String?,

    @kotlinx.serialization.SerialName("notes")
    val notes: String?,

    @kotlinx.serialization.SerialName("photos")
    val photos: List<PhotoInput>
)

@kotlinx.serialization.Serializable
data class PhotoInput(
    @kotlinx.serialization.SerialName("photoData")
    val photoData: String,

    @kotlinx.serialization.SerialName("caption")
    val caption: String? = null
)