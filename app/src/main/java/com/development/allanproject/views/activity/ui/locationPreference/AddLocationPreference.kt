package com.development.allanproject.views.activity.ui.locationPreference

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.development.allanproject.adapter.CityListAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityAddLocationPreferenceBinding
import com.development.allanproject.model.CertificateClass
import com.development.allanproject.model.commonapi.CertificateType
import com.development.allanproject.model.commonapi.CityList
import com.development.allanproject.model.commonapi.CommonApiData
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.views.activity.SignUpComplete
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.signup.SignUpViewModel
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class AddLocationPreference : AppCompatActivity(), AuthListener, KodeinAware,
    CityRecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    var certificateValue:String?=null
    var certificateId: Int?=null
    private lateinit var viewModel: LocationPrefrenceViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<Any> = ArrayList<Any>()
    var adapter: CityListAdapter? = null
    var locationHashMap: HashMap<String,Any> = HashMap()
    var cityList: ArrayList<String> = ArrayList<String>()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var speciality:String?=null
    var openAll:Boolean = false
    var dataList: ArrayList<String> = ArrayList<String>()
    override val kodein by kodein()
    private val factory: LocationPreferenceModelFactory by instance()
    lateinit var sessionManager: SessionManager
    lateinit var binding: ActivityAddLocationPreferenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_location_preference)

        viewModel = ViewModelProvider(this, factory).get(LocationPrefrenceViewModel::class.java)
        viewModel.authListener = this
        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        dataList.clear()

        adapter = CityListAdapter(this, dataList)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)



        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
            CityRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerView)

        header.set("user_id", user_id!!)
        header.set(
            "Authorization",token!!
        )
        header.set("device_type_id", "1")
        header.set("device_token", "XSDJHSHHFKJJSFLH")
        header.set("device_type", "IOS")
        header.set("v_code", "7")

        getCommonApiData(header)

        binding.add.setOnClickListener {
            if(!certificateValue.isNullOrEmpty()){
              //  certificate.experience = certificateId.toString()
                dataList.add(certificateValue!!)

                adapter!!.notifyDataSetChanged()
            }else{
                root_layout.snackbar("Add Location")
            }
        }

        binding.btnNext.setOnClickListener {

            if(binding.openCheckbox.isChecked){

                if(cityList.size>0){
                    locationHashMap.put("location",cityList)
                    locationHashMap.put("open_for_all", false)
                    viewModel.addDocumentDetail(header, locationHashMap,10)
                }
            }else{
                if(dataList.size>0){
                    for (x in dataList){
                        hashMap.add(x)
                    }
                    locationHashMap.put("location",hashMap)
                    if(binding.openCheckbox.isChecked){
                        locationHashMap.put("open_for_all", true)
                    }else{
                        locationHashMap.put("open_for_all", false)
                    }

                    //  Log.i("Testing",locationHashMap.toString())
                    viewModel.addDocumentDetail(header, locationHashMap,10)
                }else{
                    root_layout.snackbar("Add Location")
                }
            }

            //
        }

        binding.locationSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                certificateValue = cityList[position]
                             toast(cityList[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

    }

    private fun getCommonApiData(header: HashMap<String, String>) {
        viewModel.getCityList(header)
     /*binding.progressBar.setVisibility(View.VISIBLE)
        var viewModel: SignUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        viewModel.commonData().observe(
            this,
            Observer { apiResponse: CityList ->
                binding.progressBar.setVisibility(View.GONE)
                Toast.makeText(this, apiResponse.status, Toast.LENGTH_SHORT).show()
                if (apiResponse.code == 200) {
                    if (apiResponse.success == true) {
                        SignUp.commonApiData = apiResponse
                        cityList = SignUp.commonApiData.data.certificates as ArrayList<CertificateType>
                        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                            this,
                            android.R.layout.simple_spinner_item,
                            SignUp.commonApiData.data.certificates
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                        binding.locationSpinner.setAdapter(adapter)
                    }
                }
                //  startActivity(Intent(this@SignUp, PersonalDetail::class.java))
            }
        )*/
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        root_layout.snackbar("${response.step_no} is Done")
        
        if(!response.cities.isNullOrEmpty()){
            cityList = response.cities
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                response.cities
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.locationSpinner.setAdapter(adapter)    
        }else{
            if(response.code.equals("500")){
                root_layout.snackbar("${response.msg}")
            }else{
                root_layout.snackbar("${response.success}")
                if(response.success){
                    startActivity(Intent(this, SignUpComplete::class.java))
                    finish()
                }else{
                    toast("Try Later")
                }
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
        if (viewHolder is CityListAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            val name: String = dataList.get(viewHolder.adapterPosition)

//            // backup of removed item for undo purpose
//            val deletedItem: Item = dataList.get(viewHolder.adapterPosition)
            val deletedIndex = viewHolder.adapterPosition

            // remove the item from recycler view
            adapter!!.removeItem(viewHolder.adapterPosition)

        }
    }
}
