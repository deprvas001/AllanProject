package com.development.allanproject.views.activity.ui.editProfile

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.license.LicenseUpdate
import com.development.allanproject.model.research.PostResearch
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.editListener.EditListener
import com.development.allanproject.util.researchListener.ResearchAuthListener

class EditProfileViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var editAuthListener: EditListener? = null
  //  var authListener: AuthListener? = null

    fun getProfile(
        header: HashMap<String, String>
    ){
        editAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getProfile(header,"profile")
                authResponse?.let {
                    editAuthListener?.onSuccess(it)
                    return@main
                }
                editAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                editAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                editAuthListener?.onFailure(e.message!!)
            } } }


}