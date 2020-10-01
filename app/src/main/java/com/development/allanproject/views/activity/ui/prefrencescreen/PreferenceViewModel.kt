package com.development.allanproject.views.activity.ui.prefrencescreen

import android.view.View
import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.personalDetail.PersonalDetailPostParam
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.preferenceListener.PreferenceListener

class PreferenceViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var preferencecAuthListener: PreferenceListener? = null
    var authListener:AuthListener?=null


    fun getPrefereneList(
        header: HashMap<String, String>
    ){
        preferencecAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getPreference(header,"34")
                authResponse?.let {
                    preferencecAuthListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                preferencecAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                preferencecAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                preferencecAuthListener?.onFailure(e.message!!)
            }
        }
    }

    fun updatePrefereneList(
        header: HashMap<String, String>,
        detail: HashMap<String, Any>
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.updatePreference(header, detail)
                authResponse?.let {
                    authListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }
        }
    }


}

