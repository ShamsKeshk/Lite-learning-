package com.example.liteeducation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.liteeducation.databinding.ListItemLearningMaterialBinding
import com.example.liteeducation.data.model.LearningMaterial
import com.example.liteeducation.data.model.LearningMaterialFactory

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
            binding.tvLearningItemName.text = learningMaterial.name
            binding.tvLearningItemType.text = learningMaterial.type

            val imageResource = LearningMaterialFactory.getImageResourceFor(learningMaterial)
            binding.ivLearningItemPreview.setImageResource(imageResource)
        }
    }


    companion object {
        private val DIFF_UTIL =  object: DiffUtil.ItemCallback<LearningMaterial>() {
            override fun areItemsTheSame(oldItem: LearningMaterial, newItem: LearningMaterial): Boolean {
               return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: LearningMaterial, newItem: LearningMaterial): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    interface LearningMaterialClickLister {
        fun onMaterialSelected(learningMaterial: LearningMaterial)
    }
}