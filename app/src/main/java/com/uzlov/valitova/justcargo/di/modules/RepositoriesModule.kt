package com.uzlov.valitova.justcargo.di.modules

import com.uzlov.valitova.justcargo.repo.datasources.*
import com.uzlov.valitova.justcargo.repo.local.ILocalRepository
import com.uzlov.valitova.justcargo.repo.net.IDeliveryRepository
import com.uzlov.valitova.justcargo.repo.net.IRequestsRepository
import com.uzlov.valitova.justcargo.repo.net.IUserRepository
import com.uzlov.valitova.justcargo.repo.repositories.DeliveryRepositoryImpl
import com.uzlov.valitova.justcargo.repo.repositories.RequestRepositoryImpl
import com.uzlov.valitova.justcargo.repo.repositories.UserRepositoryImpl
import com.uzlov.valitova.justcargo.repo.usecases.DeliveryUseCases
import com.uzlov.valitova.justcargo.repo.usecases.RequestsUseCases
import com.uzlov.valitova.justcargo.repo.usecases.UsersUseCase
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    // USERS
    @Provides
    fun provideUserRemoteDataSource() : IUsersRemoteDataSource = UserRemoteDataSourceImpl()

    @Provides
    fun provideUserRepository(remoteDataSource: IUsersRemoteDataSource) : IUserRepository = UserRepositoryImpl(remoteDataSource)

    @Provides
    fun provideUserUseCase(userRepository: IUserRepository) : UsersUseCase = UsersUseCase(userRepository)

    // DELIVERIES
    @Provides
    fun provideDeliveriesRemoteDataSource() : IDeliveryRemoteDataSource = DeliveryRemoteDataSourceImpl()

    @Provides
    fun provideDeliveriesRepository(remoteDataSource: IDeliveryRemoteDataSource) : IDeliveryRepository = DeliveryRepositoryImpl(remoteDataSource)

    @Provides
    fun provideDeliveryUseCase(
        deliveryRepository: IDeliveryRepository,
        localRepository: ILocalRepository,
    ): DeliveryUseCases = DeliveryUseCases(deliveryRepository, localRepository)

    // REQUESTS
    @Provides
    fun provideRequestRemoteDataSource() : IRequestsRemoteDataSource = RequestsRemoteDataSourceImpl()

    @Provides
    fun provideRequestRepository(remoteDataSource: IRequestsRemoteDataSource) : IRequestsRepository = RequestRepositoryImpl(remoteDataSource)

    @Provides
    fun provideRequestsUseCase(requestsRepository: IRequestsRepository, localRepository: ILocalRepository) : RequestsUseCases = RequestsUseCases(requestsRepository, localRepository)

}