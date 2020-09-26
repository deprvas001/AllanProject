package com.development.allanproject.views.activity.ui.changepassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.R
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityChangePasswordScreenBinding
import com.development.allanproject.model.bankinfo.BankInfoResponse
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.bankListener.BankAuthListener
import com.development.allanproject.views.activity.ui.bankinfo.BankViewModel
import com.development.allanproject.views.activity.ui.bankinfo.BankViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ChangePasswordScreen : AppCompatActivity(), AuthListener, KodeinAware, View.OnClickListener {
    private lateinit var binding: ActivityChangePasswordScreenBinding
    private lateinit var viewModel: ChangePasswordViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()

    var isAdd: Boolean = false
    override val kodein by kodein()
    private val factory: ChangePassViewModelFactory by instance()
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password_screen)

        viewModel = ViewModelProvider(this, factory).get(ChangePasswordViewModel::class.java)
        viewModel.authListener = this


        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]


        header.set("user_id", "22")
        header.set(
            "Authorization","eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjAxMjE2NTg4fQ.Z-6fanu-unfVNECm5acIiUGxmwP4j4Ww7kixoNp6d5k"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        binding.update.setOnClickListener(this)
        binding.back.setOnClickListener(this)

    //    viewModel.updateBankInfo(header)

    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code.equals("200")) {
            toast("Change Password Successfully ")
        }
        else {
            toast(response.msg)
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    override fun onClick(view: View?) {
        if(view!!.id == R.id.update){
            if(binding.oldPassword.text.isNullOrEmpty()){
                toast("Please enter old password")
                return
            }else if(binding.newPassword.text.isNullOrEmpty()){
                toast("Please enter new password")
                return
            }else if(!binding.newPassword.text.toString().equals(binding.reconfirmPassword.text.toString())){
                toast("New Password And Confirm Password not match")
                return
            }else{
                var detail:HashMap<String,String> = HashMap()
                detail.put("old_password", binding.oldPassword.text.toString())
                detail.put("password", binding.newPassword.text.toString())
                viewModel.updateBankInfo(header,detail)
            }
        }else if(view!!.id == R.id.back){
            finish()
        }
    }
}
