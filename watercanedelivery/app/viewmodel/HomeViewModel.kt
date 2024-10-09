package com.watercanedelivery.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.watercanedelivery.app.data.HomeResponseModelDTO

import com.watercanedelivery.app.data.LoginResponseDTO
import com.watercanedelivery.app.repository.HomeRepository
import com.watercanedelivery.app.repository.LoginRepository
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.utils.Constant
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Karthikeyan on 27/03/2021.
 */
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {
    val TAG: String = javaClass.simpleName
    var homeLiveData: MutableLiveData<APIResult<HomeResponseModelDTO>> = MutableLiveData()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        homeLiveData.postValue(APIResult.error(Constant.WRON_MSG, null))
    }

    fun homeDataValue(staff_id: String) {
        viewModelScope.launch(exceptionHandler) {
            homeLiveData.postValue(APIResult.loading(null))
            val loginData = homeRepository.homeData(staff_id)
            Log.i(TAG, "postLoginData: " + Gson().toJson(loginData))
            homeLiveData.postValue(APIResult.success(loginData))
        }
    }
}