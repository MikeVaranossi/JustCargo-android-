package com.uzlov.valitova.justcargo.di


import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.di.modules.*
import com.uzlov.valitova.justcargo.service.BookingRequestStateService
import com.uzlov.valitova.justcargo.service.LookRequestStateService
import com.uzlov.valitova.justcargo.ui.activity.HostActivity
import com.uzlov.valitova.justcargo.ui.activity.SplashActivity
import com.uzlov.valitova.justcargo.ui.fragments.FavoritesRequestsFragment
import com.uzlov.valitova.justcargo.ui.fragments.SearchFragment
import com.uzlov.valitova.justcargo.ui.fragments.details.RequestDetailCarrierFragment
import com.uzlov.valitova.justcargo.ui.fragments.details.RequestDetailSenderFragment
import com.uzlov.valitova.justcargo.ui.fragments.home.HomeCarrierFragment
import com.uzlov.valitova.justcargo.ui.fragments.home.HomeSenderFragment
import com.uzlov.valitova.justcargo.ui.fragments.order.OrderStepOneFragment
import com.uzlov.valitova.justcargo.ui.fragments.order.OrderStepTwoFragment
import com.uzlov.valitova.justcargo.ui.fragments.order.SelectMapPositionsFragment
import com.uzlov.valitova.justcargo.ui.fragments.profile.*
import com.uzlov.valitova.justcargo.ui.fragments.registration.LoginFragment
import com.uzlov.valitova.justcargo.ui.fragments.registration.RegistrationSmsFragment
import com.uzlov.valitova.justcargo.ui.fragments.search.FindCargoFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        AuthModule::class,
        LocalModule::class,
        RepositoriesModule::class,
        ViewModelModule::class,
        GeoModule::class
    ]
)
interface AppComponent {
    fun inject(hostActivity: HostActivity)
    fun inject(hostActivity: MyDeliveriesFragment)
    fun inject(favoritesRequestsFragment: FavoritesRequestsFragment)
    fun inject(orderStepTwoFragment: OrderStepTwoFragment)
    fun inject(homeSenderFragment: HomeSenderFragment)
    fun inject(myRequestsFragment: MyRequestsFragment)
    fun inject(authService: AuthService)
    fun inject(registrationSmsFragment: RegistrationSmsFragment)
    fun inject(personalDataFragment: PersonalDataFragment)
    fun inject(homeCarrierFragment: HomeCarrierFragment)
    fun inject(splashActivity: SplashActivity)
    fun inject(profileCarrierFragment: ProfileCarrierFragment)
    fun inject(profileSenderFragment: ProfileSenderFragment)
    fun inject(selectMapPositionsFragment: SelectMapPositionsFragment)
    fun inject(orderStepOneFragment: OrderStepOneFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(requestDetailCarrierFragment: RequestDetailCarrierFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(bookingRequestStateService: BookingRequestStateService)
    fun inject(findCargoFragment: FindCargoFragment)
    fun inject(lookRequestStateService: LookRequestStateService)
    fun inject(requestDetailSenderFragment: RequestDetailSenderFragment)
}