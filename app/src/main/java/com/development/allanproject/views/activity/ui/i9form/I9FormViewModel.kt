package com.development.allanproject.views.activity.ui.i9form

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.i9form.PostI9Form
import com.development.allanproject.model.taxholding.PostTaxData
import com.development.allanproject.model.taxholding.PostTaxList
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.i9formListener.I9FormListener
import com.development.allanproject.util.taxListener.TaxAuthListener

class I9FormViewModel (
    private val repository: UserRepository
): ViewModel() {

    var authListener: AuthListener? = null
    var i9FormListener: I9FormListener?=null

    fun getTax(
        header: HashMap<String, String>,
        stepNo: String
    ){
        i9FormListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getI9Form(header,stepNo)
                authResponse?.let {
                    i9FormListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                i9FormListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                i9FormListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                i9FormListener?.onFailure(e.message!!)
            }
        }
    }


    fun  updateForm(
        header: HashMap<String, String>,
        detail: PostI9Form
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{

                val authResponse = repository.postI9Form(header,detail)
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