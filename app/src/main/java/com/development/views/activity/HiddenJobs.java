package com.development.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityHiddenJobsBinding;
import com.development.views.adapter.EducationAdapter;
import com.development.views.adapter.HiddenJobAdapter;
import com.development.views.model.EducationClass;
import com.development.views.model.HiddenJobClass;

import java.util.ArrayList;
import java.util.List;

public class HiddenJobs extends BaseActivity implements View.OnClickListener {
ActivityHiddenJobsBinding jobsBinding;
    HiddenJobAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobsBinding = DataBindingUtil.setContentView(this,R.layout.activity_hidden_jobs);
        setClickListener();
    }

    private void setClickListener(){
        jobsBinding.back.setOnClickListener(this);
        setRecyclerView();

    }

    private void setRecyclerView() {

        List<HiddenJobClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            HiddenJobClass job = new HiddenJobClass();
            job.setName("Facility Name");
            dataList.add(job);
        }
        adapter = new HiddenJobAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
        jobsBinding.recyclerView.setLayoutManager(mLayoutManager);
        jobsBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        jobsBinding.recyclerView.setAdapter(adapter);
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
