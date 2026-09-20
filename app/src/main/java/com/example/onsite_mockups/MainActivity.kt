package com.example.onsite_mockups

import com.example.onsite_mockups.data.network.SupabaseClient
import com.example.onsite_mockups.ui.screens.shared.NotificationScreen
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.onsite_mockups.ui.viewmodels.AdminViewModel
import com.example.onsite_mockups.ui.viewmodels.AuthViewModel
import com.example.onsite_mockups.ui.viewmodels.ForemanViewModel
import io.github.jan.supabase.gotrue.handleDeeplinks

class MainActivity : ComponentActivity() {

    companion object {

        private const val NOTIFICATION_PERMISSION_REQUEST_CODE =
            1001

        private const val TAG =
            "OnSiteNotifications"
    }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState

        )
        SupabaseClient
            .client
            .handleDeeplinks(intent)

        enableEdgeToEdge()

        requestNotificationPermission()

        setContent {

            OnSiteMockupsTheme {

                val navController =
                    rememberNavController()

                val authViewModel:
                        AuthViewModel =
                    viewModel()

                val foremanViewModel:
                        ForemanViewModel =
                    viewModel()

                val adminViewModel:
                        AdminViewModel =
                    viewModel()

                Scaffold(
                    modifier =
                        Modifier.fillMaxSize()
                ) { innerPadding ->

                    NavHost(
                        navController =
                            navController,

                        startDestination =
                            Screen.Splash.route,

                        modifier =
                            Modifier.padding(
                                innerPadding
                            )
                    ) {
                        composable(
                            Screen.Notifications.route
                        ) {
                            NotificationScreen(
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onNotificationClick = { notification ->

                                    val data =
                                        try {
                                            kotlinx.serialization.json.Json
                                                .decodeFromString<
                                                        Map<String, String>
                                                        >(notification.data)
                                        } catch (_: Exception) {
                                            emptyMap()
                                        }

                                    when (notification.type) {

                                        "daily_report_submitted" -> {
                                            val updateId =
                                                data["update_id"]

                                            if (
                                                !updateId.isNullOrBlank()
                                            ) {
                                                adminViewModel
                                                    .selectUpdate(
                                                        updateId
                                                    )

                                                navController.navigate(
                                                    Screen.AdminUpdateDetail.route
                                                )
                                            }
                                        }

                                        "site_assigned" -> {
                                            navController.popBackStack()
                                        }
                                    }
                                }
                            )
                        }
                        composable(
                            Screen.Splash.route
                        ) {

                            SplashScreen(
                                onNavigateToNext = {

                                    navController.navigate(
                                        Screen.Login.route
                                    ) {

                                        popUpTo(
                                            Screen.Splash.route
                                        ) {
                                            inclusive =
                                                true
                                        }
                                    }
                                }
                            )
                        }

                        composable(
                            Screen.Login.route
                        ) {

                            LoginScreen(
                                authViewModel =
                                    authViewModel,

                                onGoogleLoginSuccess = { profile ->

                                    val destination =
                                        if (profile.role == "admin") {

                                            adminViewModel
                                                .loadAdminData()

                                            Screen.AdminDashboard.route

                                        } else {

                                            foremanViewModel
                                                .setForemanProfile(
                                                    profile
                                                )

                                            foremanViewModel
                                                .loadForemanData()

                                            Screen.ForemanHome.route
                                        }

                                    navController.navigate(
                                        destination
                                    ) {
                                        popUpTo(
                                            Screen.Login.route
                                        ) {
                                            inclusive = true
                                        }
                                    }
                                },

                                onLoginSuccess = {
                                    val profile =
                                        authViewModel
                                            .currentProfile
                                            .value

                                    val destination =
                                        if (profile?.role == "admin") {

                                            adminViewModel
                                                .loadAdminData()

                                            Screen.AdminDashboard.route

                                        } else {

                                            foremanViewModel
                                                .setForemanProfile(
                                                    profile
                                                )

                                            foremanViewModel
                                                .loadForemanData()

                                            Screen.ForemanHome.route
                                        }

                                    navController.navigate(
                                        destination
                                    ) {
                                        popUpTo(
                                            Screen.Login.route
                                        ) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

                        composable(
                            Screen.AdminDashboard.route
                        ) {

                            AdminDashboardScreen(
                                adminViewModel = adminViewModel,

                                onUpdateClick = { updateId ->

                                    adminViewModel
                                        .selectUpdate(updateId)

                                    navController.navigate(
                                        Screen.AdminUpdateDetail.route
                                    )
                                },

                                onSitesCrewClick = {
                                    navController.navigate(
                                        Screen.SitesAndCrew.route
                                    )
                                },

                                onProfileClick = {
                                    navController.navigate(
                                        Screen.AdminSettings.route
                                    )
                                },

                                onNotificationClick = {
                                    navController.navigate(
                                        Screen.Notifications.route
                                    )
                                }
                            )
                        }

                        composable(
                            Screen.SitesAndCrew.route
                        ) {

                            SitesAndCrewScreen(
                                adminViewModel =
                                    adminViewModel,

                                onNavigateDashboard = {

                                    navController.navigate(
                                        Screen.AdminDashboard.route
                                    ) {

                                        popUpTo(
                                            Screen.AdminDashboard.route
                                        ) {
                                            inclusive =
                                                true
                                        }
                                    }
                                },

                                onNavigateProfile = {

                                    navController.navigate(
                                        Screen.AdminSettings.route
                                    )
                                }
                            )
                        }

                        composable(
                            Screen.AdminSettings.route
                        ) {

                            AdminSettingsScreen(
                                authViewModel =
                                    authViewModel,

                                onNavigateDashboard = {

                                    navController.navigate(
                                        Screen.AdminDashboard.route
                                    ) {

                                        popUpTo(
                                            Screen.AdminDashboard.route
                                        ) {
                                            inclusive =
                                                true
                                        }
                                    }
                                },

                                onNavigateSitesCrew = {

                                    navController.navigate(
                                        Screen.SitesAndCrew.route
                                    ) {

                                        popUpTo(
                                            Screen.SitesAndCrew.route
                                        ) {
                                            inclusive =
                                                true
                                        }
                                    }
                                },

                                onLogout = {

                                    authViewModel.logout {

                                        navController.navigate(
                                            Screen.Login.route
                                        ) {

                                            popUpTo(
                                                Screen.AdminDashboard.route
                                            ) {
                                                inclusive =
                                                    true
                                            }
                                        }
                                    }
                                }
                            )
                        }

                        composable(
                            Screen.AdminUpdateDetail.route
                        ) {

                            AdminUpdateDetailScreen(
                                adminViewModel =
                                    adminViewModel,

                                onBackClick = {
                                    navController.popBackStack()
                                },

                                onExportClick = {},

                                onFlagForReviewClick = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(
                            Screen.ForemanHome.route
                        ) {

                            ForemanHomeScreen(
                                foremanViewModel = foremanViewModel,

                                profile =
                                    authViewModel
                                        .currentProfile
                                        .collectAsState()
                                        .value,

                                onSiteClick = { siteId ->

                                    if (siteId.isNotBlank()) {

                                        foremanViewModel
                                            .selectSite(siteId)

                                        navController.navigate(
                                            Screen.DailyUpdateForm.route
                                        )
                                    }
                                },

                                onAchievementsClick = {
                                    navController.navigate(
                                        Screen.Achievements.route
                                    )
                                },

                                onProfileClick = {
                                    navController.navigate(
                                        Screen.Settings.route
                                    )
                                },

                                onNotificationClick = {
                                    navController.navigate(
                                        Screen.Notifications.route
                                    )
                                }
                            )
                        }

                        composable(
                            Screen.Achievements.route
                        ) {

                            AchievementsScreen(
                                onNavigateHome = {

                                    navController.navigate(
                                        Screen.ForemanHome.route
                                    ) {

                                        popUpTo(
                                            Screen.ForemanHome.route
                                        ) {
                                            inclusive =
                                                true
                                        }
                                    }
                                },

                                onNavigateProfile = {

                                    navController.navigate(
                                        Screen.Settings.route
                                    )
                                }
                            )
                        }

                        composable(
                            Screen.Settings.route
                        ) {

                            SettingsScreen(
                                authViewModel =
                                    authViewModel,

                                onNavigateHome = {

                                    navController.navigate(
                                        Screen.ForemanHome.route
                                    ) {

                                        popUpTo(
                                            Screen.ForemanHome.route
                                        ) {
                                            inclusive =
                                                true
                                        }
                                    }
                                },

                                onNavigateAchievements = {

                                    navController.navigate(
                                        Screen.Achievements.route
                                    ) {

                                        popUpTo(
                                            Screen.Achievements.route
                                        ) {
                                            inclusive =
                                                true
                                        }
                                    }
                                },

                                onLogout = {

                                    navController.navigate(
                                        Screen.Login.route
                                    ) {

                                        popUpTo(
                                            Screen.ForemanHome.route
                                        ) {
                                            inclusive =
                                                true
                                        }
                                    }
                                }
                            )
                        }

                        composable(
                            Screen.DailyUpdateForm.route
                        ) {

                            DailyUpdateFormScreen(
                                foremanViewModel =
                                    foremanViewModel,

                                onBackClick = {
                                    navController.popBackStack()
                                },

                                onSubmitSuccess = {

                                    navController.navigate(
                                        Screen.UpdateSynced.route
                                    ) {

                                        popUpTo(
                                            Screen.DailyUpdateForm.route
                                        ) {
                                            inclusive =
                                                true
                                        }
                                    }
                                }
                            )
                        }

                        composable(
                            Screen.UpdateSynced.route
                        ) {

                            UpdateSyncedScreen(
                                onBackToSites = {

                                    navController.navigate(
                                        Screen.ForemanHome.route
                                    ) {

                                        popUpTo(
                                            Screen.ForemanHome.route
                                        ) {
                                            inclusive =
                                                true
                                        }
                                    }
                                }
                            )
                        }

                        composable(
                            Screen.UpdateOffline.route
                        ) {

                            UpdateOfflineScreen(
                                onBackToSites = {

                                    navController.navigate(
                                        Screen.ForemanHome.route
                                    ) {

                                        popUpTo(
                                            Screen.ForemanHome.route
                                        ) {
                                            inclusive =
                                                true
                                        }
                                    }
                                }
                            )
                        }

                        composable(
                            Screen.PlaceholderNext.route
                        ) {

                            Box(
                                modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .background(
                                            Color(
                                                0xFF14171A
                                            )
                                        ),

                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Text(
                                    text =
                                        "Placeholder",

                                    color =
                                        Color.White,

                                    fontSize =
                                        18.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun requestNotificationPermission() {

        if (
            Build.VERSION.SDK_INT <
            Build.VERSION_CODES.TIRAMISU
        ) {
            return
        }

        val permission =
            Manifest.permission.POST_NOTIFICATIONS

        val alreadyGranted =
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) ==
                    PackageManager.PERMISSION_GRANTED

        if (alreadyGranted) {

            Log.d(
                TAG,
                "Notification permission already granted."
            )

            return
        }

        ActivityCompat.requestPermissions(
            this,
            arrayOf(permission),
            NOTIFICATION_PERMISSION_REQUEST_CODE
        )
    }
}