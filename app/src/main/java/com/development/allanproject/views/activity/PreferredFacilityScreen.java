package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityPreferredFacilityScreenBinding;
import com.development.allanproject.adapter.PreferedFacilityAdapter;
import com.development.allanproject.model.PreferredFacilityClass;

import java.util.ArrayList;
import java.util.List;

public class PreferredFacilityScreen extends BaseActivity implements View.OnClickListener {
ActivityPreferredFacilityScreenBinding screenBinding;
    PreferedFacilityAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenBinding = DataBindingUtil.setContentView(this,R.layout.activity_preferred_facility_screen);
         setClickListener();
    }

    private void setClickListener(){
        screenBinding.back.setOnClickListener(this);
        setRecyclerView();
    }

    private void setRecyclerView() {

        List<PreferredFacilityClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            PreferredFacilityClass language = new PreferredFacilityClass();
            language.setName("Facility Name");
            dataList.add(language);
        }
//        adapter = new PreferedFacilityAdapter(this, dataList);
//        mLayoutManager = new LinearLayoutManager(this);
//        screenBinding.recyclerView.setLayoutManager(mLayoutManager);
//        screenBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
//        screenBinding.recyclerView.setAdapter(adapter);
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
