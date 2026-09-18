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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun AchievementsScreen(
    onNavigateHome: () -> Unit,
    onNavigateAlerts: () -> Unit = {},
    onNavigateProfile: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(1) } // Achievements tab selected

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
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFF6D00),
                        selectedTextColor = Color(0xFFFF6D00),
                        unselectedIconColor = Color(0xFF9AA0A6),
                        unselectedTextColor = Color(0xFF9AA0A6),
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.EmojiEvents, contentDescription = "Achievements") },
                    label = { Text("Achievements", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFF6D00),
                        selectedTextColor = Color(0xFFFF6D00),
                        unselectedIconColor = Color(0xFF9AA0A6),
                        unselectedTextColor = Color(0xFF9AA0A6),
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        onNavigateAlerts()
                    },
                    icon = { Icon(Icons.Default.Notifications, contentDescription = "Alerts") },
                    label = { Text("Alerts", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFF6D00),
                        selectedTextColor = Color(0xFFFF6D00),
                        unselectedIconColor = Color(0xFF9AA0A6),
                        unselectedTextColor = Color(0xFF9AA0A6),
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = {
                        selectedTab = 3
                        onNavigateProfile()
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFF6D00),
                        selectedTextColor = Color(0xFFFF6D00),
                        unselectedIconColor = Color(0xFF9AA0A6),
                        unselectedTextColor = Color(0xFF9AA0A6),
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                // Header
                Text(
                    text = "Achievements",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1D20)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Keep logging updates to level up",
                    fontSize = 14.sp,
                    color = Color(0xFF6C757D)
                )
            }

            item {
                // Tier Card (Dark Card)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1D20)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "CURRENT TIER",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFC107),
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Silver Foreman",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }

                            // Trophy icon circle
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF343A40)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.EmojiEvents,
                                    contentDescription = "Trophy",
                                    tint = Color(0xFFE9ECEF),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "18 updates logged",
                                fontSize = 12.sp,
                                color = Color(0xFF9AA0A6)
                            )
                            Text(
                                text = "25 for Gold",
                                fontSize = 12.sp,
                                color = Color(0xFF9AA0A6)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Progress Bar
                        LinearProgressIndicator(
                            progress = { 18f / 25f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = Color(0xFFFF6D00),
                            trackColor = Color(0xFF343A40),
                        )
                    }
                }
            }

            item {
                // Tier Legend
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TierLegendItem(dotColor = Color(0xFFCD7F32), title = "BRONZE", subtitle = "0-9 updates")
                    TierLegendItem(dotColor = Color(0xFFC0C0C0), title = "SILVER", subtitle = "10-24 updates")
                    TierLegendItem(dotColor = Color(0xFFFFD700), title = "GOLD", subtitle = "25+ updates")
                }
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
                // Badges Section Header
                Text(
                    text = "BADGES EARNED — 4 OF 6",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9AA0A6),
                    letterSpacing = 1.sp
                )
            }

            item {
                // Badges Grid (3 columns x 2 rows)
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            BadgeItem(
                                title = "First Update",
                                subtitle = "Day 1",
                                icon = Icons.Default.Flag,
                                earned = true
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            BadgeItem(
                                title = "3-Day Streak",
                                subtitle = "Earned",
                                icon = Icons.Default.Star,
                                earned = true
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            BadgeItem(
                                title = "Photo Pro",
                                subtitle = "10 photos",
                                icon = Icons.Default.PhotoCamera,
                                earned = true
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            BadgeItem(
                                title = "10 Updates",
                                subtitle = "Earned",
                                icon = Icons.Default.CheckCircle,
                                earned = true
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            BadgeItem(
                                title = "Perfect Week",
                                subtitle = "5/5 days",
                                icon = Icons.Default.EmojiEvents,
                                earned = false
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            BadgeItem(
                                title = "25 Updates",
                                subtitle = "18/25",
                                icon = Icons.Default.EmojiEvents,
                                earned = false
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun TierLegendItem(dotColor: Color, title: String, subtitle: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
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
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = subtitle,
            fontSize = 11.sp,
            color = Color(0xFF6C757D)
        )
    }
}

@Composable
fun BadgeItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    earned: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (earned) Color(0xFFFFFCF0) else Color(0xFFF1F3F5)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon box
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (earned) Color(0xFFFFC107) else Color(0xFFE9ECEF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = if (earned) Color(0xFF1A1D20) else Color(0xFFADB5BD),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (earned) Color(0xFF1A1D20) else Color(0xFF6C757D),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = if (earned) Color(0xFF8D6E63) else Color(0xFF9AA0A6),
                maxLines = 1
            )
        }
    }
}
