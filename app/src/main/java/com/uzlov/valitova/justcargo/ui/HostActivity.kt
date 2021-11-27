package com.uzlov.valitova.justcargo.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.App
import com.uzlov.valitova.justcargo.app.lastToPower
import com.uzlov.valitova.justcargo.di.modules.LocalModule
import com.uzlov.valitova.justcargo.ui.activity.RegistrationActivity
import okhttp3.OkHttpClient
import javax.inject.Inject


class HostActivity : AppCompatActivity() {

    private var bottomNavigation: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_acivity_content)



        val tv = findViewById<TextView>(R.id.textView)
        val tv2 = findViewById<TextView>(R.id.textView2)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation?.itemIconTintList = null
        bottomNavigation?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.main_action-> {

                    val intent = Intent(this, RegistrationActivity::class.java)
// To pass any data to next activity
// start your next activity
                    startActivity(intent)
                    true
                }
                R.id.search_action-> {
                    true
                }
                R.id.delivery_action-> {
                    true
                }
                R.id.favorite_action-> {
                    true
                }
                R.id.profile_action-> {
                    true
                }
                else -> false
            }
        }


    }
}