package com.development.allanproject.views.activity.ui.backgroundinformation

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.backinformation.PostBackground
import com.development.allanproject.model.backinformation.PostBackgroundInformation
import com.development.allanproject.model.bankinfo.PostBankInfo
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.backgronddata.BackgroundAuthListener
import com.development.allanproject.util.bankListener.BankAuthListener

class BackgroundInformationViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var backgroundAuthListener: BackgroundAuthListener? = null
    var authListener: AuthListener? = null

    fun getBackInfo(
        header: HashMap<String, String>
    ){
        backgroundAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.backgroundInfo(header,"28")
                authResponse?.let {
                    backgroundAuthListener?.onSuccess(it)
                    return@main
                }
                backgroundAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                backgroundAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                backgroundAuthListener?.onFailure(e.message!!)
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

}