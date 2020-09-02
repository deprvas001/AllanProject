package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivitySetFacilityTypeBinding;
import com.development.allanproject.adapter.FacilityTypeAdapter;
import com.development.allanproject.model.FacilityTypeModel;

import java.util.ArrayList;
import java.util.List;

public class SetFacilityType extends BaseActivity implements View.OnClickListener {
    FacilityTypeAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    ActivitySetFacilityTypeBinding typeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeBinding = DataBindingUtil.setContentView(this,R.layout.activity_set_facility_type);
        typeBinding.btnNext.setOnClickListener(this);
        setRecyclerView();
    }

    private void setRecyclerView() {

        List<FacilityTypeModel> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            FacilityTypeModel certificate = new FacilityTypeModel();
            certificate.setName("Certificate");
            dataList.add(certificate);
        }
      /*  adapter = new FacilityTypeAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
        typeBinding.recyclerView.setLayoutManager(mLayoutManager);
        typeBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        typeBinding.recyclerView.setAdapter(adapter);*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                startActivity(new Intent(this,SetPreference.class));
                break;
        }
    }
}
