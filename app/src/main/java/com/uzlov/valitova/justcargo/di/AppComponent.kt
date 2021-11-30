package com.uzlov.valitova.justcargo.di


import com.uzlov.valitova.justcargo.di.modules.*
import com.uzlov.valitova.justcargo.ui.HostActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        LocalModule::class,
        RepositoriesModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(hostActivity: HostActivity)
}