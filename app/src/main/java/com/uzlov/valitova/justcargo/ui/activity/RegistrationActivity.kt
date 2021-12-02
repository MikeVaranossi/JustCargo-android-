package com.uzlov.valitova.justcargo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.ActivityRegistrationBinding
import com.uzlov.valitova.justcargo.ui.fragments.registration.WelcomeScreenFragment

class RegistrationActivity : AppCompatActivity() {

    private var _viewBinding: ActivityRegistrationBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewBinding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setSupportActionBar(viewBinding.toolbar)

        supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.container, WelcomeScreenFragment())
                .commit()
        }
    }
}