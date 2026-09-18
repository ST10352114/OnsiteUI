package com.example.onsite_mockups.ui.screens.shared

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToNext: () -> Unit
) {
    // 5-second automatic transition as requested by user
    LaunchedEffect(Unit) {
        delay(5000)
        onNavigateToNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF14171A))
            .clickable { onNavigateToNext() },
        contentAlignment = Alignment.Center
    ) {
        // Top warning stripe bar
        WarningStripeBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .align(Alignment.TopCenter)
        )

        // Bottom warning stripe bar
        WarningStripeBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .align(Alignment.BottomCenter)
        )

        // Center Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Yellow app icon box with home icon
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFFFC107)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home Icon",
                    tint = Color(0xFF14171A),
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "On Site",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Daily site reporting, made simple",
                color = Color(0xFF9AA0A6),
                fontSize = 14.sp
            )
        }

        // Small yellow loading line at bottom left as seen in mockup
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 24.dp, bottom = 36.dp)
                .size(width = 40.dp, height = 3.dp)
                .background(Color(0xFFFFC107), RoundedCornerShape(1.5.dp))
        )
    }
}

@Composable
fun WarningStripeBar(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stripeWidth = 24f
        val height = size.height
        val width = size.width
        var currentX = -height

        while (currentX < width + height) {
            // Draw yellow background / black stripe
            drawRect(
                color = Color(0xFFFFC107),
                topLeft = Offset(currentX, 0f),
                size = androidx.compose.ui.geometry.Size(stripeWidth, height)
            )
            drawRect(
                color = Color(0xFF14171A),
                topLeft = Offset(currentX + stripeWidth, 0f),
                size = androidx.compose.ui.geometry.Size(stripeWidth, height)
            )
            currentX += stripeWidth * 2
        }
    }
}
