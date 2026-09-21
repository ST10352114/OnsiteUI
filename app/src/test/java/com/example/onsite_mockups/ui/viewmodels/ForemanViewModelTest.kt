package com.example.onsite_mockups.ui.viewmodels

import app.cash.turbine.test
import com.example.onsite_mockups.MainDispatcherRule
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.models.Site
import com.example.onsite_mockups.data.models.SiteUpdate
import com.example.onsite_mockups.data.repository.OnSiteRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
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
class ForemanViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ForemanViewModel

    @Before
    fun setup() {
        mockkObject(OnSiteRepository)
        viewModel = ForemanViewModel()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `setForemanProfile updates foremanName with profile fullName`() = runTest {
        val profile = Profile(fullName = "John Doe", role = "foreman", email = "john@example.com")
        
        viewModel.setForemanProfile(profile)
        
        assertEquals("John Doe", viewModel.foremanName.value)
    }

    @Test
    fun `setForemanProfile defaults to Foreman when fullName is null or blank`() = runTest {
        val profileWithNullName = Profile(fullName = null, role = "foreman", email = "john@example.com")
        val profileWithBlankName = Profile(fullName = " ", role = "foreman", email = "john@example.com")
        
        viewModel.setForemanProfile(profileWithNullName)
        assertEquals("Foreman", viewModel.foremanName.value)
        
        viewModel.setForemanProfile(profileWithBlankName)
        assertEquals("Foreman", viewModel.foremanName.value)
    }

    @Test
    fun `loadForemanData updates sites and updates flows`() = runTest {
        val mockSites = listOf(mockk<Site>())
        val mockUpdates = listOf(mockk<SiteUpdate>())
        
        coEvery { OnSiteRepository.getSites() } returns mockSites
        coEvery { OnSiteRepository.getSiteUpdates() } returns mockUpdates
        
        viewModel.loadForemanData()
        
        viewModel.sites.test {
            assertEquals(mockSites, awaitItem())
        }
        viewModel.updates.test {
            assertEquals(mockUpdates, awaitItem())
        }
    }

    @Test
    fun `loadForemanData handles errors and sets errorMessage`() = runTest {
        coEvery { OnSiteRepository.getSites() } throws Exception("Network Error")
        
        viewModel.loadForemanData()
        
        viewModel.errorMessage.test {
            assertEquals("Network Error", awaitItem())
        }
    }
}
