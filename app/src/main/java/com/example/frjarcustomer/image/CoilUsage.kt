package com.example.frjarcustomer.image

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.LayoutDirection
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage

/**
 * Use our configured Coil ImageLoader (cache + OkHttp) via [AsyncImage].
 * No need to pass imageLoader — singleton is set in [CoilModule].
 *
 * Example:
 * ```
 * AsyncImage(
 *     model = imageUrl,
 *     contentDescription = null,
 *     modifier = Modifier.size(48.dp),
 * )
 * ```
 *
 * To warm cache from splash (after API returns URLs):
 * ```
 * viewModelScope.launch {
 *     val urls = api.getSplashData().imageUrls
 *     imageCacheWarmer.preloadUrls(urls)
 * }
 * ```
 */

/**
 * @param mirrorInRtl When true, flips the image horizontally in RTL so directional icons
 * (e.g. back arrow) point the correct way. Use for back/forward/arrow icons only.
 */
@Composable
fun CoilImage(
    url: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    animate: Boolean = false,
    mirrorInRtl: Boolean = false,
) {
    if (url == null) return

    val layoutDirection = LocalLayoutDirection.current
    val rtlScaleX = if (mirrorInRtl && layoutDirection == LayoutDirection.Rtl) -1f else 1f
    val rtlMirrorModifier = Modifier.graphicsLayer { scaleX = rtlScaleX }

    if (animate) {
        var visible by remember { mutableStateOf(false) }

        val alpha by animateFloatAsState(
            targetValue = if (visible) 1f else 0f,
            animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
            label = "alpha"
        )
        val scale by animateFloatAsState(
            targetValue = if (visible) 1f else 0.4f,
            animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
            label = "scale"
        )

        LaunchedEffect(Unit) {
            visible = true
        }

        AsyncImage(
            model = url,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier.graphicsLayer {
                this.alpha = alpha
                this.scaleX = scale * rtlScaleX
                this.scaleY = scale
            },
        )
    } else {
        AsyncImage(
            clipToBounds = true,
            model = url,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = rtlMirrorModifier.then(modifier),
        )
    }
}