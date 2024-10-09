package com.watercanedelivery.app.repository


import com.oviesmarterware.ovie.network.ApiRequest
import com.watercanedelivery.app.data.LoginResponseDTO
import com.watercanedelivery.app.webservice.APIServices
import javax.inject.Inject

class LoginRepository @Inject constructor(private var apiServices: APIServices) : ApiRequest() {
    private val TAG: String = javaClass.simpleName
    suspend fun loginUser(userName:String,password:String): LoginResponseDTO {
        val response = apiRequest { apiServices.loginRequest(userName,password).await() }
        return response
    }

}