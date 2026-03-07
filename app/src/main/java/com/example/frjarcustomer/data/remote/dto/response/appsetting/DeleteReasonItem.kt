package com.example.frjarcustomer.data.remote.dto.response.appsetting

import com.google.gson.annotations.SerializedName

data class DeleteReasonItem(
    @SerializedName("ar_reason_name") val arReasonName: String? = null,
    @SerializedName("en_reason_name") val enReasonName: String? = null,
    @SerializedName("tag") val tag: String? = null,
    @SerializedName("_id") val id: String? = null
)
