package com.example.onsite_mockups.data.network

import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.models.Site
import com.example.onsite_mockups.data.models.SiteUpdate
import retrofit2.http.*

interface OnSiteApiService {

    @GET("profiles")
    suspend fun getProfiles(@Query("role") role: String? = null): List<Profile>

    @POST("profiles")
    suspend fun createProfile(@Body profile: Profile): Profile

    @PATCH("profiles/{id}")
    suspend fun updateProfile(@Path("id") id: String, @Body profileUpdate: Map<String, Boolean>): Profile

    @GET("sites")
    suspend fun getSites(): List<Site>

    @POST("sites")
    suspend fun createSite(@Body site: Site): Site

    @GET("site-updates")
    suspend fun getSiteUpdates(
        @Query("site_id") siteId: String? = null,
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null
    ): List<SiteUpdate>

    @POST("site-updates")
    suspend fun submitUpdate(@Body update: SiteUpdate): SiteUpdate
}
