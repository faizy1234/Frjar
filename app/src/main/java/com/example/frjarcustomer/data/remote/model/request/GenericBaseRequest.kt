package com.example.frjarcustomer.data.remote.model.request

import com.example.frjarcustomer.core.network.AuthInterceptor.Companion.CUSTOMER
import com.example.frjarcustomer.core.network.AuthInterceptor.Companion.PLATFORM_VALUE
import com.example.frjarcustomer.core.network.AuthInterceptor.Companion.SEGMENT_TYPE_VALUE
import com.example.frjarcustomer.core.network.AuthInterceptor.Companion.STANDARD_TYPE
import com.google.gson.annotations.SerializedName


data class GenericBaseRequest(
    @SerializedName("user_id")
    val userId: String? = null,
    @SerializedName("is_verified")
    val isVerified: Boolean?= null,
    @SerializedName("device_id")
    val deviceId: String? = null,
    @SerializedName("platform")
    val platform: String? = PLATFORM_VALUE,
    @SerializedName("segment_type")
    val segmentType: String? = SEGMENT_TYPE_VALUE,
    @SerializedName("app_version")
    val appVersion: Double? = null,
    @SerializedName("app_lang")
    val appLang: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("email")
    val email: String? = null,


    @SerializedName("user_type")
    val userType: String? = CUSTOMER,

    @SerializedName("password")
    val password: String? = null,
    @SerializedName("otp")
    val otp: String? = null,

    @SerializedName("device_type")
    val deviceType: String? = PLATFORM_VALUE,

    @SerializedName("firebase_token")
    val firebaseToken: String? = null,


    @SerializedName("latitude")
    val latitude: String? = null,

    @SerializedName("longitude")
    val longitude: String? = null,

    @SerializedName("login_type")
    val loginType: String? = STANDARD_TYPE,

    @SerializedName("social_id")
    val socialId: String? = null,

)