package com.example.frjarcustomer.data.remote.model.request

import com.google.gson.annotations.SerializedName




data class UserRequest(

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("password")
    val password: String? = null,

    @SerializedName("otp")
    val otp: String? = null,

    @SerializedName("device_type")
    val deviceType: String? = null,

    @SerializedName("firebase_token")
    val firebaseToken: String? = null,

    @SerializedName("device_id")
    val deviceId: String? = null,

    @SerializedName("latitude")
    val latitude: String? = null,

    @SerializedName("longitude")
    val longitude: String? = null,

    @SerializedName("login_type")
    val loginType: String? = null,

    @SerializedName("social_id")
    val socialId: String? = null,

    @SerializedName("app_lang")
    val appLang: String? = null
)