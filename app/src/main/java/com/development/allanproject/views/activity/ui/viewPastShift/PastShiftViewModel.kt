package com.development.allanproject.views.activity.ui.viewPastShift

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.openshiftModel.ApplyShiftPost
import com.development.allanproject.model.openshiftModel.PostBookmark
import com.development.allanproject.model.openshiftModel.SaveShiftPost
import com.development.allanproject.model.pastShiftModel.RateFacilityModel
import com.development.allanproject.model.pastShiftModel.RequestPayModel
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.openshiftListener.OpenShiftDetailListener
import com.development.allanproject.util.pastShiftListener.PastShiftListener

class PastShiftViewModel (
    private val repository: UserRepository
): ViewModel() {

    var openListener: PastShiftListener? = null
    var authListener: AuthListener?=null

    fun getShift(
        header: HashMap<String, String>,
        id:String
    ){
        openListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getPastShiftDetail(header,id)
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


    fun requestPay(
        header: HashMap<String, String>,
        detail: RequestPayModel
    ){
       authListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.requestPay(header,detail)
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

    fun rateFacility(
        header: HashMap<String, String>,
        detail: RateFacilityModel
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.rateFacility(header,detail)
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

    fun postBookmark(
        header: HashMap<String, String>,
        post: PostBookmark
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.postBookmark(header,post)
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