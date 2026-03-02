package com.example.frjarcustomer.data.remote.dto.baseResponse

import com.example.frjarcustomer.data.remote.dto.appsetting.KeywordItem
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(

    @SerializedName("data")
    val data: T? = null,

    @SerializedName("code")
    val code: Int? = null,

    @SerializedName("en_message")
    val enMessage: String? = null,

    @SerializedName("keyword_arr") val keywordArr: List<KeywordItem>? = null,

    @SerializedName("ar_message")
    val arMessage: String? = null
)