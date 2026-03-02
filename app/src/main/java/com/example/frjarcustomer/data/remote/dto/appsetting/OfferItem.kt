package com.example.frjarcustomer.data.remote.dto.appsetting

import com.google.gson.annotations.SerializedName

data class OfferItem(
    @SerializedName("product_image") val productImage: String? = null
)
