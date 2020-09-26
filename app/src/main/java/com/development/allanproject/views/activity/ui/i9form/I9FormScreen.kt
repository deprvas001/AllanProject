package com.development.allanproject.views.activity.ui.i9form

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.development.allanproject.R
import com.development.allanproject.databinding.ActivityI9FormScreenBinding
import com.development.allanproject.model.CertificateClass
import com.development.allanproject.model.i9form.I9FormPost
import com.development.allanproject.model.i9form.UploadDataForm
import com.development.allanproject.util.toast
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class I9FormScreen : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityI9FormScreenBinding
    var profile_pic: String? = null
    var image_id: Int? = null
    var expiry_id: Int? = null
    var list_one_radio_id: Int? = null
    var list_two_radio_id: Int? = null
    var list_third_radio_id: Int? = null
    var dataList: ArrayList<I9FormPost> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_i9_form_screen)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_i9_form_screen)

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
            }else  if (i == R.id.radio3_2) {
                list_third_radio_id = 2
            }else  if (i == R.id.radio3_3) {
                list_third_radio_id = 3
            }else  if (i == R.id.radio3_4) {
                list_third_radio_id = 4
            }else  if (i == R.id.radio3_5) {
                list_third_radio_id = 5
            }else  if (i == R.id.radio3_6) {
                list_third_radio_id = 6
            }
            else  if (i == R.id.radio3_7) {
                list_third_radio_id = 2
            }
        })
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
        }else if(view!!.id == R.id.btn_update){
            dataList.clear()
            var uploadDataOne = UploadDataForm(
                profile_pic!!,profile_pic!!, binding.listLayoutOne.docNo.text.toString(),
                binding.listLayoutOne.authority.text.toString(),
                binding.listLayoutOne.oneExpiryDate.text.toString()
            )
            var listOneData = I9FormPost(1,1,
                list_one_radio_id!!,uploadDataOne)

           dataList.add(listOneData!!)

            var uploadDataTwo = UploadDataForm(
                profile_pic!!,profile_pic!!, binding.listLayoutTwo.docNo.text.toString(),
                binding.listLayoutTwo.authority.text.toString(),
                binding.listLayoutTwo.secondExpiryDate.text.toString()
            )
            var listTwoData = I9FormPost(2,2,
                list_two_radio_id!!,uploadDataTwo)

            dataList.add(listTwoData!!)

            var uploadDataThird = UploadDataForm(
                profile_pic!!,profile_pic!!, binding.listLayoutThird.docNo.text.toString(),
                binding.listLayoutThird.authority.text.toString(),
                binding.listLayoutThird.thirdExpiryDate.text.toString()
            )
            var listThirdData = I9FormPost(3,3,
                list_third_radio_id!!,uploadDataThird)

            dataList.add(listThirdData!!)
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
        //   mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        datePickerDialog.getDatePicker()
//            .setMaxDate(System.currentTimeMillis() - 568025136000L)
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
}
