package com.development.allanproject.views.activity.ui.addexperience.addWorkExperience

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.R
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityAddExperienceBinding
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.views.activity.ui.addexperience.viewmodel.AddExperienceViewModel
import com.development.allanproject.views.activity.ui.addexperience.viewmodel.AddExperienceViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class AddExperience : AppCompatActivity() , AuthListener, KodeinAware {
    private lateinit var binding: ActivityAddExperienceBinding
    private lateinit var viewModel : AddExperienceViewModel
    lateinit var sessionManager: SessionManager
    var header: HashMap<String, String> = HashMap<String, String>()
    override val kodein by kodein()
    private val factory: AddExperienceViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_experience)
        viewModel = ViewModelProvider(this, factory).get(AddExperienceViewModel::class.java)
        viewModel.authListener = this

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

        binding.btnNext.setOnClickListener {

            if(binding.inputExperience.text.isNullOrEmpty()){
                toast("Add Experience")
            }else{
                viewModel.addExperienceDetail(header, binding.inputExperience.text.toString())
            }
        }

    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        root_layout.snackbar("${response.step_no} is Done")
        if(response.code.equals("500")){
            root_layout.snackbar("${response.status}")
        }else{
            root_layout.snackbar("${response.success}")
            if(response.success){
                startActivity(Intent(this, AddExtraExperienceInfo::class.java))
                finish()
            }else{
                toast("Try Later")
            }
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}
