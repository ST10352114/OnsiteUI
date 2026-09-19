package com.example.onsite_mockups.data.repository

import com.example.onsite_mockups.data.models.*
import com.example.onsite_mockups.data.network.RetrofitClient
import com.example.onsite_mockups.data.network.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import java.util.UUID

object OnSiteRepository {
    private val supabase = SupabaseClient.client
    private val api = RetrofitClient.apiService

    var currentProfile: Profile? = null
        private set

    // Client-side state fallback lists to guarantee app functionality even if backend is offline
    private val profilesList = mutableListOf(
        Profile("1", "Thabo Mokoena", "foreman", "thabo@onsite.com", "0821112223", true),
        Profile("2", "Aisha Zulu", "admin", "aisha@onsite.com", "0824445556", true),
        Profile("3", "A. Naidoo", "foreman", "naidoo@onsite.com", "0827778889", true),
        Profile("4", "S. Zulu", "foreman", "zulu@onsite.com", "0829990001", true)
    )

    private val sitesList = mutableListOf(
        Site("ridgeview", "Ridgeview Estate — Block C", "14 Marlow Road, Umhlanga", true),
        Site("palmgrove", "Palm Grove Retail Park", "88 Chartwell Dr, Umhlanga", true),
        Site("northgate", "Northgate Office Park", "6 Sunset Ave, Durban North", true),
        Site("silverwood", "Silverwood Complex", "12 Silverwood Road", true),
        Site("harbourview", "Harbour View Towers", "44 Marine Drive", true)
    )

    private val siteUpdatesList = mutableListOf(
        SiteUpdate("u1", "ridgeview", "1", "2026-09-18", 15, 4, 3, 5, 12, "S. Dlamini, M. Khumalo", "Angle grinder, Drill", "Excavator", "Progressing on schedule"),
        SiteUpdate("u2", "northgate", "1", "2026-09-18", 10, 3, 2, 4, 9, "J. Smith", "Drill", "Concrete mixer", "Foundations poured"),
        SiteUpdate("u4", "silverwood", "3", "2026-09-18", 14, 5, 4, 3, 12, "A. K.", "Welder", "Forklift", "Structural steel complete")
    )

    fun logout() {
        currentProfile = null
        RetrofitClient.setToken(null)
    }

    suspend fun getProfiles(role: String? = null): List<Profile> {
        return try {
            val res = api.getProfiles(role)
            if (res.isNotEmpty()) {
                profilesList.clear()
                profilesList.addAll(res)
            }
            profilesList
        } catch (e: Exception) {
            android.util.Log.e("OnSiteRepository", "Error fetching profiles: ${e.message}")
            if (role != null) profilesList.filter { it.role == role } else profilesList
        }
    }

    suspend fun addProfile(fullName: String, role: String, email: String): Profile {
        val newProfile = Profile(UUID.randomUUID().toString(), fullName, role, email, null, true)
        try {
            api.createProfile(newProfile)
        } catch (e: Exception) {
            android.util.Log.e("OnSiteRepository", "Error adding profile: ${e.message}")
        }
        profilesList.add(newProfile)
        return newProfile
    }

    suspend fun getSites(): List<Site> {
        return try {
            val res = api.getSites()
            if (res.isNotEmpty()) {
                sitesList.clear()
                sitesList.addAll(res)
            }
            sitesList
        } catch (e: Exception) {
            android.util.Log.e("OnSiteRepository", "Error fetching sites: ${e.message}")
            sitesList
        }
    }

    suspend fun addSite(name: String, address: String): Site {
        val newSite = Site(UUID.randomUUID().toString(), name, address, true)
        try {
            api.createSite(newSite)
        } catch (e: Exception) {
            android.util.Log.e("OnSiteRepository", "Error adding site: ${e.message}")
        }
        sitesList.add(newSite)
        return newSite
    }

    suspend fun getSiteUpdates(): List<SiteUpdate> {
        return try {
            val res = api.getSiteUpdates()
            if (res.isNotEmpty()) {
                siteUpdatesList.clear()
                siteUpdatesList.addAll(res)
            }
            siteUpdatesList
        } catch (e: Exception) {
            android.util.Log.e("OnSiteRepository", "Error fetching site updates: ${e.message}")
            siteUpdatesList
        }
    }

    suspend fun addSiteUpdate(
        siteId: String,
        headcount: Int,
        staffNames: String,
        powerTools: String,
        plantMachines: String
    ): SiteUpdate {
        val newUpdate = SiteUpdate(
            id = UUID.randomUUID().toString(),
            siteId = siteId,
            foremanId = currentProfile?.id ?: "1",
            updateDate = "2026-09-18",
            forecastedLabor = headcount + 2,
            bricklayers = headcount / 3,
            plasterers = headcount / 3,
            pavers = headcount - (headcount / 3) * 2,
            actualLabor = headcount,
            staffNames = staffNames,
            powerTools = powerTools,
            plantMachines = plantMachines,
            notes = "Submitted via mobile application"
        )
        try {
            api.submitUpdate(newUpdate)
        } catch (e: Exception) {
            android.util.Log.e("OnSiteRepository", "Error adding site update: ${e.message}")
        }
        siteUpdatesList.add(newUpdate)
        return newUpdate
    }
}
