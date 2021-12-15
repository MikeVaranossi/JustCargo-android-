package com.uzlov.valitova.justcargo.ui.fragments.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.data.net.User
import com.uzlov.valitova.justcargo.databinding.FragmentPersonalDataLayoutBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import javax.inject.Inject

class PersonalDataFragment : BaseFragment<FragmentPersonalDataLayoutBinding>(
    FragmentPersonalDataLayoutBinding::inflate) {

    @Inject
    lateinit var authService: AuthService

    companion object {
        fun newInstance(): PersonalDataFragment {
            return PersonalDataFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.self_data_personal)
            it.setDisplayHomeAsUpEnabled(true)
        }

        addListeners()
        updateUI(authService.currentUser())
    }

    private fun updateUI(currentUser: User?) {
        currentUser?.let {
            if (it.userType?.id == 1L) viewBinding.tiLayoutDriverDocument.visibility = View.GONE
            viewBinding.tvProfileFullName.text = it.name
            viewBinding.etEmailProfile.setText(it.email)
            viewBinding.etPhoneProfile.setText(it.phone)
        }
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