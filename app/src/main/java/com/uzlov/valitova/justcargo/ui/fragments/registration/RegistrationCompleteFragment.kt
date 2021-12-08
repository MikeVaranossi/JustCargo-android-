package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.uzlov.valitova.justcargo.databinding.FragmentRegistrationCompleteBinding
import com.uzlov.valitova.justcargo.ui.HostActivity

class RegistrationCompleteFragment : Fragment() {

    private var _viewBinding: FragmentRegistrationCompleteBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _viewBinding = FragmentRegistrationCompleteBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = ""
            it.setDisplayHomeAsUpEnabled(false)
        }

        viewBinding.btnNext.setOnClickListener {
            btnNextClicked()
        }
    }

    private fun btnNextClicked(){
        if (viewBinding.layoutComplete.isVisible){
            viewBinding.layoutComplete.visibility = View.INVISIBLE
            viewBinding.layoutGift.visibility = View.VISIBLE
        }

        startActivity(Intent(requireContext(), HostActivity::class.java))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}