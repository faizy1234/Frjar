package com.example.frjarcustomer.ui.screen.appfeature

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.resourceString
import com.example.frjarcustomer.ui.components.GenericButton
import com.example.frjarcustomer.ui.components.GenericText
import com.example.frjarcustomer.ui.theme.ButtonOutlineBorder
import com.example.frjarcustomer.ui.theme.ButtonPrimary
import com.example.frjarcustomer.ui.theme.ButtonSecondary
import com.example.frjarcustomer.ui.theme.InterFontFamily
import com.example.frjarcustomer.ui.theme.TextOnAccent
import com.example.frjarcustomer.ui.theme.TextOnLightgray
import com.example.frjarcustomer.ui.theme.White
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.OnboardingData
import com.example.frjarcustomer.image.CoilImage
import com.example.frjarcustomer.ui.components.OverlayLoader
import com.example.frjarcustomer.ui.screen.intro.splash.SplashDestination
import com.example.frjarcustomer.ui.screen.intro.splash.SplashViewModel
import com.example.frjarcustomer.ui.theme.Screen_background_Root

@Composable
fun VersionExpireScreen(
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    onUpdateClick: (String) -> Unit = {},
    isForceUpdate: Boolean = false,
    viewModel: SplashViewModel,
    moveToOnboarding: (OnboardingData) -> Unit = {},
    moveToAuth: () -> Unit = {},


    ) {

    val isLoading by viewModel.isDataLoaded.collectAsStateWithLifecycle()
    val localizedMessage = viewModel.essentialAppSetting.collectAsStateWithLifecycle()

    val showNoConnection by viewModel.showNoConnection.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.destination.collect { dest ->
            when (dest) {
                is SplashDestination.OnboardingDestination -> moveToOnboarding(dest.onboardingData)
                is SplashDestination.Auth -> moveToAuth()

                else -> {}
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showNoConnection) {
                NoConnectionScreen(onTryAgainClick = viewModel::checkOnboardingStatus)
            } else {
                Spacer(modifier = Modifier.height(102.sdp))
                CoilImage(
                    url = R.drawable.ic_update_logo,
                    contentDescription = null,
                    modifier = Modifier.size(width = 199.sdp, height = 169.sdp),
                    animate = true
                )
                Spacer(modifier = Modifier.height(37.sdp))

                GenericText(
                    text = resourceString(R.string.this_version_will_expire_soon),
                    color = ButtonSecondary,
                    fontSize = 16.ssp,
                    fontWeight = FontWeight.W500,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 41.sdp)
                )
                Spacer(modifier = Modifier.height(10.sdp))
                localizedMessage.value.appUpdateMessageLocalized?.let {
                    GenericText(
                        text = it,
                        color = TextOnLightgray.copy(0.60f),
                        fontSize = 10.ssp,
                        fontWeight = FontWeight.W400,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(horizontal = 33.sdp)
                            .fillMaxWidth()
                            .padding(horizontal = 20.sdp),
                        textAlign = TextAlign.Center
                    )
                }


                Spacer(modifier = Modifier.height(36.sdp))
                GenericButton(
                    onClick = {
                        localizedMessage.value.androidCustomerUrl?.let { onUpdateClick.invoke(it) }
                    },
                    backgroundColor = ButtonPrimary,
                    modifier = Modifier
                        .padding(horizontal = 82.sdp)
                        .fillMaxWidth()
                        .height(43.sdp),

                    ) {

                    GenericText(
                        text = resourceString(R.string.update),
                        color = TextOnAccent,
                        fontSize = 12.ssp,
                        fontWeight = FontWeight.W500,
                    )
                }


                Spacer(modifier = Modifier.height(25.sdp))

                if (!isForceUpdate) {
                    GenericText(
                        text = resourceString(R.string.close),
                        color = ButtonSecondary,
                        fontSize = 12.ssp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.clickable(onClick = onCloseClick)
                    )
                    Spacer(modifier = Modifier.height(25.sdp))
                }
                GenericText(
                    text = resourceString(R.string.your_updates_will_be_free_of_charge),
                    fontSize = 10.ssp,
                    color = TextOnLightgray.copy(0.60f),
                    fontWeight = FontWeight.W400,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 53.sdp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.sdp))
            }

        }




        if (isLoading) {
            OverlayLoader()
        }

    }
}
