package com.development.allanproject.views.activity.ui.research

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.award.PostAward
import com.development.allanproject.model.license.LicenseUpdate
import com.development.allanproject.model.research.GetResearch
import com.development.allanproject.model.research.PostResearch
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.awardListener.AwardAuthListener
import com.development.allanproject.util.researchListener.ResearchAuthListener

class ResearchViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var researchAuthListener: ResearchAuthListener? = null
    var authListener: AuthListener? = null

    fun getResearch(
        header: HashMap<String, String>
    ){
        researchAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getResearch(header,"31")
                authResponse?.let {
                    researchAuthListener?.onSuccess(it)
                    return@main
                }
                researchAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                researchAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                researchAuthListener?.onFailure(e.message!!)
            } } }


    fun updateResearch(
        header: HashMap<String, String>,
        detail : HashMap<String, Any>,
        api_action: String

    ) {
        authListener?.onStarted()
        Coroutines.main {
            try {
                var award = PostResearch(31,api_action,detail)
                val authResponse = repository.postResearch(header, award)
                authResponse?.let {
                    authListener?.onSuccess(it)
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

    fun addLanguage(
        header: HashMap<String, String>,
        hashMap: HashMap<String, Any>,
        api_action:String
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{
                val personalDetail = LicenseUpdate(hashMap,29, api_action)
                val authResponse = repository.licenseUpdate(header,personalDetail)

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