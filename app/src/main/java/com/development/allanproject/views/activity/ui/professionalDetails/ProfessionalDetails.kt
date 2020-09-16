package com.development.allanproject.views.activity.ui.professionalDetails

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.AppointmentDateAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityProfessionalDetailsBinding
import com.development.allanproject.databinding.ActivityProfileSummaryScreenBinding
import com.development.allanproject.model.profileSummary.ProfileSummaryGet
import com.development.allanproject.util.ProfileSummaryAuthListener
import com.development.allanproject.util.hide
import com.development.allanproject.util.show
import com.development.allanproject.util.toast
import com.development.allanproject.views.activity.ui.profileSummary.ProfileSumViewModelFactory
import com.development.allanproject.views.activity.ui.profileSummary.ProfileSummaryViewModel
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ProfessionalDetails : AppCompatActivity(),
    ProfileSummaryAuthListener, KodeinAware, View.OnClickListener
{
    private lateinit var binding: ActivityProfessionalDetailsBinding
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
        binding = DataBindingUtil.setContentView(this,R.layout.activity_professional_details)
        viewModel = ViewModelProvider(this, factory).get(ProfileSummaryViewModel::class.java)
        viewModel.authListener = this
        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id", "22")
        header.set(
            "Authorization","eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjAwMzI2Nzg2fQ.vaiRJiTisqb89tiQJqg3t0rubigehfUnXIPtOife52k"
        )
        header.set("device_type_id", "1")
        header.set("device_type", "Android")
        header.set("device_token", "XSDJHSHHFKJJSFLH")
        header.set("v_code", "7")

        viewModel.getProfileSummary(header,"professional")
        binding.back.setOnClickListener(this)
        binding.editLicense.setOnClickListener(this)

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
                        if(data.step_no.equals("3")){
                            binding.licenseText.setText((data.percentage*100).toInt().toString()+"% Completed")
                            binding.licenseBar.setProgressTintList(ColorStateList.valueOf((Color.parseColor(data.color_code))));
                            binding.licenseBar.setProgress((data.percentage*100).toInt())
                        }else if(data.step_no.equals("4")){
                            binding.specialityText.setText((data.percentage*100).toInt().toString()+"% Completed")
                            binding.specailityProgress.setProgressTintList(ColorStateList.valueOf((Color.parseColor(data.color_code))));
                            binding.specailityProgress.setProgress((data.percentage*100).toInt())
                        }else if(data.step_no.equals("5")){
                            binding.certificateText.setText((data.percentage*100).toInt().toString()+"% Completed")
                            binding.certificateBar.setProgressTintList(ColorStateList.valueOf((Color.parseColor(data.color_code))));
                            binding.certificateBar.setProgress((data.percentage*100).toInt())
                        }else if(data.step_no.equals("16")){
                            binding.referenceText.setText((data.percentage*100).toInt().toString()+"% Completed")
                            binding.referenceBar.setProgressTintList(ColorStateList.valueOf((Color.parseColor(data.color_code))));
                            binding.referenceBar.setProgress((data.percentage*100).toInt())
                        }else if(data.step_no.equals("17")){
                            binding.healthText.setText((data.percentage*100).toInt().toString()+"% Completed")
                            binding.healthBar.setProgressTintList(ColorStateList.valueOf((Color.parseColor(data.color_code))));
                            binding.healthBar.setProgress((data.percentage*100).toInt())
                        }
                    }

                    if(response.optional.size>0){
                        for(data in response.optional){
                            if(data.step_no.equals("18")){
                                binding.skillsText.setText((data.percentage*100).toInt().toString()+"% Completed")
                                binding.skillsBar.setProgressTintList(ColorStateList.valueOf((Color.parseColor(data.color_code))));
                                binding.skillsBar.setProgress((data.percentage*100).toInt())
                            }else if(data.step_no.equals("19")){
                                binding.compitancyText.setText((data.percentage*100).toInt().toString()+"% Completed")
                                binding.compitancyBar.setProgressTintList(ColorStateList.valueOf((Color.parseColor(data.color_code))));
                                binding.compitancyBar.setProgress((data.percentage*100).toInt())
                            }else if(data.step_no.equals("20")){
                                binding.trainingText.setText((data.percentage*100).toInt().toString()+"% Completed")
                                binding.trainingBar.setProgressTintList(ColorStateList.valueOf((Color.parseColor(data.color_code))));
                                binding.trainingBar.setProgress((data.percentage*100).toInt())
                            }else if(data.step_no.equals("21")){
                                binding.ehrsText.setText((data.percentage*100).toInt().toString()+"% Completed")
                                binding.ehrsBar.setProgressTintList(ColorStateList.valueOf((Color.parseColor(data.color_code))));
                                binding.ehrsBar.setProgress((data.percentage*100).toInt())
                            }
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
      if(view!!.id == R.id.back){
          finish()
      }else if(view!!.id == R.id.edit_license){

      }
    }
}
