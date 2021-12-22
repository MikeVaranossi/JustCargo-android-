package com.uzlov.valitova.justcargo.data.net

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vehicle(
    var id: Long? = 0,
    var name: String? = "",
    var description: String? = "",
    var owner: User? = null,
    var type: VehicleType? = null,
    var registrationNumber: String? = "",
    var carryingCapacity: Int? = 0,
    var length: Int? = 0,
    var width: Int? = 0,
    var height: Int? = 0,
    var volume: Float? = 0F,
) : Parcelable
