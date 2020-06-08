package com.development.views.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityAddCertificateSecondBinding;
import com.development.allanproject.databinding.FragmentMyDocumentBinding;
import com.development.views.adapter.CertificateAdapter;
import com.development.views.adapter.MyDocumentAdapter;
import com.development.views.model.CertificateClass;
import com.development.views.model.MyDocumentClass;

import java.util.ArrayList;
import java.util.List;

public class MyDocument extends Fragment {
    MyDocumentAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    FragmentMyDocumentBinding documentBinding;
    public MyDocument() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        documentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_document, container, false);
        setRecyclerView();
        return documentBinding.getRoot();
    }

    private void setRecyclerView() {

        List<MyDocumentClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            MyDocumentClass document = new MyDocumentClass();
            document.setName("Document Name");
            dataList.add(document);
        }
        adapter = new MyDocumentAdapter(getActivity(), dataList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        documentBinding.recyclerView.setLayoutManager(mLayoutManager);
        documentBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        documentBinding.recyclerView.setAdapter(adapter);
    }
}

