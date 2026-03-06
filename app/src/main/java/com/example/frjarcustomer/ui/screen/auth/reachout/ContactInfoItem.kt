package com.example.frjarcustomer.ui.screen.auth.reachout

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frjarcustomer.image.CoilImage
import com.example.frjarcustomer.ui.components.GenericText
import com.example.frjarcustomer.ui.theme.TextBlackDarkTitle
import com.example.frjarcustomer.ui.theme.TextSecondary
import com.example.frjarcustomer.ui.theme.TextTertiary
import com.example.frjarcustomer.ui.theme.AuthScreenBackground
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.LocalPaddingValues
import com.example.frjarcustomer.appstate.resourceString
import com.example.frjarcustomer.ui.components.ContactInfoItem
import com.example.frjarcustomer.ui.components.ContactInfoRow
import com.example.frjarcustomer.ui.components.GenericButton
import com.example.frjarcustomer.ui.components.ImageGalleryRow
import com.example.frjarcustomer.ui.components.OrDivider
import com.example.frjarcustomer.ui.theme.AuthBorderNew
import com.example.frjarcustomer.ui.theme.ButtonPrimary
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp


@Composable
fun ReachOutScreen(
    onBackClick: () -> Unit = {},
    onPhoneClick: () -> Unit = {},
    onLocationClick: () -> Unit = {},
    onLanguageClick: () -> Unit = {},
    onCreateAccountClick: () -> Unit = {},
    onSignInClick: () -> Unit = {}
) {

    val safeAreaPadding = LocalPaddingValues.current


    val contactItems = listOf(
        ContactInfoItem(
            iconRes = R.drawable.ic_phone,
            title = "+966126053950",
            subtitle = "",
            onClick = onPhoneClick
        ),
        ContactInfoItem(
            iconRes = R.drawable.ic_launcher_foreground,
            title = "King Abdulaziz Branch Rd, Ash Shati",
            subtitle = "",
            onClick = onLocationClick
        ),
        ContactInfoItem(
            iconRes = R.drawable.ic_phone,
            title = "Language",
            subtitle = "English",
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
                        .padding(start = 12.dp, top = 12.dp)
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
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
            ) {
            GenericText(
                text = resourceString(R.string.reach_out_to_us),

                color = TextBlackDarkTitle,
                fontSize = 16.ssp,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(modifier = Modifier.height(20.dp))


            contactItems.forEach{  item ->
                ContactInfoRow(item = item)

            }


            Spacer(modifier = Modifier.height(28.dp))

            OrDivider()

            Spacer(modifier = Modifier.height(24.dp))

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

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                GenericText(
                    text = "Already a member? ",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
                GenericText(
                    text = "Sign In",
                    color = TextBlackDarkTitle,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onSignInClick() }
                )
            }



            Spacer(modifier = Modifier.height(32.dp))
        }
        }
    }
}