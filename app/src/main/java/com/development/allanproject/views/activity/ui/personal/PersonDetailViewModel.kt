package com.development.allanproject.views.activity.ui.personal

import android.view.View
import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.personalDetail.PersonalDetailPostParam
import com.development.allanproject.model.signupModel.Details
import com.development.allanproject.model.signupModel.PersonalDetailPost
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException

class PersonDetailViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var firstName: String? = null
    var lastName: String? = null
    var dob: String? = null
    var authListener: AuthListener? = null
    var hashMap:HashMap<String,Any> = HashMap<String,Any>()

    fun onNextButtonClick(view: View) {
        authListener?.onStarted()
        if (firstName.isNullOrEmpty() || lastName.isNullOrEmpty() || dob.isNullOrEmpty()) {
            authListener?.onFailure("Please fill all detail")
            return
        }

//        Coroutines.main {
//            try{
//
//               // var detail = Details(dob!!,""!!,firstName!!)
//              /*  hashMap.set("name",firstName!!)
//                hashMap.set("img_url", "https://dab1nmslvvntp.cloudfront.net/wp-content/uploads/2012/11/params-300x66.png"!!)
//                hashMap.set("dob",dob!!)*/
//                val personalDetail = PersonalDetailPost(hashMap,2)
//              //  val authResponse = repository.userLogin(firstName!!, dob!!)
//                val authResponse = repository.userLogin(personalDetail)
//                authResponse?.let {
//                    authListener?.onSuccess(it)
//                    //repository.saveUser(it)
//                    return@main
//                }
//                authListener?.onFailure(authResponse.success.toString())
//            }catch (e: ApiException){
//                authListener?.onFailure(e.message!!)
//            }catch (e: NoInternetException){
//                authListener?.onFailure(e.message!!)
//            }
//        }
    }

    fun addPersonalDetail(
        header: HashMap<String, String>,
        hashMap: HashMap<String, Any>
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{

                val personalDetail = PersonalDetailPostParam(hashMap,2)
                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.userDetail(header,personalDetail)
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