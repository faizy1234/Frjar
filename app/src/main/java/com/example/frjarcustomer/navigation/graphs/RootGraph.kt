package com.example.frjarcustomer.navigation.graphs

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.OnboardingData
import com.example.frjarcustomer.data.remote.utils.AppUpdateStatus
import com.example.frjarcustomer.navigation.routes.AppRoute
import com.example.frjarcustomer.navigation.utils.CustomNavType
import com.example.frjarcustomer.navigation.utils.navigateToAuth
import com.example.frjarcustomer.navigation.utils.navigateToHome
import com.example.frjarcustomer.navigation.utils.navigateToScreen
import com.example.frjarcustomer.ui.screen.appfeature.AppFeatureScreen
import com.example.frjarcustomer.ui.screen.appfeature.VersionExpireScreen
import com.example.frjarcustomer.ui.screen.intro.onboarding.CarouselScreen
import com.example.frjarcustomer.ui.screen.intro.onboarding.OnboardingViewModel
import com.example.frjarcustomer.ui.screen.intro.splash.SplashScreen
import com.example.frjarcustomer.ui.screen.intro.splash.SplashViewModel
import com.example.frjarcustomer.utils.openDialer
import com.example.frjarcustomer.utils.openPlayStore
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
object RootGraph

fun NavGraphBuilder.navigationRootGraph(
    navHostController: NavHostController,
    sharedViewModel: SplashViewModel,
    onRequestNotificationPermission: () -> Unit = {}

) {


    navigation<RootGraph>(
        startDestination = AppRoute.Splash::class
    ) {
        composable<AppRoute.Splash> {


            SplashScreen(
                moveToOnboarding = {
                    navigateToScreen(
                        navHostController,
                        AppRoute.Onboarding(it),
                        clearStack = true
                    )
                },
                moveToAuth = { navigateToHome(navHostController) },
                moveToAppFeature = {
                    navigateToScreen(
                        navHostController,
                        AppRoute.AppFeature,
                        clearStack = true
                    )
                },
                moveToVersionExpire = { updateStatus ->
                    navigateToScreen(
                        navHostController,
                        AppRoute.VersionExpire(isForceUpdate = updateStatus == AppUpdateStatus.ForceUpdate),
                        clearStack = true
                    )
                },
                viewModel = sharedViewModel
            )
        }
        composable<AppRoute.AppFeature> {
            val context = LocalContext.current

            AppFeatureScreen(
                onContactClick = { phoneNumber ->
                    openDialer(context, phoneNumber)
                },

                viewModel = sharedViewModel
            )
        }
        composable<AppRoute.VersionExpire> { backStackEntry ->

            val context = LocalContext.current
            val route = backStackEntry.toRoute<AppRoute.VersionExpire>()
            VersionExpireScreen(
                isForceUpdate = route.isForceUpdate,
                onUpdateClick = { appLink ->
                    openPlayStore(context, appLink)
                },
                moveToAuth = {
                    navigateToAuth(navHostController)
                },
                onCloseClick = {
                    sharedViewModel.checkOnboardingStatus()

                },
                moveToOnboarding = {
                    navigateToScreen(
                        navHostController,
                        AppRoute.Onboarding(it),
                        clearStack = true
                    )
                },
                viewModel = sharedViewModel
            )
        }
        composable<AppRoute.Onboarding>(
            typeMap = mapOf(
                typeOf<OnboardingData>() to CustomNavType(
                    OnboardingData::class.java,
                    OnboardingData.serializer()
                ),
            )
        )
        {
            val onboardingViewModel: OnboardingViewModel = hiltViewModel()

            LaunchedEffect(Unit) {
                onRequestNotificationPermission()

                onboardingViewModel.navigateToAuth.collect {
                    navigateToHome(navHostController)
                }
            }
            CarouselScreen(
                onFinish = onboardingViewModel::onFinish,
                onboardingPages = onboardingViewModel.onboardingData
            )
        }
    }












}
