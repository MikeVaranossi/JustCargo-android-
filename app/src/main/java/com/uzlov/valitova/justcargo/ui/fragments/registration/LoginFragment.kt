package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var viewBinding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return viewBinding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addTextChangedListener()
        viewBinding?.btnSendSms?.setOnClickListener {
            sendSmsClicked()
        }
        viewBinding?.btnNewAccount?.setOnClickListener {
            val manager = requireActivity().supportFragmentManager
            manager.apply {
                beginTransaction()
                    .replace(R.id.container, RegistrationFragment())
                    .addToBackStack("")
                    .commit()
            }
        }
    }

    private fun sendSmsClicked(){
        // TODO: добавить проверки на валидный телефон и почту
        val manager = requireActivity().supportFragmentManager
        manager.apply {
            beginTransaction()
                .replace(R.id.container, RegistrationSmsFragment.newInstance())
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    private fun addTextChangedListener(){
        viewBinding?.textInputPhone?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewBinding?.btnSendSms?.isEnabled = !viewBinding?.textInputPhone?.text.isNullOrEmpty()
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}