package com.example.onsite_mockups.ui.screens.foreman

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
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.runtime.LaunchedEffect
import com.example.onsite_mockups.ui.viewmodels.ForemanViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun ForemanHomeScreen(
    foremanViewModel: ForemanViewModel,
    onSiteClick: (String) -> Unit,
    onAchievementsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val sites by foremanViewModel.sites.collectAsState()
    val updates by foremanViewModel.updates.collectAsState()

    LaunchedEffect(Unit) {
        foremanViewModel.loadForemanData()
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
                    onClick = { selectedTab = 0 },
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
                    onClick = {
                        selectedTab = 1
                        onAchievementsClick()
                    },
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
                    onClick = { selectedTab = 2 },
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
                        onProfileClick()
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                // Top Header with Greeting & Notification Bell
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "GOOD MORNING",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9AA0A6),
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Thabo Mokoena",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1D20)
                        )
                    }

                    // Notification Bell with Badge
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .clickable(onClick = onNotificationClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color(0xFF1A1D20),
                            modifier = Modifier.size(22.dp)
                        )
                        // Badge "2"
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 8.dp, end = 8.dp)
                                .size(16.dp)
                                .background(Color(0xFFFF6D00), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "2",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            item {
                // Two Summary Tiles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Tile 1: Assigned sites
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(84.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFF8E1)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = sites.size.toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A1D20)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Assigned sites",
                                fontSize = 12.sp,
                                color = Color(0xFF6C757D),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Tile 2: Submitted today
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(84.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE8F5E9)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = updates.size.toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Submitted today",
                                fontSize = 12.sp,
                                color = Color(0xFF6C757D),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
                // My Sites Section Header & Filter
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "MY SITES",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9AA0A6),
                        letterSpacing = 1.sp
                    )
                    TextButton(onClick = onFilterClick) {
                        Text(
                            text = "Filter",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF6D00)
                        )
                    }
                }
            }

            // Dynamic Sites List
            items(sites.size) { index ->
                val site = sites[index]
                val isSubmitted = updates.any { it.siteId == site.id }
                SiteCard(
                    title = site.name,
                    address = site.address,
                    statusText = if (isSubmitted) "Submitted" else "Needs update",
                    isSubmitted = isSubmitted,
                    footerText = if (isSubmitted) "Submitted today" else "Last update: Yesterday",
                    onClick = { onSiteClick(site.id ?: "") }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun SiteCard(
    title: String,
    address: String,
    statusText: String,
    isSubmitted: Boolean,
    footerText: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Location Icon Box
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFFFF8E1)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color(0xFFFF6D00),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1D20)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = address,
                            fontSize = 12.sp,
                            color = Color(0xFF6C757D)
                        )
                    }
                }

                // Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSubmitted) Color(0xFFE8F5E9) else Color(0xFFFFF3E0))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isSubmitted) "✓$statusText" else "•$statusText",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSubmitted) Color(0xFF2E7D32) else Color(0xFFE65100)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Divider / Footer row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = footerText,
                    fontSize = 12.sp,
                    color = Color(0xFF9AA0A6)
                )
                Text(
                    text = "View ›",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6D00)
                )
            }
        }
    }
}
