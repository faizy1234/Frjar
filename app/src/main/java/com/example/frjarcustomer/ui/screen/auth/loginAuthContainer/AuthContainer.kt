package com.example.frjarcustomer.ui.screen.auth.loginAuthContainer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.animation.core.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.absoluteValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.resourceString
import com.example.frjarcustomer.image.CoilImage
import com.example.frjarcustomer.ui.components.AuthToggle
import com.example.frjarcustomer.ui.components.OverlayLoader
import com.example.frjarcustomer.ui.components.ValidationShakeState
import com.example.frjarcustomer.ui.screen.auth.OtpViewModel
import com.example.frjarcustomer.ui.screen.auth.otpScreen.OtpScreen
import com.example.frjarcustomer.ui.theme.AuthScreenBackground
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun LoginAuthContainer(
    onOtpVerified: () -> Unit,
    onBackClick: () -> Unit,
    onForgotPassword: () -> Unit,
    otpVm: OtpViewModel,
    loginVm: LoginViewModel
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()
    val pagerPage by loginVm.pagerPage.collectAsStateWithLifecycle()
    val mobileNumber by loginVm.mobileNumber.collectAsStateWithLifecycle()
    val password by loginVm.password.collectAsStateWithLifecycle()
    val passwordVisible by loginVm.passwordVisible.collectAsStateWithLifecycle()
    val submitPressed by loginVm.submitButtonPressed.collectAsStateWithLifecycle()
    val validationShake by loginVm.validationShake.collectAsStateWithLifecycle(initialValue = ValidationShakeState())
    val loginLoading by loginVm.isLoading.collectAsStateWithLifecycle(initialValue = false)
    val otpLoading by otpVm.isLoading.collectAsStateWithLifecycle(initialValue = false)

    LaunchedEffect(pagerState.currentPage) {
        loginVm.setPagerPage(pagerState.currentPage)
    }

    Box(modifier = Modifier.fillMaxSize().background(AuthScreenBackground)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 12.sdp),
            horizontalAlignment = Alignment.Start
        ) {
            CoilImage(
            url = R.drawable.ic_close_back,
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 8.sdp)
                .height(34.sdp)
                .width(26.sdp)
                .clickable { onBackClick() },
            mirrorInRtl = true
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            AuthToggle(
                titles = listOf(
                    resourceString(R.string.login_with_otp),
                    resourceString(R.string.login_with_password)
                ),
                selectedIndex = pagerPage,
                onTabSelected = { index ->
                    loginVm.setPagerPage(index)
                    scope.launch {
                        pagerState.animateScrollToPage(
                            page = index,
                            animationSpec = tween(
                                durationMillis = 400,
                                easing = FastOutSlowInEasing
                            )
                        )
                    }
                }
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .background(AuthScreenBackground)

                .fillMaxWidth()
                .weight(1f),
            userScrollEnabled = true,
            pageSpacing = 0.sdp,
            beyondViewportPageCount = 1
        ) { page ->

            val pageOffset = (
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                    ).absoluteValue


            val alpha by animateFloatAsState(
                targetValue = if (pageOffset < 0.5f) 1f else 0f,
                animationSpec = tween(durationMillis = 300, easing = LinearEasing),
                label = "pageAlpha"
            )

            val slideOffset by animateFloatAsState(
                targetValue = if (pageOffset < 0.5f) 0f else if (page > pagerState.currentPage) 60f else -60f,
                animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
                label = "pageSlide"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        this.alpha = alpha
                        translationX = slideOffset.dp.toPx()
                    }
            ) {
                when (page) {
                    0 -> OtpScreen(
                        onChangeNumberClick = onBackClick,
                        onResend = { otpVm.resendOtp() },
                        onPrimaryClick = { otpVm.validateOtpAndProceed(onOtpVerified) },
                        viewModel = otpVm
                    )

                    1 -> LoginWithPassword(
                        mobileNumber = mobileNumber,
                        onMobileNumberChange = loginVm::setMobileNumber,
                        password = password,
                        onPasswordChange = loginVm::setPassword,
                        passwordVisible = passwordVisible,
                        submitPressed = submitPressed,
                        validationShake = validationShake,
                        onTogglePasswordVisible = loginVm::togglePasswordVisible,
                        onForgotPasswordClick = onForgotPassword,
                        onLoginClick = { loginVm.onLoginClick(onOtpVerified) }
                    )
                }
            }
        }
    }
        if (loginLoading || otpLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                OverlayLoader()
            }
        }
    }
}


