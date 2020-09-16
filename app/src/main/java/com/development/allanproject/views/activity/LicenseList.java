package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityLicenseListBinding;
import com.development.allanproject.adapter.LicenseListAdapter;
import com.development.allanproject.model.LicenseListClass;

import java.util.ArrayList;
import java.util.List;

public class LicenseList extends BaseActivity implements View.OnClickListener {
    LicenseListAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    ActivityLicenseListBinding licenseListBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        licenseListBinding = DataBindingUtil.setContentView(this,R.layout.activity_license_list);
       setClickListener();
    }

    private void setClickListener(){
        licenseListBinding.back.setOnClickListener(this);
        setRecyclerView();
    }


    private void setRecyclerView() {

        List<LicenseListClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            LicenseListClass license = new  LicenseListClass();
            license.setName("License");
            dataList.add(license);
        }
       /* adapter = new LicenseListAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
        licenseListBinding.recyclerView.setLayoutManager(mLayoutManager);
        licenseListBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        licenseListBinding.recyclerView.setAdapter(adapter);*/
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
