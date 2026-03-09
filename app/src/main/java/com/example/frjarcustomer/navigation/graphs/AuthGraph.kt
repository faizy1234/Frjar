package com.example.frjarcustomer.navigation.graphs

import android.content.Context
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.frjarcustomer.R
import com.example.frjarcustomer.navigation.routes.AppRoute
import com.example.frjarcustomer.navigation.utils.CustomNavType
import com.example.frjarcustomer.navigation.utils.navigateToHome
import com.example.frjarcustomer.ui.screen.auth.AddAddressScreen
import com.example.frjarcustomer.ui.screen.auth.ForgetPasswordScreen
import com.example.frjarcustomer.ui.screen.auth.LoginScreen
import com.example.frjarcustomer.ui.screen.auth.OtpViewModel
import com.example.frjarcustomer.ui.screen.auth.SecureAccountScreen
import com.example.frjarcustomer.ui.screen.auth.loginAuthContainer.LoginAuthContainer
import com.example.frjarcustomer.ui.screen.auth.loginAuthContainer.LoginViewModel
import com.example.frjarcustomer.ui.screen.auth.otpScreen.OtpScreen
import com.example.frjarcustomer.ui.screen.auth.otpScreen.contentHolder.OtpScreenContent
import com.example.frjarcustomer.ui.screen.auth.reachout.ReachOutScreen
import com.example.frjarcustomer.ui.screen.intro.splash.SplashViewModel
import com.example.frjarcustomer.ui.screen.language.LanguageSelectionScreen
import com.example.frjarcustomer.utils.openDialer
import com.example.frjarcustomer.utils.openMapLocation
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
object AuthGraph

fun NavGraphBuilder.navigationAuthGraph(
    navHostController: NavHostController,
    sharedViewModel: SplashViewModel,
    context: Context,
) {
    navigation<AuthGraph>(startDestination = AppRoute.ReachOutScreen::class) {

        composable<AppRoute.ReachOutScreen> {
            ReachOutScreen(
                sharedViewModel = sharedViewModel,
                onBackClick = { navHostController.navigateUp() },
                onPhoneClick = {
                    openDialer(context, it)
                },
                onLocationClick = { latitude, longitude ->
                    openMapLocation(context, latitude, longitude)
                },
                onLanguageClick = {
//                    navHostController.navigate(AppRoute.LanguageSelection)
                                  },
                onCreateAccountClick = { navHostController.navigate(AppRoute.Login(initialTab = 1)) },
                onSignInClick = { navHostController.navigate(AppRoute.Login(initialTab = 0)) }
            )
        }

        composable<AppRoute.Login> {
            LoginScreen(
                onBack = { navHostController.navigateUp() },
                moveToSignUpOtp = { phoneNumber ->
                    navHostController.navigate(
                        AppRoute.OtpScreen(
                            content = OtpScreenContent(
                                number = phoneNumber,
                                isSignup = true,
                                titleText = R.string.enter_the_4digit_otp,
                                messageText = R.string.we_have_sent_a_verification_code_to_phone_number,
                                changeNumberText = R.string.change_number,
                                primaryButtonText = R.string.sign_up,

                                )
                        )
                    )
                },
                moveToForgetPassword = { navHostController.navigate(AppRoute.ForgetPassword) },
                moveToOtp = { phoneNumber ->
                    navHostController.navigate(
                        AppRoute.LoginContainer(
                            content = OtpScreenContent(
                                number = phoneNumber, isSignup = false,
                                titleText = R.string.enter_the_4digit_otp,
                                messageText = R.string.we_have_sent_a_verification_code_to_phone_number,
                                changeNumberText = R.string.change_number,
                                primaryButtonText = R.string.sign_in,
                            )
                        )
                    )
                }
            )
        }
        composable<AppRoute.OtpScreen>(
            typeMap = mapOf(
                typeOf<OtpScreenContent>() to CustomNavType(
                    OtpScreenContent::class.java,
                    OtpScreenContent.serializer()
                ),
            )
        ) {
            val otpViewModel: OtpViewModel = hiltViewModel()
            OtpScreen(
                onChangeNumberClick = { navHostController.navigateUp() },
                onResend = { otpViewModel.resendOtp() },
                onPrimaryClick = {
                    otpViewModel.validateOtpAndProceed {
                        navHostController.navigate(AppRoute.SecureAccount(phoneNumber = otpViewModel.otpScreenContent.number.orEmpty()))
                    }
                },
                viewModel = otpViewModel
            )
        }
        composable<AppRoute.LoginContainer>(
            typeMap = mapOf(
                typeOf<OtpScreenContent>() to CustomNavType(
                    OtpScreenContent::class.java,
                    OtpScreenContent.serializer()
                ),
            )

        ) {
            val otpVm: OtpViewModel = hiltViewModel()
            val loginVm: LoginViewModel = hiltViewModel()
            LoginAuthContainer(
                onOtpVerified = { navigateToHome(navHostController) },
                otpVm = otpVm,
                loginVm = loginVm,
                onBackClick = {
                    navHostController.navigateUp()
                },
                onForgotPassword = { navHostController.navigate(AppRoute.ForgetPassword) },
            )
        }
        composable<AppRoute.SecureAccount> { backStackEntry ->
            SecureAccountScreen(
                onBackPress = { navHostController.navigateUp() },
                onNext = { _, _, _ ->
                    navigateToHome(navHostController)
                },
                onSkip = {
                    navigateToHome(navHostController)
                }
            )
        }
        composable<AppRoute.AddAddress> { backStackEntry ->
            val route = backStackEntry.toRoute<AppRoute.AddAddress>()
            AddAddressScreen(
                phoneNumber = route.phoneNumber,
                onBackPress = { navHostController.navigateUp() },
                onNext = { navigateToHome(navHostController) }
            )

        }
        composable<AppRoute.ForgetPassword> {
            ForgetPasswordScreen(
                onBackPress = { navHostController.navigateUp() },
                onLoginClick = { navHostController.navigateUp() }
            )
        }

        composable<AppRoute.LanguageSelection> {
            val viewModel: com.example.frjarcustomer.ui.screen.language.LanguageSelectionViewModel = hiltViewModel()
            LanguageSelectionScreen(
                viewModel = viewModel,
                onBack = { navHostController.navigateUp() }
            )
        }
    }
}