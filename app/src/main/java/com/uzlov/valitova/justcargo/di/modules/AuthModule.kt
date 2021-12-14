package com.uzlov.valitova.justcargo.di.modules

import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.data.net.User
import com.uzlov.valitova.justcargo.data.net.UserType
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Inject

@Module
class AuthModule {


    /*@Reusable
    @Provides
    fun currentUser(): User = User(phone = "89992008289", userType = UserType(2, "Грузоперевозчик"))
*/

    @Provides
    fun authService() : AuthService = AuthService()


}