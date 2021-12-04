package com.uzlov.valitova.justcargo.repo.datasources

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.model.entities.User

interface UsersRemoteDataSource {
    fun getUsers() : LiveData<List<User>>
    fun getUser(id: String) : LiveData<User?>
    fun removeUsers(id: String)
    fun putUser(user: User)
}