package com.watercanedelivery.app.repository

import com.oviesmarterware.ovie.network.ApiRequest
import com.watercanedelivery.app.data.CommonResponseDTO
import com.watercanedelivery.app.data.LoginResponseDTO
import com.watercanedelivery.app.data.VechicelAllowance
import com.watercanedelivery.app.webservice.APIServices
import javax.inject.Inject

/**
 * Created by Karthikeyan on 02/04/2021.
 */
class VechAllowRepository @Inject constructor(private var apiServices: APIServices):ApiRequest(){

    suspend fun vechicleAllowanceData(staff_id:String): VechicelAllowance {
        val response = apiRequest { apiServices.vechicleAllowanceList(staff_id).await() }
        return response
    }

    suspend fun vechicleAddAllow(staff_id:String,startKm:String,endKm:String,entryDate:String): CommonResponseDTO {
        val response = apiRequest { apiServices.addVechAllow(staff_id,startKm,endKm,entryDate).await() }
        return response
    }
}