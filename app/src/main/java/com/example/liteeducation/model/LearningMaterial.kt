package com.example.liteeducation.model

data class LearningMaterial(var id: Int,
                            var type: String,
                            var url: String,
                            var name: String,
                            @Transient
                            var downloadState: Result<String>?)

fun LearningMaterial.isTypeVideo() : Boolean{
    return this.type == MaterialTypes.VIDEO.name
}

fun LearningMaterial.isTypePDF() : Boolean{
    return this.type == MaterialTypes.PDF.name
}

fun LearningMaterial.getDownloadProgress(): Int = when(downloadState){
    is Result.LoadingProgress -> (downloadState as Result.LoadingProgress<String>).progress
    else -> -1
}

fun LearningMaterial.isDownloaded(): Boolean = when(downloadState){
    is Result.Success -> true
    else -> false
}

