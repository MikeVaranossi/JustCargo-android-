package com.uzlov.valitova.justcargo.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentOrderStepThreeBinding
import com.uzlov.valitova.justcargo.model.entities.User
import com.uzlov.valitova.justcargo.model.entities.UserClass
import com.uzlov.valitova.justcargo.model.entities.UserType
import com.uzlov.valitova.justcargo.repo.datasources.UserRemoteDataSourceImpl

class OrderStepThreeFragment: Fragment() {
    private var _viewBinding: FragmentOrderStepThreeBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentOrderStepThreeBinding.inflate(layoutInflater, container, false).also {
        _viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OrderCreatedFragment.newInstance())
                .commit()

        }

    }

    private fun verifyEmptyEditText() {
        var buttonEnable = true
        when{
            viewBinding.textInputWeight.text.isNullOrEmpty() -> buttonEnable = false
            viewBinding.textInputWidth.text.isNullOrEmpty() -> buttonEnable = false
            viewBinding.textInputLength.text.isNullOrEmpty() -> buttonEnable = false
            viewBinding.textInputHeight.text.isNullOrEmpty() -> buttonEnable = false
        }
        viewBinding.buttonCreateRequest.isEnabled = buttonEnable

    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = OrderStepThreeFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}