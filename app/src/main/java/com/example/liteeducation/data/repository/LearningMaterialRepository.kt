package com.example.liteeducation.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.liteeducation.data.local.LocalLearningDataSource
import com.example.liteeducation.data.model.LearningMaterial
import com.example.liteeducation.data.model.Result
import com.example.liteeducation.data.remote.services.RemoteLearningDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LearningMaterialRepository
@Inject
constructor(private val remoteLearningDataSource: RemoteLearningDataSource,
            private val localLearningDataSource: LocalLearningDataSource) {

    private val learningMaterialsResult = MutableLiveData<Result<List<LearningMaterial>>>()
    private val learningDownloadsResult = MutableLiveData<Int?>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            syncLearningMaterialsFromRemote()
        }
    }

    suspend fun forceSyncRemoteLearningMaterial() {
        CoroutineScope(Dispatchers.IO).launch {
            syncLearningMaterialsFromRemote()
        }
    }

    @WorkerThread
    private suspend fun syncLearningMaterialsFromRemote(){
        learningMaterialsResult.postValue(Result.Loading())
        var learningMaterials : List<LearningMaterial> ? = null
        try {
            learningMaterials =  remoteLearningDataSource.getLearningMaterials()
            learningMaterialsResult.postValue(Result.Success(learningMaterials))
        }catch (throwable : Throwable){
            learningMaterials = getCachedData()

            if (learningMaterials != null){
                learningMaterialsResult.postValue(Result.Success(learningMaterials))
            }else{
                learningMaterialsResult.postValue(Result.Error(throwable))
            }
        }
    }

    @WorkerThread
    private suspend fun getCachedData() : List<LearningMaterial>? {
        return localLearningDataSource.getCachedLearningData()
    }


    fun getLearningMaterialResult() : LiveData<Result<List<LearningMaterial>>> {
        return learningMaterialsResult
    }

    fun getDownloadProgressResult() : LiveData<Int?>{
        return learningDownloadsResult
    }

    fun updateProgressDone(){
        learningDownloadsResult.value = null
    }

    @WorkerThread
    suspend fun updateProgressForItem(itemId: Int, progressValue: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = getLearningMaterialResult().value

            if (result !is Result.Success)
                return@launch

            if (result.data.isEmpty())
                return@launch

            result.data.forEachIndexed { index, learningMaterial ->
                if (learningMaterial.id == itemId){
                    if(progressValue >= 100){
                        learningMaterial.downloadState = Result.Success("Downloaded media path")
                    }else {
                        learningMaterial.downloadState = Result.LoadingProgress(progressValue)
                    }
                    learningDownloadsResult.postValue(index)
                    return@launch
                }
            }
        }
    }

}