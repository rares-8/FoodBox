package com.example.foodbox.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity data class that represent a row in the database
 */
@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val photoUri: String = "",
    val name: String = "",
    val ingredients: String = "",
    val instructions: String = "",
    val nutriValue: String = "",
)
