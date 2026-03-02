package com.example.frjarcustomer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.frjarcustomer.ui.theme.Screen_background_Root
import com.example.frjarcustomer.ui.theme.TextPrimary
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun OverlayLoader(){

    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {


        Box(
            Modifier
                .clip(RoundedCornerShape(5.sdp))
                .size(60.sdp)
                .background(Screen_background_Root), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = TextPrimary,
                strokeWidth = 2.sdp
            )

        }


    }
}