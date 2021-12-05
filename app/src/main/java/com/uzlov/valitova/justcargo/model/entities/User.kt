package com.uzlov.valitova.justcargo.model.entities


data class User(
    var id: Long? = 0,
    var login: String? = "",
    var password: String? = "",
    var enabled: Boolean? = true,
    var name: String? = "",
    var surname: String? = "",
    var phone: String? = "",
    var email: String? = "",
    var userType: UserType? = null,
    var userClass: UserClass? = null,
) {
}
