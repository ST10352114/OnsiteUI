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
