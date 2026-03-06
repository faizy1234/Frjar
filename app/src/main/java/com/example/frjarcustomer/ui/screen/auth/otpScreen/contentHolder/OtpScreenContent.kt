package com.example.frjarcustomer.ui.screen.auth.otpScreen.contentHolder

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Stable
@Immutable
data class OtpScreenContent(
    val messageText: Int?=null,
    val changeNumberText: Int?=null,
    val titleText: Int?=null,
    val primaryButtonText: Int?=null,
    val isSignup: Boolean=false,
    val number:String?=null ,
    val isEmailComing: Boolean=false,
    val email:String?=null

    ) : Parcelable
