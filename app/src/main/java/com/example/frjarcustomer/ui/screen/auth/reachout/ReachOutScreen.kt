package com.example.frjarcustomer.ui.screen.auth.reachout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.frjarcustomer.image.CoilImage
import com.example.frjarcustomer.ui.components.GenericText
import com.example.frjarcustomer.ui.theme.TextBlackDarkTitle
import com.example.frjarcustomer.ui.theme.AuthScreenBackground
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.AppLanguage
import com.example.frjarcustomer.appstate.AppLanguage.Companion.getLanguageName
import com.example.frjarcustomer.appstate.LocalPaddingValues
import com.example.frjarcustomer.appstate.resourceString
import com.example.frjarcustomer.ui.components.ContactInfoItem
import com.example.frjarcustomer.ui.components.ContactInfoRow
import com.example.frjarcustomer.ui.components.GenericButton
import com.example.frjarcustomer.ui.components.ImageGalleryRow
import com.example.frjarcustomer.ui.components.OrDivider
import com.example.frjarcustomer.ui.screen.intro.splash.SplashViewModel
import com.example.frjarcustomer.ui.theme.ButtonPrimary
import com.example.frjarcustomer.ui.theme.DarkNavyBlue
import com.example.frjarcustomer.ui.theme.TextGreyscale500
import com.example.frjarcustomer.utils.LocaleManager
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp


@Composable
fun ReachOutScreen(
    onBackClick: () -> Unit = {},
    onPhoneClick: (String) -> Unit = {},
    onLocationClick: (Double, Double) -> Unit = { _, _ -> },
    onLanguageClick: () -> Unit = {},
    onCreateAccountClick: () -> Unit = {},
    onSignInClick: () -> Unit = {},
    sharedViewModel: SplashViewModel
) {
    val appSetting = sharedViewModel.essentialAppSetting.collectAsStateWithLifecycle().value

    val safeAreaPadding = LocalPaddingValues.current

    val contactItems = listOf(
        ContactInfoItem(
            iconRes = R.drawable.ic_dialer,
            title = appSetting.contactNo ?: "",
            subtitle = resourceString(R.string.reach_us_out_and_ask_questions),
            onClick = {
                appSetting.contactNo?.let { onPhoneClick.invoke(it) }
            }
        ),
        ContactInfoItem(
            iconRes = R.drawable.ic_location,
            title = appSetting.address ?: "",
            subtitle = resourceString(R.string.tap_to_view_on_google_maps),
            onClick = {
                onLocationClick.invoke(
                    appSetting.latitude ?: 0.0,
                    appSetting.longitude ?: 0.0
                )
            }
        ),
        ContactInfoItem(
            iconRes = R.drawable.ic_language,
            title = resourceString(R.string.language),
            subtitle = getLanguageName(
                LocaleManager.currentLanguage
                    ?: com.example.frjarcustomer.data.remote.utils.AppLanguage.ENGLISH.value
            ),
            onClick = onLanguageClick
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthScreenBackground)
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = safeAreaPadding.calculateBottomPadding())
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(Modifier.height(39.sdp))

            Box {
                ImageGalleryRow(
                    image = R.drawable.ic_reach_out_banner
                )

                Box(
                    modifier = Modifier
                        .padding(start = 9.sdp, top = 9.sdp)
                        .size(35.sdp)
                        .clip(CircleShape)
                        .background(Color.White.copy(0.8f))
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    CoilImage(
                        url = R.drawable.ic_close_back,
                        contentDescription = "back",
                        modifier = Modifier
                            .padding(start = 5.sdp)
                            .size(30.sdp),
                        contentScale = ContentScale.Fit,
                        mirrorInRtl = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.sdp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.sdp)
            ) {
                GenericText(
                    text = resourceString(R.string.reach_out_to_us),

                    color = TextBlackDarkTitle,
                    fontSize = 15.ssp,
                    fontWeight = FontWeight.SemiBold,
                )

                Spacer(modifier = Modifier.height(15.sdp))


                contactItems.forEach { item ->
                    ContactInfoRow(item = item)

                }


//            Spacer(modifier = Modifier.height(10.dp))

                OrDivider()

                Spacer(modifier = Modifier.height(18.sdp))

                GenericButton(
                    onClick = onCreateAccountClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(43.sdp),
                    backgroundColor = ButtonPrimary,
                    contentColor = TextBlackDarkTitle,
                    shape = RoundedCornerShape(6.sdp)
                ) {
                    GenericText(
                        text = resourceString(R.string.create_account),
                        color = TextBlackDarkTitle,
                        fontSize = 12.ssp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                Spacer(modifier = Modifier.height(14.sdp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    GenericText(
                        text = resourceString(R.string.already_a_member),
                        color = TextGreyscale500,
                        fontSize = 11.ssp,
                        fontWeight = FontWeight.Normal
                    )
                    GenericText(
                        text = resourceString(R.string.sign_in),
                        color = DarkNavyBlue,
                        fontSize = 11.ssp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.clickable { onSignInClick() }
                    )
                }



                Spacer(modifier = Modifier.height(25.sdp))
            }
        }
    }
}