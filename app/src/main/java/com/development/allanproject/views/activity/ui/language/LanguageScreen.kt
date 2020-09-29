package com.development.allanproject.views.activity.ui.language

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.EditSpecialityAdapter
import com.development.allanproject.adapter.LanguageAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityLanguageScreen2Binding
import com.development.allanproject.model.commonapi.Speciality
import com.development.allanproject.model.lanugage.GetLanugage
import com.development.allanproject.model.lanugage.LanguageData
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.model.speciality.Data
import com.development.allanproject.util.*
import com.development.allanproject.util.lanugageListener.LanguageAuthListener
import com.development.allanproject.views.activity.ui.backgroundinformation.BackInformationViewModelFactory
import com.development.allanproject.views.activity.ui.backgroundinformation.BackgroundInformationViewModel
import com.development.allanproject.views.activity.ui.signup.SignUp
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LanguageScreen : AppCompatActivity(), LanguageAuthListener, AuthListener, KodeinAware,
    View.OnClickListener, LanguageAdapter.LanguageCallBack{
    private lateinit var binding: ActivityLanguageScreen2Binding
    private lateinit var viewModel: LanguageViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    override val kodein by kodein()
    private val factory: LanguageViewModelFactory by instance()
    lateinit var sessionManager: SessionManager
    var adapter: LanguageAdapter? = null
    var language_selected:String?=null
    var proficency_selected:String?=null
    var dataList: ArrayList<LanguageData> = ArrayList()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var isAdd:Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language_screen2)

        viewModel = ViewModelProvider(this, factory).get(LanguageViewModel::class.java)
        viewModel.authListener = this
        viewModel.langAuthListener = this

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
        binding.back.setOnClickListener(this)

        binding.add.setOnClickListener {
            isAdd = true
            var language: HashMap<String, Any> = HashMap<String, Any>()
            language.put("language", language_selected!!)
            language.put("proficiency", proficency_selected!!)
            viewModel.addLanguage(header, language!!,"create")
        }

        binding.languageSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                 language_selected = parent.selectedItem.toString()
                toast(parent.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        binding.proficencySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                 proficency_selected = parent.selectedItem.toString()
                toast(parent.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code.equals("200")) {
            viewModel.getLangInfo(header)
        } else {
            toast(response.msg)
        }
    }


    override fun onSuccess(response: GetLanugage) {
        progress_bar.hide()
        if (response.code == 200 && response.success && response.status.equals("ok")) {
            if (response.additional.languages.size > 0) {
                val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                    this,
                    android.R.layout.simple_spinner_item,
                    response.additional.languages
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.languageSpinner.setAdapter(adapter)
            }
            if (response.additional.proficiency.size > 0) {
                val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                    this,
                    android.R.layout.simple_spinner_item,
                    response.additional.proficiency
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.proficencySpinner.setAdapter(adapter)
            }

            if (response.data.size > 0) {
                dataList.clear()
                for (languageData in response.data) {
                    dataList.add(languageData)
                }
                adapter!!.notifyDataSetChanged()
            }

        }
    }

    override fun onClick(view: View?) {
        if(view!!.id == R.id.back){
            finish()
        }
    }

    private fun setAdapter() {
        adapter = LanguageAdapter(this, dataList,this)
        mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(mLayoutManager)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.setAdapter(adapter)

        viewModel.getLangInfo(header)
    }

    override fun listenerMethod(data: LanguageData?) {
        if (data != null) {
            isAdd = false
            var hashMap:HashMap<String,Any> = HashMap()
            hashMap.put("id", data.id)
            viewModel.addLanguage(header, hashMap,"delete")
        }
    }
}
