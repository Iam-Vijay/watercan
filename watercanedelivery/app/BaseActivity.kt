package com.watercanedelivery.app

import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.watercanedelivery.app.utils.NetworkUtils
import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by Karthikeyan on 27/03/2021.
 */
abstract class BaseActivity : DaggerAppCompatActivity() {
    /*validate the value */
    fun validateEditValue(editText: EditText, tag: String): Boolean {
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
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

    }

    open fun isNetworkConnected(): Boolean {
        if (!NetworkUtils.isNetworkConnected(applicationContext)) {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()

        }
        return NetworkUtils.isNetworkConnected(applicationContext)
    }

    fun passwordMatches(password: EditText, confirmPassword: EditText, tag: String): Boolean {
        if (password.text.toString().trim().equals(confirmPassword.text.toString().trim())) {
            confirmPassword.error = null
            return true

        } else {
            confirmPassword.error = tag
            requestFocus(confirmPassword)
            return false
        }
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