package com.uzlov.valitova.justcargo.di.modules

import com.uzlov.valitova.justcargo.repo.datasources.*
import com.uzlov.valitova.justcargo.repo.net.IDeliveryRepository
import com.uzlov.valitova.justcargo.repo.net.IRequestsRepository
import com.uzlov.valitova.justcargo.repo.net.IUserRepository
import com.uzlov.valitova.justcargo.repo.repositories.DeliveryRepositoryImpl
import com.uzlov.valitova.justcargo.repo.repositories.RequestRepositoryImpl
import com.uzlov.valitova.justcargo.repo.repositories.UserRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    // USERS
    @Provides
    fun provideUserRemoteDataSource() : UsersRemoteDataSource = UserRemoteDataSourceImpl()

    @Provides
    fun provideUserRepository(remoteDataSource: UsersRemoteDataSource) : IUserRepository = UserRepositoryImpl(remoteDataSource)

    // DELIVERIES
    @Provides
    fun provideDeliveriesRemoteDataSource() : DeliveryRemoteDataSource = DeliveryRemoteDataSourceImpl()

    @Provides
    fun provideDeliveriesRepository(remoteDataSource: DeliveryRemoteDataSource) : IDeliveryRepository = DeliveryRepositoryImpl(remoteDataSource)

    // REQUESTS
    @Provides
    fun provideRequestRemoteDataSource() : RequestsRemoteDataSource = RequestsRemoteDataSourceImpl()

    @Provides
    fun provideRequestRepository(remoteDataSource: RequestsRemoteDataSource) : IRequestsRepository = RequestRepositoryImpl(remoteDataSource)

}