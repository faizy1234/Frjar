package com.example.frjarcustomer.appstate

import android.content.Context
import android.content.res.Configuration
import android.view.View
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.text.TextUtilsCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.util.Locale


val LocalResourceContext = compositionLocalOf<Context> {
    error("LocalResourceContext not provided — wrap root with LanguageAwareComponent { }")
}

val LocalCurrentLanguage = compositionLocalOf<AppLanguage> {
    error("LocalResourceContext not provided — wrap root with LanguageAwareComponent { }")
}
val LocalPaddingValues = compositionLocalOf<PaddingValues> {
    error("LocalResourceContext not provided — wrap root with LanguageAwareComponent { }")
}


@Composable
fun LanguageAwareComponent(
    viewModel: MainActivityVm,
    activityContext: Context,
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current

    val currentLanguage by viewModel.currentLanguage.collectAsStateWithLifecycle()

    var resourceContext by remember(activityContext) {
        mutableStateOf(activityContext)
    }

    LaunchedEffect(currentLanguage) {
        val newLocale = Locale(currentLanguage.languageCode)
        val newConfig = Configuration(configuration)
        newConfig.setLocale(newLocale)

        resourceContext = activityContext.createConfigurationContext(newConfig)
    }



    val layoutDirection = remember(currentLanguage) {
        if (TextUtilsCompat.getLayoutDirectionFromLocale(Locale(currentLanguage.languageCode)) == View.LAYOUT_DIRECTION_RTL) {
            LayoutDirection.Rtl
        } else {
            LayoutDirection.Ltr
        }
    }

    CompositionLocalProvider(
        LocalResourceContext provides resourceContext,
        LocalCurrentLanguage provides currentLanguage,
        LocalLayoutDirection provides layoutDirection
    ) {
        content()
    }
}

@Composable
fun resourceString(resId: Int): String {
    return LocalResourceContext.current.getString(resId)
}

@Composable
fun resourceString(resId: Int, vararg formatArgs: Any): String {
    return LocalResourceContext.current.getString(resId, *formatArgs)
}
