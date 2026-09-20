package com.example.onsite_mockups.security

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object OnSiteBiometricManager {

    private const val PREFS_NAME = "onsite_security"
    private const val KEY_ENABLED = "biometric_enabled"
    private const val KEY_USER_ID = "biometric_user_id"

    fun isEnabled(context: Context): Boolean {
        return context
            .getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )
            .getBoolean(
                KEY_ENABLED,
                false
            )
    }

    fun enabledForUser(
        context: Context,
        userId: String
    ): Boolean {
        val preferences =
            context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )

        return preferences.getBoolean(
            KEY_ENABLED,
            false
        ) &&
                preferences.getString(
                    KEY_USER_ID,
                    null
                ) == userId
    }

    fun enable(
        context: Context,
        userId: String
    ) {
        context
            .getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )
            .edit()
            .putBoolean(
                KEY_ENABLED,
                true
            )
            .putString(
                KEY_USER_ID,
                userId
            )
            .apply()
    }

    fun disable(context: Context) {
        context
            .getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )
            .edit()
            .clear()
            .apply()
    }

    fun canAuthenticate(
        context: Context
    ): Boolean {
        val biometricManager =
            BiometricManager.from(context)

        return biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_WEAK
        ) ==
                BiometricManager.BIOMETRIC_SUCCESS
    }

    fun authenticate(
        activity: FragmentActivity,
        title: String,
        subtitle: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit = {}
    ) {
        val biometricManager =
            BiometricManager.from(activity)

        val result =
            biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_WEAK
            )

        if (
            result !=
            BiometricManager.BIOMETRIC_SUCCESS
        ) {
            onFailure(
                when (result) {
                    BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                        "This device does not have biometric hardware."

                    BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                        "Biometric authentication is currently unavailable."

                    BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                        "No fingerprint or face is enrolled on this device."

                    else ->
                        "Biometric authentication is unavailable."
                }
            )

            return
        }

        val executor =
            ContextCompat.getMainExecutor(
                activity
            )

        val prompt =
            BiometricPrompt(
                activity,
                executor,
                object :
                    BiometricPrompt.AuthenticationCallback() {

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult
                    ) {
                        super.onAuthenticationSucceeded(
                            result
                        )

                        onSuccess()
                    }

                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence
                    ) {
                        super.onAuthenticationError(
                            errorCode,
                            errString
                        )

                        onFailure(
                            errString.toString()
                        )
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()

                        onFailure(
                            "Biometric authentication failed."
                        )
                    }
                }
            )

        val promptInfo =
            BiometricPrompt.PromptInfo.Builder()
                .setTitle(title)
                .setSubtitle(subtitle)
                .setNegativeButtonText("Cancel")
                .setConfirmationRequired(false)
                .build()

        prompt.authenticate(
            promptInfo
        )
    }
}