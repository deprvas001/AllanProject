package com.development.allanproject.views.activity.ui.myprofile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.FaqAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityMyProfileScreenBinding
import com.development.allanproject.downloadfile.DownloadTask
import com.development.allanproject.model.faq.FaqData
import com.development.allanproject.model.myprofile.GetMyProfile
import com.development.allanproject.util.*
import com.development.allanproject.util.myprofileListener.MyProfileListener
import com.development.allanproject.views.activity.Settings
import com.development.allanproject.views.activity.ui.editPersonalInfo.EditPersonalInfo
import com.development.allanproject.views.activity.ui.prefrencescreen.PreferenceScreen
import com.development.allanproject.views.activity.ui.profilesetting.ProfileSettings
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MyProfileScreen : AppCompatActivity(), MyProfileListener, KodeinAware {
    private lateinit var binding: ActivityMyProfileScreenBinding
    private lateinit var viewModel: MyProfileViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    override val kodein by kodein()
    private val factory: MyProfileViewModelFactory by instance()
    lateinit var sessionManager: SessionManager
    var dataList: ArrayList<FaqData> = ArrayList()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var isAdd: Boolean? = false
    var adapter: FaqAdapter? = null
    var download:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile_screen)
        viewModel = ViewModelProvider(this, factory).get(MyProfileViewModel::class.java)
        viewModel.profileAuthListener = this

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

        viewModel.getProfile(header)

        binding.download.setOnClickListener{
           if(!download.isNullOrEmpty()){
               DownloadTask(this, download)
           }
        }

        binding.settingsLayout.setOnClickListener{
            startActivity(Intent(this, ProfileSettings::class.java))
        }

        binding.edit.setOnClickListener{
            startActivity(Intent(this, EditPersonalInfo::class.java))
        }

        binding.preferenceScreenLayout.setOnClickListener{
            startActivity(Intent(this, PreferenceScreen::class.java))
        }
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: GetMyProfile) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code == 200) {
            binding.name.setText(response.data.name)
            binding.address.setText(response.data.address)
            binding.phone.setText(response.data.phone)
            binding.email.setText(response.data.email)

            download = response.data.profile_url

            if(!response.data.img_url.isNullOrEmpty()){
                Util.loadImage(
                    binding.profileImage, response.data.img_url,
                    Util.getCircularDrawable(this)
                )
            }
        }
    }


    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}
