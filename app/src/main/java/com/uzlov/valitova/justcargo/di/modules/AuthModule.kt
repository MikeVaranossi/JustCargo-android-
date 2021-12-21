package com.uzlov.valitova.justcargo.di.modules

import com.uzlov.valitova.justcargo.auth.AuthService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthModule {

    @Singleton
    @Provides
    fun authService() : AuthService = AuthService()
}