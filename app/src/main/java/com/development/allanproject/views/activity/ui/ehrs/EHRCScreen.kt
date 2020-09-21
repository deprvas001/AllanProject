package com.development.allanproject.views.activity.ui.ehrs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.EHRSAdapter
import com.development.allanproject.adapter.EditSpecialityAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityEHRMScreenBinding
import com.development.allanproject.model.commonapi.CommonApiData
import com.development.allanproject.model.commonapi.EHRSDataType
import com.development.allanproject.model.commonapi.Speciality
import com.development.allanproject.model.ehrs.EHRSData
import com.development.allanproject.model.ehrs.EHRSList
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.model.speciality.Data
import com.development.allanproject.model.speciality.GetSpeciality
import com.development.allanproject.util.*
import com.development.allanproject.util.ehrsListener.EHRSAuthListener
import com.development.allanproject.util.specialityListener.SpecialityAuthListener
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.signup.SignUpViewModel
import com.development.allanproject.views.activity.ui.speciality.AddSpecialityViewModel
import com.development.allanproject.views.activity.ui.speciality.AddSpecialityViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class EHRCScreen : AppCompatActivity(), EHRSAuthListener, AuthListener, KodeinAware,
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener,
    EHRSAdapter.EHRSCallBack {
    private lateinit var binding: ActivityEHRMScreenBinding

    private lateinit var viewModel: EHRSViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
    var adapter: EHRSAdapter? = null
    var ehrsList: ArrayList<EHRSDataType> = ArrayList()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var ehrsId: Int? = null
    var dataList: ArrayList<EHRSData> = ArrayList()
    override val kodein by kodein()
    var dataItem: EHRSData? = null
    var isAdd: Boolean = false

    private val factory: EHRSViewModelFactory by instance()
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_e_h_r_m_screen)

        viewModel = ViewModelProvider(this, factory).get(EHRSViewModel::class.java)
        viewModel.authListener = this
        viewModel.ehrsAuthListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        adapter = EHRSAdapter(this, dataList, this)
        mLayoutManager = LinearLayoutManager(this)
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

        getCommonApiData()

        binding.facilitySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                var speciality = parent.selectedItem as EHRSDataType
                ehrsId = speciality.id
                toast(speciality.name)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        binding.add.setOnClickListener {
            isAdd = true

            var licenseRequest: HashMap<String, Any> = HashMap<String, Any>()
            licenseRequest.put("health_doc_id", ehrsId!!)
            viewModel.addSpeciality(header, licenseRequest)

        }
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: EHRSList) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code == 200) {
            dataList.clear()
            if (response.data.size > 0) {
                for (data in response.data) {
                    for (ehrsData in ehrsList) {
                        if (data.health_doc_id == ehrsData.id) {
                            data.name = ehrsData.name
                            dataList.add(data)
                        }
                    }
                }
                adapter!!.notifyDataSetChanged()
            }
        } else {
            toast(response.status.toString())
        }
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if (response.success && response.code.equals("200") && response.status.equals("ok")) {
            if (isAdd) {
                /*  startActivity(Intent(this, EditSpeciality::class.java))
                  finish()*/
                getCommonApiData()
            } else {
                toast("Deleted Successfully")
                if (dataList.contains(dataItem)) {
                    dataList.removeAt(dataList.indexOf(dataItem))
                    adapter!!.notifyDataSetChanged()
                }
            }

        } else {
            toast(response.msg)
        }

    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    private fun getCommonApiData() {
        //  binding.experience.text.clear()
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
                        ehrsList.clear()
                        dataList.clear()
                        SignUp.commonApiData = apiResponse
                        ehrsList =
                            SignUp.commonApiData.data.electronic_health_record
                        val adapter: ArrayAdapter<EHRSDataType> = ArrayAdapter<EHRSDataType>(
                            this,
                            android.R.layout.simple_spinner_item,
                            SignUp.commonApiData.data.electronic_health_record
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        isAdd = false
                        binding.facilitySpinner.setAdapter(adapter)
                        viewModel.getEHRS(header, "21")
                    }
                }
                //  startActivity(Intent(this@SignUp, PersonalDetail::class.java))
            }
        )
    }

    override fun listenerMethod(data: EHRSData?) {
        if (data != null) {
            isAdd = false
            dataItem = data
            viewModel.deleteWorkExperience(header, data.id)
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        TODO("Not yet implemented")
    }
}
