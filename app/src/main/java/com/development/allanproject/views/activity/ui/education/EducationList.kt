package com.development.allanproject.views.activity.ui.education

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.EditWorkExpAdapter
import com.development.allanproject.adapter.EducationAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityEducationListBinding
import com.development.allanproject.model.commonapi.FacilityType
import com.development.allanproject.model.education.Data
import com.development.allanproject.model.education.EducationListApiResonse
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.educationListener.EducationAuthListener
import com.development.allanproject.views.activity.ui.addexperience.viewmodel.AddExperienceViewModel
import com.development.allanproject.views.activity.ui.addexperience.viewmodel.AddExperienceViewModelFactory
import com.development.allanproject.views.activity.ui.education.viewmodel.EducationViewModel
import com.development.allanproject.views.activity.ui.education.viewmodel.EducationViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class EducationList : AppCompatActivity(),AuthListener, EducationAuthListener, KodeinAware,
    EducationAdapter.EducationCallBack, View.OnClickListener {
    private lateinit var binding: ActivityEducationListBinding
    private lateinit var viewModel: EducationViewModel
    private lateinit var adapter: EducationAdapter
    lateinit var sessionManager: SessionManager
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var facilityType: ArrayList<FacilityType> = ArrayList()
    var dataItem: Data?=null

    var header: HashMap<String, String> = HashMap()
    var dataList: ArrayList<Data> = ArrayList()
    override val kodein by kodein()

    private val factory: EducationViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_education_list)
        viewModel = ViewModelProvider(this, factory).get(EducationViewModel::class.java)
        viewModel.educationAuthListener = this
        viewModel.authListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id", user_id!!)
        header.set(
            "Authorization", token!!
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        binding.back.setOnClickListener(this)
        binding.addMore.setOnClickListener(this)

        setAdapter()

    }

    override fun onStarted() {
        progress_bar.show()
        toast("Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if(response.success && response.code.equals("200") && response.status.equals("ok")){
            toast("Deleted Successfully")
            if(dataList.contains(dataItem)){
                dataList.removeAt(dataList.indexOf(dataItem))
                adapter.notifyDataSetChanged()
            }
        }else{
            toast(response.msg)
        }

    }

    override fun onSuccess(response: EducationListApiResonse) {
        progress_bar.hide()
        if (response.success && response.code == 200) {
            if (response.status.equals("ok")) {
                if(response.data.size>0){
                    dataList.clear()
                    for(data in response.data){
                        dataList.add(data)
                    }
                    adapter.notifyDataSetChanged()
                }else{
                    toast("Data Not Found.")
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

    private fun setAdapter() {
        adapter = EducationAdapter(this, dataList,this)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        viewModel.getEducationList(header)

    }

    override fun listenerMethod(data: Data?) {
        if(data !=null){
            dataItem = data

           viewModel.deleteEducation(header,data.id!!)
        }
    }

    override fun onClick(view: View?) {
        if(view!!.id == R.id.back){
            finish()
        }else if(view!!.id == R.id.add_more){
            startActivity(Intent(this, AddEducationScreen::class.java))
        }
    }

}
