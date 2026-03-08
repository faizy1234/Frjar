package com.example.frjarcustomer.utils

object LocaleManager {
    var currentLanguage: String? = "en"


    fun init(lang: String?) {
        currentLanguage = lang
    }
}


object UserinfoManager {
    private var userId: String? = null


    fun init(lang: String?) {
        userId = lang
    }

    fun getUserId(): String? {
        return userId
    }


    fun resetState() {
        userId = null
    }
}
