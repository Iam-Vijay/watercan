package com.watercanedelivery.app.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.watercanedelivery.app.BaseActivity
import com.watercanedelivery.app.R
import com.watercanedelivery.app.data.CommonResponseDTO
import com.watercanedelivery.app.databinding.ActivityForgotPasswordBinding
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.app.utils.CustomProgressDialog
import com.watercanedelivery.app.viewmodel.ForgotViewModel
import com.watercanedelivery.utils.Constant
import com.watercanedelivery.utils.SharedPreferenceUtil
import javax.inject.Inject

class ForgotPasswordActivity : BaseActivity() {
    private lateinit var customProgressDialog: CustomProgressDialog

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil

    @Inject
    lateinit var forgotViewMode: ForgotViewModel
    var resetPasswordBinding: ActivityForgotPasswordBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resetPasswordBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(resetPasswordBinding?.root)
        resultForgotPassword()
    }

    private fun resultForgotPassword() {
        forgotViewMode.forgotLiveData.observe(this, Observer {

            when (it.status) {

                APIResult.Status.LOADING -> {
                    customProgressDialog = CustomProgressDialog.show(this, true, false)
//                    showProgress(this)
                }
                APIResult.Status.ERROR -> {
                    customProgressDialog.dismiss()
//                    hideProgress()

                }
                APIResult.Status.SUCCESS -> {
//                    hideProgress()
                    customProgressDialog.dismiss()

                    it.data.let { data -> successForgot(data) }


                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        resetPasswordBinding = null
    }

    private fun successForgot(commonResponseDTO: CommonResponseDTO?) {

        if (commonResponseDTO?.status!!) {
            sharedPreferenceUtil.saveData(
                Constant.MOBILE_NO,
                resetPasswordBinding?.edtviewMobileNo!!.toString().trim()
            )
            startActivity(Intent(this, OTPRecievedActivity::class.java))
            finish()
        }
        Toast.makeText(this, commonResponseDTO.msg, Toast.LENGTH_SHORT).show()
    }

    fun resetPassword(view: View) {
        if (validateEditValue(
                resetPasswordBinding?.edtviewMobileNo!!,
                "Please enter mobile number"
            )
        ) {
            val mobileNo = resetPasswordBinding?.edtviewMobileNo?.text.toString()
            forgotViewMode.forgotData(mobileNo)
        }


    }

    fun forgotBack(view: View) {

        onBackPressed()
    }
}