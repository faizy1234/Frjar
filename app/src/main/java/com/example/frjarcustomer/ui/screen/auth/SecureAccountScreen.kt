package com.example.frjarcustomer.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.resourceString
import com.example.frjarcustomer.image.CoilImage
import com.example.frjarcustomer.ui.components.AuthTextField
import com.example.frjarcustomer.ui.components.GenericButton
import com.example.frjarcustomer.ui.components.GenericText
import com.example.frjarcustomer.ui.components.OverlayLoader
import com.example.frjarcustomer.ui.components.ValidationShakeState
import com.example.frjarcustomer.ui.theme.AuthScreenBackground
import com.example.frjarcustomer.ui.theme.ButtonPrimary
import com.example.frjarcustomer.ui.theme.ButtonSecondary
import com.example.frjarcustomer.ui.theme.TextBlackDark
import com.example.frjarcustomer.ui.theme.TextOnAccent
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun SecureAccountScreen(
    onBackPress: () -> Unit,
    onNext: (String, String, String) -> Unit,
    onSkip: () -> Unit,
    viewModel: SecureAccountViewModel = hiltViewModel()
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val passwordVisible by viewModel.passwordVisible.collectAsState()
    val confirmPasswordVisible by viewModel.confirmPasswordVisible.collectAsState()
    val validationShake by viewModel.validationShake.collectAsStateWithLifecycle(initialValue = ValidationShakeState())
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle(initialValue = false)
    val keyboardController = LocalSoftwareKeyboardController.current


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AuthScreenBackground)
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .pointerInput(Unit) {
                    detectTapGestures { keyboardController?.hide() }
                }
                .padding(horizontal = 18.sdp),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(20.sdp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.sdp),
                contentAlignment = Alignment.Center
            ) {

                CoilImage(
                    url = R.drawable.ic_close_back,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .height(34.sdp)
                        .width(26.sdp)
                        .clickable { onBackPress() }
                )

                CoilImage(
                    url = R.drawable.ic_secure_banner,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(96.sdp)
                )
            }
            Spacer(modifier = Modifier.height(18.sdp))
            GenericText(
                text = resourceString(R.string.ready_to_secure_your_account),
                fontSize = 18.ssp,
                fontWeight = FontWeight.Medium,
                color = TextBlackDark,
                style = MaterialTheme.typography.bodyLarge,


                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.sdp)
            )
            Spacer(modifier = Modifier.height(33.sdp))


            AuthTextField(
                label = resourceString(R.string.email),
                value = email,
                isOptionalHeader = true,
                onValueChange = viewModel::setEmail,
                placeholder = resourceString(R.string.example_email),
                fieldIndex = 0,
                validationShake = validationShake
            )
            Spacer(modifier = Modifier.height(12.sdp))

            AuthTextField(
                label = resourceString(R.string.password),
                value = password,
                onValueChange = viewModel::setPassword,
                placeholder = resourceString(R.string.input_your_password_account),
                isPassword = true,
                passwordVisible = passwordVisible,
                onTrailingIconClick = { viewModel.togglePasswordVisible() },
                fieldIndex = 1,
                validationShake = validationShake,
                trailingIcon = {
                    CoilImage(
                        url = if (passwordVisible) R.drawable.ic_eye_on_input else R.drawable.ic_eye_off_input,
                        contentDescription = null,
                        modifier = Modifier.size(18.sdp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.sdp))
            AuthTextField(
                label = resourceString(R.string.confirm_password),
                value = confirmPassword,
                onValueChange = viewModel::setConfirmPassword,
                placeholder = resourceString(R.string.input_your_password_account),
                isPassword = true,
                passwordVisible = confirmPasswordVisible,
                onTrailingIconClick = { viewModel.toggleConfirmPasswordVisible() },
                fieldIndex = 2,
                validationShake = validationShake,
                trailingIcon = {
                    CoilImage(
                        url = if (confirmPasswordVisible) R.drawable.ic_eye_on_input else R.drawable.ic_eye_off_input,
                        contentDescription = null,
                        modifier = Modifier.size(18.sdp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(37.sdp))
            GenericButton(
                onClick = { viewModel.onNextClick { onNext(email, password, confirmPassword) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(43.sdp),
                backgroundColor = ButtonPrimary,
                contentColor = TextOnAccent,
                content = {
                    GenericText(
                        text = resourceString(R.string.next),
                        fontSize = 12.ssp,
                        fontWeight = FontWeight.Medium,
                        color = ButtonSecondary,
                    )
                }
            )
            Spacer(modifier = Modifier.height(18.sdp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onSkip),
                horizontalArrangement = Arrangement.Center
            ) {
                GenericText(
                    text = resourceString(R.string.skip),
                    fontSize = 12.ssp,
                    fontWeight = FontWeight.Medium,
                    color = ButtonSecondary,
                )
            }
            Spacer(modifier = Modifier.height(25.sdp))
        }
        if (isLoading) {
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
