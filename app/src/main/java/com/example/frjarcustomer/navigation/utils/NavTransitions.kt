package com.example.frjarcustomer.navigation.utils

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry

private const val DURATION_MS = 320

fun AnimatedContentTransitionScope<NavBackStackEntry>.iosLikeEnterTransition(): EnterTransition {
    return slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(DURATION_MS)) + fadeIn(animationSpec = tween(DURATION_MS))
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.iosLikeExitTransition(): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(DURATION_MS)) + fadeOut(animationSpec = tween(DURATION_MS))
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.iosLikePopEnterTransition(): EnterTransition {
    return slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(DURATION_MS)) + fadeIn(animationSpec = tween(DURATION_MS))
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.iosLikePopExitTransition(): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(DURATION_MS)) + fadeOut(animationSpec = tween(DURATION_MS))
}

private const val FAST_MS = 280

fun AnimatedContentTransitionScope<NavBackStackEntry>.fastSlideIn(): EnterTransition {
    return slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(FAST_MS))
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.fastSlideOut(): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(FAST_MS))
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.noExitAnimation(): ExitTransition = ExitTransition.None
fun AnimatedContentTransitionScope<NavBackStackEntry>.noEnterAnimation(): EnterTransition = EnterTransition.None
