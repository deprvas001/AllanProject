package com.development.allanproject.views.activity.ui.speciality

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.commonapi.ShiftPreference
import com.development.allanproject.model.signupModel.DocumentDetailPost
import com.development.allanproject.model.signupModel.PersonalDetailPost
import com.development.allanproject.model.signupModel.SetPreferencePost
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException

class AddSpecialityViewModel(
    private val repository: UserRepository
): ViewModel() {

    var authListener: AuthListener? = null

    fun addDocument(
        header: HashMap<String, String>,
        hashMap: ArrayList<HashMap<String, Any>>
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{

                val personalDetail = PersonalDetailPost(hashMap,4)
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

    fun addDocumentDetail(
        header: HashMap<String, String>,
        hashMap: ArrayList<Any>,
        stepNo: Int
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{

                val documentDetail = DocumentDetailPost(hashMap,stepNo)
                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.documentPost(header,documentDetail)
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

    fun addPreference(
        header: HashMap<String, String>,
        shiftPreference: ArrayList<Int>,
        shiftType: ArrayList<Int>,
        stepNo: Int
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{

                val documentDetail = SetPreferencePost(shiftPreference, shiftType,stepNo)
                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.addPerference(header,documentDetail)
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