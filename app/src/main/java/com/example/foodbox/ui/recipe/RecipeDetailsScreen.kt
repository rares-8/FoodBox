package com.example.foodbox.ui.recipe

import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foodbox.R
import com.example.foodbox.data.Recipe
import com.example.foodbox.ui.TopRecipeAppBar
import com.example.foodbox.navigation.NavigationDestination


object DetailsDestination : NavigationDestination {
    override val route = "details"
    override val titleRes = R.string.details_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    recipeDetailsViewModel: RecipeDetailsViewModel,
    onEditClicked: (Recipe) -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val scrollState = rememberScrollState()
    val uiState by recipeDetailsViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopRecipeAppBar(
                title = stringResource(id = DetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        },
        floatingActionButton = {
            AnimatedVisibility(visible = scrollState.value == 0) {
                FloatingActionButton(
                    onClick = { onEditClicked(uiState) },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(
                        end = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(
                            LocalLayoutDirection.current
                        )
                    )
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "edit recipe")
                }
            }

        },
        modifier = modifier
    ) { innerPadding ->

        HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.onPrimary)

        RecipeDetailsBody(
            recipe = uiState,
            scrollState = scrollState,
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
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {

    /**
     * Variables using state hosting for reusability
     */
    var ingredientsExpanded by remember { mutableStateOf(false) }
    var instructionsExpanded by remember { mutableStateOf(false) }
    var nutritionExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_extra_large)))
        Text(
            text = recipe.name,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
        )

        Log.d("RecipeDetails", "Photo URI: ${recipe.photoUri}")

        if (recipe.photoUri.isNotBlank()) {
            AsyncImage(
                model = Uri.parse(recipe.photoUri),
                contentDescription = "Recipe Image",
                modifier = Modifier
                    .height(
                        dimensionResource(id = R.dimen.image_height)
                    )
                    .fillMaxWidth(0.7f)
                    .padding(dimensionResource(id = R.dimen.padding_small)),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.placeholder)
            )
        }

        RecipeSection(
            recipeText = recipe.ingredients,
            sectionTitle = "Ingredients",
            isVisible = ingredientsExpanded,
            onButtonClicked = { ingredientsExpanded = !ingredientsExpanded })

        RecipeSection(
            recipeText = recipe.instructions,
            sectionTitle = "Instructions",
            isVisible = instructionsExpanded,
            onButtonClicked = { instructionsExpanded = !instructionsExpanded })

        if (recipe.nutriValue.isNotBlank()) {
            RecipeSection(
                recipeText = recipe.nutriValue,
                sectionTitle = "Nutritional value",
                isVisible = nutritionExpanded,
                onButtonClicked = { nutritionExpanded = !nutritionExpanded })
        }
    }
}

/**
 * Composable function that can show each section of a recipe
 */
@Composable
fun RecipeSection(
    recipeText: String,
    sectionTitle: String,
    isVisible: Boolean,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(
                start = dimensionResource(id = R.dimen.padding_medium),
                end = dimensionResource(id = R.dimen.padding_medium),
                top = dimensionResource(id = R.dimen.padding_extra_large)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = sectionTitle, style = MaterialTheme.typography.headlineMedium)

            IconButton(onClick = onButtonClicked) {
                Icon(
                    imageVector = if (isVisible) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                    contentDescription = "Expand ingredients section"
                )
            }
        }
        AnimatedVisibility(visible = isVisible) {
            Text(
                text = recipeText,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}