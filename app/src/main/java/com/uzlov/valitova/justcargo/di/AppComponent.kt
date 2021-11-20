package com.uzlov.valitova.justcargo.di


import com.uzlov.valitova.justcargo.di.modules.AppModule
import com.uzlov.valitova.justcargo.di.modules.ApiModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class
    ]
)

interface AppComponent {

}