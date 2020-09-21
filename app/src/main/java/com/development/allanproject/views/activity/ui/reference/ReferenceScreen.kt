package com.development.allanproject.views.activity.ui.reference

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.ReferenceListAdapter
import com.development.allanproject.adapter.EditSpecialityAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityReferenceScreenBinding
import com.development.allanproject.model.commonapi.CommonApiData
import com.development.allanproject.model.commonapi.EHRSDataType
import com.development.allanproject.model.commonapi.FacilityType
import com.development.allanproject.model.commonapi.Speciality
import com.development.allanproject.model.ehrs.EHRSList
import com.development.allanproject.model.experience.ReferenceListData
import com.development.allanproject.model.reference.ReferenceData
import com.development.allanproject.model.reference.ReferenceList
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.referenceListener.ReferenceAuthListener
import com.development.allanproject.views.activity.ui.ehrs.EHRSViewModel
import com.development.allanproject.views.activity.ui.ehrs.EHRSViewModelFactory
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.signup.SignUpViewModel
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.request_preference_dialog_layout.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.lang.ref.Reference
import javax.xml.parsers.FactoryConfigurationError

class ReferenceScreen : AppCompatActivity(), ReferenceAuthListener, AuthListener, KodeinAware,
    ReferenceListAdapter.ReferenceCallBack {

    private lateinit var binding: ActivityReferenceScreenBinding

    private lateinit var viewModel: ReferenceViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
    var adapter: ReferenceListAdapter? = null
    var ehrsList: ArrayList<EHRSDataType> = ArrayList()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var reference_select: FacilityType? = null
    var dataList: ArrayList<ReferenceData> = ArrayList()
    override val kodein by kodein()
    var dataItem: ReferenceData? = null
    var isAdd: Boolean = false
    var facilityList: ArrayList<FacilityType> = ArrayList()
    private val factory: ReferenceViewModelFactory by instance()
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reference_screen)

        viewModel = ViewModelProvider(this, factory).get(ReferenceViewModel::class.java)
        viewModel.authListener = this
        viewModel.referenceAuthListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        adapter = ReferenceListAdapter(this, dataList, this)
        mLayoutManager = LinearLayoutManagerWrapper(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        dataList.clear()

        header.set("user_id", user_id!!)
        header.set(
            "Authorization",token!!
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        viewModel.getReference(header, "16")

        binding.btnNext.setOnClickListener {
            dataItem = null
            getCommonApiData()
        }
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code.equals("200")) {
            startActivity(Intent(this, ReferenceScreen::class.java))
            finish()
        } else {
            toast(response.msg)
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    override fun onSuccess(response: ReferenceList) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code == 200) {
            dataList.clear()
            if (response.data.size > 0) {
                for (data in response.data) {
                    dataList.add(data)
                }
                adapter!!.notifyDataSetChanged()
            }
        } else {
            toast(response.status.toString())
        }
    }

    override fun listenerMethod(data: ReferenceData?) {
        if (data != null) {
            dataItem = data
            getCommonApiData()
        }
    }

    private fun showCustomDialog() {
        //Inflate the dialog with custom view
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.request_preference_dialog_layout, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        //show dialog
        val mAlertDialog = mBuilder.show()

        mDialogView.facility_spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                reference_select = parent.selectedItem as FacilityType
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }


        val adapter: ArrayAdapter<FacilityType> = ArrayAdapter<FacilityType>(
            this,
            android.R.layout.simple_spinner_item,
            facilityList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        isAdd = false
        mDialogView.facility_spinner.setAdapter(adapter)

        if (dataItem != null) {
            mDialogView.reference_name.setText(dataItem!!.name)
            mDialogView.job_title.setText(dataItem!!.job_title)
            mDialogView.job_type.setText(dataItem!!.job_type)
            if (dataItem!!.phone != null) {
                mDialogView.mobile.setText(dataItem!!.phone)
            }
            if (dataItem!!.email != null) {
                mDialogView.email.setText(dataItem!!.email)
            }
            var fac_type = FacilityType(dataItem!!.facility_id, dataItem!!.facility_name)
            val spinnerPosition = adapter.getPosition(fac_type!!)
            mDialogView.facility_spinner.setSelection(spinnerPosition)

        }


        //login button click of custom layout
        mDialogView.add.setOnClickListener {
            //dismiss dialog

            //get text from EditTexts of custom layout
            val reference_name = mDialogView.reference_name.text.toString()
            val title = mDialogView.job_title.text.toString()
            val type = mDialogView.job_type.text.toString()
            val mobile = mDialogView.mobile.text.toString()
            val email = mDialogView.email.text.toString()

           if(reference_name.isNullOrEmpty()){
                toast("Add Reference Name")
                return@setOnClickListener
            }else if(title.isNullOrEmpty()){
                toast("Add Job title")
                return@setOnClickListener
            }else if(type.isNullOrEmpty()){
                toast("Add Job type")
                return@setOnClickListener
            }else if(mobile.isNullOrEmpty() && email.isNullOrEmpty()){
                toast("Add Email or Phone Number")
                return@setOnClickListener
            }else{
                var hashMap : HashMap<String,Any> = HashMap()
                hashMap.put("facility_id",reference_select!!.id)
                hashMap.put("facility_name", reference_select!!.name)
                hashMap.put("name", mDialogView.reference_name.text.toString())
                hashMap.put("job_title",mDialogView.job_title.text.toString())
                hashMap.put("job_type", mDialogView.job_type.text.toString())
                hashMap.put("std_code", "+91")
                hashMap.put("phone", mDialogView.mobile.text.toString())
                hashMap.put("email", mDialogView.email.text.toString())
                if(dataItem!=null){
                    hashMap.put("id",dataItem!!.id)
                    viewModel.addReference(header, 16,"update",hashMap)
                }else{
                    viewModel.addReference(header, 16,"create",hashMap)
                }

                mAlertDialog.dismiss()
            }
        }
        //cancel button click of custom layout
        mDialogView.close.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }
    }

    private fun getCommonApiData() {
        binding.progressBar.setVisibility(View.VISIBLE)
        var signUpViewModel: SignUpViewModel =
            ViewModelProvider(this).get(SignUpViewModel::class.java)
        signUpViewModel.commonData().observe(
            this,
            Observer { apiResponse: CommonApiData ->
                binding.progressBar.setVisibility(View.GONE)
                Toast.makeText(this, apiResponse.status, Toast.LENGTH_SHORT).show()
                if (apiResponse.code == 200) {
                    if (apiResponse.success == true) {
                        facilityList.clear()
                        dataList.clear()
                        SignUp.commonApiData = apiResponse
                        facilityList =
                            SignUp.commonApiData.data.facility_type as ArrayList<FacilityType>
                        showCustomDialog()
                    }
                }
            }
        )
    }

}
