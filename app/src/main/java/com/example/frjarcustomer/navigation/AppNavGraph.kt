package com.example.frjarcustomer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.frjarcustomer.data.remote.model.responseMaper.onboarding.OnboardingData
import com.example.frjarcustomer.data.remote.utils.AppUpdateStatus
import com.example.frjarcustomer.ui.screen.auth.ForgetPasswordScreen
import com.example.frjarcustomer.ui.screen.auth.LoginScreen
import com.example.frjarcustomer.ui.screen.auth.OtpScreen
import com.example.frjarcustomer.ui.screen.auth.SignUpScreen
import com.example.frjarcustomer.ui.screen.detail.DetailScreen
import com.example.frjarcustomer.ui.screen.home.HomeScreen
import com.example.frjarcustomer.ui.screen.profile.ProfileScreen
import com.example.frjarcustomer.ui.screen.intro.splash.SplashScreen
import com.example.frjarcustomer.ui.screen.intro.onboarding.CarouselScreen
import com.example.frjarcustomer.ui.screen.intro.onboarding.OnboardingViewModel
import com.example.frjarcustomer.ui.screen.appfeature.AppFeatureScreen
import com.example.frjarcustomer.ui.screen.appfeature.VersionExpireScreen
import com.example.frjarcustomer.ui.screen.appfeature.NoConnectionScreen
import com.example.frjarcustomer.ui.screen.intro.splash.SplashViewModel
import kotlin.reflect.typeOf

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Root,
        enterTransition = { iosLikeEnterTransition() },
        exitTransition = { iosLikeExitTransition() },
        popEnterTransition = { iosLikePopEnterTransition() },
        popExitTransition = { iosLikePopExitTransition() },
        modifier = modifier
    ) {

        navigation(
            route = NavRoutes.Root,
            startDestination = AppRoute.Splash.javaClass.simpleName
        ) {
            composable<AppRoute.Splash> {
                val navBackStackEntry = navController.getBackStackEntry(NavRoutes.Root)

                val parentViewModel = hiltViewModel<SplashViewModel>(navBackStackEntry)

                SplashScreen(
                    moveToOnboarding = {
                        navigateToScreen(
                            navController,
                            AppRoute.Onboarding(it),
                            clearStack = true
                        )
                    },
                    moveToAuth = { navigateToAuth(navController) },
                    moveToAppFeature = {
                        navigateToScreen(
                            navController,
                            AppRoute.AppFeature,
                            clearStack = true
                        )
                    },
                    moveToVersionExpire = { updateStatus ->
                        navigateToScreen(
                            navController,
                            AppRoute.VersionExpire(isForceUpdate = updateStatus == AppUpdateStatus.ForceUpdate),
                            clearStack = true
                        )
                    },
                    viewModel = parentViewModel
                )
            }
            composable<AppRoute.AppFeature> {

                val navBackStackEntry = navController.getBackStackEntry(NavRoutes.Root)

                val parentViewModel = hiltViewModel<SplashViewModel>(navBackStackEntry)

                AppFeatureScreen(
                    onContactClick = { navController.popBackStack() },
                    viewModel = parentViewModel
                )
            }
            composable<AppRoute.VersionExpire> { backStackEntry ->
                val navBackStackEntry = navController.getBackStackEntry(NavRoutes.Root)

                val parentViewModel = hiltViewModel<SplashViewModel>(navBackStackEntry)

                val route = backStackEntry.toRoute<AppRoute.VersionExpire>()
                VersionExpireScreen(
                    isForceUpdate = route.isForceUpdate,
                    onUpdateClick = {

                    },
                    moveToAuth = {
                        navigateToAuth(navController)
                    },
                    onCloseClick = {
                        parentViewModel.checkOnboardingStatus()

                    },
                    moveToOnboarding = {
                        navigateToScreen(
                            navController,
                            AppRoute.Onboarding(it),
                            clearStack = true
                        )
                    },
                    viewModel = parentViewModel
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
                    onboardingViewModel.navigateToAuth.collect {
                        navigateToAuth(navController)
                    }
                }
                CarouselScreen(
                    onFinish = onboardingViewModel::onFinish,
                    onboardingPages = onboardingViewModel.onboardingData
                )
            }
        }

        navigation(
            route = NavRoutes.Auth,
            startDestination = AppRoute.Login.javaClass.simpleName
        ) {
            composable<AppRoute.Login> {
                LoginScreen(
                    moveToHome = { navigateToHome(navController) },
                    moveToCreateAccount = { navController.navigate(AppRoute.SignUp) },
                    moveToForgetPassword = { navController.navigate(AppRoute.ForgetPassword) }
                )
            }
            composable<AppRoute.SignUp> {
                SignUpScreen(
                    onBackPress = { navController.navigateUp() },
                    onSignup = { userId, userEmail ->
                        navController.navigate(
                            AppRoute.Otp(
                                userId = userId,
                                userEmail = userEmail,
                                isForgetPassword = false,
                                isDeleteAccount = false
                            )
                        )
                    }
                )
            }
            composable<AppRoute.Otp> { backStackEntry ->
                val route = backStackEntry.toRoute<AppRoute.Otp>()
                OtpScreen(
                    onOtpVerified = { navigateToHome(navController) },
                    onBackClick = {
                        if (route.isDeleteAccount) navController.navigateUp()
                        else
                            navController.navigate(NavRoutes.Auth) {
                                popUpTo(NavRoutes.Auth) { inclusive = true }
                                launchSingleTop = true
                            }
                    }
                )
            }
            composable<AppRoute.ForgetPassword> {
                ForgetPasswordScreen(onBackPress = { navController.navigateUp() })
            }
        }

        navigation(
            route = NavRoutes.Dashboard,
            startDestination = AppRoute.Home.javaClass.simpleName
        ) {
            composable<AppRoute.Home> {
                HomeScreen(
                    onNavigateToDetail = { itemId ->
                        navController.navigate(AppRoute.Detail(itemId = itemId))
                    },
                    onNavigateToProfile = { userId, tab ->
                        navController.navigate(AppRoute.Profile(userId = userId, tab = tab))
                    },
                    onNavigateToAppFeature = { navController.navigate(AppRoute.AppFeature) },
                    onNavigateToVersionExpire = { navController.navigate(AppRoute.VersionExpire()) },
                    onNavigateToNoConnection = { navController.navigate(AppRoute.NoConnection) }
                )
            }
            composable<AppRoute.Detail> { backStackEntry ->
                val route = backStackEntry.toRoute<AppRoute.Detail>()
                DetailScreen(
                    itemId = route.itemId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable<AppRoute.Profile> { backStackEntry ->

                val route = backStackEntry.toRoute<AppRoute.Profile>()
                ProfileScreen(
                    userId = route.userId,
                    tab = route.tab,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

        }
    }
}
