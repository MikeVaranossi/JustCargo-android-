 package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentRegistrationBinding


 /**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RegistrationFragment : Fragment() {

    private var viewBinding: FragmentRegistrationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return viewBinding!!.root

    }

     private fun sendSmsClicked(){
        val manager = requireActivity().supportFragmentManager
        manager.apply {
            beginTransaction()
                .replace(R.id.container, RegistrationSmsFragment.newInstance())
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.btnSendSms?.setOnClickListener {
            sendSmsClicked()
        }

        addTextChangedListener()

        val items = listOf("Грузоперевозчик", "Грузоотправитель")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_activity_profile, items)
        (viewBinding?.textfieldActivityProfile?.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun addTextChangedListener(){
        viewBinding?.textInputFio?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                verifyEmptyEditText()
            }
        })
        viewBinding?.textfieldActivityProfile?.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (viewBinding?.textfieldActivityProfile?.editText?.text.toString() == "Грузоперевозчик"){
                    viewBinding?.textfieldDriverDoc?.visibility = View.VISIBLE
                    viewBinding?.textViewDriverDoc?.visibility = View.VISIBLE
                }else{
                    viewBinding?.textfieldDriverDoc?.visibility = View.INVISIBLE
                    viewBinding?.textViewDriverDoc?.visibility = View.INVISIBLE
                }
                verifyEmptyEditText()
            }
        })
        viewBinding?.textInputPhone?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                verifyEmptyEditText()
            }
        })
        viewBinding?.textInputEmail?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                verifyEmptyEditText()
            }
        })
        viewBinding?.textInputDriverDoc?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                verifyEmptyEditText()
            }
        })
    }

    private fun verifyEmptyEditText() {
        val buttonEnable = !(viewBinding?.textInputFio?.text.isNullOrEmpty() ||
                viewBinding?.productCategoryDropdown?.text.isNullOrEmpty() ||
                viewBinding?.textInputPhone?.text.isNullOrEmpty() ||
                viewBinding?.textInputEmail?.text.isNullOrEmpty() ||
                (viewBinding?.textfieldActivityProfile?.editText?.text.toString()  == "Грузоперевозчик" &&
                        viewBinding?.textInputDriverDoc?.text.isNullOrEmpty()))

        viewBinding?.btnSendSms?.isEnabled = buttonEnable
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    companion object {
        fun newInstance() = RegistrationFragment()
    }
}