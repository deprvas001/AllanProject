package com.development.allanproject.views.activity.ui.addexperience.viewmodel

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.model.experience.AddExperiencePost
import com.development.allanproject.model.experience.DeleteExperience
import com.development.allanproject.model.signupModel.ExperiencePost
import com.development.allanproject.util.*
import com.development.allanproject.util.workExperienceListener.AuthWorkExperienceListener

class AddExperienceViewModel (
    private val repository: UserRepository

) : ViewModel() {
    var experience: String? = null
    var authListener: AuthListener? = null
    var experienceAuthListener: AuthWorkExperienceListener?=null
    var hashMap:HashMap<String,Any> = HashMap<String,Any>()
    lateinit var sessionManager: SessionManager


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


    fun addWorkDetail(
        header: HashMap<String, String>,
        details:HashMap<String,Any>

     ){
        authListener?.onStarted()

        Coroutines.main {
            try{

                val workDetail = AddExperiencePost(details!!,7)
                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.workPost(header,workDetail)
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

    fun getWorkExperience(
        header: HashMap<String, String>
    ){
        experienceAuthListener?.onStarted()
        Coroutines.main {
            try{

                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.getWorkExperienceList(header,"7")
                authResponse?.let {
                    experienceAuthListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                experienceAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                experienceAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                experienceAuthListener?.onFailure(e.message!!)
            }
        }
    }

    fun deleteWorkExperience(
        header: HashMap<String, String>,
        id:Int
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{
               var deleteItem:HashMap<String,Int> = HashMap()
                deleteItem.put("id",id)

                val workDetail = DeleteExperience(deleteItem,7,"delete")

                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.deleteWorkExperienceList(header,workDetail)
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