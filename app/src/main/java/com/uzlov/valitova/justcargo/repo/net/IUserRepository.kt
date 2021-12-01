package com.uzlov.valitova.justcargo.repo.net

import com.uzlov.valitova.justcargo.model.entities.User

interface IUserRepository {
    fun getUsers() : List<User>
    fun getUser(id: Long) : User?
    fun removeUsers(id: Long)
    fun putUser(user: User)
}