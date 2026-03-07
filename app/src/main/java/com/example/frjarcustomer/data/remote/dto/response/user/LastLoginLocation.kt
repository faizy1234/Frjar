package com.example.frjarcustomer.data.remote.dto.response.user

import com.google.gson.annotations.SerializedName

data class LastLoginLocation(

    @SerializedName("latitude")
    val latitude: String? = null,

    @SerializedName("longitude")
    val longitude: String? = null,

    @SerializedName("city")
    val city: String? = null,

    @SerializedName("address")
    val address: String? = null
)