package com.example.onsite_mockups

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onsite_mockups.ui.navigation.Screen
import com.example.onsite_mockups.ui.screens.admin.AdminDashboardScreen
import com.example.onsite_mockups.ui.screens.admin.AdminSettingsScreen
import com.example.onsite_mockups.ui.screens.admin.AdminUpdateDetailScreen
import com.example.onsite_mockups.ui.screens.admin.SitesAndCrewScreen
import com.example.onsite_mockups.ui.screens.foreman.AchievementsScreen
import com.example.onsite_mockups.ui.screens.foreman.DailyUpdateFormScreen
import com.example.onsite_mockups.ui.screens.foreman.ForemanHomeScreen
import com.example.onsite_mockups.ui.screens.foreman.SettingsScreen
import com.example.onsite_mockups.ui.screens.foreman.UpdateOfflineScreen
import com.example.onsite_mockups.ui.screens.foreman.UpdateSyncedScreen
import com.example.onsite_mockups.ui.screens.shared.LoginScreen
import com.example.onsite_mockups.ui.screens.shared.SplashScreen
import com.example.onsite_mockups.ui.theme.OnSiteMockupsTheme

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onsite_mockups.ui.viewmodels.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnSiteMockupsTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()
                val foremanViewModel: ForemanViewModel = viewModel()
                val adminViewModel: AdminViewModel = viewModel()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Splash.route) {
                            SplashScreen(
                                onNavigateToNext = {
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(Screen.Splash.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.Login.route) {
                            LoginScreen(
                                authViewModel = authViewModel,
                                onLoginSuccess = { fullName ->
                                    val profile = authViewModel.currentProfile.value
                                    val destination = if (profile?.role == "admin") {
                                        adminViewModel.loadAdminData()
                                        Screen.AdminDashboard.route
                                    } else {
                                        foremanViewModel.loadForemanData()
                                        Screen.ForemanHome.route
                                    }
                                    navController.navigate(destination) {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.AdminDashboard.route) {
                            AdminDashboardScreen(
                                adminViewModel = adminViewModel,
                                onUpdateClick = { updateId ->
                                    adminViewModel.selectUpdate(updateId)
                                    navController.navigate(Screen.AdminUpdateDetail.route)
                                },
                                onSitesCrewClick = {
                                    navController.navigate(Screen.SitesAndCrew.route)
                                },
                                onProfileClick = {
                                    navController.navigate(Screen.AdminSettings.route)
                                }
                            )
                        }
                        composable(Screen.SitesAndCrew.route) {
                            SitesAndCrewScreen(
                                adminViewModel = adminViewModel,
                                onNavigateDashboard = {
                                    navController.navigate(Screen.AdminDashboard.route) {
                                        popUpTo(Screen.AdminDashboard.route) { inclusive = true }
                                    }
                                },
                                onNavigateProfile = {
                                    navController.navigate(Screen.AdminSettings.route)
                                }
                            )
                        }
                        composable(Screen.AdminSettings.route) {
                            AdminSettingsScreen(
                                onNavigateDashboard = {
                                    navController.navigate(Screen.AdminDashboard.route) {
                                        popUpTo(Screen.AdminDashboard.route) { inclusive = true }
                                    }
                                },
                                onNavigateSitesCrew = {
                                    navController.navigate(Screen.SitesAndCrew.route) {
                                        popUpTo(Screen.SitesAndCrew.route) { inclusive = true }
                                    }
                                },
                                onLogout = {
                                    authViewModel.logout {
                                        navController.navigate(Screen.Login.route) {
                                            popUpTo(Screen.AdminDashboard.route) { inclusive = true }
                                        }
                                    }
                                }
                            )
                        }
                        composable(Screen.AdminUpdateDetail.route) {
                            AdminUpdateDetailScreen(
                                adminViewModel = adminViewModel,
                                onBackClick = { navController.popBackStack() },
                                onExportClick = {},
                                onFlagForReviewClick = { navController.popBackStack() }
                            )
                        }
                        composable(Screen.ForemanHome.route) {
                            ForemanHomeScreen(
                                foremanViewModel = foremanViewModel,
                                onSiteClick = { siteId ->
                                    foremanViewModel.selectSite(siteId)
                                    if (siteId == "palmgrove") {
                                        navController.navigate(Screen.UpdateOffline.route)
                                    } else {
                                        navController.navigate(Screen.DailyUpdateForm.route)
                                    }
                                },
                                onAchievementsClick = {
                                    navController.navigate(Screen.Achievements.route)
                                },
                                onProfileClick = {
                                    navController.navigate(Screen.Settings.route)
                                }
                            )
                        }
                        composable(Screen.Achievements.route) {
                            AchievementsScreen(
                                onNavigateHome = {
                                    navController.navigate(Screen.ForemanHome.route) {
                                        popUpTo(Screen.ForemanHome.route) { inclusive = true }
                                    }
                                },
                                onNavigateProfile = {
                                    navController.navigate(Screen.Settings.route)
                                }
                            )
                        }
                        composable(Screen.Settings.route) {
                            SettingsScreen(
                                onNavigateHome = {
                                    navController.navigate(Screen.ForemanHome.route) {
                                        popUpTo(Screen.ForemanHome.route) { inclusive = true }
                                    }
                                },
                                onNavigateAchievements = {
                                    navController.navigate(Screen.Achievements.route) {
                                        popUpTo(Screen.Achievements.route) { inclusive = true }
                                    }
                                },
                                onLogout = {
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(Screen.ForemanHome.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.DailyUpdateForm.route) {
                            DailyUpdateFormScreen(
                                foremanViewModel = foremanViewModel,
                                onBackClick = { navController.popBackStack() },
                                onSubmitSuccess = {
                                    navController.navigate(Screen.UpdateSynced.route) {
                                        popUpTo(Screen.DailyUpdateForm.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.UpdateSynced.route) {
                            UpdateSyncedScreen(
                                onBackToSites = {
                                    navController.navigate(Screen.ForemanHome.route) {
                                        popUpTo(Screen.ForemanHome.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.UpdateOffline.route) {
                            UpdateOfflineScreen(
                                onBackToSites = {
                                    navController.navigate(Screen.ForemanHome.route) {
                                        popUpTo(Screen.ForemanHome.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.PlaceholderNext.route) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xFF14171A)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Placeholder",
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
