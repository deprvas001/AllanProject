package com.development.allanproject.views.activity.ui.taxholding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.TaxHoldingAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityTaxHoldingScreenBinding
import com.development.allanproject.model.commonapi.EHRSDataType
import com.development.allanproject.model.commonapi.FacilityType
import com.development.allanproject.model.reference.ReferenceData
import com.development.allanproject.model.taxholding.GetTaxHolding
import com.development.allanproject.model.taxholding.TaxData
import com.development.allanproject.util.hide
import com.development.allanproject.util.show
import com.development.allanproject.util.snackbar
import com.development.allanproject.util.taxListener.TaxAuthListener
import com.development.allanproject.util.toast
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class TaxHoldingScreen : AppCompatActivity() , TaxAuthListener, KodeinAware {
    private lateinit var binding: ActivityTaxHoldingScreenBinding
    private lateinit var viewModel: TaxHoldingViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
    var adapter: TaxHoldingAdapter? = null
    var ehrsList: ArrayList<EHRSDataType> = ArrayList()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var reference_select: FacilityType? = null
    var dataList: ArrayList<TaxData> = ArrayList()
    override val kodein by kodein()

    var dataItem: ReferenceData? = null
    var isAdd: Boolean = false
    var facilityList: ArrayList<FacilityType> = ArrayList()
    private val factory: TaxHoldingViewModelFactory by instance()
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tax_holding_screen)
        viewModel = ViewModelProvider(this, factory).get(TaxHoldingViewModel::class.java)
        viewModel.taxAuthListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id", "12")
        header.set(
            "Authorization","eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjAwOTQ4NzU4fQ.qW95drJZcLbQLdwtsCF7JRk6dEcTkbcVINFgT_dzv-w"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        binding.back.setOnClickListener {
            finish()
        }
       setAdapter()
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: GetTaxHolding) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code == 200) {
          if(response.data.size>0){
              dataList.clear()
              for(taxdata in response.data){
                  dataList.add(taxdata)
              }
              adapter!!.notifyDataSetChanged()
          }
        } else {
            toast("Try Later")
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    private fun setAdapter() {

        adapter = TaxHoldingAdapter( dataList,this)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        viewModel.getTax(header,"22")

    }
}
