package com.example.onsite_mockups.ui.screens.foreman

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.onsite_mockups.data.models.PhotoInput
import com.example.onsite_mockups.data.models.StaffMember
import com.example.onsite_mockups.ui.viewmodels.ForemanViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

/**
 * Form screen for foremen to submit daily progress reports.
 * Captures staff, equipment usage, and photo evidence.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DailyUpdateFormScreen(
    foremanViewModel: ForemanViewModel,
    onBackClick: () -> Unit,
    onSubmitSuccess: () -> Unit
) {
    // Current site and state observation
    val selectedSite by foremanViewModel.selectedSite.collectAsState()
    val isSubmitting by foremanViewModel.isSubmitting.collectAsState()
    val isLoadingToday by foremanViewModel.isLoadingTodayUpdate.collectAsState()
    val todayUpdate by foremanViewModel.todayUpdate.collectAsState()
    val errorMessage by foremanViewModel.errorMessage.collectAsState()

    // Form inputs state
    val staffList = remember { mutableStateListOf<StaffMember>() }
    val powerTools = remember { mutableStateListOf<String>() }
    val plantMachines = remember { mutableStateListOf<String>() }
    val photos = remember { mutableStateListOf<Uri>() }
    var notes by remember { mutableStateOf("") }

    // Modal control state
    var showStaffModal by remember { mutableStateOf(false) }
    var showToolsModal by remember { mutableStateOf(false) }
    var showPlantModal by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()

    // Photo picker launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { photos.add(it) }
    }

    // Load existing report if already submitted today
    LaunchedEffect(selectedSite?.id) {
        val siteId = selectedSite?.id
        if (siteId != null) {
            val existing = foremanViewModel.loadTodayUpdate(siteId)
            if (existing != null) {
                // Populate form with existing data (read-only mode)
                notes = existing.notes ?: ""
                try {
                    val staffJson = existing.staffNames
                    if (staffJson != null) {
                        val decodedStaff = Json.decodeFromString<List<StaffMember>>(staffJson)
                        staffList.clear()
                        staffList.addAll(decodedStaff)
                    }
                } catch (_: Exception) {}
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFFF9F9FB)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Toolbar
            Surface(color = Color.White, shadowElevation = 2.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = selectedSite?.name ?: "Daily Report",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1D20)
                        )
                        Text(
                            text = "Daily Site Progress",
                            fontSize = 12.sp,
                            color = Color(0xFF6C757D)
                        )
                    }
                }
            }

            if (isLoadingToday) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFFF6D00))
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    item { Spacer(modifier = Modifier.height(4.dp)) }

                    // Staff Section
                    item {
                        SectionHeader(
                            title = "STAFF ON SITE",
                            onAddClick = { if (todayUpdate == null) showStaffModal = true },
                            count = staffList.size,
                            enabled = todayUpdate == null
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (staffList.isEmpty()) {
                            EmptyStatePlaceholder(text = "No staff added yet")
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                staffList.forEach { member ->
                                    StaffItem(
                                        name = member.name,
                                        role = member.job,
                                        onRemove = { if (todayUpdate == null) staffList.remove(member) },
                                        enabled = todayUpdate == null
                                    )
                                }
                            }
                        }
                    }

                    // Power Tools Section
                    item {
                        SectionHeader(
                            title = "POWER TOOLS USED",
                            onAddClick = { if (todayUpdate == null) showToolsModal = true },
                            count = powerTools.size,
                            enabled = todayUpdate == null
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (powerTools.isEmpty()) {
                            EmptyStatePlaceholder(text = "No tools listed")
                        } else {
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                powerTools.forEach { tool ->
                                    Chip(
                                        text = tool,
                                        onRemove = { if (todayUpdate == null) powerTools.remove(tool) },
                                        enabled = todayUpdate == null
                                    )
                                }
                            }
                        }
                    }

                    // Plant & Machinery Section
                    item {
                        SectionHeader(
                            title = "PLANT & MACHINERY",
                            onAddClick = { if (todayUpdate == null) showPlantModal = true },
                            count = plantMachines.size,
                            enabled = todayUpdate == null
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (plantMachines.isEmpty()) {
                            EmptyStatePlaceholder(text = "No machinery listed")
                        } else {
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                plantMachines.forEach { plant ->
                                    Chip(
                                        text = plant,
                                        onRemove = { if (todayUpdate == null) plantMachines.remove(plant) },
                                        enabled = todayUpdate == null
                                    )
                                }
                            }
                        }
                    }

                    // Photos Section
                    item {
                        SectionHeader(
                            title = "PHOTO EVIDENCE",
                            onAddClick = { if (todayUpdate == null) photoPickerLauncher.launch("image/*") },
                            count = photos.size,
                            enabled = todayUpdate == null
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (photos.isEmpty()) {
                            PhotoPlaceholder(onClick = { if (todayUpdate == null) photoPickerLauncher.launch("image/*") })
                        } else {
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                items(photos) { uri ->
                                    PhotoThumbnail(
                                        uri = uri,
                                        onRemove = { if (todayUpdate == null) photos.remove(uri) },
                                        enabled = todayUpdate == null
                                    )
                                }
                            }
                        }
                    }

                    // Notes Section
                    item {
                        Text(
                            text = "ADDITIONAL NOTES",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9AA0A6),
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = notes,
                            onValueChange = { if (todayUpdate == null) notes = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Enter any extra details...") },
                            minLines = 3,
                            enabled = todayUpdate == null,
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    item { Spacer(modifier = Modifier.height(20.dp)) }
                }

                // Bottom Submission Action
                Surface(color = Color.White, shadowElevation = 8.dp) {
                    Box(modifier = Modifier.padding(20.dp)) {
                        if (todayUpdate != null) {
                            // Read-only indicator if already submitted
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .background(Color(0xFFE8F5E9), RoundedCornerShape(12.dp)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF2E7D32))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Report Submitted", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Button(
                                onClick = {
                                    foremanViewModel.submitDailyUpdate(
                                        staffNames = Json.encodeToString(staffList.toList()),
                                        powerTools = Json.encodeToString(powerTools.toList()),
                                        plantMachines = Json.encodeToString(plantMachines.toList()),
                                        photos = photos.map { PhotoInput(fileName = UUID.randomUUID().toString(), base64Data = "mock_data") },
                                        notes = notes,
                                        onSubmitDone = onSubmitSuccess
                                    )
                                },
                                enabled = !isSubmitting && (staffList.isNotEmpty() || photos.isNotEmpty()),
                                modifier = Modifier.fillMaxWidth().height(52.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6D00))
                            ) {
                                if (isSubmitting) {
                                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp)
                                } else {
                                    Text("Submit Report", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Modal Sheets for Input
        if (showStaffModal) {
            AddStaffSheet(onDismiss = { showStaffModal = false }, onAdd = { staffList.add(it) })
        }

        if (showToolsModal) {
            AddSimpleItemSheet(
                title = "Add Power Tool",
                label = "Tool Name",
                onDismiss = { showToolsModal = false },
                onAdd = { powerTools.add(it) }
            )
        }

        if (showPlantModal) {
            AddSimpleItemSheet(
                title = "Add Plant/Machinery",
                label = "Machine Name",
                onDismiss = { showPlantModal = false },
                onAdd = { plantMachines.add(it) }
            )
        }
    }
}

/**
 * Bottom sheet for adding a new staff member to the report.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddStaffSheet(
    onDismiss: () -> Unit,
    onAdd: (StaffMember) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var job by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Add Staff Member",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1D20)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = job,
                onValueChange = { job = it },
                label = { Text("Job Role (e.g. Bricklayer)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (name.isNotBlank() && job.isNotBlank()) {
                        onAdd(StaffMember(name, job))
                        onDismiss()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6D00))
            ) {
                Text("Add to List")
            }
        }
    }
}

/**
 * Generic bottom sheet for adding a simple string item (tool or machine).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddSimpleItemSheet(
    title: String,
    label: String,
    onDismiss: () -> Unit,
    onAdd: (String) -> Unit
) {
    var value by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1D20)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                label = { Text(label) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (value.isNotBlank()) {
                        onAdd(value)
                        onDismiss()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6D00))
            ) {
                Text("Add Item")
            }
        }
    }
}

/**
 * Header for a form section with an optional 'Add' action.
 */
@Composable
private fun SectionHeader(
    title: String,
    onAddClick: () -> Unit,
    count: Int,
    enabled: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$title — $count",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF9AA0A6),
            letterSpacing = 1.sp
        )
        if (enabled) {
            IconButton(onClick = onAddClick, modifier = Modifier.size(24.dp)) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color(0xFFFF6D00))
            }
        }
    }
}

/**
 * Renders an individual staff member in the list.
 */
@Composable
private fun StaffItem(
    name: String,
    role: String,
    onRemove: () -> Unit,
    enabled: Boolean
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFF1F3F5)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFFADB5BD))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = role, color = Color(0xFF6C757D), fontSize = 12.sp)
            }
            if (enabled) {
                IconButton(onClick = onRemove) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Color(0xFFD32F2F), modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

/**
 * Clickable placeholder for photo uploads.
 */
@Composable
private fun PhotoPlaceholder(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFDEE2E6), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color(0xFFADB5BD))
            Spacer(modifier = Modifier.height(4.dp))
            Text("Tap to add photos", fontSize = 12.sp, color = Color(0xFFADB5BD))
        }
    }
}

/**
 * Small preview of an attached photo with a remove option.
 */
@Composable
private fun PhotoThumbnail(
    uri: Uri,
    onRemove: () -> Unit,
    enabled: Boolean
) {
    Box(modifier = Modifier.size(100.dp)) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        if (enabled) {
            IconButton(
                onClick = onRemove,
                modifier = Modifier.align(Alignment.TopEnd).padding(4.dp).size(24.dp).background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(Icons.Default.Close, contentDescription = "Remove", tint = Color.White, modifier = Modifier.size(16.dp))
            }
        }
    }
}

/**
 * Reusable chip for equipment/tool labels.
 */
@Composable
fun Chip(
    text: String,
    onRemove: () -> Unit = {},
    enabled: Boolean = true
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFFFF3E0),
        border = BorderStroke(1.dp, Color(0xFFFFE0B2))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFFE65100))
            if (enabled) {
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp).clickable(onClick = onRemove),
                    tint = Color(0xFFE65100)
                )
            }
        }
    }
}

@Composable
private fun EmptyStatePlaceholder(text: String) {
    Text(text = text, fontSize = 13.sp, color = Color(0xFFADB5BD), modifier = Modifier.padding(vertical = 4.dp))
}

@Composable
fun FormSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF9AA0A6),
        letterSpacing = 1.sp
    )
}
