package com.example.frjarcustomer.data.remote.dto.response.appsetting

import com.google.gson.annotations.SerializedName

data class ProjectUnauthTextDto(
    @SerializedName("en") val en: String? = null,
    @SerializedName("ar") val ar: String? = null
)
