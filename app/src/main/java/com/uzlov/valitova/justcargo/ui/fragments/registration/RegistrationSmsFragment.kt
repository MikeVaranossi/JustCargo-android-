package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.data.net.User
import com.uzlov.valitova.justcargo.databinding.FragmentRegistrationSmsBinding
import com.uzlov.valitova.justcargo.repo.net.IUserRepository
import com.uzlov.valitova.justcargo.ui.activity.HostActivity
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import com.uzlov.valitova.justcargo.viemodels.UsersViewModel
import com.uzlov.valitova.justcargo.viemodels.ViewModelFactory
import javax.inject.Inject
import android.os.CountDownTimer
import android.widget.Toast


class RegistrationSmsFragment : BaseFragment<FragmentRegistrationSmsBinding>(
    FragmentRegistrationSmsBinding::inflate) {

    private var cTimer: CountDownTimer? = null
    @Inject
    lateinit var factoryViewModel: ViewModelFactory
    private lateinit var model: UsersViewModel
    private val TAG: String = javaClass.simpleName
    @Inject
    lateinit var authService: AuthService
    @Inject
    lateinit var userRepository: IUserRepository

    private val stateListener = object : AuthService.IStateAuth {
        override fun registered(user: User) {
            //регистрация успешна, записываем пользователя в базу и открываем следующий экран
            userRepository.putUser(user)
            val manager = requireActivity().supportFragmentManager
            manager.apply {
                beginTransaction()
                    .replace(R.id.container, RegistrationCompleteFragment())
                    .commit()
            }
        }

        override fun login(user: User) {
            // можем редиректить на hostactivity
            startActivity(Intent(requireContext(), HostActivity::class.java))
            activity?.finish()
            Log.e(TAG, "success login: $user")
        }

        override fun logout(msg: String) {
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            // показать ошибку, или откинуть пользователя назад
            Log.e(TAG, "success logout: ")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireContext().appComponent.inject(this)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = ""
            it.setDisplayHomeAsUpEnabled(true)
        }
        viewBinding.btnRepeatPin.setOnClickListener {
            startPhoneNumberVerification()
            viewBinding.btnRepeatPin.isEnabled = false
        }
        authService.setStateAuthListener(stateListener)
        authService.setActivity(requireActivity())
        addTextChangedListener()
        startPhoneNumberVerification()
    }

    private fun addTextChangedListener() {
        viewBinding.editTextCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 6) {
                    val code = viewBinding.editTextCode.text.toString()
                    authService.checkCredential(code)
                }
            }
        })
    }

    private fun startPhoneNumberVerification() {
        arguments?.let {
            // передан телефон, значит ВХОД
            if (it.getString(PHONE_NUMBER) != null){
                it.getString(PHONE_NUMBER)?.let {phoneNumber->
                    model = factoryViewModel.create(UsersViewModel::class.java)
                    model.getUser(phoneNumber)?.observe(this, { user ->
                        //пользователь есть значит можно запрашивать код подтверждения
                        if (user != null) {
                            authService.startAuth(user)
                            startTimer()
                        }
                    })
                }
                return
            }
            // передан User, значит РЕГИСТРАЦИЯ
            if (it.getParcelable<User>(NEW_USER) != null){
                it.getParcelable<User>(NEW_USER)?.let { user->
                    authService.startRegistering(user)
                }
                return
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        authService.removeStateAuthListener()
        authService.removeActivity()
        cTimer?.cancel()
    }

    private fun startTimer() {
        cTimer = object : CountDownTimer(62000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished/1000)%60
                val minutes = ((millisUntilFinished-seconds)/1000)/60

                viewBinding.textviewTime.text = String.format("%02d:%02d", minutes, seconds)
            }
            override fun onFinish() {
                viewBinding.btnRepeatPin.isEnabled = true
            }
        }
        (cTimer as CountDownTimer).start()
    }

    companion object {
        private const val PHONE_NUMBER = "phone_number"
        private const val NEW_USER = "user"

        fun newInstance(phoneNumber: String) = RegistrationSmsFragment().apply {
            arguments = Bundle().apply {
                putString(PHONE_NUMBER, phoneNumber)
            }
        }

        fun newInstance(user: User?) = RegistrationSmsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(NEW_USER, user)
            }
        }
    }
}