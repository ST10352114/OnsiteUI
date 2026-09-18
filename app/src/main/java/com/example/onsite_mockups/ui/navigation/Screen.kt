package com.example.onsite_mockups.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object ForemanHome : Screen("foreman_home")
    object DailyUpdateForm : Screen("daily_update_form")
    object UpdateSynced : Screen("update_synced")
    object UpdateOffline : Screen("update_offline")
    object Achievements : Screen("achievements")
    object Settings : Screen("settings")
    object AdminDashboard : Screen("admin_dashboard")
    object AdminUpdateDetail : Screen("admin_update_detail")
    object SitesAndCrew : Screen("sites_and_crew")
    object AdminSettings : Screen("admin_settings")
    object PlaceholderNext : Screen("placeholder_next")
}
