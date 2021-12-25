package com.uzlov.valitova.justcargo.app

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.SuperscriptSpan
import com.uzlov.valitova.justcargo.data.local.FavoriteRequestLocal
import com.uzlov.valitova.justcargo.data.net.*


const val USER_SHARED_PREFERENCES = "USER"

fun String.lastToPower(): SpannableStringBuilder =
    if (length < 1) {
        SpannableStringBuilder()
    } else {
        SpannableStringBuilder(this).also {
            it.setSpan(SuperscriptSpan(),
                it.length - 1, it.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            it.setSpan(AbsoluteSizeSpan(26),
                it.length - 1, it.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

fun Request.inDateTimeInRange(dateRequest: Long): Boolean {
    return dateRequest >= deliveryTime ?: 0 && dateRequest <= deliveryTimeSecond ?: 0
}

fun Request.toFavoriteRequestLocal(): FavoriteRequestLocal {
    return FavoriteRequestLocal(
        id = this.id,
        requestTime = this.requestTime,
        deliveryTime = this.deliveryTime,
        cost = this.cost,
        departure = this.departure,
        destination = this.destination,
        description = this.description,
        shortInfo = this.shortInfo,
        weight = this.weight,
        length = this.length,
        width = this.width,
        height = this.height,
        status = this.status?.name
    )
}

// обратный маппинг для отправки на сервер
fun FavoriteRequestLocal.toRequestRemote(): Request {
    return Request(
        id = this.id,
        requestTime = this.requestTime,
        deliveryTime = this.deliveryTime,
        cost = this.cost,
        departure = this.departure,
        destination = this.destination,
        description = this.description,
        shortInfo = this.shortInfo,
        weight = this.weight,
        length = this.length,
        width = this.width,
        height = this.height,
        status = RequestStatus(0,this.status)
    )
}

/*
*   "Доставка" создается при отправке бронирования заявки
*   В случае успешного бронирования, в request меняется статус
*   В случае отказа бронирования - "Доставка удаляется"
*
*   p.s. Об изменении статуса присылать notification
* */
fun Delivery.create(user: User, request: Request): Delivery {
    return Delivery(
        id = System.currentTimeMillis(),
        request = request.copy(),
        trip = Trip(
            id = this.id,
            startTime = this.startTime,
            endTime = this.endTime,
            departure = request.departure,
            destination = request.destination,
            carrier = user.copy(),
            vehicle = Vehicle(id = 101,
                name = "Личное авто",
                owner = user.copy(),
                type = VehicleType(1, "Грузовик")
            ),
            trailer = Vehicle(id = 102,
                name = "Прицеп",
                owner = user.copy(),
                type = VehicleType(2, "Прицеп")
            )
        )
    )
}