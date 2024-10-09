package com.watercanedelivery.app.data.cutomer

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UpdateCustomerDTO(
    var staff_id: String? = null,

    @field:SerializedName("id")
var id: String? = null,

@field:SerializedName("brand")
var brand: String? = null,

@field:SerializedName("quantity")
var quantity: String? = null,

@field:SerializedName("cane_amount")
var cane_amount: String? = null,

@field:SerializedName("payable_amount")
var payable_amount: String? = null,

@field:SerializedName("paid_amount")
var paid_amount: String? = null,

@field:SerializedName("pending_amount")
var pending_amount: String? = null,

@field:SerializedName("in_cane1")
var in_cane1: String? = null,

@field:SerializedName("in_cane2")
var in_cane2: String? = null,

@field:SerializedName("in_cane3")
var in_cane3: String? = null,

@field:SerializedName("in_cane4")
var in_cane4: String? = null,

@field:SerializedName("in_cane5")
var in_cane5: String? = null,

@field:SerializedName("out_cane1")
var out_cane1: String? = null,

@field:SerializedName("out_cane2")
var out_cane2: String? = null,

@field:SerializedName("out_cane3")
var out_cane3: String? = null,

@field:SerializedName("out_cane4")
var out_cane4: String? = null,

@field:SerializedName("out_cane5")
var out_cane5: String? = null
)