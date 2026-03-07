package com.example.frjarcustomer.data.remote.dto.response.otp

import com.google.gson.annotations.SerializedName

data class OtpResendResponse(
    @SerializedName("otp")
    var otp: Int? = null
)