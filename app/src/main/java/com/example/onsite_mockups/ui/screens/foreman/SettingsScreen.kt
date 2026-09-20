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
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onsite_mockups.data.repository.OnSiteRepository
import com.example.onsite_mockups.ui.viewmodels.AuthViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.compose.ui.platform.LocalContext
import com.example.onsite_mockups.security.OnSiteBiometricManager
@Composable
fun SettingsScreen(
    authViewModel: AuthViewModel,
    onNavigateHome: () -> Unit,
    onNavigateAchievements: () -> Unit,
    onNavigateAlerts: () -> Unit = {},
    onLogout: () -> Unit
) {
    var selectedTab by remember {
        mutableIntStateOf(3)
    }

    val context =
        LocalContext.current

    val activity =
        context as? FragmentActivity

    var biometricLoginEnabled by remember {
        mutableStateOf(
            OnSiteBiometricManager.isEnabled(
                context
            )
        )
    }

    val currentProfile by
    authViewModel.currentProfile.collectAsState()

    var pushNotificationsEnabled by
    remember {
        mutableStateOf(true)
    }



    val scope =
        rememberCoroutineScope()

    LaunchedEffect(Unit) {
        try {
            pushNotificationsEnabled =
                OnSiteRepository
                    .getNotificationPreferences()
                    .pushEnabled
        } catch (_: Exception) {
        }
    }

    Scaffold(
        containerColor =
            Color(0xFFF9F9FB),
        bottomBar = {
            NavigationBar(
                containerColor =
                    Color.White,
                tonalElevation =
                    8.dp
            ) {
                NavigationBarItem(
                    selected =
                        selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                        onNavigateHome()
                    },
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription =
                                "Home"
                        )
                    },
                    label = {
                        Text(
                            "Home",
                            fontSize = 11.sp
                        )
                    },
                    colors =
                        navigationColors()
                )

                NavigationBarItem(
                    selected =
                        selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        onNavigateAchievements()
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
                        navigationColors()
                )

                NavigationBarItem(
                    selected =
                        selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        onNavigateAlerts()
                    },
                    icon = {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription =
                                "Alerts"
                        )
                    },
                    label = {
                        Text(
                            "Alerts",
                            fontSize = 11.sp
                        )
                    },
                    colors =
                        navigationColors()
                )

                NavigationBarItem(
                    selected =
                        selectedTab == 3,
                    onClick = {
                        selectedTab = 3
                    },
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription =
                                "Profile"
                        )
                    },
                    label = {
                        Text(
                            "Profile",
                            fontSize = 11.sp
                        )
                    },
                    colors =
                        navigationColors()
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
                    text = "Settings",
                    fontSize = 24.sp,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Color(0xFF1A1D20)
                )
            }

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
                            defaultElevation =
                                1.dp
                        )
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .size(52.dp)
                                    .clip(
                                        CircleShape
                                    )
                                    .background(
                                        Color(0xFF1A1D20)
                                    ),
                            contentAlignment =
                                Alignment.Center
                        ) {
                            Text(
                                text =
                                    currentProfile
                                        ?.fullName
                                        ?.takeIf { it.isNotBlank() }
                                        ?.take(2)
                                        ?.uppercase()
                                        ?: currentProfile?.email?.take(2)?.uppercase()
                                        ?: "TM",
                                color =
                                    Color(0xFFFFC107),
                                fontSize = 18.sp,
                                fontWeight =
                                    FontWeight.Bold
                            )
                        }

                        Spacer(
                            modifier =
                                Modifier.width(16.dp)
                        )

                        Column {
                            Text(
                                text =
                                    currentProfile
                                        ?.fullName
                                        ?.takeIf { it.isNotBlank() }
                                        ?: (currentProfile?.email?.substringBefore("@")?.replaceFirstChar { it.uppercase() } ?: "Foreman"),
                                fontSize = 16.sp,
                                fontWeight =
                                    FontWeight.Bold,
                                color =
                                    Color(0xFF1A1D20)
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(2.dp)
                            )

                            Text(
                                text =
                                    if (
                                        currentProfile
                                            ?.role ==
                                        "foreman"
                                    ) {
                                        "Foreman · Assigned sites"
                                    } else {
                                        "Employee"
                                    },
                                fontSize = 13.sp,
                                color =
                                    Color(0xFF6C757D)
                            )
                        }
                    }
                }
            }

            item {
                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

                SettingsSectionTitle(
                    title = "PREFERENCES"
                )

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

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
                            defaultElevation =
                                1.dp
                        )
                ) {
                    Column(
                        modifier =
                            Modifier.fillMaxWidth()
                    ) {
                        SettingsNavigationItem(
                            icon =
                                Icons.Default.Language,
                            title =
                                "Language",
                            subtitle =
                                "English",
                            onClick = {}
                        )

                        SettingsNavigationItem(
                            icon =
                                Icons.Default.DarkMode,
                            title =
                                "Theme",
                            subtitle =
                                "Light mode",
                            onClick = {}
                        )

                        SettingsSwitchItem(
                            icon =
                                Icons.Default.Fingerprint,
                            title =
                                "Biometric login",
                            subtitle =
                                "Use fingerprint or Face ID",
                            checked =
                                biometricLoginEnabled,
                            onCheckedChange = biometric@{ enabled ->

                                if (!enabled) {

                                    OnSiteBiometricManager.disable(
                                        context
                                    )

                                    biometricLoginEnabled =
                                        false

                                    return@biometric
                                }

                                val profile =
                                    currentProfile

                                val biometricActivity =
                                    activity

                                if (
                                    profile == null ||
                                    biometricActivity == null
                                ) {
                                    return@biometric
                                }

                                OnSiteBiometricManager.authenticate(
                                    activity =
                                        biometricActivity,
                                    title =
                                        "Enable biometric login",
                                    subtitle =
                                        "Verify your identity to enable biometric login",
                                    onSuccess = {

                                        OnSiteBiometricManager.enable(
                                            context,
                                            profile.id.toString()
                                        )

                                        biometricLoginEnabled =
                                            true
                                    }
                                )
                            }
                        )
                    }
                }
            }

            item {
                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

                SettingsSectionTitle(
                    title = "SECURITY"
                )

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

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
                            defaultElevation =
                                1.dp
                        )
                ) {
                    Column(
                        modifier =
                            Modifier.fillMaxWidth()
                    ) {
                        SettingsNavigationItem(
                            icon =
                                Icons.Default.Lock,
                            title =
                                "Change password",
                            subtitle =
                                "Last changed 3 months ago",
                            onClick = {}
                        )
                    }
                }
            }

            item {
                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

                SettingsSectionTitle(
                    title = "DATA"
                )

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

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
                            defaultElevation =
                                1.dp
                        )
                ) {
                    SettingsNavigationItem(
                        icon =
                            Icons.Default.Cloud,
                        title =
                            "Offline cache",
                        subtitle =
                            "2 updates stored on this device",
                        onClick = {}
                    )
                }
            }

            item {
                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

                OutlinedButton(
                    onClick =
                        onLogout,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                    shape =
                        RoundedCornerShape(12.dp),
                    border =
                        androidx.compose
                            .foundation
                            .BorderStroke(
                                1.dp,
                                Color(0xFFFFCDD2)
                            ),
                    colors =
                        androidx.compose
                            .material3
                            .ButtonDefaults
                            .outlinedButtonColors(
                                containerColor =
                                    Color.White
                            )
                ) {
                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically,
                        horizontalArrangement =
                            Arrangement.Center
                    ) {
                        Icon(
                            imageVector =
                                Icons.AutoMirrored.Filled.Logout,
                            contentDescription =
                                "Log Out",
                            tint =
                                Color(0xFFD32F2F),
                            modifier =
                                Modifier.size(20.dp)
                        )

                        Spacer(
                            modifier =
                                Modifier.width(8.dp)
                        )

                        Text(
                            text = "Log Out",
                            fontSize = 15.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color =
                                Color(0xFFD32F2F)
                        )
                    }
                }

                Spacer(
                    modifier =
                        Modifier.height(24.dp)
                )
            }
        }
    }
}

@Composable
private fun navigationColors() =
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

@Composable
fun SettingsSectionTitle(
    title: String
) {
    Text(
        text = title,
        fontSize = 12.sp,
        fontWeight =
            FontWeight.Bold,
        color =
            Color(0xFF9AA0A6),
        letterSpacing = 1.sp
    )
}

@Composable
fun SettingsNavigationItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = onClick
                )
                .padding(16.dp),
        horizontalArrangement =
            Arrangement.SpaceBetween,
        verticalAlignment =
            Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment =
                Alignment.CenterVertically,
            modifier =
                Modifier.weight(1f)
        ) {
            Box(
                modifier =
                    Modifier
                        .size(38.dp)
                        .clip(
                            RoundedCornerShape(
                                10.dp
                            )
                        )
                        .background(
                            Color(0xFFF1F3F5)
                        ),
                contentAlignment =
                    Alignment.Center
            ) {
                Icon(
                    imageVector =
                        icon,
                    contentDescription =
                        title,
                    tint =
                        Color(0xFF495057),
                    modifier =
                        Modifier.size(20.dp)
                )
            }

            Spacer(
                modifier =
                    Modifier.width(16.dp)
            )

            Column {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Color(0xFF1A1D20)
                )

                Spacer(
                    modifier =
                        Modifier.height(2.dp)
                )

                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color =
                        Color(0xFF6C757D)
                )
            }
        }

        Icon(
            imageVector =
                Icons.Default.ChevronRight,
            contentDescription =
                "More",
            tint =
                Color(0xFFADB5BD),
            modifier =
                Modifier.size(20.dp)
        )
    }
}

@Composable
fun SettingsSwitchItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        horizontalArrangement =
            Arrangement.SpaceBetween,
        verticalAlignment =
            Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment =
                Alignment.CenterVertically,
            modifier =
                Modifier.weight(1f)
        ) {
            Box(
                modifier =
                    Modifier
                        .size(38.dp)
                        .clip(
                            RoundedCornerShape(
                                10.dp
                            )
                        )
                        .background(
                            Color(0xFFF1F3F5)
                        ),
                contentAlignment =
                    Alignment.Center
            ) {
                Icon(
                    imageVector =
                        icon,
                    contentDescription =
                        title,
                    tint =
                        Color(0xFF495057),
                    modifier =
                        Modifier.size(20.dp)
                )
            }

            Spacer(
                modifier =
                    Modifier.width(16.dp)
            )

            Column {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Color(0xFF1A1D20)
                )

                Spacer(
                    modifier =
                        Modifier.height(2.dp)
                )

                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color =
                        Color(0xFF6C757D)
                )
            }
        }

        Switch(
            checked =
                checked,
            onCheckedChange =
                onCheckedChange,
            colors =
                SwitchDefaults.colors(
                    checkedThumbColor =
                        Color.White,
                    checkedTrackColor =
                        Color(0xFFFF6D00),
                    uncheckedThumbColor =
                        Color.White,
                    uncheckedTrackColor =
                        Color(0xFFE0E0E0)
                )
        )
    }
}