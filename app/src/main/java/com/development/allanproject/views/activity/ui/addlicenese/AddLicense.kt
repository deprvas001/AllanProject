package com.development.allanproject.views.activity.ui.addlicenese

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.R
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityAddLicenseBinding
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class AddLicense : AppCompatActivity(), AuthListener, KodeinAware {
    private lateinit var binding: ActivityAddLicenseBinding
    private lateinit var viewModel: AddLicenseViewModel
     var array: ArrayList<String> = ArrayList<String>()
    lateinit var sessionManager: SessionManager
    var header:HashMap<String,String> = HashMap<String,String>()
    var imgUrl: List<String> = listOf<String>("https://dab1nmslvvntp.cloudfront.net/wp-content/uploads/2012/11/params-300x66.png",
        "https://dab1nmslvvntp.cloudfront.net/wp-content/uploads/2012/11/params-300x66.png")
    override val kodein by kodein()
    private val factory: AddLicenseViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_license)
        viewModel = ViewModelProvider(this, factory).get(AddLicenseViewModel::class.java)
        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        viewModel.licenceCompact = "Yes"
        array.add("https://homepages.cae.wisc.edu/~ece533/images/airplane.png")
        viewModel.licenseId = "1"
        viewModel.licenseNo = "A932DWE3234EDFG"
        viewModel.state = "Delhi"
        viewModel.imgUrl = array
        viewModel.authListener = this
        binding.viewmodel = viewModel
        var hashMap:ArrayList<HashMap<String,Any>> = ArrayList<HashMap<String,Any>>()
        var licenseRequest: HashMap<String,Any> = HashMap<String,Any>()

        header.set("user_id",user_id!!)
        header.set("Authorization",token!!)
        header.set("device_type_id","1")
        header.set("v_code","7")
       // viewModel.licenseDetail(viewModel)
        binding.btnNext.setOnClickListener {
            for ( x in 0..1){
                licenseRequest.clear()
                licenseRequest.set("licence_id","1")
                licenseRequest.set("licence_no","AWNDO451AW")
                licenseRequest.set("state","Delhi")
                licenseRequest.set("licence_compact","Yes")
                licenseRequest.set("issue_date","2018-04-21")
                licenseRequest.set("expiration_date","2020-04-21")
                licenseRequest.set("img_url",imgUrl)
                hashMap.add(licenseRequest)
            }
              viewModel.addLicense(header,hashMap)
              print(licenseRequest.toString())
        }
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        root_layout.snackbar("${response.step_no} is Done")

    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}
