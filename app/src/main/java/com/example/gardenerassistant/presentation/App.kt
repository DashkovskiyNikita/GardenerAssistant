package com.example.gardenerassistant.presentation

import android.app.Application
import com.example.gardenerassistant.di.dataModule
import com.example.gardenerassistant.di.mappersModule
import com.example.gardenerassistant.di.useCasesModule
import com.example.gardenerassistant.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(viewModelsModule, useCasesModule, dataModule, mappersModule)
        }
    }
}