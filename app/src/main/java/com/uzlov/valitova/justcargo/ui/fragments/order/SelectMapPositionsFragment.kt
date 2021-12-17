package com.uzlov.valitova.justcargo.ui.fragments.order

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// created by Uzlov
// Фрагмент выбора местоположения отправления / пункта назначения
class SelectMapPositionsFragment(private var actionListener: ActionListener? = null) : BottomSheetDialogFragment() {

    interface ActionListener {
        fun select()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        actionListener = null
    }
}