package com.watercanedelivery.app.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer

import com.watercanedelivery.app.BaseActivity
import com.watercanedelivery.app.R
import com.watercanedelivery.app.data.LoginResponseDTO
import com.watercanedelivery.app.databinding.ActivityLoginBinding
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.app.utils.CustomProgressDialog
import com.watercanedelivery.app.utils.Utils
import com.watercanedelivery.app.viewmodel.LoginViewModel
import com.watercanedelivery.utils.Constant
import com.watercanedelivery.utils.SharedPreferenceUtil
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LoginActivity : BaseActivity() {
    private  var customProgressDialog: CustomProgressDialog?=null

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil

    @Inject
    lateinit var loginViewModel: LoginViewModel
    var activityLoginBinding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding?.root)
        loginResult()
    }

    override fun onDestroy() {
        super.onDestroy()
        activityLoginBinding = null
    }

    fun loginClickLogin(view: View) {

        val userName = activityLoginBinding?.edtviewEmail!!.text.toString().trim()
        val password = activityLoginBinding?.edtviewPassword!!.text.toString().trim()
        if (validateEditValue(
                activityLoginBinding?.edtviewEmail!!,
                getString(R.string.mobile_number)
            )
        ) {
            if (validateEditValue(
                    activityLoginBinding?.edtviewPassword!!,
                    getString(R.string.password_alert)
                )
            ) {
                if (isNetworkConnected())
                    loginViewModel.postLoginData(userName, password)

                /* if (validateEditValue(
                         activityLoginBinding.edtviewPassword,
                         getString(R.string.password_lengthalert)
                     )
                 ) {

                     loginViewModel.postLoginData(userName, password)
 //                    Log.i(TAG, "loginClickLogin: " + Gson().toJson(loginParamModel))
 //                    activityLoginBinding.edtviewPassword.setText("")
                 }*/
            }
        }
    }

    fun loginResult() {
        loginViewModel.loginLiveData.observe(this, Observer {

            when (it.status) {

                APIResult.Status.LOADING -> {
                    customProgressDialog = CustomProgressDialog.show(this, true, false)
//                    showProgress(this)
                }
                APIResult.Status.ERROR -> {
                    customProgressDialog?.dismiss()
//                    hideProgress()

                }
                APIResult.Status.SUCCESS -> {
//                    hideProgress()
                    customProgressDialog?.dismiss()

                    it.data.let { data -> successLogin(data) }


                }
            }
        })
    }

    private fun successLogin(loginResponseDTO: LoginResponseDTO?) {

        if (loginResponseDTO?.status!!) {
            Toast.makeText(this, "Successfully logged in", Toast.LENGTH_SHORT).show()

            sharedPreferenceUtil?.saveData(Constant.STAFF_ID, loginResponseDTO.staff?.staffId!!)
            sharedPreferenceUtil?.saveData(Constant.USER_ID, loginResponseDTO.staff?.id!!)
            Utils.loginPopup(
                this,
                loginResponseDTO!!.staff?.staffId!!
            )
            /*   startActivity(Intent(this, MainActivity::class.java))
               finish()*/
        } else {
            Toast.makeText(this, loginResponseDTO.msg, Toast.LENGTH_SHORT).show()

        }
    }

    fun forgotPasswordClick(view: View) {
        startActivity(Intent(this, ForgotPasswordActivity::class.java))


    }
}