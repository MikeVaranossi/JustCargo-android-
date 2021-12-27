package com.uzlov.valitova.justcargo.ui.fragments.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.data.net.User
import com.uzlov.valitova.justcargo.databinding.FragmentDetailsProfileCarrierBinding
import com.uzlov.valitova.justcargo.databinding.FragmentProfileSenderLayoutBinding
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.ui.fragments.registration.RegistrationSmsFragment
import ru.tinkoff.decoro.slots.PredefinedSlots

import ru.tinkoff.decoro.MaskImpl

import ru.tinkoff.decoro.Mask

private var phoneNumber : String? = null

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

        initListeners()
        arguments?.let{
            if (it.getParcelable<User>(DetailsProfileCarrierFragment.NEW_USER) != null){
                it.getParcelable<User>(DetailsProfileCarrierFragment.NEW_USER)?.let { currentUser ->
                    with(viewBinding) {
                        phoneNumber = currentUser.phone

                        tvProfileFullName.text = currentUser.name
                        tvCompletedCount.text = "Выполнено перевозок: 34"

                        val mask: Mask = MaskImpl(PredefinedSlots.RUS_PHONE_NUMBER, true)
                        mask.insertFront(currentUser.phone)
                        TVPhone.text = mask.toString()

                        TVDriverDoc.text = "7719629088"   //т.к в обьекте user нет поля для документов пока так
                    }
                }
            }
        }
    }

    private fun initListeners() {
        with(viewBinding) {
            fabCall.setOnClickListener {
                phoneNumber?.let {
                    continueCall()
                }
            }
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.Carrier)
        }
    }

    // начало звонка
    private fun continueCall() {
        phoneNumber?.let {
            if (it.trim().isEmpty()) {
                Toast.makeText(requireContext(),
                    getString(R.string.incorrect_phone_number),
                    Toast.LENGTH_SHORT).show()
                return
            }
        }

        if (checkCallToPhonePermissions()) {
            // Разрешение уже есть, звоним
            callToUser()
        } else {
            requirePermissionsCallToPhone()
        }
    }

    // звонок пользователю
    private fun callToUser() {
        phoneNumber?.let {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$it"))
            startActivity(intent)
        }
    }

    // проверка разрешений
    private fun checkCallToPhonePermissions(): Boolean {
        return if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requirePermissionsCallToPhone()
            false
        } else {
            // Разрешение уже есть, звоним
            true
        }
    }

    private fun requirePermissionsCallToPhone() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED -> {
                callToUser()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) -> {

            }
        }
    }

}

