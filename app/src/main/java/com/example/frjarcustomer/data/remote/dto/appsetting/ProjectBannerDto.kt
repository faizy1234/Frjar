package com.example.frjarcustomer.data.remote.dto.appsetting

import com.google.gson.annotations.SerializedName

data class ProjectBannerDto(
    @SerializedName("home_banner_medium_en") val homeBannerMediumEn: String? = null,
    @SerializedName("home_banner_medium_ar") val homeBannerMediumAr: String? = null,
    @SerializedName("home_banner_large_ar") val homeBannerLargeAr: String? = null,
    @SerializedName("home_banner_large_en") val homeBannerLargeEn: String? = null,
    @SerializedName("home_banner_xlarge_en") val homeBannerXlargeEn: String? = null,
    @SerializedName("home_banner_xlarge_ar") val homeBannerXlargeAr: String? = null,
    @SerializedName("home_banner_xsmall_en") val homeBannerXsmallEn: String? = null,
    @SerializedName("home_banner_xsmall_ar") val homeBannerXsmallAr: String? = null
)
