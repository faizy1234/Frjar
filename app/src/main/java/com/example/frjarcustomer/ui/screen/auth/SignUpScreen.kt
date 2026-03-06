package com.example.frjarcustomer.ui.screen.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SignUpScreen(
    onBackPress: () -> Unit,
    onSignup: (userId: String, userEmail: String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize())
}
