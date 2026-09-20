package com.example.onsite_mockups.ui.screens.foreman

import android.Manifest
import android.graphics.Bitmap
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

import com.example.onsite_mockups.data.models.StaffMember
import com.example.onsite_mockups.data.network.PhotoInput
import com.example.onsite_mockups.ui.viewmodels.ForemanViewModel

@Composable
fun DailyUpdateFormScreen(
    foremanViewModel: ForemanViewModel,
    onBackClick: () -> Unit,
    onSubmitSuccess: () -> Unit
) {

    val selectedSite by
    foremanViewModel.selectedSite
        .collectAsState()

    val isSubmitting by
    foremanViewModel.isSubmitting
        .collectAsState()

    val errorMessage by
    foremanViewModel.errorMessage
        .collectAsState()

    val staffList =
        remember {
            mutableStateListOf<StaffMember>()
        }

    val powerTools =
        remember {
            mutableStateListOf(
                ToolItem(
                    "Angle grinder",
                    false
                ),
                ToolItem(
                    "Drill",
                    false
                ),
                ToolItem(
                    "Welder",
                    false
                ),
                ToolItem(
                    "Compressor",
                    false
                )
            )
        }

    val plantMachinery =
        remember {
            mutableStateListOf(
                ToolItem(
                    "Excavator",
                    false
                ),
                ToolItem(
                    "Tower crane",
                    false
                ),
                ToolItem(
                    "Concrete mixer",
                    false
                ),
                ToolItem(
                    "Forklift",
                    false
                )
            )
        }

    val capturedPhotos =
        remember {
            mutableStateListOf<String>()
        }

    var showAddStaffDialog by
    remember {
        mutableStateOf(false)
    }

    var showCameraPermissionMessage by
    remember {
        mutableStateOf(false)
    }

    var notes by
    remember {
        mutableStateOf("")
    }

    var latestBitmap by
    remember {
        mutableStateOf<Bitmap?>(null)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->

            if (bitmap != null) {

                latestBitmap = bitmap

                capturedPhotos.add(
                    bitmapToBase64(bitmap)
                )
            }
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {
                cameraLauncher.launch(null)
            } else {
                showCameraPermissionMessage = true
            }
        }

    LaunchedEffect(
        errorMessage
    ) {
        if (errorMessage != null) {
            latestBitmap = latestBitmap
        }
    }

    val bricklayers =
        staffList.count {
            it.job == "bricklayers"
        }

    val plasterers =
        staffList.count {
            it.job == "plasterers"
        }

    val pavers =
        staffList.count {
            it.job == "pavers"
        }

    val headcount =
        staffList.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFF9F9FB)
            )
    ) {

        Surface(
            color = Color(0xFFF9F9FB),
            shadowElevation = 0.dp
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onBackClick
                ) {

                    Icon(
                        imageVector =
                            Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription =
                            "Back",
                        tint =
                            Color(0xFF1A1D20)
                    )
                }

                Spacer(
                    modifier =
                        Modifier.width(8.dp)
                )

                Column {

                    Text(
                        text = "Daily Update",
                        fontSize = 18.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            Color(0xFF1A1D20)
                    )

                    Text(
                        text =
                            "${selectedSite?.name ?: "Site"} · Today",
                        fontSize = 12.sp,
                        color =
                            Color(0xFF6C757D)
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp
                ),
            verticalArrangement =
                Arrangement.spacedBy(20.dp)
        ) {

            item {

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

                FormSectionTitle(
                    title = "STAFF ON SITE"
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
                            modifier =
                                Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.SpaceBetween,
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Column {

                                Text(
                                    text =
                                        "Total headcount",
                                    fontSize = 15.sp,
                                    fontWeight =
                                        FontWeight.Bold,
                                    color =
                                        Color(0xFF1A1D20)
                                )

                                Text(
                                    text =
                                        "$headcount people",
                                    fontSize = 12.sp,
                                    color =
                                        Color(0xFF6C757D)
                                )
                            }

                            Text(
                                text =
                                    "B $bricklayers  P $plasterers  V $pavers",
                                fontSize = 13.sp,
                                fontWeight =
                                    FontWeight.Bold,
                                color =
                                    Color(0xFF495057)
                            )
                        }

                        Spacer(
                            modifier =
                                Modifier.height(16.dp)
                        )

                        staffList.forEachIndexed { index, staff ->

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

                                Column(
                                    modifier =
                                        Modifier.weight(1f)
                                ) {

                                    Text(
                                        text =
                                            staff.name,
                                        fontWeight =
                                            FontWeight.Medium
                                    )

                                    Text(
                                        text =
                                            staff.job.replaceFirstChar {
                                                it.uppercase()
                                            },
                                        fontSize =
                                            12.sp,
                                        color =
                                            Color(0xFF6C757D)
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        staffList.removeAt(
                                            index
                                        )
                                    }
                                ) {

                                    Icon(
                                        imageVector =
                                            Icons.Default.Close,
                                        contentDescription =
                                            "Remove worker"
                                    )
                                }
                            }
                        }

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )

                        Chip(
                            text = "+ Add name",
                            selected = false,
                            isDashed = true,
                            onClick = {
                                showAddStaffDialog = true
                            }
                        )
                    }
                }
            }

            item {

                FormSectionTitle(
                    title = "POWER TOOLS USED"
                )

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

                FlowRow(
                    runSpacing = 8.dp
                ) {

                    powerTools.forEachIndexed {
                            index,
                            tool ->

                        Chip(
                            text = tool.name,
                            selected =
                                tool.selected,
                            onClick = {

                                powerTools[index] =
                                    tool.copy(
                                        selected =
                                            !tool.selected
                                    )
                            }
                        )
                    }
                }
            }

            item {

                FormSectionTitle(
                    title =
                        "PLANT & MACHINERY"
                )

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

                FlowRow(
                    runSpacing = 8.dp
                ) {

                    plantMachinery.forEachIndexed {
                            index,
                            machine ->

                        Chip(
                            text =
                                machine.name,
                            selected =
                                machine.selected,
                            onClick = {

                                plantMachinery[index] =
                                    machine.copy(
                                        selected =
                                            !machine.selected
                                    )
                            }
                        )
                    }
                }
            }

            item {

                FormSectionTitle(
                    title =
                        "PHOTO EVIDENCE"
                )

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    capturedPhotos.forEachIndexed {
                            index,
                            photoData ->

                        Box(
                            modifier =
                                Modifier
                                    .size(72.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            12.dp
                                        )
                                    )
                                    .background(
                                        Color(0xFFE0E0E0)
                                    )
                        ) {

                            Text(
                                text =
                                    "Photo ${index + 1}",
                                modifier =
                                    Modifier.align(
                                        Alignment.Center
                                    ),
                                fontSize = 10.sp,
                                color =
                                    Color.DarkGray
                            )
                        }
                    }

                    Box(
                        modifier =
                            Modifier
                                .size(72.dp)
                                .clip(
                                    RoundedCornerShape(
                                        12.dp
                                    )
                                )
                                .background(
                                    Color.White
                                )
                                .border(
                                    1.dp,
                                    Color(0xFFE0E0E0),
                                    RoundedCornerShape(
                                        12.dp
                                    )
                                )
                                .clickable {

                                    permissionLauncher
                                        .launch(
                                            Manifest.permission.CAMERA
                                        )
                                },
                        contentAlignment =
                            Alignment.Center
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.CameraAlt,
                            contentDescription =
                                "Camera",
                            tint =
                                Color(0xFF6C757D),
                            modifier =
                                Modifier.size(24.dp)
                        )
                    }
                }
            }

            item {

                FormSectionTitle(
                    title = "NOTES"
                )

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = {
                        notes = it
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "Optional notes"
                        )
                    },
                    minLines = 3
                )
            }

            if (errorMessage != null) {

                item {

                    Text(
                        text =
                            errorMessage
                                ?: "",
                        color =
                            Color(0xFFD32F2F),
                        fontWeight =
                            FontWeight.Medium
                    )
                }
            }
        }

        Surface(
            modifier =
                Modifier.fillMaxWidth(),
            color =
                Color.White,
            shadowElevation =
                8.dp
        ) {

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
            ) {

                Button(
                    enabled =
                        !isSubmitting &&
                                selectedSite?.id != null,
                    onClick = {

                        val staffJson =
                            Json.encodeToString(
                                staffList.toList()
                            )

                        val toolsJson =
                            Json.encodeToString(
                                powerTools
                                    .filter {
                                        it.selected
                                    }
                                    .map {
                                        it.name
                                    }
                            )

                        val plantJson =
                            Json.encodeToString(
                                plantMachinery
                                    .filter {
                                        it.selected
                                    }
                                    .map {
                                        it.name
                                    }
                            )

                        val photos =
                            capturedPhotos.map {
                                PhotoInput(
                                    photoData = it,
                                    caption = null
                                )
                            }

                        foremanViewModel
                            .submitDailyUpdate(
                                staffNames =
                                    staffJson,
                                powerTools =
                                    toolsJson,
                                plantMachines =
                                    plantJson,
                                photos =
                                    photos,
                                notes =
                                    notes
                                        .trim()
                                        .takeIf {
                                            it.isNotBlank()
                                        },
                                onSubmitDone =
                                    onSubmitSuccess
                            )
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                    shape =
                        RoundedCornerShape(12.dp),
                    colors =
                        ButtonDefaults
                            .buttonColors(
                                containerColor =
                                    Color(0xFFFF6D00)
                            )
                ) {

                    if (isSubmitting) {

                        CircularProgressIndicator(
                            modifier =
                                Modifier.size(22.dp),
                            color =
                                Color.White,
                            strokeWidth =
                                2.dp
                        )

                    } else {

                        Text(
                            text =
                                "Submit Update",
                            fontSize =
                                16.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color =
                                Color.White
                        )
                    }
                }
            }
        }
    }

    if (showAddStaffDialog) {

        AddStaffDialog(
            onDismiss = {
                showAddStaffDialog = false
            },
            onAdd = { name, job ->

                staffList.add(
                    StaffMember(
                        name = name,
                        job = job
                    )
                )

                showAddStaffDialog = false
            }
        )
    }

    if (showCameraPermissionMessage) {

        AlertDialog(
            onDismissRequest = {
                showCameraPermissionMessage = false
            },
            title = {
                Text("Camera permission required")
            },
            text = {
                Text(
                    "Camera access is required to capture site photos."
                )
            },
            confirmButton = {

                TextButton(
                    onClick = {
                        showCameraPermissionMessage =
                            false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
private fun AddStaffDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String) -> Unit
) {

    var name by
    remember {
        mutableStateOf("")
    }

    var selectedJob by
    remember {
        mutableStateOf("bricklayers")
    }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(
                "Add person"
            )
        },

        text = {

            Column {

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    label = {
                        Text("Name")
                    },
                    singleLine = true
                )

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

                Text(
                    text =
                        "Job",
                    fontWeight =
                        FontWeight.Bold
                )

                JobOption(
                    label = "Bricklayers",
                    value = "bricklayers",
                    selectedJob = selectedJob,
                    onSelected = {
                        selectedJob = it
                    }
                )

                JobOption(
                    label = "Plasterers",
                    value = "plasterers",
                    selectedJob = selectedJob,
                    onSelected = {
                        selectedJob = it
                    }
                )

                JobOption(
                    label = "Pavers",
                    value = "pavers",
                    selectedJob = selectedJob,
                    onSelected = {
                        selectedJob = it
                    }
                )
            }
        },

        confirmButton = {

            TextButton(
                enabled =
                    name.isNotBlank(),
                onClick = {
                    onAdd(
                        name.trim(),
                        selectedJob
                    )
                }
            ) {
                Text("Add")
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun JobOption(
    label: String,
    value: String,
    selectedJob: String,
    onSelected: (String) -> Unit
) {

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    onSelected(value)
                },
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        RadioButton(
            selected =
                selectedJob == value,
            onClick = {
                onSelected(value)
            }
        )

        Text(
            text = label
        )
    }
}

fun bitmapToBase64(
    bitmap: Bitmap
): String {

    val outputStream =
        java.io.ByteArrayOutputStream()

    bitmap.compress(
        Bitmap.CompressFormat.JPEG,
        80,
        outputStream
    )

    val bytes =
        outputStream.toByteArray()

    return Base64.encodeToString(
        bytes,
        Base64.NO_WRAP
    )
}

data class ToolItem(
    val name: String,
    val selected: Boolean
)

@Composable
fun FormSectionTitle(
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
fun Chip(
    text: String,
    selected: Boolean,
    isDashed: Boolean = false,
    onClick: () -> Unit
) {

    Surface(
        modifier =
            Modifier.clickable(
                onClick = onClick
            ),
        shape =
            RoundedCornerShape(20.dp),
        color =
            if (selected) {
                Color(0xFFFFC107)
            } else {
                Color(0xFFE9ECEF)
            },
        border =
            if (isDashed) {
                BorderStroke(
                    1.dp,
                    Color(0xFFCED4DA)
                )
            } else {
                null
            }
    ) {

        Box(
            modifier =
                Modifier.padding(
                    horizontal = 14.dp,
                    vertical = 8.dp
                ),
            contentAlignment =
                Alignment.Center
        ) {

            Text(
                text = text,
                fontSize = 13.sp,
                fontWeight =
                    if (selected) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Medium
                    },
                color =
                    if (selected) {
                        Color(0xFF1A1D20)
                    } else {
                        Color(0xFF495057)
                    }
            )
        }
    }
}

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    runSpacing: androidx.compose.ui.unit.Dp = 8.dp,
    content: @Composable () -> Unit
) {

    Column(
        modifier = modifier,
        verticalArrangement =
            Arrangement.spacedBy(
                runSpacing
            )
    ) {
        content()
    }
}