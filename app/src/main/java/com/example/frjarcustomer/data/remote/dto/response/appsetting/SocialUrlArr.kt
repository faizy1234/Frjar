package com.example.frjarcustomer.data.remote.dto.response.appsetting

import com.google.gson.annotations.SerializedName

data class SocialUrlArr(
    @SerializedName("linkedin") val linkedin: String? = null,
    @SerializedName("instagram") val instagram: String? = null,
    @SerializedName("youtube") val youtube: String? = null,
    @SerializedName("x") val x: String? = null
)
