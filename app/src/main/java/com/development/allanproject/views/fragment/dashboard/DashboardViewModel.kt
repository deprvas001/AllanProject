package com.development.allanproject.views.fragment.dashboard

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.dashboardModel.GetDashboard
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.dashboardListener.DashboardListener
import com.development.allanproject.util.myshiftListener.MyShiftListener
import com.development.allanproject.util.openshiftListener.OpenShiftListener

class DashboardViewModel (
    private val repository: UserRepository
): ViewModel() {

    var dashboardListener: DashboardListener? = null
    var openListener: OpenShiftListener? = null

    fun getDashboard(
        header: HashMap<String, String>
    ){
        dashboardListener?.onStarted()

        Coroutines.main {
            try{
                val authResponse = repository.getDashboard(header)
                authResponse?.let {
                    dashboardListener?.onSuccess(it)
                    //repository.saveUser(it)
                    return@main
                }
                dashboardListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                dashboardListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                dashboardListener?.onFailure(e.message!!)
            }
        }
    }



}