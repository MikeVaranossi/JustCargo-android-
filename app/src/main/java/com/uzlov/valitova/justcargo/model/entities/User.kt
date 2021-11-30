package com.uzlov.valitova.justcargo.model.entities

data class User(
    var id: Long?,
    var login: String?,
    var password: String?,
    var enabled: Boolean?,
    var name: String?,
    var surname: String?,
    var phone: String?,
    var email: String?,
    var userType: UserType?,
    var userClass: UserClass?,
)
