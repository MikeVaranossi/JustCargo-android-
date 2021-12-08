package com.uzlov.valitova.justcargo.ui.fragments.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentPersonalDataLayoutBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment

class PersonalDataFragment : BaseFragment<FragmentPersonalDataLayoutBinding>(
    FragmentPersonalDataLayoutBinding::inflate) {

    companion object {
        fun newInstance(): PersonalDataFragment {
            return PersonalDataFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.self_data_personal)
            it.setDisplayHomeAsUpEnabled(true)
        }

        addListeners()
    }

    private fun addListeners() {
        with(viewBinding) {
            etEmailProfile.addTextChangedListener(EmailTextChecker())
        }
    }

    inner class EmailTextChecker : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            if (!TextUtils.isEmpty(viewBinding.etEmailProfile.text) &&
                Patterns.EMAIL_ADDRESS.matcher(viewBinding.etEmailProfile.text.toString()).matches()
            ) {
                viewBinding.etEmailProfile.error = null
            } else {
                viewBinding.etEmailProfile.error = getString(R.string.email_is_incorrect)
            }
        }
    }
}