package com.example.foodbox.ui.recipe

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import com.example.foodbox.R
import com.example.foodbox.data.Recipe
import com.example.foodbox.ui.TopRecipeAppBar
import com.example.navigation.NavigationDestination
import kotlin.math.truncate


object DetailsDestination: NavigationDestination {
    override val route = "details"
    override val titleRes = R.string.details_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    modifier: Modifier = Modifier,
    recipeDetailsViewModel: RecipeDetailsViewModel
) {
    Scaffold(
        topBar = { TopRecipeAppBar(
            title = stringResource(id = R.string.details_title),
            canNavigateBack = true,
            canEdit = true,
            navigateUp = { /*TODO*/ },
            editRecipe = { /*TODO*/ })},
        floatingActionButton = { },
        modifier = modifier
    ) { innerPadding ->

        val uiState by recipeDetailsViewModel.uiState.collectAsState()

        RecipeDetailsBody(
            recipe = uiState,
            modifier = Modifier.padding(
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                bottom = innerPadding.calculateBottomPadding(),
                top = innerPadding.calculateTopPadding(),

            )
        )
    }
}

@Composable
fun RecipeDetailsBody(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Text(text = recipe.name, style = MaterialTheme.typography.headlineLarge, modifier = modifier)
}