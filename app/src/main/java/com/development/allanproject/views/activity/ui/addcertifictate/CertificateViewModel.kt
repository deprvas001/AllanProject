package com.development.allanproject.views.activity.ui.addcertifictate

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.experience.DeleteExperience
import com.development.allanproject.model.license.LicenseUpdate
import com.development.allanproject.model.signupModel.PersonalDetailPost
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.certificateListener.CertificateListener
import com.development.allanproject.util.licenseListener.LicenseAuthListener

class CertificateViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var authListener: AuthListener? = null
    var certificateAuthListener: CertificateListener?=null

    fun getCertificateList(
        header: HashMap<String, String>
    ){
        certificateAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getCertificateList(header,"5")
                authResponse?.let {
                    certificateAuthListener?.onSuccess(it)
                    return@main
                }
                certificateAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                certificateAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                certificateAuthListener?.onFailure(e.message!!)
            } } }

    fun addCertificate(
        header: HashMap<String, String>,
        hashMap: HashMap<String,Any>,
        action:String
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{

                val personalDetail = LicenseUpdate(hashMap,5, action)
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

    fun deleteCertificate(
        header: HashMap<String, String>,
        id: Int
    ) {
        authListener?.onStarted()
        Coroutines.main {
            try {
                var deleteItem: HashMap<String, Int> = HashMap()
                deleteItem.put("id", id)

                val workDetail = DeleteExperience(deleteItem, 5, "delete")

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