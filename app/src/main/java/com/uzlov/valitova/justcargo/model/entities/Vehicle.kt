package com.uzlov.valitova.justcargo.model.entities

data class Vehicle(
    var id: Long?,
    var name: String?,
    var description: String?,
    var owner: User?,
    var type: VehicleType?,
    var registrationNumber: String?,
    var carryingCapacity: Int?,
    var length: Int?,
    var width: Int?,
    var height: Int?,
    var volume: Float?,
)
