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
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

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

        createNotificationChannel()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(
            TAG,
            "FCM token received: $token"
        )

        /*
         * We will send this token to the
         * ASP.NET API in the next stage.
         *
         * For now, logging it allows us to
         * verify that Firebase is working.
         */
    }

    override fun onMessageReceived(
        remoteMessage: RemoteMessage
    ) {
        super.onMessageReceived(
            remoteMessage
        )

        Log.d(
            TAG,
            "FCM message received"
        )

        val title =
            remoteMessage.notification?.title
                ?: remoteMessage.data["title"]
                ?: "OnSite"

        val message =
            remoteMessage.notification?.body
                ?: remoteMessage.data["message"]
                ?: "You have a new notification."

        showNotification(
            title = title,
            message = message,
            data = remoteMessage.data
        )
    }

    private fun showNotification(
        title: String,
        message: String,
        data: Map<String, String>
    ) {

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

        val intent =
            Intent(
                this,
                MainActivity::class.java
            ).apply {

                flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TOP

                /*
                 * These values will later be used
                 * for deep-linking to specific
                 * site reports and assignments.
                 */
                data.forEach { entry ->

                    putExtra(
                        entry.key,
                        entry.value
                    )
                }
            }

        val pendingIntent =
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        val notification =
            NotificationCompat.Builder(
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
                    NotificationCompat.BigTextStyle()
                        .bigText(message)
                )
                .setPriority(
                    NotificationCompat.PRIORITY_HIGH
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

        NotificationManagerCompat
            .from(this)
            .notify(
                notificationId,
                notification
            )
    }

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
                NotificationManager.IMPORTANCE_HIGH
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