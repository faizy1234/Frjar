package com.example.frjarcustomer.data.remote.dto.response.appsetting

import com.google.gson.annotations.SerializedName

data class ProjectSubscriptionItem(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("is_payment_required") val isPaymentRequired: Boolean? = null,
    @SerializedName("price") val price: Double? = null,
    @SerializedName("price_after_offer") val priceAfterOffer: Double? = null,
    @SerializedName("offer") val offer: Double? = null,
    @SerializedName("vat") val vat: Double? = null,
    @SerializedName("price_with_vat") val priceWithVat: Double? = null,
    @SerializedName("expiry_date") val expiryDate: String? = null,
    @SerializedName("days") val days: String? = null,
    @SerializedName("duration_en") val durationEn: String? = null,
    @SerializedName("duration_ar") val durationAr: String? = null,
    @SerializedName("is_recommended") val isRecommended: Boolean? = null
)
