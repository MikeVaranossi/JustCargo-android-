package com.uzlov.valitova.justcargo.app

class Constant {

    companion object {
        //        FIREBASE
        const val REQUESTS = "requests" // хранит Request.kt

        const val USERS = "users" // хранит User.kt
        const val VEHICLES = "vehicles" // хранит Vehicle.kt
        const val DELIVERY = "delvery" // хранит Delivery.kt

        // type user
        const val SENDER = 1L
        const val CARRIER = 2L

        //        KEYS
        const val KEY_DELIVERY_OBJECT = "delivery_key"
        const val KEY_REQUESTS_OBJECT = "request_key"
        const val KEY_REQUESTS_LOCAL_OBJECT = "request_local_key"
        const val KEY_FROM_HOST_ACTIVITY = "from_host_activity_key"

        //        PERMISSIONS
        const val MY_PERMISSIONS_REQUEST_CALL_PHONE: Int = 1001
    }
}