package com.example.liteeducation.data.local.utils

import android.app.Application
import com.example.liteeducation.data.model.LearningMaterial
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class FileHelper{

    companion object{

        fun getJsonAsTextFromAssets(application: Application,fileName: String): String{
            return application.assets.open(fileName).use { inputStream ->
                inputStream.bufferedReader().use {
                    it.readText().replace("\n","")
                }
            }
        }

        fun convertJsonToList(json: String): List<LearningMaterial>? {

            val moshi = Moshi.Builder().build()

            val listType = Types.newParameterizedType(List::class.java, LearningMaterial::class.java)

            val adapter : JsonAdapter<List<LearningMaterial>> = moshi.adapter(listType)

            return adapter.fromJson(json)
        }
    }
}