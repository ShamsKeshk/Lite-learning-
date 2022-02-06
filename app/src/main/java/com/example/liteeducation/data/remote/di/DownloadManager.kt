package com.example.liteeducation.data.remote.di

import androidx.work.*
import com.example.liteeducation.data.remote.services.DownloadWorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ActivityComponent::class)
class DownloadManager {

    @Provides
    @ActivityScoped
    fun getDownloadConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    @Provides
    @ActivityScoped
    fun getDownloadWorker(constraints: Constraints) :OneTimeWorkRequest.Builder {
        return OneTimeWorkRequestBuilder<DownloadWorkManager>()
            // Additional configuration
            .setConstraints(constraints)
            .setInitialDelay(10, TimeUnit.MILLISECONDS)
    }


}