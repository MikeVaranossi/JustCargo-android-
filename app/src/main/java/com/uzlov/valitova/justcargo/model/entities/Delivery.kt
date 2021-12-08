package com.uzlov.valitova.justcargo.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.OffsetTime

// сущность самой сделки (при принятии заявки) по завершению выставляется время заврешения и в requests
@Parcelize
data class Delivery(
    var id: Long? = 0,
    var request: Request? = null,
    var trip: Trip? = null,
    var startTime: OffsetTime? = null,
    var endTime: OffsetTime? = null
) : Parcelable
