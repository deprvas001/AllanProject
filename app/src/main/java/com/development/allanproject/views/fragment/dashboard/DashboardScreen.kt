package com.development.allanproject.views.fragment.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.FutureShiftAdapter
import com.development.allanproject.adapter.MyShiftAdapter
import com.development.allanproject.adapter.PastShiftAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.FragmentDashboardScreenBinding
import com.development.allanproject.model.dashboardModel.GetDashboard
import com.development.allanproject.model.myshift.GetMyShift
import com.development.allanproject.model.myshift.ShiftItem
import com.development.allanproject.model.myshift.Shifts
import com.development.allanproject.model.openshiftModel.GetOpenShift
import com.development.allanproject.model.openshiftModel.GetOpenShiftList
import com.development.allanproject.util.dashboardListener.DashboardListener
import com.development.allanproject.util.hide
import com.development.allanproject.util.myshiftListener.MyShiftListener
import com.development.allanproject.util.openshiftListener.OpenShiftListener
import com.development.allanproject.util.show
import com.development.allanproject.util.snackbar
import com.development.allanproject.views.fragment.myshift.MyShiftViewModel
import com.development.allanproject.views.fragment.myshift.MyShiftViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class DashboardScreen : Fragment(), DashboardListener, KodeinAware {
    private lateinit var binding: FragmentDashboardScreenBinding
    private lateinit var viewModel: DashboardViewModel
    var header: HashMap<String, String> = HashMap()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList()
    var dataItem: Shifts? = null
    var isAdd: Boolean = false
    var shiftType: String? = null
    var filter: String? = ""

    override val kodein: Kodein by kodein()
    lateinit var sessionManager: SessionManager
    private val factory: DashboardViewModelFactory by instance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard_screen, container, false)
        viewModel = ViewModelProvider(this, factory).get(DashboardViewModel::class.java)
        viewModel.dashboardListener = this

        sessionManager = SessionManager(activity)
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

        viewModel.getDashboard(header)

        return binding.getRoot()

    }

    override fun onStarted() {
        binding.progressBar.show()
    }


    override fun onSuccess(response: GetDashboard) {
        binding.progressBar.hide()
        if (response.success && response.code == 200) {
            binding.weekLayout.shiftTakenValue.text = response.data.this_week.shift_taken
            binding.weekLayout.hoursWorkValue.text = response.data.this_week.hours_worked
            binding.weekLayout.earningValue.text = response.data.this_week.earning
            binding.weekLayout.lastWeekValue.text = response.data.this_week.last_week_earning

            binding.userLifeTime.lifeTimeValue.text = response.data.user_lifetime.hours_paid
            binding.userLifeTime.totalShiftValue.text = response.data.user_lifetime.total_shifts
            binding.userLifeTime.totalFacilityValue.text = response.data.user_lifetime.no_of_facilities
            binding.userLifeTime.shiftDeclineValue.text = response.data.user_lifetime.decline_shifts
            binding.userLifeTime.totalEarningValue.text = response.data.user_lifetime.total_earning

            binding.rewards.pointEarnedValue.text = response.data.rewards.point_earned
            binding.rewards.referalValue.text = response.data.rewards.referales
            binding.rewards.referalEarningValue.text = response.data.rewards.referal_earning

            binding.rating.rating = response.data.rating



        }
    }

    override fun onFailure(message: String) {
        binding.progressBar.hide()
        root_layout.snackbar(message)
    }

}
