package com.example.todolistandroid.config

import android.app.Application


class ApplicationConfig : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImplementation()
    }
}

