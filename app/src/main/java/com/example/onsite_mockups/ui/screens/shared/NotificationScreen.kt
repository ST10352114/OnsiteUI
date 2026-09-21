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


package com.example.onsite_mockups.ui.screens.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onsite_mockups.data.models.NotificationModel
import com.example.onsite_mockups.data.repository.OnSiteRepository
import kotlinx.coroutines.launch

/**
 * Displays a list of user notifications and allows marking them as read.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    onBackClick: () -> Unit,
    onNotificationClick: (NotificationModel) -> Unit
) {
    // UI state for notifications list
    var notifications by remember {
        mutableStateOf<List<NotificationModel>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var unreadCount by remember {
        mutableIntStateOf(0)
    }

    val scope = rememberCoroutineScope()

    // Load initial data
    LaunchedEffect(Unit) {
        try {
            notifications = OnSiteRepository.getNotifications()
            unreadCount = OnSiteRepository.getUnreadNotificationCount()
        } catch (_: Exception) {
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9FB))
    ) {
        // Toolbar with unread count indicator
        Surface(
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF1A1D20)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Notifications",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1D20)
                    )
                    
                    if (unreadCount > 0) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Badge {
                            Text(unreadCount.toString())
                        }
                    }
                }

                // Action to clear all unread notifications
                if (unreadCount > 0) {
                    TextButton(
                        onClick = {
                            scope.launch {
                                try {
                                    OnSiteRepository.markAllNotificationsRead()
                                    // Local UI update
                                    notifications = notifications.map { it.copy(isRead = true) }
                                    unreadCount = 0
                                } catch (_: Exception) {}
                            }
                        }
                    ) {
                        Text(
                            "Mark all read",
                            fontSize = 13.sp,
                            color = Color(0xFFFF6D00)
                        )
                    }
                }
            }
        }

        // List of notification items
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFFF6D00))
            }
        } else if (notifications.isEmpty()) {
            EmptyNotificationsView()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(notifications) { notification ->
                    NotificationItem(
                        notification = notification,
                        onClick = {
                            if (!notification.isRead) {
                                scope.launch {
                                    try {
                                        OnSiteRepository.markNotificationRead(notification.id)
                                        // Update local state to reflect read status
                                        notifications = notifications.map { 
                                            if (it.id == notification.id) it.copy(isRead = true) else it 
                                        }
                                        unreadCount = (unreadCount - 1).coerceAtLeast(0)
                                    } catch (_: Exception) {}
                                }
                            }
                            onNotificationClick(notification)
                        }
                    )
                }
            }
        }
    }
}

/**
 * Renders a single notification card.
 */
@Composable
private fun NotificationItem(
    notification: NotificationModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) Color.White else Color(0xFFFFF8E1)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (notification.isRead) 1.dp else 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon representing the notification type
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(getNotificationColor(notification.type).copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getNotificationIcon(notification.type),
                    contentDescription = null,
                    tint = getNotificationColor(notification.type),
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notification.title,
                        fontSize = 15.sp,
                        fontWeight = if (notification.isRead) FontWeight.Bold else FontWeight.ExtraBold,
                        color = Color(0xFF1A1D20)
                    )
                    
                    if (!notification.isRead) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFF6D00))
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(2.dp))
                
                Text(
                    text = notification.message,
                    fontSize = 13.sp,
                    color = Color(0xFF495057),
                    lineHeight = 18.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Formatted timestamp
                Text(
                    text = formatNotificationTime(notification.createdAt),
                    fontSize = 11.sp,
                    color = Color(0xFF9AA0A6)
                )
            }
        }
    }
}

/**
 * View displayed when there are no notifications.
 */
@Composable
private fun EmptyNotificationsView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFFF1F3F5)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.NotificationsNone,
                contentDescription = null,
                tint = Color(0xFFADB5BD),
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No notifications yet",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF495057)
        )
        Text(
            text = "When you get updates, they'll appear here",
            fontSize = 13.sp,
            color = Color(0xFF6C757D)
        )
    }
}

/**
 * Returns a suitable icon for the notification type.
 */
private fun getNotificationIcon(type: String): ImageVector {
    return when (type) {
        "daily_report_submitted" -> Icons.Default.Description
        "site_assigned" -> Icons.Default.LocationOn
        "achievement_unlocked" -> Icons.Default.EmojiEvents
        else -> Icons.Default.Notifications
    }
}

/**
 * Returns a theme color for the notification type.
 */
private fun getNotificationColor(type: String): Color {
    return when (type) {
        "daily_report_submitted" -> Color(0xFF2E7D32)
        "site_assigned" -> Color(0xFFFF6D00)
        "achievement_unlocked" -> Color(0xFFFFC107)
        else -> Color(0xFF495057)
    }
}

/**
 * Simplifies a date string for display.
 */
private fun formatNotificationTime(dateString: String): String {
    return try {
        // Expected format: 2024-01-20T10:00:00Z
        val date = dateString.take(10)
        val time = dateString.substring(11, 16)
        "$date at $time"
    } catch (_: Exception) {
        dateString
    }
}
