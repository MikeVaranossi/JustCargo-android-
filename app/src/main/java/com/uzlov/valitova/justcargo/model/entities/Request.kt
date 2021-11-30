package com.uzlov.valitova.justcargo.model.entities

import java.time.OffsetTime

data class Request(
    var id: Long?,
    var requestTime: OffsetTime?,
    var deliveryTime: OffsetTime?,
    var cost: Int?,
    var departure: String?,
    var destination: String?,
    var shortInfo: String?,
    var description: String?,
    var packagesNumber: Int?,
    var weight: Float?,
    var length: Int?,
    var width: Int?,
    var height: Int?,
    var owner: User?,
    var status: RequestStatus?,
)
