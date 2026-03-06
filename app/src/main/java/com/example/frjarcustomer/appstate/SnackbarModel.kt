package com.example.frjarcustomer.appstate

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
data class SnackbarModel(
    val type: MessageType = MessageType.SUCCESS,
    @DrawableRes val drawableRes: Int? = null,
    val message: MessageContent? = null,
    val duration: SnackbarDuration = SnackbarDuration.SHORT,
)

@Immutable
enum class MessageType {
    ERROR,
    SUCCESS
}

@Immutable
enum class SnackbarDuration(val durationMs: Long) {
    SHORT(4000L),
    LONG(10000L)
}

@Stable
sealed class MessageContent {
    data class ResourceString(@StringRes val resId: Int) : MessageContent()
    data class PlainString(val text: String) : MessageContent()
}
