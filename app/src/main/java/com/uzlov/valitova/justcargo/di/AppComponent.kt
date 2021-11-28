package com.uzlov.valitova.justcargo.di


import com.uzlov.valitova.justcargo.di.modules.AppModule
import com.uzlov.valitova.justcargo.di.modules.ApiModule
import com.uzlov.valitova.justcargo.di.modules.LocalModule
import com.uzlov.valitova.justcargo.di.modules.ViewModelModule
import com.uzlov.valitova.justcargo.ui.HostActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        LocalModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(hostActivity: HostActivity)
}