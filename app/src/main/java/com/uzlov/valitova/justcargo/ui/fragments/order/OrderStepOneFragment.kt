package com.uzlov.valitova.justcargo.ui.fragments.order

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentOrderStepOneBinding
import java.util.*


class OrderStepOneFragment : Fragment() {
    private var _viewBinding: FragmentOrderStepOneBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentOrderStepOneBinding.inflate(layoutInflater, container, false).also {
        _viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.label_order_step_one)
            it.setDisplayHomeAsUpEnabled(true)
        }

        viewBinding.textDate.setOnClickListener {
            openDatePicker()
        }

        viewBinding.buttonNextStep.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OrderStepTwoFragment.newInstance())
                .commit()
        }
    }

    private fun verifyEmptyEditText() {
        var buttonEnable = true
        when{
            viewBinding.textInputName.text.isNullOrEmpty() -> buttonEnable = false
            viewBinding.textInputFrom.text.isNullOrEmpty() -> buttonEnable = false
            viewBinding.textInputTo.text.isNullOrEmpty() -> buttonEnable = false
            viewBinding.textDate.text.isNullOrEmpty() -> buttonEnable = false
        }
        viewBinding.buttonNextStep.isEnabled = buttonEnable

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
                        viewBinding.textDate.setText(
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
        fun newInstance() = OrderStepOneFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}