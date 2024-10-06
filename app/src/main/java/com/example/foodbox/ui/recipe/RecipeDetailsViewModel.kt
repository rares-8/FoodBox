package com.example.foodbox.ui.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.foodbox.FoodBoxApplication
import com.example.foodbox.data.Recipe
import com.example.foodbox.data.RecipesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RecipeDetailsViewModel(
    private val recipesRepository: RecipesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(Recipe())

    val uiState: StateFlow<Recipe> = _uiState

    fun updateRecipe(newRecipe: Recipe) {
        _uiState.update {
            with(newRecipe) {
                it.copy(
                    id = id,
                    name = name,
                    instructions = instructions,
                    ingredients = ingredients,
                    nutriValue = nutriValue
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FoodBoxApplication)
                val repository = application.container.recipesRepository
                RecipeDetailsViewModel(repository)
            }
        }
    }
}