package com.uzlov.valitova.justcargo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.data.net.User
import com.uzlov.valitova.justcargo.ui.fragments.FavoritesRequestsFragment
import com.uzlov.valitova.justcargo.ui.fragments.home.HomeCarrierFragment
import com.uzlov.valitova.justcargo.ui.fragments.home.HomeSenderFragment
import com.uzlov.valitova.justcargo.ui.fragments.profile.MyDeliveriesFragment
import com.uzlov.valitova.justcargo.ui.fragments.profile.ProfileCarrierFragment
import com.uzlov.valitova.justcargo.ui.fragments.profile.ProfileSenderFragment
import com.uzlov.valitova.justcargo.ui.fragments.search.FindCargoFragment
import javax.inject.Inject


class HostActivity : AppCompatActivity() {

    private var bottomNavigation: BottomNavigationView? = null

    @Inject
    lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_acivity_content)

        applicationContext.appComponent.inject(this)

        findViewById<MaterialToolbar>(R.id.rootToolbar).let {
            setSupportActionBar(it)
            it.setNavigationOnClickListener { supportFragmentManager.popBackStack() }
            it.setNavigationIconTint(resources.getColor(R.color.white_color))
        }

        if (authService.currentUser()?.userType?.id == 1L) {
            setFragment(HomeSenderFragment.newInstance())
        } else {
            setFragment(HomeCarrierFragment.newInstance())

        }

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation?.itemIconTintList = null

        bottomNavigation?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.main_action-> {
                    if (authService.currentUser()?.userType?.id == 1L) {
                        setFragment(HomeSenderFragment.newInstance())
                        return@setOnItemSelectedListener true
                    } else {
                        setFragment(HomeCarrierFragment.newInstance())
                        return@setOnItemSelectedListener true
                    }
                }
                R.id.search_action-> {
                    setFragment(FindCargoFragment.newInstance())
                    true
                }
                R.id.delivery_action-> {
                    setFragment(MyDeliveriesFragment.newInstance())
                    true
                }
                R.id.favorite_action-> {
                    setFragment(FavoritesRequestsFragment.newInstance())
                    true
                }
                R.id.profile_action-> {
                    if (authService.currentUser()?.userType?.id == 1L) {
                        setFragment(ProfileSenderFragment.newInstance())
                        return@setOnItemSelectedListener true
                    } else {
                        setFragment(ProfileCarrierFragment.newInstance())
                        return@setOnItemSelectedListener true
                    }
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