package com.uzlov.valitova.justcargo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentProfileLayoutBinding

class ProfileFragment : BaseFragment<FragmentProfileLayoutBinding>(
FragmentProfileLayoutBinding::inflate) {

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    .replace(R.id.fragment_container, MyDeliveriesFragment.newInstance())
                    .addToBackStack(null).commit()
            }

            (requireActivity() as AppCompatActivity).supportActionBar?.let {
                it.title = getString(R.string.my_profile)
                it.setDisplayHomeAsUpEnabled(false)
            }

        }
    }
}