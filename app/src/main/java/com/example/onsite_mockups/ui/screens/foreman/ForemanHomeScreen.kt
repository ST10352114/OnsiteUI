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
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EmojiEvents
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
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.ui.viewmodels.ForemanViewModel

/**
 * Main screen for foremen. Shows assigned construction sites and provides quick access
 * to achievements and profile settings.
 */
@Composable
fun ForemanHomeScreen(
    foremanViewModel: ForemanViewModel,
    profile: Profile?,
    onNotificationClick: () -> Unit = {},
    onSiteClick: (String) -> Unit = {},
    onAchievementsClick: () -> Unit = {},
    onAlertsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    // Current bottom navigation tab selection
    var navTab by remember {
        mutableIntStateOf(0)
    }

    // Observe data from ViewModel
    val foremanName by foremanViewModel.foremanName.collectAsState()
    val sites by foremanViewModel.sites.collectAsState()
    val updates by foremanViewModel.updates.collectAsState()

    // Refresh assigned sites on screen entry
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
                // Navigation items
                NavigationBarItem(
                    selected = navTab == 0,
                    onClick = {
                        navTab = 0
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
                        onAchievementsClick()
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
                        onProfileClick()
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

                // Greeting Header
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
                            text = "GOOD MORNING",
                            fontSize = 11.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color =
                                Color(0xFF9AA0A6),
                            letterSpacing = 1.sp
                        )

                        Spacer(
                            modifier =
                                Modifier.height(2.dp)
                        )

                        Text(
                            text = "Hi, $foremanName",
                            fontSize = 22.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color =
                                Color(0xFF1A1D20)
                        )
                    }

                    // Notification Entry with Badge
                    Box(
                        modifier =
                            Modifier
                                .size(44.dp)
                                .clip(
                                    RoundedCornerShape(
                                        12.dp
                                    )
                                )
                                .background(
                                    Color.White
                                )
                                .clickable(
                                    onClick =
                                        onNotificationClick
                                ),
                        contentAlignment =
                            Alignment.Center
                    ) {
                        Icon(
                            imageVector =
                                Icons.Default.Notifications,
                            contentDescription =
                                "Notifications",
                            tint =
                                Color(0xFF1A1D20),
                            modifier =
                                Modifier.size(22.dp)
                        )

                        Box(
                            modifier =
                                Modifier
                                    .align(
                                        Alignment.TopEnd
                                    )
                                    .padding(
                                        top = 8.dp,
                                        end = 8.dp
                                    )
                                    .size(16.dp)
                                    .background(
                                        Color(
                                            0xFFFF6D00
                                        ),
                                        CircleShape
                                    ),
                            contentAlignment =
                                Alignment.Center
                        ) {
                            Text(
                                text = "2",
                                color =
                                    Color.White,
                                fontSize = 10.sp,
                                fontWeight =
                                    FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Quick Search Mockup
            item {
                Surface(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                    shape =
                        RoundedCornerShape(14.dp),
                    color = Color.White
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(
                                    horizontal = 16.dp
                                ),
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector =
                                Icons.Default.Search,
                            contentDescription =
                                "Search",
                            tint =
                                Color(0xFF9AA0A6),
                            modifier =
                                Modifier.size(20.dp)
                        )

                        Spacer(
                            modifier =
                                Modifier.width(12.dp)
                        )

                        Text(
                            text = "Search assigned sites...",
                            fontSize = 14.sp,
                            color = Color(0xFF9AA0A6)
                        )
                    }
                }
            }

            item {
                Text(
                    text = "MY ASSIGNED SITES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9AA0A6),
                    letterSpacing = 1.sp
                )
            }

            // List of sites foreman is responsible for
            items(sites.size) { index ->
                val site = sites[index]
                val update = updates.find { it.siteId == site.id }
                val isDone = update != null
                SiteCard(
                    title = site.name,
                    subtitle = site.address,
                    isDone = isDone,
                    onClick = { onSiteClick(site.id ?: "") }
                )
            }

            item {
                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }
        }
    }
}

/**
 * Renders a construction site item in the foreman list.
 * Highlights if the daily update has already been completed.
 */
@Composable
private fun SiteCard(
    title: String,
    subtitle: String,
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
                // Location icon box
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

            // Progress status badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isDone) Color(0xFFE8F5E9) else Color(0xFFFFF3E0))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (isDone) "Done" else "Required",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDone) Color(0xFF2E7D32) else Color(0xFFE65100)
                )
            }
        }
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
