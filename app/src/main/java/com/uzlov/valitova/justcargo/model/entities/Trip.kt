package com.uzlov.valitova.justcargo.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.OffsetTime

// аналог реквест от грузоперевозчиука
@Parcelize
data class Trip(
    var id: Long? = 0,
    var startTime: OffsetTime?,
    var endTime: OffsetTime?,
    var departure: String? = "",
    var destination: String? = "",
    var carrier: User? = null,
    var vehicle: Vehicle? = null,
    var trailer: Vehicle? = null,
    var hasSpace: Boolean? = false,
) : Parcelable
