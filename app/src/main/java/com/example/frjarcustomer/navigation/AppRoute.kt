package com.example.frjarcustomer.navigation

import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.OnboardingData
import kotlinx.serialization.Serializable

/**
 * Type-safe navigation routes with nested graphs:
 * - Root (Intro): Splash
 * - Auth: Login, SignUp, Otp, ForgetPassword
 * - Dashboard: Home, Detail, Profile
 */
@Serializable
sealed interface AppRoute {

    // --------------- Root / Intro graph ---------------
    @Serializable
    @kotlinx.serialization.SerialName("splash")
    data object Splash : AppRoute

    @Serializable
    @kotlinx.serialization.SerialName("onboarding")
    data class Onboarding(val pages :OnboardingData) : AppRoute

    // --------------- Auth graph ---------------
    @Serializable
    @kotlinx.serialization.SerialName("login")
    data object Login : AppRoute

    @Serializable
    @kotlinx.serialization.SerialName("signup")
    data object SignUp : AppRoute

    @Serializable
    @kotlinx.serialization.SerialName("otp")
    data class Otp(
        val userId: String = "",
        val userEmail: String = "",
        val isForgetPassword: Boolean = false,
        val isDeleteAccount: Boolean = false
    ) : AppRoute

    @Serializable
    @kotlinx.serialization.SerialName("forget_password")
    data object ForgetPassword : AppRoute

    // --------------- Dashboard graph ---------------
    @Serializable
    @kotlinx.serialization.SerialName("home")
    data object Home : AppRoute

    @Serializable
    @kotlinx.serialization.SerialName("detail")
    data class Detail(val itemId: String) : AppRoute

    @Serializable
    @kotlinx.serialization.SerialName("profile")
    data class Profile(val userId: String, val tab: Int = 0) : AppRoute

    @Serializable
    @kotlinx.serialization.SerialName("app_feature")
    data object AppFeature : AppRoute

    @Serializable
    @kotlinx.serialization.SerialName("version_expire")
    data class VersionExpire(val isForceUpdate: Boolean = false) : AppRoute

    @Serializable
    @kotlinx.serialization.SerialName("no_connection")
    data object NoConnection : AppRoute
}
