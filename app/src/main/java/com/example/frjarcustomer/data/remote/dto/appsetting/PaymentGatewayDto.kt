package com.example.frjarcustomer.data.remote.dto.appsetting

import com.google.gson.annotations.SerializedName

data class PaymentGatewayDto(
    @SerializedName("active_payment_gateway") val activePaymentGateway: String? = null,
    @SerializedName("active_native_payment_gateway") val activeNativePaymentGateway: Boolean? = null,
    @SerializedName("enable_apple_payment") val enableApplePayment: Boolean? = null,
    @SerializedName("enable_stc_payment") val enableStcPayment: Boolean? = null
)
