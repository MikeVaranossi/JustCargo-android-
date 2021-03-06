package com.uzlov.valitova.justcargo.data.net

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// физ лицо или юрлицо
@Parcelize
data class UserType(
    var id: Long? = 0,
    var name: String? = "",
) : Parcelable
