package com.example.liteeducation.data.local

import android.app.Application
import android.content.res.TypedArray
import androidx.annotation.WorkerThread
import com.example.liteeducation.data.local.utils.CACHED_LEARNING_MATERIAL_FILE_NAME
import com.example.liteeducation.data.local.utils.FileHelper
import com.example.liteeducation.data.model.LearningMaterial
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
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