package com.uzlov.valitova.justcargo.repo.datasources

import com.uzlov.valitova.justcargo.model.entities.User
import com.uzlov.valitova.justcargo.model.entities.UserClass
import com.uzlov.valitova.justcargo.model.entities.UserType

class UserRemoteDataSourceImpl : UsersRemoteDataSource {
    override fun getUsers(): List<User> {
        return emptyList()
    }

    override fun getUser(id: Long): User? {
        return User(0, "", "",false, "","","","", UserType(1, ""), UserClass(0 ,""))
    }

    override fun removeUsers(id: Long) {
    }

    override fun putUser(user: User) {

    }
}