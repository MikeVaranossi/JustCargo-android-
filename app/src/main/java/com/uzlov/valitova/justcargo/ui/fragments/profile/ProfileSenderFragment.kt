package com.uzlov.valitova.justcargo.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.databinding.FragmentProfileSenderLayoutBinding
import com.uzlov.valitova.justcargo.ui.activity.RegistrationActivity
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import javax.inject.Inject

class ProfileSenderFragment : BaseFragment<FragmentProfileSenderLayoutBinding>(
    FragmentProfileSenderLayoutBinding::inflate) {

    @Inject
    lateinit var authService: AuthService

    companion object {
        fun newInstance(): ProfileSenderFragment {
            return ProfileSenderFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
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
                    .replace(R.id.fragment_container, MyRequestsFragment.newInstance())
                    .addToBackStack(null).commit()
            }

            tvExitAccount.setOnClickListener {
                authService.signOut()
                startActivity(Intent(requireContext(), RegistrationActivity::class.java))
                activity?.finish()
            }

            (requireActivity() as AppCompatActivity).supportActionBar?.let {
                it.title = getString(R.string.my_profile)
                it.setDisplayHomeAsUpEnabled(false)
            }
        }
    }
}