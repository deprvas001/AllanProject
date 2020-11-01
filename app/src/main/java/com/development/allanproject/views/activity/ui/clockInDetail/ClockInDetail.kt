package com.development.allanproject.views.activity.ui.clockInDetail

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.OpenShiftAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityClockInDetailBinding
import com.development.allanproject.model.clockoutModel.BreakTimePost
import com.development.allanproject.model.clockoutModel.ClockInOutPost
import com.development.allanproject.model.clockoutModel.GetClockOutDetail
import com.development.allanproject.model.clockoutModel.PostClockOutDetail
import com.development.allanproject.model.openshiftModel.GetOpenShiftDetail
import com.development.allanproject.model.openshiftModel.GetOpenShiftList
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.clockoutListener.ClockOutLIstener
import com.development.allanproject.views.activity.ui.clockInShift.ClockInShift
import com.development.allanproject.views.activity.ui.clockoutDetail.ClockOutDetail
import com.development.allanproject.views.activity.ui.clockoutDetail.ClockOutViewModel
import com.development.allanproject.views.activity.ui.clockoutDetail.ClockOutViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ClockInDetail : AppCompatActivity(), AuthListener,
    ClockOutLIstener, KodeinAware, View.OnClickListener {
    private lateinit var binding: ActivityClockInDetailBinding
    private var id: Int? = 0
    private lateinit var viewModel: ClockOutViewModel
    var header: HashMap<String, String> = HashMap()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList()
    var adapter: OpenShiftAdapter? = null
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var dataList: ArrayList<GetOpenShiftList> = ArrayList()
    var dataItem: GetOpenShiftDetail? = null
    var isApply: Int = 0
    var profile_pic: String? = null
    var lat: String? = null
    var longt: String? = null
    var isClockEdit: Boolean? = false
    var isBreakTaken:Boolean? = false
    var timeElscp: String? = null
    var post: PostClockOutDetail? = null
    override val kodein: Kodein by kodein()
    lateinit var sessionManager: SessionManager
    private val factory: ClockOutViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_clock_in_detail)
        viewModel = ViewModelProvider(this, factory).get(ClockOutViewModel::class.java)
        viewModel.openListener = this

        viewModel.authListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()

        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]


        header.set("user_id", "22")
        header.set(
            "Authorization",
            "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjA0MDM5NjgyfQ.e2dEGlcD7rFljlpyI2IJYVG5FwWMDyZE9FeyQwV6N7s"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")


        getIntentInformation()
        binding.back.setOnClickListener {
            finish()
        }

        binding.clockOut.setOnClickListener {
            val intent = Intent(this, ClockOutDetail::class.java)
            intent.putExtra("shift_id", id)
            startActivity(intent)
        }

        binding.edit.setOnClickListener(this)
        binding.breakButton.setOnClickListener(this)
    }

    private fun getIntentInformation() {
        if (intent.extras != null) {
            var clockInDetail = intent!!.extras!!.getString("clocked_in")
            var breakTime = intent!!.extras!!.getString("break_time")
            var timeEsc = intent!!.extras!!.getString("time_esclipsed")
            var response = intent!!.extras!!.getParcelable<GetOpenShiftDetail>("shift_detail")
            id = intent!!.extras!!.getInt("id")
            Util.loadImage(
                binding.icon, response!!.shift.icon,
                Util.getCircularDrawable(this)
            )

            Util.loadImage(
                binding.typeImage, response.shift.type_icon,
                Util.getCircularDrawable(this)
            )
            binding.name.setText(response.shift.facility_name)
            binding.rate.setText(response.shift.rate)
            binding.type.setText(response.shift.type)
            binding.startDate.setText(response.shift.start_date)
            binding.startTime.setText(response.shift.start_time)
            binding.endDate.setText(response.shift.end_date)
            binding.endTime.setText(response.shift.end_time)
            binding.address.setText(response.shift.address)
            binding.facilityType.setText(response.shift.facility_type)
            binding.shiftHour.setText(response.shift.minimum_hours)

            if (response.shift.is_urgent) {
                binding.recent.visibility = View.VISIBLE
            }

            binding.clockInAt.text = clockInDetail.toString()
            binding.breakTime.text = breakTime.toString()

            timeElscp = timeEsc.toString()
            binding.timeEclipsed.text = timeEsc.toString()

        }
    }

    override fun onClick(view: View?) {
        if (view!!.id == R.id.edit) {
                showTime(1)
        }else if(view!!.id== R.id.break_button){
            showBreak(2)
        }
    }

    private fun showTime(type: Int) {
        val dialog = Dialog(this)
        val window: Window? = dialog.getWindow()

        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.TOP)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.clock_in_pop_second)

        var timePicker = dialog.findViewById(R.id.time_picker) as TimePicker
        timePicker.setIs24HourView(true)
        var submit = dialog.findViewById(R.id.submit) as Button

        submit.setOnClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute
            var clockType: String? = null
            var clockTime: String? = null
            clockTime = hour.toString() + ":" + minute.toString()
            binding.clockInAt.text = clockTime

            clockType = "in"

            if (type != 2) {
                isClockEdit = true
                viewModel.timeUpdate(header, ClockInOutPost(id!!, clockType!!, clockTime!!))
            }
//             else {
//                isClockEdit = false
//                viewModel.breakTimeUpdate(header, BreakTimePost(id!!, clockType, clockTime!!))
//            }
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showBreak(type: Int) {
        val dialog = Dialog(this)
        val window: Window? = dialog.getWindow()

        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.clock_in_pop)

        var time_elsp = dialog.findViewById(R.id.time_elcp) as TextView
        var close = dialog.findViewById(R.id.close) as ImageView

        var break_button = dialog.findViewById(R.id.btn_break) as Button

        time_elsp.setText(timeElscp)

        close.setOnClickListener{
            dialog.dismiss()
        }

        if(isBreakTaken!!){
            break_button.setText("Resume")

        }else{
            break_button.setText("Take Break")
        }
        break_button.setOnClickListener {
//            val hour = timePicker.hour
//            val minute = timePicker.minute
            var clockType: String? = null
            var clockTime: String? = null
         //   clockTime = hour.toString() + ":" + minute.toString()
          //  binding.clockInAt.text = clockTime

            if(isBreakTaken!!){
                clockType = "end"

            }else{
                clockType = "start"
            }

            clockTime = ""
            if (type != 2) {
                isClockEdit = true
                viewModel.timeUpdate(header, ClockInOutPost(id!!, clockType!!, clockTime!!))
            }
             else {
                isClockEdit = false
                viewModel.breakTimeUpdate(header, BreakTimePost(id!!, clockType, clockTime!!))
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onSuccess(response: GetClockOutDetail) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(response: SignResponse) {
        binding.progressBar.hide()
        if (response.success && response.code.toInt() == 200) {
           if(isClockEdit!!){
               toast("Successfully Update")
           }else{
              isBreakTaken = response.is_break
               timeElscp = response.time_eclipsed
            if(response.is_break){
                binding.breakButton.text = "Resume"
            }else{
                binding.breakButton.text = "Take Break"
            }
            if(!response.break_time.isNullOrEmpty()){
                binding.breakTime.setText(response.break_time)
                binding.timeEclipsed.setText(response.time_eclipsed)
            }
           }
        }
    }

    override fun onFailure(message: String) {
        binding.progressBar.hide()
        root_layout.snackbar(message)
    }
}
