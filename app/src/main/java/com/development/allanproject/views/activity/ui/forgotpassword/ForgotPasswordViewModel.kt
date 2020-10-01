package com.development.allanproject.views.activity.ui.forgotpassword

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.faqListener.FaqAuthlIstener

class ForgotPasswordViewModel (
    private val repository: UserRepository
) : ViewModel() {
      var authListener: AuthListener? = null

    fun forgotPassword(
        header: HashMap<String, String>,
        detail: HashMap<String, Any>
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.forgotPassword(header,detail)
                authResponse?.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                authListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            } } }



}