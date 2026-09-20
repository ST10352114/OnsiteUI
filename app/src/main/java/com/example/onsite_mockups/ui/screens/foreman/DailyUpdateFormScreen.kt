package com.example.onsite_mockups.ui.screens.foreman

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onsite_mockups.data.models.StaffMember
import com.example.onsite_mockups.data.network.PhotoInput
import com.example.onsite_mockups.ui.viewmodels.ForemanViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun DailyUpdateFormScreen(
    foremanViewModel: ForemanViewModel,
    onBackClick: () -> Unit,
    onSubmitSuccess: () -> Unit
) {
    val selectedSite by
    foremanViewModel.selectedSite.collectAsState()

    val todayUpdate by
    foremanViewModel.todayUpdate.collectAsState()

    val isLoadingTodayUpdate by
    foremanViewModel.isLoadingTodayUpdate.collectAsState()

    val isSubmitting by
    foremanViewModel.isSubmitting.collectAsState()

    val errorMessage by
    foremanViewModel.errorMessage.collectAsState()

    val staffList =
        remember {
            mutableStateListOf<StaffMember>()
        }

    val powerTools =
        remember {
            mutableStateListOf<ToolItem>()
        }

    val plantMachinery =
        remember {
            mutableStateListOf<ToolItem>()
        }

    val capturedPhotos =
        remember {
            mutableStateListOf<String>()
        }

    var notes by
    remember {
        mutableStateOf("")
    }

    var showAddStaffDialog by
    remember {
        mutableStateOf(false)
    }

    var showAddToolDialog by
    remember {
        mutableStateOf(false)
    }

    var showAddPlantDialog by
    remember {
        mutableStateOf(false)
    }

    var showCameraPermissionMessage by
    remember {
        mutableStateOf(false)
    }

    var hasLoadedExistingUpdate by
    remember {
        mutableStateOf(false)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->

            if (bitmap != null) {
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

    /*
     * Load today's update whenever the selected site changes.
     */
    LaunchedEffect(selectedSite?.id) {

        val siteId =
            selectedSite?.id

        if (!siteId.isNullOrBlank()) {

            hasLoadedExistingUpdate = false

            foremanViewModel
                .loadTodayUpdate(siteId)
        }
    }

    /*
     * Populate the form from today's existing update.
     *
     * If there is no update, todayUpdate is null and
     * the form stays empty.
     */
    LaunchedEffect(
        todayUpdate,
        isLoadingTodayUpdate
    ) {

        /*
         * Do not touch the form while the API is still loading.
         *
         * This prevents the initial null todayUpdate value from
         * being mistaken for "there is no update".
         */
        if (isLoadingTodayUpdate) {
            return@LaunchedEffect
        }

        if (hasLoadedExistingUpdate) {
            return@LaunchedEffect
        }

        /*
         * The API has finished loading and there is no update
         * for today.
         *
         * Make sure the form is empty.
         */
        val update =
            todayUpdate
                ?: run {

                    staffList.clear()
                    powerTools.clear()
                    plantMachinery.clear()
                    capturedPhotos.clear()

                    notes = ""

                    hasLoadedExistingUpdate = true

                    return@LaunchedEffect
                }

        /*
         * An update for today exists.
         *
         * Load the saved data into the form.
         */
        staffList.clear()
        powerTools.clear()
        plantMachinery.clear()
        capturedPhotos.clear()

        /*
         * STAFF
         *
         * Stored in Supabase as JSON:
         *
         * [
         *   {"name":"Thabo","job":"bricklayers"},
         *   {"name":"Thandi","job":"plasterers"}
         * ]
         */
        if (!update.staffNames.isNullOrBlank()) {

            try {

                val staff =
                    Json.decodeFromString<List<StaffMember>>(
                        update.staffNames
                    )

                staffList.addAll(
                    staff
                )

            } catch (_: Exception) {
            }
        }

        /*
         * POWER TOOLS
         *
         * Stored as:
         *
         * ["Angle grinder","Welder","Compressor"]
         */
        if (!update.powerTools.isNullOrBlank()) {

            try {

                val tools =
                    Json.decodeFromString<List<String>>(
                        update.powerTools
                    )

                powerTools.addAll(
                    tools.map { toolName ->

                        ToolItem(
                            name =
                                toolName,
                            selected =
                                true
                        )
                    }
                )

            } catch (_: Exception) {
            }
        }

        /*
         * PLANT & MACHINERY
         *
         * Stored as:
         *
         * ["Excavator","Tower crane","Concrete mixer"]
         */
        if (!update.plantMachines.isNullOrBlank()) {

            try {

                val machines =
                    Json.decodeFromString<List<String>>(
                        update.plantMachines
                    )

                plantMachinery.addAll(
                    machines.map { machineName ->

                        ToolItem(
                            name =
                                machineName,
                            selected =
                                true
                        )
                    }
                )

            } catch (_: Exception) {
            }
        }

        /*
         * PHOTOS
         *
         * Photos are already returned as base64 strings.
         */
        capturedPhotos.addAll(
            update.updatePhotos.map {
                it.photoData
            }
        )

        /*
         * NOTES
         */
        notes =
            update.notes ?: ""

        /*
         * Prevent the form from being populated again
         * on normal recomposition.
         */
        hasLoadedExistingUpdate = true
    }

    val bricklayers =
        staffList.count {
            it.job.equals(
                "bricklayers",
                ignoreCase = true
            )
        }

    val plasterers =
        staffList.count {
            it.job.equals(
                "plasterers",
                ignoreCase = true
            )
        }

    val pavers =
        staffList.count {
            it.job.equals(
                "pavers",
                ignoreCase = true
            )
        }

    val headcount =
        staffList.size

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    Color(0xFFF9F9FB)
                )
    ) {

        Surface(
            color =
                Color(0xFFF9F9FB)
        ) {

            Row(
                modifier =
                    Modifier
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
                        text =
                            "Daily Update",
                        fontSize =
                            18.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            Color(0xFF1A1D20)
                    )

                    Text(
                        text =
                            "${selectedSite?.name ?: "Site"} · Today",
                        fontSize =
                            12.sp,
                        color =
                            Color(0xFF6C757D)
                    )
                }
            }
        }

        if (
            isLoadingTodayUpdate &&
            !hasLoadedExistingUpdate
        ) {

            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .weight(1f),
                contentAlignment =
                    Alignment.Center
            ) {

                CircularProgressIndicator(
                    color =
                        Color(0xFFFF6D00)
                )
            }

        } else {

            LazyColumn(
                modifier =
                    Modifier
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
                        title =
                            "STAFF ON SITE"
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
                                        fontSize =
                                            15.sp,
                                        fontWeight =
                                            FontWeight.Bold,
                                        color =
                                            Color(0xFF1A1D20)
                                    )

                                    Text(
                                        text =
                                            "$headcount people",
                                        fontSize =
                                            12.sp,
                                        color =
                                            Color(0xFF6C757D)
                                    )
                                }

                                Text(
                                    text =
                                        "B $bricklayers  P $plasterers  V $pavers",
                                    fontSize =
                                        13.sp,
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

                            staffList.forEachIndexed {
                                    index,
                                    staff ->

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
                                                staff.job
                                                    .replaceFirstChar {
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
                                text =
                                    "+ Add name",
                                selected =
                                    false,
                                isDashed =
                                    true,
                                onClick = {
                                    showAddStaffDialog = true
                                }
                            )
                        }
                    }
                }

                item {

                    FormSectionTitle(
                        title =
                            "POWER TOOLS USED"
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    if (powerTools.isEmpty()) {

                        Text(
                            text =
                                "No power tools added.",
                            fontSize =
                                13.sp,
                            color =
                                Color(0xFF6C757D)
                        )

                    } else {

                        Column(
                            verticalArrangement =
                                Arrangement.spacedBy(
                                    8.dp
                                )
                        ) {

                            powerTools.forEachIndexed {
                                    index,
                                    tool ->

                                Row(
                                    modifier =
                                        Modifier.fillMaxWidth(),
                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {

                                    Chip(
                                        text =
                                            tool.name,
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

                                    Spacer(
                                        modifier =
                                            Modifier.width(4.dp)
                                    )

                                    IconButton(
                                        onClick = {
                                            powerTools.removeAt(
                                                index
                                            )
                                        }
                                    ) {

                                        Icon(
                                            imageVector =
                                                Icons.Default.Close,
                                            contentDescription =
                                                "Remove power tool"
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    OutlinedAddButton(
                        text =
                            "Add power tool",
                        onClick = {
                            showAddToolDialog = true
                        }
                    )
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

                    if (plantMachinery.isEmpty()) {

                        Text(
                            text =
                                "No plant or machinery added.",
                            fontSize =
                                13.sp,
                            color =
                                Color(0xFF6C757D)
                        )

                    } else {

                        Column(
                            verticalArrangement =
                                Arrangement.spacedBy(
                                    8.dp
                                )
                        ) {

                            plantMachinery.forEachIndexed {
                                    index,
                                    machine ->

                                Row(
                                    modifier =
                                        Modifier.fillMaxWidth(),
                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {

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

                                    Spacer(
                                        modifier =
                                            Modifier.width(4.dp)
                                    )

                                    IconButton(
                                        onClick = {
                                            plantMachinery.removeAt(
                                                index
                                            )
                                        }
                                    ) {

                                        Icon(
                                            imageVector =
                                                Icons.Default.Close,
                                            contentDescription =
                                                "Remove plant or machinery"
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    OutlinedAddButton(
                        text =
                            "Add plant or machinery",
                        onClick = {
                            showAddPlantDialog = true
                        }
                    )
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

                            PhotoThumbnail(
                                photoData =
                                    photoData,
                                label =
                                    "Photo ${index + 1}",
                                onRemove = {
                                    capturedPhotos.removeAt(
                                        index
                                    )
                                }
                            )
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
                        title =
                            "NOTES"
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    OutlinedTextField(
                        value =
                            notes,
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
                        minLines =
                            3
                    )
                }

                if (errorMessage != null) {

                    item {

                        Text(
                            text =
                                errorMessage ?: "",
                            color =
                                Color(0xFFD32F2F),
                            fontWeight =
                                FontWeight.Medium
                        )
                    }
                }

                item {
                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )
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
                                        photoData =
                                            it,
                                        caption =
                                            null
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
                                    if (
                                        todayUpdate != null
                                    ) {
                                        "Update Daily Report"
                                    } else {
                                        "Submit Update"
                                    },
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
    }

    if (showAddStaffDialog) {

        AddStaffDialog(
            onDismiss = {
                showAddStaffDialog = false
            },
            onAdd = { name, job ->

                staffList.add(
                    StaffMember(
                        name =
                            name,
                        job =
                            job
                    )
                )

                showAddStaffDialog = false
            }
        )
    }

    if (showAddToolDialog) {

        AddItemDialog(
            title =
                "Add power tool",
            label =
                "Tool name",
            onDismiss = {
                showAddToolDialog = false
            },
            onAdd = { name ->

                if (
                    powerTools.none {
                        it.name.equals(
                            name,
                            ignoreCase = true
                        )
                    }
                ) {

                    powerTools.add(
                        ToolItem(
                            name =
                                name,
                            selected =
                                true
                        )
                    )
                }

                showAddToolDialog = false
            }
        )
    }

    if (showAddPlantDialog) {

        AddItemDialog(
            title =
                "Add plant or machinery",
            label =
                "Plant or machinery name",
            onDismiss = {
                showAddPlantDialog = false
            },
            onAdd = { name ->

                if (
                    plantMachinery.none {
                        it.name.equals(
                            name,
                            ignoreCase = true
                        )
                    }
                ) {

                    plantMachinery.add(
                        ToolItem(
                            name =
                                name,
                            selected =
                                true
                        )
                    )
                }

                showAddPlantDialog = false
            }
        )
    }

    if (showCameraPermissionMessage) {

        AlertDialog(
            onDismissRequest = {
                showCameraPermissionMessage = false
            },
            title = {
                Text(
                    "Camera permission required"
                )
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
        onDismissRequest =
            onDismiss,
        title = {
            Text(
                "Add person"
            )
        },
        text = {

            Column {

                OutlinedTextField(
                    value =
                        name,
                    onValueChange = {
                        name = it
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    label = {
                        Text("Name")
                    },
                    singleLine =
                        true
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
                    label =
                        "Bricklayers",
                    value =
                        "bricklayers",
                    selectedJob =
                        selectedJob,
                    onSelected = {
                        selectedJob = it
                    }
                )

                JobOption(
                    label =
                        "Plasterers",
                    value =
                        "plasterers",
                    selectedJob =
                        selectedJob,
                    onSelected = {
                        selectedJob = it
                    }
                )

                JobOption(
                    label =
                        "Pavers",
                    value =
                        "pavers",
                    selectedJob =
                        selectedJob,
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
                onClick =
                    onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun AddItemDialog(
    title: String,
    label: String,
    onDismiss: () -> Unit,
    onAdd: (String) -> Unit
) {
    var value by
    remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest =
            onDismiss,
        title = {
            Text(title)
        },
        text = {

            OutlinedTextField(
                value =
                    value,
                onValueChange = {
                    value = it
                },
                modifier =
                    Modifier.fillMaxWidth(),
                label = {
                    Text(label)
                },
                singleLine =
                    true
            )
        },
        confirmButton = {

            TextButton(
                enabled =
                    value.isNotBlank(),
                onClick = {
                    onAdd(
                        value.trim()
                    )
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {

            TextButton(
                onClick =
                    onDismiss
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
            text =
                label
        )
    }
}

@Composable
private fun OutlinedAddButton(
    text: String,
    onClick: () -> Unit
) {
    Surface(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = onClick
                ),
        shape =
            RoundedCornerShape(10.dp),
        color =
            Color.Transparent,
        border =
            BorderStroke(
                1.dp,
                Color(0xFFCED4DA)
            )
    ) {

        Row(
            modifier =
                Modifier.padding(
                    horizontal = 14.dp,
                    vertical = 10.dp
                ),
            horizontalArrangement =
                Arrangement.Center,
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Icon(
                imageVector =
                    Icons.Default.Add,
                contentDescription =
                    null,
                modifier =
                    Modifier.size(18.dp),
                tint =
                    Color(0xFF495057)
            )

            Spacer(
                modifier =
                    Modifier.width(6.dp)
            )

            Text(
                text =
                    text,
                fontSize =
                    13.sp,
                fontWeight =
                    FontWeight.Medium,
                color =
                    Color(0xFF495057)
            )
        }
    }
}

@Composable
private fun PhotoThumbnail(
    photoData: String,
    label: String,
    onRemove: () -> Unit
) {
    val bitmap =
        remember(photoData) {
            decodeBase64Bitmap(
                photoData
            )
        }

    Box(
        modifier =
            Modifier
                .size(72.dp)
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .background(
                    Color(0xFFE0E0E0)
                )
    ) {

        if (bitmap != null) {

            androidx.compose.foundation.Image(
                bitmap =
                    bitmap.asImageBitmap(),
                contentDescription =
                    label,
                modifier =
                    Modifier.fillMaxSize()
            )

        } else {

            Text(
                text =
                    label,
                modifier =
                    Modifier.align(
                        Alignment.Center
                    ),
                fontSize =
                    10.sp,
                color =
                    Color.DarkGray
            )
        }

        IconButton(
            onClick =
                onRemove,
            modifier =
                Modifier
                    .align(
                        Alignment.TopEnd
                    )
                    .size(28.dp)
        ) {

            Icon(
                imageVector =
                    Icons.Default.Close,
                contentDescription =
                    "Remove photo",
                tint =
                    Color.White,
                modifier =
                    Modifier
                        .size(18.dp)
                        .background(
                            Color.Black.copy(
                                alpha = 0.55f
                            ),
                            RoundedCornerShape(
                                50
                            )
                        )
            )
        }
    }
}

private fun decodeBase64Bitmap(
    value: String
): Bitmap? {
    return try {

        val cleanValue =
            value.substringAfter(
                "base64,",
                value
            )

        val bytes =
            Base64.decode(
                cleanValue,
                Base64.DEFAULT
            )

        BitmapFactory.decodeByteArray(
            bytes,
            0,
            bytes.size
        )

    } catch (_: Exception) {
        null
    }
}

private fun bitmapToBase64(
    bitmap: Bitmap
): String {
    val outputStream =
        java.io.ByteArrayOutputStream()

    bitmap.compress(
        Bitmap.CompressFormat.JPEG,
        80,
        outputStream
    )

    return Base64.encodeToString(
        outputStream.toByteArray(),
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
        text =
            title,
        fontSize =
            12.sp,
        fontWeight =
            FontWeight.Bold,
        color =
            Color(0xFF9AA0A6),
        letterSpacing =
            1.sp
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
                onClick =
                    onClick
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
                text =
                    text,
                fontSize =
                    13.sp,
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