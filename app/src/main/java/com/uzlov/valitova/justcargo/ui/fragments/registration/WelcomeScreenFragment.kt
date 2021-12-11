package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.databinding.FragmentWelcomeScreenBinding
import com.uzlov.valitova.justcargo.repo.usecases.UsersUseCase
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.viemodels.UsersViewModel
import com.uzlov.valitova.justcargo.viemodels.ViewModelFactory
import javax.inject.Inject


class WelcomeScreenFragment : BaseFragment<FragmentWelcomeScreenBinding>(
    FragmentWelcomeScreenBinding::inflate){

    @Inject
    lateinit var factoryViewModel: ViewModelFactory
    private lateinit var model: UsersViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        model = factoryViewModel.create(UsersViewModel::class.java)

        val userPhone = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: ""
        Log.e(javaClass.simpleName, "onViewCreated userPhone: $userPhone")
        model.getUser(userPhone)?.observe(this, {
            Log.e(javaClass.simpleName, "onViewCreated: $it")
        })


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