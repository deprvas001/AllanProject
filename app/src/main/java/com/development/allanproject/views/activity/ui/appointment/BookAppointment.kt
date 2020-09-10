package com.development.allanproject.views.activity.ui.appointment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.AppointmentDateAdapter
import com.development.allanproject.adapter.AppointmentTimeAdapter
import com.development.allanproject.adapter.CertificateAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityBookAppointmentBinding
import com.development.allanproject.listener.RecyclerTouchListener
import com.development.allanproject.model.appointmentModel.AppointmentData
import com.development.allanproject.model.appointmentModel.AppointmentDetail
import com.development.allanproject.model.appointmentModel.AppointmentGetModel
import com.development.allanproject.model.appointmentModel.PostAppointment
import com.development.allanproject.model.commonapi.CertificateType
import com.development.allanproject.model.commonapi.CommonApiData
import com.development.allanproject.model.license.LicenseData
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.views.activity.ui.addlicenese.AddLicense
import com.development.allanproject.views.activity.ui.signup.SignUp
import com.development.allanproject.views.activity.ui.signup.SignUpViewModel
import com.development.allanproject.views.activity.ui.speciality.AddSpecialityViewModel
import com.development.allanproject.views.activity.ui.speciality.AddSpecialityViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class BookAppointment : AppCompatActivity(), AuthBookAppointment,
    AuthListener, KodeinAware, View.OnClickListener {
    lateinit var binding: ActivityBookAppointmentBinding
    private val factory: AppointmentModelFactory by instance()
    lateinit var sessionManager: SessionManager
    var header: HashMap<String, String> = HashMap<String, String>()
    var adapter: AppointmentDateAdapter? = null
    var timeAdapter: AppointmentTimeAdapter? = null
    var slots:ArrayList<HashMap<String,String>> = ArrayList()
    var timeSlotPosition:Int=0
    var mLayoutManager: RecyclerView.LayoutManager? = null

    private lateinit var viewModel: BoookAppointmentViewModel
    override val kodein by kodein()
    var locationList: ArrayList<AppointmentData> = ArrayList<AppointmentData>()
    var appointmentList: ArrayList<AppointmentDetail> = ArrayList<AppointmentDetail>()
    val data: ArrayList<HashMap<String, HashMap<String,List<HashMap<String,String>>>>> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_appointment)
        viewModel = ViewModelProvider(this, factory).get(BoookAppointmentViewModel::class.java)
        viewModel.authListener = this
        viewModel.postListener = this
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
        header.set("device_type", "Android")
        header.set("device_token", "XSDJHSHHFKJJSFLH")
        header.set("v_code", "7")

        getAppointmentList()

        binding.btnNext.setOnClickListener(this)

        binding.spinnerLocation.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                binding.timeSlot.visibility = View.GONE
                appointmentList = locationList[position].appointment_details

                adapter = AppointmentDateAdapter(applicationContext, appointmentList)
                mLayoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL, false)
                binding.recyclerView.setLayoutManager(mLayoutManager)
                binding.recyclerView.setItemAnimator(DefaultItemAnimator())
                binding.recyclerView.setAdapter(adapter)


                adapter!!.notifyDataSetChanged()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }



        binding.recyclerView.addOnItemTouchListener(
            RecyclerTouchListener(
                applicationContext,
                binding.recyclerView,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {
                        val appointment  = appointmentList.get(position)
                        slots =   appointment.slots

                        timeAdapter = AppointmentTimeAdapter(applicationContext, slots)
                        mLayoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL, false)
                        binding.recyclerViewTime.setLayoutManager(mLayoutManager)
                        binding.recyclerViewTime.setItemAnimator(DefaultItemAnimator())
                        binding.recyclerViewTime.setAdapter(timeAdapter)

                        binding.timeSlot.visibility = View.VISIBLE
                    }

                    override fun onLongClick(view: View, position: Int) {}
                })
        )

        binding.recyclerViewTime.addOnItemTouchListener(
            RecyclerTouchListener(
                applicationContext,
                binding.recyclerViewTime,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {
                     var timeValue = slots.get(position)
                        for(time in timeValue){
                            timeSlotPosition = time.key.toInt()
                        }
                    }
                    override fun onLongClick(view: View, position: Int) {}
                })
        )

    }

    private fun getAppointmentList() {
        binding.progressBar.setVisibility(View.VISIBLE)
        viewModel.getAppointmentList(header)
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Please Wait")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if(response.success){
            if(response.status == "ok"){
                toast("Success")
            }
        }else{
            toast(response.msg)
        }
    }

    override fun onSuccess(response: AppointmentGetModel) {
        progress_bar.hide()
        if(response.code ==200){
            if(response.success == true){
                 locationList.clear()
                for(appartmentData in response.data){
                       locationList.add(appartmentData)
                }
                val adapter: ArrayAdapter<AppointmentData> = ArrayAdapter<AppointmentData>(
                    this,
                    android.R.layout.simple_spinner_item,
                    locationList
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerLocation.setAdapter(adapter)
            }
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        toast(message)
    }

    override fun onClick(view: View?) {
        if(view!!.id == R.id.btn_next){
            var postDetail: HashMap<String,Int> = HashMap()
            postDetail.put("appointment_id",timeSlotPosition)
            viewModel.postAppointmentList(header,postDetail)
        }
    }

}
