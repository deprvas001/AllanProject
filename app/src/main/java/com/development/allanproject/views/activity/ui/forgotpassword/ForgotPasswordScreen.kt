package com.development.allanproject.views.activity.ui.forgotpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.FaqAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityForgotPasswordScreenBinding
import com.development.allanproject.model.faq.FaqData
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.faqListener.FaqAuthlIstener
import com.development.allanproject.views.activity.ui.faq.FaqViewModel
import com.development.allanproject.views.activity.ui.faq.FaqViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ForgotPasswordScreen : AppCompatActivity() , AuthListener, KodeinAware {
    private lateinit var binding: ActivityForgotPasswordScreenBinding
    private lateinit var viewModel: ForgotPasswordViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    override val kodein by kodein()
    private val factory: ForgotPasswordViewModelFactory by instance()
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forgot_password_screen)

        viewModel = ViewModelProvider(this, factory).get(ForgotPasswordViewModel::class.java)
        viewModel.authListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]


        header.set("user_id", user_id!!)
        header.set(
            "Authorization", token!!
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        binding.btnForgot.setOnClickListener {
            if(binding.inputEmail.text.isNullOrEmpty()){
                toast("Please add email")
                return@setOnClickListener
            }else{
                var hashMap:HashMap<String,Any> = HashMap()
                 hashMap.put("email", binding.inputEmail.text.toString())
                viewModel.forgotPassword(header,hashMap)
            }
        }

        binding.back.setOnClickListener {
            finish()
        }
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if(response.success && response.code.toInt() == 200 && response.status.equals("ok")){
            toast("Please check you mail.")
        }else{
            toast(response.msg)
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}
