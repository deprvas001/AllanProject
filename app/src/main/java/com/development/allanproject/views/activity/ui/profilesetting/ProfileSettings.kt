package com.development.allanproject.views.activity.ui.profilesetting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.FaqAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityProfileSettingsBinding
import com.development.allanproject.model.faq.FaqData
import com.development.allanproject.model.profilesettings.GetMyProfileNotification
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.myprofileNotificationListener.MyProfileNotificationListener
import com.development.allanproject.views.activity.ui.changepassword.ChangePasswordScreen
import com.development.allanproject.views.activity.ui.editProfile.EditProfileScreen
import com.development.allanproject.views.activity.ui.faq.FaqActivity
import com.development.allanproject.views.activity.ui.myprofile.MyProfileViewModel
import com.development.allanproject.views.activity.ui.myprofile.MyProfileViewModelFactory
import com.development.allanproject.views.activity.ui.notificationsettings.NotificationSetttings
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_personal_detail.progress_bar
import kotlinx.android.synthetic.main.activity_personal_detail.root_layout
import kotlinx.android.synthetic.main.activity_profile_summary_screen.*
import kotlinx.android.synthetic.main.forms_row.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ProfileSettings : AppCompatActivity(), View.OnClickListener ,AuthListener, KodeinAware, MyProfileNotificationListener{
   private lateinit var binding: ActivityProfileSettingsBinding
    private lateinit var viewModel: ProfileSettingViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    override val kodein by kodein()
    private val factory: ProfileSettingViewModelFactory by instance()
    lateinit var sessionManager: SessionManager
    var dataList: ArrayList<FaqData> = ArrayList()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var isAdd: Boolean? = false
    var adapter: FaqAdapter? = null
    var download:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_settings)
        viewModel = ViewModelProvider(this, factory).get(ProfileSettingViewModel::class.java)
        viewModel.profileAuthListener = this
        viewModel.authListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]


        header.set("user_id",user_id!!)
        header.set(
            "Authorization",token!!
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        binding.faq.setOnClickListener(this)
        binding.back.setOnClickListener(this)
        binding.btnProfile.setOnClickListener(this)
        binding.logout.setOnClickListener(this)
        binding.btnChangePassword.setOnClickListener(this)
        binding.btnNotification.setOnClickListener(this)

        viewModel.getMyProfileNotification(header)

        binding.jobProfile.setOnCheckedChangeListener { compoundButton, checked ->
            var hashMap:HashMap<String,Any> = HashMap()
            hashMap.put("step_no", 32)
            if(checked){
                hashMap.put("status", "on")
            }else{
                hashMap.put("status", "off")
            }
           viewModel.updateMyProfileNotification(header, hashMap)
        }
    }

    override fun onClick(view: View?) {
       if(view!!.id == R.id.faq){
           startActivity(Intent(this, FaqActivity::class.java))
       }else if(view!!.id == R.id.back){
           finish()
       }else if(view!!.id == R.id.btn_profile){
            startActivity(Intent(this, EditProfileScreen::class.java))
       }else if(view!!.id == R.id.logout){
           viewModel.logout(header)
       }else if(view!!.id == R.id.btn_change_password){
           startActivity(Intent(this, ChangePasswordScreen::class.java))
       }else if(view!!.id == R.id.btn_notification){
           startActivity(Intent(this, NotificationSetttings::class.java))
       }
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if(response.success && response.code.toInt() == 200){
            toast("Success")
        }else if(response.code.toInt() == 201){
            toast(response.msg)
            sessionManager.logoutUser()
            finish()
        }
    }

    override fun onSuccess(response: GetMyProfileNotification) {
        progress_bar.hide()
        if(response.success && response.code == 200){
            if(response.data.status.equals("on")){
                binding.jobProfile.isChecked = true
            }else{
                binding.jobProfile.isChecked = false
            }

        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}
