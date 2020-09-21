package com.development.allanproject.views.activity.ui.socialsecurity

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.adddocumentModel.PostDocument
import com.development.allanproject.model.socialsecurity.PostSocialSecurity
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.documentListener.DocumentListener
import com.development.allanproject.util.formListener.FormListener
import com.development.allanproject.util.socialsecurity.SocialSecurityListener

class SocailSecurityViewModel  (
    private val repository: UserRepository
) : ViewModel() {
    var socialAuthListener: SocialSecurityListener? = null
    var authListener: AuthListener? = null
    var formAuthListener: FormListener?=null

    fun getDocumentList(
        header: HashMap<String, String>
    ){
        socialAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getSecurityList(header,"27")
                authResponse?.let {
                    socialAuthListener?.onSuccess(it)
                    return@main
                }
                socialAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                socialAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                socialAuthListener?.onFailure(e.message!!)
            } } }

    fun addDocument(
        header: HashMap<String, String>,
        hashMap: HashMap<String,Any>,
        action :String
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{
                val detail = PostSocialSecurity(action,hashMap,27)
                val authResponse = repository.postSecurity(header,detail)
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