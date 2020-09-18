package com.development.allanproject.views.activity.ui.healthdocument

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.experience.DeleteExperience
import com.development.allanproject.model.healthDocument.HealthDocPost
import com.development.allanproject.model.license.LicenseUpdate
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.certificateListener.CertificateListener
import com.development.allanproject.util.healthdocListener.HealthDocAuthListener

class HealthDocumentViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var authListener: AuthListener? = null
    var healthAuthListener: HealthDocAuthListener?=null

    fun getHealthDocumentList(
        header: HashMap<String, String>
    ){
        healthAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getHealthDocList(header,"17")
                authResponse?.let {
                    healthAuthListener?.onSuccess(it)
                    return@main
                }
                healthAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                healthAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                healthAuthListener?.onFailure(e.message!!)
            } } }

    fun postHealthDocument(
        header: HashMap<String, String>,
        detail :HashMap<String,Any>
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{
                val healthDoc = HealthDocPost(detail,17, "create")

                val authResponse = repository.postHealthDoc(header,healthDoc)
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

}