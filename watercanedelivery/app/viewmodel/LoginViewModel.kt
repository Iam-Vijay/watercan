package com.watercanedelivery.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson

import com.watercanedelivery.app.data.LoginResponseDTO
import com.watercanedelivery.app.repository.LoginRepository
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.utils.Constant
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Karthikeyan on 27/03/2021.
 */
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {
    val TAG: String = javaClass.simpleName
    var loginLiveData: MutableLiveData<APIResult<LoginResponseDTO>> = MutableLiveData()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        loginLiveData.postValue(APIResult.error(Constant.WRON_MSG, null))
    }

    fun postLoginData(userName: String, password: String) {
        viewModelScope.launch(exceptionHandler) {
            loginLiveData.postValue(APIResult.loading(null))
            val loginData = loginRepository.loginUser(userName,password)
            Log.i(TAG, "postLoginData: " + Gson().toJson(loginData))
            loginLiveData.postValue(APIResult.success(loginData))
        }
    }
}