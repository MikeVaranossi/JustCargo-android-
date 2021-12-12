package com.uzlov.valitova.justcargo.repo.net

import androidx.lifecycle.LiveData
import com.uzlov.valitova.justcargo.data.net.User

interface IUserRepository {
    fun getUsers() : LiveData<List<User>>
    fun getUser(id: Int) :  LiveData<User?>
    fun removeUsers(id: Int)
    fun putUser(user: User)
}