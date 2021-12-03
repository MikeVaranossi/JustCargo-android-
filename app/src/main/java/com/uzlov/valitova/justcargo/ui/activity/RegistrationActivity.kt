package com.uzlov.valitova.justcargo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
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

        //setSupportActionBar(viewBinding.toolbar)

        viewBinding.toolbar.let {
            setSupportActionBar(it)
            it.setNavigationOnClickListener { supportFragmentManager.popBackStack() }
            it.setNavigationIconTint(resources.getColor(R.color.white_color))
        }

        /*findViewById<MaterialToolbar>(R.id.rootToolbar).let {
            setSupportActionBar(it)
            it.setNavigationOnClickListener { supportFragmentManager.popBackStack() }
            it.setNavigationIconTint(resources.getColor(R.color.white_color))

        }*/

        supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.container, WelcomeScreenFragment())
                .commit()
        }
    }
}