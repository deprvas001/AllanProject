package com.development.allanproject.views.activity.ui.appointment

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.apiResponse.SignUpResponse
import com.development.allanproject.model.appointmentModel.PostAppointment
import com.development.allanproject.model.locationPost.LocationPreferencePost
import com.development.allanproject.util.*

class BoookAppointmentViewModel (private val repository: UserRepository
): ViewModel() {

    var authListener: AuthBookAppointment? = null
    var postListener: AuthListener? = null


    fun getAppointmentList(
        header: HashMap<String, String>
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{

                val authResponse = repository.getAppointment(header)
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

    fun postAppointmentList(
        header: HashMap<String, String>,
        hashMap:HashMap<String,Int>
    ){
        postListener?.onStarted()

        Coroutines.main {
            try{

                val postAppointment = PostAppointment(11,hashMap)
                val authResponse = repository.postAppointment(header, postAppointment)
                authResponse?.let {

                    postListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                postListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                postListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                postListener?.onFailure(e.message!!)
            }
        }
    }

}