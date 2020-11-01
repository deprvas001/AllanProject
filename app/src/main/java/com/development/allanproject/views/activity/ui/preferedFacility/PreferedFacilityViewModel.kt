package com.development.allanproject.views.activity.ui.preferedFacility

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.hiddenJobListener.HiddenJobListener
import com.development.allanproject.util.preferedfacilityListener.PreferedFacilityListener

class PreferedFacilityViewModel  (
    private val repository: UserRepository
): ViewModel() {

    var facilityListener: PreferedFacilityListener? = null
    var authListener: AuthListener?=null

    fun getFacility(
        header: HashMap<String, String>
    ){
        facilityListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getPreferedFacility(header)
                authResponse?.let {
                    facilityListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                facilityListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                facilityListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                facilityListener?.onFailure(e.message!!)
            }
        }
    }

}