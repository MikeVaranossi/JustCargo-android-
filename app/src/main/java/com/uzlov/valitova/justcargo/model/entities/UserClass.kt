package com.uzlov.valitova.justcargo.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// отправитель или пеереевозщчик
@Parcelize
data class UserClass(
   var id: Long? = 0,
   var name: String? = "",
) : Parcelable {

}
