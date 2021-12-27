package com.uzlov.valitova.justcargo.ui.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.FragmentSearchBinding
import com.uzlov.valitova.justcargo.ui.fragments.search.FindCargoFragment
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*

class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::inflate
) {

    private var searchRequest: Request = Request()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.search_cargo)
            it.setDisplayHomeAsUpEnabled(false)
        }

        addTextWatchers()
        initListeners()
    }

    private fun initListeners() {
        viewBinding.buttonFindCargo.setOnClickListener {
            with(viewBinding) {
                try {
                    searchRequest.departure = editTextFrom.text.toString().trim()
                    searchRequest.destination = editTextTo.text.toString().trim()

                } catch (e: NullPointerException) {
                    return@setOnClickListener
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FindCargoFragment.newInstance(searchRequest))
                .addToBackStack(null)
                .commit()
        }
        viewBinding.editTextDate.setOnClickListener {
            openDatePicker()
        }
        viewBinding.imageButtonCalendar.setOnClickListener {
            openDatePicker()
        }
    }

    private fun openDatePicker() {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = today
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.text_choose_date))
                .setCalendarConstraints(constraintsBuilder.build())
                .build()
        datePicker.show(parentFragmentManager, "tag")
        datePicker.addOnPositiveButtonClickListener {
            val selectedDate: Long? = datePicker.selection
            val simpleFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            viewBinding.editTextDate.setText(
                simpleFormat.format(selectedDate).toString()
            )
            searchRequest.deliveryTime = selectedDate
        }
    }

    private fun addTextWatchers() {
        viewBinding.editTextFrom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                verifyEmptyEditText()
            }
        })
        viewBinding.editTextTo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                verifyEmptyEditText()
            }
        })
    }

    private fun verifyEmptyEditText() {
        with(viewBinding) {
            buttonFindCargo.isEnabled = !editTextFrom.text.isNullOrBlank() &&
                    !editTextTo.text.isNullOrBlank()
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