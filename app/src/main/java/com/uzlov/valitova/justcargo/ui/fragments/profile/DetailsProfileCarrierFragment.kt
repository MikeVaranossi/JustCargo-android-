package com.uzlov.valitova.justcargo.ui.fragments.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.data.net.User
import com.uzlov.valitova.justcargo.databinding.FragmentDetailsProfileCarrierBinding
import com.uzlov.valitova.justcargo.databinding.FragmentProfileSenderLayoutBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.ui.fragments.registration.RegistrationSmsFragment


class DetailsProfileCarrierFragment : BaseFragment<FragmentDetailsProfileCarrierBinding>
    (FragmentDetailsProfileCarrierBinding::inflate){

    companion object {
        private const val NEW_USER = "user"

        fun newInstance(user: User?) = DetailsProfileCarrierFragment().apply {
            arguments = Bundle().apply {
                putParcelable(NEW_USER, user)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            if (it.getParcelable<User>(DetailsProfileCarrierFragment.NEW_USER) != null){
                it.getParcelable<User>(DetailsProfileCarrierFragment.NEW_USER)?.let { user->
                    with(viewBinding) {
                        tvProfileFullName.text = user.name
                        tvCompletedCount.text = "Выполнено перевозок: 34"
                        etPhoneProfile.setText( user.phone)
                        etDriverDocProfile.setText( user.phone)
                    }
                }
            }
        }

    }

}

