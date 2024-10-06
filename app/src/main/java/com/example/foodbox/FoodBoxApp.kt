package com.example.foodbox

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodbox.navigation.RecipeNavHost

@Composable
fun FoodBoxApp(navHostController: NavHostController = rememberNavController()) {
    RecipeNavHost(navController = navHostController)
}