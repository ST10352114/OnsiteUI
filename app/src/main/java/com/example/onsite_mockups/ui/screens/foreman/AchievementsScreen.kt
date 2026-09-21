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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onsite_mockups.ui.viewmodels.AchievementsViewModel

/**
 * Displays foreman achievements, streaks, and progress toward the next tier.
 * Motivate foremen through gamification based on report submission consistency.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AchievementsScreen(
    achievementsViewModel: AchievementsViewModel,
    onNavigateHome: () -> Unit,
    onNavigateProfile: () -> Unit
) {
    // Current tab selection for the bottom navigation
    var navTab by remember {
        mutableIntStateOf(1)
    }

    // Observe stats from ViewModel
    val stats by achievementsViewModel.stats.collectAsState()

    // Recalculate stats on screen entry
    LaunchedEffect(Unit) {
        achievementsViewModel.loadAchievements()
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
                        onNavigateProfile()
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

        if (stats.isLoading) {
            Box(
                modifier =
                    Modifier.fillMaxSize(),
                contentAlignment =
                    Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFFFF6D00)
                )
            }
        } else {

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
                        text = "Achievements",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1D20)
                    )

                    Text(
                        text = "You're a ${stats.tier}",
                        fontSize = 14.sp,
                        color = Color(0xFF6C757D)
                    )
                }

                // Tier Progress Card
                item {
                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(16.dp),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            ),
                        elevation =
                            CardDefaults.cardElevation(
                                defaultElevation = 1.dp
                            )
                    ) {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                        ) {
                            Row(
                                modifier =
                                    Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.SpaceBetween,
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stats.tier,
                                    fontSize = 16.sp,
                                    fontWeight =
                                        FontWeight.Bold,
                                    color =
                                        Color(0xFF1A1D20)
                                )

                                Text(
                                    text =
                                        "${stats.totalUpdates} reports",
                                    fontSize = 13.sp,
                                    color =
                                        Color(0xFF6C757D)
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(12.dp)
                            )

                            // Linear progress indicator for tier growth
                            LinearProgressIndicator(
                                progress = {
                                    stats.progressToNextTier
                                },
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(
                                            CircleShape
                                        ),
                                color =
                                    Color(0xFFFFC107),
                                trackColor =
                                    Color(0xFFF1F3F5)
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(12.dp)
                            )

                            if (stats.nextTierTarget != null) {
                                Text(
                                    text =
                                        "${stats.updatesUntilNextTier} more reports until next tier",
                                    fontSize = 12.sp,
                                    color =
                                        Color(0xFF6C757D)
                                )
                            } else {
                                Text(
                                    text = "Maximum tier reached!",
                                    fontSize = 12.sp,
                                    color =
                                        Color(0xFF2E7D32),
                                    fontWeight =
                                        FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // Main Statistics Row
                item {
                    Row(
                        modifier =
                            Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            value = stats.totalUpdates.toString(),
                            label = "Reports",
                            modifier =
                                Modifier.weight(1f)
                        )

                        StatCard(
                            value = stats.longestStreak.toString(),
                            label = "Best Streak",
                            modifier =
                                Modifier.weight(1f)
                        )

                        StatCard(
                            value = stats.totalPhotos.toString(),
                            label = "Photos",
                            modifier =
                                Modifier.weight(1f)
                        )
                    }
                }

                item {
                    Text(
                        text = "YOUR BADGES",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9AA0A6),
                        letterSpacing = 1.sp
                    )
                }

                // Badge collection grid
                item {
                    FlowRow(
                        modifier =
                            Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.spacedBy(12.dp),
                        verticalArrangement =
                            Arrangement.spacedBy(12.dp),
                        maxItemsInEachRow = 3
                    ) {
                        BadgeItem(
                            title = "First Report",
                            icon = Icons.Default.EmojiEvents,
                            isEarned = stats.firstUpdateEarned
                        )
                        BadgeItem(
                            title = "3-Day Streak",
                            icon = Icons.Default.Whatshot,
                            isEarned = stats.threeDayStreakEarned
                        )
                        BadgeItem(
                            title = "Photo Pro",
                            icon = Icons.Default.CameraAlt,
                            isEarned = stats.photoProEarned
                        )
                        BadgeItem(
                            title = "10 Reports",
                            icon = Icons.Default.Star,
                            isEarned = stats.tenUpdatesEarned
                        )
                        BadgeItem(
                            title = "Perfect Week",
                            icon = Icons.Default.DateRange,
                            isEarned = stats.perfectWeekEarned
                        )
                        BadgeItem(
                            title = "Master",
                            icon = Icons.Default.AutoAwesome,
                            isEarned = stats.twentyFiveUpdatesEarned
                        )
                    }
                }

                item {
                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )
                }
            }
        }
    }
}

/**
 * Reusable card for displaying a single numerical statistic.
 */
@Composable
private fun StatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1D20)
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color(0xFF6C757D)
            )
        }
    }
}

/**
 * Renders an achievement badge. Displays grayscale if not yet earned.
 */
@Composable
private fun BadgeItem(
    title: String,
    icon: ImageVector,
    isEarned: Boolean
) {
    Column(
        modifier = Modifier
            .width(90.dp)
            .alpha(if (isEarned) 1f else 0.4f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(if (isEarned) Color(0xFFFFF3E0) else Color(0xFFF1F3F5)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isEarned) Color(0xFFFFC107) else Color(0xFFADB5BD),
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (isEarned) Color(0xFF1A1D20) else Color(0xFF6C757D)
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
