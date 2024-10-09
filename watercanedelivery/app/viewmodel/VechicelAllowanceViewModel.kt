package com.watercanedelivery.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.watercanedelivery.app.data.CommonResponseDTO
import com.watercanedelivery.app.data.LoginResponseDTO
import com.watercanedelivery.app.data.VechicelAllowance
import com.watercanedelivery.app.repository.LoginRepository
import com.watercanedelivery.app.repository.VechAllowRepository
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.utils.Constant
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Karthikeyan on 02/04/2021.
 */
class VechicelAllowanceViewModel @Inject constructor(private val vechAllowRepository: VechAllowRepository) :
    ViewModel() {
    val TAG: String = javaClass.simpleName
    var vechicleLiveData: MutableLiveData<APIResult<VechicelAllowance>> = MutableLiveData()
    var vechicleAddLiveData: MutableLiveData<APIResult<CommonResponseDTO>> = MutableLiveData()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        vechicleLiveData.postValue(APIResult.error(Constant.WRON_MSG, null))
    }

    private val exceptionHandlerAddVechAllow = CoroutineExceptionHandler { _, exception ->
        vechicleLiveData.postValue(APIResult.error(Constant.WRON_MSG, null))
    }

    fun vechicleData(staff_id: String) {
        viewModelScope.launch(exceptionHandler) {
            vechicleLiveData.postValue(APIResult.loading(null))
            val loginData = vechAllowRepository.vechicleAllowanceData(staff_id)
            Log.i(TAG, "vechicleData: " + Gson().toJson(loginData))
            vechicleLiveData.postValue(APIResult.success(loginData))
        }
    }

    fun addVechAllow(staff_id: String,startKm:String,endKm:String,entryDate:String) {
        viewModelScope.launch(exceptionHandlerAddVechAllow) {
            vechicleAddLiveData.postValue(APIResult.loading(null))
            val loginData = vechAllowRepository.vechicleAddAllow(staff_id,startKm,endKm,entryDate)
            Log.i(TAG, "vechicleData: " + Gson().toJson(loginData))
            vechicleAddLiveData.postValue(APIResult.success(loginData))
        }
    }
}