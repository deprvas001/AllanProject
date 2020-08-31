package com.development.allanproject.views.activity.ui.addlicenese

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.signupModel.PersonalDetailPost
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException

class AddLicenseViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var licenseId: String? = null
    var licenseNo: String? = null
    var state: String? = null
    var licenceCompact:String? = null
    var issueDate: String? = null
    var expDate: String? = null
    var imgUrl: List<String> = listOf<String>("https://dab1nmslvvntp.cloudfront.net/wp-content/uploads/2012/11/params-300x66.png",
        "https://dab1nmslvvntp.cloudfront.net/wp-content/uploads/2012/11/params-300x66.png")
    var hashMap:HashMap<String,Any> = HashMap<String,Any>()

    var authListener: AuthListener? = null

  /*  fun onNextButtonClick(view: View) {
        authListener?.onStarted()
        if (licenseId.isNullOrEmpty() || licenseNo.isNullOrEmpty() || state.isNullOrEmpty()
            || issueDate.isNullOrEmpty() || expDate.isNullOrEmpty() ) {
            authListener?.onFailure("Please fill all detail")
            return
        }

        Coroutines.main {
            try{
                hashMap.set("licence_id",licenseId!!)
                hashMap.set("licence_no",licenseNo!!)
                hashMap.set("state",state!!)
                hashMap.set("licence_compact",licenceCompact!!)
                hashMap.set("issue_date",issueDate!!)
                hashMap.set("expiration_date",expDate!!)
                hashMap.set("img_url",imgUrl)
                val personalDetail = PersonalDetailPost(hashMap,3)
                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.userLogin(personalDetail)
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
    }*/

    fun addLicense(
        header: HashMap<String, String>,
        hashMap: ArrayList<HashMap<String, Any>>
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{

                val personalDetail = PersonalDetailPost(hashMap,3)
                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.userLogin(header,personalDetail)
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