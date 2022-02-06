package com.example.liteeducation.data.model

import com.example.liteeducation.R

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