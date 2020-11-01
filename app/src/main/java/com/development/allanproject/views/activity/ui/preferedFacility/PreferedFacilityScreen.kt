package com.development.allanproject.views.activity.ui.preferedFacility

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.HiddenJobAdapter
import com.development.allanproject.adapter.PreferedFacilityAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityPreferedFacilityScreenBinding
import com.development.allanproject.model.hiddenjobs.GetHiddenJobs
import com.development.allanproject.model.hiddenjobs.GetHiddenShift
import com.development.allanproject.model.openshiftModel.GetOpenShiftDetail
import com.development.allanproject.model.preferedfacility.FacilityData
import com.development.allanproject.model.preferedfacility.GetPreferedFacility
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.hide
import com.development.allanproject.util.preferedfacilityListener.PreferedFacilityListener
import com.development.allanproject.util.show
import com.development.allanproject.util.snackbar
import com.development.allanproject.views.activity.ui.hiddenjob.HiddenJobViewModel
import com.development.allanproject.views.activity.ui.hiddenjob.HiddenViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class PreferedFacilityScreen : AppCompatActivity(), AuthListener, PreferedFacilityListener,
    KodeinAware,
View.OnClickListener{
    private lateinit var binding:ActivityPreferedFacilityScreenBinding
    private lateinit var viewModel: PreferedFacilityViewModel
    var header: HashMap<String, String> = HashMap()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList()
    var adapter: PreferedFacilityAdapter? = null
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var dataList: ArrayList<FacilityData> = ArrayList()
    var dataItem: GetOpenShiftDetail? = null
    var isApply: Int = 0
    var reason: String? = null
    var id: Int? = 0;
    override val kodein: Kodein by kodein()
    lateinit var sessionManager: SessionManager
    private val factory: PreferedFacilityViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_prefered_facility_screen)

        viewModel = ViewModelProvider(this, factory).get(PreferedFacilityViewModel::class.java)
        viewModel.facilityListener = this
        viewModel.authListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()

        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id", "22")
        header.set(
            "Authorization",
            "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjA0MzAzNjcyfQ.QIsKj1LA9aiCp3fKm4to8Y8cDagOLeqDJCOZaiZfF8s"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        setAdapter()
    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onSuccess(response: GetPreferedFacility) {
        binding.progressBar.hide()
        if (response.success && response.code == 200) {
            dataList.clear()
            if (response.facilities.size > 0) {
                for (data in response.facilities) {
                    dataList.add(data)
                }

                adapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onSuccess(response: SignResponse) {
        TODO("Not yet implemented")
    }

    override fun onFailure(message: String) {
        binding.progressBar.hide()
        root_layout.snackbar(message)

    }

    private fun setAdapter() {
        adapter = PreferedFacilityAdapter(this, dataList)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        viewModel.getFacility(header)
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}
