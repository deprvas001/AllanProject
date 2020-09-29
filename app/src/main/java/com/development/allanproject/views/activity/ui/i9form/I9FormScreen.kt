package com.development.allanproject.views.activity.ui.i9form

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.development.allanproject.R
import com.development.allanproject.adapter.TaxHoldingAdapter
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.ActivityI9FormScreenBinding
import com.development.allanproject.model.i9form.*
import com.development.allanproject.model.reference.ReferenceData
import com.development.allanproject.model.signupModel.SignResponse
import com.development.allanproject.util.*
import com.development.allanproject.util.i9formListener.I9FormListener
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_personal_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class I9FormScreen : AppCompatActivity(), View.OnClickListener, I9FormListener, AuthListener,
    KodeinAware {
    private lateinit var binding: ActivityI9FormScreenBinding
    var profile_pic: String? = ""
    var image_id: Int? = null
    var expiry_id: Int? = null
    var list_one_radio_id: Int? = 0
    var list_two_radio_id: Int? = 0
    var list_third_radio_id: Int? = 0
    var option_id: Int? = 0
    var dataList: ArrayList<I9FormPost> = ArrayList()
    var details: HashMap<String, Any> = HashMap()

    private lateinit var viewModel: I9FormViewModel
    var header: HashMap<String, String> = HashMap<String, String>()
    var hashMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
    var adapter: TaxHoldingAdapter? = null
    var additional: HashMap<String, String> = HashMap()
    override val kodein by kodein()

    var dataItem: ReferenceData? = null
    var isAdd: Boolean = false
    private val factory: I9FormViewModelFactory by instance()
    lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_i9_form_screen)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_i9_form_screen)
        viewModel = ViewModelProvider(this, factory).get(I9FormViewModel::class.java)
        viewModel.authListener = this
        viewModel.i9FormListener = this

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


        binding.listLayoutOne.oneExpiryDate.setOnClickListener(this)
        binding.listLayoutOne.oneUploadFront.setOnClickListener(this)
        binding.listLayoutOne.oneUploadBack.setOnClickListener(this)

        binding.listLayoutTwo.secondExpiryDate.setOnClickListener(this)
        binding.listLayoutTwo.secondUploadFront.setOnClickListener(this)
        binding.listLayoutTwo.secondUploadBack.setOnClickListener(this)

        binding.listLayoutThird.thirdExpiryDate.setOnClickListener(this)
        binding.listLayoutThird.thirdUploadFront.setOnClickListener(this)
        binding.listLayoutThird.thirdUploadBack.setOnClickListener(this)

        binding.btnUpdate.setOnClickListener(this)

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            if (i == R.id.radio1) {
                option_id = 1
            } else if (i == R.id.radio2) {
                option_id = 2
            } else if (i == R.id.radio3) {
                option_id = 3
            } else if (i == R.id.radio4) {
                option_id = 4
            }

        }


        binding.listLayoutOne.radioGroupListOne.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener
        { radioGroup, i ->

            if (i == R.id.radio1_1) {
                list_one_radio_id = 1
                toast("1")
            } else if (i == R.id.radio1_2) {
                list_one_radio_id = 2
            }
        })

        binding.listLayoutTwo.radioGroupListTwo.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener
        { radioGroup, i ->
            if (i == R.id.radio2_1) {
                list_two_radio_id = 1
            } else if (i == R.id.radio2_2) {
                list_two_radio_id = 2
            } else if (i == R.id.radio2_3) {
                list_two_radio_id = 3
            } else if (i == R.id.radio2_4) {
                list_two_radio_id = 4
            } else if (i == R.id.radio2_5) {
                list_two_radio_id = 5
            } else if (i == R.id.radio2_6) {
                list_two_radio_id = 6
            } else if (i == R.id.radio2_7) {
                list_two_radio_id = 7
            } else if (i == R.id.radio2_8) {
                list_two_radio_id = 8
            } else if (i == R.id.radio2_9) {
                list_two_radio_id = 9
            } else if (i == R.id.radio2_10) {
                list_two_radio_id = 10
            } else if (i == R.id.radio2_11) {
                list_two_radio_id = 11
            } else if (i == R.id.radio2_12) {
                list_two_radio_id = 12
            }
        })

        binding.listLayoutThird.radioGroupListThird.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener
        { radioGroup, i ->
            if (i == R.id.radio3_1) {
                list_third_radio_id = 1
            } else if (i == R.id.radio3_2) {
                list_third_radio_id = 2
            } else if (i == R.id.radio3_3) {
                list_third_radio_id = 3
            } else if (i == R.id.radio3_4) {
                list_third_radio_id = 4
            } else if (i == R.id.radio3_5) {
                list_third_radio_id = 5
            } else if (i == R.id.radio3_6) {
                list_third_radio_id = 6
            } else if (i == R.id.radio3_7) {
                list_third_radio_id = 7
            }
        })

        viewModel.getTax(header, "25")
    }

    override fun onClick(view: View?) {
        if (view!!.id == R.id.one_expiry_date) {
            expiry_id = 1
            getDate()
        } else if (view!!.id == R.id.one_upload_front) {
            image_id = 1
            uploadDoc()
        } else if (view!!.id == R.id.one_upload_back) {
            image_id = 2
            uploadDoc()
        } else if (view!!.id == R.id.second_expiry_date) {
            expiry_id = 2
            getDate()
        } else if (view!!.id == R.id.second_upload_front) {
            image_id = 3
            uploadDoc()
        } else if (view!!.id == R.id.second_upload_back) {
            image_id = 4
            uploadDoc()
        } else if (view!!.id == R.id.third_expiry_date) {
            expiry_id = 3
            getDate()
        } else if (view!!.id == R.id.third_upload_front) {
            image_id = 5
            uploadDoc()
        } else if (view!!.id == R.id.third_upload_back) {
            image_id = 6
            uploadDoc()
        } else if (view!!.id == R.id.btn_update) {
            dataList.clear()

            var uploadDataOne = UploadDataForm(
                profile_pic!!, profile_pic!!,
                binding.listLayoutOne.docNo.text.toString(),
                binding.listLayoutOne.authority.text.toString(),
                binding.listLayoutOne.oneExpiryDate.text.toString()
            )
            var listOneData = ListOneData(uploadDataOne, list_one_radio_id!!, true)

            var uploadDataTwo = UploadDataForm(
                profile_pic!!, profile_pic!!,
                binding.listLayoutTwo.docNo.text.toString(),
                binding.listLayoutTwo.authority.text.toString(),
                binding.listLayoutTwo.secondExpiryDate.text.toString()
            )
            var listTwoData = ListTwoData(uploadDataTwo, list_two_radio_id!!, true)

            var uploadDataThree = UploadDataForm(
                profile_pic!!, profile_pic!!,
                binding.listLayoutThird.docNo.text.toString(),
                binding.listLayoutThird.authority.text.toString(),
                binding.listLayoutThird.thirdExpiryDate.text.toString()
            )
            var listThirdData = ListThreeData(uploadDataThree, list_third_radio_id!!, true)

            if (option_id == 0) {
                toast("Please select Are you")
            } else if (list_one_radio_id == 0 && list_two_radio_id == 0 && list_third_radio_id == 0) {
                toast("Please select List 1 option or (List 2 and List 3)")
            } else {
                var details = Details(listOneData, listTwoData, listThirdData, option_id!!)
                var postI9 = PostI9Form("create", details, 25)
                viewModel.updateForm(header, postI9)
            }
        }
    }

    private fun getDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                //    binding.dob.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth)
                setTextView(year, month, dayOfMonth)
            }, year, month, day
        )

        datePickerDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            //  binding.uploadImage.setImageURI(fileUri)
            profile_pic =
                "https://i.picsum.photos/id/658/200/300.jpg?hmac=K1TI0jSVU6uQZCZkkCMzBiau45UABMHNIqoaB9icB_0"
            toast(profile_pic.toString())

            documentUpload(fileUri)

            //You can get File object from intent
            val file: File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            val filePath: String = ImagePicker.getFilePath(data)!!
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            toast(ImagePicker.getError(data))
        } else {
            toast("Task Cancelled")
        }
    }

    private fun uploadDoc() {
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    private fun documentUpload(fileUri: Uri?) {
        if (image_id == 1) {
            binding.listLayoutOne.oneUploadFront.setImageURI(fileUri)
        } else if (image_id == 2) {
            binding.listLayoutOne.oneUploadBack.setImageURI(fileUri)
        } else if (image_id == 3) {
            binding.listLayoutTwo.secondUploadFront.setImageURI(fileUri)
        } else if (image_id == 4) {
            binding.listLayoutTwo.secondUploadBack.setImageURI(fileUri)
        } else if (image_id == 5) {
            binding.listLayoutThird.thirdUploadFront.setImageURI(fileUri)
        } else if (image_id == 6) {
            binding.listLayoutThird.thirdUploadBack.setImageURI(fileUri)
        }
    }

    private fun setTextView(year: Int, month: Int, dayOfMonth: Int) {
        if (expiry_id == 1) {
            binding.listLayoutOne.oneExpiryDate.setText("" + year + "-" + month + "-" + dayOfMonth)
        } else if (expiry_id == 2) {
            binding.listLayoutTwo.secondExpiryDate.setText("" + year + "-" + month + "-" + dayOfMonth)
        } else if (expiry_id == 3) {
            binding.listLayoutThird.thirdExpiryDate.setText("" + year + "-" + month + "-" + dayOfMonth)
        }
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(response: GetI9Form) {
        progress_bar.hide()
        if (response.success && response.code == 200 && response.status.equals("ok")) {
            if (response.data.option_id != null) {
                option_id = response.data.option_id
                if (response.data.option_id == 1) {
                    binding.radio1.isChecked = true
                } else if (response.data.option_id == 2) {
                    binding.radio2.isChecked = true
                } else if (response.data.option_id == 3) {
                    binding.radio3.isChecked = true
                } else if (response.data.option_id == 4) {
                    binding.radio4.isChecked = true
                }
            }

            if(response.data.List1.size>0){
                for(listOne in response.data.List1){
                    if(listOne.isSelected){
                        list_one_radio_id = listOne.id
                        binding.listLayoutOne.radio11.isChecked = true
                        binding.listLayoutOne.docNo.setText(listOne.details.doc_no)
                        binding.listLayoutOne.oneExpiryDate.setText(listOne.details.expiry_date)
                        binding.listLayoutOne.authority.setText(listOne.details.authority)
                        if(listOne.details.back_url!=null){
                            Util.loadImage(
                                binding.listLayoutOne.oneUploadBack, listOne.details.back_url,
                                Util.getCircularDrawable(this))
                        }

                        if(listOne.details.front_url!=null){
                            Util.loadImage(
                                binding.listLayoutOne.oneUploadFront, listOne.details.front_url,
                                Util.getCircularDrawable(this))
                        }


                    }
                }
            }

            if(response.data.List2.size>0){
                for(listOne in response.data.List2){
                    if(listOne.isSelected){
                        list_two_radio_id = listOne.id
                        binding.listLayoutTwo.docNo.setText(listOne.details.doc_no)
                        binding.listLayoutTwo.secondExpiryDate.setText(listOne.details.expiry_date)
                        binding.listLayoutTwo.authority.setText(listOne.details.authority)
                        if(listOne.details.back_url!=null){
                            Util.loadImage(
                                binding.listLayoutTwo.secondUploadBack, listOne.details.back_url,
                                Util.getCircularDrawable(this))
                        }

                        if(listOne.details.front_url!=null){
                            Util.loadImage(
                                binding.listLayoutTwo.secondUploadFront, listOne.details.front_url,
                                Util.getCircularDrawable(this))
                        }

                        if(listOne.id == 1){
                            binding.listLayoutTwo.radio21.isChecked = true
                        }else if(listOne.id == 2){
                            binding.listLayoutTwo.radio22.isChecked = true
                        }
                        else if(listOne.id == 3){
                            binding.listLayoutTwo.radio23.isChecked = true
                        }else if(listOne.id == 4){
                            binding.listLayoutTwo.radio24.isChecked = true
                        }else if(listOne.id == 5){
                            binding.listLayoutTwo.radio25.isChecked = true
                        }
                        else if(listOne.id == 6){
                            binding.listLayoutTwo.radio26.isChecked = true
                        }
                        else if(listOne.id == 7){
                            binding.listLayoutTwo.radio27.isChecked = true
                        }else if(listOne.id == 8){
                            binding.listLayoutTwo.radio28.isChecked = true
                        }
                        else if(listOne.id == 9){
                            binding.listLayoutTwo.radio29.isChecked = true
                        }
                        else if(listOne.id == 10){
                            binding.listLayoutTwo.radio210.isChecked = true
                        }
                        else if(listOne.id == 11){
                            binding.listLayoutTwo.radio211.isChecked = true
                        }
                        else if(listOne.id == 12){
                            binding.listLayoutTwo.radio212.isChecked = true
                        }
                        else if(listOne.id == 13){
                            binding.listLayoutTwo.radio213.isChecked = true
                        }
                    }

                }
            }

            if(response.data.List3.size>0){
                for(listOne in response.data.List3){
                    if(listOne.isSelected){
                        list_third_radio_id = listOne.id
                        binding.listLayoutThird.docNo.setText(listOne.details.doc_no)
                        binding.listLayoutThird.thirdExpiryDate.setText(listOne.details.expiry_date)
                        binding.listLayoutThird.authority.setText(listOne.details.authority)
                        if(listOne.details.back_url!=null){
                            Util.loadImage(
                                binding.listLayoutThird.thirdUploadBack, listOne.details.back_url,
                                Util.getCircularDrawable(this))
                        }

                        if(listOne.details.front_url!=null){
                            Util.loadImage(
                                binding.listLayoutThird.thirdUploadFront, listOne.details.front_url,
                                Util.getCircularDrawable(this))
                        }
                        if(listOne.id == 1){
                            binding.listLayoutThird.radio31.isChecked = true
                        }else if(listOne.id == 2){
                            binding.listLayoutThird.radio32.isChecked = true
                        }
                        else if(listOne.id == 3){
                            binding.listLayoutThird.radio33.isChecked = true
                        }else if(listOne.id == 4){
                            binding.listLayoutThird.radio34.isChecked = true
                        }else if(listOne.id == 5){
                            binding.listLayoutThird.radio35.isChecked = true
                        }
                        else if(listOne.id == 6){
                            binding.listLayoutThird.radio36.isChecked = true
                        }
                        else if(listOne.id == 7){
                            binding.listLayoutThird.radio37.isChecked = true
                        }

                    }
                }
            }
        }
    }

    override fun onSuccess(response: SignResponse) {
        progress_bar.hide()
        if (response.success && response.status.equals("ok") && response.code.toInt() == 200) {
            toast("Successfully Updated")
        } else {
            toast(response.msg)
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
    }
}
