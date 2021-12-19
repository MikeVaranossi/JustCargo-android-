package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.R.attr
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.data.net.User
import com.uzlov.valitova.justcargo.data.net.UserClass
import com.uzlov.valitova.justcargo.data.net.UserType
import com.uzlov.valitova.justcargo.databinding.FragmentRegistrationBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import com.uzlov.valitova.justcargo.R
import ru.tinkoff.decoro.slots.PredefinedSlots
import android.R.attr.phoneNumber
import android.telephony.PhoneNumberUtils


class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(
    FragmentRegistrationBinding::inflate) {

    //пока оставим здесь в дальнейшем будем получать с сервера
    private val items = listOf("Грузоотправитель", "Грузоперевозчик" )
    var formatWatcher: MaskFormatWatcher? = null

    private fun sendSmsClicked(){
        val newUser = User(1, "", "", true,
            name = viewBinding.textInputFio.text.toString(),
            "",
            phone = formatWatcher?.mask?.toUnformattedString().toString(),
            email = viewBinding.textInputEmail.text.toString(),
            userType = UserType(
                items.indexOf(viewBinding.textfieldActivityProfile.editText?.text.toString()) + 1L,
                viewBinding.textfieldActivityProfile.editText?.text.toString()),
            userClass = UserClass(1, " ")
        )

        val manager = requireActivity().supportFragmentManager
        manager.apply {
            beginTransaction()
                .replace(R.id.container, RegistrationSmsFragment.newInstance(newUser))
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
        setDecorPhone()
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
        override fun afterTextChanged(s: Editable?) {
            verifyEmptyEditText()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun verifyEmptyEditText() {
        var buttonEnable = !(viewBinding.textInputFio.text.isNullOrEmpty() ||
                viewBinding.productCategoryDropdown.text.isNullOrEmpty() ||
                viewBinding.textInputPhone.text.isNullOrEmpty() ||
                viewBinding.textInputEmail.text.isNullOrEmpty() ||
                (viewBinding.textfieldActivityProfile.editText?.text.toString()  == "Грузоперевозчик" &&
                        viewBinding.textInputDriverDoc.text.isNullOrEmpty()))

        if (viewBinding.textfieldActivityProfile.editText?.text.toString()  == "Грузоперевозчик"){
            viewBinding.textfieldDriverDoc.visibility = View.VISIBLE
        }else{
            viewBinding.textfieldDriverDoc.visibility = View.INVISIBLE
        }
        val emailCorrected = isEmailValid()
        val phoneCorrected = isPhoneValid()
        if (!buttonEnable || !emailCorrected || !phoneCorrected) {
            buttonEnable = false
        }

        viewBinding.btnSendSms.isEnabled = buttonEnable
    }

    private fun setDecorPhone(){
        val inputField = viewBinding.textInputPhone as EditText
        inputField.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
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

    private fun isEmailValid():Boolean {
        if (!TextUtils.isEmpty(viewBinding.textInputEmail.text) &&
            Patterns.EMAIL_ADDRESS.matcher(viewBinding.textInputEmail.text.toString()).matches()
        ) {
            viewBinding.textInputEmail.error = null
            return true
        } else {
            viewBinding.textInputEmail.error = getString(R.string.email_is_incorrect)
            return false
        }
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