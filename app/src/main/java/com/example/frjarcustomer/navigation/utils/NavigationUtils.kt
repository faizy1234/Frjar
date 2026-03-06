package com.example.frjarcustomer.navigation.utils

import androidx.navigation.NavController
import com.example.frjarcustomer.navigation.graphs.AuthGraph
import com.example.frjarcustomer.navigation.graphs.HomeGraph

/**
 * Navigate to a graph/screen, optionally clearing the back stack.
 */
fun navigateToScreen(
    navController: NavController,
    route: Any,
    clearStack: Boolean = false
) {
    if (clearStack) {
        navController.navigate(route) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
    } else {
        navController.navigate(route) {
            launchSingleTop = true
        }
    }
}

/**
 * Navigate to Dashboard (Home) and clear Auth/Root from back stack.
 */
fun navigateToHome(navController: NavController) {
    navController.navigate(HomeGraph) {
        popUpTo(0) { inclusive = false }

//        popUpTo(NavRoutes.Auth) { inclusive = true }
//        popUpTo(NavRoutes.Root) { inclusive = true }
//        launchSingleTop = true
    }
}

/**
 * Navigate to Auth graph. Pops only within Root (e.g. Onboarding) so RootGraph stays on stack.
 * This avoids crash: getBackStackEntry(RootGraph) when coming directly from Splash.
 */
fun navigateToAuth(navController: NavController) {
    navController.navigate(AuthGraph) {
        popUpTo(0) { inclusive = false }
        launchSingleTop = true
    }
}

