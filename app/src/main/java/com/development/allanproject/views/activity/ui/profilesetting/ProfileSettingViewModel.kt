package com.development.allanproject.views.activity.ui.profilesetting

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.myprofileListener.MyProfileListener
import com.development.allanproject.util.myprofileNotificationListener.MyProfileNotificationListener

class ProfileSettingViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var profileAuthListener: MyProfileNotificationListener? = null
      var authListener: AuthListener? = null

    fun getMyProfileNotification(
        header: HashMap<String, String>
    ){
        profileAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getNotificationProfile(header,"32")
                authResponse?.let {
                    profileAuthListener?.onSuccess(it)
                    return@main
                }
                profileAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                profileAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                profileAuthListener?.onFailure(e.message!!)
            } } }

    fun updateMyProfileNotification(
        header: HashMap<String, String>,
        detail: HashMap<String,Any>
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.updateNotificationProfile(header,detail)
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


    fun logout(
        header: HashMap<String, String>
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.userLogout(header)
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