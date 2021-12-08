package com.uzlov.valitova.justcargo.ui.fragments.order

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentOrderStepOneBinding
import com.uzlov.valitova.justcargo.model.entities.Request
import com.uzlov.valitova.justcargo.model.entities.User
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*


class OrderStepOneFragment :
    BaseFragment<FragmentOrderStepOneBinding>(FragmentOrderStepOneBinding::inflate) {

    private var request: Request = Request()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.label_order_step_one)
            it.setDisplayHomeAsUpEnabled(true)
        }

        addTextWatchers()
        initListeners()
    }

    private fun addTextWatchers() {
        viewBinding.textInputName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                verifyEmptyEditText()
            }
        })
        viewBinding.textInputFrom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                verifyEmptyEditText()
            }
        })
        viewBinding.textInputTo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                verifyEmptyEditText()
            }
        })
        viewBinding.textDate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                verifyEmptyEditText()
            }
        })
    }

    private fun initListeners() {
        viewBinding.textDate.setOnClickListener {
            openDatePicker()
        }

        viewBinding.buttonNextStep.setOnClickListener {

            with(viewBinding) {
                request.id = System.currentTimeMillis()
                request.shortInfo = textInputName.text.toString()
                request.departure = textInputFrom.text.toString()
                request.destination = textInputTo.text.toString()
                request.cost = 10000
                request.owner = User(phone = "89992008289") // здесь будет браться реальная информация
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OrderStepTwoFragment.newInstance(request))
                .addToBackStack(null)
                .commit()
        }
    }

    private fun verifyEmptyEditText() {
        with(viewBinding) {
            buttonNextStep.isEnabled = !textInputName.text.isNullOrEmpty() &&
                    !textInputFrom.text.isNullOrEmpty() &&
                    !textInputTo.text.isNullOrEmpty() &&
                    !textDate.text.isNullOrEmpty()

        }
    }

    private fun openDatePicker() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd =
            DatePickerDialog(
                requireContext(),
                { w, year, monthOfYear, dayOfMonth ->
                    viewBinding.textDate.setText(
                        getString(
                            R.string.for_date,
                            dayOfMonth,
                            (monthOfYear + 1),
                            year
                        )
                    )

                    request.deliveryTime = Date(year, monthOfYear, dayOfMonth).time
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            OffsetDateTime.of(LocalDateTime.of(year, monthOfYear, dayOfMonth, 12, 0, 0), ZoneOffset.UTC)
//                    } else {
//                        // нужна реализация для устройств на версии < 26
//                    }
                },
                year,
                month,
                day
            )
        dpd.datePicker.minDate = System.currentTimeMillis() - 1000
        dpd.show()
    }


    companion object {
        fun newInstance() = OrderStepOneFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}