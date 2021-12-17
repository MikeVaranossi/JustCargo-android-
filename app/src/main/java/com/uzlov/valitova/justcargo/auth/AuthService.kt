package com.uzlov.valitova.justcargo.auth

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.uzlov.valitova.justcargo.data.net.User
import java.util.concurrent.TimeUnit
import com.google.firebase.auth.PhoneAuthProvider


class AuthService () {

    private var auth: FirebaseAuth = Firebase.auth
    private var storedVerificationId: String? = null
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private var activity: Activity? = null

    private var registration: Boolean = false
    private var user: User? = null

    interface IStateAuth {
        fun registered(user: User)
        fun login(user: User)
        fun logout()
    }

    private var listenerAuth: IStateAuth? = null

    fun setActivity(_activity: Activity){
        activity = _activity
    }
    fun removeActivity() {
        activity = null
    }

    fun setStateAuthListener(_listenerAuth: IStateAuth?) {
        listenerAuth = _listenerAuth
    }

    fun removeStateAuthListener() {
        listenerAuth = null
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // Этот обратный вызов будет вызываться в двух ситуациях:
            // 1 - Мгновенная проверка. В некоторых случаях номер телефона может быть мгновенно
            // проверено без необходимости отправлять или вводить проверочный код.
            // 2 - Автозагрузка. На некоторых устройствах сервисы Google Play могут автоматически
            // обнаруживаем входящее проверочное SMS и выполняем проверку без
            // действие пользователя.
            Log.d(ContentValues.TAG, "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // Этот обратный вызов вызывается при выполнении недопустимого запроса на проверку,
            // например, если формат номера телефона недействителен.
            Log.w(ContentValues.TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Неверный запрос
            } else if (e is FirebaseTooManyRequestsException) {
                //Превышена квота SMS для проекта
            }
            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // Проверочный код по SMS был отправлен на указанный номер телефона,
            // теперь нужно попросить пользователя ввести код, а затем создать учетные данные
            // путем объединения кода с идентификатором подтверждения.
            Log.d(ContentValues.TAG, "onCodeSent:$verificationId")

            // Сохраните идентификатор подтверждения и токен повторной отправки, чтобы мы могли использовать их позже
            storedVerificationId = verificationId
            resendToken = token
        }
    }

    fun checkUserIsAuth(): String? {
        return auth.currentUser?.phoneNumber
    }


    fun startAuth(userLogin: User) {
        user = userLogin
        if (user!!.phone != null ){
            // подтверждение номера телефона
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(user!!.phone!!)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activity!!)                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    fun checkCredential(code: String) {
        val credential = storedVerificationId?.let { PhoneAuthProvider.getCredential(it, code) }
        if (credential != null) {
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (registration){
                    listenerAuth?.registered(user!!)
                }else{
                    listenerAuth?.login(user!!)
                }
            }
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }

    fun startRegistering(newUser: User) {
        registration = true
        user = newUser
        newUser.phone?.let {
            user = newUser
            startAuth(user!!)
            listenerAuth?.registered(user!!)
        }
    }

    fun setUserLogin(userLogin: User) {
        user = userLogin
    }

    fun currentUser(): User? {
        return user
    }
}