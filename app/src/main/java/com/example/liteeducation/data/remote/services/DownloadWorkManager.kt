package com.example.liteeducation.data.remote.services

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.liteeducation.R
import com.example.liteeducation.data.notification.NotificationHelper
import kotlinx.coroutines.delay
import java.lang.Exception

class DownloadWorkManager constructor(private var context: Context, parameters: WorkerParameters)
    : CoroutineWorker(context,parameters){

    private lateinit var name: String

    override suspend fun doWork(): Result {
//        val learningMaterial =  inputData.getLearningMaterial(KEY_LEARNING_ITEM)
//        val urlPath =  learningMaterial?.url
        try {
            val firstUpdate = workDataOf(Progress to 20)
            val middleUpdate = workDataOf(Progress to 50)
            val lastUpdate = workDataOf(Progress to 80)

            setProgress(firstUpdate)
        delay(delayDuration)
            setProgress(middleUpdate)
        delay(delayDuration)
            setProgress(lastUpdate)
            return Result.success()

        } catch (e: Exception) {
            return Result.failure()
        }
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