package com.watercanedelivery.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.watercanedelivery.app.data.CommonResponseDTO

import com.watercanedelivery.app.data.LoginResponseDTO
import com.watercanedelivery.app.repository.ForgotRepository
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.utils.Constant
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Karthikeyan on 27/03/2021.
 */
class ForgotViewModel @Inject constructor(private val forgotRepository: ForgotRepository) :
    ViewModel() {
    val TAG: String = javaClass.simpleName
    var forgotLiveData: MutableLiveData<APIResult<CommonResponseDTO>> = MutableLiveData()
    var checkOTPLiveData: MutableLiveData<APIResult<CommonResponseDTO>> = MutableLiveData()
    var changePassLiveData: MutableLiveData<APIResult<CommonResponseDTO>> = MutableLiveData()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        forgotLiveData.postValue(APIResult.error(Constant.WRON_MSG, null))
    }

    fun checkOTP(mobileNo: String, code: String) {
        viewModelScope.launch(exceptionHandler) {
            checkOTPLiveData.postValue(APIResult.loading(null))
            val loginData = forgotRepository.otp(mobileNo, code)
            Log.i(TAG, "checkotp: " + Gson().toJson(loginData))
            checkOTPLiveData.postValue(APIResult.success(loginData))
        }
    }

    fun forgotData(mobileNo: String) {
        viewModelScope.launch(exceptionHandler) {
            forgotLiveData.postValue(APIResult.loading(null))
            val loginData = forgotRepository.forgotPassword(mobileNo)
            Log.i(TAG, "forgotPassword: " + Gson().toJson(loginData))
            forgotLiveData.postValue(APIResult.success(loginData))
        }
    }

    fun changePassword(staff_id: String,password:String) {
        viewModelScope.launch(exceptionHandler) {
            changePassLiveData.postValue(APIResult.loading(null))
            val loginData = forgotRepository.changePassword(staff_id,password)
            Log.i(TAG, "change password: " + Gson().toJson(loginData))
            changePassLiveData.postValue(APIResult.success(loginData))
        }
    }
}