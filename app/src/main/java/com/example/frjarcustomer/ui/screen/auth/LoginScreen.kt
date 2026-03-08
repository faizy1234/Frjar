package com.example.frjarcustomer.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.resourceString
import com.example.frjarcustomer.image.CoilImage
import com.example.frjarcustomer.ui.components.AuthTextField
import com.example.frjarcustomer.ui.components.AuthToggle
import com.example.frjarcustomer.ui.components.GenericButton
import com.example.frjarcustomer.ui.components.GenericText
import com.example.frjarcustomer.ui.components.OverlayLoader
import com.example.frjarcustomer.ui.components.ValidationShakeState
import com.example.frjarcustomer.ui.theme.AuthScreenBackground
import com.example.frjarcustomer.ui.theme.ButtonPrimary
import com.example.frjarcustomer.ui.theme.ButtonPrimaryPressed
import com.example.frjarcustomer.ui.theme.InterFontFamily
import com.example.frjarcustomer.ui.theme.PoppinsFontFamily
import com.example.frjarcustomer.ui.theme.TextOnAccent
import com.example.frjarcustomer.ui.theme.TextPrimary
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun LoginScreen(
    moveToHome: () -> Unit,
    moveToSignUpOtp: (String) -> Unit,
    moveToForgetPassword: () -> Unit,
    moveToOtp: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    val mobileNumber by viewModel.mobileNumber.collectAsState()
    val validationShake by viewModel.validationShake.collectAsStateWithLifecycle(initialValue = ValidationShakeState())
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle(initialValue = false)
    val keyboardController = LocalSoftwareKeyboardController.current


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(AuthScreenBackground)
                .statusBarsPadding()
                .padding(horizontal = 12.sdp)
                .imePadding()
                .pointerInput(Unit) {
                    detectTapGestures { keyboardController?.hide() }
                }
        ) {
        Spacer(modifier = Modifier.height(20.sdp))

        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {



            CoilImage(
                url = R.drawable.img_header,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .clip(RoundedCornerShape(9.sdp))
                    .height(270.sdp)
            )


            CoilImage(
                url = R.drawable.ic_close_back,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.sdp, top = 8.sdp)
                    .align(Alignment.TopStart)
                    .height(34.sdp)
                    .width(26.sdp)
            )


        }

        Spacer(modifier = Modifier.height(20.sdp))


        GenericText(
            text = resourceString(R.string.lets_get_started_with_frjar),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 27.sdp),
            fontWeight = FontWeight.W600,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 18.ssp,
            color = ButtonPrimaryPressed,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.sdp))
        AuthToggle(
            titles = listOf(resourceString(R.string.login), resourceString(R.string.sign_up)),
            selectedIndex = selectedTabIndex,
            onTabSelected = viewModel::selectTab
        )
        Spacer(modifier = Modifier.height(17.sdp))
        AuthTextField(
            label = resourceString(R.string.mobile_number),
            value = mobileNumber,
            onValueChange = viewModel::setMobileNumber,
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
                        modifier = Modifier
                            .height(15.sdp)
                    )


                }
            },
            placeholder = resourceString(R.string.mobile_number_placeholder)
        )
        Spacer(modifier = Modifier.height(24.sdp))

        GenericButton(
            onClick = {
                viewModel.onContinue(
                    moveToOtp = moveToOtp,
                    moveToSignUpOtp = moveToSignUpOtp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(43.sdp),
            backgroundColor = ButtonPrimary,
            contentColor = TextOnAccent,
            content = {
                GenericText(
                    text = resourceString(R.string.continue_),
                    fontSize = 12.ssp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                    color = TextOnAccent
                )
            }
        )
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                OverlayLoader()
            }
        }
    }
}
