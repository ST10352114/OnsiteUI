package com.example.onsite_mockups.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.GridView
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.onsite_mockups.ui.viewmodels.AdminViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun SitesAndCrewScreen(
    adminViewModel: AdminViewModel,
    onNavigateDashboard: () -> Unit,
    onNavigateAlerts: () -> Unit = {},
    onNavigateProfile: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Sites, 1 = Foremen
    var navTab by remember { mutableIntStateOf(1) } // Sites & Crew selected in bottom bar
    val sites by adminViewModel.sites.collectAsState()
    val foremen by adminViewModel.foremen.collectAsState()

    Scaffold(
        containerColor = Color(0xFFF9F9FB),
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = navTab == 0,
                    onClick = {
                        navTab = 0
                        onNavigateDashboard()
                    },
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
                NavigationBarItem(
                    selected = navTab == 1,
                    onClick = { navTab = 1 },
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
                NavigationBarItem(
                    selected = navTab == 2,
                    onClick = {
                        navTab = 2
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
                    selected = navTab == 3,
                    onClick = {
                        navTab = 3
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                // Header
                Text(
                    text = "Sites & Crew",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1D20)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Manage sites and foreman accounts",
                    fontSize = 14.sp,
                    color = Color(0xFF6C757D)
                )
            }

            item {
                // Tab Switcher Pill
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFE9ECEF)
                ) {
                    Row(
                        modifier = Modifier.padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // Sites Tab
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .clickable { selectedTab = 0 },
                            shape = RoundedCornerShape(12.dp),
                            color = if (selectedTab == 0) Color.White else Color.Transparent
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "Sites (${sites.size})",
                                    fontSize = 14.sp,
                                    fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium,
                                    color = if (selectedTab == 0) Color(0xFF1A1D20) else Color(0xFF6C757D)
                                )
                            }
                        }

                        // Foremen Tab
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .clickable { selectedTab = 1 },
                            shape = RoundedCornerShape(12.dp),
                            color = if (selectedTab == 1) Color.White else Color.Transparent
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "Foremen (${foremen.size})",
                                    fontSize = 14.sp,
                                    fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium,
                                    color = if (selectedTab == 1) Color(0xFF1A1D20) else Color(0xFF6C757D)
                                )
                            }
                        }
                    }
                }
            }

            if (selectedTab == 0) {
                // Dynamic Sites List
                items(sites.size) { index ->
                    val site = sites[index]
                    SiteManagementCard(
                        title = site.name,
                        subtitle = "Address: ${site.address}",
                        statusText = if (site.isActive) "Active" else "Closed",
                        isActive = site.isActive
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFCED4DA), RoundedCornerShape(16.dp))
                            .clickable {
                                adminViewModel.addSite("New Extension Site", "Port Elizabeth")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color(0xFF1A1D20),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Add new site",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A1D20)
                            )
                        }
                    }
                }
            } else {
                // Dynamic Foremen List
                items(foremen.size) { index ->
                    val foreman = foremen[index]
                    ForemanManagementCard(
                        name = foreman.fullName,
                        detail = foreman.email,
                        statusText = if (foreman.isActive) "Active" else "Inactive"
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xFFCED4DA), RoundedCornerShape(16.dp))
                            .clickable {
                                adminViewModel.addForeman("New Foreman Account", "new_foreman@onsite.com")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color(0xFF1A1D20),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Add new foreman",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A1D20)
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
fun SiteManagementCard(
    title: String,
    subtitle: String,
    statusText: String,
    isActive: Boolean
) {
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
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

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isActive) Color(0xFFE8F5E9) else Color(0xFFF1F3F5))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = statusText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isActive) Color(0xFF2E7D32) else Color(0xFF6C757D)
                )
            }
        }
    }
}

@Composable
fun ForemanManagementCard(
    name: String,
    detail: String,
    statusText: String
) {
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF1A1D20)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = name.take(2).uppercase(),
                        color = Color(0xFFFFC107),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1D20)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = detail,
                        fontSize = 12.sp,
                        color = Color(0xFF6C757D)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE8F5E9))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = statusText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
            }
        }
    }
}
