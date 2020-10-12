package com.development.allanproject.views.activity.ui.facilityprofile

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.experience.DeleteExperience
import com.development.allanproject.model.license.LicenseUpdate
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.ehrsListener.EHRSAuthListener
import com.development.allanproject.util.facilityprofileListener.FacilityProfileAuthListener

class FacilityProfileViewModel (
    private val repository: UserRepository
): ViewModel() {

    var facilityAuthListener: FacilityProfileAuthListener? = null

    fun getFacilityProfile(
        header: HashMap<String, String>,
        id: String
    ){
        facilityAuthListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getFacilityProfile(header,id)
                authResponse?.let {
                    facilityAuthListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                facilityAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                facilityAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                facilityAuthListener?.onFailure(e.message!!)
            }
        }
    }

}