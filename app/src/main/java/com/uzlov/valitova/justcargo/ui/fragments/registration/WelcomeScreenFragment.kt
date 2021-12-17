package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.databinding.FragmentWelcomeScreenBinding
import com.uzlov.valitova.justcargo.ui.activity.HostActivity
import com.uzlov.valitova.justcargo.ui.activity.RegistrationActivity
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.viemodels.UsersViewModel
import com.uzlov.valitova.justcargo.viemodels.ViewModelFactory
import javax.inject.Inject


class WelcomeScreenFragment : BaseFragment<FragmentWelcomeScreenBinding>(
    FragmentWelcomeScreenBinding::inflate){

    @Inject
    lateinit var factoryViewModel: ViewModelFactory
    private lateinit var model: UsersViewModel

    @Inject
    lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        val phoneNumber = authService.checkUserIsAuth()
        if (phoneNumber != null){
            viewBinding.progressBar.visibility = View.VISIBLE
            model = factoryViewModel.create(UsersViewModel::class.java)
            model.getUser(phoneNumber)?.observe(this, { user ->
                if (user != null) {
                    authService.setUserLogin(user)
                    startActivity(Intent(requireContext(), HostActivity::class.java))
                    activity?.finish()
                }
            })
        }else{
            showButtons()
        }

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

    private fun showButtons() {
        viewBinding.btnRegistration.visibility = View.VISIBLE
        viewBinding.btnLogin.visibility = View.VISIBLE
    }
}