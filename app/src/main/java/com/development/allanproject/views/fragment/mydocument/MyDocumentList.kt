package com.development.allanproject.views.fragment.mydocument

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.development.allanproject.R
import com.development.allanproject.data.session.SessionManager
import com.development.allanproject.databinding.FragmentMyDocumentListBinding
import com.development.allanproject.model.CertificateClass
import com.development.allanproject.util.AuthListener
import com.development.allanproject.util.documentListener.DocumentListener
import com.development.allanproject.views.activity.ui.adddoucment.AddDocumentViewModel
import com.development.allanproject.views.activity.ui.adddoucment.AddDocumentViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class MyDocumentList : Fragment() {
  private lateinit var binding: FragmentMyDocumentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding= DataBindingUtil.inflate(inflater,R.layout.fragment_my_document_list, container, false )

        return binding.root
    }

}
