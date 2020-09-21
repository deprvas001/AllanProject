package com.development.allanproject.views.activity.ui.addcertifictate.certificateList

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.R
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityUpadateCertificateBinding
import com.development.allanproject.model.certificate.CertificateData
import com.development.allanproject.model.commonapi.CertificateType
import com.development.allanproject.model.commonapi.CommonApiData
import com.development.allanproject.model.commonapi.License
import com.development.allanproject.model.license.Data
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.views.activity.ui.addcertifictate.CertificateViewModel
import com.development.allanproject.views.activity.ui.addcertifictate.CertificateViewModelFactory
import com.development.allanproject.views.activity.ui.addlicenese.AddLicenseViewModel
import com.development.allanproject.views.activity.ui.addlicenese.AddLicenseViewModelFactory
import com.development.allanproject.views.activity.ui.addlicenese.licenseList.ShowAllLicense
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

class UpadateCertificate : AppCompatActivity(), AuthListener, KodeinAware, View.OnClickListener {
    private lateinit var binding: ActivityUpadateCertificateBinding
    var certificateList: ArrayList<CertificateType> = ArrayList()

    private lateinit var viewModel: CertificateViewModel
    var image_array: ArrayList<String> = ArrayList<String>()
    var licenseList: ArrayList<License> = ArrayList()
    var certificate_id: Int? = 0
    var profile_pic: String? = null
    var id: Int? = null
    var compat: String? = null
    var cert_name: String? = null
    var type: Int? = 1
    private lateinit var certificateData: CertificateData
    lateinit var sessionManager: SessionManager
    var header: HashMap<String, String> = HashMap<String, String>()
    override val kodein by kodein()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
    private val factory: CertificateViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upadate_certificate)
        viewModel = ViewModelProvider(this, factory).get(CertificateViewModel::class.java)
        viewModel.authListener = this

        image_array.add("https://homepages.cae.wisc.edu/~ece533/images/airplane.png")
        getHeader()

        binding.certificateSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                if (certificateList.size > 0) {
                    var value = (parent.selectedItem as CertificateType)
                    toast(value.name)
                    certificate_id = value.id
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        binding.issueDate.setOnClickListener(this)
        binding.expiryDate.setOnClickListener(this)
        binding.frontImage.setOnClickListener(this)
        binding.backImage.setOnClickListener(this)
        binding.btnAdd.setOnClickListener(this)
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code.equals("200")) {
            startActivity(Intent(this, ShowCertificateList::class.java))
            finish()
        } else {
            toast(response.msg)
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
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
                        certificateList =
                            SignUp.commonApiData.data.certificates as ArrayList<CertificateType>
                        val adapter: ArrayAdapter<CertificateType> = ArrayAdapter<CertificateType>(
                            this,
                            android.R.layout.simple_spinner_item,
                            SignUp.commonApiData.data.certificates
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.certificateSpinner.setAdapter(adapter)

                        if (intent.extras != null) {
                            for (data in certificateList) {
                                if (data.id == certificateData.certificate_id) {
                                    cert_name = data.name
                                }
                            }
                            var certificate_data = CertificateType(certificateData.certificate_id!!.toInt(),cert_name!!)
                            val spinnerPosition = adapter.getPosition(certificate_data!!)
                            binding.certificateSpinner.setSelection(spinnerPosition)
                        }
                    }
                }
            }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            if (type == 1) {
                binding.frontImage.setImageURI(fileUri)
            } else {
                binding.backImage.setImageURI(fileUri)
            }

            profile_pic =
                "https://i.picsum.photos/id/658/200/300.jpg?hmac=K1TI0jSVU6uQZCZkkCMzBiau45UABMHNIqoaB9icB_0"
            toast(profile_pic.toString())
            //You can get File object from intent
            val file: File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            val filePath: String = ImagePicker.getFilePath(data)!!
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            toast(ImagePicker.getError(data))
        } else {
            toast("Task Cancelled")
        }
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.front_image) {
            type = 1
            imagePick()
        } else if (view?.id == R.id.back_image) {
            type = 2
            imagePick()
        } else if (view?.id == R.id.back) {
            finish()
        } else if (view?.id == R.id.issue_date) {
            getDate(1)
        } else if (view?.id == R.id.expiry_date) {
            getDate(2)
        } else if (view?.id == R.id.btn_add) {
            if (binding.issueFrom.text.isNullOrEmpty()) {
                toast("Please add issued from")
                return
            } else if (binding.issueDate.text.isNullOrEmpty()) {
                toast("Please add issued date")
                return
            } else if (binding.expiryDate.text.isNullOrEmpty()) {
                toast("Please add expiry date")
                return
            } else {
                var hashMap: HashMap<String, Any> = HashMap()

                hashMap.put("certificate_id", certificate_id!!)
                hashMap.put("issued_from", binding.issueFrom.text.toString())
                hashMap.put("issue_date", binding.issueDate.text.toString())
                hashMap.put("end_date", binding.expiryDate.text.toString())
                hashMap.put("img_urls", image_array)
                if (intent.extras != null) {
                    hashMap.put("id", certificateData.id!!)
                    viewModel.addCertificate(header, hashMap, "update")
                } else {
                    viewModel.addCertificate(header, hashMap, "create")
                }


            }
        }
    }

    private fun getHeader() {
        if (intent.extras != null) {
            certificateData = intent.getParcelableExtra<CertificateData>("select_certificate")
            binding.issueFrom.setText(certificateData.issued_from)
            binding.issueDate.setText(certificateData.issue_date)
            binding.expiryDate.setText(certificateData.end_date)
            certificate_id = certificateData.certificate_id
        }

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
        getCommonApiData()
    }

    fun getDate(editType: Int) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                if (editType == 1) {
                    binding.issueDate.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth)
                } else {
                    binding.expiryDate.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth)
                }

            }, year, month, day
        )
        //   mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.getDatePicker()

        datePickerDialog.show()
    }

    private fun imagePick() {
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }
}
