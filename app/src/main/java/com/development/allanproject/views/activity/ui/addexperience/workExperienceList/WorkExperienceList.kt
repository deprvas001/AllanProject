package com.development.allanproject.views.activity.ui.addexperience.workExperienceList

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.EditWorkExpAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityWorkExperienceListBinding
import com.development.allanproject.model.commonapi.CommonApiData
import com.development.allanproject.model.commonapi.FacilityType
import com.development.allanproject.model.experience.Data
import com.development.allanproject.model.experience.GetExperience
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.workExperienceListener.AuthWorkExperienceListener
import com.development.allanproject.views.activity.ui.addexperience.viewmodel.AddExperienceViewModel
import com.development.allanproject.views.activity.ui.addexperience.viewmodel.AddExperienceViewModelFactory
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.signup.SignUpViewModel
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class WorkExperienceList : AppCompatActivity(),
    AuthListener, AuthWorkExperienceListener, KodeinAware ,
    EditWorkExpAdapter.ExperienceCallBack{
    private lateinit var binding: ActivityWorkExperienceListBinding
    private lateinit var viewModel: AddExperienceViewModel
    private lateinit var adapter: EditWorkExpAdapter
    lateinit var sessionManager: SessionManager
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var facilityType: ArrayList<FacilityType> = ArrayList()
    var dataItem: Data?=null

    var header: HashMap<String, String> = HashMap()
    var dataList: ArrayList<Data> = ArrayList()
    override val kodein by kodein()

    private val factory: AddExperienceViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_experience_list)

        viewModel = ViewModelProvider(this, factory).get(AddExperienceViewModel::class.java)
        viewModel.experienceAuthListener = this
        viewModel.authListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id", user_id!!)
        header.set(
            "Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjAwMTcwNjI2fQ.LTgeY18K4hAt4J7yKwUBYOJoJ6Azny2K1cM2N8JUdTI"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

       getCommonApiData()

    }

    override fun onStarted() {
        progress_bar.show()
        toast("Started")
    }

    override fun onSuccess(response: GetExperience) {
        progress_bar.hide()
        if (response.success && response.code == 200) {
            if (response.status.equals("ok")) {
                if(response.data.size>0){
                    dataList.clear()
                    for(data in response.data){
                        dataList.add(data)
                    }
                    adapter.notifyDataSetChanged()
                }
            }else{
                toast(response.status)
            }
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    private fun setAdapter(facilityList: ArrayList<FacilityType>) {
        adapter = EditWorkExpAdapter(this, dataList, facilityList,this)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        viewModel.getWorkExperience(header)

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
                        if(SignUp.commonApiData.data.facility_type.size>0){
                            facilityType.clear()
                            facilityType = SignUp.commonApiData.data.facility_type as ArrayList<FacilityType>
                          /*  for( faclility in SignUp.commonApiData.data.facility_type){
                                facilityType.add(faclility)
                            }*/
                            setAdapter(facilityType)
                        }else{
                            toast("Something went wrong. Try Later")
                            finish()
                        }

                    }
                }
            })
    }

    override fun listenerMethod(data: Data?) {
        if(data !=null){
            dataItem = data
            viewModel.deleteWorkExperience(header,data.id)
        }
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if(response.success && response.code.equals("200") && response.status.equals("ok")){
            toast("Experience Deleted Successfully")
           if(dataList.contains(dataItem)){
              dataList.removeAt(dataList.indexOf(dataItem))
               adapter.notifyDataSetChanged()
           }
        }else{
            toast(response.msg)
        }
    }
}
