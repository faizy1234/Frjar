package com.example.frjarcustomer.ui.screen.intro.splash


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.frjarcustomer.R
import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.OnboardingData
import com.example.frjarcustomer.data.remote.utils.AppUpdateStatus
import com.example.frjarcustomer.image.CoilImage
import com.example.frjarcustomer.ui.components.OverlayLoader
import com.example.frjarcustomer.ui.screen.appfeature.NoConnectionScreen
import com.example.frjarcustomer.ui.theme.Screen_background_Root
import com.example.frjarcustomer.ui.theme.TextPrimary
import com.example.frjarcustomer.ui.theme.White
import kotlinx.coroutines.delay
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    moveToOnboarding: (OnboardingData) -> Unit = {},
    moveToAuth: () -> Unit = {},
    moveToAppFeature: () -> Unit = {},
    moveToVersionExpire: (AppUpdateStatus) -> Unit = {},
    viewModel: SplashViewModel
) {
    val showNoConnection by viewModel.showNoConnection.collectAsStateWithLifecycle()
    val isLoading by viewModel.isDataLoaded.collectAsStateWithLifecycle()
    var showFrame1 by remember { mutableStateOf(false) }
    var showFrame2 by remember { mutableStateOf(false) }
    var showFrame3 by remember { mutableStateOf(false) }

    val animSpec = tween<Float>(durationMillis = 800, easing = FastOutSlowInEasing)

    val frame1Alpha by animateFloatAsState(
        targetValue = if (showFrame1) 1f else 0f, animationSpec = animSpec, label = "f1Alpha"
    )
    val frame1Scale by animateFloatAsState(
        targetValue = if (showFrame1) 1f else 0.4f, animationSpec = animSpec, label = "f1Scale"
    )

    val frame2Alpha by animateFloatAsState(
        targetValue = if (showFrame2) 1f else 0f, animationSpec = animSpec, label = "f2Alpha"
    )
    val frame2Scale by animateFloatAsState(
        targetValue = if (showFrame2) 1f else 0.4f, animationSpec = animSpec, label = "f2Scale"
    )

    val frame3Alpha by animateFloatAsState(
        targetValue = if (showFrame3) 1f else 0f, animationSpec = animSpec, label = "f3Alpha"
    )
    val frame3Scale by animateFloatAsState(
        targetValue = if (showFrame3) 1f else 0.4f, animationSpec = animSpec, label = "f3Scale"
    )

    val appInfoAlpha by animateFloatAsState(
        targetValue = if (showFrame3) 1f else 0f, animationSpec = animSpec, label = "appInfoAlpha"
    )
    val appInfoScale by animateFloatAsState(
        targetValue = if (showFrame3) 1f else 0.4f, animationSpec = animSpec, label = "appInfoScale"
    )

    val frame4Alpha by animateFloatAsState(
        targetValue = if (showFrame3) 1f else 0f, animationSpec = animSpec, label = "f4Alpha"
    )
    val frame4Scale by animateFloatAsState(
        targetValue = if (showFrame3) 1f else 0.4f, animationSpec = animSpec, label = "f4Scale"
    )


    LaunchedEffect(Unit) {
        showFrame1 = true
        delay(600L)
        showFrame2 = true
        delay(600L)
        showFrame3 = true
        delay(1000L)
    }

    LaunchedEffect(Unit) {
        viewModel.destination.collect { dest ->
            when (dest) {
                is SplashDestination.OnboardingDestination -> moveToOnboarding(dest.onboardingData)
                is SplashDestination.Auth -> moveToAuth()
                is SplashDestination.AppFeature -> moveToAppFeature()
                is SplashDestination.VersionExpire -> moveToVersionExpire(dest.updateStatus)
                else -> {}
            }
        }
    }

    if (showNoConnection) {
        NoConnectionScreen(onTryAgainClick = viewModel::retry)
    } else {
        SplashContent(
            modifier = modifier,
            isLoading = isLoading,
            showFrame2 = showFrame2,
            showFrame3 = showFrame3,
            frame1Alpha = frame1Alpha,
            frame1Scale = frame1Scale,
            frame2Alpha = frame2Alpha,
            frame2Scale = frame2Scale,
            frame3Alpha = frame3Alpha,
            frame3Scale = frame3Scale,
            appInfoAlpha = appInfoAlpha,
            appInfoScale = appInfoScale,
            frame4Alpha = frame4Alpha,
            frame4Scale = frame4Scale
        )
    }
}

@Composable
private fun SplashContent(
    modifier: Modifier,
    isLoading: Boolean,
    showFrame2: Boolean,
    showFrame3: Boolean,
    frame1Alpha: Float,
    frame1Scale: Float,
    frame2Alpha: Float,
    frame2Scale: Float,
    frame3Alpha: Float,
    frame3Scale: Float,
    appInfoAlpha: Float,
    appInfoScale: Float,
    frame4Alpha: Float,
    frame4Scale: Float
) {
    Box(
        modifier
            .fillMaxSize()
            .background(White),
        contentAlignment = Alignment.Center
    ) {

        Box(
            Modifier
                .clip(CircleShape)
                .size(290.sdp)
                .graphicsLayer {
                    alpha = frame3Alpha
                    scaleX = frame3Scale
                    scaleY = frame3Scale
                },
            contentAlignment = Alignment.Center
        ) {
            CoilImage(
                R.drawable.splash_three,
                contentDescription = null,
            )
        }

        Box(
            Modifier
                .clip(CircleShape)
                .size(290.sdp)
                .graphicsLayer {
                    alpha = frame2Alpha
                    scaleX = frame2Scale
                    scaleY = frame2Scale
                },
            contentAlignment = Alignment.Center
        ) {
            CoilImage(
                R.drawable.splash_two,
                contentDescription = null,
            )
        }

        Box(
            Modifier
                .clip(CircleShape)
                .size(137.sdp)
                .graphicsLayer {
                    alpha = frame1Alpha
                    scaleX = frame1Scale
                    scaleY = frame1Scale
                },
            contentAlignment = Alignment.Center
        ) {
            CoilImage(
                R.drawable.splash_one,
                contentDescription = null,
            )
        }

        CoilImage(
            R.drawable.app_name_english,
            contentDescription = null,
            modifier = Modifier
                .size(width = 200.sdp, height = 58.sdp)
                .padding(bottom = 16.sdp)
                .graphicsLayer {
                    alpha = appInfoAlpha
                    scaleX = appInfoScale
                    scaleY = appInfoScale
                }
        )

        CoilImage(
            R.drawable.splash_description_text,
            contentDescription = null,
            modifier = Modifier
                .width(118.sdp)
                .padding(top = 47.sdp)
                .graphicsLayer {
                    alpha = appInfoAlpha
                    scaleX = appInfoScale
                    scaleY = appInfoScale
                }
        )

        Box(
            Modifier
                .fillMaxSize()
                .padding(top = 55.sdp, end = 40.sdp, start = 31.sdp, bottom = 8.sdp)
                .graphicsLayer {
                    alpha = frame4Alpha
                    scaleX = frame4Scale
                    scaleY = frame4Scale
                },
            contentAlignment = Alignment.Center
        ) {
            CoilImage(
                R.drawable.splash_full_vector,
                contentDescription = null,
            )
        }


        if (isLoading) {
            OverlayLoader()
        }


    }
}