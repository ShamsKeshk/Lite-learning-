package com.example.liteeducation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.example.liteeducation.data.extentions.getProgressAsInt
import com.example.liteeducation.data.extentions.putLearningMaterial
import com.example.liteeducation.databinding.FragmentLearningMaterialBinding
import com.example.liteeducation.model.LearningMaterial
import com.example.liteeducation.model.Result
import com.example.liteeducation.data.remote.services.DownloadWorkManager
import com.example.liteeducation.ui.interfaces.RetryCallback
import com.example.liteeducation.ui.adapters.LearningMaterialAdapter
import com.example.liteeducation.ui.viewmodel.LearningMaterialViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [LearningMaterialFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class LearningMaterialFragment : Fragment() , LearningMaterialAdapter.LearningMaterialClickLister {

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment LearningMaterialFragment.
         */
        @JvmStatic
        fun newInstance() = LearningMaterialFragment()
    }

    private lateinit var binding: FragmentLearningMaterialBinding

    private lateinit var viewModel: LearningMaterialViewModel

    private val adapter : LearningMaterialAdapter by lazy {
        LearningMaterialAdapter(this)
    }

    @Inject
    lateinit var downloadRequestBuilder : OneTimeWorkRequest.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLearningMaterialBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(requireActivity()).get(LearningMaterialViewModel::class.java)

        binding.swipeRefreshLearningData.setOnRefreshListener {

            viewModel.refreshData()

            binding.swipeRefreshLearningData.isRefreshing = false
        }

        binding.rvLearningMaterial.itemAnimator = null

        binding.rvLearningMaterial.adapter = adapter


        binding.retryCallback = object : RetryCallback {
            override fun retry() {
                viewModel.refreshData()
            }
        }

        binding.lifecycleOwner = this

        initLearningMaterialObserver()

        return binding.root
    }

    private fun initLearningMaterialObserver(){

        viewModel.getLearningMaterialResult().observe(viewLifecycleOwner) {
            binding.dataState = it

            if (it is Result.Success) {
                adapter.submitList(it.data)
            }
        }

        viewModel.getDownloadProgressResult().observe(viewLifecycleOwner) {
            it?.let {
                adapter.notifyItemChanged(it)
                viewModel.updateProgressDone()
            }
        }
    }

    override fun onMaterialSelected(learningMaterial: LearningMaterial) {
        downloadMediaFor(learningMaterial)
    }

    private fun downloadMediaFor(learningMaterial: LearningMaterial) {
        val work: OneTimeWorkRequest = getDownloadWorkFor(learningMaterial)

        WorkManager.getInstance(requireContext()).enqueue(work)

        initObserverForDownloadWork(work,learningMaterial)
    }

    private fun initObserverForDownloadWork(work: OneTimeWorkRequest,learningMaterial: LearningMaterial){
        WorkManager.getInstance(requireActivity().applicationContext)
            .getWorkInfoByIdLiveData(work.id)
            .observe(this) {
                it?.run {
                    val progress = getProgressAsInt()
                    if (progress != -1) {
                        viewModel.updateDownloadProgressFor(learningMaterial.id, progress)
                    }
                }
            }
    }

    private fun getDownloadWorkFor(learningMaterial: LearningMaterial): OneTimeWorkRequest{
        val data = Data.Builder()
        data.putLearningMaterial(DownloadWorkManager.KEY_LEARNING_ITEM, learningMaterial)

        return downloadRequestBuilder.setInputData(data.build()).build()
    }
}