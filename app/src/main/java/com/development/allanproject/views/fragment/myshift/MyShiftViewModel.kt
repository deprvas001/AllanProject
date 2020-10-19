package com.development.allanproject.views.fragment.myshift

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.myshiftListener.MyShiftListener
import com.development.allanproject.util.openshiftListener.OpenShiftListener

class MyShiftViewModel (
    private val repository: UserRepository
): ViewModel() {

    var myShiftListener: MyShiftListener? = null

    fun getShift(
        header: HashMap<String, String>,
        type:String
    ){
        myShiftListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getMyShift(header,type)
                authResponse?.let {
                    myShiftListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                myShiftListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                myShiftListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                myShiftListener?.onFailure(e.message!!)
            }
        }
    }


}