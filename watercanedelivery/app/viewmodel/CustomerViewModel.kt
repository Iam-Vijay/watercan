package com.watercanedelivery.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.watercanedelivery.app.data.BrandResponseDTO
import com.watercanedelivery.app.data.CommonResponseDTO
import com.watercanedelivery.app.data.CustomerResponseDTO
import com.watercanedelivery.app.data.ViewCustomerDTO
import com.watercanedelivery.app.data.cutomer.CustomerListRespDTO
import com.watercanedelivery.app.data.cutomer.UpdateCustomerDTO
import com.watercanedelivery.app.repository.CustomerRepository
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.utils.Constant
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Karthikeyan on 31/03/2021.
 */
class CustomerViewModel @Inject constructor(private var customerRepository: CustomerRepository) :
    ViewModel() {

    val TAG: String = javaClass.simpleName
    var customerLiveData: MutableLiveData<APIResult<CustomerListRespDTO>> = MutableLiveData()
    var customerViewLiveData: MutableLiveData<APIResult<ViewCustomerDTO>> = MutableLiveData()
    var brandLiveData: MutableLiveData<APIResult<BrandResponseDTO>> = MutableLiveData()
    var updateOrderLiveData: MutableLiveData<APIResult<CommonResponseDTO>> = MutableLiveData()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        customerLiveData.postValue(APIResult.error(Constant.WRON_MSG, null))
    }
    private val viewExceptionHandler = CoroutineExceptionHandler { _, exception ->
        customerViewLiveData.postValue(APIResult.error(Constant.WRON_MSG, null))
    }
    private val brandExceptionHandler = CoroutineExceptionHandler { _, exception ->
        brandLiveData.postValue(APIResult.error(Constant.WRON_MSG, null))
    }
    private val updateExceptionHandler = CoroutineExceptionHandler { _, exception ->
        updateOrderLiveData.postValue(APIResult.error(Constant.WRON_MSG, null))
    }

    fun customerData() {
        viewModelScope.launch(exceptionHandler) {
            customerLiveData.postValue(APIResult.loading(null))
            val getCustomerData = customerRepository.getCustomer()
            Log.i(TAG, "postLoginData: " + Gson().toJson(getCustomerData))
            customerLiveData.postValue(APIResult.success(getCustomerData))
        }
    }

    fun customerView(id: String) {
        Log.i(TAG, "customerView id: "+id)
        viewModelScope.launch {
            (viewExceptionHandler)
            customerViewLiveData.postValue(APIResult.loading(null))
            val customerViewData = customerRepository.getViewCustomer(id)

            Log.i(TAG, "customerView: " + Gson().toJson(customerViewData))
            customerViewLiveData.postValue(APIResult.success(customerViewData))

        }
    }

    fun getBrand() {
        viewModelScope.launch {
            (brandExceptionHandler)
            customerViewLiveData.postValue(APIResult.loading(null))
            val customerViewData = customerRepository.getBrand()
            brandLiveData.postValue(APIResult.success(customerViewData))

        }
    }

    fun updateOrder(updateCustomerDTO: UpdateCustomerDTO) {
        viewModelScope.launch {
            (updateExceptionHandler)
            customerViewLiveData.postValue(APIResult.loading(null))
            val customerViewData = customerRepository.updateOrder(updateCustomerDTO)
            updateOrderLiveData.postValue(APIResult.success(customerViewData))

        }
    }

}