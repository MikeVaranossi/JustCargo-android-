package com.uzlov.valitova.justcargo.app

class Constant {

    companion object {
        //        FIREBASE
        const val REQUESTS = "requests" // хранит Request.kt
        const val USERS = "users" // хранит User.kt
        const val VEHICLES = "vehicles" // хранит Vehicle.kt
        const val DELIVERY = "delvery" // хранит Delivery.kt

        //        KEYS
        const val KEY_DELIVERY_OBJECT = "delivery_key"
        const val KEY_REQUESTS_OBJECT = "request_key"
        const val KEY_REQUESTS_LOCAL_OBJECT = "request_local_key"

        //        PERMISSIONS
        const val MY_PERMISSIONS_REQUEST_CALL_PHONE: Int = 1001
    }
}