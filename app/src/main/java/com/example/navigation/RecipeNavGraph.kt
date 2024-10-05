package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import com.example.foodbox.ui.home.HomeDestination
import com.example.foodbox.ui.home.HomeScreen
import com.example.foodbox.ui.recipe.DetailsDestination
import com.example.foodbox.ui.recipe.RecipeDetailsScreen
import com.example.foodbox.ui.recipe.RecipeDetailsViewModel
import com.example.foodbox.ui.recipe.RecipeEntryDestination
import com.example.foodbox.ui.recipe.RecipeEntryScreen
import com.example.foodbox.ui.recipe.RecipeEntryViewModel

@Composable
fun RecipeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val recipeDetailsViewModel: RecipeDetailsViewModel =
        viewModel(factory = RecipeDetailsViewModel.Factory)

    val recipeEntryViewModel: RecipeEntryViewModel =
        viewModel(factory = RecipeEntryViewModel.Factory)

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToRecipeEntry = { navController.navigate(RecipeEntryDestination.route) },
                navigateToRecipeDetails = {
                    recipeDetailsViewModel.updateRecipe(it)
                    navController.navigate(DetailsDestination.route)
                }
            )
        }

        composable(route = DetailsDestination.route) {
            RecipeDetailsScreen(
                recipeDetailsViewModel = recipeDetailsViewModel,
                navigateUp = { navController.popBackStack() })
        }

        composable(route = RecipeEntryDestination.route) {
            RecipeEntryScreen(
                recipeEntryViewModel = recipeEntryViewModel,
                onSaveClick = {/* TODO */ },
                navigateUp = { navController.popBackStack() })
        }
    }

}