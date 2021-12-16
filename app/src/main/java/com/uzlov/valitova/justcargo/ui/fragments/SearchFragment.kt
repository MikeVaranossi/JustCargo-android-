package com.uzlov.valitova.justcargo.ui.fragments


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentSearchBinding
import com.uzlov.valitova.justcargo.ui.fragments.search.FindCargoFragment
import java.util.*

class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.search_cargo)
        }
        viewBinding.buttonFindCargo.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FindCargoFragment.newInstance())
                .commit()
        }
        viewBinding.imageButtonCalendar.setOnClickListener {
            openDatePicker()
        }
    }

    private fun openDatePicker() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd =
            context?.let {
                DatePickerDialog(
                    it,
                    { _, year, monthOfYear, dayOfMonth ->
                        viewBinding.editTextDate.setText(
                            getString(
                                R.string.for_date_search,
                                dayOfMonth,
                                (monthOfYear + 1),
                                year
                            )
                        )
                    },
                    year,
                    month,
                    day
                )
            }
        dpd?.datePicker?.minDate = System.currentTimeMillis() - 1000
        dpd?.show()
    }

    companion object {
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}