package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentRegistrationBinding
import com.uzlov.valitova.justcargo.databinding.FragmentRegistrationCompleteBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(
    FragmentRegistrationBinding::inflate) {

    private fun sendSmsClicked(){
        val manager = requireActivity().supportFragmentManager
        manager.apply {
            beginTransaction()
                .replace(R.id.container, RegistrationSmsFragment.newInstance( viewBinding.textInputPhone.text.toString()))
                .commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.text_registration)
            it.setDisplayHomeAsUpEnabled(false)
        }

        viewBinding.btnSendSms.setOnClickListener {
            sendSmsClicked()
        }

        addTextChangedListener()

        val items = listOf("Грузоперевозчик", "Грузоотправитель")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_activity_profile, items)
        (viewBinding.textfieldActivityProfile.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun addTextChangedListener(){
        viewBinding.textInputFio.addTextChangedListener(TextFieldValidation())
        viewBinding.textfieldActivityProfile.editText?.addTextChangedListener(TextFieldValidation())
        viewBinding.textInputPhone.addTextChangedListener(TextFieldValidation())
        viewBinding.textInputEmail.addTextChangedListener(TextFieldValidation())
        viewBinding.textInputDriverDoc.addTextChangedListener(TextFieldValidation())
    }

    inner class TextFieldValidation : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            verifyEmptyEditText()
        }
    }

    private fun verifyEmptyEditText() {
        val buttonEnable = !(viewBinding.textInputFio.text.isNullOrEmpty() ||
                viewBinding.productCategoryDropdown.text.isNullOrEmpty() ||
                viewBinding.textInputPhone.text.isNullOrEmpty() ||
                viewBinding.textInputEmail.text.isNullOrEmpty() ||
                (viewBinding.textfieldActivityProfile.editText?.text.toString()  == "Грузоперевозчик" &&
                        viewBinding.textInputDriverDoc.text.isNullOrEmpty()))

        viewBinding.btnSendSms.isEnabled = buttonEnable
    }

}