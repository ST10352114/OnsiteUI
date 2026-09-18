package com.example.onsite_mockups.ui.screens.foreman

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
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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

@Composable
fun DailyUpdateFormScreen(
    onBackClick: () -> Unit,
    onSubmitSuccess: () -> Unit
) {
    var headcount by remember { mutableIntStateOf(12) }
    val staffList = remember { mutableStateListOf("S. Dlamini", "M. Khumalo") }

    val powerTools = remember {
        mutableStateListOf(
            ToolItem("Angle grinder", true),
            ToolItem("Drill", true),
            ToolItem("Welder", false),
            ToolItem("Compressor", false)
        )
    }

    val plantMachinery = remember {
        mutableStateListOf(
            ToolItem("Excavator", true),
            ToolItem("Tower crane", false),
            ToolItem("Concrete mixer", true),
            ToolItem("Forklift", false)
        )
    }

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
                        text = "Daily Update",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1D20)
                    )
                    Text(
                        text = "Ridgeview Estate — Block C · Today",
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
                // Section: STAFF ON SITE
                FormSectionTitle(title = "STAFF ON SITE")
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Headcount Stepper Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total headcount",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A1D20)
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Minus Button
                                Surface(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clickable { if (headcount > 0) headcount-- },
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFF1F3F5)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Remove,
                                            contentDescription = "Decrease",
                                            tint = Color(0xFF1A1D20),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = headcount.toString(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A1D20)
                                )

                                // Plus Button (Yellow)
                                Surface(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clickable { headcount++ },
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFFFC107)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Increase",
                                            tint = Color(0xFF1A1D20),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        FlowRow(
                            runSpacing = 8.dp
                        ) {
                            staffList.forEach { staff ->
                                Chip(
                                    text = "$staff ✕",
                                    selected = true,
                                    onClick = { staffList.remove(staff) }
                                )
                            }
                            // Add name chip
                            Chip(
                                text = "+ Add name",
                                selected = false,
                                isDashed = true,
                                onClick = {
                                    staffList.add("New Worker")
                                }
                            )
                        }
                    }
                }
            }

            item {
                // Section: POWER TOOLS USED
                FormSectionTitle(title = "POWER TOOLS USED")
                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    runSpacing = 8.dp
                ) {
                    powerTools.forEachIndexed { index, tool ->
                        Chip(
                            text = tool.name,
                            selected = tool.selected,
                            onClick = {
                                powerTools[index] = tool.copy(selected = !tool.selected)
                            }
                        )
                    }
                    // Custom chip
                    Chip(
                        text = "+ Custom",
                        selected = false,
                        isDashed = true,
                        onClick = {}
                    )
                }
            }

            item {
                // Section: PLANT & MACHINERY
                FormSectionTitle(title = "PLANT & MACHINERY")
                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    runSpacing = 8.dp
                ) {
                    plantMachinery.forEachIndexed { index, machine ->
                        Chip(
                            text = machine.name,
                            selected = machine.selected,
                            onClick = {
                                plantMachinery[index] = machine.copy(selected = !machine.selected)
                            }
                        )
                    }
                    // Custom chip
                    Chip(
                        text = "+ Custom",
                        selected = false,
                        isDashed = true,
                        onClick = {}
                    )
                }
            }

            item {
                // Section: PHOTO EVIDENCE
                FormSectionTitle(title = "PHOTO EVIDENCE")
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Photo Placeholder 1
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFD7CCC8))
                    )
                    // Photo Placeholder 2
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFD7CCC8))
                    )
                    // Camera Upload Button
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Camera",
                            tint = Color(0xFF6C757D),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Bottom Submit Button
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Button(
                    onClick = onSubmitSuccess,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6D00)
                    )
                ) {
                    Text(
                        text = "Submit Update",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
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

data class ToolItem(val name: String, val selected: Boolean)

@Composable
fun Chip(
    text: String,
    selected: Boolean,
    isDashed: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = if (selected) Color(0xFFFFC107) else Color(0xFFE9ECEF),
        border = if (isDashed) androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCED4DA)) else null
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 13.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                color = if (selected) Color(0xFF1A1D20) else Color(0xFF495057)
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
        verticalArrangement = Arrangement.spacedBy(runSpacing)
    ) {
        content()
    }
}
