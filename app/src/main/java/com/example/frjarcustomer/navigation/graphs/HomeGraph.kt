package com.example.frjarcustomer.navigation.graphs

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.frjarcustomer.navigation.routes.AppRoute
import com.example.frjarcustomer.navigation.utils.navigateToAuth
import com.example.frjarcustomer.ui.screen.dashedboard.cart.CartScreen
import com.example.frjarcustomer.ui.screen.dashedboard.favorite.FavouriteScreen
import com.example.frjarcustomer.ui.screen.detail.DetailScreen
import com.example.frjarcustomer.ui.screen.dashedboard.home.HomeScreen
import com.example.frjarcustomer.ui.screen.dashedboard.profile.ProfileScreen
import com.example.frjarcustomer.ui.screen.dashedboard.search.SearchScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeGraph

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.navigationHomeGraph(
    navHostController: NavHostController,
) {

    navigation<HomeGraph>(
        startDestination = AppRoute.Home::class
    ) {
        composable<AppRoute.Home> {
            HomeScreen(

            )
        }

        composable<AppRoute.Search> {
            SearchScreen()
        }

        composable<AppRoute.Cart> {
            CartScreen()
        }

        composable<AppRoute.Favourite> {
            FavouriteScreen()
        }
        composable<AppRoute.Profile> {
            ProfileScreen()
        }


        composable<AppRoute.Detail> { backStackEntry ->
            val route = backStackEntry.toRoute<AppRoute.Detail>()
            DetailScreen(
                itemId = route.itemId,
                onNavigateBack = { navHostController.popBackStack() }
            )
        }


    }


}