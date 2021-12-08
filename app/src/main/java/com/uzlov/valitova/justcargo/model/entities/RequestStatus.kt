package com.uzlov.valitova.justcargo.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// открыта или закрыта
@Parcelize
data class RequestStatus(
    var id: Long? = 0,
    var name: String? = "",
) : Parcelable
