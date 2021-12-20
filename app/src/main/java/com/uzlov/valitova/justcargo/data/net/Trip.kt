package com.uzlov.valitova.justcargo.data.net

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// аналог реквест от грузоперевозчиука
@Parcelize
data class Trip(
    var id: Long? = 0,
    var startTime: Long? = 0,
    var endTime: Long? = 0,
    var departure: String? = "",
    var destination: String? = "",
    var carrier: User? = null,
    var vehicle: Vehicle? = null,
    var trailer: Vehicle? = null,
    var hasSpace: Boolean? = false,
) : Parcelable
