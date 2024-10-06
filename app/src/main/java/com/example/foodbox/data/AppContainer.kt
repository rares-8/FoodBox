package com.example.foodbox.data

import android.content.Context

/**
 * App container for Dependency Injection
 */
interface AppContainer {
    val recipesRepository: RecipesRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val recipesRepository: RecipesRepository by lazy {
        OfflineRecipesRepository(RecipeDatabase.getDatabase(context).recipeDao())
    }
}