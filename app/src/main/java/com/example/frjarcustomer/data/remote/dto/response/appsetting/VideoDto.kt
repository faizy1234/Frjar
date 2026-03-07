package com.example.frjarcustomer.data.remote.dto.response.appsetting

import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("media") val media: String? = null,
    @SerializedName("status") val status: Boolean? = null,
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("updatedAt") val updatedAt: String? = null,
    @SerializedName("__v") val v: Int? = null
)
