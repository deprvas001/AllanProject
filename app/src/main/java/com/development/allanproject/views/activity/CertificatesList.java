package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityCertificatesListBinding;
import com.development.allanproject.adapter.EducationAdapter;
import com.development.allanproject.model.EducationClass;

import java.util.ArrayList;
import java.util.List;

public class CertificatesList extends  BaseActivity implements View.OnClickListener {
    EducationAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    ActivityCertificatesListBinding listBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listBinding = DataBindingUtil.setContentView(this,R.layout.activity_certificates_list);
        setClickListener();
    }

    private void setClickListener(){
        listBinding.back.setOnClickListener(this);
        setRecyclerView();

    }

    private void setRecyclerView() {

        List<EducationClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            EducationClass educationClass = new EducationClass();
            educationClass.setName("Institute");
            dataList.add(educationClass);
        }
        adapter = new EducationAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
        listBinding.recyclerView.setLayoutManager(mLayoutManager);
        listBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        listBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }

    }
}
