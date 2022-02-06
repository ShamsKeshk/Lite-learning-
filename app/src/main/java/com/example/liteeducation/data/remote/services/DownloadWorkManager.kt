package com.example.liteeducation.data.remote.services

import android.content.Context
import androidx.work.*
import com.example.liteeducation.R
import com.example.liteeducation.data.extentions.getLearningMaterial
import com.example.liteeducation.data.notification.NotificationHelper
import kotlinx.coroutines.*

class DownloadWorkManager constructor(private var context: Context, parameters: WorkerParameters)
    : CoroutineWorker(context,parameters){

    private lateinit var name: String

    override suspend fun doWork(): Result {
        val learningMaterial =  inputData.getLearningMaterial(KEY_LEARNING_ITEM)
        val urlPath =  learningMaterial?.url

        (1..105).forEach {
            delay(300)
            setProgress(workDataOf(Progress to it))
        }

        return Result.success()
    }


    companion object {
        const val KEY_LEARNING_ITEM = "KEY_LEARNING_ITEM"

        const val Progress = "Progress"
        private const val delayDuration = 1L
        private const val NOTIFICATION_ID = 12

        const val FileSize = "Size"
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notificationBuilder = NotificationHelper.createNotification(
            context,
            R.string.download_channel_id,
            R.string.download_notification_title,
            R.drawable.ic_ondemand_video_60)

        notificationBuilder.setContentText(context.getString(R.string.download_notification_description,name))
        return ForegroundInfo(NOTIFICATION_ID,notificationBuilder.build() )
    }

}