package com.example.frjarcustomer.data.remote.dto.response.user

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @SerializedName("desc")
    val desc: String? = null,

    @SerializedName("data_preference")
    val dataPreference: String? = null,

    @SerializedName("_id")
    val userId: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("password")
    val password: String? = null,

    @SerializedName("user_type")
    val userType: String? = null,

    @SerializedName("is_verified")
    val isVerified: Boolean? = null,

    @SerializedName("status")
    val status: Boolean? = null,

    @SerializedName("name")
    val name: String? = null,

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

    @SerializedName("media")
    val media: String? = null,

    @SerializedName("request")
    val request: String? = null,

    @SerializedName("city")
    val city: String? = null,

    @SerializedName("app_type")
    val appType: String? = null,

    @SerializedName("last_login_location")
    val lastLoginLocation: com.example.frjarcustomer.data.remote.dto.response.user.LastLoginLocation? = null,

    @SerializedName("last_login_platform")
    val lastLoginPlatform: String? = null,

    @SerializedName("review_arr")
    val reviewArr: List<Any>? = null,

    @SerializedName("project_transaction_arr")
    val projectTransactionArr: List<Any>? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null,

    @SerializedName("__v")
    val v: Int? = null,

    @SerializedName("service_status")
    val serviceStatus: String? = null,

    @SerializedName("read_id_arr")
    val readIdArr: List<String>? = null,

    @SerializedName("token")
    val token: String? = null
)