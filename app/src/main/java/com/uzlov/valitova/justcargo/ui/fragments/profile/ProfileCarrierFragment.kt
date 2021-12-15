package com.uzlov.valitova.justcargo.ui.fragments.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentProfileCarrierLayoutBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment

class ProfileCarrierFragment : BaseFragment<FragmentProfileCarrierLayoutBinding>(
    FragmentProfileCarrierLayoutBinding::inflate) {

    companion object {
        fun newInstance(): ProfileCarrierFragment {
            return ProfileCarrierFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            tvSelfData.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, PersonalDataFragment.newInstance())
                    .addToBackStack(null).commit()
            }

            tvMyDeliveries.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MyDeliveriesFragment.newInstance(false))
                    .addToBackStack(null).commit()
            }

            (requireActivity() as AppCompatActivity).supportActionBar?.let {
                it.title = getString(R.string.my_profile)
                it.setDisplayHomeAsUpEnabled(false)
            }
        }
    }
}