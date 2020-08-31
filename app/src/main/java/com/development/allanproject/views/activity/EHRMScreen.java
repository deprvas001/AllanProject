package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityEHRMScreenBinding;
import com.development.allanproject.adapter.EHRSAdapter;
import com.development.allanproject.model.EHRSClass;

import java.util.ArrayList;
import java.util.List;

public class EHRMScreen extends BaseActivity implements View.OnClickListener {
ActivityEHRMScreenBinding screenBinding;
    EHRSAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenBinding = DataBindingUtil.setContentView(this,R.layout.activity_e_h_r_m_screen);
        setClickListener();
    }

    private void setClickListener(){
        screenBinding.back.setOnClickListener(this);
        setRecyclerView();

    }

    private void setRecyclerView() {

        List<EHRSClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            EHRSClass ehrsClass = new EHRSClass();
            ehrsClass.setName("Certificate");
            dataList.add(ehrsClass);
        }
        adapter = new EHRSAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
        screenBinding.recyclerView.setLayoutManager(mLayoutManager);
        screenBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        screenBinding.recyclerView.setAdapter(adapter);
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
