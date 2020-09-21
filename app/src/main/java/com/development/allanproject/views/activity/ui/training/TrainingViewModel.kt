package com.development.allanproject.views.activity.ui.training

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.reference.ReferencePost
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.referenceListener.ReferenceAuthListener
import com.development.allanproject.util.trainingListener.TrainingAuthListener

class TrainingViewModel (
    private val repository: UserRepository
): ViewModel() {

    var authListener: AuthListener? = null
    var trainingAuthListener: TrainingAuthListener?=null

    fun getPdf(
        header: HashMap<String, String>,
        stepNo: String
    ){
        trainingAuthListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getPdf(header,stepNo)
                authResponse?.let {
                    trainingAuthListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                trainingAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                trainingAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                trainingAuthListener?.onFailure(e.message!!)
            }
        }
    }

}