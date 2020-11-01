package com.development.allanproject.views.activity.ui.clockoutDetail

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.clockShiftModel.ClockInShiftPost
import com.development.allanproject.model.clockoutModel.BreakTimePost
import com.development.allanproject.model.clockoutModel.ClockInOutPost
import com.development.allanproject.model.clockoutModel.GetClockOutDetail
import com.development.allanproject.model.clockoutModel.PostClockOutDetail
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.clockoutListener.ClockOutLIstener
import com.development.allanproject.util.openshiftListener.OpenShiftDetailListener

class ClockOutViewModel  (
    private val repository: UserRepository
): ViewModel() {

    var openListener: ClockOutLIstener? = null
    var authListener: AuthListener?=null

    fun clockOut(
        header: HashMap<String, String>,
        post: PostClockOutDetail
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.postClockOut(header,post)
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

    fun getClockOutShift(
        header: HashMap<String, String>,
        id:String
    ){
        openListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getClockOutDetail(header,id)
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

    fun timeUpdate(
        header: HashMap<String, String>,
        post: ClockInOutPost
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.postTimeUpdate(header,post)
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

    fun breakTimeUpdate(
        header: HashMap<String, String>,
        post: BreakTimePost
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.postBreakTime(header,post)
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