package com.development.allanproject.views.activity.ui.viewPastShift

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.OpenShiftAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityViewPastShiftBinding
import com.development.allanproject.model.cancelShiftModel.CancelShiftPost
import com.development.allanproject.model.openshiftModel.*
import com.development.allanproject.model.pastShiftModel.MyPastShift
import com.development.allanproject.model.pastShiftModel.RateFacilityModel
import com.development.allanproject.model.pastShiftModel.RequestPayModel
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.openshiftListener.OpenShiftDetailListener
import com.development.allanproject.util.pastShiftListener.PastShiftListener
import com.development.allanproject.views.activity.ui.facilityprofile.FacilityProfileScreen
import com.development.allanproject.views.activity.ui.openShift.ApplyShiftScreenView
import com.development.allanproject.views.activity.ui.openShift.ViewOpenShiftModel
import com.development.allanproject.views.activity.ui.openShift.ViewOpenShiftModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import okhttp3.internal.notifyAll
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ViewPastShift : AppCompatActivity(), AuthListener, PastShiftListener,
    KodeinAware, View.OnClickListener {
    private lateinit var binding: ActivityViewPastShiftBinding
    private lateinit var viewModel: PastShiftViewModel
    var header: HashMap<String, String> = HashMap()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList()
    var adapter: OpenShiftAdapter? = null
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var dataList: ArrayList<GetOpenShiftList> = ArrayList()
    var dataItem: MyPastShift? = null
    var rating_emoji: Int = 0
    var id:Int?=0;
    var isFav:Boolean?=false
    override val kodein: Kodein by kodein()
    lateinit var sessionManager: SessionManager
    private val factory: PastShiftViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_past_shift)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_past_shift)

        viewModel = ViewModelProvider(this, factory).get(PastShiftViewModel::class.java)
        viewModel.openListener = this
        viewModel.authListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()

        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id", "22")
        header.set(
            "Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjA0MTM2Nzc2fQ.NYuL3bpj62Q8QGVlPSpjK0p6vG2ixEoRb0GepIDflws"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        if(intent.extras !=null){
            id  = intent!!.extras!!.getInt("shift_id")
            viewModel.getShift(header,intent!!.extras!!.get("shift_id").toString())
        }

        binding.name.setOnClickListener {
            val intent = Intent(this, FacilityProfileScreen::class.java)
            intent.putExtra("facility_id", id)
            startActivity(intent)
        }
        binding.back.setOnClickListener { finish() }
        binding.requestPay.setOnClickListener(this)
        binding.rateFacility.setOnClickListener(this)
    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onSuccess(response: SignResponse) {
        binding.progressBar.hide()
        if(response.success && response.code.toInt() == 200){
            toast("Request Pay Successfully")
        }else{
            toast(response.msg)
        }
    }

    override fun onSuccess(response: MyPastShift) {
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
            binding.earned.setText(response.shift.earned)
            binding.shiftWage.setText(response.shift.wage)
            binding.shiftPermium.setText(response.shift.premium)
            binding.clockInTime.setText(response.shift.clock_in_time)
            binding.clockOutTime.setText(response.shift.clock_out_time)
            binding.shedHrs.setText(response.shift.minimum_hours)
            if(response.shift.is_paid){
                binding.status.setText("Paid")
            }else{
                binding.status.setText("Not Paid")
            }


            if(response.shift.is_urgent){
                binding.recent.visibility = View.VISIBLE
            }

        }
    }

    override fun onFailure(message: String) {
        binding.progressBar.hide()
        root_layout.snackbar(message)

    }

    override fun onClick(view: View?) {
        if(view!!.id == R.id.request_pay){
         viewModel.requestPay(header, RequestPayModel(id!!,true))
        }else if(view!!.id == R.id.rate_facility){
            showMaterialDialog()
        }
    }

    private fun showMaterialDialog() {
        val dialog = Dialog(this)
        val window: Window? = dialog.getWindow()

        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.TOP )

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.rate_dialog)

        val facility_name = dialog.findViewById(R.id.name) as TextView
        val date = dialog.findViewById(R.id.date) as TextView
        val address = dialog.findViewById(R.id.address) as TextView
        val comment = dialog.findViewById(R.id.comment_box) as EditText
        val hrs = dialog.findViewById(R.id.total_hrs) as TextView
        val price = dialog.findViewById(R.id.price) as TextView
        val rate_button  = dialog.findViewById(R.id.rate_facility) as TextView
        val is_anoym = dialog.findViewById(R.id.rate_anomy) as CheckBox
        val imageView = dialog.findViewById(R.id.imageView) as ImageView
        val emoji1 = dialog.findViewById(R.id.image1) as ImageView
        val emoji2 = dialog.findViewById(R.id.image2) as ImageView
        val emoji3 = dialog.findViewById(R.id.image3) as ImageView
        val emoji4 = dialog.findViewById(R.id.image4) as ImageView
        val emoji5 = dialog.findViewById(R.id.image5) as ImageView
        val bookmark = dialog.findViewById(R.id.bookmark) as ImageView

        if(dataItem!!.shift.is_facility_fav){
            bookmark.setColorFilter(
                ContextCompat.getColor(this, R.color.colorPrimary),
                android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        emoji1.setOnClickListener {
            emoji1.setColorFilter(
                ContextCompat.getColor(this, R.color.text_color_hint),
                android.graphics.PorterDuff.Mode.MULTIPLY);
            emoji2.setColorFilter(null);
            emoji3.setColorFilter(null);
            emoji4.setColorFilter(null);
            emoji5.setColorFilter(null);
            rating_emoji = 1
        }
        emoji2.setOnClickListener {
            emoji1.setColorFilter(null);
            emoji2.setColorFilter( ContextCompat.getColor(this, R.color.text_color_hint),
                android.graphics.PorterDuff.Mode.MULTIPLY);
            emoji3.setColorFilter(null);
            emoji4.setColorFilter(null);
            emoji5.setColorFilter(null);
            rating_emoji = 2
        }
        emoji3.setOnClickListener {
            emoji1.setColorFilter(null);
            emoji2.setColorFilter(null);
            emoji3.setColorFilter( ContextCompat.getColor(this, R.color.text_color_hint),
                android.graphics.PorterDuff.Mode.MULTIPLY);
            emoji4.setColorFilter(null);
            emoji5.setColorFilter(null);
            rating_emoji = 3
        }
        emoji4.setOnClickListener {
            emoji1.setColorFilter(null);
            emoji2.setColorFilter(null);
            emoji3.setColorFilter(null);
            emoji4.setColorFilter( ContextCompat.getColor(this, R.color.text_color_hint),
                android.graphics.PorterDuff.Mode.MULTIPLY);
            emoji5.setColorFilter(null);
            rating_emoji = 4
        }
        emoji5.setOnClickListener {
            emoji1.setColorFilter(null);
            emoji2.setColorFilter(null);
            emoji3.setColorFilter(null);
            emoji4.setColorFilter(null);
            emoji5.setColorFilter( ContextCompat.getColor(this, R.color.text_color_hint),
                android.graphics.PorterDuff.Mode.MULTIPLY);
            rating_emoji = 5
        }


        Util.loadImage(
            imageView, dataItem!!.shift.icon,
            Util.getCircularDrawable(this)
        )

        facility_name.setText(dataItem!!.shift.facility_name)
        date.setText(dataItem!!.shift.start_date)
        address.setText(dataItem!!.shift.address)
        hrs.setText(dataItem!!.shift.minimum_hours)
        price.setText(dataItem!!.shift.earned)

        rate_button.setOnClickListener {
            if(comment.text.toString().isNullOrEmpty()){
                toast("Please add comment")
                return@setOnClickListener
            }else if(rating_emoji == 0){
                toast("Please select rating")
                return@setOnClickListener
            } else{
                var is_Anoym = false
                if(is_anoym.isChecked){
                     is_Anoym = true
                }else{
                    is_Anoym = false
                }

                val rate_comment  = RateFacilityModel(dataItem!!.shift.facility_id,dataItem!!.shift.id,
                    rating_emoji, comment.text.toString(),is_Anoym )

                viewModel.rateFacility(header, rate_comment)
                dialog.dismiss()
            }
        }

        bookmark.setOnClickListener {
            if(dataItem!!.shift.is_facility_fav){
                isFav = false
            }else{
                isFav = true
            }
            val post = PostBookmark(dataItem!!.shift.facility_id, isFav!!)
            viewModel.postBookmark(header,post)
        }

        dialog.show()
    }
}
