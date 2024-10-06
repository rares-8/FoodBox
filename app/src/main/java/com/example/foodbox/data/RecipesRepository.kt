package com.example.foodbox.data

import kotlinx.coroutines.flow.Flow

/**
 * Provides update, retrieve, delete and insert operations for [Recipe] from a given source
 */
interface RecipesRepository {
    /**
     * Retrieve all recipes
     */
    fun getAllRecipes(): Flow<List<Recipe>>

    /**
     * Insert recipe in data source
     */
    suspend fun insertRecipe(recipe: Recipe)

    /**
     * Delete recipe from data source
     */
    suspend fun deleteRecipe(recipe: Recipe)

    /**
     * Update recipe
     */
    suspend fun updateRecipe(recipe: Recipe)
}