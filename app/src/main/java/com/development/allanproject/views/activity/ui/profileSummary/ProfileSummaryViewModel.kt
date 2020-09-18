package com.development.allanproject.views.activity.ui.profileSummary

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.appointmentModel.PostAppointment
import com.development.allanproject.util.*

class ProfileSummaryViewModel (private val repository: UserRepository
): ViewModel() {

    var authListener: ProfileSummaryAuthListener? = null
    /*var postListener: AuthListener? = null*/
    fun getProfileSummary(
        header: HashMap<String, String>,
        type:String
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getProfileSummary(header,type)
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