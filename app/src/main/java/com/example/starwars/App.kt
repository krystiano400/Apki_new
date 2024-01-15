package com.example.starwars

import android.app.Application
import com.example.starwars.module.starModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(starModule)
        }
    }
}