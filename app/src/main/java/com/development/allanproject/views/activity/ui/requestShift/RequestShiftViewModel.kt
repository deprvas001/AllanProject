package com.development.allanproject.views.activity.ui.requestShift

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.openshiftListener.OpenShiftListener

class RequestShiftViewModel (
    private val repository: UserRepository
): ViewModel() {

    var openListener: OpenShiftListener? = null

    fun getShift(
        header: HashMap<String, String>,
        type:String
    ){
        openListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getRequestShift(header,type)
                authResponse?.let {
                    openListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                openListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                openListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                openListener?.onFailure(e.message!!)
            }
        }
    }


}