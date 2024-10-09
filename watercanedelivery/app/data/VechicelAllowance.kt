package com.watercanedelivery.app.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VechicelAllowance(
    val status: Boolean? = null,
    @SerializedName("staff_allowance")
    val staffAllowance: ArrayList<StaffAllowanceDTO?>? = null
)