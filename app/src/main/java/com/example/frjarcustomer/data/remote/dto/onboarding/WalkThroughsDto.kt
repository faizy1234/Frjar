package com.example.frjarcustomer.data.remote.dto.onboarding

import com.google.gson.annotations.SerializedName




data class WalkThroughItemDto(

    @SerializedName("_id")
    val id: String? = null,

    @SerializedName("title_en")
    val titleEn: String? = null,

    @SerializedName("title_ar")
    val titleAr: String? = null,

    @SerializedName("desc_en")
    val descEn: String? = null,

    @SerializedName("desc_ar")
    val descAr: String? = null,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("status")
    val status: Boolean? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null,

    @SerializedName("__v")
    val version: Int? = null,

    @SerializedName("app_type")
    val appType: String? = null
)