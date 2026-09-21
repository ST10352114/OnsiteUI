package com.example.onsite_mockups.ui.viewmodels

import app.cash.turbine.test
import com.example.onsite_mockups.MainDispatcherRule
import com.example.onsite_mockups.data.models.SiteUpdate
import com.example.onsite_mockups.data.repository.OnSiteRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AchievementsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: AchievementsViewModel

    @Before
    fun setup() {
        mockkObject(OnSiteRepository)
        viewModel = AchievementsViewModel()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `loadAchievements updates stats with correctly calculated values`() = runTest {
        val updates = listOf(
            SiteUpdate(id = "1", siteId = "s1", foremanId = "f1", updateDate = "2024-01-01", forecastedLabor = 5, bricklayers = 2, plasterers = 2, pavers = 1, actualLabor = 5, updatePhotos = listOf(mockk())),
            SiteUpdate(id = "2", siteId = "s1", foremanId = "f1", updateDate = "2024-01-02", forecastedLabor = 5, bricklayers = 2, plasterers = 2, pavers = 1, actualLabor = 5, updatePhotos = listOf(mockk(), mockk())),
            SiteUpdate(id = "3", siteId = "s1", foremanId = "f1", updateDate = "2024-01-04", forecastedLabor = 5, bricklayers = 2, plasterers = 2, pavers = 1, actualLabor = 5, updatePhotos = emptyList())
        )
        
        coEvery { OnSiteRepository.getSiteUpdates() } returns updates
        
        viewModel.loadAchievements()
        
        viewModel.stats.test {
            val stats = awaitItem()
            assertEquals(3, stats.totalUpdates)
            assertEquals(3, stats.totalPhotos)
            assertEquals(2, stats.longestStreak) // Jan 1st and 2nd are consecutive
        }
    }

    @Test
    fun `AchievementStats tier calculation logic`() {
        val statsBronze = AchievementStats(totalUpdates = 5)
        assertEquals("Bronze Foreman", statsBronze.tier)
        
        val statsSilver = AchievementStats(totalUpdates = 10)
        assertEquals("Silver Foreman", statsSilver.tier)
        
        val statsGold = AchievementStats(totalUpdates = 25)
        assertEquals("Gold Foreman", statsGold.tier)
    }

    @Test
    fun `AchievementStats badges earned calculation`() {
        val stats = AchievementStats(totalUpdates = 1, longestStreak = 3, totalPhotos = 10)
        // Earned: firstUpdate, threeDayStreak, photoPro
        assertEquals(3, stats.earnedBadgeCount)
    }
}
