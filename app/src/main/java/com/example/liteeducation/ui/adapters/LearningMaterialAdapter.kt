package com.example.liteeducation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.liteeducation.databinding.ListItemLearningMaterialBinding
import com.example.liteeducation.model.LearningMaterial
import com.example.liteeducation.model.LearningMaterialFactory
import com.example.liteeducation.model.Result
import com.example.liteeducation.model.getDownloadProgress

class LearningMaterialAdapter(val clickListener: LearningMaterialClickLister) : ListAdapter<LearningMaterial, LearningMaterialAdapter.ViewHolder>(
    DIFF_UTIL
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemLearningMaterialBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(private val binding: ListItemLearningMaterialBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                clickListener.onMaterialSelected(getItem(adapterPosition))
            }
        }

        fun bindItem(learningMaterial: LearningMaterial){
            binding.downloadResult = learningMaterial.downloadState

            binding.tvLearningItemName.text = learningMaterial.name
            binding.tvLearningItemType.text = learningMaterial.type

            val imageResource = LearningMaterialFactory.getImageResourceFor(learningMaterial)
            binding.ivLearningItemPreview.setImageResource(imageResource)

            when (learningMaterial.downloadState) {
                is Result.LoadingProgress -> {
                    binding.downloadProgress = (learningMaterial.downloadState as Result.LoadingProgress<String>).progress
                }else -> {
                    binding.downloadProgress = 0
                }
            }
        }
    }


    companion object {
        private val DIFF_UTIL =  object: DiffUtil.ItemCallback<LearningMaterial>() {
            override fun areItemsTheSame(oldItem: LearningMaterial, newItem: LearningMaterial): Boolean {
               return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: LearningMaterial, newItem: LearningMaterial): Boolean {
                return oldItem.id == newItem.id && oldItem.getDownloadProgress() == newItem.getDownloadProgress()
            }
        }
    }

    interface LearningMaterialClickLister {
        fun onMaterialSelected(learningMaterial: LearningMaterial)
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }
}