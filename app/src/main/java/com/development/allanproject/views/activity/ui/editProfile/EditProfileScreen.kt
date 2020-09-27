package com.development.allanproject.views.activity.ui.editProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.EditProfileAdapter
import com.development.allanproject.adapter.ResearchAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityEditProfileScreenBinding
import com.development.allanproject.model.editProfile.EditData
import com.development.allanproject.model.editProfile.GetEditProfile
import com.development.allanproject.model.research.GetResearch
import com.development.allanproject.model.research.ResearchData
import com.development.allanproject.util.*
import com.development.allanproject.util.editListener.EditListener
import com.development.allanproject.views.activity.ui.research.ResearchViewModel
import com.development.allanproject.views.activity.ui.research.ResearchViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class EditProfileScreen : AppCompatActivity(), EditListener, KodeinAware,
    View.OnClickListener {
   private lateinit var binding: ActivityEditProfileScreenBinding
    private lateinit var viewModel: EditProfileViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    override val kodein by kodein()
    private val factory: EditProfileViewModelFactory by instance()
    lateinit var sessionManager: SessionManager
    var language_selected: String? = null
    var proficency_selected: String? = null
    var dataList: ArrayList<EditData> = ArrayList()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var isAdd: Boolean? = false
    var adapter: EditProfileAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile_screen)
        viewModel = ViewModelProvider(this, factory).get(EditProfileViewModel::class.java)
        viewModel.editAuthListener= this

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

    }

    private fun setAdapter() {
        adapter =EditProfileAdapter(this, dataList)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        viewModel.getProfile(header)
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: GetEditProfile) {
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

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }


}
