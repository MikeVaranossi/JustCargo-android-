package com.uzlov.valitova.justcargo.model.entities

import java.time.OffsetTime

data class Delivery(
    var id: Long? = 0,
    var request: Request? = null,
    var trip: Trip? = null,
    var startTime: OffsetTime? = null,
    var endTime: OffsetTime? = null,
)
