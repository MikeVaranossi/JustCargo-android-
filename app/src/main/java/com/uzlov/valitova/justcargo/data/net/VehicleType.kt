package com.uzlov.valitova.justcargo.data.net

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VehicleType(
    var id: Long? = 0,
    var name: String? = null,

) : Parcelable
