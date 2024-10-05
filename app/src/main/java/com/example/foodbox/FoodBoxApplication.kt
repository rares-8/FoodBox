package com.example.foodbox

import android.app.Application
import com.example.foodbox.data.AppContainer
import com.example.foodbox.data.AppDataContainer

class FoodBoxApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}