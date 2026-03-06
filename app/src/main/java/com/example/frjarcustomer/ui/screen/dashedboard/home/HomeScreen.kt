package com.example.frjarcustomer.ui.screen.dashedboard.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.MessageContent
import com.example.frjarcustomer.appstate.MessageType
import com.example.frjarcustomer.appstate.SnackbarController
import com.example.frjarcustomer.appstate.SnackbarDuration
import com.example.frjarcustomer.appstate.SnackbarModel
import com.example.frjarcustomer.appstate.resourceString

@Composable
fun HomeScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToProfile: (userId: String, tab: Int) -> Unit,
    onNavigateToAppFeature: () -> Unit = {},
    onNavigateToVersionExpire: () -> Unit = {},
    onNavigateToNoConnection: () -> Unit = {},

    onNavigateToAuth: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onNavigateToAuth) {
            Text(resourceString(R.string.auth))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick ={
            SnackbarController.show(
                SnackbarModel(
                    type = MessageType.SUCCESS,
                    message = MessageContent.PlainString("Copied to clipboard"),
                    duration = SnackbarDuration.SHORT
                )
            )
        }) {
            Text("New Features")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onNavigateToVersionExpire) {
            Text("Version Expire")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onNavigateToNoConnection) {
            Text("No Connection")
        }
    }
}
