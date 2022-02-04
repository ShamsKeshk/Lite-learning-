package com.example.liteeducation.data.remote.services

import com.example.liteeducation.data.model.LearningMaterial
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST

interface LearningMaterialService {


    @POST("/movies")
    suspend fun getLearningMaterial() : List<LearningMaterial>


    companion object {
        fun getJsonHeader() : String{
            return "Accept: application/json"
        }
    }
}