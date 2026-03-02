package com.example.frjarcustomer.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

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
    navController.navigate(NavRoutes.Dashboard) {
        popUpTo(NavRoutes.Auth) { inclusive = true }
        popUpTo(NavRoutes.Root) { inclusive = true }
        launchSingleTop = true
    }
}

/**
 * Navigate to Auth graph and clear back stack up to Root.
 */
fun navigateToAuth(navController: NavController) {
    navController.navigate(NavRoutes.Auth) {
        popUpTo(NavRoutes.Root) { inclusive = true }
        launchSingleTop = true
    }
}
