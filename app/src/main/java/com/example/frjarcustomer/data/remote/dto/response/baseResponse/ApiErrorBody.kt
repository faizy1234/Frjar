package com.example.frjarcustomer.data.remote.dto.response.baseResponse

import com.example.frjarcustomer.data.remote.utils.AppLanguage

data class ApiErrorBody(
    val error: String? = null,
    val code: Int? = null,
    val enMessage: String? = null,
    val arMessage: String? = null,
    val urMessage: String? = null,
    val hiMessage: String? = null
) {
    fun messageFor(languageCode: String): String =
        when (languageCode) {
            AppLanguage.ARABIC.value -> arMessage
            AppLanguage.URDU.value -> urMessage
            AppLanguage.HINDI.value -> hiMessage
            else -> enMessage
        }.orEmpty().ifBlank { error.orEmpty() }.ifBlank { enMessage.orEmpty() }
}
