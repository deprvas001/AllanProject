package com.development.allanproject.views.activity.ui.changepassword

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.bankinfo.PostBankInfo
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.bankListener.BankAuthListener

class ChangePasswordViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var bankAuthListener: BankAuthListener? = null
    var authListener: AuthListener? = null

    fun updateBankInfo(
        header: HashMap<String, String>,
        detail: HashMap<String,String>
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.changePassword(header,detail)
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