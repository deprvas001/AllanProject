package com.development.allanproject.views.activity.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.R
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityLoginBinding
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.views.activity.SignUpComplete
import com.development.allanproject.views.activity.ui.addexperience.addWorkExperience.AddExtraExperienceInfo
import com.development.allanproject.views.activity.ui.addexperience.addWorkExperience.AddExperience
import com.development.allanproject.views.activity.ui.addlicenese.AddLicenses
import com.development.allanproject.views.activity.ui.locationPreference.AddLocationPreference
import com.development.allanproject.views.activity.ui.personal.PersonalDetail
import com.development.allanproject.views.activity.ui.setPreference.SetPreferenceScreen
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.speciality.AddSpeciality
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*

class LoginActivity : AppCompatActivity() , AuthListener, KodeinAware, View.OnClickListener{
    private lateinit var binding:ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    lateinit var session: SessionManager
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
    var personalDetail: HashMap<String, Any> = HashMap<String, Any>()
    var header: HashMap<String, String> = HashMap<String, String>()
    var profile_pic:String?=null

    override val kodein by kodein()
    private val factory: LoginViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

        binding.signup.setOnClickListener(this)

        session = SessionManager(applicationContext)

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
            root_layout.snackbar("${response.msg}")
            if(response.success){
                val user_id: String = response.user_id.toString()
                val token: String =  response.auth_token
                var stepNo:Int = response.step_no
                var isCompleted: Boolean = response.is_sign_up_completed
                session.createLoginSession(user_id, token)

                if(isCompleted){
                    startActivity(Intent(this,SignUpComplete::class.java))
                    finish()
                }else{
                   if(stepNo == 2){
                       startActivity(Intent(this, PersonalDetail::class.java))
                       finish()
                   }else if(stepNo == 3){
                       startActivity(Intent(this, AddLicenses::class.java))
                       finish()
                   }else if(stepNo == 4){
                       startActivity(Intent(this, AddSpeciality::class.java))
                       finish()
                   }
                   else if(stepNo == 5){
                       startActivity(Intent(this, com.development.allanproject.views.activity.ui.addcertifictate.AddCertificate::class.java))
                       finish()
                   } else if(stepNo == 6){
                       startActivity(Intent(this, AddExperience::class.java))
                       finish()
                   }else if(stepNo == 7){
                       startActivity(Intent(this, AddExtraExperienceInfo::class.java))
                       finish()
                   }else if(stepNo == 8){
                       startActivity(Intent(this, com.development.allanproject.views.activity.ui.setFaciliity.FacilityType::class.java))
                       finish()
                   }else if(stepNo == 9){
                       startActivity(Intent(this, SetPreferenceScreen::class.java))
                       finish()
                   }else if(stepNo == 10){
                       startActivity(Intent(this, AddLocationPreference::class.java))
                       finish()
                   }else if(stepNo == 10){
                       startActivity(Intent(this, PersonalDetail::class.java))
                       finish()
                   }
                }


            }else{
                toast("Try Later")
            }
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    override fun onClick(view: View?) {
        if(view?.id == R.id.signup){
            startActivity(Intent(this, SignUp::class.java))
        }
    }
}
