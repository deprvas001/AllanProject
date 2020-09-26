package com.development.allanproject.views.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityLanguageScreenBinding;
import com.development.allanproject.adapter.LanguageAdapter;
import com.development.allanproject.model.LanguageClass;

import java.util.ArrayList;
import java.util.List;

public class LanguageScreen extends BaseActivity implements View.OnClickListener {
    LanguageAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    ActivityLanguageScreenBinding  screenBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenBinding = DataBindingUtil.setContentView(this,R.layout.activity_language_screen);
       setClickListener();
    }


    private void setClickListener(){
        screenBinding.back.setOnClickListener(this);
        setRecyclerView();
    }
    
    private void setRecyclerView() {

        List<LanguageClass> dataList = new ArrayList<>();

        for(int i=0;i<=5;i++){
            LanguageClass language = new LanguageClass();
            language.setName("language");
            dataList.add(language);
        }
//        adapter = new LanguageAdapter(this, dataList);
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
