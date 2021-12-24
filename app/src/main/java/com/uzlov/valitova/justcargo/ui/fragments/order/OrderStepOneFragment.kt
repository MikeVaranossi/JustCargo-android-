package com.uzlov.valitova.justcargo.ui.fragments.order

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.FragmentOrderStepOneBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import java.lang.NumberFormatException
import java.util.*
import java.text.SimpleDateFormat
import javax.inject.Inject


class OrderStepOneFragment :
    BaseFragment<FragmentOrderStepOneBinding>(FragmentOrderStepOneBinding::inflate) {
    @Inject
    lateinit var authService: AuthService

    private var request: Request = Request()
    private var addressFrom = String()
    private var addressTo = String()
    val fragment = SelectMapPositionsFragment.newInstance("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
    }

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
        viewBinding.textInputCost.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                verifyEmptyEditText()
            }
        })
    }





    private fun initListeners() {

        viewBinding.textInputFrom.setOnClickListener {
            openMapFragment()
            setAddressFrom()

        }
        viewBinding.textInputTo.setOnClickListener {
            viewBinding.textInputFrom.setText(addressFrom)
            openMapFragment()
            setAddressTo()

        }
        viewBinding.textDate.setOnClickListener {
            viewBinding.textInputTo.setText(addressTo)
            openDatePicker()

        }
        viewBinding.buttonNextStep.setOnClickListener {

            with(viewBinding) {
                request.id = System.currentTimeMillis()
                request.shortInfo = textInputName.text.toString()
                request.departure = textInputFrom.text.toString()
                request.destination = textInputTo.text.toString()
                try {
                    request.cost = textInputCost.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    request.cost = 0
                    Toast.makeText(context, "Неверный формат ввода суммы", Toast.LENGTH_SHORT)
                        .show()
                }
                request.owner = authService.currentUser()

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
                    !textDate.text.isNullOrEmpty() &&
                    !textInputCost.text.isNullOrEmpty()

        }
    }
private fun setAddressFrom(){
    fragment.setActionListener(object : SelectMapPositionsFragment.ActionListener {
        override fun select(address: String, latitude: Double, longitude: Double) {
            addressFrom = address
        }
    })
    viewBinding.textInputFrom.setText(addressFrom)
}
    private fun setAddressTo(){
        fragment.setActionListener(object : SelectMapPositionsFragment.ActionListener {
            override fun select(address: String, latitude: Double, longitude: Double) {
                addressTo = address
            }
        })
    }
    private fun openMapFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

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
            viewBinding.textDate.setText(
                getString(
                    R.string.for_date,
                    simpleFormat.format(startDate).toString(),
                    simpleFormat.format(endDate).toString()
                )
            )
            // в request теперь непонятно как добавлять - в лонг не запихнешь, пока оставила первую дату
            request.deliveryTime = Date((selectedDates.first as Long)).time
        }

    }

    companion object {
        fun newInstance() = OrderStepOneFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}