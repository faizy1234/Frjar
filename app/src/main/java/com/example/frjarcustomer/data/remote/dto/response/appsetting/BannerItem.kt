package com.example.frjarcustomer.data.remote.dto.response.appsetting

import com.google.gson.annotations.SerializedName

data class BannerItem(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("media") val media: String? = null,
    @SerializedName("media_ar") val mediaAr: String? = null,
    @SerializedName("platform") val platform: String? = null,
    @SerializedName("banner_tag") val bannerTag: String? = null,
    @SerializedName("seconds") val seconds: String? = null,
    @SerializedName("status") val status: Boolean? = null,
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("__v") val v: Int? = null,
    @SerializedName("company_id") val companyId: String? = null,
    @SerializedName("name_en") val nameEn: String? = null,
    @SerializedName("product_id") val productId: String? = null,
    @SerializedName("service_id") val serviceId: String? = null,
    @SerializedName("service_tag") val serviceTag: String? = null,
    @SerializedName("category_id") val categoryId: String? = null,
    @SerializedName("category_tag") val categoryTag: String? = null
)
