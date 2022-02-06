package com.example.liteeducation.binding_adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.liteeducation.model.DownloadStateFactory
import com.example.liteeducation.model.Result


@BindingAdapter("app:updateDownloadImage")
fun updateDownloadImage(imageView: ImageView, result : Result<Any>?){
    imageView.setImageResource(DownloadStateFactory.getDownloadStateImage(result))
}