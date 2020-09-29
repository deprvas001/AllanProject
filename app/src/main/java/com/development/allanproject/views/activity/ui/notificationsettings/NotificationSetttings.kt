package com.development.allanproject.views.activity.ui.notificationsettings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.FaqAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityNotificationBinding
import com.development.allanproject.databinding.ActivityNotificationSettingsBinding
import com.development.allanproject.databinding.ActivityNotificationSetttingsBinding
import com.development.allanproject.model.faq.FaqData
import com.development.allanproject.model.notificationModel.GetNotificationSettings
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.settingsListener.NotificaitonSettingsListener
import com.development.allanproject.views.activity.ui.profilesetting.ProfileSettingViewModel
import com.development.allanproject.views.activity.ui.profilesetting.ProfileSettingViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class NotificationSetttings : AppCompatActivity(), View.OnClickListener,NotificaitonSettingsListener, AuthListener, KodeinAware  {
    private lateinit var binding: ActivityNotificationSetttingsBinding
    private lateinit var viewModel: NotificationSettingViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    override val kodein by kodein()
    private val factory: NotificationSettingsViewModelfactory by instance()
    lateinit var sessionManager: SessionManager
    var dataList: ArrayList<FaqData> = ArrayList()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var isAdd: Boolean? = false
    var adapter: FaqAdapter? = null
    var download:String?=null
    var hashMap: HashMap<String, Any> = HashMap()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification_setttings)
        viewModel = ViewModelProvider(this, factory).get(NotificationSettingViewModel::class.java)
        viewModel.authListener = this
        viewModel.notificationAuthListener = this

        binding.back.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)

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

        viewModel.getNotificationSettings(header)

    }

    override fun onClick(view: View?) {
        if (view!!.id == R.id.back) {
            finish()
        }else if(view!!.id == R.id.btn_save){
            if(binding.all.isChecked){
                hashMap.clear()

                hashMap.put("all", "on")
                hashMap.put("shift", "off")
                hashMap.put("shift_email", "off")
            }else{
                hashMap.clear()
                hashMap.put("all", "off")
                 if(binding.shiftNotification.isChecked){
                     hashMap.put("shift", "on")
                 }else{
                     hashMap.put("shift", "off")
                 }

                if(binding.emailNotification.isChecked){
                    hashMap.put("shift_email", "on")
                }else{
                    hashMap.put("shift_email", "off")
                }
            }

            viewModel.postSetting(header, hashMap)
        }
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: GetNotificationSettings) {
        progress_bar.hide()
        if(response.success && response.code==200 && response.status.equals("ok")){
            if(!response.data.all.isNullOrEmpty()){
                if(response.data.all.equals("on")){
                    binding.all.isChecked =true
                }else {
                    binding.all.isChecked =false
                }

                if(response.data.shift.equals("on")){
                    binding.shiftNotification.isChecked = true
                }else{
                    binding.shiftNotification.isChecked = false
                }

                if(response.data.shift_email.equals("on")){
                    binding.emailNotification.isChecked = true
                }else{
                    binding.emailNotification.isChecked = false
                }
            }
        }
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if(response.success && response.code.toInt() == 200){
            toast("Success")
        }else {
            toast(response.msg)
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}
