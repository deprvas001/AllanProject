package com.development.allanproject.views.activity.ui.speciality

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.CertificateAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityAddSpecialityBinding
import com.development.allanproject.model.CertificateClass
import com.development.allanproject.model.commonapi.CommonApiData
import com.development.allanproject.model.commonapi.Speciality
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.views.activity.AddCertificate
import com.development.allanproject.views.activity.AddCertificateSecond
import com.development.allanproject.views.activity.AddExperience
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.signup.SignUpViewModel
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class AddSpeciality : AppCompatActivity(), AuthListener, KodeinAware ,
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener
{
    private lateinit var binding: ActivityAddSpecialityBinding
    private lateinit var viewModel: AddSpecialityViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
    var adapter: CertificateAdapter? = null
    var specialityList: ArrayList<Speciality> = ArrayList<Speciality>()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var speciality:String?=null
    var dataList: ArrayList<CertificateClass> = ArrayList<CertificateClass>()
    override val kodein by kodein()
    private val factory: AddSpecialityViewModelFactory by instance()
    lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_speciality)
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

        getCommonApiData()
        // viewModel.licenseDetail(viewModel)
        binding.btnNext.setOnClickListener {

            if(dataList.size>0){
                for (x in dataList){
                    var licenseRequest: HashMap<String, Any> = HashMap<String, Any>()

                    licenseRequest.set("speciality_id",x.name)
                    licenseRequest.set("exp_years",x.experience)
                    if(!hashMap.contains(licenseRequest)){
                        hashMap.add(licenseRequest)
                    }
                }
                viewModel.addDocument(header, hashMap)
            }else{
                root_layout.snackbar("Add Speciality")
            }
          //
        }

        binding.add.setOnClickListener {
            if(!binding.experience.text.isNullOrEmpty()){
                val certificate = CertificateClass()
                certificate.name = speciality
                certificate.experience = binding.experience.text.toString()
                if(!dataList.contains(certificate)){
                    dataList.add(certificate)
                }else{
                    toast("Same Speciality.Already Add")
                }


                adapter!!.notifyDataSetChanged()
            }else{
                root_layout.snackbar("Add Speciality")
            }

        }

        binding.facilitySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                speciality = specialityList[position].name
                toast(specialityList[position].name)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
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
                startActivity(Intent(this, com.development.allanproject.views.activity.ui.addcertifictate.AddCertificate::class.java))
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

    private fun getCommonApiData() {
        binding.progressBar.setVisibility(View.VISIBLE)
       var viewModel:SignUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        viewModel.commonData().observe(
            this,
            Observer { apiResponse: CommonApiData ->
                binding.progressBar.setVisibility(View.GONE)
                Toast.makeText(this, apiResponse.status, Toast.LENGTH_SHORT).show()
                if (apiResponse.code == 200) {
                    if (apiResponse.success == true) {
                        SignUp.commonApiData = apiResponse
                        specialityList = SignUp.commonApiData.data.speciality as ArrayList<Speciality>
                        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                            this,
                            android.R.layout.simple_spinner_item,
                            SignUp.commonApiData.data.speciality
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                       binding.facilitySpinner.setAdapter(adapter)
                    }
                }
              //  startActivity(Intent(this@SignUp, PersonalDetail::class.java))
            }
        )
    }


}
