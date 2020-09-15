package com.development.allanproject.views.activity.ui.education.viewmodel

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.model.education.AddDetails
import com.development.allanproject.model.education.AddEductionModel
import com.development.allanproject.model.experience.AddExperiencePost
import com.development.allanproject.model.experience.DeleteExperience
import com.development.allanproject.model.signupModel.ExperiencePost
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.educationListener.EducationAuthListener
import com.development.allanproject.util.workExperienceListener.AuthWorkExperienceListener
import com.development.allanproject.views.activity.AddEducation

class EducationViewModel (
    private val repository: UserRepository

) : ViewModel() {
    var experience: String? = null
    var authListener: AuthListener? = null
    var educationAuthListener: EducationAuthListener? = null
    var hashMap: HashMap<String, Any> = HashMap<String, Any>()
    lateinit var sessionManager: SessionManager


    fun getEducationList(
        header: HashMap<String, String>
    ) {
        educationAuthListener?.onStarted()
        Coroutines.main {
            try {

                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.getEducationList(header, "14")
                authResponse?.let {
                    educationAuthListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                educationAuthListener?.onFailure(authResponse.success.toString())
            } catch (e: ApiException) {
                educationAuthListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                educationAuthListener?.onFailure(e.message!!)
            }
        }
    }

    fun deleteEducation(
        header: HashMap<String, String>,
        id: Int
    ) {
        authListener?.onStarted()
        Coroutines.main {
            try {
                var deleteItem: HashMap<String, Int> = HashMap()
                deleteItem.put("id", id)

                val workDetail = DeleteExperience(deleteItem, 14, "delete")

                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.deleteWorkExperienceList(header, workDetail)
                authResponse?.let {
                    authListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.success.toString())
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }
    }

    fun addEducation(
        header: HashMap<String, String>,
        addDetails: AddDetails,
        action: String
    ) {
        authListener?.onStarted()
        Coroutines.main {
            try {
                 var addEducation = AddEductionModel(14,action, addDetails!!)
                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.addEducation(header, addEducation)
                authResponse?.let {
                    authListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.success.toString())
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }
    }

}