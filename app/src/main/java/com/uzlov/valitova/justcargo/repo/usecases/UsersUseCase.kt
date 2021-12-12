package com.uzlov.valitova.justcargo.repo.usecases

import com.uzlov.valitova.justcargo.data.net.User
import com.uzlov.valitova.justcargo.repo.net.IUserRepository
import javax.inject.Inject

class UsersUseCase @Inject constructor(var userRepository: IUserRepository) {
     fun getUsers() = userRepository.getUsers()
     fun getUsers(id: Int) = userRepository.getUser(id)
     fun removeUsers(id: Int) = userRepository.removeUsers(id)
     fun putUser(user: User) = userRepository.putUser(user)
}

