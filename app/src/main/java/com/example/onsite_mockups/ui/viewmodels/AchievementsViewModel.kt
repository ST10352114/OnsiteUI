//Reference list
// Android Developers, 2019. Save data in a local database using room  |  android developers. [online] Android Developers. Available at: <https://developer.android.com/training/data-storage/room> [Accessed 17 August 2026].
// Android Developers, n.d. App architecture: Data layer - persistent work with WorkManager - android developers | background work. [online] Android Developers. Available at: <https://developer.android.com/develop/background-work/background-tasks/persistent> [Accessed 17 August 2026].
// Android Developers, n.d. BiometricPrompt. [online] Android Developers. Available at: <https://developer.android.com/reference/android/hardware/biometrics/BiometricPrompt> [Accessed 17 August 2026].
// Android Developers, n.d. Material design 3 in compose | jetpack compose. [online] Android Developers. Available at: <https://developer.android.com/develop/ui/compose/designsystems/material3> [Accessed 17 August 2026].
// Authgear, 2025. Login & signup UX: The 2025 guide to best practices (examples & tips). [online] Authgear. Available at: <https://www.authgear.com/post/login-signup-ux-guide/> [Accessed 23 August 2026].
// Bennett, T., 2024. Direct database access vs. REST APIs: Compare application activity. [online] blog.dreamfactory.com. Available at: <https://blog.dreamfactory.com/direct-database-access-vs-rest-apis-pros-and-cons-for-application-connectivity> [Accessed 17 August 2026].
// Cloudflare, 2024. What is rate limiting? | Rate limiting and bots. [online] Cloudflare.com. Available at: <https://www.cloudflare.com/learning/bots/what-is-rate-limiting/> [Accessed 23 August 2026].
// Firebase, 2026. Get started with firebase cloud messaging in android apps. [online] Firebase. Available at: <https://firebase.google.com/docs/cloud-messaging/android/get-started> [Accessed 17 August 2026].
// InEight, 2023. 8 Must-haves for a construction management platform. [online] InEight. Available at: <https://ineight.com/blog/8-must-haves-for-a-construction-management-platform/> [Accessed 17 August 2026].
// Kitch, B., 2024. How to create an agile project plan for software development. [online] Mural.co. Available at: <https://www.mural.co/blog/how-to-create-an-agile-project-plan> [Accessed 17 August 2026].
// Kohler, T., 2022. Autonomy, relatedness, and competence in UX design. [online] Nielsen Norman Group. Available at: <https://www.nngroup.com/articles/autonomy-relatedness-competence/> [Accessed 17 August 2026].
// PostgREST, 2017. Pagination and count. [online] PostgREST 16. Available at: <https://docs.postgrest.org/en/stable/references/api/pagination_count.html> [Accessed 17 August 2026].
// QuickBooks, 2026. What is data export? Meaning & process in 2025 | QuickBooks. [online] Intuit.com. Available at: <https://quickbooks.intuit.com/r/bookkeeping/data-export/> [Accessed 23 August 2026].
// Render, n.d. Cloud application hosting for developers | render. [online] Cloud Application Hosting for Developers | Render. Available at: <https://render.com/> [Accessed 17 August 2026].
// Softbiz, 2026. Why business logic belongs on the server, not the frontend. [online] Softbiz. Available at: <https://www.softbiz.com/technology/backend-and-api-development/why-business-logic-belongs-on-the-server-not-the-frontend> [Accessed 17 August 2026].
// Supabase, 2023. Auth | supabase docs. [online] supabase.com. Available at: <https://supabase.com/docs/guides/auth> [Accessed 17 August 2026].
// Supabase, 2024. Row level security | supabase docs. [online] Supabase. Available at: <https://supabase.com/docs/guides/database/postgres/row-level-security> [Accessed 17 August 2026].
// Supabase, 2026. Environment variables | supabase Docs. [online] supabase. Available at: <https://supabase.com/docs/guides/functions/secrets> [Accessed 17 August 2026].W3C, 2024. Web content accessibility guidelines (WCAG) 2.2. [online] www.w3.org. W3C. Available at: <https://www.w3.org/TR/WCAG22/> [Accessed 17 August 2026].


package com.example.onsite_mockups.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onsite_mockups.data.models.SiteUpdate
import com.example.onsite_mockups.data.repository.OnSiteRepository
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Holds statistics and earned status for foreman achievements.
 */
data class AchievementStats(
    val totalUpdates: Int = 0,
    val totalPhotos: Int = 0,
    val longestStreak: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    // Computed properties for badge achievement status
    val firstUpdateEarned: Boolean
        get() = totalUpdates >= 1

    val threeDayStreakEarned: Boolean
        get() = longestStreak >= 3

    val photoProEarned: Boolean
        get() = totalPhotos >= 10

    val tenUpdatesEarned: Boolean
        get() = totalUpdates >= 10

    val perfectWeekEarned: Boolean
        get() = longestStreak >= 5

    val twentyFiveUpdatesEarned: Boolean
        get() = totalUpdates >= 25

    /**
     * Total number of badges earned across all categories.
     */
    val earnedBadgeCount: Int
        get() =
            listOf(
                firstUpdateEarned,
                threeDayStreakEarned,
                photoProEarned,
                tenUpdatesEarned,
                perfectWeekEarned,
                twentyFiveUpdatesEarned
            ).count { it }

    /**
     * Determines the user's tier based on total submissions.
     */
    val tier: String
        get() =
            when {
                totalUpdates >= 25 ->
                    "Gold Foreman"

                totalUpdates >= 10 ->
                    "Silver Foreman"

                else ->
                    "Bronze Foreman"
            }

    /**
     * Target count for the next tier.
     */
    val nextTierTarget: Int?
        get() =
            when {
                totalUpdates < 10 ->
                    10

                totalUpdates < 25 ->
                    25

                else ->
                    null
            }

    /**
     * Percentage progress toward the next tier (0.0 to 1.0).
     */
    val progressToNextTier: Float
        get() {
            return when {
                totalUpdates < 10 ->
                    totalUpdates / 10f

                totalUpdates < 25 ->
                    (totalUpdates - 10) / 15f

                else ->
                    1f
            }.coerceIn(0f, 1f)
        }

    /**
     * Number of updates required to reach the next tier.
     */
    val updatesUntilNextTier: Int
        get() =
            when {
                totalUpdates < 10 ->
                    10 - totalUpdates

                totalUpdates < 25 ->
                    25 - totalUpdates

                else ->
                    0
            }
}

/**
 * ViewModel for the Achievements screen.
 * Calculates streaks and badges based on the user's submission history.
 */
class AchievementsViewModel : ViewModel() {

    private val _stats =
        MutableStateFlow(
            AchievementStats()
        )

    /**
     * State of achievement statistics and badges.
     */
    val stats:
            StateFlow<AchievementStats> =
        _stats.asStateFlow()

    /**
     * Fetches site updates from the repository and recalculates achievements.
     */
    fun loadAchievements() {

        if (_stats.value.isLoading) {
            return
        }

        viewModelScope.launch {

            _stats.value =
                _stats.value.copy(
                    isLoading = true,
                    errorMessage = null
                )

            try {

                val updates =
                    OnSiteRepository
                        .getSiteUpdates()

                val totalUpdates =
                    updates.size

                val totalPhotos =
                    updates.sumOf {
                        it.updatePhotos.size
                    }

                // Calculate the longest consecutive submission streak
                val longestStreak =
                    calculateLongestStreak(
                        updates
                    )

                _stats.value =
                    AchievementStats(
                        totalUpdates =
                            totalUpdates,

                        totalPhotos =
                            totalPhotos,

                        longestStreak =
                            longestStreak,

                        isLoading =
                            false,

                        errorMessage =
                            null
                    )

            } catch (e: Exception) {

                _stats.value =
                    _stats.value.copy(
                        isLoading = false,
                        errorMessage =
                            e.message
                                ?: "Failed to load achievements."
                    )
            }
        }
    }

    /**
     * Calculates the longest streak of consecutive days with at least one update.
     */
    private fun calculateLongestStreak(
        updates: List<SiteUpdate>
    ): Int {

        // Extract and sort distinct dates from updates
        val dates =
            updates
                .mapNotNull { update ->
                    try {
                        LocalDate.parse(
                            update.updateDate
                                .take(10)
                        )
                    } catch (_: Exception) {
                        null
                    }
                }
                .distinct()
                .sorted()

        if (dates.isEmpty()) {
            return 0
        }

        var longest =
            1

        var current =
            1

        // Iterate through dates to find consecutive sequences
        for (index in 1 until dates.size) {

            val previous =
                dates[index - 1]

            val currentDate =
                dates[index]

            val difference =
                ChronoUnit.DAYS.between(
                    previous,
                    currentDate
                )

            if (difference == 1L) {

                current++

                if (current > longest) {
                    longest = current
                }

            } else {

                current = 1
            }
        }

        return longest
    }
}
