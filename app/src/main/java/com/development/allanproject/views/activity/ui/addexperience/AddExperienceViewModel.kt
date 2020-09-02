package com.development.allanproject.views.activity.ui.addexperience

import android.text.Editable
import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.model.signupModel.ExperiencePost
import com.development.allanproject.model.signupModel.PersonalDetailPost
import com.development.allanproject.util.*

class AddExperienceViewModel (
    private val repository: UserRepository

) : ViewModel() {
    var experience: String? = null
    var authListener: AuthListener? = null
    var hashMap:HashMap<String,Any> = HashMap<String,Any>()
    lateinit var sessionManager: SessionManager


  /*  fun onNextButtonClick(view: View) {
        authListener?.onStarted()
        if (experience.isNullOrEmpty()) {
            authListener?.onFailure("Please add experience")
            return
        }

        Coroutines.main {
            try{
                hashMap.put("experience",experience!!)

                val personalDetail = ExperiencePost(hashMap,6)
                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.experiencePost(header,personalDetail)
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
    }*/

    fun addExperienceDetail(
        header: HashMap<String, String>,
        experience: String?
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{

                val personalDetail = ExperiencePost(experience!!,6)
                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.experiencePost(header,personalDetail)
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