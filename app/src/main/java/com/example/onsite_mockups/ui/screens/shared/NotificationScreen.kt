package com.example.onsite_mockups.ui.screens.shared

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onsite_mockups.data.models.NotificationModel
import com.example.onsite_mockups.data.repository.OnSiteRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun NotificationScreen(
    onBackClick: () -> Unit,
    onNotificationClick: (NotificationModel) -> Unit = {}
) {
    var notifications by remember {
        mutableStateOf<List<NotificationModel>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    val scope = rememberCoroutineScope()

    fun loadNotifications() {
        scope.launch {
            isLoading = true
            errorMessage = null

            try {
                notifications =
                    OnSiteRepository.getNotifications()
            } catch (e: Exception) {
                errorMessage =
                    e.message ?: "Unable to load notifications."
            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        loadNotifications()
    }

    Scaffold(
        containerColor = Color(0xFFF9F9FB),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF1A1D20)
                    )
                }

                Text(
                    text = "Notifications",
                    modifier = Modifier.weight(1f),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1D20)
                )

                if (
                    notifications.any {
                        !it.isRead
                    }
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    OnSiteRepository
                                        .markAllNotificationsRead()

                                    notifications =
                                        notifications.map {
                                            it.copy(
                                                isRead = true
                                            )
                                        }
                                } catch (e: Exception) {
                                    errorMessage =
                                        e.message
                                            ?: "Unable to mark notifications as read."
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                Color.Transparent,
                            contentColor =
                                Color(0xFFFF6D00)
                        )
                    ) {
                        Text(
                            text = "Read all",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFFFF6D00)
                    )
                }
            }

            errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp),
                    horizontalAlignment =
                        Alignment.CenterHorizontally,
                    verticalArrangement =
                        Arrangement.Center
                ) {
                    Text(
                        text = errorMessage
                            ?: "Something went wrong.",
                        color = Color(0xFFD32F2F),
                        fontSize = 14.sp
                    )

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )

                    Button(
                        onClick = {
                            loadNotifications()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                Color(0xFFFF6D00)
                        )
                    ) {
                        Text("Retry")
                    }
                }
            }

            notifications.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp),
                    horizontalAlignment =
                        Alignment.CenterHorizontally,
                    verticalArrangement =
                        Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(
                                Color(0xFFFFF3E0)
                            ),
                        contentAlignment =
                            Alignment.Center
                    ) {
                        Icon(
                            imageVector =
                                Icons.Default.Notifications,
                            contentDescription =
                                "No notifications",
                            tint =
                                Color(0xFFFF6D00),
                            modifier =
                                Modifier.size(30.dp)
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )

                    Text(
                        text = "You're all caught up",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1D20)
                    )

                    Spacer(
                        modifier =
                            Modifier.height(6.dp)
                    )

                    Text(
                        text =
                            "New site updates and assignments will appear here.",
                        fontSize = 13.sp,
                        color = Color(0xFF6C757D)
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(
                            horizontal = 16.dp
                        ),
                    verticalArrangement =
                        Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )
                    }

                    items(
                        notifications,
                        key = {
                            it.id
                        }
                    ) { notification ->

                        NotificationCard(
                            notification =
                                notification,
                            onClick = {

                                if (
                                    !notification.isRead
                                ) {
                                    scope.launch {
                                        try {
                                            OnSiteRepository
                                                .markNotificationRead(
                                                    notification.id
                                                )

                                            notifications =
                                                notifications.map {
                                                    if (
                                                        it.id ==
                                                        notification.id
                                                    ) {
                                                        it.copy(
                                                            isRead =
                                                                true
                                                        )
                                                    } else {
                                                        it
                                                    }
                                                }
                                        } catch (
                                            e: Exception
                                        ) {
                                            errorMessage =
                                                e.message
                                                    ?: "Unable to update notification."
                                        }
                                    }
                                }

                                onNotificationClick(
                                    notification
                                )
                            }
                        )
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
    }
}

@Composable
private fun NotificationCard(
    notification: NotificationModel,
    onClick: () -> Unit
) {
    val background =
        if (notification.isRead) {
            Color.White
        } else {
            Color(0xFFFFF8F0)
        }

    CardLikeNotification(
        notification = notification,
        background = background,
        onClick = onClick
    )
}

@Composable
private fun CardLikeNotification(
    notification: NotificationModel,
    background: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(16.dp)
            )
            .background(background)
            .clickable(
                onClick = onClick
            )
            .padding(16.dp),
        verticalAlignment =
            Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(
                    if (notification.isRead) {
                        Color(0xFFF1F3F5)
                    } else {
                        Color(0xFFFFE0B2)
                    }
                ),
            contentAlignment =
                Alignment.Center
        ) {
            Icon(
                imageVector =
                    if (notification.isRead) {
                        Icons.Default.Check
                    } else {
                        Icons.Default.Notifications
                    },
                contentDescription =
                    "Notification",
                tint =
                    if (notification.isRead) {
                        Color(0xFF6C757D)
                    } else {
                        Color(0xFFFF6D00)
                    },
                modifier =
                    Modifier.size(21.dp)
            )
        }

        Spacer(
            modifier =
                Modifier.size(12.dp)
        )

        Column(
            modifier =
                Modifier.weight(1f)
        ) {
            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment =
                    Alignment.Top
            ) {
                Text(
                    text =
                        notification.title,
                    fontSize = 15.sp,
                    fontWeight =
                        if (
                            notification.isRead
                        ) {
                            FontWeight.SemiBold
                        } else {
                            FontWeight.Bold
                        },
                    color =
                        Color(0xFF1A1D20),
                    modifier =
                        Modifier.weight(1f)
                )

                if (!notification.isRead) {
                    Box(
                        modifier = Modifier
                            .padding(
                                start = 8.dp,
                                top = 4.dp
                            )
                            .size(8.dp)
                            .background(
                                Color(0xFFFF6D00),
                                CircleShape
                            )
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )

            Text(
                text =
                    notification.message,
                fontSize = 13.sp,
                color =
                    Color(0xFF6C757D)
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Text(
                text =
                    formatNotificationDate(
                        notification.createdAt
                    ),
                fontSize = 11.sp,
                color =
                    Color(0xFF9AA0A6)
            )
        }
    }
}

private fun formatNotificationDate(
    value: String
): String {
    return try {
        val input =
            java.text.SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss",
                java.util.Locale.US
            )

        val output =
            java.text.SimpleDateFormat(
                "dd MMM yyyy, HH:mm",
                java.util.Locale.US
            )

        val cleaned =
            value.substringBefore(".")

        output.format(
            input.parse(cleaned)
                ?: return value
        )
    } catch (
        _: Exception
    ) {
        value
    }
}