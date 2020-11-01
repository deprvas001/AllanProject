package com.development.allanproject.views.activity.ui.openShift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.development.allanproject.R
import com.development.allanproject.databinding.ActivityApplyShiftScreenViewBinding
import com.development.allanproject.model.openshiftModel.GetOpenShiftDetail
import com.development.allanproject.util.Util

class ApplyShiftScreenView : AppCompatActivity() {
    private lateinit var binding: ActivityApplyShiftScreenViewBinding
    var dataItem: GetOpenShiftDetail? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_shift_screen_view)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_apply_shift_screen_view)
        if(intent.extras !=null){
            dataItem = intent!!.extras!!.getParcelable("shift_detail")
            binding.shiftNumber.setText(intent!!.extras!!.getString("shift_id"))
            binding.successMsg.setText(intent!!.extras!!.getString("shift_msg"))
            binding.notMsg.setText(intent!!.extras!!.getString("shift_not_msg"))
        }

        Util.loadImage(
            binding.icon,dataItem!!.shift.icon,
            Util.getCircularDrawable(this)
        )

        Util.loadImage(
            binding.typeImage,dataItem!!.shift.type_icon,
            Util.getCircularDrawable(this)
        )
        binding.name.setText(dataItem!!.shift.facility_name)
        binding.rate.setText(dataItem!!.shift.rate)
        binding.type.setText(dataItem!!.shift.type)
        binding.startDate.setText(dataItem!!.shift.start_date)
        binding.startTime.setText(dataItem!!.shift.start_time)
        binding.endDate.setText(dataItem!!.shift.end_date)
        binding.endTime.setText(dataItem!!.shift.end_time)
        binding.address.setText(dataItem!!.shift.address)
        binding.facilityType.setText(dataItem!!.shift.facility_type)
        binding.shiftHour.setText(dataItem!!.shift.minimum_hours)
//        binding.breif.setText(dataItem!!.shift.summary)
//        binding.supervisor.setText(dataItem!!.shift.supervisor)
//        binding.datePosted.setText(dataItem!!.shift.posted_date)

        if(dataItem!!.shift.is_urgent){
            binding.recent.visibility = View.VISIBLE
        }

        binding.back.setOnClickListener {
            finish()
        }

    }
}
