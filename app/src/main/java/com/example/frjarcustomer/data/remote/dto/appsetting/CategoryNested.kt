package com.example.frjarcustomer.data.remote.dto.appsetting

import com.google.gson.annotations.SerializedName

data class CategoryNested(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("title_en") val titleEn: String? = null,
    @SerializedName("title_ar") val titleAr: String? = null,
    @SerializedName("media") val media: String? = null,
    @SerializedName("service_id") val serviceId: String? = null,
    @SerializedName("service_tag") val serviceTag: String? = null
)
