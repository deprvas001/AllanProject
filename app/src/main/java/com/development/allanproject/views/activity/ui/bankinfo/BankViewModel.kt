package com.development.allanproject.views.activity.ui.bankinfo

import androidx.lifecycle.ViewModel
import com.development.allanproject.data.repository.UserRepository
import com.development.allanproject.model.bankinfo.PostBankInfo
import com.development.allanproject.model.experience.DeleteExperience
import com.development.allanproject.model.license.LicenseUpdate
import com.development.allanproject.model.signupModel.PersonalDetailPost
import com.development.allanproject.util.ApiException
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.Coroutines
import com.development.allanproject.util.NoInternetException
import com.development.allanproject.util.bankListener.BankAuthListener
import com.development.allanproject.util.licenseListener.LicenseAuthListener

class BankViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var bankAuthListener: BankAuthListener? = null
    var authListener: AuthListener? = null

    fun getBankInfo(
        header: HashMap<String, String>
    ){
        bankAuthListener?.onStarted()
        Coroutines.main {
            try{

                val authResponse = repository.bankInfo(header,"26")
                authResponse?.let {
                    bankAuthListener?.onSuccess(it)
                    return@main
                }
                bankAuthListener?.onFailure(authResponse.success.toString())
            }catch (e: ApiException){
                bankAuthListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                bankAuthListener?.onFailure(e.message!!)
            } } }

    fun updateBankInfo(
        header: HashMap<String, String>,
        hashMap: HashMap<String,String>
    ){
        authListener?.onStarted()
        Coroutines.main {
            try{
                 var postInfo = PostBankInfo("26", hashMap,"create")
                val authResponse = repository.updatebankInfo(header,postInfo)
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