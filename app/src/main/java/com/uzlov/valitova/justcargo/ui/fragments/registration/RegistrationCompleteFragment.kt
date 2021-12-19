package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.databinding.FragmentRegistrationCompleteBinding
import com.uzlov.valitova.justcargo.ui.activity.HostActivity
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
        activity?.finish()
    }
}