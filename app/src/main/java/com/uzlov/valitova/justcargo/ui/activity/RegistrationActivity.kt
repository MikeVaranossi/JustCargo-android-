package com.uzlov.valitova.justcargo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.ActivityRegistrationBinding
import com.uzlov.valitova.justcargo.ui.fragments.registration.LoginFragment
import com.uzlov.valitova.justcargo.ui.fragments.registration.RegistrationFragment
import com.uzlov.valitova.justcargo.ui.fragments.registration.WelcomeScreenFragment

class RegistrationActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setSupportActionBar(viewBinding.toolbar)


        supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.container, WelcomeScreenFragment.newInstance())
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }
}