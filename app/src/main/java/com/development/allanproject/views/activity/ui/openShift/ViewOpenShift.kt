package com.development.allanproject.views.activity.ui.openShift

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.OpenShiftAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityViewOpenShiftBinding
import com.development.allanproject.model.ehrs.EHRSData
import com.development.allanproject.model.openshiftModel.ApplyShiftPost
import com.development.allanproject.model.openshiftModel.GetOpenShiftDetail
import com.development.allanproject.model.openshiftModel.GetOpenShiftList
import com.development.allanproject.model.openshiftModel.SaveShiftPost
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.openshiftListener.OpenShiftDetailListener
import com.development.allanproject.views.activity.FacilityProfile
import com.development.allanproject.views.activity.ui.clockInShift.ClockInShift
import com.development.allanproject.views.activity.ui.facilityprofile.FacilityProfileScreen
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ViewOpenShift : AppCompatActivity(), AuthListener,OpenShiftDetailListener, KodeinAware, View.OnClickListener {
    private lateinit var binding:ActivityViewOpenShiftBinding
    private lateinit var viewModel: ViewOpenShiftModel
    var header: HashMap<String, String> = HashMap()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList()
    var adapter: OpenShiftAdapter? = null
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var dataList: ArrayList<GetOpenShiftList> = ArrayList()
    var dataItem: GetOpenShiftDetail? = null
    var isApply: Int = 0
    var id:Int?=0;
    override val kodein: Kodein by kodein()
    lateinit var sessionManager: SessionManager
    private val factory: ViewOpenShiftModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_open_shift)
        viewModel = ViewModelProvider(this, factory).get(ViewOpenShiftModel::class.java)
        viewModel.openListener = this
        viewModel.authListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()

        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id", "22")
        header.set(
            "Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjA0MDM5NjgyfQ.e2dEGlcD7rFljlpyI2IJYVG5FwWMDyZE9FeyQwV6N7s"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        if(intent.extras !=null){
            id  = intent!!.extras!!.getInt("shift_id")
            viewModel.getShift(header,intent!!.extras!!.get("shift_id").toString())
        }

        binding.name.setOnClickListener {
            val intent = Intent(this,FacilityProfileScreen::class.java)
            intent.putExtra("facility_id", id)
            startActivity(intent)
        }
        binding.back.setOnClickListener { finish() }
        binding.saveShift.setOnClickListener(this)
        binding.apply.setOnClickListener(this)
        binding.request.setOnClickListener(this)

        binding.clockIn.setOnClickListener {
            val intent = Intent(this,ClockInShift::class.java)
            intent.putExtra("shift_id", id)
            startActivity(intent)
        }
    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onSuccess(response: SignResponse) {
        binding.progressBar.hide()

        if(response.success && response.code.toInt() == 200) {
            if (isApply == 0) {
                toast("Shift Successfully Saved")
            }else {
                var intent = Intent(this, ApplyShiftScreenView::class.java)
                intent.putExtra("shift_detail", dataItem!!)
                intent.putExtra("shift_id", response.shift_id)
                intent.putExtra("shift_msg",response.msg)
                intent.putExtra("shift_not_msg", response.msg_noti)
                startActivity(intent)
            }
        }
    }

    override fun onSuccess(response: GetOpenShiftDetail) {
        binding.progressBar.hide()
        if(response.success){
            dataItem = response
            Util.loadImage(
                binding.icon, response.shift.icon,
                Util.getCircularDrawable(this)
            )

            Util.loadImage(
                binding.typeImage, response.shift.type_icon,
                Util.getCircularDrawable(this)
            )
            id = response.shift.facility_id
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
            binding.breif.setText(response.shift.summary)
            binding.supervisor.setText(response.shift.supervisor)
            binding.datePosted.setText(response.shift.posted_date)

            if(response.shift.is_urgent){
                binding.recent.visibility = View.VISIBLE
            }

            if(response.shift.category.equals("normal")){
                binding.request.visibility = View.VISIBLE
            }else{
                binding.apply.visibility = View.VISIBLE
            }

            if(response.shift.is_saved){
                binding.saveShift.setText("Remove From Save")
            }else{
                binding.saveShift.setText("Save Shift")
            }

        }
    }

    override fun onFailure(message: String) {
        binding.progressBar.hide()
        root_layout.snackbar(message)

    }

    override fun onClick(view: View?) {
        if(view!!.id == R.id.save_shift){
            isApply = 0
            if(dataItem!!.shift.is_saved){
                viewModel.saveShift(header, SaveShiftPost(id!!, false))
            }else{
                viewModel.saveShift(header, SaveShiftPost(id!!, true))
            }

        }else if(view!!.id == R.id.apply){
            isApply = 1
            viewModel.applyShift(header, ApplyShiftPost(id!!, "apply"))
        }else  if(view!!.id == R.id.request){
            isApply = 2
            viewModel.applyShift(header, ApplyShiftPost(id!!, "request"))
        }
    }
}
