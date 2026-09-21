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

/**
 * Initial landing screen with branding and an animated entry into the app.
 */
@Composable
fun SplashScreen(
    onNavigateToNext: () -> Unit
) {
    // Automatic transition to the next screen after a delay
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
        // Decorative top warning stripe bar
        WarningStripeBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .align(Alignment.TopCenter)
        )

        // Decorative bottom warning stripe bar
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

        // Small yellow progress indicator at bottom left
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 24.dp, bottom = 36.dp)
                .size(width = 40.dp, height = 3.dp)
                .background(Color(0xFFFFC107), RoundedCornerShape(1.5.dp))
        )
    }
}

/**
 * Draws a bar with diagonal construction-style warning stripes.
 */
@Composable
fun WarningStripeBar(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stripeWidth = 24f
        val height = size.height
        val width = size.width
        var currentX = -height

        while (currentX < width + height) {
            // Alternating yellow and black stripes
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
