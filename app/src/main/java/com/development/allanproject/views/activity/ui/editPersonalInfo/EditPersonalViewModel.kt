package com.development.allanproject.views.activity.ui.editPersonalInfo

import android.view.View
import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.personalDetail.PersonalDetailPostParam
import com.development.allanproject.model.personalDetail.PersonalInfromationUpdate
import com.development.allanproject.util.*

class EditPersonalViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var firstName: String? = null
    var lastName: String? = null
    var dob: String? = null
    var authPersonalListener: AuthPersonalDetail? = null
    var authListener: AuthListener? = null
    var hashMap:HashMap<String,Any> = HashMap<String,Any>()

    fun onNextButtonClick(view: View) {
        authListener?.onStarted()
        if (firstName.isNullOrEmpty() || lastName.isNullOrEmpty() || dob.isNullOrEmpty()) {
            authListener?.onFailure("Please fill all detail")
            return
        }

    }

    fun getPersonalDetail(
        header: HashMap<String, String>,
        step: String
    ){
        authPersonalListener?.onStarted()
        Coroutines.main {
            try{

                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.getPersonalDetail(header,step)
                authResponse?.let {
                    authPersonalListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                authPersonalListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                authPersonalListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authPersonalListener?.onFailure(e.message!!)
            }
        }
    }

    fun updatePersonalDetail(
        header: HashMap<String, String>,
        detail: HashMap<String,String>
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{

                val personalInfo  = PersonalInfromationUpdate(detail,2,"update")
                val authResponse = repository.updatePersonalDetail(header,personalInfo)
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