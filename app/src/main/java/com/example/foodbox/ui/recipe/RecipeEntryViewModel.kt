package com.example.foodbox.ui.recipe

import android.view.View
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
import kotlinx.coroutines.flow.MutableStateFlow

class RecipeEntryViewModel(private val recipesRepository: RecipesRepository) : ViewModel() {

    var recipeUiState by mutableStateOf(RecipeUiState())
        private set

    /**
     * Updates UI state and checks if recipe is valid
     */
    fun updateUiState(newRecipe: Recipe) {
        recipeUiState =
            RecipeUiState(recipe = newRecipe, isValid = validateRecipe(newRecipe))
    }

    private fun validateRecipe(recipe: Recipe): Boolean {
        with(recipe) {
            return name.isNotBlank() && ingredients.isNotBlank() && instructions.isNotBlank()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FoodBoxApplication)
                val repository = application.container.recipesRepository
                RecipeEntryViewModel(repository)
            }
        }
    }
}

/**
 * Represents UI State for a recipe
 */
data class RecipeUiState(
    val recipe: Recipe = Recipe(),
    val isValid: Boolean = false
)

