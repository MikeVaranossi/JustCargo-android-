
package com.uzlov.valitova.justcargo.ui.fragments.registration

import com.google.firebase.auth.FirebaseAuth

class AuthService() {

    fun getUser (){
        var userUid = FirebaseAuth.getInstance().currentUser?.uid

    }
}
    /*
  fun login(credentials: LoginCredentials): User = credentials.let { (email, password) ->
        val user = db.findUser(email) ?: throw UserNotFound
        BcryptHasher.checkPassword(password, user)
        val token = JwtConfig.makeToken(user)
        return user.copy(token = token)
    }

    fun register(details: RegistrationDetails): User = details.let { (username, email, password) ->
        val hashed = BcryptHasher.hashPassword(password)
        val id = db.insertUser(details.copy(password = hashed))
        return User(id, email, "", "", username, "", null).run {
            copy(token = JwtConfig.makeToken(this))
        }
    }

    fun updateUser(new: User, current: User): User {
        val final = when {
            new.password != null -> new.copy(password = BcryptHasher.hashPassword(new.password))
            else -> new
        }
        return db.updateUser(final, current) ?: throw UserNotFound
    }*//*



    fun register(){
        Firebase.auth.createUserWithEmailAndPassword()
        FIRAuth.auth()?.createUser(withEmail: emailField.text!, password: passwordField.text!, completion: { (user: FIRUser?, error) in
            if error != nil {
                print(error)
                return

            } else {
                print("User Created...") }

            guard let uid = user?.uid else { return }

            // You can set these values to whatever you want, plus add more!
            let values = ["email": emailField.text!, "username": usernameField.text!] as [String : Any]

            // Then pass your values into another function called 'RegisterUserIntoDatabase'
            // which creates the user in your Firebase Database...

            self.registerUserIntoDatabase(uid, values: values as [String : AnyObject])
        })
    }




}*/
