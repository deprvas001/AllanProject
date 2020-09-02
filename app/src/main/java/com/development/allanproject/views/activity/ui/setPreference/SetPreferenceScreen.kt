package com.development.allanproject.views.activity.ui.setPreference

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.FacilityTypeAdapter
import com.development.allanproject.adapter.SetPreferenceAdapter
import com.development.allanproject.adapter.ShiftTypeAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityFacilityTypeBinding
import com.development.allanproject.databinding.ActivitySetPreferenceScreenBinding
import com.development.allanproject.model.CertificateClass
import com.development.allanproject.model.commonapi.CommonApiData
import com.development.allanproject.model.commonapi.FacilityPreference
import com.development.allanproject.model.commonapi.ShiftPreference
import com.development.allanproject.model.commonapi.ShiftType
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.views.activity.SetFacilityType
import com.development.allanproject.views.activity.SetPreference
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.signup.SignUpViewModel
import com.development.allanproject.views.activity.ui.speciality.AddSpecialityViewModel
import com.development.allanproject.views.activity.ui.speciality.AddSpecialityViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SetPreferenceScreen : AppCompatActivity(), AuthListener, KodeinAware,
RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    lateinit var binding: ActivitySetPreferenceScreenBinding
    var certificateValue: String? = null
    var certificateId: Int? = null
    private lateinit var viewModel: AddSpecialityViewModel
    var  listShiftPreference:ArrayList<Int> = ArrayList<Int>()
    var  listShiftType:ArrayList<Int> = ArrayList<Int>()
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<Any> = ArrayList<Any>()
    var adapter: SetPreferenceAdapter? = null
    var adapterShift: ShiftTypeAdapter? = null
    var shiftPreferenceList: ArrayList<ShiftPreference> = ArrayList<ShiftPreference>()
    var shiftTypeList: ArrayList<ShiftType> = ArrayList<ShiftType>()

    var mLayoutManager: RecyclerView.LayoutManager? = null
    var speciality: String? = null
    var dataList: ArrayList<CertificateClass> = ArrayList<CertificateClass>()
    override val kodein by kodein()
    private val factory: AddSpecialityViewModelFactory by instance()
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_preference_screen)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_preference_screen)

        viewModel = ViewModelProvider(this, factory).get(AddSpecialityViewModel::class.java)
        viewModel.authListener = this
        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        getCommonApiData()

        header.set("user_id", user_id!!)
        header.set(
            "Authorization", token!!
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        binding.btnNext.setOnClickListener {
            listShiftPreference.clear()
            listShiftType.clear()
            if(SetPreferenceAdapter.selectItem.size<=0 || ShiftTypeAdapter.selectShiftType.size<=0){
                toast("Please select Shift Preference And Shift Type")
            }else{
                for(preference in SetPreferenceAdapter.selectItem){
                    listShiftPreference.add(preference)
                }

                for(shift in ShiftTypeAdapter.selectShiftType){
                    listShiftType.add(shift)
                }
                viewModel.addPreference(header,listShiftPreference,listShiftType,9)
            }
        }

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
                startActivity(Intent(this, SetPreferenceScreen::class.java))
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

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
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
                        shiftPreferenceList = SignUp.commonApiData.data.shift_preference as ArrayList<ShiftPreference>
                        shiftTypeList = SignUp.commonApiData.data.shift_type as ArrayList<ShiftType>

                        adapter = SetPreferenceAdapter(this, shiftPreferenceList)
                        mLayoutManager = LinearLayoutManager(this)
                        binding.recyclerView.setLayoutManager(mLayoutManager)
                        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
                        binding.recyclerView.setAdapter(adapter)

                        adapterShift = ShiftTypeAdapter(this, shiftTypeList)
                        mLayoutManager = LinearLayoutManager(this)
                        binding.recycleViewType.setLayoutManager(mLayoutManager)
                        binding.recycleViewType.setItemAnimator(DefaultItemAnimator())
                        binding.recycleViewType.setAdapter(adapterShift)

                        /*  val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                              this,
                              android.R.layout.simple_spinner_item,
                              SignUp.commonApiData.data.facility_preference
                          )
                          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
  */
                        //   binding.spinnerCertificate.setAdapter(adapter)
                    }
                }
                //  startActivity(Intent(this@SignUp, PersonalDetail::class.java))
            }
        )
    }
}
