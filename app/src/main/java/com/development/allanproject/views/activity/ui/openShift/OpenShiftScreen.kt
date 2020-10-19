package com.development.allanproject.views.activity.ui.openShift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.OpenShiftAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityOpenShiftScreenBinding
import com.development.allanproject.model.ehrs.EHRSData
import com.development.allanproject.model.openshiftModel.GetOpenShift
import com.development.allanproject.model.openshiftModel.GetOpenShiftList
import com.development.allanproject.util.hide
import com.development.allanproject.util.openshiftListener.OpenShiftListener
import com.development.allanproject.util.show
import com.development.allanproject.util.snackbar
import com.development.allanproject.views.fragment.openshift.OpenShiftViewModel
import com.development.allanproject.views.fragment.openshift.OpenShiftViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class OpenShiftScreen : AppCompatActivity() , OpenShiftListener, KodeinAware {
    private lateinit var binding: ActivityOpenShiftScreenBinding
    private lateinit var viewModel: OpenShiftViewModel
    var header: HashMap<String, String> = HashMap()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList()
    var adapter: OpenShiftAdapter? = null
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var dataList: ArrayList<GetOpenShiftList> = ArrayList()
    var dataItem: EHRSData? = null
    var isAdd: Boolean = false

    override val kodein: Kodein by kodein()
    lateinit var sessionManager: SessionManager
    private val factory: OpenShiftViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_open_shift_screen)
        viewModel = ViewModelProvider(this, factory).get(OpenShiftViewModel::class.java)
        viewModel.openListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()

        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id", "22")
        header.set(
            "Authorization","eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjAyODI4MzYxfQ.o155y3aH47Kjlo8lN78Sub_w_RrA4ZCRG--_jbg9eSo"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

       setAdapter()
    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onSuccess(response: GetOpenShift) {
        binding.progressBar.hide()

        if(response.success && response.code == 200){
            dataList.clear()
            if(response.shifts.size>0){
                for(data in response.shifts){
                    dataList.add(data)
                }
                adapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onFailure(message: String) {
        binding.progressBar.hide()
        root_layout.snackbar(message)
    }

    private fun setAdapter() {
        adapter = OpenShiftAdapter(this, dataList)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        viewModel.getShift(header)
    }
}