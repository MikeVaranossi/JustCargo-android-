package com.uzlov.valitova.justcargo.ui.fragments


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentSearchBinding
import com.uzlov.valitova.justcargo.ui.fragments.search.FindCargoFragment
import java.text.SimpleDateFormat
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
        viewBinding.tvAdvancedSearch.setOnClickListener {
            Toast.makeText(context, "Данная функция будет доступна совсем скоро. Следите за обновлениями!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openDatePicker() {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = today
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText(getString(R.string.text_choose_date))
                .setCalendarConstraints(constraintsBuilder.build())
                .build()
        dateRangePicker.show(parentFragmentManager, "tag")
        dateRangePicker.addOnPositiveButtonClickListener {
            val selectedDates: androidx.core.util.Pair<Long, Long>? = dateRangePicker.selection
            val (startDate, endDate) = Pair(
                Date((selectedDates?.first as Long)),
                Date((selectedDates.second as Long))
            )
            val simpleFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
            viewBinding.editTextDate.setText(getString(
                R.string.for_date,
                simpleFormat.format(startDate).toString(),
                simpleFormat.format(endDate).toString()
            ))
        }

    }

    companion object {
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}