package com.development.views.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.development.allanproject.MainActivity;
import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityAddSpecialtyBinding;
import com.development.views.adapter.CertificateAdapter;
import com.development.views.model.CertificateClass;

import java.util.ArrayList;
import java.util.List;

public class AddSpecialty extends BaseActivity implements View.OnClickListener {
    CertificateAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    ActivityAddSpecialtyBinding specialtyBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        specialtyBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_specialty);
        setClickListener();
    }

    private void setClickListener(){
        specialtyBinding.back.setOnClickListener(this);
        specialtyBinding.btnNext.setOnClickListener(this);
        setRecyclerView();
    }

    private void setRecyclerView() {

        List<CertificateClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            CertificateClass certificate = new CertificateClass();
            certificate.setName("Ambulatory Care");
            dataList.add(certificate);
        }
        adapter = new CertificateAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this);
        specialtyBinding.recyclerView.setLayoutManager(mLayoutManager);
        specialtyBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        specialtyBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;

            case R.id.btn_next:
                customDialog();
                break;
        }

    }

    private void customDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddSpecialty.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.request_preference_dialog_layout, viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
