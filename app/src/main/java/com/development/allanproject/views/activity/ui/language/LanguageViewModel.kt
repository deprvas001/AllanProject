package com.development.allanproject.views.activity.ui.language

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.backinformation.PostBackgroundInformation
import com.development.allanproject.model.license.LicenseUpdate
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.backgronddata.BackgroundAuthListener
import com.development.allanproject.util.lanugageListener.LanguageAuthListener

class LanguageViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var langAuthListener: LanguageAuthListener? = null
    var authListener: AuthListener? = null

    fun getLangInfo(
        header: HashMap<String, String>
    ){
        langAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.languageInfo(header,"29")
                authResponse?.let {
                    langAuthListener?.onSuccess(it)
                    return@main
                }
                langAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                langAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                langAuthListener?.onFailure(e.message!!)
            } } }


    fun updateBackInfo(
        header: HashMap<String, String>,
        detail : ArrayList<HashMap<String, Any>>

    ) {
        authListener?.onStarted()
        Coroutines.main {
            try {
                var postInfo = PostBackgroundInformation(28, detail)
                val authResponse = repository.updatebackgroundInfo(header, postInfo)
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