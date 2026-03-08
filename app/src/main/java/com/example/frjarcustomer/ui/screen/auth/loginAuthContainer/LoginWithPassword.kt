package com.example.frjarcustomer.ui.screen.auth.loginAuthContainer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.resourceString
import com.example.frjarcustomer.image.CoilImage
import com.example.frjarcustomer.ui.components.AuthTextField
import com.example.frjarcustomer.ui.components.GenericButton
import com.example.frjarcustomer.ui.components.GenericText
import com.example.frjarcustomer.ui.components.ValidationShakeState
import com.example.frjarcustomer.ui.theme.AuthScreenBackground
import com.example.frjarcustomer.ui.theme.ButtonPrimary
import com.example.frjarcustomer.ui.theme.TextGreyscale500
import com.example.frjarcustomer.ui.theme.TextOnAccent
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun LoginWithPassword(
    mobileNumber: String,
    onMobileNumberChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    submitPressed: Boolean,
    validationShake: ValidationShakeState,
    onTogglePasswordVisible: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current


    Scaffold(
        modifier = Modifier
            .background(AuthScreenBackground)
            .fillMaxSize()
            .padding(horizontal = 9.sdp)
            .systemBarsPadding()
            .pointerInput(Unit) {
                detectTapGestures { keyboardController?.hide() }
            },
        containerColor = AuthScreenBackground,
        bottomBar = {
            GenericButton(
                onClick = onLoginClick,
                modifier = Modifier
                    .padding(bottom = 16.sdp)
                    .fillMaxWidth()
                    .height(43.sdp),
                backgroundColor = ButtonPrimary,
                contentColor = TextOnAccent,
                content = {
                    GenericText(
                        text = resourceString(R.string.login),
                        fontSize = 12.ssp,
                        color = TextOnAccent
                    )
                }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it),
            horizontalAlignment = Alignment.End

        ) {
            Spacer(modifier = Modifier.height(24.sdp))
            AuthTextField(
                label = resourceString(R.string.mobile_number),
                value = mobileNumber,
                onValueChange = onMobileNumberChange,
                placeholder = resourceString(R.string.mobile_number_placeholder),
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone,
                fieldIndex = 0,
                validationShake = validationShake,
                leadingIcon = {
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CoilImage(
                            url = R.drawable.ic_flag,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 12.sdp)
                                .height(15.sdp)
                                .width(23.sdp)
                        )
                        CoilImage(
                            url = R.drawable.ic_divider,
                            contentDescription = null,
                            modifier = Modifier.height(15.sdp)
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(24.sdp))
            AuthTextField(
                label = resourceString(R.string.password),
                value = password,
                onValueChange = onPasswordChange,
                placeholder = resourceString(R.string.input_your_password_account),
                isPassword = true,
                passwordVisible = passwordVisible,
                fieldIndex = 1,
                validationShake = validationShake,
                onTrailingIconClick = onTogglePasswordVisible,
                trailingIcon = {
                    CoilImage(

                        url = if (passwordVisible) R.drawable.ic_eye_on_input else R.drawable.ic_eye_off_input,
                        contentDescription = null,
                        modifier = Modifier.size(18.sdp)
                    )
                }
            )
            Spacer(modifier = Modifier.height(12.sdp))
            GenericText(
                text = resourceString(R.string.forgot_password),
                fontSize = 11.ssp,
                color = TextGreyscale500,
                modifier = Modifier
                    .clickable(onClick = onForgotPasswordClick)
            )

        }

    }
}
