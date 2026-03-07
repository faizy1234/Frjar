package com.example.frjarcustomer.data.remote.dto.response.appsetting

import com.google.gson.annotations.SerializedName

data class ServiceItem(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("title_en") val titleEn: String? = null,
    @SerializedName("title_ar") val titleAr: String? = null,
    @SerializedName("media_arr") val mediaArr: List<com.example.frjarcustomer.data.remote.dto.response.appsetting.ServiceMediaItem>? = null,
    @SerializedName("home_media") val homeMedia: String? = null,
    @SerializedName("tag") val tag: String? = null,
    @SerializedName("status") val status: Boolean? = null
)
