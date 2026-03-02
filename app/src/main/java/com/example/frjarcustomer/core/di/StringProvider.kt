package com.example.frjarcustomer.core.di
import android.content.Context
import android.content.res.Configuration
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class StringProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val language: LanguageProvider
) {


    fun getString(@StringRes resId: Int): String {
        return localizedContext().getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return localizedContext().getString(resId, *formatArgs)
    }

    private fun localizedContext(): Context {
        val languageCode =language.getLanguageCode()
        val locale = Locale(languageCode)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}
