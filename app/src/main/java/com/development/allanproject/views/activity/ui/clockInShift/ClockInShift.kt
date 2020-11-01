package com.development.allanproject.views.activity.ui.clockInShift

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.BuildConfig
import com.development.allanproject.R
import com.development.allanproject.adapter.OpenShiftAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityClockInShiftBinding
import com.development.allanproject.model.clockShiftModel.ClockInShiftPost
import com.development.allanproject.model.openshiftModel.ApplyShiftPost
import com.development.allanproject.model.openshiftModel.GetOpenShiftDetail
import com.development.allanproject.model.openshiftModel.GetOpenShiftList
import com.development.allanproject.model.openshiftModel.SaveShiftPost
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.openshiftListener.OpenShiftDetailListener
import com.development.allanproject.views.activity.ui.clockInDetail.ClockInDetail
import com.development.allanproject.views.activity.ui.facilityprofile.FacilityProfileScreen
import com.development.allanproject.views.activity.ui.openShift.ApplyShiftScreenView
import com.development.allanproject.views.activity.ui.openShift.ViewOpenShiftModel
import com.development.allanproject.views.activity.ui.openShift.ViewOpenShiftModelFactory
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.File

class ClockInShift : AppCompatActivity() , AuthListener,
    OpenShiftDetailListener, KodeinAware, View.OnClickListener, com.google.android.gms.location.LocationListener{
    private lateinit var binding: ActivityClockInShiftBinding
    private lateinit var viewModel: ClockShiftViewModel
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

    /**
     * Represents a geographical location.
     */
    protected var mLastLocation: Location? = null

    private var mLatitudeLabel: String? = null
    private var mLongitudeLabel: String? = null

    override val kodein: Kodein by kodein()
    lateinit var sessionManager: SessionManager
    private val factory: ClockShiftViewModelFactory by instance()

    override fun onLocationChanged(location: Location?) {
        // You can now create a LatLng Object for use with maps
        // val latLng = LatLng(location.latitude, location.longitude)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_clock_in_shift)

        viewModel = ViewModelProvider(this, factory).get(ClockShiftViewModel::class.java)
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
            val intent = Intent(this, FacilityProfileScreen::class.java)
            intent.putExtra("facility_id", id)
            startActivity(intent)
        }
        binding.back.setOnClickListener { finish() }
        binding.clockIn.setOnClickListener(this)
        binding.camera.setOnClickListener(this)
    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onSuccess(response: SignResponse) {
        binding.progressBar.hide()

        if(response.success && response.code.toInt() == 200) {
            val intent = Intent(this, ClockInDetail::class.java)
            intent.putExtra("clocked_in", response.clocked_in_at)
            intent.putExtra("break_time", response.break_time)
            intent.putExtra("id", id)
            intent.putExtra("time_esclipsed", response.time_eclipsed)
            intent.putExtra("shift_detail", dataItem)
            startActivity(intent)
        }else{
            toast(response.msg)
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
        if(view!!.id == R.id.clock_in){
            if(!binding.camerabox.isChecked){
                toast("Please select Camera box")
                return
            }else if(!binding.locationbox.isChecked){
                toast("Please select current location")
                return
            } else if(profile_pic.isNullOrEmpty()){
                toast("Please select Camera Image")
                return
            }
            else{
                lat = "28.7041"
                longt = "77.1025"
                viewModel.clockIn(header, ClockInShiftPost(id!!,profile_pic!!,lat!!,longt!! ))
            }


        }else if(view!!.id == R.id.camera){
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            binding.camera.setImageURI(fileUri)
            profile_pic = "https://i.picsum.photos/id/658/200/300.jpg?hmac=K1TI0jSVU6uQZCZkkCMzBiau45UABMHNIqoaB9icB_0"
            //  toast(profile_pic.toString())
            //You can get File object from intent
            val file: File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            val filePath:String = ImagePicker.getFilePath(data)!!
        }
        else if (resultCode == ImagePicker.RESULT_ERROR) {
            toast( ImagePicker.getError(data))
        } else {
            toast("Task Cancelled")
        }
    }

}

