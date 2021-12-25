package com.uzlov.valitova.justcargo.ui.fragments.order

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.data.net.Request
import com.uzlov.valitova.justcargo.databinding.FragmentOrderStepTwoBinding
import com.uzlov.valitova.justcargo.repo.usecases.RequestsUseCases
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import javax.inject.Inject

class OrderStepTwoFragment :
    BaseFragment<FragmentOrderStepTwoBinding>(FragmentOrderStepTwoBinding::inflate) {

    @Inject
    lateinit var requestsUseCases: RequestsUseCases

    private var request: Request = Request()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.label_order_step_two)
            it.setDisplayHomeAsUpEnabled(true)
        }

        initTextWatchers()
        initListeners()

        arguments?.let {
            it.getParcelable<Request>(Constant.KEY_REQUESTS_OBJECT)?.let { _request ->
                request = _request.copy()
            }
        }
    }

    private fun initListeners() {
        viewBinding.buttonCreateRequest.setOnClickListener {
            with(viewBinding) {
                try {
                    request.height = textInputHeight.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    textInputHeight.error = getString(R.string.incorrect_number)
                    return@setOnClickListener
                }
                try {
                    request.weight = textInputWeight.text.toString().toFloat()
                } catch (e: NumberFormatException) {
                    textInputWeight.error = getString(R.string.incorrect_number)
                    return@setOnClickListener
                }
                try {
                    request.width = textInputWidth.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    textInputWidth.error = getString(R.string.incorrect_number)
                    return@setOnClickListener
                }
                try {
                    request.length = textInputLength.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    textInputLength.error = getString(R.string.incorrect_number)
                    return@setOnClickListener
                }
                request.description = textInputAddComment.text.toString()
            }
            // сохраняем заявку на бэк
            requestsUseCases.putRequest(request)

            // возврат на главный экран
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OrderCreatedFragment.newInstance()).commit()
        }
    }

    private fun initTextWatchers() {
        viewBinding.textInputHeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                verifyEmptyEditText()
            }
        })
        viewBinding.textInputLength.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                verifyEmptyEditText()
            }
        })
        viewBinding.textInputWeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                verifyEmptyEditText()
            }
        })
        viewBinding.textInputWidth.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                verifyEmptyEditText()

            }
        })
        viewBinding.buttonCreateRequest.setOnClickListener {
            requestsUseCases.putRequest(request)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OrderCreatedFragment.newInstance())
                .commit()
        }
    }

    private fun verifyEmptyEditText() {
        with(viewBinding) {
            buttonCreateRequest.isEnabled = !textInputWeight.text.isNullOrEmpty() &&
                    !textInputLength.text.isNullOrEmpty() &&
                    !textInputWidth.text.isNullOrEmpty() &&
                    !textInputHeight.text.isNullOrEmpty()

            if (!isInteger(textInputWidth.text.toString())) {
                textInputWidth.error = "Введите целое число"
                buttonCreateRequest.isEnabled = false
            }
            if (!isInteger(textInputLength.text.toString())) {
                textInputLength.error = "Введите целое число"
                buttonCreateRequest.isEnabled = false
            }
            if (!isInteger(textInputHeight.text.toString())) {
                textInputHeight.error = "Введите целое число"
                buttonCreateRequest.isEnabled = false
            }
        }

    }

    private fun isInteger(input: String): Boolean {
        val integerChars = '0'..'9'
        return input.all { it in integerChars }
    }

    companion object {
        fun newInstance(request: Request) = OrderStepTwoFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constant.KEY_REQUESTS_OBJECT, request)
            }
        }
    }
}