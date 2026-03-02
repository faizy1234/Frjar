package com.example.frjarcustomer.ui.screen.appfeature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.resourceString
import com.example.frjarcustomer.image.CoilImage
import com.example.frjarcustomer.ui.components.GenericButton
import com.example.frjarcustomer.ui.components.GenericText
import com.example.frjarcustomer.ui.screen.intro.splash.SplashViewModel
import com.example.frjarcustomer.ui.theme.ButtonPrimary
import com.example.frjarcustomer.ui.theme.ButtonSecondary
import com.example.frjarcustomer.ui.theme.Screen_background_Root
import com.example.frjarcustomer.ui.theme.TextOnAccent
import com.example.frjarcustomer.ui.theme.TextOnLightgray
import com.example.frjarcustomer.ui.theme.TextSecondary
import com.example.frjarcustomer.ui.theme.White
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun AppFeatureScreen(
    onContactClick: () -> Unit,
    viewModel: SplashViewModel,
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(74.sdp))
            CoilImage(
                url = R.drawable.ic_oval_tick,
                contentDescription = null,
                modifier = Modifier.size(150.sdp),
                animate = true
            )
            Spacer(modifier = Modifier.height(16.sdp))
            CoilImage(
                url = R.drawable.ic_service_person,
                contentDescription = null,
                modifier = Modifier.size(width = 91.sdp,height = 110.sdp),
            )
            Spacer(modifier = Modifier.height(31.sdp))

            GenericText(
                text = resourceString(R.string.new_features_and_bugs_fixed),
                color = ButtonSecondary,
                fontSize = 15.ssp,
                fontWeight = FontWeight.W500,
                style =MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 40.sdp).fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.sdp))
            GenericText(
                text = resourceString(R.string.we_have_added_new_features__fixed_bugs_to_make_your_experience_smooth_as_possible),
                color = TextOnLightgray.copy(0.60f),
                fontSize = 10.ssp,
                fontWeight = FontWeight.W400,
                style =MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 33.sdp).fillMaxWidth().padding(horizontal = 20.sdp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(23.sdp))
            GenericButton(
                onClick = onContactClick,
                backgroundColor = ButtonPrimary,
                modifier = Modifier.padding(horizontal = 82.sdp).fillMaxWidth().height(44.sdp),

            ) {
                GenericText(
                    text = resourceString(R.string.contact),
                    color =TextOnAccent ,
                    fontSize = 12.ssp,
                    fontWeight = FontWeight.W500,
                    )
            }
        }
    }
}
