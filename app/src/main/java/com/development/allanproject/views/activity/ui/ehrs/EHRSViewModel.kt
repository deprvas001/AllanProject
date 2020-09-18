package com.development.allanproject.views.activity.ui.ehrs

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.experience.DeleteExperience
import com.development.allanproject.model.license.LicenseUpdate
import com.development.allanproject.model.signupModel.DocumentDetailPost
import com.development.allanproject.model.signupModel.PersonalDetailPost
import com.development.allanproject.model.signupModel.SetPreferencePost
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.ehrsListener.EHRSAuthListener
import com.development.allanproject.util.specialityListener.SpecialityAuthListener

class EHRSViewModel (
    private val repository: UserRepository
): ViewModel() {

    var authListener: AuthListener? = null
    var ehrsAuthListener: EHRSAuthListener?=null

    fun getEHRS(
        header: HashMap<String, String>,
        stepNo: String
    ){
        ehrsAuthListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getEHRS(header,stepNo)
                authResponse?.let {
                    ehrsAuthListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                ehrsAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                ehrsAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                ehrsAuthListener?.onFailure(e.message!!)
            }
        }
    }

    fun deleteWorkExperience(
        header: HashMap<String, String>,
        id:Int
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{
                var deleteItem:HashMap<String,Int> = HashMap()
                deleteItem.put("id",id)



                val workDetail = DeleteExperience(deleteItem,21,"delete")

                //  val authResponse = repository.userLogin(firstName!!, dob!!)
                val authResponse = repository.deleteWorkExperienceList(header,workDetail)
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

    fun addSpeciality(
        header: HashMap<String, String>,
        hashMap: HashMap<String, Any>
    ){
        authListener?.onStarted()

        Coroutines.main {
            try{
                val personalDetail = LicenseUpdate(hashMap,21, "create")
                val authResponse = repository.licenseUpdate(header,personalDetail)

                //  val personalDetail = PersonalDetailPost(hashMap,4)
                //  val authResponse = repository.userLogin(firstName!!, dob!!)

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