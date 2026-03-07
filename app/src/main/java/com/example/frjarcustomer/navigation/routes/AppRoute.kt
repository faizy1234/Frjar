package com.example.frjarcustomer.navigation.routes

import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.OnboardingData
import com.example.frjarcustomer.ui.screen.auth.otpScreen.contentHolder.OtpScreenContent
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute {

    // --------------- Root / Intro graph ---------------
    @Serializable
    data object Splash : AppRoute

    @Serializable
    data class Onboarding(val pages : OnboardingData) : AppRoute

    // --------------- Auth graph ---------------



    @Serializable
    data class Login(val initialTab: Int = 0) : AppRoute
    @Serializable
    data object ReachOutScreen : AppRoute

    @Serializable
    data object SignUp : AppRoute

    @Serializable
    data class OtpScreen(val content: OtpScreenContent) : AppRoute


    @Serializable
    data class LoginContainer(val content: OtpScreenContent) : AppRoute



    @Serializable
    data class SecureAccount(val phoneNumber: String = "") : AppRoute

    @Serializable
    data class AddAddress(val phoneNumber: String = "") : AppRoute

    @Serializable
    data object ForgetPassword : AppRoute

    // --------------- Dashboard graph ---------------
    @Serializable
    data object Home : AppRoute

    @Serializable
    data object Search : AppRoute


    @Serializable
    data object Cart : AppRoute

    @Serializable
    data object Favourite : AppRoute




    @Serializable
    data class Detail(val itemId: String) : AppRoute

    @Serializable
    data object Profile : AppRoute

    @Serializable
    data object AppFeature : AppRoute

    @Serializable
    data class VersionExpire(val isForceUpdate: Boolean = false) : AppRoute

    @Serializable
    data object NoConnection : AppRoute
}