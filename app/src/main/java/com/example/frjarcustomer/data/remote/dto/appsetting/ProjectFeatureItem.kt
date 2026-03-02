package com.example.frjarcustomer.data.remote.dto.appsetting

import com.google.gson.annotations.SerializedName

data class ProjectFeatureItem(
    @SerializedName("en") val en: String? = null,
    @SerializedName("ar") val ar: String? = null
)
