package com.example.liteeducation.model

import com.example.liteeducation.R

class DownloadStateFactory {


    companion object{

        fun getDownloadStateImage(result: Result<Any>?): Int{
            return when(result){
                is Result.Success -> R.drawable.ic_play_circle_outline
                else -> R.drawable.ic_download_30
            }
        }

        fun getDownloadStateProgressColor(result: Result<Any>?): Int{
            return when(result){
                is Result.Success -> R.drawable.progress_download_completed
                else -> R.drawable.circle_shape
            }
        }
    }
}