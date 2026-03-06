package com.example.frjarcustomer.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import kotlin.math.roundToInt
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

private const val SCALE_FACTOR = 1.28


@Composable
fun Int.dpScaled(): Dp {
    val scaledInt = remember(this) {
        (this / SCALE_FACTOR).roundToInt().coerceAtLeast(0)
    }
    return scaledInt.sdp
}

@Composable
fun Int.spScaled(): TextUnit {
    val scaledInt = remember(this) {
        (this / SCALE_FACTOR).roundToInt().coerceAtLeast(1)
    }
    return scaledInt.ssp
}