package com.example.frjarcustomer.navigation.utils

import com.example.frjarcustomer.R
import com.example.frjarcustomer.navigation.routes.AppRoute
import kotlinx.serialization.Serializable

@Serializable
enum class TopLevelDestination(
    val route: AppRoute,
    val icon: Int,
    val iconTextId: Int,
) {
    HomeScreenNavigationRoute(
        route = AppRoute.Home,
        icon = R.drawable.ic_home,
        iconTextId = R.string.home,
    ),
    CollectionScreenNavigationRoute(
        route = AppRoute.Search,
        icon = R.drawable.ic_search,
        iconTextId = R.string.search,
    ),
    FavoritesScreenNavigationRoute(
        route = AppRoute.Favourite,
        icon = R.drawable.ic_heart,
        iconTextId = R.string.favorite,
    ),
    SettingsScreenNavigationRoute(
        route = AppRoute.Profile,
        icon = R.drawable.ic_more,
        iconTextId = R.string.more,
    ),
}