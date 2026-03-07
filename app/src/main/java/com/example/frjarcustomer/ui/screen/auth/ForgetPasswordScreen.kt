package com.example.frjarcustomer.ui.screen.auth

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.resourceString
import com.example.frjarcustomer.image.CoilImage
import com.example.frjarcustomer.ui.components.AuthTextField
import com.example.frjarcustomer.ui.components.GenericButton
import com.example.frjarcustomer.ui.components.GenericText
import com.example.frjarcustomer.ui.components.OtpDigitsRow
import com.example.frjarcustomer.ui.theme.AuthBorder
import com.example.frjarcustomer.ui.theme.AuthBorderNew
import com.example.frjarcustomer.ui.theme.AuthScreenBackground
import com.example.frjarcustomer.ui.theme.AuthTimerGreen
import com.example.frjarcustomer.ui.theme.ButtonPrimary
import com.example.frjarcustomer.ui.theme.ButtonSecondary
import com.example.frjarcustomer.ui.theme.InterFontFamily
import com.example.frjarcustomer.ui.theme.TextBlackDark
import com.example.frjarcustomer.ui.theme.TextBlackDarkTitle
import com.example.frjarcustomer.ui.theme.TextGreyscale500
import com.example.frjarcustomer.ui.theme.TextOnAccent
import com.example.frjarcustomer.ui.theme.TextOnLightGray
import com.example.frjarcustomer.ui.theme.TextPrimary
import com.example.frjarcustomer.ui.theme.White
import com.example.frjarcustomer.ui.util.dpScaled
import com.example.frjarcustomer.ui.util.spScaled
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun ForgetPasswordScreen(
    onBackPress: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: ForgetPasswordViewModel = hiltViewModel()
) {
    val step by viewModel.step.collectAsState()
    val selectedMethodEmail by viewModel.selectedMethodEmail.collectAsState()
    val email by viewModel.email.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val otp by viewModel.otp.collectAsState()
    val countdownSeconds by viewModel.countdownSeconds.collectAsState()
    val newPassword by viewModel.newPassword.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val newPasswordVisible by viewModel.newPasswordVisible.collectAsState()
    val confirmPasswordVisible by viewModel.confirmPasswordVisible.collectAsState()

    BackHandler() {
        if (step == ForgetPasswordStep.MethodSelect) {
            onBackPress()
        } else {
            viewModel.onBack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthScreenBackground)
            .statusBarsPadding()
    ) {
        AnimatedContent(
            targetState = step,
            modifier = Modifier.fillMaxSize(),
            transitionSpec = {
                val initial = initialState
                val target = targetState
                val forward = target.ordinal > initial.ordinal
                val enter = slideInHorizontally(
                    initialOffsetX = { if (forward) it else -it },
                    animationSpec = tween(320)
                ) + fadeIn(animationSpec = tween(320))
                val exit = slideOutHorizontally(
                    targetOffsetX = { if (forward) -it else it },
                    animationSpec = tween(320)
                ) + fadeOut(animationSpec = tween(320))
                enter togetherWith exit
            },
            label = "forgot_password_steps"
        ) { currentStep ->
            when (currentStep) {
                ForgetPasswordStep.MethodSelect -> MethodSelectStep(
                    selectedMethodEmail = selectedMethodEmail,
                    onSelectEmail = viewModel::selectMethodEmail,
                    onContinue = viewModel::onContinueFromMethod,
                    onBackPress = onBackPress
                )

                ForgetPasswordStep.InputEmail -> InputEmailStep(
                    email = email,
                    onEmailChange = viewModel::setEmail,
                    onSendCode = viewModel::onSendCode,
                    onBackPress = { viewModel.onBack() }
                )

                ForgetPasswordStep.InputPhone -> InputPhoneStep(
                    phone = phone,
                    onPhoneChange = viewModel::setPhone,
                    onSendCode = viewModel::onSendCode,
                    onBackPress = { viewModel.onBack() }
                )

                ForgetPasswordStep.VerifyOtp -> VerifyOtpStep(
                    email = email,
                    phone = phone,
                    isEmail = selectedMethodEmail,
                    otp = otp,
                    onOtpChange = viewModel::setOtp,
                    countdownSeconds = countdownSeconds,
                    onResend = { viewModel.setCountdownSeconds(48) },
                    onContinue = viewModel::onVerifyOtpContinue,
                    onBackPress = { viewModel.onBack() }
                )

                ForgetPasswordStep.NewPassword -> NewPasswordStep(
                    newPassword = newPassword,
                    onNewPasswordChange = viewModel::setNewPassword,
                    newPasswordVisible = newPasswordVisible,
                    onToggleNewPasswordVisible = viewModel::toggleNewPasswordVisible,
                    confirmPassword = confirmPassword,
                    onConfirmPasswordChange = viewModel::setConfirmPassword,
                    confirmPasswordVisible = confirmPasswordVisible,
                    onToggleConfirmPasswordVisible = viewModel::toggleConfirmPasswordVisible,
                    onConfirm = viewModel::onConfirmNewPassword,
                    onBackPress = { viewModel.onBack() }
                )

                ForgetPasswordStep.Success -> SuccessStep(
                    onLoginClick = onLoginClick,
                    onBackPress = { viewModel.onBack() }
                )
            }
        }
    }
}

@Composable
private fun MethodSelectStep(
    selectedMethodEmail: Boolean,
    onSelectEmail: (Boolean) -> Unit,
    onContinue: () -> Unit,
    onBackPress: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .background(AuthScreenBackground)
            .fillMaxSize()
            .systemBarsPadding(),
        containerColor = AuthScreenBackground,
        bottomBar = {
            GenericButton(
                onClick = onContinue,
                modifier = Modifier
                    .padding( start =12.sdp ,end=12.sdp, bottom = 16.sdp)
                    .fillMaxWidth()
                    .height(43.sdp),
                backgroundColor = ButtonPrimary,
                contentColor = TextOnAccent,
                content = {
                    GenericText(
                        text = resourceString(R.string.continue_),
                        fontSize = 12.ssp,
                        color = TextOnAccent
                    )
                }
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(AuthScreenBackground)
                .padding(start= 12.sdp, end = 12.sdp     , bottom = paddingValues.calculateBottomPadding()),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(16.dpScaled()))
            CoilImage(
                url = R.drawable.ic_close_back,
                contentDescription = null,
                modifier = Modifier
                    .height(34.sdp)
                    .width(26.sdp)
                    .clickable { onBackPress() }
            )

            Spacer(modifier = Modifier.height(16.sdp))
            GenericText(
                text = resourceString(R.string.forgot_password).replace("?", ""),
                fontSize = 15.ssp,
                fontWeight = FontWeight.W500,
                color = TextBlackDarkTitle,
            )
            Spacer(modifier = Modifier.height(6.sdp))
            GenericText(
                text = resourceString(R.string.select_verification_method_and_we_will_send_verification_code),
                fontSize = 12.ssp,
                color = TextOnLightGray,
            )
            Spacer(modifier = Modifier.height(21.sdp))
            MethodCard(
                title = resourceString(R.string.email),
                subtitle = resourceString(R.string.send_to_your_email),
                selected = selectedMethodEmail,
                icon = R.drawable.ic_mail,
                onClick = { onSelectEmail(true) }
            )
            Spacer(modifier = Modifier.height(20.dpScaled()))
            MethodCard(
                title = resourceString(R.string.phone_number),
                subtitle = resourceString(R.string.send_to_your_phone_number),
                selected = !selectedMethodEmail,
                icon = R.drawable.ic_phone,
                onClick = { onSelectEmail(false) }
            )





        }
    }
}

@Composable
private fun MethodCard(
    title: String,
    subtitle: String,
    @DrawableRes icon: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(9.sdp)
    val borderColor = if (selected) TextPrimary else AuthBorderNew
    val iconTint = if (selected) TextPrimary else TextOnLightGray
    val iconBg = if (selected) TextPrimary.copy(alpha = 0.05f) else AuthBorder.copy(alpha = 0f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, shape)
            .clickable(onClick = onClick)
            .padding(11.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(37.sdp)
                .border(1.5.dp, borderColor, CircleShape)
                .background(iconBg, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(16.sdp)
            )
        }

        Spacer(modifier = Modifier.width(15.sdp))

        Column(modifier = Modifier.weight(1f)) {
            GenericText(
                text = title,
                fontSize = 12.ssp,
                color = TextBlackDarkTitle,
            )
            GenericText(
                text = subtitle,
                fontSize = 12.ssp,
                color = TextOnLightGray,
            )
        }
    }
}

@Composable
private fun InputEmailStep(
    email: String,
    onEmailChange: (String) -> Unit,
    onSendCode: () -> Unit,
    onBackPress: () -> Unit
) {

    Scaffold(
        modifier = Modifier
            .background(AuthScreenBackground)
            .fillMaxSize()
            .systemBarsPadding(),
        containerColor = AuthScreenBackground,
        bottomBar = {
            GenericButton(
                onClick = onSendCode,
                modifier = Modifier
                    .padding( start =12.sdp ,end=12.sdp, bottom = 16.sdp)
                    .fillMaxWidth()
                    .height(43.sdp),
                backgroundColor = ButtonPrimary,
                contentColor = ButtonSecondary,
                content = {
                    GenericText(
                        text = resourceString(R.string.send_code),
                        fontSize = 12.ssp,
                        color = ButtonSecondary
                    )
                }
            )
        }
    ) {


        paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(AuthScreenBackground)
                .padding(start= 12.sdp, end = 12.sdp     , bottom = paddingValues.calculateBottomPadding()),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(16.dpScaled()))
            CoilImage(
                url = R.drawable.ic_close_back,
                contentDescription = null,
                modifier = Modifier
                    .height(34.sdp)
                    .width(26.sdp)
                    .clickable { onBackPress() }
            )
            Spacer(modifier = Modifier.height(16.sdp))

            GenericText(
                text = resourceString(R.string.reset_password),
                fontSize = 15.ssp,
                fontWeight = FontWeight.W500,
                color = TextBlackDarkTitle,
            )
            Spacer(modifier = Modifier.height(6.sdp))
            GenericText(
                text = resourceString(R.string.enter_your_email_we_will_send_a_verification_code_to_email),
                fontSize = 12.ssp,
                color = TextOnLightGray
            )
            Spacer(modifier = Modifier.height(21.sdp))
            AuthTextField(
                label = resourceString(R.string.email),
                value = email,
                onValueChange = onEmailChange,
                placeholder = resourceString(R.string.example_email),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_mail),
                        contentDescription = null,
                        tint = TextPrimary,
                        modifier = Modifier.size(18.sdp)
                    )

                },
            )



        }
    }

}

@Composable
private fun InputPhoneStep(
    phone: String,
    onPhoneChange: (String) -> Unit,
    onSendCode: () -> Unit,
    onBackPress: () -> Unit
) {

    Scaffold(
        modifier = Modifier
            .background(AuthScreenBackground)
            .fillMaxSize()
            .systemBarsPadding(),
        containerColor = AuthScreenBackground,
        bottomBar = {
            GenericButton(
                onClick = onSendCode,
                modifier = Modifier
                    .padding( start =12.sdp ,end=12.sdp, bottom = 16.sdp)
                    .fillMaxWidth()
                    .height(43.sdp),
                backgroundColor = ButtonPrimary,
                contentColor = ButtonSecondary,
                content = {
                    GenericText(
                        text = resourceString(R.string.send_code),
                        fontSize = 12.ssp,
                        color = ButtonSecondary
                    )
                }
            )
        }
    ) {


            paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(AuthScreenBackground)
                .padding(start= 12.sdp, end = 12.sdp     , bottom = paddingValues.calculateBottomPadding()),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(16.dpScaled()))
            CoilImage(
                url = R.drawable.ic_close_back,
                contentDescription = null,
                modifier = Modifier
                    .height(34.sdp)
                    .width(26.sdp)
                    .clickable { onBackPress() }
            )
            Spacer(modifier = Modifier.height(16.sdp))


            GenericText(
                text = resourceString(R.string.reset_password),
                fontSize = 15.ssp,
                fontWeight = FontWeight.W500,
                color = TextBlackDarkTitle,
            )
            Spacer(modifier = Modifier.height(6.sdp))
            GenericText(
                text = resourceString(R.string.enter_your_phone_number_we_will_send_a_verification_code),
                fontSize = 12.ssp,
                color = TextOnLightGray
            )

            Spacer(modifier = Modifier.height(21.sdp))
            AuthTextField(
                label = resourceString(R.string.phone),
                keyboardType = KeyboardType.Phone,
                value = phone,
                onValueChange = onPhoneChange,
                prefixText ="(+966)",
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_phone),
                        contentDescription = null,
                        tint = TextPrimary,
                        modifier = Modifier.size(16.sdp)
                    )

                },
            )



}

}
    }

@Composable
private fun VerifyOtpStep(
    email: String,
    phone: String,
    isEmail: Boolean,
    otp: String,
    onOtpChange: (String) -> Unit,
    countdownSeconds: Int,
    onResend: () -> Unit,
    onContinue: () -> Unit,
    onBackPress: () -> Unit
) {
    val sentTo = if (isEmail) email else phone
    val messageText = "${resourceString(R.string.please_enter_the_code_we_just_sent_to)} ${
        if (isEmail) resourceString(R.string.email) else resourceString(R.string.phone_number)
    } $sentTo. "

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthScreenBackground)
    ) {
        Spacer(modifier = Modifier.height(24.dpScaled()))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 9.dpScaled())
                .clickable(onClick = onBackPress),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(
                url = R.drawable.ic_close_back,
                contentDescription = null,
                modifier = Modifier.size(26.dpScaled(), 34.dpScaled())
            )
        }
//        OtpPageContent(
//            modifier = Modifier.weight(1f),
//            messageText = messageText,
//            changeNumberText = resourceString(R.string.change_number),
//            onChangeNumberClick = onBackPress,
//            otp = otp,
//            onOtpChange = onOtpChange,
//            countdownSeconds = countdownSeconds,
//            onResend = onResend,
//            primaryButtonText = resourceString(R.string.continue_),
//            onPrimaryClick = onContinue,
//            titleText = resourceString(R.string.verify_code),
//            verificationCodeLabel = resourceString(R.string.verification_code),
//            resendingLabel = resourceString(R.string.resending),
//            sendCodeReloadInLabel = resourceString(R.string.send_code_reload_in)
//        )
    }
}

@Composable
private fun NewPasswordStep(
    newPassword: String,
    onNewPasswordChange: (String) -> Unit,
    newPasswordVisible: Boolean,
    onToggleNewPasswordVisible: () -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    confirmPasswordVisible: Boolean,
    onToggleConfirmPasswordVisible: () -> Unit,
    onConfirm: () -> Unit,
    onBackPress: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = 24.dpScaled())
    ) {
        Spacer(modifier = Modifier.height(24.dpScaled()))
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onBackPress)) {
            CoilImage(
                url = R.drawable.ic_close_back,
                contentDescription = null,
                modifier = Modifier.size(26.dpScaled(), 34.dpScaled())
            )
        }
        Spacer(modifier = Modifier.height(24.dpScaled()))
        GenericText(
            text = resourceString(R.string.new_password),
            fontSize = 24.spScaled(),
            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
            color = TextPrimary,
            fontFamily = InterFontFamily
        )
        Spacer(modifier = Modifier.height(8.dpScaled()))
        GenericText(
            text = resourceString(R.string.create_a_new_password_that_is_safe_and_easy_to_remember),
            fontSize = 16.spScaled(),
            color = TextGreyscale500,
            fontFamily = InterFontFamily
        )
        Spacer(modifier = Modifier.height(24.dpScaled()))
        AuthTextField(
            label = resourceString(R.string.new_password),
            value = newPassword,
            onValueChange = onNewPasswordChange,
            placeholder = resourceString(R.string.please_enter_new_password),
            isPassword = true,
            passwordVisible = newPasswordVisible,
            onTrailingIconClick = onToggleNewPasswordVisible,
            trailingIcon = {
                GenericText(
                    text = if (newPasswordVisible) resourceString(R.string.hide) else resourceString(
                        R.string.show
                    ),
                    fontSize = 12.spScaled(),
                    color = TextGreyscale500,
                    fontFamily = InterFontFamily
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dpScaled()))
        AuthTextField(
            label = resourceString(R.string.confirm_password),
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            placeholder = resourceString(R.string.confirm_password),
            isPassword = true,
            passwordVisible = confirmPasswordVisible,
            onTrailingIconClick = onToggleConfirmPasswordVisible,
            trailingIcon = {
                GenericText(
                    text = if (confirmPasswordVisible) resourceString(R.string.hide) else resourceString(
                        R.string.show
                    ),
                    fontSize = 12.spScaled(),
                    color = TextGreyscale500,
                    fontFamily = InterFontFamily
                )
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        GenericButton(
            onClick = onConfirm,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dpScaled())
                .padding(bottom = 24.dpScaled()),
            backgroundColor = ButtonPrimary,
            contentColor = TextOnAccent,
            content = {
                GenericText(
                    text = resourceString(R.string.confirm_new_password),
                    fontSize = 16.spScaled(),
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                    color = TextOnAccent,
                    fontFamily = InterFontFamily
                )
            }
        )
    }
}

@Composable
private fun SuccessStep(
    onLoginClick: () -> Unit,
    onBackPress: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TextBlackDark)
            .padding(horizontal = 24.dpScaled()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dpScaled()))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f))
            CoilImage(
                url = R.drawable.ic_forgot_password_success,
                contentDescription = null,
                modifier = Modifier.size(80.dpScaled(), 80.dpScaled())
            )
            Box(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(24.dpScaled()))
        GenericText(
            text = resourceString(R.string.password_changed),
            fontSize = 24.spScaled(),
            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
            color = White,
            fontFamily = InterFontFamily
        )
        Spacer(modifier = Modifier.height(8.dpScaled()))
        GenericText(
            text = resourceString(R.string.password_changed_successfully_you_can_login_again_with_a_new_password),
            fontSize = 16.spScaled(),
            color = TextGreyscale500,
            fontFamily = InterFontFamily
        )
        Spacer(modifier = Modifier.weight(1f))
        GenericButton(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dpScaled())
                .padding(bottom = 24.dpScaled()),
            backgroundColor = ButtonPrimary,
            contentColor = TextOnAccent,
            content = {
                GenericText(
                    text = resourceString(R.string.login),
                    fontSize = 16.spScaled(),
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                    color = TextOnAccent,
                    fontFamily = InterFontFamily
                )
            }
        )
    }
}
