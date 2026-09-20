package com.example.onsite_mockups.data.repository

import android.util.Log
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.models.Site
import com.example.onsite_mockups.data.models.SiteUpdate
import com.example.onsite_mockups.data.network.RetrofitClient
import java.util.UUID

object OnSiteRepository {

    private val api = RetrofitClient.apiService

    var currentProfile: Profile? = null
        private set

    private val profilesList = mutableListOf<Profile>()
    private val sitesList = mutableListOf<Site>()
    private val siteUpdatesList = mutableListOf<SiteUpdate>()

    fun logout() {
        currentProfile = null
        RetrofitClient.setToken(null)
    }

    suspend fun getProfiles(role: String? = null): List<Profile> {
        return try {
            val response = api.getProfiles(role)

            profilesList.clear()
            profilesList.addAll(response)

            if (role != null) {
                profilesList.filter {
                    it.role.equals(role, ignoreCase = true)
                }
            } else {
                profilesList.toList()
            }
        } catch (e: Exception) {
            Log.e(
                "OnSiteRepository",
                "Error fetching profiles",
                e
            )

            throw e
        }
    }

    suspend fun addProfile(
        fullName: String,
        role: String,
        email: String
    ): Profile {
        val newProfile = Profile(
            fullName = fullName,
            role = role,
            email = email,
            phone = null,
            isActive = true
        )

        return try {
            val response = api.createProfile(newProfile)

            profilesList.removeAll {
                it.id == response.id
            }

            profilesList.add(response)

            Log.d(
                "OnSiteRepository",
                "Successfully added profile: ${response.fullName}"
            )

            response
        } catch (e: Exception) {
            Log.e(
                "OnSiteRepository",
                "Error adding profile",
                e
            )

            throw e
        }
    }

    suspend fun getSites(): List<Site> {
        return try {
            val response = api.getSites()

            sitesList.clear()
            sitesList.addAll(response)

            Log.d(
                "OnSiteRepository",
                "Fetched ${response.size} sites from API"
            )

            sitesList.toList()
        } catch (e: Exception) {
            Log.e(
                "OnSiteRepository",
                "Error fetching sites",
                e
            )

            throw e
        }
    }

    suspend fun addSite(
        name: String,
        address: String
    ): Site {
        val newSite = Site(
            name = name,
            address = address,
            isActive = true
        )

        return try {
            val response = api.createSite(newSite)

            sitesList.removeAll {
                it.id == response.id
            }

            sitesList.add(response)

            Log.d(
                "OnSiteRepository",
                "Successfully added site: ${response.name}"
            )

            response
        } catch (e: Exception) {
            Log.e(
                "OnSiteRepository",
                "Error adding site",
                e
            )

            throw e
        }
    }

    suspend fun getSiteUpdates(): List<SiteUpdate> {
        return try {
            val response = api.getSiteUpdates()

            siteUpdatesList.clear()
            siteUpdatesList.addAll(response)

            siteUpdatesList.toList()
        } catch (e: Exception) {
            Log.e(
                "OnSiteRepository",
                "Error fetching site updates",
                e
            )

            throw e
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
            foremanId = currentProfile?.id ?: "",
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

        return try {
            val response = api.submitUpdate(newUpdate)

            siteUpdatesList.add(response)

            Log.d(
                "OnSiteRepository",
                "Successfully submitted site update"
            )

            response
        } catch (e: Exception) {
            Log.e(
                "OnSiteRepository",
                "Error adding site update",
                e
            )

            throw e
        }
    }
}