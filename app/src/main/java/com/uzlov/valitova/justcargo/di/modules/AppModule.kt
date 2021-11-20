package com.uzlov.valitova.justcargo.di.modules

import android.content.Context
import com.uzlov.valitova.justcargo.app.App
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: App) {

    @Provides
    fun app(): Context = app

}