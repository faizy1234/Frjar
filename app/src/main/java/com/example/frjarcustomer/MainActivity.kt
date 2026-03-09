package com.example.frjarcustomer

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import androidx.navigation.get
import com.example.frjarcustomer.appstate.AppLanguage
import com.example.frjarcustomer.appstate.FrjarBottomAppBar
import com.example.frjarcustomer.appstate.LanguageAwareComponent
import com.example.frjarcustomer.appstate.LocalPaddingValues
import com.example.frjarcustomer.appstate.MainActivityVm
import com.example.frjarcustomer.appstate.ObserveAsEvents
import com.example.frjarcustomer.appstate.ProfileTabEvent
import com.example.frjarcustomer.appstate.MessageContent
import com.example.frjarcustomer.appstate.SnackbarController
import com.example.frjarcustomer.appstate.SnackbarModel
import com.example.frjarcustomer.navigation.graphs.HomeGraph
import com.example.frjarcustomer.navigation.mainNavhost.AppNavGraph
import com.example.frjarcustomer.navigation.routes.AppRoute
import com.example.frjarcustomer.navigation.utils.navigateToAuth
import com.example.frjarcustomer.ui.components.AnimatableSnackbar
import com.example.frjarcustomer.navigation.utils.TopLevelDestination
import com.example.frjarcustomer.ui.theme.FrjarTheme
import dagger.hilt.android.AndroidEntryPoint
import network.chaintech.sdpcomposemultiplatform.sdp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainActivityVm: MainActivityVm by viewModels()

    private val requestNotificationPermission =
        registerForActivityResult(RequestPermission()) { _ -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val currentLanguage by mainActivityVm.currentLanguage.collectAsStateWithLifecycle(
                initialValue = AppLanguage.DEFAULT
            )
            FrjarTheme(currentLanguage = currentLanguage, darkTheme = false) {
                ObserveAsEvents(flow = SnackbarController.events) { event ->
                    SnackbarController.show(
                        SnackbarModel(message = MessageContent.PlainString(event.message))
                    )
                }

                val currentSnackbar by SnackbarController.current.collectAsStateWithLifecycle(initialValue = null)
                val navController = rememberNavController()
                val topLevelDestination = listOf(
                    TopLevelDestination.HomeScreenNavigationRoute,
                    TopLevelDestination.CollectionScreenNavigationRoute,
                    TopLevelDestination.FavoritesScreenNavigationRoute,
                    TopLevelDestination.SettingsScreenNavigationRoute
                )
                androidx.compose.runtime.LaunchedEffect(Unit) {
                    mainActivityVm.profileTabEvent.collect { event ->
                        when (event) {
                            is ProfileTabEvent.NavigateToAuth -> navigateToAuth(navController)
                            is ProfileTabEvent.NavigateToProfile -> navController.navigate(AppRoute.Profile) {
                                popUpTo(navController.graph[HomeGraph].id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                }

                LanguageAwareComponent(
                    viewModel = mainActivityVm,
                    activityContext = this@MainActivity,
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color.Transparent,
                        topBar = {
                            Column(modifier = Modifier.wrapContentSize()) {
                                Spacer(Modifier.height(25.sdp))
                                AnimatableSnackbar(
                                    snackbarModel = currentSnackbar,
                                    onSnackbarDismiss = { SnackbarController.dismiss() }
                                )
                            }
                        },
                        bottomBar = {
                            FrjarBottomAppBar(
                                navController = navController,
                                bottomBarItems = topLevelDestination,
                                onProfileClick = { mainActivityVm.onProfileTabClicked() }
                            )
                        }
                    ) { innerPadding ->
                            CompositionLocalProvider(LocalPaddingValues provides innerPadding) {
                                AppNavGraph(
                                    navController = navController,
                                    modifier = Modifier.fillMaxSize(),
                                    onRequestNotificationPermission = { requestNotificationPermissionIfNeeded() }
                                )
                            }


                    }
                }


            }
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!hasPermission) {
                requestNotificationPermission.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
