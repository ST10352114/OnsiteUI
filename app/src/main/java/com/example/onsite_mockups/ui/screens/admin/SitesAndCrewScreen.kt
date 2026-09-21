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


package com.example.onsite_mockups.ui.screens.admin
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.models.Site
import com.example.onsite_mockups.data.models.SiteForemanAssignment
import com.example.onsite_mockups.ui.viewmodels.AdminViewModel

/**
 * Screen for managing construction sites, foremen, and their assignments.
 * Administrators can add new sites, register foremen, and link them to projects.
 */
@Composable
fun SitesAndCrewScreen(
    adminViewModel: AdminViewModel,
    onNavigateDashboard: () -> Unit,
    onNavigateAlerts: () -> Unit = {},
    onNavigateProfile: () -> Unit = {}
) {
    // Current tab selection for the bottom navigation
    var selectedTab by remember {
        mutableIntStateOf(0)
    }

    // Current tab for the application-wide bottom bar
    var navTab by remember {
        mutableIntStateOf(1)
    }

    // Modal control for adding new entities
    var showAddSiteDialog by remember {
        mutableStateOf(false)
    }

    var showAddForemanDialog by remember {
        mutableStateOf(false)
    }

    var showAssignForemanDialog by remember {
        mutableStateOf(false)
    }
    var showEditSiteDialog by remember {
        mutableStateOf(false)
    }

    var selectedSiteForEdit by remember {
        mutableStateOf<Site?>(null)
    }

    // Observe data from the ViewModel
    val sites by adminViewModel.sites.collectAsState()
    val foremen by adminViewModel.foremen.collectAsState()
    val assignments by adminViewModel.assignments.collectAsState()
    val isSaving by adminViewModel.isSaving.collectAsState()
    val errorMessage by adminViewModel.errorMessage.collectAsState()
    val successMessage by adminViewModel.successMessage.collectAsState()

    // Refresh data on screen entry
    LaunchedEffect(Unit) {
        adminViewModel.loadAdminData()
    }

    // Automatically close dialogs on success
    LaunchedEffect(successMessage) {
        if (successMessage != null) {
            showAddSiteDialog = false
            showAddForemanDialog = false
            showAssignForemanDialog = false
            showEditSiteDialog = false
            selectedSiteForEdit = null
        }
    }

    Scaffold(
        containerColor = Color(0xFFF9F9FB),
        bottomBar = {
            // Standard Bottom Navigation
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
                    icon = {
                        Icon(
                            Icons.Default.BarChart,
                            contentDescription = "Dashboard"
                        )
                    },
                    label = {
                        Text(
                            "Dashboard",
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
                            Icons.Default.GridView,
                            contentDescription = "Sites & Crew"
                        )
                    },
                    label = {
                        Text(
                            "Sites & Crew",
                            fontSize = 11.sp
                        )
                    },
                    colors = navigationColors()
                )

                NavigationBarItem(
                    selected = navTab == 2,
                    onClick = {
                        navTab = 2
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
                    colors = navigationColors()
                )

                NavigationBarItem(
                    selected = navTab == 3,
                    onClick = {
                        navTab = 3
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Sites & Crew",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1D20)
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = "Manage sites and foreman assignments",
                    fontSize = 14.sp,
                    color = Color(0xFF6C757D)
                )
            }

            // Segmented toggle between Sites and Foremen views
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFE9ECEF)
                ) {
                    Row(
                        modifier = Modifier.padding(4.dp),
                        horizontalArrangement =
                            Arrangement.spacedBy(4.dp)
                    ) {

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .clickable {
                                    selectedTab = 0
                                },
                            shape =
                                RoundedCornerShape(12.dp),
                            color =
                                if (selectedTab == 0) {
                                    Color.White
                                } else {
                                    Color.Transparent
                                }
                        ) {
                            Box(
                                contentAlignment =
                                    Alignment.Center
                            ) {
                                Text(
                                    text =
                                        "Sites (${sites.size})",
                                    fontSize = 14.sp,
                                    fontWeight =
                                        if (selectedTab == 0) {
                                            FontWeight.Bold
                                        } else {
                                            FontWeight.Medium
                                        },
                                    color =
                                        if (selectedTab == 0) {
                                            Color(0xFF1A1D20)
                                        } else {
                                            Color(0xFF6C757D)
                                        }
                                )
                            }
                        }

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .clickable {
                                    selectedTab = 1
                                },
                            shape =
                                RoundedCornerShape(12.dp),
                            color =
                                if (selectedTab == 1) {
                                    Color.White
                                } else {
                                    Color.Transparent
                                }
                        ) {
                            Box(
                                contentAlignment =
                                    Alignment.Center
                            ) {
                                Text(
                                    text =
                                        "Foremen (${foremen.size})",
                                    fontSize = 14.sp,
                                    fontWeight =
                                        if (selectedTab == 1) {
                                            FontWeight.Bold
                                        } else {
                                            FontWeight.Medium
                                        },
                                    color =
                                        if (selectedTab == 1) {
                                            Color(0xFF1A1D20)
                                        } else {
                                            Color(0xFF6C757D)
                                        }
                                )
                            }
                        }
                    }
                }
            }

            if (selectedTab == 0) {
                // List of construction sites
                items(
                    items = sites,
                    key = {
                        it.id ?: it.name
                    }
                ) { site ->

                    val siteAssignments =
                        assignments.filter {
                            it.siteId == site.id
                        }

                    SiteManagementCard(
                        site = site,
                        assignments = siteAssignments,
                        onClick = {
                            adminViewModel.clearMessages()
                            selectedSiteForEdit = site
                            showEditSiteDialog = true
                        }
                    )
                }

                // Call-to-action buttons for Sites
                item {
                    AddButton(
                        text = "Assign foreman to site",
                        onClick = {
                            adminViewModel.clearMessages()
                            adminViewModel.refreshAssignmentData()
                            showAssignForemanDialog = true
                        }
                    )
                }

                item {
                    AddButton(
                        text = "Add new site",
                        onClick = {
                            adminViewModel.clearMessages()
                            showAddSiteDialog = true
                        }
                    )
                }

            } else {
                // List of user profiles (Foremen)
                items(
                    items = foremen,
                    key = {
                        it.id ?: it.email ?: it.hashCode()
                    }
                ) { foreman ->

                    val assignmentCount =
                        assignments.count {
                            it.foremanId == foreman.id
                        }

                    ForemanManagementCard(
                        name = foreman.fullName ?: "Unnamed Foreman",
                        detail = foreman.email ?: "No email",
                        statusText =
                            if (foreman.isActive) {
                                "Active"
                            } else {
                                "Inactive"
                            },
                        assignmentCount =
                            assignmentCount
                    )
                }

                // Call-to-action button for Foremen
                item {
                    AddButton(
                        text = "Add new foreman",
                        onClick = {
                            adminViewModel.clearMessages()
                            showAddForemanDialog = true
                        }
                    )
                }
            }

            // Inline error notification
            if (errorMessage != null) {
                item {
                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color(0xFFFFEBEE)
                            ),
                        shape =
                            RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text =
                                errorMessage ?: "",
                            modifier =
                                Modifier.padding(14.dp),
                            color =
                                Color(0xFFC62828),
                            fontSize = 13.sp
                        )
                    }
                }
            }

            // Inline success notification
            if (successMessage != null) {
                item {
                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color(0xFFE8F5E9)
                            ),
                        shape =
                            RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text =
                                successMessage ?: "",
                            modifier =
                                Modifier.padding(14.dp),
                            color =
                                Color(0xFF2E7D32),
                            fontSize = 13.sp
                        )
                    }
                }
            }

            item {
                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }
        }
    }

    // Dialog management logic
    if (showAddSiteDialog) {
        AddSiteDialog(
            isSaving = isSaving,
            onDismiss = {
                if (!isSaving) {
                    showAddSiteDialog = false
                    adminViewModel.clearMessages()
                }
            },
            onSubmit = { name, address ->
                adminViewModel.addSite(
                    name,
                    address
                )
            }
        )
    }
    if (
        showEditSiteDialog &&
        selectedSiteForEdit != null
    ) {
        EditSiteDialog(
            site = selectedSiteForEdit!!,
            isSaving = isSaving,
            onDismiss = {
                if (!isSaving) {
                    showEditSiteDialog = false
                    selectedSiteForEdit = null
                    adminViewModel.clearMessages()
                }
            },
            onSubmit = { id, name, address, isActive ->
                adminViewModel.updateSite(
                    id = id,
                    name = name,
                    address = address,
                    isActive = isActive
                )
            }
        )
    }
    if (showAddForemanDialog) {
        AddForemanDialog(
            isSaving = isSaving,
            onDismiss = {
                if (!isSaving) {
                    showAddForemanDialog = false
                    adminViewModel.clearMessages()
                }
            },
            onSubmit = { fullName, email ->
                adminViewModel.addForeman(
                    fullName,
                    email
                )
            }
        )
    }

    if (showAssignForemanDialog) {
        AssignForemanDialog(
            sites = sites,
            foremen = foremen,
            assignments = assignments,
            isSaving = isSaving,
            onDismiss = {
                if (!isSaving) {
                    showAssignForemanDialog = false
                    adminViewModel.clearMessages()
                }
            },
            onSubmit = { siteId, foremanId ->
                adminViewModel.assignForeman(
                    siteId,
                    foremanId
                )
            }
        )
    }
}

/**
 * Modal to edit an existing construction site.
 */
@Composable
private fun EditSiteDialog(
    site: Site,
    isSaving: Boolean,
    onDismiss: () -> Unit,
    onSubmit: (
        String,
        String,
        String,
        Boolean
    ) -> Unit
) {
    var name by remember(site.id) {
        mutableStateOf(site.name)
    }

    var address by remember(site.id) {
        mutableStateOf(site.address)
    }

    var isActive by remember(site.id) {
        mutableStateOf(site.isActive)
    }

    AlertDialog(
        onDismissRequest = {
            if (!isSaving) {
                onDismiss()
            }
        },
        title = {
            Text(
                text = "Edit Site",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = {
                        Text("Site name")
                    },
                    singleLine = true,
                    enabled = !isSaving,
                    modifier =
                        Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = {
                        address = it
                    },
                    label = {
                        Text("Address")
                    },
                    minLines = 2,
                    maxLines = 3,
                    enabled = !isSaving,
                    modifier =
                        Modifier.fillMaxWidth()
                )

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    verticalAlignment =
                        Alignment.CenterVertically,
                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    Column {
                        Text(
                            text =
                                if (isActive) {
                                    "Site is active"
                                } else {
                                    "Site is closed"
                                },
                            fontWeight =
                                FontWeight.Medium
                        )

                        Text(
                            text =
                                if (isActive) {
                                    "The site is available for use."
                                } else {
                                    "The site is marked as closed."
                                },
                            fontSize = 12.sp,
                            color =
                                Color(0xFF6C757D)
                        )
                    }

                    Switch(
                        checked = isActive,
                        onCheckedChange = {
                            isActive = it
                        },
                        enabled = !isSaving
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSubmit(
                        site.id ?: "",
                        name.trim(),
                        address.trim(),
                        isActive
                    )
                },
                enabled =
                    name.isNotBlank() &&
                            address.isNotBlank() &&
                            site.id != null &&
                            !isSaving,
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            Color(0xFFFF6D00)
                    )
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        modifier =
                            Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Text("Save changes")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isSaving
            ) {
                Text("Cancel")
            }
        }
    )
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

/**
 * Large outline button for primary creation actions.
 */
@Composable
private fun AddButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(
                RoundedCornerShape(16.dp)
            )
            .border(
                1.dp,
                Color(0xFFCED4DA),
                RoundedCornerShape(16.dp)
            )
            .clickable {
                onClick()
            },
        contentAlignment =
            Alignment.Center
    ) {
        Row(
            verticalAlignment =
                Alignment.CenterVertically,
            horizontalArrangement =
                Arrangement.Center
        ) {
            Icon(
                imageVector =
                    Icons.Default.Add,
                contentDescription =
                    "Add",
                tint =
                    Color(0xFF1A1D20),
                modifier =
                    Modifier.size(18.dp)
            )

            Spacer(
                modifier =
                    Modifier.width(6.dp)
            )

            Text(
                text = text,
                fontSize = 15.sp,
                fontWeight =
                    FontWeight.Bold,
                color =
                    Color(0xFF1A1D20)
            )
        }
    }
}

/**
 * Modal to add a new construction site.
 */
@Composable
private fun AddSiteDialog(
    isSaving: Boolean,
    onDismiss: () -> Unit,
    onSubmit: (
        String,
        String
    ) -> Unit
) {
    var siteName by remember {
        mutableStateOf("")
    }

    var address by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add New Site",
                fontWeight =
                    FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = siteName,
                    onValueChange = {
                        siteName = it
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    label = {
                        Text("Site name")
                    },
                    singleLine = true,
                    enabled = !isSaving
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = {
                        address = it
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    label = {
                        Text("Address")
                    },
                    minLines = 2,
                    enabled = !isSaving
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSubmit(
                        siteName.trim(),
                        address.trim()
                    )
                },
                enabled =
                    siteName.isNotBlank() &&
                            address.isNotBlank() &&
                            !isSaving,
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            Color(0xFFFF6D00)
                    )
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        modifier =
                            Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color =
                            Color.White
                    )
                } else {
                    Text("Add Site")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isSaving
            ) {
                Text("Cancel")
            }
        }
    )
}

/**
 * Modal to register a new foreman profile.
 */
@Composable
private fun AddForemanDialog(
    isSaving: Boolean,
    onDismiss: () -> Unit,
    onSubmit: (
        String,
        String
    ) -> Unit
) {
    var fullName by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add New Foreman",
                fontWeight =
                    FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = fullName,
                    onValueChange = {
                        fullName = it
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    label = {
                        Text("Full name")
                    },
                    singleLine = true,
                    enabled = !isSaving
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    label = {
                        Text("Email")
                    },
                    singleLine = true,
                    enabled = !isSaving
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSubmit(
                        fullName.trim(),
                        email.trim()
                    )
                },
                enabled =
                    fullName.isNotBlank() &&
                            email.isNotBlank() &&
                            !isSaving,
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            Color(0xFFFF6D00)
                    )
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        modifier =
                            Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color =
                            Color.White
                    )
                } else {
                    Text("Add Foreman")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isSaving
            ) {
                Text("Cancel")
            }
        }
    )
}

/**
 * Modal to link a foreman to a specific site.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssignForemanDialog(
    sites: List<Site>,
    foremen: List<Profile>,
    assignments: List<SiteForemanAssignment>,
    isSaving: Boolean,
    onDismiss: () -> Unit,
    onSubmit: (
        String,
        String
    ) -> Unit
) {
    var selectedSite by remember {
        mutableStateOf<Site?>(null)
    }

    var selectedForeman by remember {
        mutableStateOf<Profile?>(null)
    }

    var siteMenuExpanded by remember {
        mutableStateOf(false)
    }

    var foremanMenuExpanded by remember {
        mutableStateOf(false)
    }

    val alreadyAssigned =
        selectedSite?.id != null &&
                selectedForeman?.id != null &&
                assignments.any {
                    it.siteId == selectedSite?.id &&
                            it.foremanId == selectedForeman?.id
                }

    AlertDialog(
        onDismissRequest = {
            if (!isSaving) {
                onDismiss()
            }
        },
        title = {
            Text(
                text = "Assign Foreman",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement =
                    Arrangement.spacedBy(14.dp)
            ) {

                Text(
                    text = "Site",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6C757D)
                )

                ExposedDropdownMenuBox(
                    expanded = siteMenuExpanded,
                    onExpandedChange = {
                        if (!isSaving &&
                            sites.isNotEmpty()
                        ) {
                            siteMenuExpanded =
                                !siteMenuExpanded

                            if (siteMenuExpanded) {
                                foremanMenuExpanded =
                                    false
                            }
                        }
                    }
                ) {
                    OutlinedTextField(
                        value =
                            selectedSite?.name
                                ?: if (sites.isEmpty()) {
                                    "No sites available"
                                } else {
                                    ""
                                },
                        onValueChange = {},
                        readOnly = true,
                        enabled =
                            !isSaving &&
                                    sites.isNotEmpty(),
                        label = {
                            Text("Select site")
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults
                                .TrailingIcon(
                                    expanded =
                                        siteMenuExpanded
                                )
                        },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = siteMenuExpanded,
                        onDismissRequest = {
                            siteMenuExpanded = false
                        }
                    ) {
                        sites.forEach { site ->

                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(
                                            text =
                                                site.name,
                                            fontWeight =
                                                FontWeight.Medium
                                        )

                                        Text(
                                            text =
                                                site.address,
                                            fontSize = 12.sp,
                                            color =
                                                Color(0xFF6C757D)
                                        )
                                    }
                                },
                                onClick = {
                                    selectedSite = site
                                    selectedForeman = null
                                    siteMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                Text(
                    text = "Foreman",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6C757D)
                )

                ExposedDropdownMenuBox(
                    expanded = foremanMenuExpanded,
                    onExpandedChange = {
                        if (!isSaving &&
                            foremen.isNotEmpty()
                        ) {
                            foremanMenuExpanded =
                                !foremanMenuExpanded

                            if (foremanMenuExpanded) {
                                siteMenuExpanded =
                                    false
                            }
                        }
                    }
                ) {
                    OutlinedTextField(
                        value =
                            selectedForeman?.fullName
                                ?: if (foremen.isEmpty()) {
                                    "No foremen available"
                                } else {
                                    ""
                                },
                        onValueChange = {},
                        readOnly = true,
                        enabled =
                            !isSaving &&
                                    foremen.isNotEmpty(),
                        label = {
                            Text("Select foreman")
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults
                                .TrailingIcon(
                                    expanded =
                                        foremanMenuExpanded
                                )
                        },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = foremanMenuExpanded,
                        onDismissRequest = {
                            foremanMenuExpanded = false
                        }
                    ) {
                        foremen.forEach { foreman ->

                            val assigned =
                                selectedSite?.id != null &&
                                        foreman.id != null &&
                                        assignments.any {
                                            it.siteId ==
                                                    selectedSite?.id &&
                                                    it.foremanId ==
                                                    foreman.id
                                        }

                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(
                                            text =
                                                foreman.fullName ?: "Unnamed Foreman",
                                            fontWeight =
                                                FontWeight.Medium
                                        )

                                        Text(
                                            text =
                                                if (assigned) {
                                                    "${foreman.email ?: "No email"} • Already assigned"
                                                } else {
                                                    foreman.email ?: "No email"
                                                },
                                            fontSize = 12.sp,
                                            color =
                                                if (assigned) {
                                                    Color(0xFF2E7D32)
                                                } else {
                                                    Color(0xFF6C757D)
                                                }
                                        )
                                    }
                                },
                                enabled = !assigned,
                                onClick = {
                                    selectedForeman =
                                        foreman

                                    foremanMenuExpanded =
                                        false
                                }
                            )
                        }
                    }
                }

                if (sites.isEmpty()) {
                    Text(
                        text =
                            "No sites were loaded. Close this popup and refresh the Sites & Crew screen.",
                        color =
                            Color(0xFFC62828),
                        fontSize = 12.sp
                    )
                }

                if (foremen.isEmpty()) {
                    Text(
                        text =
                            "No foremen were loaded. Close this popup and refresh the Sites & Crew screen.",
                        color =
                            Color(0xFFC62828),
                        fontSize = 12.sp
                    )
                }

                if (alreadyAssigned) {
                    Text(
                        text =
                            "This foreman is already assigned to this site.",
                        color =
                            Color(0xFFC62828),
                        fontSize = 12.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSubmit(
                        selectedSite?.id ?: "",
                        selectedForeman?.id ?: ""
                    )
                },
                enabled =
                    selectedSite?.id != null &&
                            selectedForeman?.id != null &&
                            !alreadyAssigned &&
                            !isSaving,
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            Color(0xFFFF6D00)
                    )
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        modifier =
                            Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Text("Assign")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isSaving
            ) {
                Text("Cancel")
            }
        }
    )
}

/**
 * Card representing a construction site in the management list.
 */
@Composable
private fun SiteManagementCard(
    site: Site,
    assignments: List<SiteForemanAssignment>,
    onClick: () -> Unit
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
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
                    .padding(16.dp)
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
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
                            Icons.Default.LocationOn,
                        contentDescription =
                            "Location",
                        tint =
                            Color(0xFF495057),
                        modifier =
                            Modifier.size(20.dp)
                    )
                }

                Spacer(
                    modifier =
                        Modifier.width(12.dp)
                )

                Column(
                    modifier =
                        Modifier.weight(1f)
                ) {
                    Text(
                        text = site.name,
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
                        text =
                            site.address,
                        fontSize = 12.sp,
                        color =
                            Color(0xFF6C757D)
                    )
                }

                Box(
                    modifier =
                        Modifier
                            .clip(
                                RoundedCornerShape(
                                    8.dp
                                )
                            )
                            .background(
                                if (site.isActive) {
                                    Color(0xFFE8F5E9)
                                } else {
                                    Color(0xFFF1F3F5)
                                }
                            )
                            .padding(
                                horizontal = 10.dp,
                                vertical = 4.dp
                            )
                ) {
                    Text(
                        text =
                            if (site.isActive) {
                                "Active"
                            } else {
                                "Closed"
                            },
                        fontSize = 12.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            if (site.isActive) {
                                Color(0xFF2E7D32)
                            } else {
                                Color(0xFF6C757D)
                            }
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(14.dp)
            )

            Text(
                text =
                    "Assigned Foremen",
                fontSize = 12.sp,
                fontWeight =
                    FontWeight.Bold,
                color =
                    Color(0xFF6C757D)
            )

            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )

            if (assignments.isEmpty()) {
                Text(
                    text =
                        "No foremen assigned",
                    fontSize = 13.sp,
                    color =
                        Color(0xFF9AA0A6)
                )
            } else {
                assignments.forEach { assignment ->
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = 4.dp
                                ),
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .size(30.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            8.dp
                                        )
                                    )
                                    .background(
                                        Color(0xFF1A1D20)
                                    ),
                            contentAlignment =
                                Alignment.Center
                        ) {
                            Text(
                                text =
                                    assignment.foremanName
                                        .split(" ")
                                        .take(2)
                                        .mapNotNull {
                                            it.firstOrNull()
                                        }
                                        .joinToString("")
                                        .uppercase(),
                                color =
                                    Color(0xFFFFC107),
                                fontSize = 11.sp,
                                fontWeight =
                                    FontWeight.Bold
                            )
                        }

                        Spacer(
                            modifier =
                                Modifier.width(10.dp)
                        )

                        Text(
                            text =
                                assignment.foremanName,
                            fontSize = 13.sp,
                            fontWeight =
                                FontWeight.Medium,
                            color =
                                Color(0xFF1A1D20)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Card representing a foreman profile in the management list.
 */
@Composable
private fun ForemanManagementCard(
    name: String,
    detail: String,
    statusText: String,
    assignmentCount: Int
) {
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
                                Color(0xFF1A1D20)
                            ),
                    contentAlignment =
                        Alignment.Center
                ) {
                    Text(
                        text =
                            name
                                .split(" ")
                                .take(2)
                                .mapNotNull {
                                    it.firstOrNull()
                                }
                                .joinToString("")
                                .uppercase(),
                        color =
                            Color(0xFFFFC107),
                        fontSize = 13.sp,
                        fontWeight =
                            FontWeight.Bold
                    )
                }

                Spacer(
                    modifier =
                        Modifier.width(12.dp)
                )

                Column {
                    Text(
                        text = name,
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
                        text = detail,
                        fontSize = 12.sp,
                        color =
                            Color(0xFF6C757D)
                    )

                    Spacer(
                        modifier =
                            Modifier.height(3.dp)
                    )

                    Text(
                        text =
                            "$assignmentCount site assignment${if (assignmentCount == 1) "" else "s"}",
                        fontSize = 11.sp,
                        color =
                            Color(0xFF6C757D)
                    )
                }
            }

            Box(
                modifier =
                    Modifier
                        .clip(
                            RoundedCornerShape(
                                8.dp
                            )
                        )
                        .background(
                            Color(0xFFE8F5E9)
                        )
                        .padding(
                            horizontal = 10.dp,
                            vertical = 4.dp
                        )
            ) {
                Text(
                    text = statusText,
                    fontSize = 12.sp,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Color(0xFF2E7D32)
                )
            }
        }
    }
}
