package com.development.allanproject.views.activity.ui.addlicenese

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.experience.DeleteExperience
import com.development.allanproject.model.license.LicenseUpdate
import com.development.allanproject.model.signupModel.PersonalDetailPost
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.licenseListener.LicenseAuthListener

class AddLicenseViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var authListener: AuthListener? = null
    var liceseAuthListener: LicenseAuthListener?=null

    fun addLicense(
        header: HashMap<String, String>,
        hashMap: ArrayList<HashMap<String, Any>>
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{

                val personalDetail = PersonalDetailPost(hashMap,3)
                val authResponse = repository.userLogin(header,personalDetail)
                authResponse?.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                authListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            } } }

    fun updateLicense(
        header: HashMap<String, String>,
        hashMap: HashMap<String,Any>,
        action:String
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{

                val personalDetail = LicenseUpdate(hashMap,3, action)
                val authResponse = repository.licenseUpdate(header,personalDetail)
                authResponse?.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                authListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            } } }

    fun getLicenseList(
        header: HashMap<String, String>
    ){
        liceseAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getLicenseList(header,"3")
                authResponse?.let {
                    liceseAuthListener?.onSuccess(it)
                    return@main
                }
                liceseAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                liceseAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                liceseAuthListener?.onFailure(e.message!!)
            } } }


    fun deleteEducation(
        header: HashMap<String, String>,
        id: Int
    ) {
        authListener?.onStarted()
        Coroutines.main {
            try {
                var deleteItem: HashMap<String, Int> = HashMap()
                deleteItem.put("id", id)

                val workDetail = DeleteExperience(deleteItem, 3, "delete")

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
}