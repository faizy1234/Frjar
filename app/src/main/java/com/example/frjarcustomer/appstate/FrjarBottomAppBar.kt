package com.example.frjarcustomer.appstate

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.get
import com.example.frjarcustomer.navigation.graphs.HomeGraph
import com.example.frjarcustomer.navigation.routes.AppRoute
import com.example.frjarcustomer.navigation.utils.NavigationScreenRouteClassNames
import com.example.frjarcustomer.navigation.utils.TopLevelDestination
import com.example.frjarcustomer.navigation.utils.currentRouteClassName
import com.example.frjarcustomer.navigation.utils.routeClassName
import com.example.frjarcustomer.ui.components.GenericText
import com.example.frjarcustomer.ui.theme.TextGreyscale500
import com.example.frjarcustomer.ui.theme.TextPrimary
import com.example.frjarcustomer.ui.theme.White
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FrjarBottomAppBar(
    navController: NavController,
    bottomBarItems: List<TopLevelDestination>,
    onProfileClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = navController.currentRouteClassName in NavigationScreenRouteClassNames
    ) {
        NavigationBar(
            containerColor = White,
            modifier = modifier

                .animateEnterExit(
                    enter = fadeIn() + slideInHorizontally(),
                    exit = fadeOut() + slideOutHorizontally()
                )
        ) {
            bottomBarItems.forEach { destination ->
                val isSelected = navController.currentRouteClassName == destination.routeClassName
                val itemColor = if (isSelected) TextPrimary else TextGreyscale500
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        if (destination.route == AppRoute.Profile && onProfileClick != null) {
                            onProfileClick()
                        } else {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph[HomeGraph].id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Image(
                            painter = painterResource(id = destination.icon),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            colorFilter =ColorFilter.tint(itemColor) ,
                            modifier = Modifier
                                .size(18.sdp)
                        )
                    },
                    label = {
                        GenericText(
                            text = resourceString(destination.iconTextId),
                            fontSize = 8.ssp,
                            fontWeight = FontWeight.Bold,
                            color = itemColor,
                        )
                    },

                )
            }
        }
    }
}