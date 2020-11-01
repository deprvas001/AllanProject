package com.development.allanproject.views.activity.ui.clockInShift

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.clockShiftModel.ClockInShiftPost
import com.development.allanproject.model.openshiftModel.PostBookmark
import com.development.allanproject.model.pastShiftModel.RateFacilityModel
import com.development.allanproject.model.pastShiftModel.RequestPayModel
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.openshiftListener.OpenShiftDetailListener
import com.development.allanproject.util.pastShiftListener.PastShiftListener

class ClockShiftViewModel (
    private val repository: UserRepository
): ViewModel() {

    var openListener:  OpenShiftDetailListener? = null
    var authListener: AuthListener?=null


    fun clockIn(
        header: HashMap<String, String>,
        post: ClockInShiftPost
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.postClockIn(header,post)
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
}