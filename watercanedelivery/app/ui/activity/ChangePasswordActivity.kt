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
import com.watercanedelivery.app.databinding.ActivityChangePasswordBinding
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.app.utils.CustomProgressDialog
import com.watercanedelivery.app.viewmodel.ForgotViewModel
import com.watercanedelivery.utils.Constant
import com.watercanedelivery.utils.SharedPreferenceUtil
import javax.inject.Inject

class ChangePasswordActivity : BaseActivity() {
    private lateinit var customProgressDialog: CustomProgressDialog
    var changePasswordBinding: ActivityChangePasswordBinding? = null

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil

    @Inject
    lateinit var forgotViewModel: ForgotViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changePasswordBinding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(changePasswordBinding?.root)
        resultChangePassword()
    }

    private fun resultChangePassword() {

        forgotViewModel.changePassLiveData.observe(this, Observer {

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

                    it.data.let { data -> successChangePassword(data) }


                }
            }
        })
    }

    private fun successChangePassword(data: CommonResponseDTO?) {

        if (data?.status!!) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        Toast.makeText(this, data.msg, Toast.LENGTH_SHORT).show()
    }

    fun savePassword(view: View) {


        if (validateEditValue(changePasswordBinding?.edtviewPassword!!, "Enter Password")) {
            if (validateEditValue(
                    changePasswordBinding?.edtviewConfirmPassword!!,
                    "Enter Confirm password"
                )
            ) {
                if (passwordMatches(
                        changePasswordBinding?.edtviewPassword!!,
                        changePasswordBinding?.edtviewConfirmPassword!!,
                        "Password mismatch"
                    )
                ) {
                    val staff_id = sharedPreferenceUtil.getData(Constant.USER_ID)
                    val password =
                        changePasswordBinding?.edtviewConfirmPassword?.text.toString().trim()
                    forgotViewModel.changePassword(staff_id!!, password)
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        changePasswordBinding = null
    }
}