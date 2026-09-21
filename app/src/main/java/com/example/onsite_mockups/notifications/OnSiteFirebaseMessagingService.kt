package com.example.onsite_mockups.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.onsite_mockups.MainActivity
import com.example.onsite_mockups.R
import com.example.onsite_mockups.data.repository.OnSiteRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Service for handling Firebase Cloud Messaging (FCM) notifications.
 * Manages token registration and displays local notifications when messages are received.
 */
class OnSiteFirebaseMessagingService :
    FirebaseMessagingService() {

    companion object {

        private const val TAG =
            "OnSiteFCM"

        private const val CHANNEL_ID =
            "onsite_notifications"

        private const val CHANNEL_NAME =
            "OnSite Notifications"

        private const val CHANNEL_DESCRIPTION =
            "Notifications from the OnSite construction management system"
    }

    override fun onCreate() {

        super.onCreate()

        // Ensure the notification channel is created for Android O+
        createNotificationChannel()
    }

    /**
     * Called when a new FCM token is generated for the device.
     */
    override fun onNewToken(
        token: String
    ) {

        super.onNewToken(token)

        Log.d(
            TAG,
            "FCM token refreshed."
        )

        // Register the new token with our backend
        CoroutineScope(
            Dispatchers.IO
        ).launch {

            try {

                OnSiteRepository
                    .registerDeviceToken(
                        token
                    )

                Log.d(
                    TAG,
                    "Refreshed FCM token registered."
                )

            } catch (e: Exception) {

                Log.e(
                    TAG,
                    "Could not register refreshed FCM token.",
                    e
                )
            }
        }
    }

    /**
     * Called when an FCM message is received while the app is in foreground or has data payload.
     */
    override fun onMessageReceived(
        remoteMessage: RemoteMessage
    ) {

        super.onMessageReceived(
            remoteMessage
        )

        Log.d(
            TAG,
            "FCM message received."
        )

        // Extract title and message from the notification or data payload
        val title =
            remoteMessage
                .notification
                ?.title
                ?: remoteMessage.data["title"]
                ?: "OnSite"

        val message =
            remoteMessage
                .notification
                ?.body
                ?: remoteMessage.data["message"]
                ?: "You have a new notification."

        // Show a local notification to the user
        showNotification(
            title = title,
            message = message,
            data = remoteMessage.data
        )
    }

    /**
     * Builds and displays a local system notification.
     */
    private fun showNotification(
        title: String,
        message: String,
        data: Map<String, String>
    ) {

        // Check for POST_NOTIFICATIONS permission on Android 13+
        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {

            if (
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) !=
                PackageManager.PERMISSION_GRANTED
            ) {

                Log.w(
                    TAG,
                    "Notification permission not granted."
                )

                return
            }
        }

        // Create an intent to open MainActivity when the notification is clicked
        val intent =
            Intent(
                this,
                MainActivity::class.java
            ).apply {

                flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TOP

                // Pass FCM data to the activity for deep linking/routing
                data.forEach {
                        entry ->

                    putExtra(
                        entry.key,
                        entry.value
                    )
                }
            }

        val pendingIntent =
            PendingIntent.getActivity(
                this,
                System.currentTimeMillis()
                    .toInt(),

                intent,

                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        // Build the notification with high priority and auto-cancel
        val notification =
            NotificationCompat
                .Builder(
                    this,
                    CHANNEL_ID
                )
                .setSmallIcon(
                    R.mipmap.ic_launcher
                )
                .setContentTitle(
                    title
                )
                .setContentText(
                    message
                )
                .setStyle(
                    NotificationCompat
                        .BigTextStyle()
                        .bigText(
                            message
                        )
                )
                .setPriority(
                    NotificationCompat
                        .PRIORITY_HIGH
                )
                .setAutoCancel(
                    true
                )
                .setContentIntent(
                    pendingIntent
                )
                .build()

        val notificationId =
            System.currentTimeMillis()
                .toInt()

        // Show the notification
        NotificationManagerCompat
            .from(this)
            .notify(
                notificationId,
                notification
            )
    }

    /**
     * Creates the notification channel required for Android 8.0+.
     */
    private fun createNotificationChannel() {

        if (
            Build.VERSION.SDK_INT <
            Build.VERSION_CODES.O
        ) {
            return
        }

        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager
                    .IMPORTANCE_HIGH
            ).apply {

                description =
                    CHANNEL_DESCRIPTION

                enableVibration(
                    true
                )
            }

        val notificationManager =
            getSystemService(
                NotificationManager::class.java
            )

        notificationManager
            .createNotificationChannel(
                channel
            )
    }
}
