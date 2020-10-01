package com.development.allanproject.views.activity.ui.prefrencescreen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.R
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityPreferenceScreenBinding
import com.development.allanproject.model.preferenceModel.GetPreferenceList
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.preferenceListener.PreferenceListener
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_sign_up.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class PreferenceScreen : AppCompatActivity(), AuthListener, PreferenceListener, KodeinAware,
    View.OnClickListener {
    private lateinit var binding: ActivityPreferenceScreenBinding
    private lateinit var viewModel: PreferenceViewModel
    lateinit var sessionManager: SessionManager
    var personalDetail: HashMap<String, Any> = HashMap()
    var header: HashMap<String, String> = HashMap()
    var profile_pic: String? = null

    override val kodein by kodein()
    private val factory: PreferenceViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preference_screen)
        viewModel = ViewModelProvider(this, factory).get(PreferenceViewModel::class.java)
        viewModel.preferencecAuthListener = this
        viewModel.authListener = this

        sessionManager = SessionManager(this)
        val user: HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]
        header["user_id"] = user_id!!
        header["Authorization"] = token!!
        header["device_type_id"] = "1"
        header["v_code"] = "7"

        viewModel.getPrefereneList(header)

        binding.save.setOnClickListener(this)
        binding.back.setOnClickListener { finish() }
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if (response.success && response.code.toInt() == 200 && response.status.equals("ok")) {
            toast("Update Successfully")
        } else {
            toast(response.msg)
        }
    }

    override fun onSuccess(response: GetPreferenceList) {
        progress_bar.hide()
        if (response.success && response.code == 200 && response.status.equals("ok")) {
            if (response.data.facilitiy_type.size > 0) {

                binding.facilityType.setItems(response.data.facilitiy_type)
                binding.facilityType.setSelection(response.data.facilitiy_type)

                binding.facilityName.setItems(response.data.facilitiy_name)
                binding.facilityName.setSelection(response.data.facilitiy_name)

                binding.spinnerAddress.setItems(response.data.address)
                binding.spinnerAddress.setSelection(response.data.address)

                binding.shiftSpinner.setItems(response.data.shift_type)
                binding.shiftSpinner.setSelection(response.data.shift_type)

                binding.daySpinner.setItems(response.data.days)
                binding.daySpinner.setSelection(response.data.days)

                binding.citySpinner.setItems(response.data.cities)
                binding.citySpinner.setSelection(response.data.cities)
            }
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    override fun onClick(view: View?) {
        if (view!!.id == R.id.save) {
            val facilityType: ArrayList<Int> = ArrayList()
            val facilityName: ArrayList<Int> = ArrayList()
            val shiftList: ArrayList<Int> = ArrayList()
            val dayList: ArrayList<Int> = ArrayList()
            val cityList: ArrayList<String> = ArrayList()
            val addressList: ArrayList<String> = ArrayList()


            if (binding.facilityType.selectedFacilitiyTypes.size > 0) {
                for (facility_type in binding.facilityType.selectedFacilitiyTypes)
                        facilityType.add(facility_type.id)

            }else{
                toast("Please select facility type")
                return
            }

            if(binding.facilityName.selectedFacilitiyTypes.size>0){
                for (facility_name in binding.facilityName.selectedFacilitiyTypes)
                        facilityName.add(facility_name.id)
            }else{
                toast("Please select facility name")
                return
            }

            if(binding.shiftSpinner.selectedFacilitiyTypes.size>0){
                for (shift in binding.shiftSpinner.selectedFacilitiyTypes)
                        shiftList.add(shift.id)

            }else{
                toast("Please select shift type")
                return
            }


            if(binding.daySpinner.selectedFacilitiyTypes.size>0){
                for (day in binding.daySpinner.selectedFacilitiyTypes)  {
                    dayList.add(day.id)
                }
            }else{
                toast("Please select day spinner")
                return
            }

            if(binding.spinnerAddress.selectedFacilitiyTypes.size>0){
                for (add in binding.spinnerAddress.selectedFacilitiyTypes)
                        addressList.add(add.name)

            }else{
                toast("Please select  address")
                return
            }

           if(binding.citySpinner.selectedFacilitiyTypes.size>0){
               for (city in binding.citySpinner.selectedFacilitiyTypes)
                       cityList.add(city.name)

           }else{
               toast("Please select city")
               return
           }

            val hashMap: HashMap<String, Any> = HashMap()
            val details: HashMap<String, Any> = HashMap()
            details.put("facilities", facilityType)
            details.put("facility_name", facilityName)
            details.put("shift_types", shiftList)
            details.put("location", cityList)
            details.put("address", addressList)
            details.put("days", dayList)

            hashMap.put("step_no", 34)
            hashMap.put("details", details)
            viewModel.updatePrefereneList(header,hashMap)
        }
    }
}
