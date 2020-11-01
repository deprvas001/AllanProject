package com.development.allanproject.views.activity.ui.hiddenjob

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.openshiftModel.PostBookmark
import com.development.allanproject.model.pastShiftModel.RateFacilityModel
import com.development.allanproject.model.pastShiftModel.RequestPayModel
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.hiddenJobListener.HiddenJobListener
import com.development.allanproject.util.pastShiftListener.PastShiftListener

class HiddenJobViewModel (
    private val repository: UserRepository
): ViewModel() {

    var jobListener: HiddenJobListener? = null
    var authListener: AuthListener?=null

    fun getShift(
        header: HashMap<String, String>
    ){
        jobListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getHiddenJob(header)
                authResponse?.let {
                    jobListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                jobListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                jobListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                jobListener?.onFailure(e.message!!)
            }
        }
    }

}