package com.development.allanproject.views.activity.ui.faq

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
import com.development.allanproject.adapter.FaqAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityFaqBinding
import com.development.allanproject.model.award.AwardData
import com.development.allanproject.model.award.GetAward
import com.development.allanproject.model.faq.FaqData
import com.development.allanproject.model.faq.GetFaqList
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.awardListener.AwardAuthListener
import com.development.allanproject.util.faqListener.FaqAuthlIstener
import com.development.allanproject.views.activity.ui.awards.AwardViewModel
import com.development.allanproject.views.activity.ui.awards.AwardViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class FaqActivity : AppCompatActivity(),  FaqAuthlIstener, KodeinAware{
    private lateinit var binding:ActivityFaqBinding
    private lateinit var viewModel: FaqViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    override val kodein by kodein()
    private val factory: FaqViewModelFactory by instance()
    lateinit var sessionManager: SessionManager
    var language_selected: String? = null
    var proficency_selected: String? = null
    var dataList: ArrayList<FaqData> = ArrayList()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var isAdd: Boolean? = false
    var adapter:FaqAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_faq)
        viewModel = ViewModelProvider(this, factory).get(FaqViewModel::class.java)
        viewModel.faqAuthListener = this

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

    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: GetFaqList) {
        progress_bar.hide()
        if(response.success ){
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

    private fun setAdapter() {
        adapter = FaqAdapter(this, dataList)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        viewModel.getFaq(header)
    }
}
