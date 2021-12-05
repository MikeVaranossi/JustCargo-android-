package com.uzlov.valitova.justcargo.repo.usecases

import com.uzlov.valitova.justcargo.model.entities.User
import com.uzlov.valitova.justcargo.repo.net.IUserRepository
import javax.inject.Inject

class UsersUseCase @Inject constructor(var userRepository: IUserRepository) {
    suspend fun getUsers() = userRepository.getUsers()
    suspend fun getUsers(id: String) = userRepository.getUser(id)
    suspend fun removeUsers(id: String) = userRepository.removeUsers(id)
    suspend fun putUser(user: User) = userRepository.putUser(user)
}

