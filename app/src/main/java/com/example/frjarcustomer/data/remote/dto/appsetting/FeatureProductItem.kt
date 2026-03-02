package com.example.frjarcustomer.data.remote.dto.appsetting

import com.google.gson.annotations.SerializedName

data class FeatureProductItem(
    @SerializedName("home_banner_xsmall_en") val homeBannerXsmallEn: String? = null,
    @SerializedName("home_banner_xsmall_ar") val homeBannerXsmallAr: String? = null,
    @SerializedName("products_arr") val productsArr: List<ProductImageItem>? = null
)
