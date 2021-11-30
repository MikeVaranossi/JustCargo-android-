package com.uzlov.valitova.justcargo.repo.datasources

import com.uzlov.valitova.justcargo.model.entities.User

interface UsersLocalDataSource {
    fun getUsers() : List<User>
    fun getUser(id: Long)
    fun removeUsers(id: Long)
    fun putUser(user: User)
}