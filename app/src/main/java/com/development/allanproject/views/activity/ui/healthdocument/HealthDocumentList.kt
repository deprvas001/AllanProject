package com.development.allanproject.views.activity.ui.healthdocument

import android.app.Activity
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
import com.development.allanproject.adapter.HealthDocumentAdpater
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityHealthDocumentListBinding
import com.development.allanproject.model.certificate.CertificateData
import com.development.allanproject.model.certificate.CertificateList
import com.development.allanproject.model.commonapi.FacilityType
import com.development.allanproject.model.healthDocument.HealthDocumentData
import com.development.allanproject.model.healthDocument.HealthDocumentList
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.certificateListener.CertificateListener
import com.development.allanproject.util.healthdocListener.HealthDocAuthListener
import com.development.allanproject.util.hide
import com.development.allanproject.util.show
import com.development.allanproject.util.toast
import com.development.allanproject.views.activity.ui.addcertifictate.CertificateViewModel
import com.development.allanproject.views.activity.ui.addcertifictate.CertificateViewModelFactory
import com.development.allanproject.views.activity.ui.addcertifictate.certificateList.ShowCertificateList
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.File

class HealthDocumentList : AppCompatActivity() ,  HealthDocumentAdpater.HealthDocCallBack,
    HealthDocAuthListener, AuthListener, KodeinAware,
    View.OnClickListener{
    private lateinit var binding: ActivityHealthDocumentListBinding
    private lateinit var viewModel: HealthDocumentViewModel
    private lateinit var adapter: HealthDocumentAdpater
    lateinit var sessionManager: SessionManager
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var facilityType: ArrayList<FacilityType> = ArrayList()
    var profile_pic: String? = null

    var header: HashMap<String, String> = HashMap()
    var dataList: ArrayList<HealthDocumentData> = ArrayList()
    override val kodein by kodein()
    private val factory: HealthDocViewModelFactory by instance()
    var dataItem: HealthDocumentData?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_health_document_list)
        viewModel = ViewModelProvider(this, factory).get(HealthDocumentViewModel::class.java)
        viewModel.healthAuthListener= this
        viewModel.authListener = this

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

        setAdapter()
        binding.back.setOnClickListener(this)
    }

    private fun setAdapter() {
        adapter = HealthDocumentAdpater(this, dataList,this)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        viewModel.getHealthDocumentList(header)

    }

    override fun onStarted() {
        progress_bar.show()
        toast("Started")
    }

    override fun onSuccess(response: SignResponse) {
            progress_bar.hide()
            if (response.success && response.status.equals("ok") && response.code.equals("200")) {
               startActivity(Intent(this, com.development.allanproject.views.activity.ui.healthdocument.HealthDocumentList::class.java))
               finish()
            } else {
                toast(response.msg)
            }
    }


    override fun onFailure(message: String) {
        progress_bar.hide()
        toast(message)
    }

    override fun onSuccess(response: HealthDocumentList) {
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

    override fun onClick(view: View?) {
      if(view!!.id==R.id.back){
          finish()
      }
    }

    override fun listenerMethod(data: HealthDocumentData?) {
        if(data !=null){
            dataItem = data
          imagePick()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data

            profile_pic =
                "https://i.picsum.photos/id/658/200/300.jpg?hmac=K1TI0jSVU6uQZCZkkCMzBiau45UABMHNIqoaB9icB_0"
            toast(profile_pic.toString())
            //You can get File object from intent
            val file: File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            val filePath: String = ImagePicker.getFilePath(data)!!

            var detail: HashMap<String,Any> = HashMap()
            detail.put("health_doc_id", dataItem!!.health_doc_id)
            detail.put("doc_url",profile_pic!!)
            viewModel.postHealthDocument(header,detail)

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            toast(ImagePicker.getError(data))
        } else {
            toast("Task Cancelled")
        }
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
