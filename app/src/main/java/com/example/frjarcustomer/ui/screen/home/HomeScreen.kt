package com.example.frjarcustomer.ui.screen.home

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

@Composable
fun HomeScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToProfile: (userId: String, tab: Int) -> Unit,
    onNavigateToAppFeature: () -> Unit = {},
    onNavigateToVersionExpire: () -> Unit = {},
    onNavigateToNoConnection: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to home screen",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { onNavigateToDetail("item-1") }) {
            Text("Open Detail (item-1)")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { onNavigateToProfile("user-1", 0) }) {
            Text("Open Profile")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onNavigateToAppFeature) {
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
