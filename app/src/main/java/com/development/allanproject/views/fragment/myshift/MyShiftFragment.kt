package com.development.allanproject.views.fragment.myshift

import android.app.Dialog
import android.opengl.Visibility
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.FutureShiftAdapter
import com.development.allanproject.adapter.MyShiftAdapter
import com.development.allanproject.adapter.PastShiftAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.FragmentMyShift2Binding
import com.development.allanproject.model.myshift.GetMyShift
import com.development.allanproject.model.myshift.ShiftItem
import com.development.allanproject.model.myshift.Shifts
import com.development.allanproject.model.openshiftModel.GetOpenShift
import com.development.allanproject.model.openshiftModel.GetOpenShiftList
import com.development.allanproject.util.hide
import com.development.allanproject.util.myshiftListener.MyShiftListener
import com.development.allanproject.util.openshiftListener.OpenShiftListener
import com.development.allanproject.util.show
import com.development.allanproject.util.snackbar
import com.development.allanproject.views.activity.HomeScreen
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


/**
 * A simple [Fragment] subclass.
 */
class MyShiftFragment : Fragment(), MyShiftListener, OpenShiftListener,KodeinAware {
    private lateinit var binding: FragmentMyShift2Binding
    private lateinit var viewModel: MyShiftViewModel
    var header: HashMap<String, String> = HashMap()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList()
    var adapter: MyShiftAdapter? = null
    var pastAdapter: PastShiftAdapter? = null
    var futureAdapter: FutureShiftAdapter? = null
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var dataList: ArrayList<ShiftItem> = ArrayList()
    var futureDataList: ArrayList<GetOpenShiftList> = ArrayList()

    var myShiftList: ArrayList<ShiftItem> = ArrayList()
    var dataItem: Shifts? = null
    var isAdd: Boolean = false
    var shiftType:String? = null
    var filter:String?=""

    override val kodein: Kodein by kodein()
    lateinit var sessionManager: SessionManager
    private val factory: MyShiftViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // DataBindingUtil.inflate(inflater, R.layout.fragment_my_shift2, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_shift2, container, false)
        viewModel = ViewModelProvider(this, factory).get(MyShiftViewModel::class.java)
        viewModel.myShiftListener = this
        viewModel.openListener = this

        sessionManager = SessionManager(activity)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()

        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id", "22")
        header.set(
            "Authorization",
            "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMiwiZXhwIjoxNjA0MTM2Nzc2fQ.NYuL3bpj62Q8QGVlPSpjK0p6vG2ixEoRb0GepIDflws"
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        (activity as HomeScreen).filter.visibility = View.VISIBLE

        (activity as HomeScreen).filter.setOnClickListener {
            showMaterialDialog()
        }
        setAdapter()
        binding.past.setOnClickListener {

                binding.today.setTextColor(resources.getColor(R.color.colorPrimary))
                binding.past.setTextColor(resources.getColor(android.R.color.white))
                binding.future.setTextColor(resources.getColor(R.color.colorPrimary))
                binding.past.setBackgroundResource(R.drawable.button_background);
                binding.today.setBackgroundResource(0);
                binding.future.setBackgroundResource(0);
                dataList.clear()

                setPastAdapter()
        }

        binding.today.setOnClickListener {
                binding.today.setTextColor(resources.getColor(android.R.color.white))
                binding.past.setTextColor(resources.getColor(R.color.colorPrimary))
                binding.future.setTextColor(resources.getColor(R.color.colorPrimary))

                binding.today.setBackgroundResource(R.drawable.button_background);
                binding.past.setBackgroundResource(0);
                binding.future.setBackgroundResource(0);
                dataList.clear()

                setAdapter()
        }

        binding.future.setOnClickListener {
                binding.today.setBackgroundResource(0);
                binding.today.setTextColor(resources.getColor(R.color.colorPrimary))
                binding.past.setTextColor(resources.getColor(R.color.colorPrimary))
                binding.past.setBackgroundResource(0);
                binding.future.setBackgroundResource(R.drawable.button_background);
                binding.future.setTextColor(resources.getColor(android.R.color.white))
                futureDataList.clear()

               setFutureAdapter()

        }
        return binding.root

    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onSuccess(response: GetOpenShift) {
        binding.progressBar.hide()
        if(response.success && response.code == 200){
            futureDataList.clear()
            if(response.shifts.size>0){
                binding.empty.visibility = View.INVISIBLE
                for(data in response.shifts){
                    futureDataList.add(data)
                }
                futureAdapter!!.notifyDataSetChanged()
            }else{
                binding.empty.visibility = View.VISIBLE
            }
        }
    }

    override fun onSuccess(response: GetMyShift) {
        binding.progressBar.hide()
        if (response.success && response.code == 200) {
            dataList.clear()
            myShiftList.clear()

            if (response.shifts.size > 0) {
                 binding.empty.visibility = View.INVISIBLE
                for (data in response.shifts) {
                    dataList.add(data)
                  //  setEvent(data)
                }
            } else {
                  binding.empty.visibility = View.VISIBLE
            }
            if(shiftType!!.equals("past")){
                pastAdapter!!.notifyDataSetChanged()
            }else if(shiftType!!.equals("today")){
                adapter!!.notifyDataSetChanged()
            }
            //  dataItem = response.shifts

        }
    }

    override fun onFailure(message: String) {
        binding.progressBar.hide()
        root_layout.snackbar(message)
    }

    private fun setAdapter() {
        shiftType = "today"
        adapter = MyShiftAdapter(context, dataList)
        mLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)


        viewModel.getShift(header, shiftType!!,filter!!)
    }

    private fun setPastAdapter() {
        shiftType="past"
        pastAdapter = PastShiftAdapter(context, dataList)
        mLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(pastAdapter)
        viewModel.getShift(header, shiftType!!,filter!!)
    }

    private fun setFutureAdapter() {
        shiftType="future"
        futureAdapter = FutureShiftAdapter(context, futureDataList)
        mLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(futureAdapter)

        viewModel.getFutureShift(header, shiftType!!,filter!!)
    }

    private fun showMaterialDialog() {
        val dialog = Dialog(requireContext())
        val window: Window? = dialog.getWindow()
        val wlp = window!!.attributes
        wlp.gravity = Gravity.TOP or Gravity.RIGHT
        wlp.width = ViewGroup.LayoutParams.MATCH_PARENT
        wlp.y = 30
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window!!.attributes = wlp

//        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        window?.setGravity(Gravity.TOP )

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_filter_layout)
        val applied = dialog.findViewById(R.id.action_applied) as TextView
        val saved = dialog.findViewById(R.id.action_save) as TextView
        val cancel = dialog.findViewById(R.id.action_cancel) as TextView
        val offered = dialog.findViewById(R.id.action_offer) as TextView
        applied.setOnClickListener {
            filter = "applied"
            if(shiftType.equals("future")){
                viewModel.getFutureShift(header, shiftType!!,filter!!)
            }else{
                viewModel.getShift(header, shiftType!!,filter!!)
            }

           // setTextViewBackground()
            dialog.dismiss()
        }

        saved.setOnClickListener {
           // setTextViewBackground()
            filter = "saved"
            if(shiftType.equals("future")){
                viewModel.getFutureShift(header, shiftType!!,filter!!)
            }else{
                viewModel.getShift(header, shiftType!!,filter!!)
            }
            dialog.dismiss()
        }

        cancel.setOnClickListener {
           // setTextViewBackground()
            filter = "cancel"
            if(shiftType.equals("future")){
                viewModel.getFutureShift(header, shiftType!!,filter!!)
            }else{
                viewModel.getShift(header, shiftType!!,filter!!)
            }
            dialog.dismiss()
        }

        offered.setOnClickListener {
           // setTextViewBackground()
            filter = "offered"
            if(shiftType.equals("future")){
                viewModel.getFutureShift(header, shiftType!!,filter!!)
            }else{
                viewModel.getShift(header, shiftType!!,filter!!)
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setTextViewBackground() {
        binding.today.setTextColor(resources.getColor(android.R.color.white))
        binding.past.setTextColor(resources.getColor(R.color.colorPrimary))
        binding.future.setTextColor(resources.getColor(R.color.colorPrimary))

        binding.today.setBackgroundResource(R.drawable.button_background);
        binding.past.setBackgroundResource(0);
        binding.future.setBackgroundResource(0);
    }

}
