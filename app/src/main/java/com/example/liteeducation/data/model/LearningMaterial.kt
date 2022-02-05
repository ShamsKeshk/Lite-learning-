package com.example.liteeducation.data.model

import com.example.liteeducation.R

data class LearningMaterial(var id: Int,
                            var type: String,
                            var url: String,
                            var name: String,
                            @Transient
                            var downloadState: Result<String>?) {

}

fun LearningMaterial.isTypeVideo() : Boolean{
    return this.type == MaterialTypes.VIDEO.name
}

fun LearningMaterial.isTypePDF() : Boolean{
    return this.type == MaterialTypes.PDF.name
}


enum class MaterialTypes {
    VIDEO , PDF
}

class LearningMaterialFactory{

    companion object {

        fun getImageResourceFor(learningMaterial: LearningMaterial) : Int{
            return if (learningMaterial.isTypeVideo()){
                R.drawable.ic_ondemand_video_60
            }else if (learningMaterial.isTypePDF()){
                R.drawable.ic_pdf_60
            }else{
                R.drawable.ic_un_known_type_60
            }
        }
    }
}