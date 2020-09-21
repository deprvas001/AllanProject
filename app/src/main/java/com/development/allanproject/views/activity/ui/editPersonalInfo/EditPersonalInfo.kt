package com.development.allanproject.views.activity.ui.editPersonalInfo

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.R
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityEditPersonalInfoBinding
import com.development.allanproject.model.personalDetail.GetPersonalDetail
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class EditPersonalInfo : AppCompatActivity(), AuthPersonalDetail, AuthListener
    , KodeinAware, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private lateinit var binding: ActivityEditPersonalInfoBinding
    private lateinit var viewModel: EditPersonalViewModel
    lateinit var sessionManager: SessionManager
    var profile_pic: String? = null
    var compatValue: String? = null
    var header: HashMap<String, String> = HashMap()
    var detail: HashMap<String, String> = HashMap()
    override val kodein by kodein()

    private val factory: EditPersonalViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_personal_info)
        viewModel = ViewModelProvider(this, factory).get(EditPersonalViewModel::class.java)
        viewModel.authPersonalListener = this
        viewModel.authListener = this

        initializeView()

        binding.back.setOnClickListener(this)
        binding.dob.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
        binding.profileImage.setOnClickListener(this)
        binding.radioGroup.setOnCheckedChangeListener(this)

    }


    override fun onStarted() {
        progress_bar.show()
        toast("Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if (response.success && response.status == "ok" && response.code.equals("200")) {
            toast("Personal Information Update Successfully")
        } else {
            toast(response.msg)
        }
    }

    override fun onSuccess(response: GetPersonalDetail) {
        progress_bar.hide()
        if (response.code.equals("500")) {
            root_layout.snackbar("${response.status}")
        } else {
            root_layout.snackbar("${response.success}")
            if (response.success && response.status == "ok" && response.code == 200) {

                toast("Success")
                binding.personalDeatail = response.data
                if (!response.data.img_url.isNullOrEmpty()) {
                    profile_pic = response.data.img_url
                }
                if (!response.data.gender.isNullOrEmpty()) {
                    if (response.data.gender.equals("Male")) {
                        binding.radio1.isChecked = true
                    } else if (response.data.gender.equals("Female")) {
                        binding.radio2.isChecked = true
                    } else if (response.data.gender.equals("Others")) {
                        binding.radio2.isChecked = true
                    }
                }
            } else {
                toast("Try Later")
                finish()
            }
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    fun getPersonalDetail() {
        viewModel.getPersonalDetail(header, "2")
    }

    override fun onClick(view: View?) {
        if (view!!.id == R.id.back) {
            finish()
        } else if (view.id == R.id.dob) {
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
        } else if (view?.id == R.id.profile_image) {
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        } else if (view?.id == R.id.btn_next) {
            if (binding.inputName.text.isNullOrEmpty()) {
                toast("Please fill name")
                return
            } else if (binding.dob.text.isNullOrEmpty()) {
                toast("Please add date of birth")
                return
            } else if (profile_pic.isNullOrEmpty()) {
                toast("Please Upload Profile Image.")
                return
            } else if (compatValue.isNullOrEmpty()) {
                toast("Please select gender")
                return
            } else if (binding.address.text.isNullOrEmpty()) {
                toast("Please add address")
                return
            } else {
                updateProfile()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            binding.profileImage.setImageURI(fileUri)
            profile_pic =
                "https://i.picsum.photos/id/658/200/300.jpg?hmac=K1TI0jSVU6uQZCZkkCMzBiau45UABMHNIqoaB9icB_0"

            val file: File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            val filePath: String = ImagePicker.getFilePath(data)!!
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            toast(ImagePicker.getError(data))
        } else {
            toast("Task Cancelled")
        }
    }

    fun initializeView() {

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

        getPersonalDetail()
    }

    override fun onCheckedChanged(radio: RadioGroup?, checkedId: Int) {
        val radio: RadioButton = findViewById(checkedId)
        Toast.makeText(
            applicationContext, " On checked change :" +
                    " ${radio.text}",
            Toast.LENGTH_SHORT
        ).show()
        compatValue = radio.text.toString()
    }

    private fun updateProfile() {
        detail.clear()
        detail.put("img_url", profile_pic!!)
        detail.put("name", binding.inputName.text.toString())
        detail.put("address", binding.address.text.toString())
        detail.put("dob", binding.dob.text.toString())
        detail.put("gender", compatValue!!)
        detail.put("ethnicity", "White")
        detail.put("summary", binding.summary.text.toString())
        Log.i("Testing", detail.toString())
        viewModel.updatePersonalDetail(header, detail)
    }
}
