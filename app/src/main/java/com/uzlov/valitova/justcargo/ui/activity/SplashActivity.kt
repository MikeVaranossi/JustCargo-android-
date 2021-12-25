package com.uzlov.valitova.justcargo.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.uzlov.valitova.justcargo.app.USER_SHARED_PREFERENCES
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.data.net.User
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
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val phoneNumber = authService.checkUserIsAuth()
        val userPref = getUserInSharedPreferences()

        if (userPref != null && phoneNumber != null){
            authService.setUserLogin(userPref)
            startActivity(Intent(this, HostActivity::class.java))
            finish()
        }else{
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }
    }

    private fun getUserInSharedPreferences(): User? {
        val userJson = sharedPreferences.getString(USER_SHARED_PREFERENCES, "")
        if (userJson != "")
            return Gson().fromJson(userJson, User::class.java)
        return null
    }
}