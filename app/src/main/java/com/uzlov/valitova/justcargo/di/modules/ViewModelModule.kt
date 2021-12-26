package com.uzlov.valitova.justcargo.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzlov.valitova.justcargo.viemodels.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun viewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DeliveryViewModel::class)
    abstract fun deliveryViewModel(deliveryViewModel: DeliveryViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel::class)
    abstract fun usersViewModel(usersViewModel: UsersViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RequestsViewModel::class)
    abstract fun requestsViewModel(requestsViewModel: RequestsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesRequestsViewModel::class)
    abstract fun favoritesRequestsViewModel(favoritesRequestsViewModel: FavoritesRequestsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyRequestsViewModel::class)
    abstract fun myRequestsViewModel(myRequestsViewModel: MyRequestsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GeocodingViewModel::class)
    abstract fun geoViewModel(geoViewModel: GeocodingViewModel) : ViewModel
}