package com.uzlov.valitova.justcargo.model.entities

import java.time.OffsetTime

data class Delivery(
    var id: Long?,
    var request: Request?,
    var trip: Trip?,
    var startTime: OffsetTime,
    var endTime: OffsetTime,
)
