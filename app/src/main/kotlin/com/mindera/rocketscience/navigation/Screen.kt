package com.mindera.rocketscience.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("Home")
}