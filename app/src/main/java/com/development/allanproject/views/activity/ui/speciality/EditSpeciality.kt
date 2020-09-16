package com.development.allanproject.views.activity.ui.speciality

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
import com.development.allanproject.adapter.EditSpecialityAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityEditSpecialityBinding
import com.development.allanproject.model.CertificateClass
import com.development.allanproject.model.commonapi.CommonApiData
import com.development.allanproject.model.commonapi.Speciality
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.model.speciality.Data
import com.development.allanproject.model.speciality.GetSpeciality
import com.development.allanproject.util.*
import com.development.allanproject.util.specialityListener.SpecialityAuthListener
import com.development.allanproject.views.activity.ui.addcertifictate.AddCertificate
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.signup.SignUpViewModel
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_personal_detail.progress_bar
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.position_layout_item.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class EditSpeciality : AppCompatActivity(), SpecialityAuthListener, AuthListener, KodeinAware,
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener,
    EditSpecialityAdapter.SpecialityCallBack {
    private lateinit var binding: ActivityEditSpecialityBinding
    private lateinit var viewModel: AddSpecialityViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
    var adapter: EditSpecialityAdapter? = null
    var specialityList: ArrayList<Speciality> = ArrayList<Speciality>()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var speciality: String? = null
    var dataList: ArrayList<Data> = ArrayList()
    override val kodein by kodein()
    var dataItem: Data? = null
    var isAdd:Boolean = false

    private val factory: AddSpecialityViewModelFactory by instance()
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_speciality)
        viewModel = ViewModelProvider(this, factory).get(AddSpecialityViewModel::class.java)
        viewModel.authListener = this
        viewModel.specialityAuthListener = this
        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        adapter = EditSpecialityAdapter(this, dataList, this)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        dataList.clear()

        /* val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
             RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
         ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerView)
 */
        header.set("user_id", "22")
        header.set(
            "Authorization",
            "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjAwMzI2Nzg2fQ.vaiRJiTisqb89tiQJqg3t0rubigehfUnXIPtOife52k"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        getCommonApiData()
        // viewModel.licenseDetail(viewModel)
        binding.btnNext.setOnClickListener {


            //
        }

        binding.add.setOnClickListener {
            isAdd = true
            if(binding.experience.text.isNullOrEmpty()){
                toast("Please Add Experience")
            }else{
                var licenseRequest: HashMap<String, Any> = HashMap<String, Any>()
                licenseRequest.put("speciality_id",speciality!!)
                licenseRequest.put("exp_years", binding.experience.text.toString())
                viewModel.addSpeciality(header,licenseRequest)
            }


        }

        binding.facilitySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                speciality = specialityList[position].id.toString()
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

    override fun onSuccess(response: GetSpeciality) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code == 200) {
            dataList.clear()
            if (response.data.size > 0) {
                for (data in response.data) {
                    for (speciality in specialityList) {

                        if (data.speciality_id == speciality.id) {
                            val specailityData = Data(
                                data.created_at,
                                data.exp_years,
                                data.id,
                                data.nurse_id,
                                data.speciality_id,
                                data.status,
                                data.updated_at,
                                speciality.name
                            )
                            dataList.add(specailityData)
                        }
                    }
                }
                adapter!!.notifyDataSetChanged()
            }
        } else {
            toast(response.status.toString())
        }
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if (response.success && response.code.equals("200") && response.status.equals("ok")) {
           if(isAdd){
             /*  startActivity(Intent(this, EditSpeciality::class.java))
               finish()*/
               getCommonApiData()
           }else{
               toast("Deleted Successfully")
               if (dataList.contains(dataItem)) {
                   dataList.removeAt(dataList.indexOf(dataItem))
                   adapter!!.notifyDataSetChanged()
               }
           }

        } else {
            toast(response.msg)
        }

    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    /*override fun onSwiped(
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
    }*/

    private fun getCommonApiData() {
        binding.experience.text.clear()
        binding.progressBar.setVisibility(View.VISIBLE)
        var signUpViewModel: SignUpViewModel =
            ViewModelProvider(this).get(SignUpViewModel::class.java)
        signUpViewModel.commonData().observe(
            this,
            Observer { apiResponse: CommonApiData ->
                binding.progressBar.setVisibility(View.GONE)
                Toast.makeText(this, apiResponse.status, Toast.LENGTH_SHORT).show()
                if (apiResponse.code == 200) {
                    if (apiResponse.success == true) {
                        specialityList.clear()
                        dataList.clear()
                        SignUp.commonApiData = apiResponse
                        specialityList =
                            SignUp.commonApiData.data.speciality as ArrayList<Speciality>
                        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                            this,
                            android.R.layout.simple_spinner_item,
                            SignUp.commonApiData.data.speciality
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        isAdd = false
                        binding.facilitySpinner.setAdapter(adapter)
                        viewModel.getSpeciality(header, "4")
                    }
                }
                //  startActivity(Intent(this@SignUp, PersonalDetail::class.java))
            }
        )
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        TODO("Not yet implemented")
    }

    override fun listenerMethod(data: Data?) {
        if (data != null) {
            isAdd = false
            dataItem = data
            viewModel.deleteWorkExperience(header, data.id)
        }
    }
}
