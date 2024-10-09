package com.watercanedelivery.app.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HomeResponseModelDTO(

    @field:SerializedName("orders")
    val orders: Int? = null,

    @field:SerializedName("total_km")
    val totalKm: Int? = null,

    @field:SerializedName("total_expenses")
    val totalExpenses: Int? = null,

    @field:SerializedName("cane")
    val cane: Int? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)