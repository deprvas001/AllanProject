package com.development.allanproject.views.activity.ui.reference

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.experience.DeleteExperience
import com.development.allanproject.model.license.LicenseUpdate
import com.development.allanproject.model.reference.ReferencePost
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.ehrsListener.EHRSAuthListener
import com.development.allanproject.util.referenceListener.ReferenceAuthListener

class ReferenceViewModel (
    private val repository: UserRepository
): ViewModel() {

    var authListener: AuthListener? = null
    var referenceAuthListener: ReferenceAuthListener?=null

    fun getReference(
        header: HashMap<String, String>,
        stepNo: String
    ){
        referenceAuthListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getReferenceList(header,stepNo)
                authResponse?.let {
                    referenceAuthListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                referenceAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                referenceAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                referenceAuthListener?.onFailure(e.message!!)
            }
        }
    }

    fun addReference(
        header: HashMap<String, String>,
        stepNo: Int,
        api_action:String,
        hashMap: HashMap<String,Any>
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{
                var reference = ReferencePost(stepNo,api_action,hashMap)
                val authResponse = repository.referencePost(header,reference)
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