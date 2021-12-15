package com.uzlov.valitova.justcargo.auth

import com.uzlov.valitova.justcargo.data.net.User

interface IStateAuth {
    fun login(user: User)
    fun logout()
}