package com.example.frjarcustomer.data.remote.dto.response.appsetting

import com.google.gson.annotations.SerializedName

data class KeywordItem(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("title_ar") val titleAr: String? = null,
    @SerializedName("title_en") val titleEn: String? = null,
    @SerializedName("service_tag") val serviceTag: String? = null,
    @SerializedName("service_id") val serviceId: String? = null
)
