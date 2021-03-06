package com.example.liteeducation

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(){

    private var toast: Toast? = null


    protected fun displayMessage(message: String){
        toast?.cancel()

        toast = Toast.makeText(requireContext(),message,Toast.LENGTH_LONG)

        toast?.run {
            show()
        }
    }
}