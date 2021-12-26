package com.uzlov.valitova.justcargo.app

class Constant {

    companion object {

        //        FIREBASE
        const val REQUESTS = "requests" // хранит Request.kt

        const val USERS = "users" // хранит User.kt
        const val VEHICLES = "vehicles" // хранит Vehicle.kt
        const val DELIVERY = "deliveries" // хранит Delivery.kt

        // type user
        const val SENDER = 1L
        const val CARRIER = 2L

        //        KEYS
        const val KEY_DELIVERY_OBJECT = "delivery_key"
        const val KEY_REQUESTS_OBJECT = "request_key"
        const val KEY_REQUESTS_LOCAL_OBJECT = "request_local_key"
        const val KEY_FROM_HOST_ACTIVITY = "from_host_activity_key"
        const val KEY_TITLE = "key_title"

        //        PERMISSIONS
        const val MY_PERMISSIONS_REQUEST_CALL_PHONE: Int = 1001

        // STATES REQUESTS
        const val STATE_OPEN = 200 // заявка открыта, отправитель никого еще не подтвердил
        const val STATE_IN_PROGRESS = 201 // отправитель подтвердил бронь (перевозчику)
        const val STATE_COMPLETE =
            202 //заявка выполнена (после того как подтвердили что груз доставили)
        const val STATE_NOT_COMPLETED =
            203 // заявка не выполнена (перевозчик отменил доставку (после брони уже) тогда удаляется delivery а в request выставляется статус этот)
        const val STATE_CANCELLED =
            204 // заявка отменена (отправитель отменил перевозку - то все delivery удаляются)

        const val STATE_OPEN_MESSAGE = "Заявка открыта"
        const val STATE_IN_PROGRESS_MESSAGE = "Бронирование подтверждено"
        const val STATE_COMPLETE_MESSAGE = "Перевозка завершена"
        const val STATE_NOT_COMPLETED_MESSAGE = "Не выполнена"
        const val STATE_CANCELLED_MESSAGE = "Отменена"


    }
}