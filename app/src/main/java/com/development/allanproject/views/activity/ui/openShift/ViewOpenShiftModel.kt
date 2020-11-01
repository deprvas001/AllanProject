package com.development.allanproject.views.activity.ui.openShift

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.cancelShiftModel.CancelShiftPost
import com.development.allanproject.model.openshiftModel.ApplyShiftPost
import com.development.allanproject.model.openshiftModel.GetOpenShiftDetail
import com.development.allanproject.model.openshiftModel.SaveShiftPost
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.openshiftListener.OpenShiftDetailListener
import com.development.allanproject.util.openshiftListener.OpenShiftListener

class ViewOpenShiftModel (
    private val repository: UserRepository
): ViewModel() {

    var openListener: OpenShiftDetailListener? = null
    var authListener: AuthListener?=null

    fun cancelShift(
        header: HashMap<String, String>,
        detail: CancelShiftPost
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.cancelShift(header,detail)
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

    fun getShift(
        header: HashMap<String, String>,
        id:String
    ){
        openListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getOpenShiftDetail(header,id)
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

    fun saveShift(
        header: HashMap<String, String>,
        detail: SaveShiftPost
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.saveShift(header,detail)
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

    fun applyShift(
        header: HashMap<String, String>,
        detail: ApplyShiftPost
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.applyShift(header,detail)
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