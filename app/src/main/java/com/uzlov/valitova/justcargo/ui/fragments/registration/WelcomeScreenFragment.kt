package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentWelcomeScreenBinding


class WelcomeScreenFragment : Fragment() {
    private var _viewBinding: FragmentWelcomeScreenBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _viewBinding = FragmentWelcomeScreenBinding.inflate(inflater, container, false)
        return viewBinding.root

    }

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



    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

}