package com.uzlov.valitova.justcargo.data.local

import com.uzlov.valitova.justcargo.data.net.RequestStatus
import java.time.OffsetTime

data class RequestLocal (
    var id: Long? = 0,
    var requestTime: OffsetTime? = null,
    var cost: Int? = 0,
    var departure: String? = "",
    var destination: String? = "",
    var shortInfo: String? = "",
    var weight: Float? = 0F,
    var length: Int? = 0,
    var width: Int? = 0,
    var height: Int? = 0,
    var status: RequestStatus? = null,
)
