package com.development.allanproject.views.activity.ui.addlicenese.licenseList

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.R
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityEditLicenseScreenBinding
import com.development.allanproject.model.commonapi.CommonApiData
import com.development.allanproject.model.commonapi.License
import com.development.allanproject.model.license.Data
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.views.activity.ui.addlicenese.AddLicenseViewModel
import com.development.allanproject.views.activity.ui.addlicenese.AddLicenseViewModelFactory
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.signup.SignUpViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class EditLicenseScreen : AppCompatActivity(), AuthListener, KodeinAware, View.OnClickListener {
 private lateinit var  binding:ActivityEditLicenseScreenBinding
    private lateinit var viewModel: AddLicenseViewModel
    var array: ArrayList<String> = ArrayList<String>()
    var licenseList: ArrayList<License> = ArrayList()
    var stateList: ArrayList<String> = ArrayList()
    var licenseValue:String?=null
    var licenseId:String?=null
    var stateValue:String?=null
    var compatValue:String?=null
    var profile_pic:String?=null
    var state_value:String?=null
    var license_id:String?=null
    var license_value:String?=null
    var id:Int?=null
    var compat:String?=null
    var licenseRequest: HashMap<String,Any> = HashMap<String,Any>()
    lateinit var sessionManager: SessionManager
    var header:HashMap<String,String> = HashMap<String,String>()
    var imgUrl: List<String> = listOf<String>("https://dab1nmslvvntp.cloudfront.net/wp-content/uploads/2012/11/params-300x66.png",
        "https://dab1nmslvvntp.cloudfront.net/wp-content/uploads/2012/11/params-300x66.png")
    override val kodein by kodein()

    var hashMap:ArrayList<HashMap<String,Any>> = ArrayList<HashMap<String,Any>>()

    private val factory: AddLicenseViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_license_screen)
        viewModel = ViewModelProvider(this, factory).get(AddLicenseViewModel::class.java)

        getIntentInformation()


        binding.btnNext.setOnClickListener {

            licenseRequest.clear()
            licenseRequest.set("id", id!!)
            licenseRequest.set("licence_id",licenseId!!)
            licenseRequest.set("licence_no",binding.inputLicense.text.toString())
            licenseRequest.set("state",stateValue!!)
            licenseRequest.set("licence_compact",compatValue!!)
            licenseRequest.set("issue_date",binding.inputIssueDate.text.toString())
            licenseRequest.set("expiration_date",binding.expireDate.text.toString())
            licenseRequest.set("img_url",array!!)
            hashMap.add(licenseRequest)
            viewModel.updateLicense(header,licenseRequest,"update")

        }

        binding.compatGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                Toast.makeText(applicationContext," On checked change :"+
                        " ${radio.text}",
                    Toast.LENGTH_SHORT).show()
                compatValue = radio.text.toString()
            })



        binding.licenseSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                if(licenseList.size>0){
                    licenseValue = licenseList[position].name
                    licenseId = licenseList[position].id.toString()
                    toast(licenseList[position].name)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        binding.state.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                if(stateList.size>0){
                    stateValue = stateList[position]
                    toast(stateValue!!)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        binding.compatGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                Toast.makeText(applicationContext," On checked change :"+
                        " ${radio.text}",
                    Toast.LENGTH_SHORT).show()
                compatValue = radio.text.toString()
            })

        binding.inputIssueDate.setOnClickListener {
            getDate(1)
        }
        binding.expireDate.setOnClickListener {
            getDate(2)
        }

        binding.uploadImage.setOnClickListener(this)
        binding.back.setOnClickListener(this)


    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if(response.success && response.status.equals("ok") && response.code.equals("200")){
            startActivity(Intent(this, ShowAllLicense::class.java))
            finish()
        }else{
            toast(response.msg)
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }


    fun getDate(editType:Int){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                if(editType == 1){
                    binding.inputIssueDate.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth)
                }else{
                    binding.expireDate.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth)
                }

            }, year, month, day
        )
        //   mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.getDatePicker()

        datePickerDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            binding.uploadImage.setImageURI(fileUri)
            profile_pic = "https://i.picsum.photos/id/658/200/300.jpg?hmac=K1TI0jSVU6uQZCZkkCMzBiau45UABMHNIqoaB9icB_0"
            toast(profile_pic.toString())
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

    override fun onClick(view: View?) {
        if(view?.id == R.id.upload_image){
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }else if(view?.id == R.id.back){
            finish()
        }
    }

    private fun getCommonApiData() {
        binding.progressBar.setVisibility(View.VISIBLE)
        var viewModel: SignUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        viewModel.commonData().observe(
            this,
            Observer { apiResponse: CommonApiData ->
                binding.progressBar.setVisibility(View.GONE)
                //  Toast.makeText(this, apiResponse.status, Toast.LENGTH_SHORT).show()
                if (apiResponse.code == 200) {
                    if (apiResponse.success == true) {
                        SignUp.commonApiData = apiResponse
                        licenseList = SignUp.commonApiData.data.license as ArrayList<License>
                        stateList = SignUp.commonApiData.data.states as ArrayList<String>
                        val adapter: ArrayAdapter<License> = ArrayAdapter<License>(
                            this,
                            android.R.layout.simple_spinner_item,
                            SignUp.commonApiData.data.license
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.licenseSpinner.setAdapter(adapter)

                        if(!license_id.isNullOrEmpty() ){
                            for(license in licenseList){
                                if (license.id == license_id!!.toInt())
                                    license_value = license.name
                            }
                            var license = License(license_id!!.toInt(),license_value!!)
                            val spinnerPosition = adapter.getPosition(license!!)
                            binding.licenseSpinner.setSelection(spinnerPosition)
                        }

                        val stateAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            this,
                            android.R.layout.simple_spinner_item,
                            SignUp.commonApiData.data.states
                        )
                        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.state.setAdapter(stateAdapter)

                        if(!state_value.isNullOrEmpty()){
                            val spinnerPosition = stateAdapter.getPosition(state_value!!)
                            binding.state.setSelection(spinnerPosition)
                        }

                    }
                }
                //  startActivity(Intent(this@SignUp, PersonalDetail::class.java))
            }
        )
    }
    private fun getIntentInformation(){
        val data = intent.getParcelableExtra<Data>("select_license")
        id = data.id
        license_id =  data.licence_id.toString()
      //  license_value = data.licence_no
        state_value = data.state
        compat = data.licence_compact
        compatValue = compat

        binding.inputLicense.setText(data.licence_no)

        if(!data.issue_date.isNullOrEmpty()){
            binding.inputIssueDate.setText(data.issue_date)
        }
        if(!data.expiration_date.isNullOrEmpty()){
            binding.expireDate.setText(data.expiration_date)
        }

        if(compat.equals("Yes")){
            binding.yes.isChecked = true
        }else{
            binding.no.isChecked = true
        }

        array.add("https://homepages.cae.wisc.edu/~ece533/images/airplane.png")
        viewModel.authListener = this
        binding.viewmodel = viewModel

        getHeader()
    }
    private fun getHeader(){
        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id", "22")
        header.set(
            "Authorization","eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjAwMzI2Nzg2fQ.vaiRJiTisqb89tiQJqg3t0rubigehfUnXIPtOife52k"
        )
        header.set("device_type_id","1")

        header.set("v_code","7")
        getCommonApiData()
    }
}
