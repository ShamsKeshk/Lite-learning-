package com.example.liteeducation.binding_adapters

import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.example.liteeducation.model.DownloadStateFactory
import com.example.liteeducation.model.Result

@BindingAdapter("app:updateDownloadProgress")
fun updateDownloadProgress(progressBar: ProgressBar, result : Result<Any>?){
    progressBar.setBackgroundResource(DownloadStateFactory.getDownloadStateProgressColor(result))
}