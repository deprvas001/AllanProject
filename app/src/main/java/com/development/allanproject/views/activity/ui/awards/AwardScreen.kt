package com.development.allanproject.views.activity.ui.awards

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
import com.development.allanproject.adapter.LanguageAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityAwardScreenBinding
import com.development.allanproject.model.award.AwardData
import com.development.allanproject.model.award.GetAward
import com.development.allanproject.model.lanugage.LanguageData
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.awardListener.AwardAuthListener
import com.development.allanproject.views.activity.ui.language.LanguageViewModel
import com.development.allanproject.views.activity.ui.language.LanguageViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AwardScreen : AppCompatActivity(), AuthListener, AwardAuthListener,KodeinAware,
    View.OnClickListener,AwardAdapter.AwardCallBack {
    private lateinit var binding: ActivityAwardScreenBinding
    private lateinit var viewModel: AwardViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    override val kodein by kodein()
    private val factory: AwardViewModelFactory by instance()
    lateinit var sessionManager: SessionManager
    var language_selected: String? = null
    var proficency_selected: String? = null
    var dataList: ArrayList<AwardData> = ArrayList()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var isAdd: Boolean? = false
    var adapter: AwardAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_award_screen)

        viewModel = ViewModelProvider(this, factory).get(AwardViewModel::class.java)
        viewModel.authListener = this
        viewModel.awardAuthListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]


        header.set("user_id", "22")
        header.set(
            "Authorization",
            "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjAxMzA5MjM2fQ.F8hL12fMrqOqnaiREMcJoNepI_lOwbwSF-Y2HgqKtdg"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        setAdapter()

        binding.add.setOnClickListener(this)

        binding.date.setOnClickListener(this)
        binding.back.setOnClickListener(this)

    }


    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: GetAward) {
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

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code.equals("200")) {
            viewModel.getAward(header)
        } else {
            toast(response.msg)
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }


    override fun onClick(view: View?) {
        if (view!!.id == R.id.add) {
            if (binding.honor.text.isNullOrEmpty()) {
                toast("Please add honor")
                return
            } else if (binding.organization.text.isNullOrEmpty()) {
                toast("Please add organization")
                return
            } else if (binding.date.text.isNullOrEmpty()) {
                toast("Please add date")
                return
            } else {
                var award: HashMap<String, Any> = HashMap()
                award.put("award", binding.honor.text.toString())
                award.put("organization", binding.organization.text.toString())
                award.put("award_date", binding.date.text.toString())
                viewModel.updateAward(header, award!!, "create")
            }

        } else if (view!!.id == R.id.date) {
            setDate()
        }else if (view!!.id == R.id.back) {
           finish()
        }
    }

    private fun setDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                binding.date.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth)
            }, year, month, day
        )
        //   mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.getDatePicker()
            .setMaxDate(System.currentTimeMillis() - 568025136000L)
        datePickerDialog.show()
    }

    private fun setAdapter() {
        adapter = AwardAdapter(this, dataList,this)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        viewModel.getAward(header)
    }

    override fun listenerMethod(data: AwardData?) {
        if (data != null) {
            isAdd = false
            var hashMap:HashMap<String,Any> = HashMap()
            hashMap.put("id", data.id)
            viewModel.updateAward(header,hashMap,"delete")
        }
    }
}
