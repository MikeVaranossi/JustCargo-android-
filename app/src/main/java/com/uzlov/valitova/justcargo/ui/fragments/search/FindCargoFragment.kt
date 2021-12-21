package com.uzlov.valitova.justcargo.ui.fragments.search

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentFindCargoBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment

class FindCargoFragment : BaseFragment<FragmentFindCargoBinding>(
    FragmentFindCargoBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.find_cargo)
            it.setDisplayHomeAsUpEnabled(true)
        }

        viewBinding.buttonSort.setOnClickListener {
            showPopUp(it)
        }
    }

    private fun showPopUp(view: View) {
        val wrapper = ContextThemeWrapper(view.context, R.style.MyStyle_PopupMenu)
        val popupMenu = context?.let { PopupMenu(wrapper, view) }
        val inflater = popupMenu?.menuInflater
        inflater?.inflate(R.menu.sort_popup_menu, popupMenu.menu)
        popupMenu?.show()
        popupMenu?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item1 -> {
                    Toast.makeText(view.context, it.title, Toast.LENGTH_SHORT).show()
                }
                R.id.item2 -> {
                    Toast.makeText(view.context, it.title, Toast.LENGTH_SHORT).show()
                }
                R.id.item3 -> {
                    Toast.makeText(view.context, it.title, Toast.LENGTH_SHORT).show()
                }
                R.id.item4 -> {
                    Toast.makeText(view.context, it.title, Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    companion object {
        fun newInstance() =
            FindCargoFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}