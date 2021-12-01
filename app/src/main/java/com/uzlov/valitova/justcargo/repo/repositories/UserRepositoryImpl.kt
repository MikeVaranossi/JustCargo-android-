package com.uzlov.valitova.justcargo.repo.repositories

import com.uzlov.valitova.justcargo.model.entities.User
import com.uzlov.valitova.justcargo.repo.datasources.UsersRemoteDataSource
import com.uzlov.valitova.justcargo.repo.net.IUserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    var remoteDataSource: UsersRemoteDataSource,
) : IUserRepository {
    override fun getUsers(): List<User>  = remoteDataSource.getUsers()
    override fun getUser(id: Long) : User? = remoteDataSource.getUser(id)
    override fun removeUsers(id: Long) = remoteDataSource.removeUsers(id)
    override fun putUser(user: User) = remoteDataSource.putUser(user)
}