package com.watercanedelivery.app.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Karthikeyan on 23/04/2021.
 */
data class IncaneDetailDTO(
    @SerializedName("in_cane1")
    val inCane1: String? = null, @SerializedName("in_cane2")

    val inCane2: String? = null, @SerializedName("in_cane3")

    val inCane3: String? = null, @SerializedName("in_cane4")

    val inCane4: String? = null, @SerializedName("in_cane5")

    val inCane5: String? = null )
