package com.uzlov.valitova.justcargo.viemodels

import com.uzlov.valitova.justcargo.data.net.User
import com.uzlov.valitova.justcargo.repo.usecases.UsersUseCase
import javax.inject.Inject

class UsersViewModel @Inject constructor(private var usersUseCases: UsersUseCase?)  : BaseViewModel() {

    fun getUsers() = usersUseCases?.getUsers()

    fun getUser(id: String) = usersUseCases?.getUsers(id)

    fun addUser(user: User) = usersUseCases?.putUser(user)

    fun removeUser(id: String) = usersUseCases?.removeUsers(id)
}