package com.example.onsite_mockups.data.repository

import android.util.Log
import com.example.onsite_mockups.data.models.AssignmentRequest
import com.example.onsite_mockups.data.models.CreateProfileResponse
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.models.Site
import com.example.onsite_mockups.data.models.SiteEditRequest
import com.example.onsite_mockups.data.models.SiteForemanAssignment
import com.example.onsite_mockups.data.models.SiteUpdate
import com.example.onsite_mockups.data.network.OnSiteApiService
import com.example.onsite_mockups.data.network.PhotoInput
import com.example.onsite_mockups.data.network.RetrofitClient
import com.example.onsite_mockups.data.network.SiteUpdateRequest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object OnSiteRepository {

    private val api =
        RetrofitClient.apiService

    var currentProfile: Profile? = null
        private set

    private val profilesList =
        mutableListOf<Profile>()

    private val sitesList =
        mutableListOf<Site>()

    private val assignmentsList =
        mutableListOf<SiteForemanAssignment>()

    private val siteUpdatesList =
        mutableListOf<SiteUpdate>()

    fun setCurrentProfile(
        profile: Profile?
    ) {
        currentProfile = profile
    }

    fun logout() {
        currentProfile = null
        RetrofitClient.setToken(null)
    }

    suspend fun getProfiles(
        role: String? = null
    ): List<Profile> {

        return try {

            val response =
                api.getProfiles(role)

            profilesList.clear()
            profilesList.addAll(response)

            if (role != null) {

                profilesList.filter {
                    it.role.equals(
                        role,
                        ignoreCase = true
                    )
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
    ): CreateProfileResponse {

        val newProfile =
            Profile(
                fullName =
                    fullName,
                role =
                    role,
                email =
                    email,
                phone =
                    null,
                isActive =
                    true
            )

        return try {

            val response =
                api.createProfile(
                    newProfile
                )

            profilesList.removeAll {
                it.id ==
                        response.profile.id
            }

            profilesList.add(
                response.profile
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

    suspend fun getSites():
            List<Site> {

        return try {

            val response =
                api.getSites()

            sitesList.clear()
            sitesList.addAll(response)

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

        val newSite =
            Site(
                name =
                    name,
                address =
                    address,
                isActive =
                    true
            )

        return try {

            val response =
                api.createSite(
                    newSite
                )

            sitesList.removeAll {
                it.id ==
                        response.id
            }

            sitesList.add(
                response
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

    suspend fun updateSite(
        id: String,
        name: String,
        address: String,
        isActive: Boolean
    ): Site {

        val request =
            SiteEditRequest(
                name =
                    name.trim(),
                address =
                    address.trim(),
                isActive =
                    isActive
            )

        return try {

            val response =
                api.updateSite(
                    id =
                        id,
                    siteUpdate =
                        request
                )

            sitesList.removeAll {
                it.id ==
                        response.id
            }

            sitesList.add(
                response
            )

            response

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Error updating site",
                e
            )

            throw e
        }
    }

    suspend fun getAssignments():
            List<SiteForemanAssignment> {

        return try {

            val response =
                api.getAssignments()

            assignmentsList.clear()
            assignmentsList.addAll(response)

            assignmentsList.toList()

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Error fetching assignments",
                e
            )

            throw e
        }
    }

    suspend fun assignForemanToSite(
        siteId: String,
        foremanId: String
    ): SiteForemanAssignment? {

        return try {

            val request =
                AssignmentRequest(
                    siteId =
                        siteId,
                    foremanId =
                        foremanId
                )

            api.createAssignment(
                request
            )

            val refreshed =
                getAssignments()

            refreshed.firstOrNull {
                it.siteId ==
                        siteId &&
                        it.foremanId ==
                        foremanId
            }

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Error assigning foreman",
                e
            )

            throw e
        }
    }

    suspend fun removeForemanFromSite(
        siteId: String,
        foremanId: String
    ) {

        api.deleteAssignment(
            siteId =
                siteId,
            foremanId =
                foremanId
        )

        assignmentsList.removeAll {
            it.siteId ==
                    siteId &&
                    it.foremanId ==
                    foremanId
        }
    }

    suspend fun getSiteUpdates():
            List<SiteUpdate> {

        return try {

            val response =
                api.getSiteUpdates()

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

    suspend fun getTodaySiteUpdate(
        siteId: String
    ): SiteUpdate? {

        val today =
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.US
            ).format(
                Date()
            )

        return try {

            val response =
                api.getSiteUpdates(
                    siteId =
                        siteId,
                    startDate =
                        today,
                    endDate =
                        today
                )

            val todayUpdate =
                response.firstOrNull {
                    it.siteId ==
                            siteId &&
                            it.updateDate
                                .startsWith(
                                    today
                                )
                }

            if (todayUpdate != null) {

                siteUpdatesList.removeAll {
                    it.id ==
                            todayUpdate.id
                }

                siteUpdatesList.add(
                    todayUpdate
                )
            }

            todayUpdate

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Error fetching today's site update",
                e
            )

            throw e
        }
    }

    suspend fun addSiteUpdate(
        siteId: String,
        staffNames: String,
        powerTools: String,
        plantMachines: String,
        photos: List<PhotoInput>,
        notes: String?
    ): SiteUpdate {

        val request =
            SiteUpdateRequest(
                siteId =
                    siteId,
                staffNames =
                    staffNames,
                powerTools =
                    powerTools,
                plantMachines =
                    plantMachines,
                notes =
                    notes,
                photos =
                    photos
            )

        return try {

            val response =
                api.submitUpdate(
                    request
                )

            siteUpdatesList.removeAll {
                it.id ==
                        response.id
            }

            siteUpdatesList.add(
                response
            )

            Log.d(
                "OnSiteRepository",
                "Daily update submitted successfully"
            )

            response

        } catch (e: Exception) {

            Log.e(
                "OnSiteRepository",
                "Error submitting daily update",
                e
            )

            throw e
        }
    }
}