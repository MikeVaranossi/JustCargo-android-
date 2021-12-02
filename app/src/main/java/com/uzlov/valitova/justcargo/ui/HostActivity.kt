package com.uzlov.valitova.justcargo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.ui.fragments.ProfileFragment

class HostActivity : AppCompatActivity() {

    private var bottomNavigation: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_acivity_content)


        findViewById<MaterialToolbar>(R.id.rootToolbar).let {
            setSupportActionBar(it)
            it.setNavigationOnClickListener { supportFragmentManager.popBackStack() }
            it.setNavigationIconTint(resources.getColor(R.color.white_color))

        }

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation?.itemIconTintList = null
        bottomNavigation?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.main_action-> {
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
                    setFragment(ProfileFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}