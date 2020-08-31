package com.development.allanproject.views.activity.ui.speciality

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
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.signup.SignUpViewModel
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class AddSpeciality : AppCompatActivity(), AuthListener, KodeinAware ,
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    private lateinit var binding: ActivityAddSpecialityBinding
    private lateinit var viewModel: AddSpecialityViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
    var licenseRequest: HashMap<String, Any> = HashMap<String, Any>()
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


        adapter = CertificateAdapter(this, dataList)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        dataList.clear()

        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
            RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerView)


        header.set("user_id", "3")
        header.set(
            "Authorization",
            "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjozLCJleHAiOjE1OTg3MDc4MTd9.QHIvzoql7qmAfwdgZBhEA6vv0sEDCziK0agaPuYQSKo"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        getCommonApiData()
        // viewModel.licenseDetail(viewModel)
        binding.btnNext.setOnClickListener {
            /* for ( x in 0..1){
                 licenseRequest.clear()
                 licenseRequest.set("speciality_id","1")
                 licenseRequest.set("exp_years","5")
                 hashMap.add(licenseRequest)
             }*/

            if(dataList.size>0){
                for (x in dataList){
                    licenseRequest.set("speciality_id",x.name)
                    licenseRequest.set("exp_years",x.experience)
                    hashMap.add(licenseRequest)
                }
            //    viewModel.addCertificate(header, hashMap)
            }else{
                root_layout.snackbar("Add Speciality")
            }

          //

        }

        binding.add.setOnClickListener {
            val certificate = CertificateClass()
            certificate.name = speciality
            certificate.experience = binding.experience.text.toString()
            dataList.add(certificate)

            adapter!!.notifyDataSetChanged()
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

            // showing snack bar with Undo option
       //     root_layout.snackbar("$name removed from LIST!")
           /* val snackbar = Snackbar
                .make(coordinatorLayout, "$name removed from cart!", Snackbar.LENGTH_LONG)
            snackbar.setAction("UNDO", object : OnClickListener() {
                fun onClick(view: View?) {

                    // undo is selected, restore the deleted item
                    mAdapter.restoreItem(deletedItem, deletedIndex)
                }
            })*/
           /* snackbar.setActionTextColor(Color.YELLOW)
            snackbar.show()*/
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
