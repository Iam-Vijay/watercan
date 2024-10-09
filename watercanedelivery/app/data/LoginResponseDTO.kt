package com.watercanedelivery.app.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponseDTO(

    @field:SerializedName("staff")
    val staff: StaffDTO? = null,

    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("msg")
    val msg: String? = null
)