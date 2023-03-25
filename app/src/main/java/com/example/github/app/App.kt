package com.example.github.app

import android.app.Application
import com.example.github.di.appmodule
import com.example.github.di.remoteModule
import com.example.github.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appmodule, remoteModule, viewModelModule))
        }
    }
}