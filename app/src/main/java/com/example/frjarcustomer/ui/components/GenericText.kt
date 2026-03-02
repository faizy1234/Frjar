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
    val resolvedStyle = baseStyle.copy(
        color = color,
        fontFamily = fontFamily ?: baseStyle.fontFamily,
        fontSize = if (fontSize != TextUnit.Unspecified) fontSize else baseStyle.fontSize,
        fontWeight = fontWeight ?: baseStyle.fontWeight,
        lineHeight = if (lineHeight != TextUnit.Unspecified) lineHeight else baseStyle.lineHeight,
        letterSpacing = if (letterSpacing != TextUnit.Unspecified) letterSpacing else baseStyle.letterSpacing,
        textAlign = textAlign ?: baseStyle.textAlign
    )
    Text(
        text = text,
        modifier = modifier,
        style = resolvedStyle,
        maxLines = maxLines,
        overflow = overflow
    )
}
