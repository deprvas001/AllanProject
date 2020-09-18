package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityHealthDocumentBinding;
import com.development.allanproject.adapter.HealthDocumentAdpater;
import com.development.allanproject.model.HealthDocumentClass;

import java.util.ArrayList;
import java.util.List;

public class HealthDocument extends  BaseActivity implements View.OnClickListener {
    HealthDocumentAdpater adapter;
    RecyclerView.LayoutManager mLayoutManager;
    ActivityHealthDocumentBinding documentBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        documentBinding = DataBindingUtil.setContentView(this,R.layout.activity_health_document);
        setClickListener();
    }

    private void setClickListener(){
        documentBinding.back.setOnClickListener(this);
        setRecyclerView();
    }

    @Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.back:
                 finish();
                 break;
         }
    }

    private void setRecyclerView() {

        List<HealthDocumentClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            HealthDocumentClass document = new HealthDocumentClass();
            document.setName("Health Document");
            dataList.add(document);
        }
//        adapter = new HealthDocumentAdpater(this, dataList);
//        mLayoutManager = new LinearLayoutManager(this);
//        documentBinding.recyclerView.setLayoutManager(mLayoutManager);
//        documentBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
//        documentBinding.recyclerView.setAdapter(adapter);
    }
}
