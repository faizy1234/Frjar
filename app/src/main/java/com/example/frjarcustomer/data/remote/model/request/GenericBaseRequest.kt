package com.example.frjarcustomer.data.remote.model.request

import com.example.frjarcustomer.core.network.AuthInterceptor.Companion.PLATFORM_VALUE
import com.example.frjarcustomer.core.network.AuthInterceptor.Companion.SEGMENT_TYPE_VALUE
import com.google.gson.annotations.SerializedName


data class GenericBaseRequest(
    @SerializedName("device_id")
    val deviceId: String? = null,
    @SerializedName("platform")
    val platform: String? = PLATFORM_VALUE,
    @SerializedName("segment_type")
    val segmentType: String? = SEGMENT_TYPE_VALUE,
    @SerializedName("app_version")
    val appVersion: Double ? = null,
    @SerializedName("app_lang")
    val appLang: String? = null
)