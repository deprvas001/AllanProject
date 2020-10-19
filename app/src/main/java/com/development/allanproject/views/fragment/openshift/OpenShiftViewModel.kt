package com.development.allanproject.views.fragment.openshift

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.openshiftListener.OpenShiftListener

class OpenShiftViewModel (
    private val repository: UserRepository
): ViewModel() {

    var openListener: OpenShiftListener? = null

    fun getShift(
        header: HashMap<String, String>
    ){
        openListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getOpenShift(header)
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