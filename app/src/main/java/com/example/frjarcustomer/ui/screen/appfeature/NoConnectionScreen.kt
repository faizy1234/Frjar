package com.example.frjarcustomer.ui.screen.appfeature

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
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
import com.example.frjarcustomer.ui.theme.Screen_background_Root
import com.example.frjarcustomer.ui.theme.TextOnAccent
import com.example.frjarcustomer.ui.theme.TextOnLightgray
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import androidx.compose.ui.text.font.FontWeight
import com.example.frjarcustomer.image.CoilImage
import com.example.frjarcustomer.ui.theme.White

@Composable
fun NoConnectionScreen(
    onTryAgainClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(102.sdp))
            CoilImage(
                url = R.drawable.ic_no_connection,
                contentDescription = null,
                modifier = Modifier.size(174.sdp),
                animate = true
            )
            Spacer(modifier = Modifier.height(34.sdp))
            GenericText(
                text = resourceString(R.string.no_connection),
                color = ButtonSecondary,
                fontSize = 16.ssp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 46.sdp)
            )

            Spacer(modifier = Modifier.height(10.sdp))

            GenericText(
                text = resourceString(R.string.theres_no_internet_connections_please_check_your_internet),
                color = TextOnLightgray.copy(0.60f),
                fontSize = 10.ssp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(horizontal = 33.sdp).fillMaxWidth().padding(horizontal = 16.sdp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(36.sdp))
            GenericButton(
                onClick = onTryAgainClick,
                backgroundColor = ButtonPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 82.sdp)
                    .height(44.sdp),

                ) {

                GenericText(
                    text = resourceString(R.string.try_again),
                    color = TextOnAccent,
                    fontWeight = FontWeight.W500,
                    fontSize = 12.ssp,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.height(32.sdp))
        }
    }
}
