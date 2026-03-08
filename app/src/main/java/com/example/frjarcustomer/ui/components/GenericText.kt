package com.example.frjarcustomer.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.frjarcustomer.appstate.LocalCurrentLanguage

private const val RTL_SCRIPT_SIZE_SCALE = 0.92f

private fun lighterWeightForRtlScript(weight: FontWeight): FontWeight = when (weight.weight) {
    in 700..999 -> FontWeight.SemiBold
    in 600..699 -> FontWeight.Medium
    in 500..599 -> FontWeight.Normal
    else -> weight
}

@Composable
fun GenericText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    fontFamily: FontFamily? = null,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    style: TextStyle? = null
) {
    val baseStyle = style ?: MaterialTheme.typography.bodyMedium
    var resolvedStyle = baseStyle.copy(
        color = color,
        fontFamily = fontFamily ?: baseStyle.fontFamily,
        fontSize = if (fontSize != TextUnit.Unspecified) fontSize else baseStyle.fontSize,
        fontWeight = fontWeight ?: baseStyle.fontWeight,
        lineHeight = if (lineHeight != TextUnit.Unspecified) lineHeight else baseStyle.lineHeight,
        letterSpacing = if (letterSpacing != TextUnit.Unspecified) letterSpacing else baseStyle.letterSpacing,
        textAlign = textAlign ?: baseStyle.textAlign
    )

    val languageCode = LocalCurrentLanguage.current.languageCode
    val isRtlScript = languageCode == "ar" || languageCode == "ur"
    if (isRtlScript) {
        val weight = resolvedStyle.fontWeight ?: FontWeight.Normal
        resolvedStyle = resolvedStyle.copy(
            fontSize = (resolvedStyle.fontSize.value * RTL_SCRIPT_SIZE_SCALE).sp,
            lineHeight = (resolvedStyle.lineHeight.value * RTL_SCRIPT_SIZE_SCALE).sp,
            fontWeight = lighterWeightForRtlScript(weight)
        )
    }

    Text(
        text = text,
        modifier = modifier,
        style = resolvedStyle,
        maxLines = maxLines,
        overflow = overflow
    )
}
