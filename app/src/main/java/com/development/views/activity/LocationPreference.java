package com.development.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityLocationPreferenceBinding;
import com.development.views.adapter.CertificateAdapter;
import com.development.views.model.CertificateClass;

import java.util.ArrayList;
import java.util.List;

public class LocationPreference extends BaseActivity implements View.OnClickListener {
ActivityLocationPreferenceBinding preferenceBinding;
    CertificateAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceBinding = DataBindingUtil.setContentView(this,R.layout.activity_location_preference);
    }

    private void setClickListener(){
        preferenceBinding.back.setOnClickListener(this);
        setRecyclerView();
    }

    private void setRecyclerView() {

        List<CertificateClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            CertificateClass certificate = new CertificateClass();
            certificate.setName("San Franscisco.");
            dataList.add(certificate);
        }
        adapter = new CertificateAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
        preferenceBinding.recyclerView.setLayoutManager(mLayoutManager);
        preferenceBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        preferenceBinding.recyclerView.setAdapter(adapter);
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
