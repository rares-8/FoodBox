package com.example.foodbox.ui.recipe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.foodbox.FoodBoxApplication
import com.example.foodbox.data.Recipe
import com.example.foodbox.data.RecipesRepository

/**
 * View Model to update entries in the database
 */
class RecipeEditViewModel(
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    var uiState by mutableStateOf(RecipeUiState())
        private set

    private fun validateRecipe(recipe: Recipe): Boolean {
        with(recipe) {
            return name.isNotBlank() && ingredients.isNotBlank() && instructions.isNotBlank()
        }
    }

    suspend fun updateRecipe() {
        if (validateRecipe(uiState.recipe)) {
            recipesRepository.updateRecipe(uiState.recipe)
        }
    }

    fun updateUiState(newRecipe: Recipe) {
        uiState =  RecipeUiState(recipe = newRecipe, isValid = validateRecipe(newRecipe))
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FoodBoxApplication)
                val repository = application.container.recipesRepository
                RecipeEditViewModel(repository)
            }
        }
    }
}