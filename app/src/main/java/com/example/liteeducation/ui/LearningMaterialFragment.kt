package com.example.liteeducation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.liteeducation.databinding.FragmentLearningMaterialBinding
import com.example.liteeducation.data.model.LearningMaterial
import com.example.liteeducation.data.model.Result
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [LearningMaterialFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class LearningMaterialFragment : Fragment() , LearningMaterialAdapter.LearningMaterialClickLister {

    private lateinit var binding: FragmentLearningMaterialBinding

    private lateinit var viewModel: LearningMaterialViewModel

    private val adapter : LearningMaterialAdapter by lazy {
        LearningMaterialAdapter(this)
    }

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

        viewModel.getLearningMaterialResult().observe(viewLifecycleOwner , Observer {
           binding.dataState = it

            when(it){
                is Result.Success -> {
                    adapter.submitList(it.data)
                }
            }
        })

        viewModel.getDownloadProgressResult().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.notifyItemChanged(it)
                viewModel.updateProgressDone()
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment LearningMaterialFragment.
         */
        @JvmStatic
        fun newInstance() = LearningMaterialFragment()
    }

    private var toast : Toast? = null

    override fun onMaterialSelected(learningMaterial: LearningMaterial) {
        Toast.makeText(context,"Clicked :${learningMaterial.name}",Toast.LENGTH_LONG).show()
    }

    private fun displayMessage(message: String){
        toast?.let {
            it.cancel()
        }

        toast = Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).apply {
            show()
        }
    }
}