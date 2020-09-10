package com.development.allanproject.views.activity.ui.addlicenese

import android.app.Activity
import android.content.Intent
import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.AddLicenseAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityAddLicensesBinding
import com.development.allanproject.listener.LicenseRecyclerItemTouch
import com.development.allanproject.listener.RecyclerTouchListener
import com.development.allanproject.listener.RecyclerTouchListener.ClickListener
import com.development.allanproject.model.commonapi.CommonApiData
import com.development.allanproject.model.commonapi.License
import com.development.allanproject.model.license.LicenseData
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.signup.SignUpViewModel
import com.development.allanproject.views.activity.ui.speciality.AddSpeciality
import kotlinx.android.synthetic.main.activity_add_license.*
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_personal_detail.root_layout
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class AddLicenses : AppCompatActivity(), AuthListener, KodeinAware,
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    lateinit var binding: ActivityAddLicensesBinding
    private lateinit var viewModel: AddLicenseViewModel
    var dataList: ArrayList<LicenseData> = ArrayList()
    var adapter:AddLicenseAdapter? = null
    var array: ArrayList<String> = ArrayList()
    var licenseValue:String?=null
    var licenseId:String?=null
    var stateValue:String?=null
    var compatValue:String?=null
    lateinit var sessionManager: SessionManager
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var header:HashMap<String,String> = HashMap()
    var licenseList: ArrayList<License> = ArrayList()
    var stateList: ArrayList<String> = ArrayList()
    var itemPosition:Int?=null

    var imgUrl: ArrayList<String> = ArrayList()
    override val kodein by kodein()
    private val factory: AddLicenseViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_licenses)

        viewModel = ViewModelProvider(this, factory).get(AddLicenseViewModel::class.java)
        sessionManager = SessionManager(this)
        viewModel.authListener = this

        adapter = AddLicenseAdapter(this, dataList)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        dataList.clear()

        var hashMap:ArrayList<HashMap<String,Any>> = ArrayList<HashMap<String,Any>>()

        header.set("user_id",user_id!!)
        header.set("Authorization",token!!)
        header.set("device_type_id","1")
        header.set("v_code","7")

        getCommonApiData()

        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
            LicenseRecyclerItemTouch(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerView)

        binding.add.setOnClickListener {
            val radio: RadioButton = findViewById(binding.radioGroup.checkedRadioButtonId)
            compatValue = radio.text.toString()

           val licenseObj = LicenseData()
            licenseObj.license = licenseValue
            licenseObj.state = stateValue
            licenseObj.compat = compatValue
            licenseObj.id = licenseId
            if(!dataList.contains(licenseObj)){
                dataList.add(licenseObj)
            }
            adapter!!.notifyDataSetChanged()
        }

        binding.licenseSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                licenseValue = licenseList[position].name
                licenseId = licenseList[position].id.toString()
                toast(licenseList[position].name)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        binding.spinnerState.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                stateValue = stateList[position]
                toast(stateValue!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        binding.radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                Toast.makeText(applicationContext," On checked change :"+
                        " ${radio.text}",
                    Toast.LENGTH_SHORT).show()
                compatValue = radio.text.toString()
            })

        binding.btnNext.setOnClickListener {
            hashMap.clear()
            if(dataList.size>0){
                for (x in dataList){
                    var licenseRequest: HashMap<String,Any> = HashMap<String,Any>()
                    licenseRequest.put("licence_id",x.id)
                    licenseRequest.put("licence_no",x.license)
                    licenseRequest.put("state",x.state)
                    licenseRequest.put("licence_compact",x.compat)
                    if(!x.expiryDate.isNullOrEmpty()){
                        licenseRequest.set("expiration_date","2020-04-21")
                    }
                    if(!x.issuedate.isNullOrEmpty()){
                        licenseRequest.set("issue_date",x.expiryDate)
                    }
                    if(!x.imageUrl.isNullOrEmpty()){
                        imgUrl.add(x.imageUrl)
                        licenseRequest.set("img_url",imgUrl)
                    }
                    hashMap.add(licenseRequest)
                    Log.i("Testing", hashMap.toString())
                }
                viewModel.addLicense(header, hashMap)
            }else{
                root_layout.snackbar("Add License")
            }
        }

        // row click listener
        binding.recyclerView.addOnItemTouchListener(
            RecyclerTouchListener(
                applicationContext,
                binding.recyclerView,
                object : ClickListener {
                    override fun onClick(view: View, position: Int) {
                        itemPosition = position
                        val data: LicenseData = dataList.get(position)
                         toast(data.state)
                        val intent = Intent(this@AddLicenses,AddLicense::class.java)
                        intent.putExtra("license_id",licenseId)
                        intent.putExtra("license_no",licenseValue)
                        intent.putExtra("state",stateValue)
                        intent.putExtra("compat_value",compatValue)
                        startActivityForResult(intent,1001)

                    }

                    override fun onLongClick(view: View, position: Int) {}
                })
        )

    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
    //    root_layout.snackbar("${response.step_no} is Done")
        if(response.code.equals("500")){
            root_layout.snackbar("${response.status}")
        }else{
            root_layout.snackbar("${response.success}")
            if(response.success){
                startActivity(Intent(this, AddSpeciality::class.java))
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

    private fun getCommonApiData() {
        binding.progressBar.setVisibility(View.VISIBLE)
        var viewModel: SignUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        viewModel.commonData().observe(
            this,
            Observer { apiResponse: CommonApiData ->
                binding.progressBar.setVisibility(View.GONE)
              //  Toast.makeText(this, apiResponse.status, Toast.LENGTH_SHORT).show()
                if (apiResponse.code == 200) {
                    if (apiResponse.success == true) {
                        SignUp.commonApiData = apiResponse
                      licenseList = SignUp.commonApiData.data.license as ArrayList<License>
                      stateList = SignUp.commonApiData.data.states as ArrayList<String>
                        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                            this,
                            android.R.layout.simple_spinner_item,
                            SignUp.commonApiData.data.license
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.licenseSpinner.setAdapter(adapter)

                        val stateAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                            this,
                            android.R.layout.simple_spinner_item,
                            SignUp.commonApiData.data.states
                        )
                        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerState.setAdapter(stateAdapter)
                    }
                }
                //  startActivity(Intent(this@SignUp, PersonalDetail::class.java))
            }
        )
    }

    override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        direction: Int,
        position: Int
    ) {
        if (viewHolder is AddLicenseAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            val name: String = dataList.get(viewHolder.adapterPosition).license

//            // backup of removed item for undo purpose
//            val deletedItem: Item = dataList.get(viewHolder.adapterPosition)
            val deletedIndex = viewHolder.adapterPosition

            // remove the item from recycler view
            adapter!!.removeItem(viewHolder.adapterPosition)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == 1001){
                var licenseRequest: HashMap<String,Any> = HashMap<String,Any>()
                licenseRequest = data!!.getSerializableExtra("data") as HashMap<String, Any>;
                Log.i("Testing", licenseRequest.toString())
                 if(itemPosition != null){
                     dataList.get(itemPosition!!).imageUrl = licenseRequest.get("img_url").toString()
                     dataList.get(itemPosition!!).id = licenseRequest.get("licence_id").toString()
                     dataList.get(itemPosition!!).license = licenseRequest.get("licence_no").toString()
                     dataList.get(itemPosition!!).state = licenseRequest.get("state").toString()
                     dataList.get(itemPosition!!).compat = licenseRequest.get("licence_compact").toString()
                     dataList.get(itemPosition!!).issuedate = licenseRequest.get("issue_date").toString()
                     dataList.get(itemPosition!!).expiryDate= licenseRequest.get("expiration_date").toString()

                     Log.i("Testing", dataList.toString())
                     adapter!!.notifyDataSetChanged()

                 }

            }

        }


    }

}
