package com.uzlov.valitova.justcargo.ui.fragments


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentSearchBinding
import com.uzlov.valitova.justcargo.ui.fragments.search.FindCargoFragment
import java.util.*

class SearchFragment : Fragment() {
    private var _viewBinding: FragmentSearchBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(layoutInflater, container, false).also {
        _viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.search_cargo)
            it.setDisplayHomeAsUpEnabled(true)
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
                                R.string.for_date,
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

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }


    companion object {
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}