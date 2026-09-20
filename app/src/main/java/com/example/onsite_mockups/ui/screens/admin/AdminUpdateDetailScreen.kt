package com.example.onsite_mockups.ui.screens.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onsite_mockups.data.models.StaffMember
import com.example.onsite_mockups.ui.screens.foreman.Chip
import com.example.onsite_mockups.ui.screens.foreman.FormSectionTitle
import com.example.onsite_mockups.ui.viewmodels.AdminViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Composable
fun AdminUpdateDetailScreen(
    adminViewModel: AdminViewModel,
    onBackClick: () -> Unit,
    onExportClick: () -> Unit = {},
    onFlagForReviewClick: () -> Unit = {}
) {
    val update by adminViewModel.selectedUpdate.collectAsState()

    val foreman =
        adminViewModel.getForemanForUpdate(update)

    val staff =
        decodeStaff(update?.staffNames)

    val powerTools =
        decodeStringList(update?.powerTools)

    val plantMachines =
        decodeStringList(update?.plantMachines)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9FB))
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
                verticalAlignment = Alignment.CenterVertically
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
                    modifier = Modifier.width(8.dp)
                )

                Column {

                    Text(
                        text =
                            if (update != null) {
                                "Update Details"
                            } else {
                                "No Update"
                            },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1D20)
                    )

                    Text(
                        text =
                            if (update != null) {
                                "Submitted ${update?.updateDate}"
                            } else {
                                "Nothing submitted today"
                            },
                        fontSize = 12.sp,
                        color = Color(0xFF6C757D)
                    )
                }
            }
        }

        if (update == null) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text =
                        "No update data found for this site.",
                    color =
                        Color(0xFF9AA0A6)
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement =
                    Arrangement.spacedBy(20.dp)
            ) {

                item {

                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )

                    SubmitterCard(
                        foremanName =
                            foreman?.fullName
                                ?: "Unknown Foreman",
                        createdAt =
                            update?.createdAt
                                ?: "from mobile"
                    )
                }

                item {

                    FormSectionTitle(
                        title =
                            "STAFF ON SITE — ${staff.size}"
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    if (staff.isEmpty()) {

                        Text(
                            text =
                                "No staff recorded",
                            fontSize =
                                13.sp,
                            color =
                                Color(0xFF9AA0A6)
                        )

                    } else {

                        Column(
                            modifier =
                                Modifier.fillMaxWidth(),
                            verticalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {

                            staff.forEach { person ->

                                StaffCard(
                                    person =
                                        person
                                )
                            }
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
                                "No power tools recorded",
                            fontSize =
                                13.sp,
                            color =
                                Color(0xFF9AA0A6)
                        )

                    } else {

                        ItemList(
                            items =
                                powerTools
                        )
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

                    if (plantMachines.isEmpty()) {

                        Text(
                            text =
                                "No plant or machinery recorded",
                            fontSize =
                                13.sp,
                            color =
                                Color(0xFF9AA0A6)
                        )

                    } else {

                        ItemList(
                            items =
                                plantMachines
                        )
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

                    val photos =
                        update
                            ?.updatePhotos
                            ?: emptyList()

                    if (photos.isEmpty()) {

                        Text(
                            text =
                                "No photos uploaded",
                            fontSize =
                                13.sp,
                            color =
                                Color(0xFF9AA0A6)
                        )

                    } else {

                        Text(
                            text =
                                "${photos.size} photo(s) uploaded",
                            fontSize =
                                13.sp,
                            color =
                                Color(0xFF495057)
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )
                }

                item {

                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
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

                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    OutlinedButton(
                        onClick =
                            onExportClick,
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(52.dp),
                        shape =
                            RoundedCornerShape(12.dp),
                        border =
                            BorderStroke(
                                1.dp,
                                Color(0xFFE0E0E0)
                            ),
                        colors =
                            ButtonDefaults
                                .outlinedButtonColors(
                                    containerColor =
                                        Color.White
                                )
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Download,
                            contentDescription =
                                "Export",
                            tint =
                                Color(0xFF1A1D20),
                            modifier =
                                Modifier.size(18.dp)
                        )

                        Spacer(
                            modifier =
                                Modifier.width(8.dp)
                        )

                        Text(
                            text =
                                "Export",
                            fontSize =
                                15.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color =
                                Color(0xFF1A1D20)
                        )
                    }

                    Button(
                        onClick =
                            onFlagForReviewClick,
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(52.dp),
                        shape =
                            RoundedCornerShape(12.dp),
                        colors =
                            ButtonDefaults
                                .buttonColors(
                                    containerColor =
                                        Color(0xFF1A1D20)
                                )
                    ) {

                        Text(
                            text =
                                "Flag for review",
                            fontSize =
                                15.sp,
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

@Composable
private fun SubmitterCard(
    foremanName: String,
    createdAt: String
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
                    Alignment.CenterVertically
            ) {

                Box(
                    modifier =
                        Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(
                                Color(0xFF1A1D20)
                            ),
                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(
                        text =
                            foremanName
                                .take(2)
                                .uppercase(),
                        color =
                            Color(0xFFFFC107),
                        fontSize =
                            14.sp,
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
                        text =
                            foremanName,
                        fontSize =
                            15.sp,
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
                            "Foreman · Submitted $createdAt",
                        fontSize =
                            12.sp,
                        color =
                            Color(0xFF6C757D)
                    )
                }
            }

            Box(
                modifier =
                    Modifier
                        .clip(
                            RoundedCornerShape(8.dp)
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
                    text =
                        "✓ Synced",
                    fontSize =
                        12.sp,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Color(0xFF2E7D32)
                )
            }
        }
    }
}

@Composable
private fun StaffCard(
    person: StaffMember
) {
    Surface(
        modifier =
            Modifier.fillMaxWidth(),
        shape =
            RoundedCornerShape(12.dp),
        color =
            Color(0xFFFFF8E1)
    ) {

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 14.dp,
                        vertical = 10.dp
                    ),
            horizontalArrangement =
                Arrangement.SpaceBetween,
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text =
                        person.name,
                    fontSize =
                        14.sp,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Color(0xFF1A1D20)
                )

                Text(
                    text =
                        person.job
                            .replaceFirstChar {
                                it.uppercase()
                            },
                    fontSize =
                        12.sp,
                    color =
                        Color(0xFF6C757D)
                )
            }

            Text(
                text =
                    "Staff",
                fontSize =
                    11.sp,
                fontWeight =
                    FontWeight.Medium,
                color =
                    Color(0xFF8A6D00)
            )
        }
    }
}

@Composable
private fun ItemList(
    items: List<String>
) {
    Column(
        modifier =
            Modifier.fillMaxWidth(),
        verticalArrangement =
            Arrangement.spacedBy(8.dp)
    ) {

        items.forEach { item ->

            Chip(
                text =
                    item,
                selected =
                    true,
                onClick = {}
            )
        }
    }
}

private fun decodeStringList(
    json: String?
): List<String> {

    if (json.isNullOrBlank()) {
        return emptyList()
    }

    return try {

        Json.decodeFromString<List<String>>(
            json
        )

    } catch (_: Exception) {

        json
            .split(",")
            .map {
                it.trim()
            }
            .filter {
                it.isNotBlank()
            }
    }
}

private fun decodeStaff(
    json: String?
): List<StaffMember> {

    if (json.isNullOrBlank()) {
        return emptyList()
    }

    return try {

        Json.decodeFromString<List<StaffMember>>(
            json
        )

    } catch (_: Exception) {

        json
            .split(",")
            .map {
                it.trim()
            }
            .filter {
                it.isNotBlank()
            }
            .map {
                StaffMember(
                    name =
                        it,
                    job =
                        "unknown"
                )
            }
    }
}