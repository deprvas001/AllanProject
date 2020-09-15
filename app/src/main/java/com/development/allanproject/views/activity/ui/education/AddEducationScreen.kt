package com.development.allanproject.views.activity.ui.education

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.R
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityAddEducationScreenBinding
import com.development.allanproject.model.commonapi.CommonApiData
import com.development.allanproject.model.education.AddDetails
import com.development.allanproject.model.education.Data
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.views.activity.ui.education.viewmodel.EducationViewModel
import com.development.allanproject.views.activity.ui.education.viewmodel.EducationViewModelFactory
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.signup.SignUpViewModel
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AddEducationScreen : AppCompatActivity() , AuthListener,  KodeinAware,
     View.OnClickListener{
    private lateinit var binding: ActivityAddEducationScreenBinding
    var degreeList: ArrayList<String> = ArrayList()
    override val kodein by kodein()
    private lateinit var viewModel: EducationViewModel
    lateinit var sessionManager: SessionManager
    var header: HashMap<String, String> = HashMap()
    var degree:String?=""
    var action:String?="create"
    private val factory: EducationViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_education_screen)
        viewModel = ViewModelProvider(this, factory).get(EducationViewModel::class.java)
        viewModel.authListener = this
        binding.addDetail = AddDetails(null,binding.institution.text.toString(),
        binding.address.text.toString(), degree!!,binding.startDate.text.toString(),binding.endDate.text.toString())

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id","22")

        header.set(
            "Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE2MDAxOTMxNTJ9.Vt9PZL9kXGUtlIKsfdSckpsFx8QLu9IW2Z1KcMnw_fY"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        getIntentInformation()

        binding.back.setOnClickListener(this)
        binding.btnAdd.setOnClickListener(this)
        binding.endDate.setOnClickListener(this)
        binding.startDate.setOnClickListener(this)

        getCommonApiData()

        binding.spinnerDegree.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
               degree =  parent.selectedItem.toString()
                binding.addDetail!!.degree = degree!!
                toast(degree!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun getCommonApiData() {
        binding.progressBar.setVisibility(View.VISIBLE)
        var viewModel: SignUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        viewModel.commonData().observe(
            this,
            Observer { apiResponse: CommonApiData ->
                binding.progressBar.setVisibility(View.GONE)
                Toast.makeText(this, apiResponse.status, Toast.LENGTH_SHORT).show()
                if (apiResponse.code == 200) {
                    if (apiResponse.success == true) {
                        SignUp.commonApiData = apiResponse
                        degreeList = SignUp.commonApiData.data.degrees as ArrayList<String>
                        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            this,
                            android.R.layout.simple_spinner_item,
                            SignUp.commonApiData.data.degrees
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerDegree.setAdapter(adapter)

                        var spinnerPosition =adapter.getPosition(degree)
                        binding.spinnerDegree.setSelection(spinnerPosition);
                    }
                }
                //  startActivity(Intent(this@SignUp, PersonalDetail::class.java))
            }
        )
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if(response.success && response.code.equals("200") && response.status.equals("ok")){
            toast("Education Successfully Added.")
            startActivity(Intent(this,EducationList::class.java))
            finish()
        }else{
            toast(response.msg)
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    override fun onClick(view: View?) {
        if(view!!.id == R.id.back){
            finish()
        }else if(view!!.id== R.id.btn_add){
           if(binding.addDetail!!.institution.isNullOrEmpty()){
               toast("Please Add Institution")
               return
           }else if(binding.addDetail!!.address.isNullOrEmpty()) {
                toast("Please Add Address")
               return
           }else if(binding.addDetail!!.start_date.isNullOrEmpty()){
               toast("Add start Date")
               return
           }else{
               if(!binding.checkbox.isChecked && binding.addDetail!!.end_date.isNullOrEmpty()){
                   toast("Add End Date")
                   return
               }else{
                   if(binding.checkbox.isChecked){
                       binding.endDate.text.clear()
                   }

                 //  Log.i("Testing", binding.addDetail!!.toString())
                   viewModel.addEducation(header,binding.addDetail!!,action!!)
               }
           }
        }else if(view!!.id == R.id.end_date){
            getDate(2)
        }else if(view!!.id==R.id.start_date){
            getDate(1)
        }
    }


    private fun getDate(i: Int) {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in textbox
                    if(i==1){
                        binding.startDate.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth)
                    }else {
                        binding.endDate.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth)
                    }

                }, year, month, day
            )
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            //   mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
           /* datePickerDialog.getDatePicker()
                .setMaxDate(System.currentTimeMillis() - 568025136000L)*/
            datePickerDialog.show()

    }

    private fun  getIntentInformation(){
        if(intent.extras!=null){
            if(intent.extras!!.containsKey("item")){
               var  listData = intent.extras!!.get("item") as Data

                binding.addDetail!!.institution = listData!!.institution
                binding.addDetail!!.address = listData!!.address
                binding.addDetail!!.start_date = listData!!.start_date
                binding.addDetail!!.id = listData!!.id
                action = "update"
                degree = listData!!.degree
                if(!listData!!.end_date.isNullOrEmpty()){
                    binding.addDetail!!.end_date = listData!!.end_date
                }



                if(listData!!.end_date.isNullOrEmpty()){
                    binding.checkbox.isChecked = true
                }
            }
        }
    }
}
