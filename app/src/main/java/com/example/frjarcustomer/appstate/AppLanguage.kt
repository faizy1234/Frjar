package com.example.frjarcustomer.appstate

import com.example.frjarcustomer.R


data class AppLanguage(
    val languageCode: String,
    val languageName: String ,
    val localizedNameRes: Int,
) {
    companion object {
         val DEFAULT = AppLanguage("en", "English",
             R.string.english)

        val SUPPORTED = listOf(
            DEFAULT,
            AppLanguage(
                languageCode = "ar",
                languageName = "Arabic",
                localizedNameRes = R.string.english
            ),
            AppLanguage(
                languageCode = "ur",
                languageName = "Urdu",
                localizedNameRes = R.string.english
            ),
            AppLanguage(
                languageCode = "hi",
                languageName = "Hindi",
                localizedNameRes = R.string.english
            ),

        )



        fun fromCode(code: String?): AppLanguage =
            SUPPORTED.firstOrNull { it.languageCode == code } ?: DEFAULT

        fun getLanguageName(languageCode: String): String {
            return SUPPORTED.find { it.languageCode == languageCode }?.languageName ?: languageCode
        }
    }


}
