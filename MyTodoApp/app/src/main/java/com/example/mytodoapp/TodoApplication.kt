package com.example.mytodoapp

import android.app.Application
import com.example.mytodoapp.data.AppContainer
import com.example.mytodoapp.data.AppDataContainer

class TodoApplication:Application() {
    lateinit var container:AppContainer
    override fun onCreate() {
        super.onCreate()
        container=AppDataContainer(this)
    }
}