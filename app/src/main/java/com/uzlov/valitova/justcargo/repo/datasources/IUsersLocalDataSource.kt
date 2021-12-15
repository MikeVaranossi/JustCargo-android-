package com.uzlov.valitova.justcargo.repo.datasources

import com.uzlov.valitova.justcargo.data.net.User

interface IUsersLocalDataSource {
    fun getUsers() : List<User>
    fun getUser(id: String)
    fun removeUsers(id: String)
    fun putUser(user: User)
}