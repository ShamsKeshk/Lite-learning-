package com.example.liteeducation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liteeducation.model.LearningMaterial
import com.example.liteeducation.model.Result
import com.example.liteeducation.data.repository.LearningMaterialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningMaterialViewModel
@Inject constructor(private val learningMaterialRepo: LearningMaterialRepository) : ViewModel() {


    fun refreshData() {
        viewModelScope.launch {
            learningMaterialRepo.forceSyncRemoteLearningMaterial()
        }
    }

    fun getLearningMaterialResult() : LiveData<Result<List<LearningMaterial>>> {
        return learningMaterialRepo.getLearningMaterialResult()
    }

    fun updateDownloadProgressFor(itemId: Int, progressValue: Int) {
        viewModelScope.launch {
            learningMaterialRepo.updateProgressForItem(itemId, progressValue)
        }
    }

    fun getDownloadProgressResult() : LiveData<Int?>{
        return learningMaterialRepo.getDownloadProgressResult()
    }

    fun updateProgressDone(){
        learningMaterialRepo.updateProgressDone()
    }
}