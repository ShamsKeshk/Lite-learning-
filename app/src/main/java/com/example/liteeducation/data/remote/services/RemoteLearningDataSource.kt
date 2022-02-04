package com.example.liteeducation.data.remote.services

import com.example.liteeducation.data.model.LearningMaterial
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


class RemoteLearningDataSource @Inject constructor(private val learningMaterialService: LearningMaterialService) {

    suspend fun getLearningMaterials() : List<LearningMaterial> {
        return learningMaterialService.getLearningMaterial()
    }
}