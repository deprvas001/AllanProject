package com.development.allanproject.views.activity.ui.training

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.development.allanproject.R
import com.development.allanproject.adapter.ReferenceListAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityTrainingScreenBinding
import com.development.allanproject.model.commonapi.EHRSDataType
import com.development.allanproject.model.commonapi.FacilityType
import com.development.allanproject.model.reference.ReferenceData
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.model.training.GetTrainingPdf
import com.development.allanproject.util.*
import com.development.allanproject.util.trainingListener.TrainingAuthListener
import com.development.allanproject.views.activity.ui.reference.ReferenceScreen
import com.development.allanproject.views.activity.ui.reference.ReferenceViewModel
import com.development.allanproject.views.activity.ui.reference.ReferenceViewModelFactory
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class TrainingScreen : AppCompatActivity(), TrainingAuthListener, KodeinAware
 {
     private lateinit var binding: ActivityTrainingScreenBinding
    private lateinit var viewModel: TrainingViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
    var adapter: ReferenceListAdapter? = null
    var ehrsList: ArrayList<EHRSDataType> = ArrayList()
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var reference_select: FacilityType? = null
    var dataList: ArrayList<ReferenceData> = ArrayList()
    override val kodein by kodein()
    var dataItem: ReferenceData? = null
    var isAdd: Boolean = false
    var facilityList: ArrayList<FacilityType> = ArrayList()
    private val factory: TrainingViewModelFactory by instance()
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_training_screen)

        viewModel = ViewModelProvider(this, factory).get(TrainingViewModel::class.java)
        viewModel.trainingAuthListener = this

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

        viewModel.getPdf(header, "20")
        binding.back.setOnClickListener {
            finish()
        }
    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

     override fun onSuccess(response: GetTrainingPdf) {
         progress_bar.hide()
         if (response.success && response.status.equals("ok") && response.code == 200) {
             binding.webView.webViewClient = WebViewClient()
             binding.webView.settings.setSupportZoom(true)
             binding.webView.getSettings().setBuiltInZoomControls(true);
             binding.webView.settings.javaScriptEnabled = true
             var pdf_url = response.additional
             binding.webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+pdf_url)

         } else {
             toast(response.msg)
         }
     }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}
