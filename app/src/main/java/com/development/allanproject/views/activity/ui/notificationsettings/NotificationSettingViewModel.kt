package com.development.allanproject.views.activity.ui.notificationsettings

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.notificationModel.GetNotificationSettings
import com.development.allanproject.model.notificationModel.PostNotificationSettings
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.myprofileNotificationListener.MyProfileNotificationListener
import com.development.allanproject.util.settingsListener.NotificaitonSettingsListener

class NotificationSettingViewModel (
    private val repository: UserRepository
) : ViewModel() {
    var notificationAuthListener: NotificaitonSettingsListener? = null
    var authListener: AuthListener? = null


    fun getNotificationSettings(
        header: HashMap<String, String>
    ){
        notificationAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.getNotificationSettings(header,"33")
                authResponse?.let {
                    notificationAuthListener?.onSuccess(it)
                    return@main
                }
                notificationAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                notificationAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                notificationAuthListener?.onFailure(e.message!!)
            } } }

    fun postSetting(
        header: HashMap<String, String>,
        detail: HashMap<String,Any>
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{
                val postSetting = PostNotificationSettings(detail,33)
                val authResponse = repository.postNotification(header,postSetting)
                authResponse?.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                authListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            } } }


}