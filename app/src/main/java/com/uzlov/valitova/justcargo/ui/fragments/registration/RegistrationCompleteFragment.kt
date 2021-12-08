package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.uzlov.valitova.justcargo.databinding.FragmentLoginBinding
import com.uzlov.valitova.justcargo.databinding.FragmentRegistrationCompleteBinding
import com.uzlov.valitova.justcargo.ui.HostActivity
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment

class RegistrationCompleteFragment : BaseFragment<FragmentRegistrationCompleteBinding>(
    FragmentRegistrationCompleteBinding::inflate)  {

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
        startActivity(Intent(requireContext(), HostActivity::class.java))
    }
}