package com.example.frjarcustomer.ui.screen.auth

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    onBack: () -> Unit,
    moveToSignUpOtp: (String) -> Unit,
    moveToForgetPassword: () -> Unit,
    moveToOtp: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) viewModel.onLocationPermissionGranted()
    }
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.onLocationPermissionGranted()
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    val mobileNumber by viewModel.mobileNumber.collectAsState()
    val validationShake by viewModel.validationShake.collectAsStateWithLifecycle(initialValue = ValidationShakeState())
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle(initialValue = false)
    val keyboardController = LocalSoftwareKeyboardController.current

    // Disable autofill on this screen so "0" or focus doesn't trigger phone/OTP auto-paste on some devices
    DisposableEffect(Unit) {
        val activity = context as? android.app.Activity
        val decorView = activity?.window?.decorView
        val previous = decorView?.importantForAutofill ?: View.IMPORTANT_FOR_AUTOFILL_AUTO
        decorView?.importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
        onDispose {
            decorView?.importantForAutofill = previous
        }
    }

    Box(
        modifier = Modifier
            .background(AuthScreenBackground)
            .fillMaxSize()
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState(

                ))
                .fillMaxSize()
                .padding(horizontal = 12.sdp)
                .padding(bottom = 24.sdp)
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
                        .height(265.sdp)
                )


                CoilImage(
                    url = R.drawable.ic_close_back,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 8.sdp, top = 8.sdp)
                        .align(Alignment.TopStart)
                        .height(34.sdp)
                        .width(26.sdp)
                        .clickable {
                            onBack.invoke()
                        },
                    mirrorInRtl = true
                )


            }

            Spacer(modifier = Modifier.height(20.sdp))


            GenericText(
                text = resourceString(R.string.lets_get_started_with_frjar),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 27.sdp),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineLarge,
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
                modifier = Modifier            .imePadding()
                ,
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
                placeholder = resourceString(R.string.mobile_number)
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
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
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
