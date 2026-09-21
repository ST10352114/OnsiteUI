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


package com.example.onsite_mockups.ui.screens.foreman

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.example.onsite_mockups.security.OnSiteBiometricManager
import com.example.onsite_mockups.ui.viewmodels.AuthViewModel

/**
 * Screen for managing foreman settings, preferences, and security options.
 */
@Composable
fun SettingsScreen(
    authViewModel: AuthViewModel,
    onNavigateHome: () -> Unit,
    onNavigateAchievements: () -> Unit,
    onNavigateAlerts: () -> Unit = {},
    onLogout: () -> Unit
) {
    // Current tab selection for the bottom navigation
    var navTab by remember {
        mutableIntStateOf(2)
    }

    // Observe user profile
    val currentProfile by authViewModel.currentProfile.collectAsState()

    val context = LocalContext.current
    val activity = context as? FragmentActivity

    // Manage biometric enrollment state
    var biometricEnabled by remember {
        mutableStateOf(OnSiteBiometricManager.isEnabled(context))
    }

    Scaffold(
        containerColor = Color(0xFFF9F9FB),
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                // Navigation items
                NavigationBarItem(
                    selected = navTab == 0,
                    onClick = {
                        navTab = 0
                        onNavigateHome()
                    },
                    icon = {
                        Icon(
                            Icons.Default.GridView,
                            contentDescription = "Home"
                        )
                    },
                    label = {
                        Text(
                            "Home",
                            fontSize = 11.sp
                        )
                    },
                    colors = navigationColors()
                )

                NavigationBarItem(
                    selected = navTab == 1,
                    onClick = {
                        navTab = 1
                        onNavigateAchievements()
                    },
                    icon = {
                        Icon(
                            Icons.Default.EmojiEvents,
                            contentDescription = "Achievements"
                        )
                    },
                    label = {
                        Text(
                            "Awards",
                            fontSize = 11.sp
                        )
                    },
                    colors = navigationColors()
                )

                NavigationBarItem(
                    selected = navTab == 2,
                    onClick = {
                        navTab = 2
                    },
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = {
                        Text(
                            "Profile",
                            fontSize = 11.sp
                        )
                    },
                    colors = navigationColors()
                )
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {

            item {
                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

                Text(
                    text = "Profile Settings",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1D20)
                )
            }

            // User Identity Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // User avatar box
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF1A1D20)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = currentProfile?.fullName?.take(2)?.uppercase() ?: "FM",
                                color = Color(0xFFFFC107),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = currentProfile?.fullName ?: "Foreman User",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A1D20)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = currentProfile?.email ?: "foreman@onsite.com",
                                fontSize = 13.sp,
                                color = Color(0xFF6C757D)
                            )
                        }
                    }
                }
            }

            // Preferences Section
            item {
                Spacer(modifier = Modifier.height(4.dp))
                SettingsSectionTitle(title = "PREFERENCES")
                Spacer(modifier = Modifier.height(8.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        SettingsNavigationItem(
                            icon = Icons.Default.Language,
                            title = "Language",
                            subtitle = "English (UK)",
                            onClick = {}
                        )

                        // Toggle for Biometric Authentication
                        SettingsSwitchItem(
                            icon = Icons.Default.Fingerprint,
                            title = "Biometric Login",
                            subtitle = "Enable Fingerprint or Face ID",
                            checked = biometricEnabled,
                            onCheckedChange = { enabled ->
                                if (!enabled) {
                                    OnSiteBiometricManager.disable(context)
                                    biometricEnabled = false
                                } else {
                                    val profile = currentProfile
                                    val bioActivity = activity
                                    if (profile != null && bioActivity != null) {
                                        OnSiteBiometricManager.authenticate(
                                            activity = bioActivity,
                                            title = "Enable biometrics",
                                            subtitle = "Verify to enable secure login",
                                            onSuccess = {
                                                OnSiteBiometricManager.enable(context, profile.id.toString())
                                                biometricEnabled = true
                                            }
                                        )
                                    }
                                }
                            }
                        )

                        SettingsNavigationItem(
                            icon = Icons.Default.Notifications,
                            title = "Notifications",
                            subtitle = "Alerts on for site updates",
                            onClick = {}
                        )
                    }
                }
            }

            // Security Section
            item {
                Spacer(modifier = Modifier.height(4.dp))
                SettingsSectionTitle(title = "SECURITY")
                Spacer(modifier = Modifier.height(8.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        SettingsNavigationItem(
                            icon = Icons.Default.Lock,
                            title = "Change Password",
                            subtitle = "Last changed 2 months ago",
                            onClick = {}
                        )
                    }
                }
            }

            // Logout Button
            item {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFFFCDD2)),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, tint = Color(0xFFD32F2F), modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Log Out", color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

/**
 * Renders a header for a settings group.
 */
@Composable
fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF9AA0A6),
        letterSpacing = 1.sp,
        modifier = Modifier.padding(start = 4.dp)
    )
}

/**
 * Standard settings row with a chevron for navigation.
 */
@Composable
fun SettingsNavigationItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF495057), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1D20))
            Text(text = subtitle, fontSize = 12.sp, color = Color(0xFF6C757D))
        }
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFFADB5BD), modifier = Modifier.size(20.dp))
    }
}

/**
 * Settings row with an inline switch for boolean preferences.
 */
@Composable
fun SettingsSwitchItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF495057), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1D20))
            Text(text = subtitle, fontSize = 12.sp, color = Color(0xFF6C757D))
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFFFF6D00))
        )
    }
}

@Composable
private fun navigationColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = Color(0xFFFF6D00),
    selectedTextColor = Color(0xFFFF6D00),
    unselectedIconColor = Color(0xFF9AA0A6),
    unselectedTextColor = Color(0xFF9AA0A6),
    indicatorColor = Color.Transparent
)
