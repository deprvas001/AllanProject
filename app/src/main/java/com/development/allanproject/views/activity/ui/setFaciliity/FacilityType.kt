package com.development.allanproject.views.activity.ui.setFaciliity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.CertificateAdapter
import com.development.allanproject.adapter.FacilityTypeAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityFacilityTypeBinding
import com.development.allanproject.model.CertificateClass
import com.development.allanproject.model.commonapi.CertificateType
import com.development.allanproject.model.commonapi.CommonApiData
import com.development.allanproject.model.commonapi.FacilityPreference
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.views.activity.AddExperience
import com.development.allanproject.views.activity.SetPreference
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.signup.SignUpViewModel
import com.development.allanproject.views.activity.ui.speciality.AddSpecialityViewModel
import com.development.allanproject.views.activity.ui.speciality.AddSpecialityViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class FacilityType : AppCompatActivity(), AuthListener, KodeinAware,
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    lateinit var binding: ActivityFacilityTypeBinding

    var certificateValue: String? = null
    var certificateId: Int? = null
    private lateinit var viewModel: AddSpecialityViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<Any> = ArrayList<Any>()
    var adapter: FacilityTypeAdapter? = null
    var preferenceList: ArrayList<FacilityPreference> = ArrayList<FacilityPreference>()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var speciality: String? = null
    var dataList: ArrayList<CertificateClass> = ArrayList<CertificateClass>()
    override val kodein by kodein()
    private val factory: AddSpecialityViewModelFactory by instance()
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_facility_type)

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
             if(FacilityTypeAdapter.selectItem.size>0){
                 for (x in FacilityTypeAdapter.selectItem){
                     hashMap.add(x)
                 }
                 viewModel.addDocumentDetail(header,hashMap,8)
             }else{
                 toast("Select Preference")
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
                        preferenceList = SignUp.commonApiData.data.facility_preference as ArrayList<FacilityPreference>
                        adapter = FacilityTypeAdapter(this, preferenceList)
                        mLayoutManager = LinearLayoutManager(this)
                        binding.recyclerView.setLayoutManager(mLayoutManager)
                        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
                        binding.recyclerView.setAdapter(adapter)
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
                 startActivity(Intent(this, SetPreference::class.java))
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
}
