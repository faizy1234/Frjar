package com.example.frjarcustomer.ui.screen.auth

import android.location.Geocoder
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.resourceString
import com.example.frjarcustomer.image.CoilImage
import com.example.frjarcustomer.ui.components.AuthTextField
import com.example.frjarcustomer.ui.components.GenericButton
import com.example.frjarcustomer.ui.components.GenericText
import com.example.frjarcustomer.ui.theme.AuthBorder
import com.example.frjarcustomer.ui.theme.AuthScreenBackground
import com.example.frjarcustomer.ui.theme.ButtonPrimary
import com.example.frjarcustomer.ui.theme.InterFontFamily
import com.example.frjarcustomer.ui.theme.TextColorAr
import com.example.frjarcustomer.ui.theme.TextGreyscale500
import com.example.frjarcustomer.ui.theme.TextOnAccent
import com.example.frjarcustomer.ui.theme.TextPrimary
import com.example.frjarcustomer.ui.util.dpScaled
import com.example.frjarcustomer.ui.util.spScaled
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import java.util.Locale

@Composable
fun AddAddressScreen(
    phoneNumber: String,
    onBackPress: () -> Unit,
    onNext: () -> Unit,
    viewModel: AddAddressViewModel = hiltViewModel(),
) {
    val address by viewModel.address.collectAsState()
    val showProductsByAddress by viewModel.showProductsByAddress.collectAsState()
    val selectedLatLng by viewModel.selectedLatLng.collectAsState()
    val isLoadingAddress by viewModel.isLoadingAddress.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(selectedLatLng, 15f)
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            viewModel.onMapDragEnd(cameraPositionState.position.target)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthScreenBackground)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.sdp),
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
                url = R.drawable.ic_location_banner,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(150.sdp)
            )
        }

        Spacer(modifier = Modifier.height(18.sdp))
        GenericText(
            text = resourceString(R.string.add_your_address),
            fontSize = 18.ssp,
            color = TextPrimary,
            fontWeight = FontWeight.W500,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.sdp)
        )
        Spacer(modifier = Modifier.height(12.sdp))
        AuthTextField(
            label = resourceString(R.string.select_your_address),
            value = if (isLoadingAddress) "Loading..." else address,
            onValueChange = viewModel::setAddress,
            placeholder = resourceString(R.string.example_riyadh),
            enabled = !isLoadingAddress
        )
        Spacer(modifier = Modifier.height(16.dpScaled()))

        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.sdp)
                .clip(RoundedCornerShape(10.sdp))
                .background(TextGreyscale500.copy(alpha = 0.12f)),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = true,
                myLocationButtonEnabled = false
            ),
            properties = MapProperties(isMyLocationEnabled = false)
        ) {
            Marker(
                state = MarkerState(position = selectedLatLng),
                title = address.ifEmpty { "Selected Location" }
            )
        }


        Spacer(modifier = Modifier.height(15.sdp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 10.sdp, end = 5.sdp)
                    .size(16.sdp)
                    .clip(CircleShape)
                    .border(
                        width = 1.5.dp,
                        color = if (showProductsByAddress) ButtonPrimary else AuthBorder,
                        shape = CircleShape
                    )
                    .background(if (showProductsByAddress) ButtonPrimary else Color.White)
                    .clickable { viewModel.setShowProductsByAddress(!showProductsByAddress) },
                contentAlignment = Alignment.Center
            ) {
                if (showProductsByAddress) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.sdp)
                    )
                }
            }
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            color = TextColorAr
                        )
                    ) {
                        append(resourceString(R.string.select_if_you_want_to_show_products) + " ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.W600,
                            color = TextPrimary
                        )
                    ) {
                        append(resourceString(R.string.based_on_your_address))
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            color = TextColorAr
                        )
                    ) {
                        append(" " + resourceString(R.string.you_can_change_it_later_from_address_in_user_profile))
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 10.ssp,
                color = TextColorAr,
            )
        }

        Spacer(modifier = Modifier.height(37.sdp))
        GenericButton(
            onClick = { onNext() },
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
                    color = TextOnAccent,
                )
            }
        )
    }
}