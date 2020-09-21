package com.development.allanproject.views.activity.ui.uploadid.form

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.adddocumentModel.PostDocument
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.documentListener.DocumentListener
import com.development.allanproject.util.formListener.FormListener

class FormViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var docAuthListener: DocumentListener? = null
    var authListener: AuthListener? = null
    var formAuthListener: FormListener?=null

    fun getFormList(
        header: HashMap<String, String>
    ){
        formAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getFormList(header,"23")
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