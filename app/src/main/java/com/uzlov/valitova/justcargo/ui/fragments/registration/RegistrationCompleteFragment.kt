package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.uzlov.valitova.justcargo.databinding.FragmentRegistrationCompleteBinding

class RegistrationCompleteFragment : Fragment() {

    private var viewBinding: FragmentRegistrationCompleteBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = FragmentRegistrationCompleteBinding.inflate(inflater, container, false)
        return viewBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.btnNext?.setOnClickListener {
            btnNextClicked()
        }
    }

    private fun btnNextClicked(){
        if (viewBinding?.layoutComplete?.isVisible == true){
            viewBinding?.layoutComplete?.visibility = View.INVISIBLE
            viewBinding?.layoutGift?.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    companion object {
        fun newInstance() = RegistrationCompleteFragment()
    }
}