package com.development.allanproject.views.activity.ui.personal

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.R
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityPersonalDetailBinding
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.views.activity.AddExperience
import com.development.allanproject.views.activity.AddLicense
import com.development.allanproject.views.activity.ui.addexperience.AddExtraExperienceInfo
import com.development.allanproject.views.activity.ui.addlicenese.AddLicenses
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.File
import java.util.*

class PersonalDetail : AppCompatActivity(), AuthListener, KodeinAware, View.OnClickListener {
    private lateinit var binding: ActivityPersonalDetailBinding
    private lateinit var viewModel: PersonDetailViewModel
    lateinit var sessionManager: SessionManager
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
    var personalDetail: HashMap<String, Any> = HashMap<String, Any>()
    var header: HashMap<String, String> = HashMap<String, String>()
    var profile_pic:String?=null

    override val kodein by kodein()
    private val factory: PersonalDetailViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_detail)
        viewModel = ViewModelProvider(this, factory).get(PersonDetailViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

        sessionManager = SessionManager(this)
        // get user data from session
        // get user data from session
        val user: HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]
        header.set("user_id", user_id!!)
        header.set("Authorization", token!!)
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        binding.profileImage.setOnClickListener(this)

        binding.btnNext.setOnClickListener(this)

        binding.dob.setOnClickListener(this)
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
                startActivity(Intent(this,AddLicenses::class.java))
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

    override fun onClick(view: View?) {
       if(view?.id == R.id.profile_image){
           ImagePicker.with(this)
               .crop()	    			//Crop image(Optional), Check Customization for more option
               .compress(1024)			//Final image size will be less than 1 MB(Optional)
               .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
               .start()
       }else if(view?.id == R.id.btn_next){
           if (binding.inputFirst.text.isNullOrEmpty() || binding.dob.text.isNullOrEmpty()) {
              // root_layout.snackbar("Fill all Details")
               toast("Fill All Personal Details")
               return
           }else if (profile_pic.isNullOrEmpty()){
               toast("Upload Profile Image")
               return
           }else{
                  hashMap.clear()
                  personalDetail.clear()
                  personalDetail.set(
                      "name", binding.inputFirst.text.append(" ")
                          .append(binding.inputLast.text).toString()
                  )
                  personalDetail.set("dob", binding.dob.text.toString())
                  personalDetail.set("img_url", profile_pic!!)
//               hashMap.add(personalDetail)
                  viewModel.addPersonalDetail(header,personalDetail)

           }
       }else if(view?.id == R.id.dob){
           val c = Calendar.getInstance()
           val year = c.get(Calendar.YEAR)
           val month = c.get(Calendar.MONTH)
           val day = c.get(Calendar.DAY_OF_MONTH)

           val datePickerDialog = DatePickerDialog(
               this,
               DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                   // Display Selected date in textbox
                   binding.dob.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth)
               }, year, month, day
           )
           //   mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
           datePickerDialog.getDatePicker()
               .setMaxDate(System.currentTimeMillis() - 568025136000L)
           datePickerDialog.show()
       }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            binding.profileImage.setImageURI(fileUri)
            profile_pic = "https://i.picsum.photos/id/658/200/300.jpg?hmac=K1TI0jSVU6uQZCZkkCMzBiau45UABMHNIqoaB9icB_0"
          //  toast(profile_pic.toString())
            //You can get File object from intent
            val file: File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            val filePath:String = ImagePicker.getFilePath(data)!!
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            toast( ImagePicker.getError(data))
        } else {
            toast("Task Cancelled")
        }
    }
}
