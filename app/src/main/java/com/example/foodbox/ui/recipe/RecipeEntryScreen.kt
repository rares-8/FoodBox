package com.example.foodbox.ui.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.foodbox.R
import com.example.foodbox.data.Recipe
import com.example.foodbox.ui.TopRecipeAppBar
import com.example.navigation.NavigationDestination

object RecipeEntryDestination : NavigationDestination {
    override val route = "recipe_entry"
    override val titleRes = R.string.recipe_entry
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeEntryScreen(
    onSaveClick: () -> Unit,
    navigateUp: () -> Unit,
    recipeEntryViewModel: RecipeEntryViewModel,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopRecipeAppBar(
                title = stringResource(RecipeEntryDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        RecipeEntryBody(
            recipeUiState = recipeEntryViewModel.recipeUiState,
            onValueChange = recipeEntryViewModel::updateUiState,
            onSaveClick = onSaveClick,
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun RecipeEntryBody(
    recipeUiState: RecipeUiState,
    onValueChange: (Recipe) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        RecipeForm(
            recipe = recipeUiState.recipe,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            enabled = recipeUiState.isValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.save_action))
        }
    }
}

@Composable
fun RecipeForm(
    recipe: Recipe,
    modifier: Modifier = Modifier,
    onValueChange: (Recipe) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = recipe.name,
            onValueChange = { onValueChange(recipe.copy(name = it)) },
            label = { Text(text = stringResource(id = R.string.recipe_name_entry)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = recipe.ingredients,
            onValueChange = { onValueChange(recipe.copy(ingredients = it)) },
            label = { Text(text = stringResource(id = R.string.recipe_ingredients_entry)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        OutlinedTextField(
            value = recipe.instructions,
            onValueChange = { onValueChange(recipe.copy(instructions = it)) },
            label = { Text(text = stringResource(id = R.string.recipe_instructions_entry)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        OutlinedTextField(
            value = recipe.nutriValue,
            onValueChange = { onValueChange(recipe.copy(nutriValue = it)) },
            label = { Text(text = stringResource(id = R.string.recipe_nutritional_entry)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}