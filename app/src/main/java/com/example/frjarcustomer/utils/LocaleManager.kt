package com.example.frjarcustomer.utils

object LocaleManager {
    var currentLanguage: String? = "en"

    fun init(lang: String?) {
        currentLanguage = lang
    }
}
