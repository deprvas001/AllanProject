package com.development.allanproject.views.activity.ui.myprofile

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.faqListener.FaqAuthlIstener
import com.development.allanproject.util.myprofileListener.MyProfileListener

class MyProfileViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var profileAuthListener: MyProfileListener? = null
    //  var authListener: AuthListener? = null

    fun getProfile(
        header: HashMap<String, String>
    ){
        profileAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getMyProfile(header,"2")
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



}