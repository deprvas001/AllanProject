package com.development.allanproject.views.activity.ui.faq

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.award.PostAward
import com.development.allanproject.model.license.LicenseUpdate
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.awardListener.AwardAuthListener
import com.development.allanproject.util.faqListener.FaqAuthlIstener

class FaqViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var faqAuthListener: FaqAuthlIstener? = null
  //  var authListener: AuthListener? = null

    fun getFaq(
        header: HashMap<String, String>
    ){
        faqAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getFaq(header)
                authResponse?.let {
                    faqAuthListener?.onSuccess(it)
                    return@main
                }
                faqAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                faqAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                faqAuthListener?.onFailure(e.message!!)
            } } }



}