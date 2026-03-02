package com.example.frjarcustomer.data.remote.dto.appsetting

import com.google.gson.annotations.SerializedName

data class ServiceMediaItem(
    @SerializedName("media") val media: String? = null,
    @SerializedName("media_ar") val mediaAr: String? = null,
    @SerializedName("banner_tag") val bannerTag: String? = null,
    @SerializedName("seconds") val seconds: String? = null,
    @SerializedName("status") val status: Boolean? = null
)
