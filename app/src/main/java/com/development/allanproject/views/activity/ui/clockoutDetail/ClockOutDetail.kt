package com.development.allanproject.views.activity.ui.clockoutDetail

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.OpenShiftAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityClockOutDetailBinding
import com.development.allanproject.model.clockoutModel.BreakTimePost
import com.development.allanproject.model.clockoutModel.ClockInOutPost
import com.development.allanproject.model.clockoutModel.GetClockOutDetail
import com.development.allanproject.model.clockoutModel.PostClockOutDetail
import com.development.allanproject.model.openshiftModel.GetOpenShiftDetail
import com.development.allanproject.model.openshiftModel.GetOpenShiftList
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.clockoutListener.ClockOutLIstener
import com.development.allanproject.views.activity.ui.SignatureScreen
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ClockOutDetail : AppCompatActivity(), AuthListener,
    ClockOutLIstener, KodeinAware, View.OnClickListener {
    private lateinit var binding: ActivityClockOutDetailBinding
    private lateinit var viewModel: ClockOutViewModel
    var header: HashMap<String, String> = HashMap()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList()
    var adapter: OpenShiftAdapter? = null
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var dataList: ArrayList<GetOpenShiftList> = ArrayList()
    var dataItem: GetOpenShiftDetail? = null
    var isApply: Int = 0
    var profile_pic:String? = null
    var lat:String? = null
    var longt:String? = null
    var id:Int?=0;
    var isClockOut:Boolean?=false
    var post:PostClockOutDetail?=null
    override val kodein: Kodein by kodein()
    lateinit var sessionManager: SessionManager
    private val factory: ClockOutViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_clock_out_detail)
        viewModel = ViewModelProvider(this, factory).get(ClockOutViewModel::class.java)
        viewModel.openListener = this

        viewModel.authListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()

        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]


        header.set("user_id", "22")
        header.set(
            "Authorization","eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjA0MDM5NjgyfQ.e2dEGlcD7rFljlpyI2IJYVG5FwWMDyZE9FeyQwV6N7s"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        if(intent.extras !=null){
            id  = intent!!.extras!!.getInt("shift_id")
            viewModel.getClockOutShift(header,intent!!.extras!!.get("shift_id").toString())
        }

        binding.btnClockout.setOnClickListener(this)
        binding.signature.setOnClickListener(this)
        binding.checkInTimeEdit.setOnClickListener(this)
        binding.checkOutTimeEdit.setOnClickListener(this)
        binding.breakTimeEdit.setOnClickListener(this)
    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onSuccess(response: SignResponse) {
        binding.progressBar.hide()
        if(response.success && response.code.toInt() == 200){
            if(isClockOut!!){
                toast("Successfully Clocked Out")
                finish()
            }else{
                toast("Successfully Update")
            }
        }
    }

    override fun onSuccess(response:GetClockOutDetail) {
        binding.progressBar.hide()

        if(response.success && response.code.toInt() == 200) {
             post = PostClockOutDetail(id!!,response.shift.checkintime,
                response.shift.checkouttime,response.shift.breaktime,"")

            binding.clockout = post

            binding.timeEcs.text = response.shift.time_eclipsed
            binding.checkInTime.text = response.shift.checkintime
            binding.checkOutTime.text = response.shift.checkouttime
            binding.breakTime.text = response.shift.breaktime
            binding.supervisor.text = response.shift.supervisor

        }else{
        }
    }

    override fun onFailure(message: String) {
        binding.progressBar.hide()
        root_layout.snackbar(message)
    }

    override fun onClick(view: View?) {
      if(view!!.id == R.id.signature){
          startActivityForResult(Intent(this, SignatureScreen::class.java),1001)
      }else if(view!!.id == R.id.btn_clockout){
         // post!!.shift_id = id!!
          isClockOut = true
     //    Log.i("Allan", binding.clockout!!.toString())
          viewModel.clockOut(header,binding.clockout!!)
      }else if(view!!.id == R.id.check_in_time_edit){
           showTime(1)
      }else if(view!!.id == R.id.check_out_time_edit){
          showTime(2)
      }else if(view!!.id == R.id.break_time_edit){
          showTime(3)
      }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.extras!!.get("result").toString()
            binding.signature.setImageURI(Uri.parse(fileUri!!))
            post!!.signature = fileUri
            profile_pic = "https://i.picsum.photos/id/658/200/300.jpg?hmac=K1TI0jSVU6uQZCZkkCMzBiau45UABMHNIqoaB9icB_0"
            //  toast(profile_pic.toString())
            //You can get File object from intent
//            val file: File = ImagePicker.getFile(data)!!
//
//            //You can also get File Path from intent
//            val filePath:String = ImagePicker.getFilePath(data)!!
        }
        else if (resultCode == ImagePicker.RESULT_ERROR) {
            toast( ImagePicker.getError(data))
        } else {
            toast("Task Cancelled")
        }
    }

    private fun showTime(type:Int) {
        val dialog = Dialog(this)
        val window: Window? = dialog.getWindow()

        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.TOP )

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.clock_in_pop_second)

        var timePicker = dialog.findViewById(R.id.time_picker) as TimePicker
        timePicker.setIs24HourView(true)
        var submit = dialog.findViewById(R.id.submit) as Button

        submit.setOnClickListener{
            isClockOut = false
            val hour = timePicker.hour
            val minute = timePicker.minute
            var clockType:String?=null
            var clockTime:String?=null
            clockTime = hour.toString()+":"+minute.toString()
            if(type==1){
                binding.checkInTime.text = clockTime
                clockType = "in"
            }else if(type==2){
                binding.checkOutTime.text = clockTime
                clockType="out"
            }else{
                clockType = ""
                binding.breakTime.text = clockTime

            }

            if(type != 3){
                viewModel.timeUpdate(header, ClockInOutPost(id!!,clockType!!,clockTime!!))
            }else{
                viewModel.breakTimeUpdate(header, BreakTimePost(id!!,clockType,clockTime!!))
            }
            dialog.dismiss()

        }

        dialog.show()
    }
}
