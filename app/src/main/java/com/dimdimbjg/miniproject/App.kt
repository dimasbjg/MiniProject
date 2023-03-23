package com.dimdimbjg.miniproject

import android.app.Application
import com.dimdimbjg.miniproject.di.appModule
import com.dimdimbjg.miniproject.di.networkModule
import com.dimdimbjg.miniproject.di.storageModule
import com.dimdimbjg.miniproject.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                appModule,
                storageModule,
                networkModule,
                viewModelModule
            )
        }
    }
}