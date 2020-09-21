package com.development.allanproject.views.activity.ui.addexperience.addWorkExperience

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.AddPositionAdapter
import com.development.allanproject.adapter.AddReferenceAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityAddExtraExperienceInfoBinding
import com.development.allanproject.listener.PositionRecyclerItemTouch
import com.development.allanproject.model.commonapi.*
import com.development.allanproject.model.experience.PositionData
import com.development.allanproject.model.experience.ReferenceData
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.views.activity.ui.addexperience.viewmodel.AddExperienceViewModel
import com.development.allanproject.views.activity.ui.addexperience.viewmodel.AddExperienceViewModelFactory
import com.development.allanproject.views.activity.ui.addexperience.workExperienceList.WorkExperienceList
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.signup.SignUpViewModel
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AddExtraExperienceInfo : AppCompatActivity() , AuthListener, KodeinAware,
    PositionRecyclerItemTouch.RecyclerItemTouchHelperListener{
    lateinit var binding: ActivityAddExtraExperienceInfoBinding
    private lateinit var viewModel: AddExperienceViewModel
    var header: HashMap<String, String> = HashMap()
    var hashMap: ArrayList<Any> = ArrayList()
    var adapter: AddPositionAdapter? = null
    var adapterReference: AddReferenceAdapter? = null
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var speciality:String?=null
    var facilityId:String?=null
    var currentCompany:String?= "No"
    var empty:String?=""
    var dataList: ArrayList<PositionData> = ArrayList()
    var referenceList: ArrayList<ReferenceData> = ArrayList()
    var workPost: HashMap<String, Any> = HashMap()
    var month:String?=null
    var year:String?=null
    var jobType:String?=null
    var positionData = PositionData()
    var referenceData = ReferenceData()
    var facilityType: ArrayList<FacilityType> = ArrayList()
    var addMoreButton: Int?=0
    override val kodein by kodein()
    private val factory: AddExperienceViewModelFactory by instance()
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_extra_experience_info )

        viewModel = ViewModelProvider(this, factory).get(AddExperienceViewModel::class.java)
        viewModel.authListener = this
        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]


        adapter = AddPositionAdapter(this, dataList)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        adapterReference = AddReferenceAdapter(this, referenceList)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerViewReference.setLayoutManager(mLayoutManager)
        binding.recyclerViewReference.setItemAnimator(DefaultItemAnimator())
        binding.recyclerViewReference.setAdapter(adapterReference)



        dataList.clear()
        referenceList.clear()

        getCommonApiData()

     /*   val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
            PositionRecyclerItemTouch(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerView)*/

        header.set("user_id", user_id!!)
        header.set(
            "Authorization", token!!
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        if(intent.extras!=null){
            if(intent.extras!!.getBoolean("isEdit")){
               binding.skip.visibility = View.GONE
                binding.addMoreLicense.visibility = View.GONE
            }
        }

        binding.positionLayout.addMore.setOnClickListener{
            if(binding.positionLayout.position.text.isNullOrEmpty() ){
                toast("Please Add Position first")
            }else{
                var positionData = PositionData()
                dataList.add(positionData)
                adapter!!.notifyDataSetChanged()
            }
        }

        binding.referenceLayout.addMore.setOnClickListener{
            if(binding.referenceLayout.name.text.isNullOrEmpty()){
                toast("Please Add Referenece Name")
            }else{
                var positionData = ReferenceData()
                referenceList.add(positionData)
                adapterReference!!.notifyDataSetChanged()
            }
        }

        binding.btnNextStep.setOnClickListener {
            addMoreButton = 1
            addWorkExperience(addMoreButton!!)
        }

        binding.date.setOnClickListener{
            dateDialog(1)
        }

        binding.endDate.setOnClickListener{
            dateDialog(2)
        }

        binding.radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                toast("${radio.text}")
                currentCompany = radio.text.toString()
                if(currentCompany.equals("No")){
                    binding.endDate.visibility = View.VISIBLE
                }else{
                    binding.endDate.visibility = View.GONE
                    binding.endDate.text.clear()
                }
            })

        binding.facilitySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                facilityId = facilityType[position].id.toString()
                toast(facilityType[position].name.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        binding.facilitySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                facilityId = facilityType[position].id.toString()
                toast(facilityType[position].name.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        binding.positionLayout.monthSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
               month = parent.selectedItem.toString()
                toast(month!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        binding.positionLayout.yearSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                year = parent.selectedItem.toString()
                toast(year!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        binding.referenceLayout.managerSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                jobType = parent.selectedItem.toString()
                toast(jobType!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        binding.addMoreLicense.setOnClickListener {
            addMoreButton = 0
          addWorkExperience(addMoreButton!!)
        }

        binding.skip.setOnClickListener {
            addMoreButton = 1
            viewModel.addWorkDetail(header,workPost)
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
                if(intent.extras!=null){
                     if(intent.extras!!.getBoolean("isEdit")){
                        startActivity(Intent(this, WorkExperienceList::class.java))
                        finish()
                     }
                }else{
                    if(addMoreButton == 0){
                        startActivity(Intent(this, AddExtraExperienceInfo::class.java))
                        finish()
                    }else{
                        startActivity(Intent(this, com.development.allanproject.views.activity.ui.setFaciliity.FacilityType::class.java))
                        finish()
                    }

                }

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
        if (viewHolder is AddPositionAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            val name: String = dataList.get(viewHolder.adapterPosition).position

//            // backup of removed item for undo purpose
//            val deletedItem: Item = dataList.get(viewHolder.adapterPosition)
            val deletedIndex = viewHolder.adapterPosition

            // remove the item from recycler view
            adapter!!.removeItem(viewHolder.adapterPosition)

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
                        facilityType = SignUp.commonApiData.data.facility_type as ArrayList<FacilityType>
                        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                            this,
                            android.R.layout.simple_spinner_item,
                            SignUp.commonApiData.data.facility_type
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                        binding.facilitySpinner.setAdapter(adapter)
                    } }
            })
    }

    fun dateDialog(i: Int) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                if(i==1){
                    binding.date.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth)
                }else{
                    binding.endDate.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth)
                }

            }, year, month, day
        )
          datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show()
    }

    fun addWorkExperience(i: Int) {
        if(binding.name.text.isNullOrEmpty() || binding.address.text.isNullOrEmpty()
            || binding.date.text.isNullOrEmpty() ||
            binding.positionLayout.position.text.isNullOrEmpty()
        ){
            toast("Please fill details")
        }else{
            positionData.position= binding.positionLayout.position.text.toString()
            positionData.speciality = binding.positionLayout.specaility.text.toString()
            positionData.unit =  binding.positionLayout.unit.text.toString()
            positionData.charting_technology =  binding.positionLayout.emr.text.toString()
            positionData.isCharge_experience =  binding.positionLayout.checkbox.isChecked
            positionData.position_year = year
            positionData.position_month = month

            if(!dataList.contains(positionData)){
                dataList.add(positionData!!)
            }

            Log.i("referenceData",dataList.toString())

            for ( data in dataList){
                if(data.position.isNullOrEmpty() || data.charting_technology.isNullOrEmpty() ||
                    data.position_year.isNullOrEmpty() || data.position_month.isNullOrEmpty()){
                    toast("Please fill position detail properly")
                    return;
                }else if(data.position_year.equals("Year") || data.position_month.equals("Month")){
                    toast("Please select duration of position")
                    return;
                }
            }


            referenceData.facility_name = binding.referenceLayout.facilityName.text.toString()
            referenceData.name = binding.referenceLayout.name.text.toString()
            referenceData.job_title = binding.referenceLayout.title.text.toString()
            referenceData.job_type = binding.referenceLayout.titleManager.text.toString()
            referenceData.phone = binding.referenceLayout.mobile.text.toString()
            referenceData.email = binding.referenceLayout.email.text.toString()
            referenceData.std_code = "+91"

            if(!referenceList.contains(referenceData)){
                referenceList.add(referenceData)
            }


            Log.i("referenceData",referenceList.toString())

            for (data in referenceList){
                if(data.facility_name.isNullOrEmpty() || data.job_title.isNullOrEmpty() ||
                    data.name.isNullOrEmpty() || data.job_type.isNullOrEmpty()
                ){
                    toast("Please fill reference properly")
                    return;
                }else if( data.email.isNullOrEmpty() and data.phone.isNullOrEmpty()){
                    toast("Please add email id or phone number")
                    return;
                }
            }

            if(currentCompany.equals("No")){
               if(binding.endDate.text.isNullOrEmpty()){
                   toast("Please add end date")
                   return
               }
            }
            workPost.put("facility_id",facilityId!!.toString())
            workPost.put("facility_name", binding.name.text!!.toString())
            workPost.put("address", binding.address.text!!.toString())
            workPost.put("start_date",binding.date.text!!.toString())
            workPost.put("end_date",binding.endDate.text.toString())
/*
            workPost.put("current_company",currentCompany!!.toString())
*/
            workPost.put("sub_details", dataList!!)
            workPost.put("reference", referenceList!!)

            Log.i("Testing", workPost.toString())

            viewModel.addWorkDetail(header,workPost)
        }
    }
}
