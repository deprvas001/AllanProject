package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityAddCertificateSecondBinding;
import com.development.allanproject.adapter.CertificateAdapter;
import com.development.allanproject.model.CertificateClass;

import java.util.ArrayList;
import java.util.List;

public class AddCertificateSecond extends BaseActivity implements View.OnClickListener {
   CertificateAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    ActivityAddCertificateSecondBinding  secondBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        secondBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_certificate_second);
        setClickListener();
    }

    private void setClickListener(){
        secondBinding.back.setOnClickListener(this);
        secondBinding.btnNext.setOnClickListener(this);
        setRecyclerView();
    }

    private void setRecyclerView() {

        List<CertificateClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            CertificateClass certificate = new CertificateClass();
            certificate.setName("Certificate");
            dataList.add(certificate);
        }
        adapter = new CertificateAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
        secondBinding.recyclerView.setLayoutManager(mLayoutManager);
        secondBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        secondBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;

            case R.id.btn_next:
                 startActivity(new Intent(this,AddExperience.class));
                break;
        }
    }
}
