package com.example.liteeducation.data.extentions

import androidx.work.Data
import androidx.work.WorkInfo
import com.example.liteeducation.model.LearningMaterial
import com.example.liteeducation.data.remote.services.DownloadWorkManager
import com.squareup.moshi.Moshi

fun WorkInfo.getProgressAsInt(): Int {
    return this.progress.getInt(DownloadWorkManager.Progress, -1)
}

// Serialize a single object.
fun Data.Builder.putLearningMaterial(key: String, learningMaterial: LearningMaterial) {
    val moshi = Moshi.Builder().build().adapter(LearningMaterial::class.java).lenient()
    this.putString(key,moshi.toJson(learningMaterial))
}

// Deserialize to single object.
fun Data.getLearningMaterial(jsonKey: String): LearningMaterial? {
    val moshi = Moshi.Builder().build()
        .adapter(LearningMaterial::class.java).lenient()

    val value = this.getString(jsonKey)

    if(value != null) {
        return moshi.fromJson(value)
    }

    return null
}