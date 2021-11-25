package com.uzlov.valitova.justcargo.model.entities

import java.time.OffsetTime

data class Trip(
    var id: Long?,
    var startTime: OffsetTime?,
    var endTime: OffsetTime?,
    var departure: String?,
    var destination: String?,
    var carrier: User?,
    var vehicle: Vehicle?,
    var trailer: Vehicle?,
    var hasSpace: Boolean?,
)
