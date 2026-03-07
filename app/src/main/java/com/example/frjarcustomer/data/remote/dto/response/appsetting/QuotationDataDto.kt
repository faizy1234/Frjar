package com.example.frjarcustomer.data.remote.dto.response.appsetting

import com.google.gson.annotations.SerializedName

data class QuotationDataDto(
    @SerializedName("quotation_amount") val quotationAmount: Double? = null,
    @SerializedName("is_payment_required") val isPaymentRequired: Boolean? = null,
    @SerializedName("inquiry_desc_en") val inquiryDescEn: String? = null,
    @SerializedName("inquiry_desc_ar") val inquiryDescAr: String? = null,
    @SerializedName("quote_desc_en") val quoteDescEn: String? = null,
    @SerializedName("quote_desc_ar") val quoteDescAr: String? = null
)
