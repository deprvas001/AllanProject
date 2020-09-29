package com.development.allanproject.views.activity.ui.taxholding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.model.taxholding.GetTaxHolding
import com.development.allanproject.model.taxholding.PostTaxList
import com.development.allanproject.model.taxholding.TaxData
import com.development.allanproject.util.*
import com.development.allanproject.util.taxListener.TaxAuthListener
import com.development.allanproject.views.activity.ui.SignatureScreen
import kotlinx.android.synthetic.main.activity_add_licenses.*
import kotlinx.android.synthetic.main.activity_personal_detail.*
import kotlinx.android.synthetic.main.activity_personal_detail.progress_bar
import kotlinx.android.synthetic.main.activity_personal_detail.root_layout
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class TaxHoldingScreen : AppCompatActivity() ,AuthListener, TaxAuthListener, KodeinAware , View.OnClickListener{
    private lateinit var binding: ActivityTaxHoldingScreenBinding
    private lateinit var viewModel: TaxHoldingViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
    var adapter: TaxHoldingAdapter? = null
    var additional :HashMap<String,String> = HashMap()
    var ehrsList: ArrayList<EHRSDataType> = ArrayList()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var reference_select: FacilityType? = null
    var dataList: ArrayList<TaxData> = ArrayList()
    override val kodein by kodein()
    var taxHolding: GetTaxHolding?=null
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
        viewModel.authListener = this

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

        binding.back.setOnClickListener {
            finish()
        }
       setAdapter()
        binding.signature.setOnClickListener(this)
        binding.update.setOnClickListener(this)
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if(response.success && response.status.equals("ok") && response.code.toInt() == 200){
              toast("Successfully Updated")
        }else{
            toast("Something went wrong")
        }
    }

    override fun onSuccess(response: GetTaxHolding) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code == 200) {
          if(response.data.size>0){
                 taxHolding = response
               binding.decleration.setText(response.additional.declaration)
//              taxHolding!!.additional = response.additional
//              taxHolding!!.data = dataList
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

    override fun onClick(view: View?) {
        if(view!!.id == R.id.update){
            Log.i("Testing", taxHolding.toString())
             additional.clear()
             additional.put("declaration",taxHolding!!.additional.declaration)
             additional.put("signature","https://i.picsum.photos/id/658/200/300.jpg?hmac=K1TI0jSVU6uQZCZkkCMzBiau45UABMHNIqoaB9icB_0")
             var details =  PostTaxList(additional,dataList)

            viewModel.updateTax(header, details)
        }else if(view!!.id == R.id.signature){
            startActivity(Intent(this, SignatureScreen::class.java))
        }
    }
}
