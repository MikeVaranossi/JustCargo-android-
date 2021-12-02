package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentWelcomeScreenBinding


class WelcomeScreenFragment : Fragment() {
    private var viewBinding: FragmentWelcomeScreenBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = FragmentWelcomeScreenBinding.inflate(inflater, container, false)
        return viewBinding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = requireActivity().supportFragmentManager
        viewBinding?.btnLogin?.setOnClickListener {
            manager.apply {
                beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
        viewBinding?.btnRegistration?.setOnClickListener {
            manager.apply {
                beginTransaction()
                    .replace(R.id.container, RegistrationFragment.newInstance())
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }

        viewBinding?.let {
            Glide.with(this).load(R.drawable.image_start).into(
                it.imageview)
        };
    }



    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    companion object {
        fun newInstance() = WelcomeScreenFragment()
    }

}