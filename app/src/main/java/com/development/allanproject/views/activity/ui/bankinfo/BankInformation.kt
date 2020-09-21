package com.development.allanproject.views.activity.ui.bankinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.development.allanproject.R
import com.development.allanproject.adapter.EHRSAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityBankInformationBinding
import com.development.allanproject.model.bankinfo.BankData
import com.development.allanproject.model.bankinfo.BankInfoResponse
import com.development.allanproject.model.ehrs.EHRSList
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.bankListener.BankAuthListener
import com.development.allanproject.views.activity.ui.ehrs.EHRSViewModel
import com.development.allanproject.views.activity.ui.ehrs.EHRSViewModelFactory
import io.reactivex.internal.operators.maybe.MaybeToPublisher.instance
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class BankInformation : AppCompatActivity(), AuthListener,BankAuthListener, KodeinAware {
    private lateinit var binding: ActivityBankInformationBinding
    private lateinit var viewModel: BankViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()

    var isAdd: Boolean = false
    override val kodein by kodein()
    private val factory: BankViewModelFactory by instance()
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_bank_information)

        viewModel = ViewModelProvider(this, factory).get(BankViewModel::class.java)
        viewModel.authListener = this
        viewModel.bankAuthListener = this


        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]


        header.set("user_id", user_id!!)
        header.set(
            "Authorization",token!!
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        viewModel.getBankInfo(header)

        binding.btnLogin.setOnClickListener {
            if(binding.inputBank.text.isNullOrEmpty()){
                toast("Add Bank Account Number")
                return@setOnClickListener
            }else if(binding.routeInfo.text.isNullOrEmpty()){
                toast("Add Routing Number")
                return@setOnClickListener
            }else{
                var hashMap:HashMap<String,String> = HashMap()
                hashMap.put("account_no", binding.inputBank.text.toString())
                hashMap.put("routing_no", binding.routeInfo.text.toString())

                viewModel.updateBankInfo(header,hashMap)

            }
        }

    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code.equals("200")) {
         toast("Successfully Added")
        } else {
            toast(response.status)
        }
    }

    override fun onSuccess(response: BankInfoResponse) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code == 200) {
           if(response.data.size>0){

               var bank = response.data.get(response.data.size-1)
               binding.inputBank.setText(bank.account_no)
               binding.routeInfo.setText(bank.routing_no)
           }

        } else {
            toast(response.status)
        }
    }



    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}
