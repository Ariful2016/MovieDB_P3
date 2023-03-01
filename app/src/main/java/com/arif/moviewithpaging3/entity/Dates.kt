package com.arif.moviewithpaging3.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Dates(
    @SerializedName("maximum")
    val maximum : String,

    @SerializedName("minimum")
    val minimum : String
)
