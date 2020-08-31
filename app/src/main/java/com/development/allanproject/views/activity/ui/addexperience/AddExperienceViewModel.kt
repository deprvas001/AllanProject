package com.development.allanproject.views.activity.ui.addexperience

import android.view.View
import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.signupModel.Details
import com.development.allanproject.model.signupModel.PersonalDetailPost
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException

class AddExperienceViewModel (
    private val repository: UserRepository

) : ViewModel() {
    var experience: String? = null
    var authListener: AuthListener? = null
    var hashMap:HashMap<String,Any> = HashMap<String,Any>()

    fun onNextButtonClick(view: View) {
        authListener?.onStarted()
        if (experience.isNullOrEmpty()) {
            authListener?.onFailure("Please add experience")
            return
        }

//        Coroutines.main {
//            try{
//
////               hashMap.set("experience",experience!!)
//              //  var detail = Details(hashMap)
//                val personalDetail = PersonalDetailPost(hashMap,6)
//                //  val authResponse = repository.userLogin(firstName!!, dob!!)
//                val authResponse = repository.userLogin(personalDetail)
//                authResponse?.let {
//                    authListener?.onSuccess(it)
//                    //repository.saveUser(it)
//                    return@main
//                }
//                authListener?.onFailure(authResponse.success.toString())
//            }catch (e: ApiException){
//                authListener?.onFailure(e.message!!)
//            }catch (e: NoInternetException){
//                authListener?.onFailure(e.message!!)
//            }
//        }
    }

}