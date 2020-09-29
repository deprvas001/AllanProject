package com.development.allanproject.views.activity.ui.backgroundinformation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.R
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityBackgroundInformationScreenBinding
import com.development.allanproject.model.backinformation.GetBackgroundData
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.backgronddata.BackgroundAuthListener
import com.development.allanproject.views.activity.ui.bankinfo.BankViewModel
import com.development.allanproject.views.activity.ui.bankinfo.BankViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class BackgroundInformationScreen : AppCompatActivity() ,BackgroundAuthListener, AuthListener, KodeinAware, View.OnClickListener {
    private lateinit var binding: ActivityBackgroundInformationScreenBinding
    private lateinit var viewModel: BackgroundInformationViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var dataList: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
    var radioHashMap1: HashMap<String,Any> = HashMap()
    var radioHashMap2: HashMap<String,Any> = HashMap()
    var radioHashMap3: HashMap<String,Any> = HashMap()
    var radioHashMap4: HashMap<String,Any> = HashMap()
    var isAdd: Boolean = false
    override val kodein by kodein()
    private val factory: BackInformationViewModelFactory by instance()
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_background_information_screen)

        viewModel = ViewModelProvider(this, factory).get(BackgroundInformationViewModel::class.java)
        viewModel.authListener = this
        viewModel.backgroundAuthListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]


        header.set("user_id", user_id!!)
        header.set(
            "Authorization", token!!
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        binding.btnUpdate.setOnClickListener(this)

        binding.radioGroupOne.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener
        { radioGroup, i ->

               radioHashMap1.clear()
               radioHashMap1.put("id",1)
               if(i==R.id.radio1_1){
                   radioHashMap1.put("value","Yes")
               }else{
                   radioHashMap1.put("value","No")
               }

        })

        binding.radioGroupTwo.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener
            { radioGroup, i ->
                radioHashMap2.clear()
                radioHashMap2.put("id",2)
                if(i==R.id.radio2_1){
                    radioHashMap2.put("value","Yes")
                }else{
                    radioHashMap2.put("value","No")
                }
            })

        binding.radioGroupThree.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener
            { radioGroup, i ->
                radioHashMap3.clear()
                radioHashMap3.put("id",3)
                if(i==R.id.radio3_1){
                    radioHashMap3.put("value","Yes")
                }else{
                    radioHashMap3.put("value","No")
                }
            })

        binding.radioGroupFour.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener
            { radioGroup, i ->
                radioHashMap4.clear()
                radioHashMap4.put("id",4)
                if(i==R.id.radio4_1){
                    radioHashMap4.put("value","Yes")
                }else{
                    radioHashMap4.put("value","No")
                }

            })

        viewModel.getBackInfo(header)

        binding.back.setOnClickListener(this)
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code.equals("200")) {
            toast("Successfully Added")
        } else {
            toast(response.msg)
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    override fun onClick(view: View?) {
        if(view!!.id == R.id.btn_update){
            dataList.clear()
            if(!radioHashMap1.isEmpty()){
                dataList.add(radioHashMap1)
            }
            if(!radioHashMap2.isEmpty()){
                dataList.add(radioHashMap2)
            }
            if(!radioHashMap3.isEmpty()){
                dataList.add(radioHashMap3)
            }
            if(!radioHashMap4.isEmpty()){
                dataList.add(radioHashMap4)
            }
            if(!dataList.isEmpty()){
                viewModel.updateBackInfo(header, dataList!!)
            }else{
                toast("Choose the answer")
            }
        }else if(view!!.id == R.id.back){
            finish()
        }
    }

    override fun onSuccess(response: GetBackgroundData) {
        progress_bar.hide()
        if(response.code == 200 && response.status.equals("ok") && response.success){
             if(response.data.size>0){
               for (data in response.data){
                   if(data.id == 1){
                       if(data.value.equals("Yes")){
                           binding.radio11.isChecked = true
                       }else if(data.value.equals("No")){
                           binding.radio12.isChecked = true
                       }
                   }else if(data.id == 2){
                       if(data.value.equals("Yes")){
                           binding.radio21.isChecked = true
                       }else if(data.value.equals("No")){
                           binding.radio22.isChecked = true
                       }
                   }else if(data.id == 3){
                       if(data.value.equals("Yes")){
                           binding.radio31.isChecked = true
                       }else if(data.value.equals("No")){
                           binding.radio32.isChecked = true
                       }
                   }else if(data.id == 4){
                       if(data.value.equals("Yes")){
                           binding.radio41.isChecked = true
                       }else if(data.value.equals("No")){
                           binding.radio42.isChecked = true
                       }
                   }
               }
             }
        }
    }
}
