package com.example.frjarcustomer.ui.screen.auth.otpScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.resourceString
import com.example.frjarcustomer.image.CoilImage
import com.example.frjarcustomer.ui.components.GenericButton
import com.example.frjarcustomer.ui.components.GenericText
import com.example.frjarcustomer.ui.components.OtpDigitsRow
import com.example.frjarcustomer.ui.components.ValidationShakeState
import com.example.frjarcustomer.ui.screen.auth.OtpViewModel
import com.example.frjarcustomer.ui.theme.AuthScreenBackground
import com.example.frjarcustomer.ui.theme.AuthTimerGreen
import com.example.frjarcustomer.ui.theme.ButtonPrimary
import com.example.frjarcustomer.ui.theme.TextOnAccent
import com.example.frjarcustomer.ui.theme.TextBlackDark
import com.example.frjarcustomer.ui.theme.TextPrimary
import com.example.frjarcustomer.ui.theme.TextGreyscale500
import com.example.frjarcustomer.ui.util.dpScaled
import com.example.frjarcustomer.utils.PhoneConstants
import com.example.frjarcustomer.ui.util.spScaled
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun OtpScreen(
    onChangeNumberClick: () -> Unit,
    onResend: () -> Unit,
    onPrimaryClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OtpViewModel
) {
    val otpContent = viewModel.otpScreenContent
    val otp by viewModel.otp.collectAsStateWithLifecycle()
    val countdownSeconds by viewModel.countdownSeconds.collectAsStateWithLifecycle()
    val validationShake by viewModel.validationShake.collectAsStateWithLifecycle(initialValue = ValidationShakeState())
    val keyboardController = LocalSoftwareKeyboardController.current

    val otpShakeTrigger = if (0 in validationShake.invalidIndices) validationShake.triggerId else 0
    val isSignup = otpContent.isSignup
    val horizontalPadding = if (isSignup) 18.sdp else 9.sdp
    val minutes = countdownSeconds / 60
    val seconds = countdownSeconds % 60
    val timeText = "%02d:%02d".format(minutes, seconds)

    val changeText = if (otpContent.isEmailComing) R.string.emputy else otpContent.changeNumberText
        ?: R.string.emputy

    val contactDisplayValue = if (otpContent.isEmailComing) {
        otpContent.email.orEmpty()
    } else {
        otpContent.number?.let { " ${PhoneConstants.SAUDI_PHONE_PREFIX}$it ." }.orEmpty()
    }

    val annotatedString = buildAnnotatedString {
        otpContent.messageText?.let { msgRes ->
            append(resourceString(msgRes))
            if (contactDisplayValue.isNotBlank()) append(contactDisplayValue)
        }
        pushStringAnnotation(tag = "CHANGE_NUMBER", annotation = "change_number")
        withStyle(
            style = SpanStyle(
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 10.ssp
            )
        ) {
            append(resourceString(changeText))
        }
        pop()
    }

    Scaffold(
        modifier = modifier
            .background(AuthScreenBackground)
            .fillMaxSize()
            .padding(horizontal = horizontalPadding)
            .systemBarsPadding()
            .pointerInput(Unit) {
                detectTapGestures { keyboardController?.hide() }
            },
        containerColor = AuthScreenBackground,

        topBar = {
            if (isSignup){
                Row(modifier = Modifier
                    .padding(bottom = 10.sdp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    CoilImage(
                        url = R.drawable.ic_close_back,
                        contentDescription = null,
                        modifier = Modifier
                            .height(34.sdp)
                            .width(26.sdp)
                            .clickable { onChangeNumberClick()}
                    )
                }
            }


        },
        bottomBar = {
            GenericButton(
                onClick = onPrimaryClick,
                modifier = Modifier
                    .padding(bottom = 16.sdp)
                    .fillMaxWidth()
                    .height(56.dpScaled()),
                backgroundColor = ButtonPrimary,
                contentColor = TextOnAccent,
                shape = RoundedCornerShape(8.dpScaled()),
                content = {
                    otpContent.primaryButtonText?.let {
                        GenericText(
                            text = resourceString(it),
                            fontSize = 16.spScaled(),
                            color = TextOnAccent
                        )
                    }

                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.height(24.sdp))
            otpContent.titleText?.let {
                GenericText(
                    text = resourceString(it),
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 24.spScaled(),
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(6.sdp))

            ClickableText(
                text = annotatedString,
                style = TextStyle(
                    fontSize = 14.spScaled(),
                    color = TextGreyscale500,
                    textAlign = TextAlign.Center
                ),
                onClick = { offset ->
                    annotatedString.getStringAnnotations(
                        tag = "CHANGE_NUMBER",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let { onChangeNumberClick() }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.sdp)
            )

            Spacer(modifier = Modifier.height(22.sdp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GenericText(
                    text = resourceString(R.string.verification_code),
                    fontSize = 14.spScaled(),
                    color = TextBlackDark,
                    fontWeight = FontWeight.W500
                )
                GenericText(
                    text = resourceString(R.string.resending),
                    fontSize = 14.spScaled(),
                    color = TextBlackDark,
                    fontWeight = FontWeight.W500
                )
            }
            Spacer(modifier = Modifier.height(7.sdp))
            OtpDigitsRow(
                otp = otp,
                onOtpChange = { viewModel.setOtp(it) }
            )
            Spacer(modifier = Modifier.height(10.sdp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (countdownSeconds == 0) Arrangement.End else Arrangement.SpaceBetween
            ) {
                if (countdownSeconds == 0) {
                    GenericText(
                        text = resourceString(R.string.resend),
                        modifier = Modifier.clickable { onResend() },
                        color = AuthTimerGreen
                    )
                } else {
                    GenericText(
                        text = resourceString(R.string.send_code_reload_in),
                        fontSize = 12.spScaled(),
                        fontWeight = FontWeight.W500,
                        color = AuthTimerGreen
                    )
                    GenericText(
                        text = timeText,
                        fontSize = 12.spScaled(),
                        fontWeight = FontWeight.W500,
                        color = AuthTimerGreen
                    )
                }


            }
        }
    }
}