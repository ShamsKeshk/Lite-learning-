package com.example.liteeducation.data.extentions

import androidx.work.WorkInfo
import com.example.liteeducation.data.remote.services.DownloadWorkManager


fun WorkInfo.getProgressAsInt(): Int {
    return this.progress.getInt(DownloadWorkManager.Progress, 0)
}
