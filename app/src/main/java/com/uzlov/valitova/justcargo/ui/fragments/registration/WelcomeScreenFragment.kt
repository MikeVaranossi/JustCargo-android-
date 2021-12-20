package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentWelcomeScreenBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment


class WelcomeScreenFragment : BaseFragment<FragmentWelcomeScreenBinding>(
    FragmentWelcomeScreenBinding::inflate){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        val manager = requireActivity().supportFragmentManager
        viewBinding.btnLogin.setOnClickListener {
            manager.apply {
                beginTransaction()
                    .replace(R.id.container, LoginFragment())
                    .commit()
            }
        }
        viewBinding.btnRegistration.setOnClickListener {
            manager.apply {
                beginTransaction()
                    .replace(R.id.container, RegistrationFragment())
                    .commit()
            }
        }

        viewBinding.let {
            Glide.with(this).load(R.drawable.image_start).into(
                it.imageview)
        }
    }
}