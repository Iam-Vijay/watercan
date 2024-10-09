package com.watercanedelivery.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.watercanedelivery.app.data.*
import com.watercanedelivery.app.repository.CustomerRepository
import com.watercanedelivery.app.repository.ExpensesRepository
import com.watercanedelivery.app.utils.APIResult
import com.watercanedelivery.utils.Constant
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Karthikeyan on 31/03/2021.
 */
class ExpensesViewModel @Inject constructor(private var expensesRepository: ExpensesRepository) :
    ViewModel() {

    val TAG: String = javaClass.simpleName
    var expensesLiveData: MutableLiveData<APIResult<StaffResponseDTO>> = MutableLiveData()
    var expensesAddLiveData: MutableLiveData<APIResult<CommonResponseDTO>> = MutableLiveData()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        expensesLiveData.postValue(APIResult.error(Constant.WRON_MSG, null))
    }
    private val expenseAddexceptionHandler = CoroutineExceptionHandler { _, exception ->
        expensesLiveData.postValue(APIResult.error(Constant.WRON_MSG, null))
    }

    fun customerData(staff_id: String) {
        viewModelScope.launch(exceptionHandler) {
            expensesLiveData.postValue(APIResult.loading(null))
            val loginData = expensesRepository.getExpenses(staff_id)
            Log.i(TAG, "postLoginData: " + Gson().toJson(loginData))
            expensesLiveData.postValue(APIResult.success(loginData))
        }
    }

    fun addExpenses(staff_id: String, remarks: String, amount: String,entryDate:String) {
        viewModelScope.launch(expenseAddexceptionHandler) {
            expensesAddLiveData.postValue(APIResult.loading(null))
            val loginData = expensesRepository.addExpenses(staff_id, remarks, amount,entryDate)
            Log.i(TAG, "postLoginData: " + Gson().toJson(loginData))
            expensesAddLiveData.postValue(APIResult.success(loginData))
        }
    }

}