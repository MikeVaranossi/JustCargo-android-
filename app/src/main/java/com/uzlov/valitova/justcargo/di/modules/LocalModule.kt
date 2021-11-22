package com.uzlov.valitova.justcargo.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {

    @Singleton
    @Provides
    fun getLocalName() : String = "com.uzlov.inhunter_preferences"


    @Singleton
    @Provides
    fun sharedPreference(app: Context) : SharedPreferences = app.getSharedPreferences(getLocalName(), Context.MODE_PRIVATE)

}