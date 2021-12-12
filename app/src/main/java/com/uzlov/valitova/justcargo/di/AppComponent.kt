package com.uzlov.valitova.justcargo.di


import com.uzlov.valitova.justcargo.di.modules.*
import com.uzlov.valitova.justcargo.ui.activity.HostActivity
import com.uzlov.valitova.justcargo.ui.fragments.FavoritesRequestsFragment
import com.uzlov.valitova.justcargo.ui.fragments.home.HomeSenderFragment
import com.uzlov.valitova.justcargo.ui.fragments.profile.MyDeliveriesFragment
import com.uzlov.valitova.justcargo.ui.fragments.profile.MyRequestsFragment
import com.uzlov.valitova.justcargo.ui.fragments.order.OrderStepTwoFragment
import com.uzlov.valitova.justcargo.ui.fragments.registration.WelcomeScreenFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        AuthService::class,
        LocalModule::class,
        RepositoriesModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(hostActivity: HostActivity)
    fun inject(hostActivity: WelcomeScreenFragment)
    fun inject(hostActivity: MyDeliveriesFragment)
    fun inject(favoritesRequestsFragment: FavoritesRequestsFragment)
    fun inject(orderStepTwoFragment: OrderStepTwoFragment)
    fun inject(homeSenderFragment: HomeSenderFragment)
    fun inject(myRequestsFragment: MyRequestsFragment)
}