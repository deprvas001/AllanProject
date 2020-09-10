package com.development.allanproject.views.activity.ui.addcertifictate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.CertificateAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityAddCertificate2Binding
import com.development.allanproject.model.CertificateClass
import com.development.allanproject.model.commonapi.CertificateType
import com.development.allanproject.model.commonapi.CommonApiData
import com.development.allanproject.model.commonapi.Speciality
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.views.activity.AddCertificateSecond
import com.development.allanproject.views.activity.AddExperience
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.signup.SignUpViewModel
import com.development.allanproject.views.activity.ui.speciality.AddSpecialityViewModel
import com.development.allanproject.views.activity.ui.speciality.AddSpecialityViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_personal_detail.progress_bar
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class AddCertificate : AppCompatActivity(), AuthListener, KodeinAware,
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
 lateinit var binding: ActivityAddCertificate2Binding
    var certificateValue:String?=null
    var certificateId: Int?=null
    private lateinit var viewModel: AddSpecialityViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<Any> = ArrayList<Any>()
    var adapter: CertificateAdapter? = null
    var certifiateList: ArrayList<CertificateType> = ArrayList<CertificateType>()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var speciality:String?=null
    var dataList: ArrayList<CertificateClass> = ArrayList<CertificateClass>()
    override val kodein by kodein()
    private val factory: AddSpecialityViewModelFactory by instance()
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_certificate2)
        getCommonApiData()

        viewModel = ViewModelProvider(this, factory).get(AddSpecialityViewModel::class.java)
        viewModel.authListener = this
        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        adapter = CertificateAdapter(this, dataList)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        dataList.clear()

        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
            RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerView)

        header.set("user_id", user_id!!)
        header.set(
            "Authorization",token!!
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        binding.add.setOnClickListener {
            if(!certificateValue.isNullOrEmpty()){
                val certificate = CertificateClass()
                certificate.name = certificateValue
                certificate.experience = certificateId.toString()
                dataList.add(certificate)

                adapter!!.notifyDataSetChanged()
            }else{
                root_layout.snackbar("Add Certificate")
            }

        }

        binding.btnNext.setOnClickListener {

            if(dataList.size>0){
                for (x in dataList){
                    hashMap.add(x.experience.toInt())
                }
              //  Log.i("Testing",hashMap.toString())
                viewModel.addDocumentDetail(header, hashMap,5)
            }else{
                root_layout.snackbar("Add Certificate")
            }
            //
        }

        binding.spinnerCertificate.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                certificateValue = certifiateList[position].name
                certificateId = certifiateList[position].id
                toast(certifiateList[position].name)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
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
                       certifiateList = SignUp.commonApiData.data.certificates as ArrayList<CertificateType>
                        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                            this,
                            android.R.layout.simple_spinner_item,
                            SignUp.commonApiData.data.certificates
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerCertificate.setAdapter(adapter)
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
                startActivity(Intent(this, com.development.allanproject.views.activity.ui.addexperience.AddExperience::class.java))
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

    override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        direction: Int,
        position: Int
    ) {
        if (viewHolder is CertificateAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            val name: String = dataList.get(viewHolder.adapterPosition).getName()

//            // backup of removed item for undo purpose
//            val deletedItem: Item = dataList.get(viewHolder.adapterPosition)
            val deletedIndex = viewHolder.adapterPosition

            // remove the item from recycler view
            adapter!!.removeItem(viewHolder.adapterPosition)

        }
    }
}
