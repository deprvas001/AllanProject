package com.development.allanproject.views.activity.ui.adddoucment

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.adddocumentModel.PostDocument
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.documentListener.DocumentListener
import com.development.allanproject.util.formListener.FormListener

class AddDocumentViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var docAuthListener: DocumentListener? = null
    var authListener: AuthListener? = null
    var formAuthListener: FormListener?=null


    fun getDocumentList(
        header: HashMap<String, String>
    ){
        docAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getDocumentList(header,"23")
                authResponse?.let {
                    docAuthListener?.onSuccess(it)
                    return@main
                }
                docAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                docAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                docAuthListener?.onFailure(e.message!!)
            } } }

    fun addDocument(
        header: HashMap<String, String>,
        hashMap: HashMap<String,Any>,
        action :String
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{
                val detail = PostDocument(action,hashMap,23)
                val authResponse = repository.addDocument(header,detail)
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




    fun getFormList(
        header: HashMap<String, String>
    ){
        formAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getFormList(header,"24")
                authResponse?.let {
                    formAuthListener?.onSuccess(it)
                    return@main
                }
                formAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                formAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                formAuthListener?.onFailure(e.message!!)
            } } }
}