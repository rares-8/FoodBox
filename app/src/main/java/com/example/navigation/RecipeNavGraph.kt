package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodbox.ui.home.HomeDestination
import com.example.foodbox.ui.home.HomeScreen
import com.example.foodbox.ui.home.HomeViewModel
import com.example.foodbox.ui.recipe.DetailsDestination
import com.example.foodbox.ui.recipe.RecipeDetailsScreen
import com.example.foodbox.ui.recipe.RecipeDetailsViewModel
import com.example.foodbox.ui.recipe.RecipeEditDestination
import com.example.foodbox.ui.recipe.RecipeEditScreen
import com.example.foodbox.ui.recipe.RecipeEditViewModel
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

    val homeViewModel: HomeViewModel =
        viewModel(factory = HomeViewModel.Factory)

    val editViewModel: RecipeEditViewModel =
        viewModel(factory = RecipeEditViewModel.Factory)

    val focusManager = LocalFocusManager.current

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                onEdit = { recipe ->
                    editViewModel.updateUiState(recipe)
                    navController.navigate(RecipeEditDestination.route) },
                navigateToRecipeEntry = {
                    recipeEntryViewModel.clearUiState()
                    navController.navigate(RecipeEntryDestination.route)
                },
                homeViewModel = homeViewModel,
                navigateToRecipeDetails = {
                    recipeDetailsViewModel.updateRecipe(it)
                    navController.navigate(DetailsDestination.route)
                }
            )
        }

        composable(route = DetailsDestination.route) {
            RecipeDetailsScreen(
                onEditClicked = { recipe ->
                    editViewModel.updateUiState(recipe)
                    navController.navigate(RecipeEditDestination.route)
                },
                recipeDetailsViewModel = recipeDetailsViewModel,
                navigateUp = {
                    navController.popBackStack()
                })
        }

        composable(route = RecipeEntryDestination.route) {
            RecipeEntryScreen(
                recipeEntryViewModel = recipeEntryViewModel,
                navigateUp = {
                    focusManager.clearFocus()
                    navController.popBackStack()
                })
        }

        composable(route = RecipeEditDestination.route) {
            RecipeEditScreen(
                navigateUp = {
                    recipeDetailsViewModel.updateRecipe(editViewModel.uiState.recipe)
                    focusManager.clearFocus()
                    navController.popBackStack()
                },
                recipeEditViewModel = editViewModel,
            )
        }
    }

}