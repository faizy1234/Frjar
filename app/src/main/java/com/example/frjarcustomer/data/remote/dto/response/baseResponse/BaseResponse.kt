package com.example.frjarcustomer.data.remote.dto.response.baseResponse

import com.example.frjarcustomer.data.remote.dto.response.appsetting.KeywordItem
import com.example.frjarcustomer.data.remote.utils.AppLanguage
import com.example.frjarcustomer.utils.LocaleManager
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
    val arMessage: String? = null,

    @SerializedName("ur_message")
    val urMessage: String? = null,

    @SerializedName("hi_message")
    val hiMessage: String? = null
){

    val appUpdateMessageLocalized: String
        get() = (when (LocaleManager.currentLanguage) {
            AppLanguage.ARABIC.value -> arMessage ?: enMessage
            AppLanguage.HINDI.value -> hiMessage ?: enMessage
            AppLanguage.URDU.value -> urMessage ?: enMessage
            AppLanguage.ENGLISH.value -> enMessage
            null -> enMessage
            else -> enMessage
        } ?: "")
}