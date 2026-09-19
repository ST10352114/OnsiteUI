package com.example.onsite_mockups.ui.screens.admin

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onsite_mockups.ui.screens.foreman.Chip
import com.example.onsite_mockups.ui.screens.foreman.FormSectionTitle

import com.example.onsite_mockups.ui.viewmodels.AdminViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun AdminUpdateDetailScreen(
    adminViewModel: AdminViewModel,
    onBackClick: () -> Unit,
    onExportClick: () -> Unit = {},
    onFlagForReviewClick: () -> Unit = {}
) {
    val update by adminViewModel.selectedUpdate.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9FB))
    ) {
        // Top App Bar
        Surface(
            color = Color(0xFFF9F9FB),
            shadowElevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF1A1D20)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = if (update != null) "Update Details" else "Ridgeview Estate",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1D20)
                    )
                    Text(
                        text = "Submitted ${update?.updateDate ?: "today"}",
                        fontSize = 12.sp,
                        color = Color(0xFF6C757D)
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                // Submitter Card
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
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Avatar TM
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF1A1D20)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (update != null) "TM" else "TM", // Could be dynamic if update has foreman name
                                    color = Color(0xFFFFC107),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = if (update != null) "Thabo Mokoena" else "Thabo Mokoena",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A1D20)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Foreman · Submitted ${update?.createdAt ?: "from mobile"}",
                                    fontSize = 12.sp,
                                    color = Color(0xFF6C757D)
                                )
                            }
                        }

                        // Synced Badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFE8F5E9))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "✓Synced",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }
                }
            }

            item {
                // Section: STAFF ON SITE
                FormSectionTitle(title = "STAFF ON SITE — ${update?.actualLabor ?: 12}")
                Spacer(modifier = Modifier.height(8.dp))

                com.example.onsite_mockups.ui.screens.foreman.FlowRow(
                    runSpacing = 8.dp
                ) {
                    val staff = update?.staffNames?.split(",")?.map { it.trim() } ?: listOf("S. Dlamini", "M. Khumalo")
                    staff.forEach { name ->
                        if (name.isNotEmpty()) Chip(text = name, selected = true, onClick = {})
                    }
                }
            }

            item {
                // Section: POWER TOOLS USED
                FormSectionTitle(title = "POWER TOOLS USED")
                Spacer(modifier = Modifier.height(8.dp))

                com.example.onsite_mockups.ui.screens.foreman.FlowRow(
                    runSpacing = 8.dp
                ) {
                    val tools = update?.powerTools?.split(",")?.map { it.trim() } ?: listOf("Angle grinder", "Drill")
                    tools.forEach { tool ->
                        if (tool.isNotEmpty()) Chip(text = tool, selected = true, onClick = {})
                    }
                }
            }

            item {
                // Section: PLANT & MACHINERY
                FormSectionTitle(title = "PLANT & MACHINERY")
                Spacer(modifier = Modifier.height(8.dp))

                com.example.onsite_mockups.ui.screens.foreman.FlowRow(
                    runSpacing = 8.dp
                ) {
                    val plant = update?.plantMachines?.split(",")?.map { it.trim() } ?: listOf("Excavator", "Concrete mixer")
                    plant.forEach { machine ->
                        if (machine.isNotEmpty()) Chip(text = machine, selected = true, onClick = {})
                    }
                }
            }

            item {
                // Section: PHOTO EVIDENCE — 4
                FormSectionTitle(title = "PHOTO EVIDENCE — 4")
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFD7CCC8))
                    )
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFBCAAA4))
                    )
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFA1887F))
                    )
                    // +1 overflow photo thumbnail
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF6D4C41)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+1",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Bottom Action Bar (Export & Flag for review)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onExportClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0)),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Export",
                        tint = Color(0xFF1A1D20),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Export",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1D20)
                    )
                }

                Button(
                    onClick = onFlagForReviewClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1A1D20)
                    )
                ) {
                    Text(
                        text = "Flag for review",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
