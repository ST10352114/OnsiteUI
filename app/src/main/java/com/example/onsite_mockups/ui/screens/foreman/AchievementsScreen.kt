package com.example.onsite_mockups.ui.screens.foreman

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onsite_mockups.ui.viewmodels.AchievementStats
import com.example.onsite_mockups.ui.viewmodels.AchievementsViewModel

@Composable
fun AchievementsScreen(
    achievementsViewModel: AchievementsViewModel,
    onNavigateHome: () -> Unit,
    onNavigateAlerts: () -> Unit = {},
    onNavigateProfile: () -> Unit = {}
) {
    var selectedTab by remember {
        mutableIntStateOf(1)
    }

    val stats by
    achievementsViewModel.stats.collectAsState()

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
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                        onNavigateHome()
                    },
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = {
                        Text(
                            "Home",
                            fontSize = 11.sp
                        )
                    },
                    colors =
                        NavigationBarItemDefaults.colors(
                            selectedIconColor =
                                Color(0xFFFF6D00),
                            selectedTextColor =
                                Color(0xFFFF6D00),
                            unselectedIconColor =
                                Color(0xFF9AA0A6),
                            unselectedTextColor =
                                Color(0xFF9AA0A6),
                            indicatorColor =
                                Color.Transparent
                        )
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                    },
                    icon = {
                        Icon(
                            Icons.Default.EmojiEvents,
                            contentDescription =
                                "Achievements"
                        )
                    },
                    label = {
                        Text(
                            "Achievements",
                            fontSize = 11.sp
                        )
                    },
                    colors =
                        NavigationBarItemDefaults.colors(
                            selectedIconColor =
                                Color(0xFFFF6D00),
                            selectedTextColor =
                                Color(0xFFFF6D00),
                            unselectedIconColor =
                                Color(0xFF9AA0A6),
                            unselectedTextColor =
                                Color(0xFF9AA0A6),
                            indicatorColor =
                                Color.Transparent
                        )
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        onNavigateAlerts()
                    },
                    icon = {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Alerts"
                        )
                    },
                    label = {
                        Text(
                            "Alerts",
                            fontSize = 11.sp
                        )
                    },
                    colors =
                        NavigationBarItemDefaults.colors(
                            selectedIconColor =
                                Color(0xFFFF6D00),
                            selectedTextColor =
                                Color(0xFFFF6D00),
                            unselectedIconColor =
                                Color(0xFF9AA0A6),
                            unselectedTextColor =
                                Color(0xFF9AA0A6),
                            indicatorColor =
                                Color.Transparent
                        )
                )

                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = {
                        selectedTab = 3
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
                    colors =
                        NavigationBarItemDefaults.colors(
                            selectedIconColor =
                                Color(0xFFFF6D00),
                            selectedTextColor =
                                Color(0xFFFF6D00),
                            unselectedIconColor =
                                Color(0xFF9AA0A6),
                            unselectedTextColor =
                                Color(0xFF9AA0A6),
                            indicatorColor =
                                Color.Transparent
                        )
                )
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp),
            verticalArrangement =
                Arrangement.spacedBy(20.dp)
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

                Spacer(
                    modifier =
                        Modifier.height(2.dp)
                )

                Text(
                    text =
                        "Keep logging updates to level up",
                    fontSize = 14.sp,
                    color = Color(0xFF6C757D)
                )
            }

            item {

                if (stats.isLoading) {

                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(20.dp),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color(0xFF1A1D20)
                            )
                    ) {

                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(180.dp),
                            contentAlignment =
                                Alignment.Center
                        ) {

                            CircularProgressIndicator(
                                color =
                                    Color(0xFFFFC107)
                            )
                        }
                    }

                } else {

                    TierCard(
                        stats = stats
                    )
                }
            }

            item {

                TierLegendItemRow()
            }

            item {

                Text(
                    text =
                        "BADGES EARNED — ${stats.earnedBadgeCount} OF 6",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9AA0A6),
                    letterSpacing = 1.sp
                )
            }

            item {

                if (stats.errorMessage != null) {

                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(16.dp),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color(0xFFFFEBEE)
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                        ) {

                            Text(
                                text =
                                    stats.errorMessage
                                        ?: "Failed to load achievements.",
                                color =
                                    Color(0xFFD32F2F),
                                fontSize = 13.sp
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(10.dp)
                            )

                            Button(
                                onClick = {
                                    achievementsViewModel
                                        .loadAchievements()
                                },
                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor =
                                            Color(0xFFFF6D00)
                                    )
                            ) {
                                Text("Retry")
                            }
                        }
                    }

                } else {

                    BadgesGrid(
                        stats = stats
                    )
                }
            }

            item {

                Spacer(
                    modifier =
                        Modifier.height(24.dp)
                )
            }
        }
    }
}

@Composable
private fun TierCard(
    stats: AchievementStats
) {
    Card(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(20.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    Color(0xFF1A1D20)
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
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

                Column {

                    Text(
                        text = "CURRENT TIER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFC107),
                        letterSpacing = 1.sp
                    )

                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )

                    Text(
                        text = stats.tier,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Box(
                    modifier =
                        Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                Color(0xFF343A40)
                            ),
                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.EmojiEvents,
                        contentDescription =
                            "Trophy",
                        tint =
                            Color(0xFFE9ECEF),
                        modifier =
                            Modifier.size(24.dp)
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Text(
                    text =
                        "${stats.totalUpdates} updates logged",
                    fontSize = 12.sp,
                    color = Color(0xFF9AA0A6)
                )

                Text(
                    text =
                        if (
                            stats.nextTierTarget != null
                        ) {
                            "${stats.nextTierTarget} for next tier"
                        } else {
                            "Highest tier reached"
                        },
                    fontSize = 12.sp,
                    color = Color(0xFF9AA0A6)
                )
            }

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            LinearProgressIndicator(
                progress = {
                    stats.progressToNextTier
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(
                            RoundedCornerShape(4.dp)
                        ),
                color =
                    Color(0xFFFF6D00),
                trackColor =
                    Color(0xFF343A40)
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Text(
                text =
                    when {
                        stats.updatesUntilNextTier > 0 ->
                            "${stats.updatesUntilNextTier} more updates to reach the next tier"

                        else ->
                            "You've reached the highest tier"
                    },
                fontSize = 11.sp,
                color = Color(0xFF9AA0A6)
            )
        }
    }
}

@Composable
private fun TierLegendItemRow() {
    Row(
        modifier =
            Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.SpaceBetween
    ) {

        TierLegendItem(
            dotColor = Color(0xFFCD7F32),
            title = "BRONZE",
            subtitle = "0-9 updates"
        )

        TierLegendItem(
            dotColor = Color(0xFFC0C0C0),
            title = "SILVER",
            subtitle = "10-24 updates"
        )

        TierLegendItem(
            dotColor = Color(0xFFFFD700),
            title = "GOLD",
            subtitle = "25+ updates"
        )
    }
}

@Composable
private fun BadgesGrid(
    stats: AchievementStats
) {
    Column(
        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        Row(
            modifier =
                Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            Box(
                modifier =
                    Modifier.weight(1f)
            ) {
                BadgeItem(
                    title = "First Update",
                    subtitle =
                        if (stats.firstUpdateEarned) {
                            "Earned"
                        } else {
                            "1 update"
                        },
                    icon = Icons.Default.Flag,
                    earned =
                        stats.firstUpdateEarned
                )
            }

            Box(
                modifier =
                    Modifier.weight(1f)
            ) {
                BadgeItem(
                    title = "3-Day Streak",
                    subtitle =
                        if (stats.threeDayStreakEarned) {
                            "Earned"
                        } else {
                            "${stats.longestStreak}/3 days"
                        },
                    icon = Icons.Default.Star,
                    earned =
                        stats.threeDayStreakEarned
                )
            }

            Box(
                modifier =
                    Modifier.weight(1f)
            ) {
                BadgeItem(
                    title = "Photo Pro",
                    subtitle =
                        if (stats.photoProEarned) {
                            "Earned"
                        } else {
                            "${stats.totalPhotos}/10 photos"
                        },
                    icon = Icons.Default.PhotoCamera,
                    earned =
                        stats.photoProEarned
                )
            }
        }

        Row(
            modifier =
                Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            Box(
                modifier =
                    Modifier.weight(1f)
            ) {
                BadgeItem(
                    title = "10 Updates",
                    subtitle =
                        if (stats.tenUpdatesEarned) {
                            "Earned"
                        } else {
                            "${stats.totalUpdates}/10"
                        },
                    icon = Icons.Default.CheckCircle,
                    earned =
                        stats.tenUpdatesEarned
                )
            }

            Box(
                modifier =
                    Modifier.weight(1f)
            ) {
                BadgeItem(
                    title = "Perfect Week",
                    subtitle =
                        if (stats.perfectWeekEarned) {
                            "Earned"
                        } else {
                            "${stats.longestStreak}/5 days"
                        },
                    icon = Icons.Default.EmojiEvents,
                    earned =
                        stats.perfectWeekEarned
                )
            }

            Box(
                modifier =
                    Modifier.weight(1f)
            ) {
                BadgeItem(
                    title = "25 Updates",
                    subtitle =
                        if (stats.twentyFiveUpdatesEarned) {
                            "Earned"
                        } else {
                            "${stats.totalUpdates}/25"
                        },
                    icon = Icons.Default.EmojiEvents,
                    earned =
                        stats.twentyFiveUpdatesEarned
                )
            }
        }
    }
}

@Composable
private fun TierLegendItem(
    dotColor: Color,
    title: String,
    subtitle: String
) {
    Column(
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Row(
            verticalAlignment =
                Alignment.CenterVertically,
            horizontalArrangement =
                Arrangement.spacedBy(4.dp)
        ) {

            Box(
                modifier =
                    Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(dotColor)
            )

            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1D20)
            )
        }

        Spacer(
            modifier =
                Modifier.height(2.dp)
        )

        Text(
            text = subtitle,
            fontSize = 11.sp,
            color = Color(0xFF6C757D)
        )
    }
}

@Composable
private fun BadgeItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    earned: Boolean
) {
    Card(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(16.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    if (earned) {
                        Color(0xFFFFFCF0)
                    } else {
                        Color(0xFFF1F3F5)
                    }
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 8.dp
                    ),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Box(
                modifier =
                    Modifier
                        .size(48.dp)
                        .clip(
                            RoundedCornerShape(12.dp)
                        )
                        .background(
                            if (earned) {
                                Color(0xFFFFC107)
                            } else {
                                Color(0xFFE9ECEF)
                            }
                        ),
                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint =
                        if (earned) {
                            Color(0xFF1A1D20)
                        } else {
                            Color(0xFFADB5BD)
                        },
                    modifier =
                        Modifier.size(24.dp)
                )
            }

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color =
                    if (earned) {
                        Color(0xFF1A1D20)
                    } else {
                        Color(0xFF6C757D)
                    },
                maxLines = 1
            )

            Spacer(
                modifier =
                    Modifier.height(2.dp)
            )

            Text(
                text = subtitle,
                fontSize = 10.sp,
                color =
                    if (earned) {
                        Color(0xFF8D6E63)
                    } else {
                        Color(0xFF9AA0A6)
                    },
                maxLines = 1
            )
        }
    }
}