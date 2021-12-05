package com.uzlov.valitova.justcargo.ui.fragments.registration

import android.content.ContentValues.TAG
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
import com.google.firebase.ktx.Firebase
import com.uzlov.valitova.justcargo.R
import com.uzlov.valitova.justcargo.databinding.FragmentRegistrationSmsBinding
import java.util.concurrent.TimeUnit

class RegistrationSmsFragment : Fragment() {

    private var _viewBinding: FragmentRegistrationSmsBinding? = null
    private val viewBinding get() = _viewBinding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _viewBinding = FragmentRegistrationSmsBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = ""
            it.setDisplayHomeAsUpEnabled(true)
        }

        addTextChangedListener()
        callbacksFirebase()
        startPhoneNumberVerification()
    }

    private fun addTextChangedListener(){
        viewBinding.editTextCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 6){
                    val code = viewBinding.editTextCode.text.toString()
                    val credential = storedVerificationId?.let { PhoneAuthProvider.getCredential(it, code) }
                    if (credential != null) {
                        signInWithPhoneAuthCredential(credential)
                    }
                }
            }
        })
    }

    private fun startPhoneNumberVerification() {
        var phoneNumber = ""
        arguments?.let {
            phoneNumber = it.getString(PHONE_NUMBER).toString()
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun callbacksFirebase(){
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // Этот обратный вызов будет вызываться в двух ситуациях:
                // 1 - Мгновенная проверка. В некоторых случаях номер телефона может быть мгновенно
                // проверено без необходимости отправлять или вводить проверочный код.
                // 2 - Автозагрузка. На некоторых устройствах сервисы Google Play могут автоматически
                // обнаруживаем входящее проверочное SMS и выполняем проверку без
                // действие пользователя.
                Log.d(TAG, "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // Этот обратный вызов вызывается при выполнении недопустимого запроса на проверку,
                // например, если формат номера телефона недействителен.
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Неверный запрос
                } else if (e is FirebaseTooManyRequestsException) {
                    //Превышена квота SMS для проекта
                }
                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // Проверочный код по SMS был отправлен на указанный номер телефона,
                // теперь нужно попросить пользователя ввести код, а затем создать учетные данные
                // путем объединения кода с идентификатором подтверждения.
                Log.d(TAG, "onCodeSent:$verificationId")

                // Сохраните идентификатор подтверждения и токен повторной отправки, чтобы мы могли использовать их позже
                storedVerificationId = verificationId
                resendToken = token
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = task.result?.user
                    //проверка кода удачная
                    val manager = requireActivity().supportFragmentManager
                    manager.apply {
                        beginTransaction()
                            .replace(R.id.container, RegistrationCompleteFragment())
                            .commit()
                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Log.w(TAG, "FirebaseAuthInvalidCredentialsException", task.exception)
                        Toast.makeText(
                            requireContext(),
                            "Что-то пошло не так. Попробуйте позже.",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }


    companion object {
        private const val PHONE_NUMBER = "phone_number"

        fun newInstance(phoneNumber: String) = RegistrationSmsFragment().apply {
            arguments = Bundle().apply {
                putString(PHONE_NUMBER, phoneNumber)
            }
        }
    }
}