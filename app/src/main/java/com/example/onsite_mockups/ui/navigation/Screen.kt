package com.example.onsite_mockups.ui.navigation

/**
 * Sealed class defining all available screen routes in the application.
 * Used for Jetpack Compose Navigation.
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")

    // Foreman-specific screens
    object ForemanHome : Screen("foreman_home")
    object DailyUpdateForm : Screen("daily_update_form")
    object UpdateSynced : Screen("update_synced")
    object UpdateOffline : Screen("update_offline")
    object Achievements : Screen("achievements")
    object Settings : Screen("settings")

    // Administrator-specific screens
    object AdminDashboard : Screen("admin_dashboard")
    object AdminUpdateDetail : Screen("admin_update_detail")
    object SitesAndCrew : Screen("sites_and_crew")
    object AdminSettings : Screen("admin_settings")

    // Shared screens
    object Notifications : Screen("notifications")

    // Temporary placeholder for development
    object PlaceholderNext : Screen("placeholder_next")
}
