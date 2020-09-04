package com.development.allanproject.views.activity.ui.login

import android.view.View
import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.login.LoginPost
import com.development.allanproject.model.signupModel.PersonalDetailPost
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException

class LoginViewModel( private val repository: UserRepository
) : ViewModel() {
    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null
    var hashMap: HashMap<String, Any> = HashMap<String, Any>()

    fun onLoginButton(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Please enter email id and password")
            return
        }

            authListener?.onStarted()
            Coroutines.main {
                try {

                    val loginPost = LoginPost(email!!, password!!)
                    //  val authResponse = repository.userLogin(firstName!!, dob!!)
                    val authResponse = repository.login(loginPost)
                    authResponse?.let {
                        authListener?.onSuccess(it)
                        //repository.saveUser(it)
                        return@main
                    }
                    authListener?.onFailure(authResponse.success.toString())
                } catch (e: ApiException) {
                    authListener?.onFailure(e.message!!)
                } catch (e: NoInternetException) {
                    authListener?.onFailure(e.message!!)
                }
            }
    }
}