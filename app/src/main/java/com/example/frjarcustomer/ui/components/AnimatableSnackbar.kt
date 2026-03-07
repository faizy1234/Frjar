package com.example.frjarcustomer.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frjarcustomer.appstate.MessageContent
import com.example.frjarcustomer.appstate.MessageType
import com.example.frjarcustomer.appstate.SnackbarModel
import com.example.frjarcustomer.ui.theme.AuthTimerGreen
import com.example.frjarcustomer.ui.theme.ErrorRed
import com.example.frjarcustomer.ui.theme.InterFontFamily
import kotlinx.coroutines.delay
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AnimatableSnackbar(
    snackbarModel: SnackbarModel?,
    onSnackbarDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isVisible by remember { mutableStateOf(false) }
    var snackbarBackgroundColor by remember { mutableStateOf<Color?>(null) }
    var animProgress by remember { mutableFloatStateOf(0f) }

    val message = when (val messageContent = snackbarModel?.message) {
        is MessageContent.ResourceString -> stringResource(messageContent.resId)
        is MessageContent.PlainString -> messageContent.text
        null -> ""
    }

    LaunchedEffect(key1 = snackbarModel) {
        if (snackbarModel != null) {
            isVisible = true
            snackbarBackgroundColor = when (snackbarModel.type) {
                MessageType.ERROR -> ErrorRed
                MessageType.SUCCESS -> AuthTimerGreen
            }

            animate(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 200,
                    easing = EaseOutCubic
                )
            ) { value, _ -> animProgress = value }

            delay(snackbarModel.duration.durationMs)

            animate(
                initialValue = 1f,
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = 200,
                    easing = FastOutLinearInEasing
                )
            ) { value, _ -> animProgress = value }

            isVisible = false
            onSnackbarDismiss()
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = EnterTransition.None,
        exit = ExitTransition.None
    ) {
        Box(
            modifier = modifier
                .padding(horizontal = 12.dp)
                .padding(top = 8.dp)
                .graphicsLayer {
                    translationY = -120f * (1f - animProgress)
                    scaleX = 0.92f + (0.08f * animProgress)
                    scaleY = 0.92f + (0.08f * animProgress)
                    alpha = animProgress
                }
        ) {
            Snackbar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = snackbarBackgroundColor ?: Color.Transparent,
                shape = RoundedCornerShape(14.dp),
                actionOnNewLine = false,
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        if (snackbarModel?.drawableRes != null) {
                            Icon(
                                painter = painterResource(id = snackbarModel.drawableRes),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        if (snackbarModel?.message != null) {
                            Text(
                                text = message,
                                color = Color.White,
                                fontSize = 14.sp,
                                maxLines = 3,
                                fontFamily = InterFontFamily,
                                fontWeight = FontWeight.Medium,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            )
        }
    }
}