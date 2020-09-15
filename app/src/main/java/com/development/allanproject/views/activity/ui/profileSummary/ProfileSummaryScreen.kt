package com.development.allanproject.views.activity.ui.profileSummary

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.AppointmentDateAdapter
import com.development.allanproject.adapter.AppointmentTimeAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityProfileSummaryBinding
import com.development.allanproject.databinding.ActivityProfileSummaryScreenBinding
import com.development.allanproject.model.appointmentModel.AppointmentData
import com.development.allanproject.model.appointmentModel.AppointmentDetail
import com.development.allanproject.model.profileSummary.ProfileSummaryGet
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.views.activity.ui.addexperience.workExperienceList.WorkExperienceList
import com.development.allanproject.views.activity.ui.appointment.AppointmentModelFactory
import com.development.allanproject.views.activity.ui.appointment.BoookAppointmentViewModel
import com.development.allanproject.views.activity.ui.editPersonalInfo.EditPersonalInfo
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ProfileSummaryScreen() : AppCompatActivity(),
    ProfileSummaryAuthListener, KodeinAware, View.OnClickListener{
    private lateinit var binding:ActivityProfileSummaryScreenBinding
    private val factory: ProfileSumViewModelFactory by instance()
    lateinit var sessionManager: SessionManager
    var header: HashMap<String, String> = HashMap()
    var adapter: AppointmentDateAdapter? = null
    var mLayoutManager: RecyclerView.LayoutManager? = null

    private lateinit var viewModel: ProfileSummaryViewModel
    override val kodein by kodein()
    val data: ArrayList<HashMap<String, HashMap<String,List<HashMap<String,String>>>>> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_summary_screen)

        viewModel = ViewModelProvider(this, factory).get(ProfileSummaryViewModel::class.java)
        viewModel.authListener = this
        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id", user_id!!)
        header.set(
            "Authorization","eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjAwMTcwNjI2fQ.LTgeY18K4hAt4J7yKwUBYOJoJ6Azny2K1cM2N8JUdTI"
        )
        header.set("device_type_id", "1")
        header.set("device_type", "Android")
        header.set("device_token", "XSDJHSHHFKJJSFLH")
        header.set("v_code", "7")

        viewModel.getProfileSummary(header,"summary")
        binding.editPersonalInfo.setOnClickListener(this)
        binding.editWork.setOnClickListener(this)
        binding.editEmployment.setOnClickListener(this)
    }


    override fun onStarted() {
        progress_bar.show()
        toast("Login Please Wait")
    }

    override fun onSuccess(response: ProfileSummaryGet) {
        progress_bar.hide()
        if(response.success){
            if(response.code == 200){
                if(response.status == "ok"){
                   for(data in response.data) {
                       if(data.name.equals("Profile Info")){
                            binding.profileText.setText((data.percentage*100).toInt().toString()+"% Completed")
                            binding.profileBar.setProgressTintList(ColorStateList.valueOf((Color.parseColor(data.color_code))));
                            binding.profileBar.setProgress((data.percentage*100).toInt())
                       }else if(data.name.equals("Work Experience")){
                           binding.workText.setText((data.percentage*100).toInt().toString()+"% Completed")
                           binding.workProgress.setProgressTintList(ColorStateList.valueOf((Color.parseColor(data.color_code))));
                           binding.workProgress.setProgress((data.percentage*100).toInt())
                       }else if(data.name.equals("Employment Details")){
                           binding.employeeText.setText((data.percentage*100).toInt().toString()+"% Completed")
                           binding.employmentBar.setProgressTintList(ColorStateList.valueOf((Color.parseColor(data.color_code))));
                           binding.employmentBar.setProgress((data.percentage*100).toInt())
                       }else if(data.name.equals("Education")){
                           binding.educationText.setText((data.percentage*100).toInt().toString()+"% Completed")
                           binding.educationBar.setProgressTintList(ColorStateList.valueOf((Color.parseColor(data.color_code))));
                           binding.educationBar.setProgress((data.percentage*100).toInt())
                       }else if(data.name.equals("Professional Details")){
                           binding.professionalText.setText((data.percentage*100).toInt().toString()+"% Completed")
                           binding.professionBar.setProgressTintList(ColorStateList.valueOf((Color.parseColor(data.color_code))));
                           binding.professionBar.setProgress((data.percentage*100).toInt())
                       }
                   }
                }
            }else if(response.code == 401){
                toast(response.msg)
            }else{
                toast(response.msg)
            }
        }else{
            toast(response.msg)
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        toast(message)
    }

    override fun onClick(view: View?) {
        if(view!!.id==R.id.edit_personal_info){
            startActivity(Intent(this, EditPersonalInfo::class.java))
        }else if(view!!.id==R.id.edit_work){
            startActivity(Intent(this, WorkExperienceList::class.java))
        }else if(view!!.id==R.id.edit_employment){
            startActivity(Intent(this, WorkExperienceList::class.java))
        }
    }
}
