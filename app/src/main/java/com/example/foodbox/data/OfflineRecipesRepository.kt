package com.example.foodbox.data

import kotlinx.coroutines.flow.Flow

class OfflineRecipesRepository(private val recipeDao: RecipeDao) : RecipesRepository {
    override fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()

    override suspend fun deleteRecipe(recipe: Recipe) = recipeDao.delete(recipe)

    override suspend fun insertRecipe(recipe: Recipe) = recipeDao.insert(recipe)

    override suspend fun updateRecipe(recipe: Recipe) = recipeDao.update(recipe)
}