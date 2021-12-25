package com.uzlov.valitova.justcargo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.Constant
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.ui.fragments.FavoritesRequestsFragment
import com.uzlov.valitova.justcargo.ui.fragments.SearchFragment
import com.uzlov.valitova.justcargo.ui.fragments.home.HomeSenderFragment
import com.uzlov.valitova.justcargo.ui.fragments.home.MapDeliveriesFragment
import com.uzlov.valitova.justcargo.ui.fragments.profile.MyDeliveriesFragment
import com.uzlov.valitova.justcargo.ui.fragments.profile.MyRequestsFragment
import com.uzlov.valitova.justcargo.ui.fragments.profile.ProfileCarrierFragment
import com.uzlov.valitova.justcargo.ui.fragments.profile.ProfileSenderFragment
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
            it.setNavigationIconTint(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.white_color
                )
            )
        }
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation?.itemIconTintList = null

        if (authService.currentUser()?.userType?.id == 1L) {
            setFragment(HomeSenderFragment.newInstance())
            bottomNavigation?.inflateMenu(R.menu.main_menu_sender)
        } else {
            setFragment(MapDeliveriesFragment.newInstance(null))
            bottomNavigation?.inflateMenu(R.menu.main_carrier_menu)

        }

        bottomNavigation?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.main_action -> {
                    if (authService.currentUser()?.userType?.id == Constant.SENDER) {
                        setFragment(HomeSenderFragment.newInstance())
                        return@setOnItemSelectedListener true
                    } else if (authService.currentUser()?.userType?.id == Constant.CARRIER) {
                        setFragment(MapDeliveriesFragment.newInstance(null))
                        return@setOnItemSelectedListener true
                    }
                    return@setOnItemSelectedListener false
                }
                R.id.search_action -> {
                    setFragment(SearchFragment.newInstance())
                    true
                }
                R.id.delivery_action -> {
                    if (authService.currentUser()?.userType?.id == Constant.SENDER) {
                        setFragment(MyRequestsFragment.newInstance(true))
                        return@setOnItemSelectedListener true
                    } else if (authService.currentUser()?.userType?.id == Constant.CARRIER) {
                        setFragment(MyDeliveriesFragment.newInstance(true))
                        return@setOnItemSelectedListener true
                    }

                    false
                }
                R.id.favorite_action -> {
                    setFragment(FavoritesRequestsFragment.newInstance())
                    true
                }
                R.id.profile_action -> {
                    if (authService.currentUser()?.userType?.id == Constant.SENDER) {
                        setFragment(ProfileSenderFragment.newInstance())
                        return@setOnItemSelectedListener true
                    } else if (authService.currentUser()?.userType?.id == Constant.CARRIER) {
                        setFragment(ProfileCarrierFragment.newInstance())
                        return@setOnItemSelectedListener true
                    }
                    return@setOnItemSelectedListener false
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