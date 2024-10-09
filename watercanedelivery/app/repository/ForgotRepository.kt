package com.watercanedelivery.app.repository


import com.oviesmarterware.ovie.network.ApiRequest
import com.watercanedelivery.app.data.CommonResponseDTO
import com.watercanedelivery.app.data.LoginResponseDTO
import com.watercanedelivery.app.webservice.APIServices
import javax.inject.Inject

class ForgotRepository @Inject constructor(private var apiServices: APIServices) : ApiRequest() {
    private val TAG: String = javaClass.simpleName
    suspend fun forgotPassword(mobile_no: String): CommonResponseDTO {
        val response = apiRequest { apiServices.forgotPassword(mobile_no).await() }
        return response
    }

    suspend fun otp(mobile_no: String, code: String): CommonResponseDTO {
        val response = apiRequest { apiServices.resetCode(mobile_no, code).await() }
        return response
    }

    suspend fun changePassword(staff_id: String, password: String): CommonResponseDTO {
        val response = apiRequest { apiServices.changePassword(staff_id, password).await() }
        return response
    }

}