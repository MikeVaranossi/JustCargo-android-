package com.uzlov.valitova.justcargo.app

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.uzlov.valitova.justcargo.di.AppComponent
import com.uzlov.valitova.justcargo.di.DaggerAppComponent
import com.uzlov.valitova.justcargo.di.modules.AppModule

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }