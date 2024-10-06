package com.example.foodbox.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.foodbox.FoodBoxApplication
import com.example.foodbox.data.Recipe
import com.example.foodbox.data.RecipesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


/**
 * ViewModel to retrieve and delete recipes in the database
 */
class HomeViewModel(private val recipesRepository: RecipesRepository): ViewModel() {

    /**
     * Holds UI state
     */
    val homeUiState: StateFlow<HomeUiState> =
        recipesRepository.getAllRecipes()
            .map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    suspend fun deleteRecipe(recipe: Recipe) {
        recipesRepository.deleteRecipe(recipe)
    }


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FoodBoxApplication)
                val repository = application.container.recipesRepository
                HomeViewModel(repository)
            }
        }
    }
}

/**
 * Represents the UI state for home screen
 */
data class HomeUiState(val recipeList: List<Recipe> = listOf())