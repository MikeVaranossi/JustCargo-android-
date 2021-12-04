package com.uzlov.valitova.justcargo.repo.repositories

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.model.entities.User
import com.uzlov.valitova.justcargo.repo.datasources.IUsersRemoteDataSource
import com.uzlov.valitova.justcargo.repo.net.IUserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    var remoteDataSource: IUsersRemoteDataSource,
) : IUserRepository {
    override fun getUsers(): LiveData<List<User>>  = remoteDataSource.getUsers()
    override fun getUser(id: String) : LiveData<User?> = remoteDataSource.getUser(id)
    override fun removeUsers(id: String) = remoteDataSource.removeUsers(id)
    override fun putUser(user: User) = remoteDataSource.putUser(user)
}