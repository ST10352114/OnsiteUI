package com.example.onsite_mockups.ui.screens.admin

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
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.onsite_mockups.ui.viewmodels.AdminViewModel
import androidx.compose.runtime.collectAsState

/**
 * The main entry screen for Administrators.
 * Provides a high-level overview of construction site activity and navigation to management tools.
 */
@Composable
fun AdminDashboardScreen(
    adminViewModel: AdminViewModel,
    onNotificationClick: () -> Unit = {},
    onExportClick: () -> Unit = {},
    onUpdateClick: (String) -> Unit = {},
    onSitesCrewClick: () -> Unit = {},
    onAlertsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    // UI state for bottom navigation
    var selectedTab by remember { mutableIntStateOf(0) }
    
    // Data flows from the ViewModel
    val sites by adminViewModel.sites.collectAsState()
    val updates by adminViewModel.updates.collectAsState()

    // Refresh data on screen entry
    LaunchedEffect(Unit) {
        adminViewModel.loadAdminData()
    }

    Scaffold(
        containerColor = Color(0xFFF9F9FB),
        bottomBar = {
            // Standard bottom navigation bar
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                // Dashboard Tab
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.BarChart, contentDescription = "Dashboard") },
                    label = { Text("Dashboard", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFF6D00),
                        selectedTextColor = Color(0xFFFF6D00),
                        unselectedIconColor = Color(0xFF9AA0A6),
                        unselectedTextColor = Color(0xFF9AA0A6),
                        indicatorColor = Color.Transparent
                    )
                )
                // Sites & Crew Tab
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        onSitesCrewClick()
                    },
                    icon = { Icon(Icons.Default.GridView, contentDescription = "Sites & Crew") },
                    label = { Text("Sites & Crew", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFF6D00),
                        selectedTextColor = Color(0xFFFF6D00),
                        unselectedIconColor = Color(0xFF9AA0A6),
                        unselectedTextColor = Color(0xFF9AA0A6),
                        indicatorColor = Color.Transparent
                    )
                )
                // Alerts/Notifications Tab
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        onAlertsClick()
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
                // Profile/Settings Tab
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
                
                // Toolbar with branding and notification bell
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "OVERVIEW",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9AA0A6),
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Site Dashboard",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1D20)
                        )
                    }

                    // Notification bell with unread badge
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
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 8.dp, end = 8.dp)
                                .size(16.dp)
                                .background(Color(0xFFFF6D00), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "5",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            item {
                // Statistical Summary Tiles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Total managed sites
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = sites.size.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A1D20)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Total sites",
                                fontSize = 11.sp,
                                color = Color(0xFF6C757D),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Reports submitted today
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = updates.size.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Updated today",
                                fontSize = 11.sp,
                                color = Color(0xFF6C757D),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Sites pending submission
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = (sites.size - updates.size).coerceAtLeast(0).toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE65100)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Pending",
                                fontSize = 11.sp,
                                color = Color(0xFF6C757D),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            item {
                // Global Search and Export buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color(0xFF9AA0A6),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Site, foreman, date",
                                fontSize = 13.sp,
                                color = Color(0xFF9AA0A6)
                            )
                        }
                    }

                    // Global export button
                    Surface(
                        modifier = Modifier
                            .size(44.dp)
                            .clickable(onClick = onExportClick),
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = "Export",
                                tint = Color(0xFF495057),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "TODAY'S UPDATES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9AA0A6),
                    letterSpacing = 1.sp
                )
            }

            // List of updates for the day across all managed sites
            items(sites.size) { index ->
                val site = sites[index]
                val update = updates.find { it.siteId == site.id }
                val isDone = update != null
                val updateForeman = adminViewModel.getForemanForUpdate(update)
                AdminUpdateCard(
                    title = site.name,
                    subtitle = if (isDone && update != null) "${updateForeman?.fullName ?: "Foreman"} · ${update.updateDate}" else "Pending · —",
                    statusText = if (isDone) "Done" else "Pending",
                    isDone = isDone,
                    onClick = { onUpdateClick(site.id ?: "") }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

/**
 * Card representing an individual site's update status in the administrator list.
 */
@Composable
fun AdminUpdateCard(
    title: String,
    subtitle: String,
    statusText: String,
    isDone: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Location iconography
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF1F3F5)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color(0xFF495057),
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
                        text = subtitle,
                        fontSize = 12.sp,
                        color = Color(0xFF6C757D)
                    )
                }
            }

            // Completion status badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isDone) Color(0xFFE8F5E9) else Color(0xFFFFF3E0))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (isDone) "✓$statusText" else "•$statusText",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDone) Color(0xFF2E7D32) else Color(0xFFE65100)
                )
            }
        }
    }
}
