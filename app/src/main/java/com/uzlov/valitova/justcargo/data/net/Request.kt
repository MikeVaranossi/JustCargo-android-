package com.uzlov.valitova.justcargo.data.net

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// файл запрос на перевозку от грузоотправителя
@Parcelize
data class Request(
    var id: Long? = 0,
    var requestTime: Long? = 0,
    var deliveryTime: Long? = 0,
    var cost: Int? = 0,
    var departure: String? = "",
    var destination: String? = "",
    var shortInfo: String? = "",
    var description: String? = "",
    var packagesNumber: Int? = 0,
    var weight: Float? = 0F,
    var length: Int? = 0,
    var width: Int? = 0,
    var height: Int? = 0,
    var owner: User? = null,
    var status: RequestStatus? = RequestStatus(1, "Открыта"),
) : Parcelable