package com.development.allanproject.views.activity.ui.missedShift

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.missedShift.GetMissedShift
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.missedShiftListener.MissedShiftListener
import com.development.allanproject.util.preferedfacilityListener.PreferedFacilityListener

class MissedShiftViewModel  (
    private val repository: UserRepository
): ViewModel() {

    var shiftListener: MissedShiftListener? = null
    var authListener: AuthListener?=null

    fun getShift(
        header: HashMap<String, String>
    ){
        shiftListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getMissedShift(header)
                authResponse?.let {
                    shiftListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                shiftListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                shiftListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                shiftListener?.onFailure(e.message!!)
            }
        }
    }

}