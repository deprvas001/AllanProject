package com.development.allanproject.views.activity.ui.socialsecurity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.R
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivitySocailSecurityBinding
import com.development.allanproject.databinding.ActivitySocialSecurityScreenBinding
import com.development.allanproject.model.adddocumentModel.GetDocumentSpinner
import com.development.allanproject.model.adddocumentModel.MyDocumentData
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.model.socialsecurity.SecurityData
import com.development.allanproject.util.*
import com.development.allanproject.util.documentListener.DocumentListener
import com.development.allanproject.views.activity.ui.adddoucment.AddDocumentViewModel
import com.development.allanproject.views.activity.ui.adddoucment.AddDocumentViewModelFactory
import com.development.allanproject.views.activity.ui.uploadid.UploadIDs
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.File

class SocialSecurityScreen : AppCompatActivity() , AuthListener,
    KodeinAware, View.OnClickListener{
    private lateinit var binding: ActivitySocialSecurityScreenBinding
    var header: HashMap<String, String> = HashMap<String, String>()
    private lateinit var viewModel: SocailSecurityViewModel
    override val kodein by kodein()
    var uploadFront: String?=null
    var uploadBack: String?= null
    var doc_type:Int?=null
    var selected_document: SecurityData?=null
    var selected_doc_type:String?=null
    private val factory: SocialSecurityFactory by instance()
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_social_security_screen)

        viewModel = ViewModelProvider(this, factory).get(SocailSecurityViewModel::class.java)
        viewModel.authListener = this

        sessionManager = SessionManager(this)
        val user: java.util.HashMap<String, String> = sessionManager.getUserDetails()
        toast(user.toString())
        var user_id = user[SessionManager.KEY_USERID]
        var token = user[SessionManager.KEY_TOKEN]


        header.set("user_id", user_id!!)
        header.set(
            "Authorization", token!!
        )
        header.set("device_type_id", "1")
        header.set("v_code", "7")

        binding.uploadBack.setOnClickListener(this)
        binding.uploadFront.setOnClickListener(this)
        binding.addDocument.setOnClickListener(this)

        getIntentInformation()

    }

    override fun onStarted() {
        progress_bar.show()
        toast("Login Started")
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if(response.success && response.status.equals("ok") && response.code.toInt() == 200 ){
           toast("Added Successfully")
            startActivity(Intent(this, SocialSecurityList::class.java))
            finish()
        }else{
            toast(response.msg)
        }
    }


    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    override fun onClick(view: View?) {
        if(view!!.id == R.id.add_document){
            uploadDoc()
        }else if(view!!.id == R.id.upload_front){
            doc_type  =1
            uploadImage()
        }else if(view!!.id == R.id. upload_back){
            doc_type = 2
            uploadImage()
        }
    }

    private fun uploadImage(){
        ImagePicker.with(this)
            .crop()	    			//Crop image(Optional), Check Customization for more option
            .compress(1024)			//Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            if(doc_type == 1){
                binding.uploadFront.setImageURI(fileUri)
                uploadFront = "https://i.picsum.photos/id/658/200/300.jpg?hmac=K1TI0jSVU6uQZCZkkCMzBiau45UABMHNIqoaB9icB_0"
            }else{
                binding.uploadBack.setImageURI(fileUri)
                uploadBack = "https://dummyimage.com/600x400/000/fff"
            }

            val file: File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            val filePath:String = ImagePicker.getFilePath(data)!!
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            toast( ImagePicker.getError(data))
        } else {
            toast("Task Cancelled")
        }
    }

    private fun uploadDoc(){
        if(binding.documentNo.text.isNullOrEmpty()){
            toast("Add Document Name")
            return
        }else if(uploadFront.isNullOrEmpty()){
            toast("Please add front photo")
            return
        }else if(uploadBack.isNullOrEmpty()){
            toast("Please add back Photo")
            return
        }else{
            var detail:HashMap<String,Any> = HashMap()

            detail.put("name", binding.documentNo.text.toString())
            detail.put("doc_front", uploadFront!!)
            detail.put("doc_back", uploadBack!!)

            if(intent.extras!=null){
                detail.put("id", selected_document!!.id)
                viewModel.addDocument(header,detail,"update")
            }else{
                viewModel.addDocument(header,detail,"create")
            }


            /*if(intent.extras !=null){
                detail.put("id", selected_document!!.id)
                viewModel.addDocument(header,detail,"update")

            }else{
                viewModel.addDocument(header,detail,"create")
            }*/

        }
    }

    private fun getIntentInformation(){
        if(intent.extras!=null){
                selected_document = intent.getParcelableExtra<SecurityData>("select_doc")
                binding.documentNo.setText(selected_document!!.name)
                if(!selected_document!!.doc_front.isNullOrEmpty()){
                    uploadFront = selected_document!!.doc_front
                    Util.loadImage(
                        binding.uploadFront, selected_document!!.doc_front,
                        Util.getCircularDrawable(this)
                    )
                }

                if(!selected_document!!.doc_back.isNullOrEmpty()){
                    uploadBack = selected_document!!.doc_back
                    Util.loadImage(
                        binding.uploadBack, selected_document!!.doc_back,
                        Util.getCircularDrawable(this)
                    )
                }

        }
    }
}
