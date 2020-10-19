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
import com.development.allanproject.adapter.MyShiftAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.FragmentMyShift2Binding
import com.development.allanproject.model.myshift.GetMyShift
import com.development.allanproject.model.myshift.ShiftItem
import com.development.allanproject.model.myshift.Shifts
import com.development.allanproject.util.hide
import com.development.allanproject.util.myshiftListener.MyShiftListener
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
class MyShiftFragment : Fragment(), MyShiftListener, KodeinAware {
    private lateinit var binding:FragmentMyShift2Binding
    private lateinit var viewModel: MyShiftViewModel
    var header: HashMap<String, String> = HashMap()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList()
    var adapter: MyShiftAdapter? = null
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var dataList: ArrayList<ShiftItem> = ArrayList()
    var myShiftList: ArrayList<ShiftItem> = ArrayList()
    var dataItem: Shifts? = null
    var isAdd: Boolean = false

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
        viewModel = ViewModelProvider(this, factory).get( MyShiftViewModel::class.java)
        viewModel.myShiftListener = this

        sessionManager = SessionManager(activity)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()

        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]

        header.set("user_id", user_id!!)
        header.set(
            "Authorization", token!!)
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        (activity as HomeScreen).filter.visibility = View.VISIBLE

        (activity as HomeScreen).filter.setOnClickListener {
                    showMaterialDialog()
        }
        setAdapter()
        binding.past.setOnClickListener {
            if(dataItem!=null){
                binding.today.setTextColor(resources.getColor(R.color.colorPrimary))
                binding.past.setTextColor(resources.getColor(android.R.color.white))
                binding.future.setTextColor(resources.getColor(R.color.colorPrimary))
                binding.past.setBackgroundResource(R.drawable.button_background);
                binding.today.setBackgroundResource(0);
                binding.future.setBackgroundResource(0);
                dataList.clear()
                if(dataItem!!.past.size>0){
                    binding.empty.visibility = View.GONE
                for(data in dataItem!!.past){
                    dataList.add(data)
                    setEvent(data)
                }
            }else{
                binding.empty.visibility = View.VISIBLE
            }
                adapter!!.notifyDataSetChanged()
            }
        }

        binding.today.setOnClickListener {
            if(dataItem!=null){
                binding.today.setTextColor(resources.getColor(android.R.color.white))
                binding.past.setTextColor(resources.getColor(R.color.colorPrimary))
                binding.future.setTextColor(resources.getColor(R.color.colorPrimary))

                binding.today.setBackgroundResource(R.drawable.button_background);
                binding.past.setBackgroundResource(0);
                binding.future.setBackgroundResource(0);
                dataList.clear()
                if(dataItem!!.today.size>0){
                    binding.empty.visibility = View.GONE
                for(data in dataItem!!.today){
                    dataList.add(data)
                    setEvent(data)
                }
            }else{
                binding.empty.visibility = View.VISIBLE
            }
                adapter!!.notifyDataSetChanged()
            }
        }

        binding.future.setOnClickListener {
            if(dataItem!=null){
                binding.today.setBackgroundResource(0);
                binding.today.setTextColor(resources.getColor(R.color.colorPrimary))
                binding.past.setTextColor(resources.getColor(R.color.colorPrimary))
                binding.past.setBackgroundResource(0);
                binding.future.setBackgroundResource(R.drawable.button_background);
                binding.future.setTextColor(resources.getColor(android.R.color.white))
                dataList.clear()

                if(dataItem!!.future.size>0){
                    binding.empty.visibility = View.GONE
                    for(data in dataItem!!.future){
                        dataList.add(data)
                        setEvent(data)
                    }
                }else{
                    binding.empty.visibility = View.VISIBLE
                }

                adapter!!.notifyDataSetChanged()
            }
        }
        return binding.root

    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onSuccess(response: GetMyShift) {
        binding.progressBar.hide()
        if(response.success && response.code == 200){
            dataList.clear()
            myShiftList.clear()

            if(response.shifts.today.size>0){
                binding.empty.visibility = View.GONE
                for(data in response.shifts.today){
                    dataList.add(data)
                    setEvent(data)
                }
            }else{
                binding.empty.visibility = View.VISIBLE
            }
            dataItem = response.shifts
            adapter!!.notifyDataSetChanged()
        }
    }

    override fun onFailure(message : String) {
        binding.progressBar.hide()
        root_layout.snackbar(message)
    }

    private fun setAdapter() {
        adapter = MyShiftAdapter(context, dataList)
        mLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        viewModel.getShift(header,"")
    }

    private fun showMaterialDialog() {
        val dialog = Dialog(requireContext())
        val window: Window? = dialog.getWindow()
        val wlp = window!!.attributes
        wlp.gravity = Gravity.TOP or Gravity.RIGHT
        wlp.width = ViewGroup.LayoutParams.MATCH_PARENT
        wlp.y=30
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
            viewModel.getShift(header,"applied")
            setTextViewBackground()
            dialog.dismiss()
        }

        saved.setOnClickListener {
            setTextViewBackground()
            viewModel.getShift(header,"saved")
            dialog.dismiss()
        }

        cancel.setOnClickListener {
            setTextViewBackground()
            viewModel.getShift(header,"canceled")
            dialog.dismiss()
        }

        offered.setOnClickListener {
            setTextViewBackground()
            viewModel.getShift(header,"offered")
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setTextViewBackground(){
        binding.today.setTextColor(resources.getColor(android.R.color.white))
        binding.past.setTextColor(resources.getColor(R.color.colorPrimary))
        binding.future.setTextColor(resources.getColor(R.color.colorPrimary))

        binding.today.setBackgroundResource(R.drawable.button_background);
        binding.past.setBackgroundResource(0);
        binding.future.setBackgroundResource(0);
    }

    private fun  setEvent(dataItem: ShiftItem){

//             if(dataItem.category.equals("event")){
//                 binding.cardEvent.visibility = View.VISIBLE
//                 binding.eventName.setText(dataItem.facility_name)
//                 binding.shiftName.setText(dataItem.name)
//                 binding.date.setText(dataItem.date)
//                 binding.time.setText(dataItem.time)
//             }else{
//                 binding.cardEvent.visibility = View.INVISIBLE
//             }

    }
}
