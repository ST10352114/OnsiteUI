package com.example.onsite_mockups.data.network

import com.example.onsite_mockups.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Profiles
    @GET("api/v1/profiles")
    suspend fun getProfiles(@Query("role") role: String? = null): Response<List<Profile>>

    @POST("api/v1/profiles")
    suspend fun createProfile(@Body profile: Profile): Response<Profile>

    @PATCH("api/v1/profiles/{id}")
    suspend fun updateProfile(@Path("id") id: String, @Body updates: Map<String, Any>): Response<Unit>

    @DELETE("api/v1/profiles/{id}")
    suspend fun deleteProfile(@Path("id") id: String): Response<Unit>

    // Sites
    @GET("api/v1/sites")
    suspend fun getSites(): Response<List<Site>>

    @POST("api/v1/sites")
    suspend fun createSite(@Body site: Site): Response<Site>

    @PATCH("api/v1/sites/{id}")
    suspend fun updateSite(@Path("id") id: String, @Body updates: Map<String, Any>): Response<Unit>

    // Site Updates
    @GET("api/v1/site-updates")
    suspend fun getSiteUpdates(): Response<List<SiteUpdate>>

    @POST("api/v1/site-updates")
    suspend fun createSiteUpdate(@Body update: SiteUpdate): Response<SiteUpdate>
}
