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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.example.onsite_mockups.data.models.Profile
import com.example.onsite_mockups.data.network.SupabaseClient
import com.example.onsite_mockups.security.OnSiteBiometricManager
import com.example.onsite_mockups.ui.viewmodels.AuthViewModel
import com.example.onsite_mockups.ui.viewmodels.LoginState
import io.github.jan.supabase.gotrue.auth

/**
 * The login screen of the application.
 * Supports email/password login, Google OAuth, and biometric authentication.
 */
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    isGoogleCallback: Boolean = false,
    onLoginSuccess: (Profile) -> Unit,
    onForgotPasswordClick: () -> Unit = {}
) {
    // Local UI state for form fields
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var biometricPromptShown by remember {
        mutableStateOf(false)
    }

    // Observe login state from ViewModel
    val loginState by authViewModel.loginState.collectAsState()

    val context = LocalContext.current

    val activity = context as? FragmentActivity

    /*
     * Handle the Google OAuth callback automatically when redirected back to the app.
     */
    LaunchedEffect(isGoogleCallback) {
        if (!isGoogleCallback) {
            return@LaunchedEffect
        }

        val user =
            SupabaseClient
                .client
                .auth
                .currentUserOrNull()

        if (user != null) {
            authViewModel.completeGoogleLogin { profile ->
                onLoginSuccess(profile)
            }
        }
    }

    /*
     * Automatically prompt for biometric authentication if enabled.
     */
    LaunchedEffect(
        isGoogleCallback,
        activity
    ) {
        if (
            isGoogleCallback ||
            activity == null ||
            biometricPromptShown
        ) {
            return@LaunchedEffect
        }

        val biometricEnabled =
            OnSiteBiometricManager.isEnabled(context)

        if (!biometricEnabled) {
            return@LaunchedEffect
        }

        if (!OnSiteBiometricManager.canAuthenticate(context)) {
            return@LaunchedEffect
        }

        biometricPromptShown = true

        // Trigger the biometric prompt
        OnSiteBiometricManager.authenticate(
            activity = activity,
            title = "Unlock OnSite",
            subtitle = "Verify your identity to continue",
            onSuccess = {
                authViewModel.loginWithBiometrics { profile ->
                    onLoginSuccess(profile)
                }
            },
            onFailure = {
                // Keep the normal login screen available on failure.
            }
        )
    }

    // UI Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9FB))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 28.dp,
                    vertical = 32.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Logo
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .background(
                        Color(0xFF1A1D20),
                        RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "OS",
                    color = Color(0xFFFFC107),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Welcome back",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1D20)
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Sign in to continue to OnSite",
                fontSize = 14.sp,
                color = Color(0xFF6C757D)
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            // Email Input
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = {
                    Text("Email")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email"
                    )
                },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // Password Input with Visibility Toggle
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = {
                    Text("Password")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password"
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Text(
                            text =
                                if (passwordVisible) {
                                    "Hide"
                                } else {
                                    "Show"
                                },
                            fontSize = 12.sp,
                            color = Color(0xFFFF6D00)
                        )
                    }
                },
                visualTransformation =
                    if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            TextButton(
                onClick = onForgotPasswordClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Forgot password?",
                    color = Color(0xFFFF6D00),
                    fontSize = 13.sp
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // Error Message Display
            if (loginState is LoginState.Error) {
                Text(
                    text = (loginState as LoginState.Error).message,
                    color = Color(0xFFD32F2F),
                    fontSize = 13.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )
            }

            // Sign In Button
            Button(
                onClick = {
                    authViewModel.login(
                        email = email,
                        password = password
                    ) { profile ->
                        onLoginSuccess(profile)
                    }
                },
                enabled = loginState !is LoginState.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF6D00),
                    contentColor = Color.White
                )
            ) {
                if (loginState is LoginState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Sign In",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Manual Biometric Unlock Button
            if (OnSiteBiometricManager.isEnabled(context)) {
                Spacer(modifier = Modifier.height(12.dp))
                
                OutlinedButton(
                    onClick = {
                        if (activity != null) {
                            OnSiteBiometricManager.authenticate(
                                activity = activity,
                                title = "Unlock OnSite",
                                subtitle = "Verify your identity to continue",
                                onSuccess = {
                                    authViewModel.loginWithBiometrics { profile ->
                                        onLoginSuccess(profile)
                                    }
                                }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFFF6D00))
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = "Biometric Login",
                        tint = Color(0xFFFF6D00),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Biometric Unlock",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6D00)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "or",
                fontSize = 13.sp,
                color = Color(0xFF9AA0A6)
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // Google OAuth Entry Point (Mocked as a TextField-style row)
            OutlinedTextField(
                value = "",
                onValueChange = {},
                readOnly = true,
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = {
                    Text("Continue with Google")
                },
                leadingIcon = {
                    Text(
                        text = "G",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            authViewModel.loginWithGoogle()
                        }
                    ) {
                        Text(
                            text = "→",
                            fontSize = 20.sp,
                            color = Color(0xFFFF6D00)
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}
