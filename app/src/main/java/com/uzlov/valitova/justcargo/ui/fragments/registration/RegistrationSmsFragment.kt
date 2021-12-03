package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentRegistrationSmsBinding

class RegistrationSmsFragment : Fragment() {

    private var _viewBinding: FragmentRegistrationSmsBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _viewBinding = FragmentRegistrationSmsBinding.inflate(inflater, container, false)
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = ""
            it.setDisplayHomeAsUpEnabled(true)
        }

        addTextChangedListener()
    }

    private fun addTextChangedListener(){
        viewBinding.editTextPin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 4){
                    val manager = requireActivity().supportFragmentManager
                    manager.apply {
                        beginTransaction()
                            .replace(R.id.container, RegistrationCompleteFragment())
                            .commit()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}