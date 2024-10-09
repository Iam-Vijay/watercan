package com.watercanedelivery.app.repository

import com.google.android.material.tabs.TabLayout
import com.oviesmarterware.ovie.network.ApiRequest
import com.watercanedelivery.app.data.*

import com.watercanedelivery.app.webservice.APIServices
import javax.inject.Inject

/**
 * Created by Karthikeyan on 31/03/2021.
 */
class ExpensesRepository @Inject constructor(private var apiServices: APIServices) : ApiRequest() {
    private val TAG: String = javaClass.simpleName
    suspend fun getExpenses(staff_id: String): StaffResponseDTO {
        val response = apiRequest { apiServices.getExpensesList(staff_id).await() }
        return response
    }

    suspend fun addExpenses(staff_id: String, remarks: String, amount: String,entryDate:String): CommonResponseDTO {
        val addExpenseResponse =
            apiRequest { apiServices.addExpenses(staff_id, remarks, amount,entryDate).await() }
        return addExpenseResponse
    }
}