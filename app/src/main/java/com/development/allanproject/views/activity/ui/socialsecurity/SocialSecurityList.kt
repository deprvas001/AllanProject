package com.development.allanproject.views.activity.ui.socialsecurity

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
import com.development.allanproject.adapter.FormAdapter
import com.development.allanproject.adapter.MyDocumentAdapter
import com.development.allanproject.adapter.SecurityAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivitySocialSecurityListBinding
import com.development.allanproject.model.adddocumentModel.GetDocumentSpinner
import com.development.allanproject.model.adddocumentModel.MyDocumentData
import com.development.allanproject.model.form.FormData
import com.development.allanproject.model.form.GetFormList
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.model.socialsecurity.GetSocialSecurity
import com.development.allanproject.model.socialsecurity.SecurityData
import com.development.allanproject.util.*
import com.development.allanproject.util.documentListener.DocumentListener
import com.development.allanproject.util.formListener.FormListener
import com.development.allanproject.util.socialsecurity.SocialSecurityListener
import com.development.allanproject.views.activity.ui.adddoucment.AddDocumentViewModel
import com.development.allanproject.views.activity.ui.adddoucment.AddDocumentViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SocialSecurityList : AppCompatActivity() , AuthListener, SocialSecurityListener,
    KodeinAware, View.OnClickListener,
    SecurityAdapter.DocumentCallBack{
    private lateinit var binding: ActivitySocialSecurityListBinding
    var header: HashMap<String, String> = HashMap<String, String>()
    private lateinit var viewModel: SocailSecurityViewModel
    var dataList: ArrayList<SecurityData> = ArrayList()

    override val kodein by kodein()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var dataItem: SecurityData?=null

    private lateinit var adapter: SecurityAdapter

    var uploadFront: String?=null
    var uploadBack: String?= null
    var doc_type:Int?=null
    var selected_doc_type:String?=null
    private val factory: SocialSecurityFactory by instance()
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_social_security_list)
        viewModel = ViewModelProvider(this, factory).get(SocailSecurityViewModel::class.java)
        viewModel.socialAuthListener = this
        viewModel.authListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        dataList.clear()

        header.set("user_id", user_id!!)
        header.set(
            "Authorization", token!!
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        setAdapter()
        binding.addMore.setOnClickListener(this)

    }

    private fun setAdapter() {
        adapter = SecurityAdapter(this, dataList,this)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        viewModel.getDocumentList(header)

    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: GetSocialSecurity) {
        progress_bar.hide()
        if(response.success && response.status.equals("ok") && response.code.toInt() == 200){
            if(response.data.size>0){
                dataList.clear()
                for( data in response.data){
                    dataList.add(data)
                    adapter.notifyDataSetChanged()
                }
            }
        }else{
            toast("Try Later")
        }
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if(response.success && response.status.equals("ok") && response.code.toInt() == 200){
            toast("Delete Successfully")
            if(dataList.contains(dataItem)){
                dataList.removeAt(dataList.indexOf(dataItem))
                adapter.notifyDataSetChanged()
            }
        }else{
            toast(response.msg)
        }
    }



    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    override fun listenerMethod(data: SecurityData?) {
        if(data !=null){
            dataItem = data
            var hashMap: HashMap<String,Any> = HashMap()
            hashMap.put("id", data.id!!)
            viewModel.addDocument(header,hashMap,"delete")
        }
    }

    override fun onClick(view: View?) {
       if(view!!.id == R.id.add_more){
           startActivity(Intent(this, SocialSecurityScreen::class.java))
       }
    }
}
