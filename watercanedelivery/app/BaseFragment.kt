package com.watercanedelivery.app

import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.watercanedelivery.app.utils.NetworkUtils
import dagger.android.support.DaggerFragment

/**
 * Created by Karthikeyan on 02/04/2021.
 */
abstract class BaseFragment : DaggerFragment() {
    /*validate the value */
    fun validateEditValue(editText: TextView, tag: String): Boolean {
        if (editText.text.toString().trim().isEmpty()) {
            editText.error = tag
            requestFocus(editText)
            return false
        } else
            editText.error = null

        return true
    }
    /*To focus on view*/
    private fun requestFocus(view: View) {
        if (view.requestFocus())
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

    }
    open fun isNetworkConnected(): Boolean {
        if (!NetworkUtils.isNetworkConnected(requireActivity())) {
            Toast.makeText(requireActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show()

        }
        return NetworkUtils.isNetworkConnected(requireActivity())
    }

    /*validate the password length*/
    fun validatePasswordLength(editText: EditText, tag: String): Boolean {
        if (editText.text.toString().trim().length < 8) {
            editText.error = tag
            requestFocus(editText)
            return false
        } else
            editText.error = null
        return true
    }

}