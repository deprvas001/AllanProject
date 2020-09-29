package com.development.allanproject.views.activity.ui.notificationscreen

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.myprofileListener.MyProfileListener
import com.development.allanproject.util.notificationListener.NotificationAuthListener

class NotificationViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var authListener: NotificationAuthListener? = null
    //  var authListener: AuthListener? = null

    fun getNotification(
        header: HashMap<String, String>
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getNotification(header)
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