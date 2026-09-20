package com.example.onsite_mockups.ui.screens.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onsite_mockups.data.network.SupabaseClient
import com.example.onsite_mockups.ui.viewmodels.AuthViewModel
import com.example.onsite_mockups.ui.viewmodels.LoginState
import io.github.jan.supabase.gotrue.auth

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: (String) -> Unit,
    onGoogleLoginSuccess: (com.example.onsite_mockups.data.models.Profile) -> Unit,
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var googleCallbackHandled by remember {
        mutableStateOf(false)
    }

    val loginState by
    authViewModel.loginState.collectAsState()

    val isLoading =
        loginState is LoginState.Loading

    val errorMessage =
        (loginState as? LoginState.Error)?.message

    LaunchedEffect(Unit) {
        val user =
            SupabaseClient
                .client
                .auth
                .currentUserOrNull()

        if (
            user != null &&
            !googleCallbackHandled
        ) {
            googleCallbackHandled = true

            authViewModel
                .completeGoogleLogin { profile ->
                    onGoogleLoginSuccess(
                        profile
                    )
                }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush =
                    Brush.verticalGradient(
                        colors =
                            listOf(
                                Color(0xFF1A1D20),
                                Color(0xFF101214)
                            )
                    )
            )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(
                    brush =
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    Color(0xFFFFC107)
                                        .copy(
                                            alpha = 0.1f
                                        ),
                                    Color.Transparent
                                )
                        )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp)
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier =
                    Modifier.height(60.dp)
            )

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Box(
                    modifier =
                        Modifier
                            .size(56.dp)
                            .clip(
                                RoundedCornerShape(
                                    16.dp
                                )
                            )
                            .background(
                                Color(0xFFFFC107)
                            ),
                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Construction,
                        contentDescription =
                            "OnSite Logo",
                        tint =
                            Color(0xFF1A1D20),
                        modifier =
                            Modifier.size(32.dp)
                    )
                }

                Spacer(
                    modifier =
                        Modifier.width(16.dp)
                )

                Column {

                    Text(
                        text = "ONSITE",
                        fontSize = 28.sp,
                        fontWeight =
                            FontWeight.Black,
                        color = Color.White,
                        letterSpacing = 2.sp
                    )

                    Text(
                        text =
                            "MOCKUPS PRO",
                        fontSize = 12.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            Color(0xFFFFC107),
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(48.dp)
            )

            Surface(
                modifier =
                    Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(24.dp),
                color =
                    Color(0xFF262A2E),
                tonalElevation = 8.dp
            ) {

                Column(
                    modifier =
                        Modifier.padding(24.dp)
                ) {

                    Text(
                        text =
                            "Welcome Back",
                        fontSize = 22.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            Color.White
                    )

                    Text(
                        text =
                            "Sign in to manage your construction projects",
                        fontSize = 14.sp,
                        color =
                            Color(0xFF9BA3AF),
                        modifier =
                            Modifier.padding(
                                top = 4.dp
                            )
                    )

                    Spacer(
                        modifier =
                            Modifier.height(32.dp)
                    )

                    AnimatedVisibility(
                        visible =
                            errorMessage != null
                    ) {

                        Surface(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        bottom = 16.dp
                                    ),
                            color =
                                Color(0xFFFF5252)
                                    .copy(
                                        alpha = 0.1f
                                    ),
                            shape =
                                RoundedCornerShape(
                                    8.dp
                                ),
                            border =
                                androidx.compose.foundation
                                    .BorderStroke(
                                        1.dp,
                                        Color(0xFFFF5252)
                                            .copy(
                                                alpha = 0.5f
                                            )
                                    )
                        ) {

                            Row(
                                modifier =
                                    Modifier.padding(
                                        12.dp
                                    ),
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Icon(
                                    Icons.Default.Error,
                                    contentDescription =
                                        null,
                                    tint =
                                        Color(0xFFFF5252),
                                    modifier =
                                        Modifier.size(
                                            20.dp
                                        )
                                )

                                Spacer(
                                    modifier =
                                        Modifier.width(
                                            8.dp
                                        )
                                )

                                Text(
                                    text =
                                        errorMessage
                                            ?: "",
                                    color =
                                        Color(0xFFFF5252),
                                    fontSize =
                                        13.sp
                                )
                            }
                        }
                    }

                    Text(
                        text =
                            "EMAIL ADDRESS",
                        fontSize = 11.sp,
                        fontWeight =
                            FontWeight.ExtraBold,
                        color =
                            Color(0xFFFFC107),
                        letterSpacing = 1.sp
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        modifier =
                            Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                "e.g. thabo@onsite.com",
                                color =
                                    Color(0xFF5A626C)
                            )
                        },
                        shape =
                            RoundedCornerShape(
                                12.dp
                            ),
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription =
                                    null,
                                tint =
                                    Color(0xFF9BA3AF)
                            )
                        },
                        colors =
                            OutlinedTextFieldDefaults
                                .colors(
                                    focusedBorderColor =
                                        Color(0xFFFFC107),
                                    unfocusedBorderColor =
                                        Color(0xFF3D444B),
                                    focusedContainerColor =
                                        Color(0xFF1A1D20),
                                    unfocusedContainerColor =
                                        Color(0xFF1A1D20),
                                    focusedTextColor =
                                        Color.White,
                                    unfocusedTextColor =
                                        Color.White
                                )
                    )

                    Spacer(
                        modifier =
                            Modifier.height(20.dp)
                    )

                    Text(
                        text =
                            "PASSWORD",
                        fontSize = 11.sp,
                        fontWeight =
                            FontWeight.ExtraBold,
                        color =
                            Color(0xFFFFC107),
                        letterSpacing = 1.sp
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        modifier =
                            Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(
                                12.dp
                            ),
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription =
                                    null,
                                tint =
                                    Color(0xFF9BA3AF)
                            )
                        },
                        visualTransformation =
                            if (passwordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),
                        trailingIcon = {

                            IconButton(
                                onClick = {
                                    passwordVisible =
                                        !passwordVisible
                                }
                            ) {

                                Icon(
                                    imageVector =
                                        if (
                                            passwordVisible
                                        )
                                            Icons.Default.VisibilityOff
                                        else
                                            Icons.Default.Visibility,
                                    contentDescription =
                                        null,
                                    tint =
                                        Color(0xFF9BA3AF)
                                )
                            }
                        },
                        colors =
                            OutlinedTextFieldDefaults
                                .colors(
                                    focusedBorderColor =
                                        Color(0xFFFFC107),
                                    unfocusedBorderColor =
                                        Color(0xFF3D444B),
                                    focusedContainerColor =
                                        Color(0xFF1A1D20),
                                    unfocusedContainerColor =
                                        Color(0xFF1A1D20),
                                    focusedTextColor =
                                        Color.White,
                                    unfocusedTextColor =
                                        Color.White
                                )
                    )

                    TextButton(
                        onClick =
                            onForgotPasswordClick,
                        modifier =
                            Modifier.align(
                                Alignment.End
                            )
                    ) {

                        Text(
                            "Forgot Password?",
                            color =
                                Color(0xFF9BA3AF),
                            fontSize =
                                13.sp
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier.height(24.dp)
                    )

                    Button(
                        onClick = {
                            authViewModel.login(
                                email,
                                password
                            ) { profile ->
                                onLoginSuccess(
                                    profile.fullName
                                )
                            }
                        },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                        shape =
                            RoundedCornerShape(
                                14.dp
                            ),
                        colors =
                            ButtonDefaults
                                .buttonColors(
                                    containerColor =
                                        Color(0xFFFFC107),
                                    contentColor =
                                        Color(0xFF1A1D20)
                                ),
                        enabled =
                            !isLoading &&
                                    email.isNotBlank() &&
                                    password.isNotBlank()
                    ) {

                        if (isLoading) {

                            CircularProgressIndicator(
                                modifier =
                                    Modifier.size(
                                        24.dp
                                    ),
                                color =
                                    Color(0xFF1A1D20),
                                strokeWidth =
                                    3.dp
                            )

                        } else {

                            Text(
                                "SIGN IN",
                                fontSize = 16.sp,
                                fontWeight =
                                    FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )

                    Row(
                        modifier =
                            Modifier.fillMaxWidth(),
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        HorizontalDivider(
                            modifier =
                                Modifier.weight(1f),
                            color =
                                Color(0xFF3D444B)
                        )

                        Text(
                            text = "  OR  ",
                            color =
                                Color(0xFF707780),
                            fontSize = 12.sp
                        )

                        HorizontalDivider(
                            modifier =
                                Modifier.weight(1f),
                            color =
                                Color(0xFF3D444B)
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )

                    OutlinedButton(
                        onClick = {
                            authViewModel
                                .loginWithGoogle()
                        },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                        shape =
                            RoundedCornerShape(
                                14.dp
                            ),
                        enabled =
                            !isLoading,
                        colors =
                            ButtonDefaults
                                .outlinedButtonColors(
                                    contentColor =
                                        Color.White
                                ),
                        border =
                            androidx.compose.foundation
                                .BorderStroke(
                                    1.dp,
                                    Color(0xFF3D444B)
                                )
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.AccountCircle,
                            contentDescription =
                                null,
                            tint =
                                Color.White
                        )

                        Spacer(
                            modifier =
                                Modifier.width(12.dp)
                        )

                        Text(
                            text =
                                "CONTINUE WITH GOOGLE",
                            fontSize = 14.sp,
                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )
        }
    }
}