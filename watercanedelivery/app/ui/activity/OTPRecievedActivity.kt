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
import com.watercanedelivery.app.databinding.ActivityOTPRecievedBinding
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.app.utils.CustomProgressDialog
import com.watercanedelivery.app.viewmodel.ForgotViewModel
import com.watercanedelivery.utils.Constant
import com.watercanedelivery.utils.SharedPreferenceUtil
import javax.inject.Inject

class OTPRecievedActivity : BaseActivity() {
    private var mobileNo: String? = null
    private lateinit var customProgressDialog: CustomProgressDialog

    @Inject
    lateinit var forgotViewModel: ForgotViewModel

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil
    var otpRecievedBinding: ActivityOTPRecievedBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        otpRecievedBinding = ActivityOTPRecievedBinding.inflate(layoutInflater)
        setContentView(otpRecievedBinding?.root)
        mobileNo = sharedPreferenceUtil.getData(Constant.MOBILE_NO)
        resultForgotPassword()
        resultOTP()
    }

    private fun resultOTP() {

        forgotViewModel.checkOTPLiveData.observe(this, Observer {

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

                    it.data.let { data -> successOTP(data) }


                }
            }
        })

    }

    private fun successOTP(commonResponseDTO: CommonResponseDTO?) {

        if (commonResponseDTO?.status!!) {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
            finish()
        }
        Toast.makeText(this, commonResponseDTO.msg, Toast.LENGTH_SHORT).show()
    }

    fun verifyOtp(view: View) {

        if (validateEditValue(otpRecievedBinding?.edtviewOtp!!, "Enter OTP")) {
            val code = otpRecievedBinding?.edtviewOtp?.text.toString().trim()
            forgotViewModel.checkOTP(mobileNo!!, code)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        otpRecievedBinding = null
    }

    fun resendPassword(view: View) {

        forgotViewModel.forgotData(mobileNo!!)

    }

    private fun resultForgotPassword() {
        forgotViewModel.forgotLiveData.observe(this, Observer {

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

    private fun successForgot(commonResponseDTO: CommonResponseDTO?) {


        Toast.makeText(this, commonResponseDTO?.msg, Toast.LENGTH_SHORT).show()
    }
}