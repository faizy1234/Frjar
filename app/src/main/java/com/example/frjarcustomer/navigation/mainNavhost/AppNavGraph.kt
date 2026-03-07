package com.example.frjarcustomer.navigation.mainNavhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.frjarcustomer.navigation.graphs.RootGraph
import com.example.frjarcustomer.navigation.graphs.navigationAuthGraph
import com.example.frjarcustomer.navigation.graphs.navigationHomeGraph
import com.example.frjarcustomer.navigation.graphs.navigationRootGraph
import com.example.frjarcustomer.navigation.utils.iosLikeEnterTransition
import com.example.frjarcustomer.navigation.utils.iosLikeExitTransition
import com.example.frjarcustomer.navigation.utils.iosLikePopEnterTransition
import com.example.frjarcustomer.navigation.utils.iosLikePopExitTransition
import com.example.frjarcustomer.ui.screen.intro.splash.SplashViewModel


@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onRequestNotificationPermission: () -> Unit = {}
) {

    val sharedViewModel: SplashViewModel = hiltViewModel()
    val context = navController.context
    NavHost(
        navController = navController,
        startDestination = RootGraph,
        enterTransition = { iosLikeEnterTransition() },
        exitTransition = { iosLikeExitTransition() },
        popEnterTransition = { iosLikePopEnterTransition() },
        popExitTransition = { iosLikePopExitTransition() },
        modifier = modifier
    ) {

        navigationRootGraph(
            navHostController = navController,
            sharedViewModel,
            onRequestNotificationPermission
        )
        navigationAuthGraph(
            navHostController = navController,
            sharedViewModel,
            context
        )
        navigationHomeGraph(
            navHostController = navController,

            )


    }


}
