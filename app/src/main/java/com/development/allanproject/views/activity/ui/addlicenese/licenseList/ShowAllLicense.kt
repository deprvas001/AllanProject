package com.development.allanproject.views.activity.ui.addlicenese.licenseList

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
import com.development.allanproject.adapter.EducationAdapter
import com.development.allanproject.adapter.LicenseListAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityShowAllLicenseBinding
import com.development.allanproject.model.commonapi.FacilityType
import com.development.allanproject.model.license.Data
import com.development.allanproject.model.education.EducationListApiResonse
import com.development.allanproject.model.license.ShowLicensesList
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.licenseListener.LicenseAuthListener
import com.development.allanproject.views.activity.ui.addlicenese.AddLicenseViewModel
import com.development.allanproject.views.activity.ui.addlicenese.AddLicenseViewModelFactory
import com.development.allanproject.views.activity.ui.education.viewmodel.EducationViewModel
import com.development.allanproject.views.activity.ui.education.viewmodel.EducationViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ShowAllLicense : AppCompatActivity(), AuthListener, LicenseAuthListener, KodeinAware,
    LicenseListAdapter.LicenseCallBack, View.OnClickListener  {
    private lateinit var binding: ActivityShowAllLicenseBinding
    private lateinit var viewModel: AddLicenseViewModel
    private lateinit var adapter: LicenseListAdapter
    lateinit var sessionManager: SessionManager
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var facilityType: ArrayList<FacilityType> = ArrayList()
    var dataItem: Data?=null

    var header: HashMap<String, String> = HashMap()
    var dataList: ArrayList<Data> = ArrayList()
    override val kodein by kodein()

    private val factory: AddLicenseViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_all_license)
        viewModel = ViewModelProvider(this, factory).get(AddLicenseViewModel::class.java)
        viewModel.liceseAuthListener = this
        viewModel.authListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id", "22")
        header.set(
            "Authorization","eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjAwMzI2Nzg2fQ.vaiRJiTisqb89tiQJqg3t0rubigehfUnXIPtOife52k"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        setAdapter()
        binding.addMore.setOnClickListener(this)
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

    override fun onSuccess(response: ShowLicensesList) {
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
        adapter = LicenseListAdapter(this, dataList,this)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        viewModel.getLicenseList(header)

    }

    override fun listenerMethod(data: Data?) {
        if(data !=null){
            dataItem = data
            viewModel.deleteEducation(header,data.id!!)
        }
    }

    override fun onClick(view: View?) {
       if(view!!.id == R.id.add_more){
           startActivity(Intent(this,AddMoreLicense::class.java))
       }
    }
}
