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



package com.example.onsite_mockups

import com.example.onsite_mockups.ui.viewmodels.AchievementsViewModel
import com.example.onsite_mockups.data.network.SupabaseClient
import com.example.onsite_mockups.ui.screens.shared.NotificationScreen
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.example.onsite_mockups.security.OnSiteBiometricManager
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
import androidx.fragment.app.FragmentActivity

/**
 * The entry point activity for the OnSite application.
 * Manages the main navigation host, deep linking, and permission requests.
 */
class MainActivity : FragmentActivity() {

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

        // Initialize Supabase deep link handling
        SupabaseClient
            .client
            .handleDeeplinks(intent)

        // Enable edge-to-edge UI layout
        enableEdgeToEdge()

        // Prompt for notification permissions on supported versions
        requestNotificationPermission()

        // Check if the activity was launched via a Google login callback
        val isGoogleCallback =
            savedInstanceState == null &&
                    intent?.data?.scheme == "onsite" &&
                    intent?.data?.host == "login-callback"

        setContent {

            OnSiteMockupsTheme {

                val navController =
                    rememberNavController()

                // Initialize shared ViewModels
                val authViewModel:
                        AuthViewModel =
                    viewModel()

                val foremanViewModel:
                        ForemanViewModel =
                    viewModel()

                val achievementsViewModel:
                        AchievementsViewModel =
                    viewModel()

                val adminViewModel:
                        AdminViewModel =
                    viewModel()

                Scaffold(
                    modifier =
                        Modifier.fillMaxSize()
                ) { innerPadding ->

                    // Application-wide Navigation Host
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

                        // Notifications Screen
                        composable(
                            Screen.Notifications.route
                        ) {

                            NotificationScreen(
                                onBackClick = {
                                    navController.popBackStack()
                                },

                                onNotificationClick = { notification ->

                                    // Parse data from notification for navigation
                                    val data =
                                        try {
                                            kotlinx.serialization.json.Json
                                                .decodeFromString<
                                                        Map<String, String>
                                                        >(
                                                    notification.data
                                                )
                                        } catch (_: Exception) {
                                            emptyMap()
                                        }

                                    when (
                                        notification.type
                                    ) {

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

                        // Splash Screen
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

                        // Login Screen
                        composable(
                            Screen.Login.route
                        ) {

                            LoginScreen(
                                authViewModel =
                                    authViewModel,

                                isGoogleCallback =
                                    isGoogleCallback,

                                onLoginSuccess = { profile ->

                                    // Route user based on their role
                                    val destination =
                                        if (
                                            profile.role ==
                                            "admin"
                                        ) {

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
                                            inclusive =
                                                true
                                        }
                                    }
                                }
                            )
                        }

                        // Administrator Dashboard
                        composable(
                            Screen.AdminDashboard.route
                        ) {

                            AdminDashboardScreen(
                                adminViewModel =
                                    adminViewModel,

                                onUpdateClick = { updateId ->

                                    adminViewModel
                                        .selectUpdate(
                                            updateId
                                        )

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
                                },

                                onAlertsClick = {

                                    navController.navigate(
                                        Screen.Notifications.route
                                    )
                                }
                            )
                        }

                        // Administrator Sites and Crew Management
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
                                },

                                onNavigateAlerts = {

                                    navController.navigate(
                                        Screen.Notifications.route
                                    )
                                }
                            )
                        }

                        // Administrator Settings Screen
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

                                onNavigateAlerts = {

                                    navController.navigate(
                                        Screen.Notifications.route
                                    )
                                },

                                onLogout = {

                                    authViewModel.logout {

                                        navController.navigate(
                                            Screen.Login.route
                                        ) {

                                            popUpTo(0) {
                                                inclusive = true
                                            }

                                            launchSingleTop = true
                                        }
                                    }
                                }
                            )
                        }

                        // Administrator Site Update Detail View
                        composable(
                            Screen.AdminUpdateDetail.route
                        ) {

                            AdminUpdateDetailScreen(
                                adminViewModel =
                                    adminViewModel,

                                onBackClick = {
                                    navController.popBackStack()
                                },

                                onExportClick = {}
                            )
                        }

                        // Foreman Home Screen
                        composable(
                            Screen.ForemanHome.route
                        ) {

                            ForemanHomeScreen(
                                foremanViewModel =
                                    foremanViewModel,

                                profile =
                                    authViewModel
                                        .currentProfile
                                        .collectAsState()
                                        .value,

                                onSiteClick = { siteId ->

                                    if (
                                        siteId.isNotBlank()
                                    ) {

                                        foremanViewModel
                                            .selectSite(
                                                siteId
                                            )

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
                                },

                                onAlertsClick = {

                                    navController.navigate(
                                        Screen.Notifications.route
                                    )
                                }
                            )
                        }

                        // Foreman Achievements Screen
                        composable(
                            Screen.Achievements.route
                        ) {

                            AchievementsScreen(
                                achievementsViewModel =
                                    achievementsViewModel,

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

                        // Foreman Settings Screen
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

                                onNavigateAlerts = {

                                    navController.navigate(
                                        Screen.Notifications.route
                                    )
                                },

                                onLogout = {

                                    authViewModel.logout {

                                        navController.navigate(
                                            Screen.Login.route
                                        ) {

                                            popUpTo(0) {
                                                inclusive = true
                                            }

                                            launchSingleTop = true
                                        }
                                    }
                                }
                            )
                        }

                        // Foreman Daily Update Form
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

                        // Success confirmation after syncing update
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

                        // Offline mode indication (if connectivity is lost)
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

                        // Placeholder for future expansion
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

    /**
     * Requests POST_NOTIFICATIONS permission on Android 13+ (API 33).
     */
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

