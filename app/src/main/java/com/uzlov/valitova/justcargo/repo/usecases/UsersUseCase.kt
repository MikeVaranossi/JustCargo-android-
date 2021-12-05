package com.uzlov.valitova.justcargo.repo.usecases

import com.uzlov.valitova.justcargo.model.entities.User
import com.uzlov.valitova.justcargo.repo.net.IUserRepository
import javax.inject.Inject

class UsersUseCase @Inject constructor(var userRepository: IUserRepository) {
     fun getUsers() = userRepository.getUsers()
     fun getUsers(id: String) = userRepository.getUser(id)
     fun removeUsers(id: String) = userRepository.removeUsers(id)
     fun putUser(user: User) = userRepository.putUser(user)
}

