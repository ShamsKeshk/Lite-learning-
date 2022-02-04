package com.example.liteeducation.data.remote.di

import com.example.liteeducation.data.remote.services.LearningMaterialService
import com.example.liteeducation.data.utils.BASE_SERVER_URL_MATERIAL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkComponent {

    @Provides
    @Singleton
    fun getOkHttpClient() :OkHttpClient{
        return OkHttpClient().newBuilder().build()
    }

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_SERVER_URL_MATERIAL)
            .build()
    }

    @Provides
    @Singleton
    fun getLearningMaterialService(retrofit: Retrofit) : LearningMaterialService {
        return retrofit.create(LearningMaterialService::class.java)
    }

}