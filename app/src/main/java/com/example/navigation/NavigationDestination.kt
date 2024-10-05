package com.example.navigation


/**
 * Describes navigation destinations in the app
 */
interface NavigationDestination {
    /**
     * Path to a composable
     */
    val route: String

    /**
     * Title that shows up in the top bar
     */
    val titleRes: Int
}