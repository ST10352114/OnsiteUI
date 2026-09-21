package com.example.onsite_mockups.ui.viewmodels

import app.cash.turbine.test
import com.example.onsite_mockups.MainDispatcherRule
import com.example.onsite_mockups.data.network.SupabaseClient
import io.github.jan.supabase.SupabaseClient as JanSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: AuthViewModel
    private val mockSupabaseClient = mockk<JanSupabaseClient>()
    private val mockAuth = mockk<Auth>()

    @Before
    fun setup() {
        mockkStatic("io.github.jan.supabase.gotrue.AuthKt")
        mockkObject(SupabaseClient)
        every { SupabaseClient.client } returns mockSupabaseClient
        every { mockSupabaseClient.auth } returns mockAuth
        
        viewModel = AuthViewModel()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `login with empty fields sets error state`() = runTest {
        viewModel.login("", "") { }
        
        viewModel.loginState.test {
            val state = awaitItem()
            assertTrue(state is LoginState.Error)
            assertTrue((state as LoginState.Error).message.contains("empty"))
        }
    }
}
