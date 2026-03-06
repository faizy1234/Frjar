package com.example.frjarcustomer

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.frjarcustomer.appstate.AppLanguage
import com.example.frjarcustomer.appstate.FrjarBottomAppBar
import com.example.frjarcustomer.appstate.LanguageAwareComponent
import com.example.frjarcustomer.appstate.LocalPaddingValues
import com.example.frjarcustomer.appstate.MainActivityVm
import com.example.frjarcustomer.navigation.mainNavhost.AppNavGraph
import com.example.frjarcustomer.navigation.utils.TopLevelDestination
import com.example.frjarcustomer.ui.theme.FrjarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainActivityVm: MainActivityVm by viewModels()

    private val requestNotificationPermission =
        registerForActivityResult(RequestPermission()) { _ -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val currentLanguage by mainActivityVm.currentLanguage.collectAsStateWithLifecycle(
                initialValue = AppLanguage.DEFAULT
            )
            FrjarTheme(currentLanguage = currentLanguage) {
                val navController = rememberNavController()
                val topLevelDestination = listOf(
                    TopLevelDestination.HomeScreenNavigationRoute,
                    TopLevelDestination.CollectionScreenNavigationRoute,
                    TopLevelDestination.FavoritesScreenNavigationRoute,
                    TopLevelDestination.SettingsScreenNavigationRoute
                )

                LanguageAwareComponent(
                    viewModel = mainActivityVm,
                    activityContext = this@MainActivity,
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color.Transparent,
                        bottomBar = {
                            FrjarBottomAppBar(navController = navController, topLevelDestination)
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
