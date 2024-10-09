package com.watercanedelivery.app.repository

import com.google.android.material.tabs.TabLayout
import com.oviesmarterware.ovie.network.ApiRequest
import com.watercanedelivery.app.data.*
import com.watercanedelivery.app.data.cutomer.CustomerListRespDTO
import com.watercanedelivery.app.data.cutomer.UpdateCustomerDTO
import com.watercanedelivery.app.webservice.APIServices
import javax.inject.Inject

/**
 * Created by Karthikeyan on 31/03/2021.
 */
class CustomerRepository @Inject constructor(private var apiServices: APIServices) : ApiRequest() {
    private val TAG: String = javaClass.simpleName
    suspend fun getCustomer(): CustomerListRespDTO {
        val response = apiRequest { apiServices.customerRequest().await() }
        return response
    }

    suspend fun getViewCustomer(id: String): ViewCustomerDTO {
        val viewResponseDTO = apiRequest { apiServices.viewCustomer(id).await() }
        return viewResponseDTO
    }

    suspend fun getBrand(): BrandResponseDTO {
        val brandData = apiRequest { apiServices.getBrandRequest().await() }
        return brandData
    }

    suspend fun updateOrder(updateCustomerDTO: UpdateCustomerDTO): CommonResponseDTO {
        val brandData = apiRequest { apiServices.updateOrder(updateCustomerDTO).await() }
        return brandData
    }

}