package com.uzlov.valitova.justcargo.di.modules

import com.uzlov.valitova.justcargo.auth.IStateAuth
import com.uzlov.valitova.justcargo.auth.StateAuthImpl
import com.uzlov.valitova.justcargo.data.net.User
import com.uzlov.valitova.justcargo.data.net.UserType
import dagger.Module
import dagger.Provides

@Module
class AuthService {

    @Provides
    fun currentUser() = User(phone = "89992008289", userType = UserType(2, "Грузоперевозчик"))

    @Provides
    fun loginListener() : IStateAuth = StateAuthImpl()

}