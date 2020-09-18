package com.development.allanproject.views.activity.ui.addcertifictate.certificateList

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
import com.development.allanproject.adapter.CertificateListAdapter
import com.development.allanproject.adapter.LicenseListAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityShowCertificateListBinding
import com.development.allanproject.model.certificate.CertificateData
import com.development.allanproject.model.certificate.CertificateList
import com.development.allanproject.model.certificate.Data
import com.development.allanproject.model.commonapi.FacilityType
import com.development.allanproject.model.license.ShowLicensesList
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.certificateListener.CertificateListener
import com.development.allanproject.util.licenseListener.LicenseAuthListener
import com.development.allanproject.views.activity.ui.addcertifictate.CertificateViewModel
import com.development.allanproject.views.activity.ui.addcertifictate.CertificateViewModelFactory
import com.development.allanproject.views.activity.ui.addlicenese.AddLicenseViewModel
import com.development.allanproject.views.activity.ui.addlicenese.AddLicenseViewModelFactory
import com.development.allanproject.views.activity.ui.addlicenese.licenseList.AddMoreLicense
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ShowCertificateList : AppCompatActivity(),CertificateListener, AuthListener,  KodeinAware,
    CertificateListAdapter.CertificateCallBack, View.OnClickListener  {
    private  lateinit var binding: ActivityShowCertificateListBinding
    private lateinit var viewModel: CertificateViewModel
    private lateinit var adapter: CertificateListAdapter
    lateinit var sessionManager: SessionManager
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var facilityType: ArrayList<FacilityType> = ArrayList()
    var dataItem: CertificateData?=null

    var header: HashMap<String, String> = HashMap()
    var dataList: ArrayList<CertificateData> = ArrayList()
    override val kodein by kodein()

    private val factory: CertificateViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_certificate_list)

        viewModel = ViewModelProvider(this, factory).get(CertificateViewModel::class.java)
        viewModel.certificateAuthListener= this
        viewModel.authListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id", "22")
        header.set(
            "Authorization","eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjAwNDE3NTYyfQ.WeS9KAVGZQQRMn8Yj1xSVoVsY84DH2SQ8AS3NBemeKI"
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

    override fun onSuccess(response: CertificateList) {
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
        }else{
            toast(response.status)
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        toast(message)
    }

    private fun setAdapter() {
        adapter = CertificateListAdapter(this, dataList,this)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        viewModel.getCertificateList(header)

    }

    override fun listenerMethod(data: CertificateData?) {
        if(data !=null){
            dataItem = data
           viewModel.deleteCertificate(header,data.id!!)
        }
    }

    override fun onClick(view: View?) {
        if(view!!.id == R.id.add_more){
            startActivity(Intent(this, UpadateCertificate::class.java))
        }
    }
}
