package com.uzlov.valitova.justcargo.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.databinding.ActivitySplashBinding
import com.uzlov.valitova.justcargo.viemodels.UsersViewModel
import com.uzlov.valitova.justcargo.viemodels.ViewModelFactory
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySplashBinding
    @Inject
    lateinit var factoryViewModel: ViewModelFactory
    private lateinit var model: UsersViewModel
    @Inject
    lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val phoneNumber = authService.checkUserIsAuth()
        if (phoneNumber != null){
            model = factoryViewModel.create(UsersViewModel::class.java)
            model.getUser(phoneNumber)?.observe(this, { user ->
                if (user != null) {
                    authService.setUserLogin(user)
                    startActivity(Intent(this, HostActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(this, RegistrationActivity::class.java))
                    finish()
                }
            })
        }else{
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }

    }
}