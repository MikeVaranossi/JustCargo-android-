package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentLoginBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate) {

    var formatWatcher: MaskFormatWatcher? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.text_entrance)
            it.setDisplayHomeAsUpEnabled(false)
        }

        addTextChangedListener()
        setDecorPhone()
        viewBinding.btnSendSms.setOnClickListener {
            sendSmsClicked()
        }
        viewBinding.btnNewAccount.setOnClickListener {
            val manager = requireActivity().supportFragmentManager
            manager.apply {
                beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, RegistrationFragment())
                    .commit()
            }
        }
    }

    private fun sendSmsClicked(){
        val phone = formatWatcher?.mask?.toUnformattedString().toString()
        val manager = requireActivity().supportFragmentManager
        if(phone != null)
            manager.apply {
                beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, RegistrationSmsFragment.newInstance(phone))
                    .commit()
            }
    }

    private fun addTextChangedListener(){
        viewBinding.textInputPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                viewBinding.btnSendSms.isEnabled = isPhoneValid()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }


    private fun setDecorPhone(){
        val inputField = viewBinding.textInputPhone as EditText
        inputField.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                if (inputField.text.toString() == "")
                    inputField.setText("+")
        }

        inputField.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        formatWatcher = MaskFormatWatcher(
            MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        )

        formatWatcher!!.installOn(inputField)
    }

    private fun isPhoneValid():Boolean {
        val phone = formatWatcher?.mask?.toUnformattedString().toString()

        if (PhoneNumberUtils.formatNumberToE164(phone, "RU")==null){
            viewBinding.textInputPhone.error = getString(R.string.phone_is_incorrect)
            return false
        }else{
            viewBinding.textInputPhone.error = null
            return true
        }
    }

}