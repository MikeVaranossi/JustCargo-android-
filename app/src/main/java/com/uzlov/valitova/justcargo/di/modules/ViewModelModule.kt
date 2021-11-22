package com.uzlov.valitova.justcargo.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzlov.valitova.justcargo.viemodels.MainHomeViewModel
import com.uzlov.valitova.justcargo.viemodels.ViewModelFactory
import com.uzlov.valitova.justcargo.viemodels.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun viewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainHomeViewModel::class)
    abstract fun homeViewModel(mainHomeViewModel: MainHomeViewModel) : ViewModel
}