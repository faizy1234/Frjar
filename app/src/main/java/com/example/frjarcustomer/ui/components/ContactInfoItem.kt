package com.example.frjarcustomer.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.Alignment
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
import com.example.frjarcustomer.R
import com.example.frjarcustomer.ui.theme.AuthBorderNew
import com.example.frjarcustomer.ui.theme.AuthScreenBackground
import com.example.frjarcustomer.ui.theme.White
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp


data class ContactInfoItem(
    @DrawableRes val iconRes: Int,
    val title: String,
    val subtitle: String,
    val onClick: () -> Unit = {}
)

@Composable
fun ImageGalleryRow(
    image: Any,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(377.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 16.sdp,
                    topEnd = 0.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
    ) {
        CoilImage(
            url = image,
            contentDescription = "property image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
fun ContactInfoRow(
    item: ContactInfoItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding( bottom = 15.sdp)
            .fillMaxWidth()
            .clickable { item.onClick() }
            ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(35.sdp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = AuthBorderNew,
                    shape = CircleShape
                )
                .background(AuthScreenBackground),
            contentAlignment = Alignment.Center
        ) {
            CoilImage(
                url = item.iconRes,
                contentDescription = item.title,
                modifier = Modifier.size(17.sdp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.width(12.sdp))

        Column(modifier = Modifier.weight(1f)) {
            GenericText(
                text = item.title,
                color = TextBlackDarkTitle,
                fontSize = 11.ssp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(2.dp))
            GenericText(
                text = item.subtitle,
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 18.sp
            )
        }

        CoilImage(
            url = R.drawable.ic_arrow_right,
            contentDescription = "arrow",
            modifier = Modifier.size(14.sdp),
            contentScale = ContentScale.Fit
        )
    }
}


@Composable
fun OrDivider(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = TextTertiary,
            thickness = 1.dp
        )
        GenericText(
            text = "OR",
            modifier = Modifier.padding(horizontal = 12.dp),
            color = TextTertiary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.sp
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = TextTertiary,
            thickness = 1.dp
        )
    }
}
