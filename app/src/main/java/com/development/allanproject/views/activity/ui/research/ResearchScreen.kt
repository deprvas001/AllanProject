package com.development.allanproject.views.activity.ui.research

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.AwardAdapter
import com.development.allanproject.adapter.ResearchAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityResearchScreen2Binding
import com.development.allanproject.model.award.AwardData
import com.development.allanproject.model.research.GetResearch
import com.development.allanproject.model.research.ResearchData
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.awardListener.AwardAuthListener
import com.development.allanproject.util.researchListener.ResearchAuthListener
import com.development.allanproject.views.activity.ui.awards.AwardViewModel
import com.development.allanproject.views.activity.ui.awards.AwardViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ResearchScreen : AppCompatActivity(), AuthListener, ResearchAuthListener, KodeinAware,
    View.OnClickListener, ResearchAdapter.ResearchCallBack {
     private lateinit var binding: ActivityResearchScreen2Binding
    private lateinit var viewModel: ResearchViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    override val kodein by kodein()
    private val factory: ResearchViewModelFactory by instance()
    lateinit var sessionManager: SessionManager
    var language_selected: String? = null
    var proficency_selected: String? = null
    var dataList: ArrayList<ResearchData> = ArrayList()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var isAdd: Boolean? = false
    var adapter: ResearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_research_screen2)

        viewModel = ViewModelProvider(this, factory).get(ResearchViewModel::class.java)
        viewModel.authListener = this
        viewModel.researchAuthListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]


        header.set("user_id", user_id!!)
        header.set(
            "Authorization",token!!
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        setAdapter()

        binding.back.setOnClickListener(this)
        binding.startDate.setOnClickListener(this)
        binding.endDate.setOnClickListener(this)
        binding.add.setOnClickListener(this)
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: GetResearch) {
        progress_bar.hide()
        if(response.code == 200 && response.success && response.status.equals("ok")){
            dataList.clear()
            if(response.data.size>0){
                for( data in response.data){
                    dataList.add(data)
                }
            }
            adapter!!.notifyDataSetChanged()
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code.equals("200")) {
            viewModel.getResearch(header)
        } else {
            toast(response.msg)
        }

    }

    private fun setAdapter() {
        adapter =ResearchAdapter(this, dataList,this)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        viewModel.getResearch(header)
    }

    override fun onClick(view: View?) {
       if(view!!.id ==R.id.end_date){
           setDate(2)
       }else if(view!!.id ==R.id.start_date){
           setDate(1)
       }else   if (view!!.id == R.id.add) {
           if (binding.address.text.isNullOrEmpty()) {
               toast("Please add address")
               return
           } else if (binding.organization.text.isNullOrEmpty()) {
               toast("Please add organization")
               return
           } else if (binding.endDate.text.isNullOrEmpty()) {
               toast("Please add end date")
               return
           } else if (binding.startDate.text.isNullOrEmpty()) {
               toast("Please add start date")
               return
           } else {
               var award: HashMap<String, Any> = HashMap()
               award.put("organization", binding.organization.text.toString())
               award.put("address", binding.address.text.toString())
               award.put("start_date", binding.startDate.text.toString())
               award.put("end_date", binding.endDate.text.toString())
               viewModel.updateResearch(header, award!!, "create")
           }

       }else if(view!!.id == R.id.back){
           finish()
       }
    }

    override fun listenerMethod(data: ResearchData?) {
        if (data != null) {
            isAdd = false
            var hashMap:HashMap<String,Any> = HashMap()
            hashMap.put("id", data.id)
            viewModel.updateResearch(header,hashMap,"delete")
        }
    }

    private fun setDate(position:Int) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
               if(position == 1){
                   binding.startDate.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth)
               }else{
                   binding.endDate.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth)
               }

            }, year, month, day
        )
        //   mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.getDatePicker()
            .setMaxDate(System.currentTimeMillis() - 568025136000L)
        datePickerDialog.show()
    }

}
