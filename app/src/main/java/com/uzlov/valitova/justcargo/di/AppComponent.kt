package com.uzlov.valitova.justcargo.di


import com.uzlov.valitova.justcargo.di.modules.*
import com.uzlov.valitova.justcargo.ui.HostActivity
import com.uzlov.valitova.justcargo.ui.fragments.FavoritesRequestsFragment
import com.uzlov.valitova.justcargo.ui.fragments.HomeFragment
import com.uzlov.valitova.justcargo.ui.fragments.MyDeliveriesFragment
import com.uzlov.valitova.justcargo.ui.fragments.order.OrderStepTwoFragment
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
    fun inject(hostActivity: MyDeliveriesFragment)
    fun inject(favoritesRequestsFragment: FavoritesRequestsFragment)
    fun inject(orderStepTwoFragment: OrderStepTwoFragment)
    fun inject(homeFragment: HomeFragment)
}