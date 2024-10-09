package com.watercanedelivery.app.repository


import com.oviesmarterware.ovie.network.ApiRequest
import com.watercanedelivery.app.data.HomeResponseModelDTO
import com.watercanedelivery.app.data.LoginResponseDTO
import com.watercanedelivery.app.webservice.APIServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private var apiServices: APIServices) : ApiRequest() {
    private val TAG: String = javaClass.simpleName
    suspend fun homeData(staff_id: String): HomeResponseModelDTO {
        val response = apiRequest {
            apiServices.homeScreen(staff_id).await()
        }
        return response
    }

}