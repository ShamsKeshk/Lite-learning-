package com.example.liteeducation.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import com.example.liteeducation.R

class NotificationHelper {


    companion object{

        internal fun createNotification(context: Context,
                                        @StringRes channelIdResource: Int,
                                        @StringRes titleResource: Int,
                                        @DrawableRes smallIconResource: Int) : NotificationCompat.Builder {
            val builder = NotificationCompat.Builder(context, context.getString(channelIdResource))
                .setSmallIcon(smallIconResource)
                .setContentTitle(context.getString(titleResource))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            createNotificationChannel(context,
                R.string.download_channel_name,
                R.string.download_channel_description,
                R.string.download_channel_id)

            return builder
        }

        private fun createNotificationChannel(context: Context,
                                              @StringRes channelNameResource: Int,
                                              @StringRes channelDescriptionResource: Int,
                                              @StringRes channelIdResource: Int) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = context.getString(channelNameResource)
                val descriptionText = context.getString(channelDescriptionResource)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(context.getString(channelIdResource), name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}