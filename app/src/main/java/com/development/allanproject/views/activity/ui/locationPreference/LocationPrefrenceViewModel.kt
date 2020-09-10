package com.development.allanproject.views.activity.ui.locationPreference

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.locationPost.LocationPreferencePost
import com.development.allanproject.model.signupModel.DocumentDetailPost
import com.development.allanproject.model.signupModel.PersonalDetailPost
import com.development.allanproject.model.signupModel.SetPreferencePost
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException

class LocationPrefrenceViewModel(private val repository: UserRepository
): ViewModel() {

    var authListener: AuthListener? = null


    fun addDocumentDetail(
        header: HashMap<String, String>,
        hashMap: HashMap<String, Any>,
        stepNo: Int
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{

                val documentDetail = LocationPreferencePost(hashMap,stepNo)
                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.locationPost(header,documentDetail)
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

    fun getCityList(
        header: HashMap<String, String>
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{

                val authResponse = repository.getCityList(header)
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