package com.example.frjarcustomer.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import network.chaintech.sdpcomposemultiplatform.sdp
@Composable
fun GenericButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    disabledBackgroundColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
    disabledContentColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
    border: BorderStroke? = null,
    shape: Shape = RoundedCornerShape(6.sdp),
    elevation: Dp = 3.dp,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    val containerColor = if (enabled) backgroundColor else disabledBackgroundColor
    val textColor = if (enabled) contentColor else disabledContentColor

    Box(
        modifier = modifier
            .shadow(elevation, shape, clip = true)
            .clip(shape)
            .background(containerColor)
            .then(
                if (border != null)
                    Modifier.border(border, shape)
                else Modifier
            )
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(LocalContentColor provides textColor) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}
@Composable
fun GenericOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    disabledBorderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
    disabledContentColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
    shape: Shape = RoundedCornerShape(8.dp),
    borderWidth: Dp = 1.dp,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
) {

    GenericButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = Color.Transparent,
        disabledContentColor = disabledContentColor,
        border = BorderStroke(
            borderWidth,
            if (enabled) borderColor else disabledBorderColor
        ),
        shape = shape,
        elevation = 0.dp,
        contentPadding = contentPadding,
        content = {}
    )
}