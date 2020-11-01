package com.development.allanproject.views.activity.ui.facilityprofile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.SocailAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityFacilityProfileScreenBinding
import com.development.allanproject.model.commonapi.EHRSDataType
import com.development.allanproject.model.ehrs.EHRSData
import com.development.allanproject.model.facilityprofileModel.GetFacilityProfile
import com.development.allanproject.model.facilityprofileModel.Social
import com.development.allanproject.model.openshiftModel.PostBookmark
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.facilityprofileListener.FacilityProfileAuthListener
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class FacilityProfileScreen : AppCompatActivity() ,AuthListener, FacilityProfileAuthListener, KodeinAware {
    private lateinit var binding:ActivityFacilityProfileScreenBinding
    private lateinit var viewModel: FacilityProfileViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
    var adapter: SocailAdapter? = null
    var ehrsList: ArrayList<EHRSDataType> = ArrayList()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var ehrsId: Int? = null
    var dataList: ArrayList<Social> = ArrayList()
//    override val kodein by kodein()
     override val kodein by kodein()
    var dataItem: GetFacilityProfile? = null
    var isAdd: Boolean = false

    private val factory: FacilityProfileViewModelFactory by instance()
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_facility_profile_screen)
        viewModel = ViewModelProvider(this, factory).get(FacilityProfileViewModel::class.java)
        viewModel.facilityAuthListener = this
        viewModel.authListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()

        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id", "22")
        header.set(
            "Authorization","eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjAzOTUxMDA0fQ.gu5uLksY_2dCvxMHVft7o2fKKV4ciXR30-DUMEHu4dw"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        setAdapter()

        if(intent.extras !=null){
            viewModel.getFacilityProfile(header,intent!!.extras!!.get("facility_id").toString())
        }


        binding.favourite.setOnClickListener {
            if(!isAdd){
                isAdd = true
            }else{
                isAdd = false
            }
            val post = PostBookmark(1,isAdd)
            viewModel.postBookmark(header,post)
        }

        binding.back.setOnClickListener {
            finish()
        }

        binding.linked.setOnClickListener {
            val uri = Uri.parse(dataItem!!.facility.linkedin)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.facebook.setOnClickListener {
            val uri = Uri.parse(dataItem!!.facility.facebook)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.insta.setOnClickListener {
            val uri = Uri.parse(dataItem!!.facility.instagram)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        binding.twitter.setOnClickListener {
            val uri = Uri.parse(dataItem!!.facility.twitter)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(response: GetFacilityProfile) {
        progress_bar.hide()
        if(response.success && response.status.equals("ok") && response.code == 200){
             dataItem = response
             binding.name.setText(response.facility.name)
             binding.facilityType.setText(response.facility.name)
             binding.noOfBeds.setText(response.facility.bed_count)
             binding.color.setText(response.facility.scrub_color)
             binding.freeParking.setText(response.facility.free_parking.toString())
             binding.parentCompany.setText(response.facility.parent_company)
             isAdd = response.facility.marked_favorite

            if(response.facility.marked_favorite){
                binding.favourite.setColorFilter(ContextCompat.getColor(
                    this, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
            }

            if(response.facility.rating!=null){
                val rating: Float = response.facility.rating.toFloat()
                binding.rating.setRating(rating)
            }

            if(response.facility.img_url !=null){
                Util.loadImage(
                    binding.profile,response.facility.img_url,
                    Util.getCircularDrawable(this)
                )
            }

            if(response.facility.is_verified){
               binding.verified.setColorFilter(ContextCompat.getColor(
                   this, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
            }

            if(response.facility.blocked) {
                binding.blocked.visibility = View.VISIBLE
            }

            if(response.facility.marked_favorite){
                binding.favourite.setColorFilter(ContextCompat.getColor(
                    this, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
            }

            if (response.facility.bg_url != null) {
                Util.loadImage(
                    binding.banner,response.facility.bg_url,
                    Util.getCircularDrawable(this)
                )
            }


            if (response.facility.img_url != null) {
                Util.loadImage(
                    binding.profile,response.facility.img_url,
                    Util.getCircularDrawable(this)
                )
            }



//            if(response.facility.social.size>0){
//                dataList.clear()
//                for(data in response.facility.social){
//                    dataList.add(data)
//                }
//
//                adapter!!.notifyDataSetChanged()
//            }
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    private fun setAdapter(){
        adapter = SocailAdapter(this, dataList)
        mLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if(response.success){
            if(isAdd){
                binding.favourite.setColorFilter(ContextCompat.getColor(
                this, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
            }else{
                binding.favourite.setColorFilter(ContextCompat.getColor(
                    this, R.color.text_color_hint), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        }
    }
}
