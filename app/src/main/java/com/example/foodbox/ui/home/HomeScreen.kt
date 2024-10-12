package com.example.foodbox.ui.home

import android.net.Uri
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foodbox.R
import com.example.foodbox.data.Recipe
import com.example.foodbox.ui.TopRecipeAppBar
import com.example.foodbox.ui.theme.FoodBoxTheme
import com.example.foodbox.navigation.NavigationDestination
import com.example.foodbox.ui.theme.scrimDark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction1

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onEdit: (Recipe) -> Unit,
    navigateToRecipeDetails: (Recipe) -> Unit,
    navigateToRecipeEntry: () -> Unit,
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val homeUiState by homeViewModel.homeUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopRecipeAppBar(
                title = stringResource(id = HomeDestination.titleRes),
                canNavigateBack = false,
                navigateUp = { })
        },
        floatingActionButton = {
            AnimatedVisibility(visible = !scrollState.canScrollBackward) {
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

        HomeBody(
            onEdit = onEdit,
            onDelete = homeViewModel::deleteRecipe,
            coroutineScope = coroutineScope,
            scrollState = scrollState,
            recipeList = homeUiState.recipeList,
            navigateToRecipeDetails = navigateToRecipeDetails,
            paddingValues = innerPadding
        )
    }
}

@Composable
fun HomeBody(
    onDelete: KSuspendFunction1<Recipe, Unit>,
    onEdit: (Recipe) -> Unit,
    coroutineScope: CoroutineScope,
    scrollState: LazyListState,
    recipeList: List<Recipe>,
    navigateToRecipeDetails: (Recipe) -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        contentPadding = paddingValues
    ) {
        items(items = recipeList, key = { it.id }) { recipe ->
            RecipeItem(
                onEdit = { onEdit(recipe) },
                navigateToRecipeDetails = navigateToRecipeDetails,
                onDelete = {
                    coroutineScope.launch {
                        onDelete(recipe)
                    }
                },
                recipe = recipe,
                modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.padding_small)
                )
            )
        }
    }
}

@Composable
fun DropDownOptions(
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "more")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.delete_option)) },
                onClick = onDelete,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = stringResource(
                            id = R.string.delete_option
                        )
                    )
                })

            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.edit_option)) },
                onClick = onEdit,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = stringResource(
                            id = R.string.edit_option
                        )
                    )
                }
            )
        }
    }


}

@Composable
fun RecipeItem(
    onDelete: () -> (Unit),
    onEdit: () -> Unit,
    navigateToRecipeDetails: (Recipe) -> Unit,
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { navigateToRecipeDetails(recipe) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(

        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                AsyncImage(
                    model = Uri.parse(recipe.photoUri),
                    contentDescription = "Recipe Image",
                    modifier = Modifier
                        .height(
                            dimensionResource(id = R.dimen.image_height)
                        )
                        .fillMaxWidth(0.7f)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = dimensionResource(id = R.dimen.padding_medium)),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.placeholder_preview)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = dimensionResource(id = R.dimen.padding_small))
                )

                DropDownOptions(onDelete = onDelete, onEdit = onEdit)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeItemPreview() {
    FoodBoxTheme {
        RecipeItem(
            onDelete = {},
            onEdit = {},
            navigateToRecipeDetails = {},
            recipe = Recipe(
                name = "Steak",
                ingredients = "meat, salt",
                instructions = "cook it"
            )
        )
    }
}