package com.development.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.development.allanproject.R;
import com.development.allanproject.databinding.ActivityFaqScreenBinding;
import com.development.views.adapter.FaqAdapter;
import com.development.views.model.Faq;

import java.util.ArrayList;
import java.util.List;

public class FaqScreen extends BaseActivity implements View.OnClickListener {
ActivityFaqScreenBinding screenBinding;
    List<Faq> faqList = new ArrayList<>();
    private FaqAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenBinding = DataBindingUtil.setContentView(this,R.layout.activity_faq_screen);
         setClickListener();
    }

    private void setClickListener(){
        screenBinding.back.setOnClickListener(this);
        setReyclerView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    private void setReyclerView(){
        mAdapter = new FaqAdapter(faqList);
        mLayoutManager = new LinearLayoutManager(FaqScreen.this);
        screenBinding.recyclerView.setLayoutManager(mLayoutManager);
        screenBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        screenBinding.recyclerView.setAdapter(mAdapter);

        faqData();
    }

    private void faqData(){
        faqList.clear();
        for(int i=0;i<6;i++){
            Faq faq = new Faq("Lorem ipsum dolor ?","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            faqList.add(faq);
        }
        mAdapter.notifyDataSetChanged();

    }
}
