package com.example.foodbox.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodbox.R
import com.example.foodbox.data.Recipe
import com.example.foodbox.ui.TopRecipeAppBar
import com.example.foodbox.ui.recipe.RecipeDetailsBody
import com.example.foodbox.ui.theme.FoodBoxTheme
import com.example.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToRecipeDetails: (Recipe) -> Unit,
    navigateToRecipeEntry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopRecipeAppBar(
                title = stringResource(id = HomeDestination.titleRes),
                canNavigateBack = false,
                navigateUp = { })
        },
        floatingActionButton = {
            AnimatedVisibility(visible = scrollState.value == 0) {
                FloatingActionButton(
                    onClick = navigateToRecipeEntry,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(
                        end = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(
                            LocalLayoutDirection.current
                        )
                    )
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "edit recipe")
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->

        // TODO: put a grid here

        RecipeItem(
            navigateToRecipeDetails = navigateToRecipeDetails,
            recipe = Recipe(
                name = "Steak",
                ingredients = "Use Local Fonts Instead: Instead of relying on Google Play Services, you can download the Google Fonts locally and add them to your app. This way, the fonts are bundled directly within the app and don’t need to be fetched over the network.Use Local Fonts Instead: Instead of relying on Google Play Services, you can download the Google Fonts locally and add them to your app. This way, the fonts are bundled directly within the app and don’t need to be fetched over the network.Use Local Fonts Instead: Instead of relying on Google Play Services, you can download the Google Fonts locally and add them to your app. This way, the fonts are bundled directly within the app and don’t need to be fetched over the network.Use Local Fonts Instead: Instead of relying on Google Play Services, you can download the Google Fonts locally and add them to your app. This way, the fonts are bundled directly within the app and don’t need to be fetched over the network.Use Local Fonts Instead: Instead of relying on Google Play Services, you can download the Google Fonts locally and add them to your app. This way, the fonts are bundled directly within the app and don’t need to be fetched over the network.Use Local Fonts Instead: Instead of relying on Google Play Services, you can download the Google Fonts locally and add them to your app. This way, the fonts are bundled directly within the app and don’t need to be fetched over the network.",
                instructions = "cook it",
                nutriValue = ""
            ),
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun RecipeItem(
    navigateToRecipeDetails: (Recipe) -> Unit,
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { navigateToRecipeDetails(recipe) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = recipe.name, style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeItemPreview() {
    FoodBoxTheme {
        RecipeItem(
            navigateToRecipeDetails = {},
            recipe = Recipe(
                name = "Steak",
                ingredients = "meat, salt",
                instructions = "cook it"
            )
        )
    }
}