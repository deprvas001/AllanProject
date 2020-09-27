package com.development.allanproject.views.activity.ui.awards

import androidx.lifecycle.ViewModel
import com.development.allanproject.adapter.AwardAdapter
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.award.PostAward
import com.development.allanproject.model.backinformation.PostBackgroundInformation
import com.development.allanproject.model.license.LicenseUpdate
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.awardListener.AwardAuthListener
import com.development.allanproject.util.lanugageListener.LanguageAuthListener

class AwardViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var awardAuthListener: AwardAuthListener? = null
    var authListener: AuthListener? = null

    fun getAward(
        header: HashMap<String, String>
    ){
        awardAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getAward(header,"30")
                authResponse?.let {
                    awardAuthListener?.onSuccess(it)
                    return@main
                }
                awardAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                awardAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                awardAuthListener?.onFailure(e.message!!)
            } } }


    fun updateAward(
        header: HashMap<String, String>,
        detail : HashMap<String, Any>,
        api_action: String

    ) {
        authListener?.onStarted()
        Coroutines.main {
            try {
                var award = PostAward(30,api_action,detail)
                val authResponse = repository.updateAward(header, award)
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