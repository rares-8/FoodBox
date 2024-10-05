package com.example.foodbox.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.foodbox.data.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopRecipeAppBar(
    title: String,
    canNavigateBack: Boolean,
    canEdit: Boolean,
    navigateUp: () -> Unit,
    editRecipe: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehaviour: TopAppBarScrollBehavior? = null,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.headlineMedium) },
        modifier = modifier,
        scrollBehavior = scrollBehaviour,
        actions = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "go back"
                    )
                }
            }

            if (canEdit) {
                IconButton(onClick = editRecipe) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "edit"
                    )
                }
            }
        }
    )
}