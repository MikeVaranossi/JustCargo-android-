package com.uzlov.valitova.justcargo.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentRegistration2Binding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class Registration2Fragment : Fragment() {

    private var viewBinding: FragmentRegistration2Binding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentRegistration2Binding.inflate(inflater, container, false)
        return viewBinding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addTextChangedListener()
        /*binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
    }

    private fun addTextChangedListener(){
        viewBinding?.editTextPin?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 4){
                    val manager = activity?.supportFragmentManager
                    manager?.apply {
                        beginTransaction()
                            .replace(R.id.container, Registration3Fragment.newInstance())
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    companion object {
        fun newInstance() = Registration2Fragment()
    }
}