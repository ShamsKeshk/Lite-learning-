package com.example.liteeducation.data.local

import android.app.Application
import androidx.annotation.WorkerThread
import com.example.liteeducation.data.local.utils.CACHED_LEARNING_MATERIAL_FILE_NAME
import com.example.liteeducation.data.local.utils.FileHelper
import com.example.liteeducation.model.LearningMaterial
import javax.inject.Inject

class LocalLearningDataSource @Inject constructor(private val application: Application) {

    @WorkerThread
    suspend fun getCachedLearningData() : List<LearningMaterial>?{
        val jsonResponse = FileHelper.getJsonAsTextFromAssets(application,
            CACHED_LEARNING_MATERIAL_FILE_NAME)

        if (jsonResponse.isNullOrEmpty())
            return null

        return FileHelper.convertJsonToList(jsonResponse)
    }


}