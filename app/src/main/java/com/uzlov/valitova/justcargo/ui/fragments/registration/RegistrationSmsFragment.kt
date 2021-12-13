package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.app.appComponent
import com.uzlov.valitova.justcargo.auth.AuthService
import com.uzlov.valitova.justcargo.data.net.User
import com.uzlov.valitova.justcargo.databinding.FragmentRegistrationSmsBinding
import com.uzlov.valitova.justcargo.repo.net.IUserRepository
import com.uzlov.valitova.justcargo.ui.fragments.BaseFragment
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RegistrationSmsFragment : BaseFragment<FragmentRegistrationSmsBinding>(
    FragmentRegistrationSmsBinding::inflate) {

//    private lateinit var auth: FirebaseAuth
//    private val mDatabase: DatabaseReference? = null
//    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
//    private var storedVerificationId: String? = ""
//    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private val TAG: String = javaClass.simpleName
    private var user: User? = null
    private var phone: String? = null

    @Inject
    lateinit var authService: AuthService

    @Inject
    lateinit var userRepository: IUserRepository

    private val stateListener = object : AuthService.IStateAuth {
        override fun registered(user: User) {
            userRepository.putUser(user)
        }

        override fun login(user: User) {
            // можем редиректить на hostactivity
            Log.e(TAG, "success login: $user")

        }

        override fun success() {
            Log.e(TAG, "success AUTH: ")
        }


        override fun logout() {
            // показать ошибку, или откинуть пользователя назад
            Log.e(TAG, "success logout: ")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireContext().appComponent.inject(this)

//        auth = Firebase.auth
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = ""
            it.setDisplayHomeAsUpEnabled(true)
        }

        authService.setStateAuthListener(stateListener)

        addTextChangedListener()
//        callbacksFirebase()
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
                    authService.startAuth(phoneNumber)
                }

                return
            }
            // передан телефон, значит РЕГИСТРАЦИЯ
            if (it.getParcelable<User>(NEW_USER) != null){
                it.getParcelable<User>(NEW_USER)?.let { user->
                    authService.startRegistering(user)
                }
                return
            }
        }

//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNumber)       // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(requireActivity())                 // Activity (for callback binding)
//            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
    }


//    val listener {
//    login
//    logout
//}

//    private fun callbacksFirebase(){
//        // startAuth(listener)
//        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                // Этот обратный вызов будет вызываться в двух ситуациях:
//                // 1 - Мгновенная проверка. В некоторых случаях номер телефона может быть мгновенно
//                // проверено без необходимости отправлять или вводить проверочный код.
//                // 2 - Автозагрузка. На некоторых устройствах сервисы Google Play могут автоматически
//                // обнаруживаем входящее проверочное SMS и выполняем проверку без
//                // действие пользователя.
//                Log.d(TAG, "onVerificationCompleted:$credential")
//                signInWithPhoneAuthCredential(credential)
//            }
//
//            override fun onVerificationFailed(e: FirebaseException) {
//                // Этот обратный вызов вызывается при выполнении недопустимого запроса на проверку,
//                // например, если формат номера телефона недействителен.
//                Log.w(TAG, "onVerificationFailed", e)
//
//                if (e is FirebaseAuthInvalidCredentialsException) {
//                    // Неверный запрос
//                } else if (e is FirebaseTooManyRequestsException) {
//                    //Превышена квота SMS для проекта
//                }
//                // Show a message and update the UI
//            }
//
//            override fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {
//                // Проверочный код по SMS был отправлен на указанный номер телефона,
//                // теперь нужно попросить пользователя ввести код, а затем создать учетные данные
//                // путем объединения кода с идентификатором подтверждения.
//                Log.d(TAG, "onCodeSent:$verificationId")
//
//                // Сохраните идентификатор подтверждения и токен повторной отправки, чтобы мы могли использовать их позже
//                storedVerificationId = verificationId
//                resendToken = token
//            }
//        }
//    }

//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    Log.d(TAG, "signInWithCredential:success")
//                    val userPhone = task.result?.user?.phoneNumber
//
//                    val database = FirebaseDatabase.getInstance()
//                    val usersRef = database.getReference("users")
//
//                    var user = arguments?.getParcelable<User>(NEW_USER) as User?
//
//                    if (user != null)
//                        usersRef.child(userPhone.toString()).setValue(user)
//
//                    //проверка кода удачная
//                    val manager = requireActivity().supportFragmentManager
//                    manager.apply {
//                        beginTransaction()
//                            .replace(R.id.container, RegistrationCompleteFragment())
//                            .commit()
//                    }
//                } else {
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        Log.w(TAG, "FirebaseAuthInvalidCredentialsException", task.exception)
//                        Toast.makeText(
//                            requireContext(),
//                            "Что-то пошло не так. Попробуйте позже.",
//                            Toast.LENGTH_LONG
//                        ).show()
//
//                    }
//                }
//            }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        authService.removeStateAuthListener()
        authService.removeActivity()
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