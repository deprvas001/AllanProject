package com.development.allanproject.views.activity.ui.taxholding

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.taxListener.TaxAuthListener
import com.development.allanproject.util.trainingListener.TrainingAuthListener

class TaxHoldingViewModel (
    private val repository: UserRepository
): ViewModel() {

    var authListener: AuthListener? = null
    var taxAuthListener: TaxAuthListener?=null

    fun getTax(
        header: HashMap<String, String>,
        stepNo: String
    ){
        taxAuthListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getTaxHolding(header,stepNo)
                authResponse?.let {
                    taxAuthListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                taxAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                taxAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                taxAuthListener?.onFailure(e.message!!)
            }
        }
    }

}