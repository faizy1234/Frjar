package com.example.frjarcustomer.ui.screen.dashedboard.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.frjarcustomer.ui.theme.AuthScreenBackground
import com.example.frjarcustomer.ui.theme.White

@Composable
fun FavouriteScreen(){
    Column(
        modifier = Modifier
            .background(AuthScreenBackground)
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("fav Screen")

    }
}